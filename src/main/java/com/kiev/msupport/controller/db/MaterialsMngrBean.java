package com.kiev.msupport.controller.db;

import com.kiev.msupport.domain.*;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class MaterialsMngrBean {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("materials");
    EntityManager em = emf.createEntityManager();
    CriteriaBuilder builder = em.getCriteriaBuilder();


    public void bootstrap(){
        CategoryEntity categoryEntity = new CategoryEntity("Огнеупоры");
        UnitEntity unitEntity = new UnitEntity("тонны");
        DepartmentEntity depEntity = new DepartmentEntity("пост1");
        Manager manager = new Manager("Oleg Pigolenko");

        categoryEntity = updateEntity(categoryEntity);
        unitEntity = updateEntity(unitEntity);
        depEntity = updateEntity(depEntity);
        manager = updateEntity(manager);

        MTREntity mtr = new MTREntity(categoryEntity, "кирпич", unitEntity);
        mtr = updateEntity(mtr);

        ExpenseEntity expense = new ExpenseEntity(mtr, "0.24", depEntity, Long.toString(System.currentTimeMillis()), manager);
        IncomeEntity  incomeEntity = new IncomeEntity(mtr, "2.4", "22.5", depEntity, Long.toString(System.currentTimeMillis()), manager);
        RequestEntity request = new RequestEntity(mtr, "2.0", depEntity, Long.toString(System.currentTimeMillis()), manager);

        updateEntity(expense);
        updateEntity(incomeEntity);
        updateEntity(request);

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

    public <T> T updateEntity(T e){
        EntityTransaction et = em.getTransaction();
        et.begin();
        T upd = em.merge(e);
        et.commit();
        return upd;
    }
}
