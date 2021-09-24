package com.rahand.idea.channel.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rahand.idea.channel.enums.Bank;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private final int code;
    private final String message;
    private Bank bank;
    private String stackTrace;
    private List<ValidationError> errors;

    public ErrorResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ErrorResponse(int code, String message, Bank bank) {
        this.code = code;
        this.message = message;
        this.bank = bank;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Bank getBankName() {
        return bank;
    }

    public void setBankName(Bank bank) {
        this.bank = bank;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public List<ValidationError> getErrors() {
        return errors;
    }

    public void setErrors(List<ValidationError> errors) {
        this.errors = errors;
    }

    private static class ValidationError {
        private final String field;
        private final String message;

        public ValidationError(String field, String message) {
            this.field = field;
            this.message = message;
        }

        public String getField() {
            return field;
        }

        public String getMessage() {
            return message;
        }
    }

    public void addValidationError(String field, String message) {
        if (Objects.isNull(errors)) {
            errors = new ArrayList<>();
        }
        errors.add(new ValidationError(field, message));
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", bankName=" + bank +
                ", stackTrace='" + stackTrace + '\'' +
                ", errors=" + errors +
                '}';
    }
}