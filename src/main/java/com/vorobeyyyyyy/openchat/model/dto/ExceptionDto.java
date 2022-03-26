package com.vorobeyyyyyy.openchat.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExceptionDto {
	private int code;
	private String message;
}
