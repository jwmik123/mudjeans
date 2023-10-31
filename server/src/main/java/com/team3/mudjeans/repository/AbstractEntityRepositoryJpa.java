package com.team3.mudjeans.repository;

import com.team3.mudjeans.entity.Identifiable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class AbstractEntityRepositoryJpa <E extends Identifiable> implements EntityRepository<E> {

  @PersistenceContext
  protected EntityManager em;

  private final Class<E> entityClass;

  public AbstractEntityRepositoryJpa(Class<E> entityClass) {
    this.entityClass = entityClass;
    System.out.println("Created " + this.getClass().getName() + "<" + this.entityClass.getSimpleName() + ">");
  }

  @Override
  public List<E> findAll() {
    CriteriaBuilder builder = em.getCriteriaBuilder();
    CriteriaQuery<E> cq = builder.createQuery(entityClass);
    Root<E> root = cq.from(entityClass);
    cq.select(root);
    return em.createQuery(cq).getResultList();
  }

  @Override
  public E findById(long id) {
    return em.find(entityClass, id);
  }

  @Override
  public E save(E entity) {
    return em.merge(entity);
  }

  @Override
  public boolean deleteById(long id) {
    try {
      E a = em.merge(em.find(entityClass, id));
      em.remove(a);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  @Override
  public List<E> findByQuery(String query, Object ... args) {
    TypedQuery<E> tq = em.createNamedQuery(query, entityClass);
    for (int i = 1; i <= args.length; i++) {
      tq.setParameter(i, args[i-1]);
    }
    return tq.getResultList();
  }
}
