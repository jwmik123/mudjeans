package com.team3.mudjeans.repository;


import com.team3.mudjeans.entity.Identifiable;

import java.util.List;

public interface EntityRepository<E extends Identifiable> {
  List<E> findAll();
  E findById(long id);
  E save(E entity);
  boolean deleteById(long id);
  List<E> findByQuery(String query, Object ... args);
}
