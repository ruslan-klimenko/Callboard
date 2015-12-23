package org.agileengine.callboard.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GenericCommonDTO<T> {

    public static final String RESULT_FIELD = "Result";
    public static final String DEFAULT_RESULT_STATUS = "OK";
    public static final String MESSAGE_FIELD = "Message";

    @JsonProperty(RESULT_FIELD)
    protected String result;

    public GenericCommonDTO() {
        result = DEFAULT_RESULT_STATUS;
    }
    public String getResult() {
        return result;
    }
}