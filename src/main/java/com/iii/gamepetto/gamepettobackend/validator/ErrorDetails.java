package com.iii.gamepetto.gamepettobackend.validator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ErrorDetails {
    private final List<FieldError> fieldErrors = new ArrayList<>();
    private Date timestamp;
    private String message;

    public ErrorDetails(Date timestamp, String message) {
        this.timestamp = timestamp;
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<FieldError> getFieldErrors() {
        return fieldErrors;
    }

    public void addFieldError(String field, Object rejectedValue, String message) {
        FieldError error = new FieldError(field, rejectedValue, message);
        fieldErrors.add(error);
    }

    static class FieldError {

        private String field;
        private String message;
        private Object rejectedValue;

        public FieldError(String field, Object rejectedValue, String message) {
            this.field = field;
            this.rejectedValue = rejectedValue;
            this.message = message;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public Object getRejectedValue() {
            return rejectedValue;
        }

        public void setRejectedValue(Object rejectedValue) {
            this.rejectedValue = rejectedValue;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
