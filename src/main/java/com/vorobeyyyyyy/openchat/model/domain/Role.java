package com.vorobeyyyyyy.openchat.model.domain;

import java.util.List;

import javax.persistence.*;

import com.vorobeyyyyyy.openchat.model.base.UuidEntity;
import com.vorobeyyyyyy.openchat.model.enumerated.Authority;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class Role extends UuidEntity {

	private String name;

	@ElementCollection
	@CollectionTable(name = "role_authorities")
	@Enumerated(EnumType.STRING)
	@Column(name = "authority")
	private List<Authority> authorities;
}
