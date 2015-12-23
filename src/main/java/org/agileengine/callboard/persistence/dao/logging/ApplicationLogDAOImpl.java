package org.agileengine.callboard.persistence.dao.logging;

import org.agileengine.callboard.model.persistence.logging.ApplicationLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.Map;

@Repository
public class ApplicationLogDAOImpl {

    public static final String QUERY_INSERT_APPLICATION_LOG = "INSERT INTO "+ ApplicationLog.TABLE_NAME +
                                                                "("+ApplicationLog.COLUMN_IS_COMPLETED+", " +
                                                                    ApplicationLog.COLUMN_OCCURRENCE+", " +
                                                                    ApplicationLog.COLUMN_RESULT+") " +
                                                                "VALUES(:"+ApplicationLog.COLUMN_IS_COMPLETED+", :" +
                                                                            ApplicationLog.COLUMN_OCCURRENCE + ", :" +
                                                                            ApplicationLog.COLUMN_RESULT+")";


    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public ApplicationLogDAOImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(ApplicationLog applicationLog) {
        jdbcTemplate.update(QUERY_INSERT_APPLICATION_LOG, createParamsMap(applicationLog));
    }

    private Map<String, Object> createParamsMap(ApplicationLog applicationLog) {
        Map<String, Object> params = new HashMap<>();
        if(applicationLog != null) {
            params.put(ApplicationLog.TABLE_ID, applicationLog.getApplicationLogId());
            params.put(ApplicationLog.COLUMN_RESULT, applicationLog.getResult());
            params.put(ApplicationLog.COLUMN_OCCURRENCE, applicationLog.getOccurrence());
            params.put(ApplicationLog.COLUMN_IS_COMPLETED, applicationLog.getIsCompleted());
        }
        return params;
    }

}
