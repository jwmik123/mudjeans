package com.team3.mudjeans.repository;

import com.team3.mudjeans.entity.Jean;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class JPAJeanRepository extends AbstractEntityRepositoryJpa<Jean> {
    public JPAJeanRepository() {
        super(Jean.class);
    }

}
