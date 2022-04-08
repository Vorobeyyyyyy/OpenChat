package com.vorobeyyyyyy.openchat.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

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

    default <T> Optional<T> fetchOneWhere(EntityPath<T> from, Predicate... where) {
        return Optional.ofNullable(selectFrom(from).where(where).fetchOne());
    }

    default <T> Page<T> fetchPageWhere(EntityPath<T> from, Pageable pageable, Predicate... where) {
        return toPage(selectFrom(from, pageable).where(where));
    }

    @SuppressWarnings("deprecation")
    default <T> PageImpl<T> toPage(JPAQuery<T> query) {
        QueryResults<T> queryResults = query.fetchResults();
        return new PageImpl<>(
                queryResults.getResults(),
                PageRequest.of((int) (queryResults.getOffset() / queryResults.getLimit()), (int) queryResults.getLimit()),
                queryResults.getTotal()
        );
    }
}
