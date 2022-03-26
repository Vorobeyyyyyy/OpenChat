package com.vorobeyyyyyy.openchat.model.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.vorobeyyyyyy.openchat.model.base.UuidEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User extends UuidEntity {

	private String username;

	private String mobilePhone;

	private String password;
}
