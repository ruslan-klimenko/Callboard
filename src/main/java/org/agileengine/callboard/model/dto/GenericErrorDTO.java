package org.agileengine.callboard.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GenericErrorDTO<T> extends GenericCommonDTO<T>{

    public static final String ERROR_RESULT_STATUS = "ERROR";

    @JsonProperty(MESSAGE_FIELD)
    protected String message;

    public GenericErrorDTO(String message) {
        result = ERROR_RESULT_STATUS;
        this.message = message;
    }
}
