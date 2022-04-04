package com.vorobeyyyyyy.openchat.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.EntityPath;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface BaseCustomRepository {

    JPAQueryFactory select();

    default <T> JPAQuery<T> selectFrom(EntityPath<T> from) {
        return select().selectFrom(from);
    }

    default <T> JPAQuery<T> selectFrom(EntityPath<T> from, Pageable pageable) {
        return select()
                .selectFrom(from)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());
    }

    @SuppressWarnings("deprecation")
    default <T> PageImpl<T> toPage(JPAQuery<T> query) {
        QueryResults<T> queryResults = query.fetchResults();
        return new PageImpl<T>(
                queryResults.getResults(),
                PageRequest.of((int) (queryResults.getOffset() / queryResults.getLimit()), (int) queryResults.getLimit()),
                queryResults.getTotal()
        );
    }
}
