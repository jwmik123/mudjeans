package com.team3.mudjeans.repository;

import com.team3.mudjeans.entity.JeanOrder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class JPAOrderRepository extends AbstractEntityRepositoryJpa<JeanOrder> {

    @PersistenceContext
    private EntityManager em;

    public JPAOrderRepository() {
        super(JeanOrder.class);
    }

//    @Override
//    public JeanOrder save(JeanOrder jeanOrder) {
//        return em.merge(jeanOrder);
//    }
//
//    @Override
//    public void delete(JeanOrder jeanOrder) {
//        JeanOrder toRemove = em.merge(jeanOrder);
//        em.remove(toRemove);
//    }
//
//    @Override
//    public JeanOrder findById(long id) {
//        return em.find(JeanOrder.class, id);
//    }
//
//    @Override
//    public List<JeanOrder> findAll() {
//        TypedQuery<JeanOrder> namedQuery = em.createNamedQuery("find_all_orders", JeanOrder.class);
//
//        return namedQuery.getResultList();
//    }
}
