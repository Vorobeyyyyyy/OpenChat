package com.vorobeyyyyyy.openchat.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.vorobeyyyyyy.openchat.repository.BaseCustomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
@AllArgsConstructor
public class BaseCustomRepositoryImpl implements BaseCustomRepository {

    private EntityManager entityManager;

    public JPAQueryFactory select() {
        return new JPAQueryFactory(entityManager);
    }
}
