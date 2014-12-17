package com.kiev.msupport.controller.db;

import com.kiev.msupport.controller.view.report.ReportTable;
import com.kiev.msupport.domain.*;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import java.math.BigDecimal;
import java.util.*;

public class MaterialsMngrBean {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("materials");
    EntityManager em = emf.createEntityManager();

    CriteriaBuilder builder = em.getCriteriaBuilder();

    public void bootstrap() {
        updateEntity(new CategoryEntity("огнеупоры"));
        updateEntity(new CategoryEntity("кислотоупоры"));
        updateEntity(new CategoryEntity("упаковка и тара"));
        updateEntity(new CategoryEntity("металлопрокат"));
        updateEntity(new CategoryEntity("подшипники"));
        updateEntity(new CategoryEntity("сырье"));
        updateEntity(new CategoryEntity("СИЗ"));
        updateEntity(new CategoryEntity("хоз. товары"));
        updateEntity(new CategoryEntity("електрика"));
        updateEntity(new CategoryEntity("КИП"));
        updateEntity(new CategoryEntity("насосное оборудование"));
        updateEntity(new CategoryEntity("ж/д запчасти"));
        updateEntity(new CategoryEntity("авто запчасти"));
        updateEntity(new CategoryEntity("ГСМ"));
        updateEntity(new CategoryEntity("нестандартное оборудование"));
        updateEntity(new CategoryEntity("ферроматериалы"));

        updateEntity(new UnitEntity("тн"));
        updateEntity(new UnitEntity("шт"));
        updateEntity(new UnitEntity("кг"));
        updateEntity(new UnitEntity("м"));
        updateEntity(new UnitEntity("к-кт"));
        updateEntity(new UnitEntity("л"));
        updateEntity(new UnitEntity("п"));

        updateEntity(new DepartmentEntity("рмц №1"));
        updateEntity(new DepartmentEntity("рмц №2"));
        updateEntity(new DepartmentEntity("рмц №3"));
        updateEntity(new DepartmentEntity("рмц №4"));
        updateEntity(new DepartmentEntity("склад №1"));
        updateEntity(new DepartmentEntity("склад №2"));
        updateEntity(new DepartmentEntity("склад №3"));
        updateEntity(new DepartmentEntity("склад №4"));
        updateEntity(new DepartmentEntity("цех №1"));
        updateEntity(new DepartmentEntity("цех №2"));
        updateEntity(new DepartmentEntity("цех №3"));
        updateEntity(new DepartmentEntity("цех №4"));

        updateEntity(new Manager("Пиголенко О."));
        updateEntity(new Manager("Краченко Ж."));
        updateEntity(new Manager("Петрук І."));
        updateEntity(new Manager("Селецкая Р."));
        updateEntity(new Manager("Мироненко Д."));
        updateEntity(new Manager("Мироненко Д."));
        updateEntity(new Manager("Коваль А."));
        updateEntity(new Manager("Олимпиев Н."));
        updateEntity(new Manager("Рямушкина А."));
        updateEntity(new Manager("Задворный Ю."));
        updateEntity(new Manager("Попович К."));
    }


    public <T> List<T> findOffset(int from, int pageSize, Class clazz) {
        CriteriaQuery<T> cQuery = builder.createQuery(clazz);
        Root<T> start = cQuery.from(clazz);
        CriteriaQuery<T> select = cQuery.select(start);

        TypedQuery typedQuery = em.createQuery(select);
        typedQuery.setFirstResult(from * pageSize);
        typedQuery.setMaxResults(pageSize);
        return typedQuery.getResultList();
    }

    public <T> List<T> findAll(Class clazz) {
        CriteriaQuery<T> cQuery = builder.createQuery(clazz);
        Root<T> start = cQuery.from(clazz);
        CriteriaQuery<T> select = cQuery.select(start);

        TypedQuery typedQuery = em.createQuery(select);
        return typedQuery.getResultList();
    }

    public MTREntity getMTR(Long categoryId, Long unitsId, String name) {
        Query q = em.createQuery("select e from MTREntity e where cat_id=:cId and " +
                "unit_id=:uId and name = :name", MTREntity.class);
        q.setParameter("cId", categoryId);
        q.setParameter("name", name);
        q.setParameter("uId", unitsId);
        try {
            return (MTREntity) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public <T> Map<Long, ArrayList<T>> groupedByDepartmentFoundByMTR(MTREntity mtr, Class clazz) {
        CriteriaQuery<Tuple> q = builder.createTupleQuery();
        Root<T> c = q.from(clazz);

        q.multiselect(c.alias("entity"), c.get("department").alias("department"));
        q.where(builder.equal(c.get("mtr"), mtr));
//        q.groupBy(c.get("department"));
        TypedQuery<Tuple> typedQuery = em.createQuery(q);
        List<Tuple> result = typedQuery.getResultList();

        Map<Long, ArrayList<T>> mapByDeps = new HashMap<Long, ArrayList<T>>();
        //filter by departments
        for (Tuple r : result) {
            T entity = (T) r.get("entity");
            Long depId = ((DepartmentEntity) r.get("department")).getId();
            if (!mapByDeps.containsKey(depId)) {
                mapByDeps.put(depId, new ArrayList<T>());
            }
            mapByDeps.get(depId).add(entity);
        }

        return mapByDeps;
    }

    public <T> Map<Long, ArrayList<T>> groupedByDepartmentFoundByMTRByDate(MTREntity mtr, Class clazz, Date date) {
        CriteriaQuery<Tuple> q = builder.createTupleQuery();
        Root<T> c = q.from(clazz);

        q.multiselect(c.alias("entity"), c.get("department").alias("department"));
        q.where(builder.equal(c.get("mtr"), mtr), builder.greaterThan(c.<Date>get("date"), date));
//        q.groupBy(c.get("department"));
        TypedQuery<Tuple> typedQuery = em.createQuery(q);
        List<Tuple> result = typedQuery.getResultList();

        Map<Long, ArrayList<T>> mapByDeps = new HashMap<Long, ArrayList<T>>();
        //filter by departments
        for (Tuple r : result) {
            T entity = (T) r.get("entity");
            Long depId = ((DepartmentEntity) r.get("department")).getId();
            if (!mapByDeps.containsKey(depId)) {
                mapByDeps.put(depId, new ArrayList<T>());
            }
            mapByDeps.get(depId).add(entity);
        }

        return mapByDeps;
    }

    private BigDecimal incomeAmountSum(List<IncomeEntity> list) {
        BigDecimal bd = new BigDecimal(0);
        if (list == null || list.isEmpty()) return null;
        for (IncomeEntity e : list) {
            bd = bd.add(new BigDecimal(e.getAmount()));
        }

        return bd;
    }

    private BigDecimal incomePriceSum(List<IncomeEntity> list) {
        BigDecimal bd = new BigDecimal(0);

        if (list == null || list.isEmpty()) return null;
        for (IncomeEntity e : list) {
            bd = bd.add(new BigDecimal(e.getPrice()).multiply(new BigDecimal(e.getAmount())));
        }

        return bd;
    }

    private BigDecimal expenseAmountSum(List<ExpenseEntity> list) {
        BigDecimal bd = new BigDecimal(0);
        if (list == null || list.isEmpty()) return null;
        for (ExpenseEntity e : list) {
            bd = bd.add(new BigDecimal(e.getAmount()));
        }

        return bd;
    }

    private BigDecimal requestAmountSum(List<RequestEntity> list) {
        BigDecimal bd = new BigDecimal(0);
        if (list == null || list.isEmpty()) return null;
        for (RequestEntity e : list) {
            bd = bd.add(new BigDecimal(e.getAmount()));
        }

        return bd;
    }

    public List<ReportTable> getReports(String name) {
        List<ReportTable> reportTableList = new ArrayList<ReportTable>();
        try {
            List<MTREntity> mtrs = getListByName(MTREntity.class, name, null);
            reportTableList = generateReportsForMTR(mtrs);
        } catch (NoResultException e) {
            e.printStackTrace();
        }

        return reportTableList;
    }

    public List<ReportTable> getReports(Long categoryId) {
        List<ReportTable> reportTableList = new ArrayList<ReportTable>();
        try {
            CategoryEntity category = em.find(CategoryEntity.class, categoryId);
            List<MTREntity> mtrs = getListByName(MTREntity.class, null, category);
            reportTableList = generateReportsForMTR(mtrs);
        } catch (NoResultException e) {
            e.printStackTrace();
        }

        return reportTableList;
    }

    public List<ReportTable> getReports(String name, Long categoryId) {
        List<ReportTable> reportTableList = new ArrayList<ReportTable>();
        try {
            CategoryEntity category = em.find(CategoryEntity.class, categoryId);
            List<MTREntity> mtrs = getListByName(MTREntity.class, name, category);
            reportTableList = generateReportsForMTR(mtrs);
        } catch (NoResultException e) {
            e.printStackTrace();
        }

        return reportTableList;
    }


    public List<ReportTable> getReports() {
        List<ReportTable> reportTableList = new ArrayList<ReportTable>();
        try {
            List<MTREntity> mtrs = findAll(MTREntity.class);
            reportTableList = generateReportsForMTR(mtrs);
        } catch (NoResultException e) {
            e.printStackTrace();
        }

        return reportTableList;
    }

    private List<ReportTable> generateReportsForMTR(List<MTREntity> mtrs) {
        List<ReportTable> reportTableList = new ArrayList<ReportTable>();

        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, 1);
        Date currentMonthStartDate = c.getTime();

        for (MTREntity mtr : mtrs) {

            Map<Long, ArrayList<IncomeEntity>> incomesMap = groupedByDepartmentFoundByMTR(mtr, IncomeEntity.class);
            Map<Long, ArrayList<ExpenseEntity>> expenseMap = groupedByDepartmentFoundByMTR(mtr, ExpenseEntity.class);

            Map<Long, ArrayList<IncomeEntity>> incomesMapInMonth =
                    groupedByDepartmentFoundByMTRByDate(mtr, IncomeEntity.class, currentMonthStartDate);
            Map<Long, ArrayList<ExpenseEntity>> expenseMapInMonth
                    = groupedByDepartmentFoundByMTRByDate(mtr, ExpenseEntity.class, currentMonthStartDate);
            Map<Long, ArrayList<RequestEntity>> requestMapInMonth
                    = groupedByDepartmentFoundByMTRByDate(mtr, RequestEntity.class, currentMonthStartDate);


            //filter by departments

            List<DepartmentEntity> deps = findAll(DepartmentEntity.class);
            //all times queries
            BigDecimal residueForToday;
            BigDecimal fullPrice;
            BigDecimal residueForPeriod;
            BigDecimal monthIncome;
            BigDecimal demand;

            for (DepartmentEntity dep : deps) {
                Long d = dep.getId();
                BigDecimal incomeSum = incomeAmountSum(incomesMap.get(d));
                BigDecimal expenseSum = expenseAmountSum(expenseMap.get(d));

                if (incomeSum != null && expenseSum != null) residueForToday = incomeSum.subtract(expenseSum);
                else residueForToday = null;
                fullPrice = incomePriceSum(incomesMap.get(d));

                BigDecimal incomeSumP = incomeAmountSum(incomesMapInMonth.get(d));
                BigDecimal expenseSumP = expenseAmountSum(expenseMapInMonth.get(d));
                BigDecimal requestSumP = requestAmountSum(requestMapInMonth.get(d));

                if (incomeSumP != null && expenseSumP != null) residueForPeriod = incomeSumP.subtract(expenseSumP);
                else residueForPeriod = null;
                monthIncome = incomeSumP;
                demand = requestSumP;

                if (residueForPeriod == null
                        && residueForToday == null
                        && demand == null
                        && monthIncome == null
                        && fullPrice == null) {
                    continue;
                }

                ReportTable rep = new ReportTable(
                        mtr.getCategory().getName(),
                        mtr.getName(),
                        mtr.getUnits().getName(),
                        dep.getName(),
                        residueForToday == null ? "0" : residueForToday.toString(),
                        residueForPeriod == null ? "0" : residueForPeriod.toString(),
                        fullPrice.toString(),
                        demand == null ? "0" : demand.toString(),
                        monthIncome == null ? "0" : monthIncome.toString());

                reportTableList.add(rep);
            }
        }
        return reportTableList;
    }

    public <T> T getEntity(Class<T> clazz, Long id) {
        return em.find(clazz, id);
    }

    public CategoryEntity categoryIfNotExist(String name) {
        CategoryEntity r = getByName(CategoryEntity.class, name);
        if (r == null) {
            r = updateEntity(new CategoryEntity(name));
        }
        return r;
    }

    public <T> T getByName(Class<T> clazz, String name) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> q = cb.createQuery(clazz);
        Root<T> c = q.from(clazz);
        q.select(c).where(cb.equal(c.get("name"), name));

        TypedQuery<T> tquery = em.createQuery(q);

        T result = null;
        try {
            result = tquery.getSingleResult();
        } catch (NoResultException e) {
            e.printStackTrace();
        }
        return result;
    }

    public <T> List<T> getListByName(Class<T> clazz, String name, CategoryEntity category) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        EntityType<T> type = em.getMetamodel().entity(clazz);
        CriteriaQuery<T> q = cb.createQuery(clazz);
        Root<T> c = q.from(clazz);

        q.select(c);

        if (name != null && !name.isEmpty() && category == null) {
            q = q.where(
                    cb.like(
                            cb.lower(
                                    c.get(
                                            type.getDeclaredSingularAttribute("name", String.class)
                                    )
                            ), "%" + name.toLowerCase() + "%"
                    ));
        }

        if (name != null && !name.isEmpty() && category != null) {
                q = q.where(
                        cb.and(
                                cb.like(
                                        cb.lower(
                                                c.get(
                                                        type.getDeclaredSingularAttribute("name", String.class)
                                                )
                                        ), "%" + name.toLowerCase() + "%"
                                ), cb.equal(c.get("category"), category)));
        }

        if((name == null||name.isEmpty()) && category!=null){
            q = q.where(cb.equal(c.get("category"), category));
        }

        TypedQuery<T> tquery = em.createQuery(q);

        List<T> result = null;
        try {
            result = tquery.getResultList();
        } catch (NoResultException e) {
            e.printStackTrace();
        }
        return result;
    }

    public <T> T updateEntity(T e) {
        EntityTransaction et = em.getTransaction();
        et.begin();
        T upd = em.merge(e);
        et.commit();
        return upd;
    }

//    public long countMtrs(){
//        CriteriaBuilder qb = em.getCriteriaBuilder();
//        CriteriaQuery<Long> cq = qb.createQuery(Long.class);
//        cq.select(qb.count(cq.from(MTREntity.class)));
//        return em.createQuery(cq).getSingleResult();
//    }


    public <T> List<T> getByManagerAndCategory(Class<T> clazz, Manager man, CategoryEntity category) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> q = cb.createQuery(clazz);
        Root<T> c = q.from(clazz);
        if(category == null && man !=null){
            q = q.select(c).where(cb.equal(c.get("manager"), man));
        }
        if(category != null && man == null){
            q = q.where(cb.equal(c.get("category"), category));
        }

        if (category != null && man!=null) {
            q = q.where(cb.and(cb.equal(c.get("category"), category), cb.equal(c.get("manager"), man)));
        }

        TypedQuery<T> tquery = em.createQuery(q);

        List<T> result = null;
        try {
            result = tquery.getResultList();
        } catch (NoResultException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<AnalysisEntity> getAnalyticsByCategory(Long categoryId) {
        CategoryEntity category = em.find(CategoryEntity.class, categoryId);
        return getByManagerAndCategory(AnalysisEntity.class, null, category);
    }

    public List<AnalysisEntity> getAnalyticsByManager(Long managerId) {
        Manager man = em.find(Manager.class, managerId);
        return getByManagerAndCategory(AnalysisEntity.class, man, null);
    }

    public List<AnalysisEntity> getAnalyticsByManagerAndCategory(Long managerId, Long categoryId) {
        Manager man = em.find(Manager.class, managerId);
        CategoryEntity category = em.find(CategoryEntity.class, categoryId);
        return getByManagerAndCategory(AnalysisEntity.class, man, category);
    }


}
