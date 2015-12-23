package org.agileengine.callboard.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class GenericGetDTO<T> extends GenericCommonDTO<T> {

    public static final String RECORDS_FIELD = "Records";

    @JsonProperty(RECORDS_FIELD)
    private List<T> records;

    public GenericGetDTO(List<T> records) {
        this.records = records;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }
}
