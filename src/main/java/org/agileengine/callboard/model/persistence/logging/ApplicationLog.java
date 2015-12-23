package org.agileengine.callboard.model.persistence.logging;

import org.agileengine.callboard.model.persistence.AbstractModel;

public class ApplicationLog extends AbstractModel {
    public static final String TABLE_NAME = "application_log";
    public static final String TABLE_ID = "id_application_log";
    public static final String COLUMN_IS_COMPLETED = "is_completed";
    public static final String COLUMN_OCCURRENCE = "occurrence";
    public static final String COLUMN_RESULT = "result";

    private Integer applicationLogId;
    private boolean isCompleted;
    private String occurrence;
    private String result;

    private ApplicationLog(Integer applicationLogId, boolean isCompleted,
                           String occurrence, String result) {
        this.applicationLogId = applicationLogId;
        this.isCompleted = isCompleted;
        this.occurrence = occurrence;
        this.result = result;
    }

    public Integer getApplicationLogId() {
        return applicationLogId;
    }

    public void setApplicationLogId(Integer applicationLogId) {
        this.applicationLogId = applicationLogId;
    }

    public boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public String getOccurrence() {
        return occurrence;
    }

    public void setOccurrence(String occurrence) {
        this.occurrence = occurrence;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public static class ApplicationLogBuilder {
        private Integer applicationLogId;
        private boolean isCompleted;
        private String occurrence;
        private String result;

        public ApplicationLogBuilder setApplicationLogId(Integer applicationLogId) {
            this.applicationLogId = applicationLogId;
            return this;
        }

        public ApplicationLogBuilder setCompleted(boolean isCompleted) {
            this.isCompleted = isCompleted;
            return this;
        }

        public ApplicationLogBuilder setOccurrence(String occurrence) {
            this.occurrence = occurrence;
            return this;
        }

        public ApplicationLogBuilder setResult(String result) {
            this.result = result;
            return this;
        }

        public ApplicationLog build() {
            return new ApplicationLog(applicationLogId,
                                      isCompleted,
                                      occurrence,
                                      result);
        }
    }
}
