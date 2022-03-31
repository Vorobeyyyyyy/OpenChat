package com.vorobeyyyyyy.openchat.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SendCodeRequest {
    @NotNull(message = "Phone must be not null")
    @NotEmpty(message = "Phone must be not empty")
    private String phone;
}
