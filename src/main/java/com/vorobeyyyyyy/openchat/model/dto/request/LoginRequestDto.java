package com.vorobeyyyyyy.openchat.model.dto.request;

import lombok.Data;

@Data
public class LoginRequestDto {

	private String phone;

	private String code;
}
