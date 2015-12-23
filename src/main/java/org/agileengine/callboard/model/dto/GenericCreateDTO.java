package org.agileengine.callboard.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GenericCreateDTO<T> extends GenericCommonDTO<T> {

    public static final String RECORD_FIELD = "Record";

    @JsonProperty(RECORD_FIELD)
    private T record;

    public GenericCreateDTO(T record) {
        this.record = record;
    }

    public T getRecord() {
        return record;
    }

    public void setRecord(T record) {
        this.record = record;
    }
}
