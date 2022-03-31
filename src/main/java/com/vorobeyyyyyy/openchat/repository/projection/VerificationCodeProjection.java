package com.vorobeyyyyyy.openchat.repository.projection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VerificationCodeProjection {

    private String code;

    private Long created;
}
