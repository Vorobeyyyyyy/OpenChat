package com.vorobeyyyyyy.openchat.repository;

import com.vorobeyyyyyy.openchat.model.domain.Chat;
import com.vorobeyyyyyy.openchat.model.domain.Chat_;
import com.vorobeyyyyyy.openchat.model.dto.ChatFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface ChatRepository extends JpaRepository<Chat, UUID>, JpaSpecificationExecutor<Chat> {

    Page<Chat> findAllBy(Specification<Chat> specification, Pageable pageable);

    default Page<Chat> findAll(ChatFilter filter, Pageable pageable) {
        Specification<Chat> specification = (root, query, criteriaBuilder) -> {
            if (filter.getName() != null) {
                query.where(criteriaBuilder.like(root.get(Chat_.NAME), filter.getName()));
            }
            return query.getRestriction();
        };
        return findAllBy(specification, pageable);
    }
}