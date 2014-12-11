package com.kiev.msupport.domain;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

public class MaterialsMngrBean {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("materials");
    EntityManager em = emf.createEntityManager();

    public void bootstrap(){
        for(int i=0; i<40; i++) {
            EntityTransaction et = em.getTransaction();
            et.begin();
            MaterialEntity entity = new MaterialEntity("Че-то"+i, 10l, 20l, new Date().toString(), "Никель", "Нужно закупать на 10 больше");
            em.persist(entity);
            et.commit();
        }
    }

    public List<MaterialEntity> findOffset(int from, int pageSize) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<MaterialEntity> cQuery = builder.createQuery(MaterialEntity.class);
        Root<MaterialEntity> start = cQuery.from(MaterialEntity.class);
        CriteriaQuery<MaterialEntity> select = cQuery.select(start);

        TypedQuery typedQuery = em.createQuery(select);
        typedQuery.setFirstResult(from);
        typedQuery.setMaxResults(pageSize);
        return typedQuery.getResultList();
    }
}
