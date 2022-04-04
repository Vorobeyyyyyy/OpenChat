package com.vorobeyyyyyy.openchat.model.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.With;

@Data
@Builder
@With
public class TokenDto {

	private long expires;

	private long duration;

	private String token;

	private String refreshToken;

	private boolean newAccount;
}
