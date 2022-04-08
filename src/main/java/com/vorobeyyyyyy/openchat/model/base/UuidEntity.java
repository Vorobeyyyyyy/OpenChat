package com.vorobeyyyyyy.openchat.model.base;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.*;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import com.vorobeyyyyyy.openchat.model.domain.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public abstract class UuidEntity {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(
		name = "UUID",
		strategy = "org.hibernate.id.UUIDGenerator"
	)
	@Column(nullable = false, updatable = false, unique = true)
	protected UUID uuid;

	@Column(nullable = false, updatable = false)
	@CreatedDate
	protected LocalDateTime createdDate;

	@Column(nullable = false)
	@LastModifiedDate
	protected LocalDateTime updatedDate;

	@Override
	public final boolean equals(Object o) {
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		UuidEntity that = (UuidEntity) o;

		return new EqualsBuilder().append(uuid, that.uuid).isEquals();
	}

	@Override
	public final int hashCode() {
		return new HashCodeBuilder(17, 37).append(uuid).toHashCode();
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "{" +
			"uuid=" + uuid
			+ "}";
	}

	@PrePersist
	protected void prePersist() {
	}

	@PreUpdate
	protected void preUpdate() {
	}

	public final boolean isNew() {
		return uuid == null;
	}
}
