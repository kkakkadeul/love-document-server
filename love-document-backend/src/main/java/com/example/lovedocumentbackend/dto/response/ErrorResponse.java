package com.example.lovedocumentbackend.dto.response;

import com.example.lovedocumentbackend.enumclass.ErrorCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ErrorResponse {

    @Schema(description = "에러 시간", example = "2023-03-20T21:05:18.643457")
    private LocalDateTime timestamp = LocalDateTime.now();

    @Schema(description = "에러 메세지", example = "입력 오류")
    private String message;

    @Schema(description = "에러 코드", example = "보니")
    private String code;

    @Schema(description = "에러 코드 번호", example = "400")
    private int status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("errors")
    private List<CustomFieldError> customFieldErrors;


    public ErrorResponse() {
    }

    static public ErrorResponse create() {
        return new ErrorResponse();
    }

    public ErrorResponse code(String code) {
        this.code = code;
        return this;
    }

    public ErrorResponse status(int status) {
        this.status = status;
        return this;
    }

    public ErrorResponse message(String message) {
        this.message = message;
        return this;
    }

    public ErrorResponse errors(Errors errors) {
        setCustomFieldErrors(errors.getFieldErrors());
        return this;
    }

    public void setCustomFieldErrors(List<FieldError> fieldErrors) {

        customFieldErrors = new ArrayList<>();

        fieldErrors.forEach(error -> {
            customFieldErrors.add(new CustomFieldError(
                    error.getCodes()[0],
                    error.getRejectedValue(),
                    error.getDefaultMessage()
            ));
        });
    }
    public static class CustomFieldError {

        @Schema(description = "상세내용 필드", example = "Pattern.userApiRequest.password")
        private String field;

        @Schema(description = "상세내용 값", example = "123")
        private Object value;

        @Schema(description = "상세내용 이유", example = "4자 ~ 20자의 비밀번호여야 합니다.")
        private String reason;

        public CustomFieldError(String field, Object value, String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }

        public String getField() {
            return field;
        }

        public Object getValue() {
            return value;
        }

        public String getReason() {
            return reason;
        }
    }
}
