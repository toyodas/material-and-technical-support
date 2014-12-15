package com.kiev.msupport.controller.db;

import com.kiev.msupport.controller.view.report.ReportTable;
import com.kiev.msupport.domain.*;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.*;

public class MaterialsMngrBean {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("materials");
    EntityManager em = emf.createEntityManager();

    CriteriaBuilder builder = em.getCriteriaBuilder();

    public Manager manager = updateEntity(new Manager("Oleg Pigolenko"));

    public void bootstrap() {
        manager = new Manager("Oleg Pigolenko");

        updateEntity(new CategoryEntity("Огнеупоры"));
        updateEntity(new CategoryEntity("Тара"));

        updateEntity(new UnitEntity("тн"));
        updateEntity(new UnitEntity("шт"));
        updateEntity(new UnitEntity("кг"));

        updateEntity(new DepartmentEntity("рмц№14"));
        updateEntity(new DepartmentEntity("склад №1"));
        updateEntity(new DepartmentEntity("склад №2"));
        updateEntity(new DepartmentEntity("цех №7"));
        updateEntity(new DepartmentEntity("цех №3"));

        manager = updateEntity(manager);
    }

    public <T> List<T> findOffset(int from, int pageSize, Class clazz) {
        CriteriaQuery<T> cQuery = builder.createQuery(clazz);
        Root<T> start = cQuery.from(clazz);
        CriteriaQuery<T> select = cQuery.select(start);

        TypedQuery typedQuery = em.createQuery(select);
        typedQuery.setFirstResult(from);
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

    public List<ReportTable> getReports(int from, int length) {
        List<ReportTable> reportTableList = new ArrayList<ReportTable>();
        try {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.DAY_OF_MONTH, 1);
            Date currentMonthStartDate = c.getTime();

            List<MTREntity> mtrs = findOffset(from, length, MTREntity.class);
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

                    if (incomeSum != null) residueForToday = incomeSum.subtract(expenseSum);
                    else residueForToday = null;
                    fullPrice = incomePriceSum(incomesMap.get(d));

                    BigDecimal incomeSumP = incomeAmountSum(incomesMapInMonth.get(d));
                    BigDecimal expenseSumP = expenseAmountSum(expenseMapInMonth.get(d));
                    BigDecimal requestSumP = requestAmountSum(requestMapInMonth.get(d));

                    if (incomeSumP != null) residueForPeriod = incomeSumP.subtract(expenseSumP);
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
        } catch (NoResultException e) {
            e.printStackTrace();
        }

        return reportTableList;
    }


    public <T> T getEntity(Class<T> clazz, Long id) {
        return em.find(clazz, id);
    }


    public CategoryEntity categoryIfNotExist(String name){
        CategoryEntity r = getByName(CategoryEntity.class, name);
        if(r == null){
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
        try{
            tquery.getSingleResult();
        } catch(NoResultException e){
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

}
