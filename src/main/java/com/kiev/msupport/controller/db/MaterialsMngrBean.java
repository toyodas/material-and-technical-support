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

    public Manager manager;

    public void bootstrap(){
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

    public <T> T getById(Class clazz, Long id){
        return (T)em.find(clazz, id);
    }

    public MTREntity getMTR(Long categoryId, Long unitsId, String name){
        Query q = em.createQuery("select e from MTREntity e where cat_id=:cId and " +
                "unit_id=:uId and name = :name", MTREntity.class);
        q.setParameter("cId", categoryId);
        q.setParameter("name", name);
        q.setParameter("uId", unitsId);
        try {
            return (MTREntity)q.getSingleResult();
        } catch (NoResultException e){
            return null;
        }
    }


    public <T> T getEntity(Class<T> clazz, Long id){
        return em.find(clazz, id);
    }

    public <T> T updateEntity(T e){
        EntityTransaction et = em.getTransaction();
        et.begin();
        T upd = em.merge(e);
        et.commit();
        return upd;
    }

}
