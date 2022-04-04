package com.vorobeyyyyyy.openchat.web;

import com.vorobeyyyyyy.openchat.exception.BaseException;
import com.vorobeyyyyyy.openchat.model.dto.response.ExceptionDto;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ExceptionDto> handleBaseException(BaseException e) {
        return ResponseEntity
                .status(HttpStatus.valueOf(e.getCode() / 1000))
                .body(ExceptionDto.builder()
                        .code(e.getCode())
                        .message(e.getMessage())
                        .messageKey(e.getMessageKey())
                        .build());
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleExceptionInternal(
            @NonNull Exception ex,
            @Nullable Object body,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatus status,
            @NonNull WebRequest request
    ) {
        ExceptionDto dto = ExceptionDto.builder()
                .code(status.value())
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(status).body(dto);
    }
}
