package com.team3.mudjeans.repository;

import com.team3.mudjeans.entity.Permission;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class JPAPermissionRepository extends AbstractEntityRepositoryJpa<Permission> {

    public JPAPermissionRepository() {
        super(Permission.class);
    }

//    @PersistenceContext
//    private EntityManager em;
//
//    @Override
//    public Permission save(Permission permission) {
//        return em.merge(permission);
//    }
//
//    @Override
//    public void delete(Permission permission) {
//        Permission toRemove = em.merge(permission);
//        em.remove(toRemove);
//    }
//
//    @Override
//    public Permission findById(long id) {
//        return em.find(Permission.class, id);
//    }
//
//    @Override
//    public List<Permission> findAll() {
//        TypedQuery<Permission> namedQuery = em.createNamedQuery("find_all_permissions", Permission.class);
//
//        return namedQuery.getResultList();
//    }
}
