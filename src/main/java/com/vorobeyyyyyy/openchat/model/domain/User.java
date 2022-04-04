package com.vorobeyyyyyy.openchat.model.domain;

import javax.persistence.*;

import com.vorobeyyyyyy.openchat.model.base.UuidEntity;

import com.vorobeyyyyyy.openchat.model.enumerated.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class User extends UuidEntity {

	@Column(nullable = false, unique = true)
	private String username;

	@Column(nullable = false, unique = true)
	private String mobilePhone;

	@ElementCollection
	@CollectionTable(name = "user_roles")
	@Enumerated(EnumType.STRING)
	@Column(name = "role")
	private List<Role> roles;

	@ManyToMany
	@JoinTable(
			name = "chat_users",
			joinColumns = @JoinColumn(name = "user_uuid"),
			inverseJoinColumns = @JoinColumn(name = "chat_uuid")
	)
	private List<Chat> chats;

	@Column(nullable = false)
	private boolean confirmed = false;
}
