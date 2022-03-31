package com.vorobeyyyyyy.openchat.model.dto;

import lombok.Data;

@Data
public class LoginRequestDto {

	private String phone;

	private String code;
}
