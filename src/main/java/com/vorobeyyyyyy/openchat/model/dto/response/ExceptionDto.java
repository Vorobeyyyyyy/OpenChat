package com.vorobeyyyyyy.openchat.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExceptionDto {

    private int code;

    private String message;

    private String messageKey;

    private Object additionalInfo;
}
