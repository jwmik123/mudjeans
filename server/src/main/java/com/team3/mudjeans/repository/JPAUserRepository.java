package com.team3.mudjeans.repository;

import com.team3.mudjeans.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class JPAUserRepository extends AbstractEntityRepositoryJpa<User> {

    public JPAUserRepository() {
        super(User.class);
    }



    //    @PersistenceContext
//    private EntityManager em;
//
//    @Override
//    public User save(User user) {
//        return em.merge(user);
//    }
//
//    @Override
//    public void delete(User user) {
//        User toRemove = em.merge(user);
//        em.remove(toRemove);
//    }
//
//    @Override
//    public User findById(long id) {
//        return em.find(User.class, id);
//    }
//
//    @Override
//    public List<User> findAll() {
//        TypedQuery<User> namedQuery = em.createNamedQuery("find_all_users", User.class);
//
//        return namedQuery.getResultList();
//    }
}
