package org.agileengine.callboard.persistence.dao;

import org.agileengine.callboard.model.persistence.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PostDAOImpl implements PostDAO {

    public static final String QUERY_INSERT_POST = "INSERT INTO "+ Post.TABLE_NAME+
                                                    "("+ Post.COLUMN_TOPIC+", "+ Post.COLUMN_MESSAGE+") " +
                                                    "VALUES(:" + Post.COLUMN_TOPIC + ",:" + Post.COLUMN_MESSAGE + ")";

    public static final String QUERY_UPDATE_POST = "UPDATE "+ Post.TABLE_NAME+
                                                    " SET "+ Post.COLUMN_TOPIC+" = :"+ Post.COLUMN_TOPIC+"," +
                                                            Post.COLUMN_MESSAGE +" = :"+ Post.COLUMN_MESSAGE +
                                                    " WHERE " + Post.TABLE_ID + " = :"+ Post.TABLE_ID;

    public static final String QUERY_DELETE_POST = "DELETE FROM " + Post.TABLE_NAME +
                                                    " WHERE " + Post.TABLE_ID + " = :" + Post.TABLE_ID;

    public static final String QUERY_ALL_POST = "SELECT * FROM " + Post.TABLE_NAME;

    public static final String QUERY_BY_ID_POST = "SELECT * FROM " + Post.TABLE_NAME +
                                                    " WHERE " + Post.TABLE_ID + " = :" + Post.TABLE_ID;

    public static final String QUERY_BY_TOPIC_POST = "SELECT * FROM " + Post.TABLE_NAME +
            " WHERE " + Post.COLUMN_TOPIC + " = :" + Post.COLUMN_TOPIC;

    public static final String QUERY_LAST_RECORD = "SELECT * FROM " + Post.TABLE_NAME +
                                                    " WHERE " + Post.TABLE_ID +
                                                     " = (SELECT max("+Post.TABLE_ID+") FROM " + Post.TABLE_NAME + ")";

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Post create(Post post) {
        jdbcTemplate.update(QUERY_INSERT_POST, createParamsMap(post));
        return findLast();
    }

    @Override
    public Post update(Post post) {
        jdbcTemplate.update(QUERY_UPDATE_POST, createParamsMap(post));
        return findById(post.getPostId());
    }

    @Override
    public Post remove(Post post) {
        jdbcTemplate.update(QUERY_DELETE_POST, createParamsMap(post));
        return post;
    }

    @Override
    public List<Post> findAll() {
        return jdbcTemplate.query(
                QUERY_ALL_POST,
                new PostRowMapper()
        );
    }

    @Override
    public Post findById(Integer id) {
        return jdbcTemplate.queryForObject(
                QUERY_BY_ID_POST,
                createParamsMap(new Post.PostBuilder().setId(id).build()),
                new PostRowMapper()
        );
    }

    @Override
    public Post findByTopic(String topic) {
        return jdbcTemplate.queryForObject(
                QUERY_BY_TOPIC_POST,
                createParamsMap(new Post.PostBuilder().setTopic(topic).build()),
                new PostRowMapper()
        );
    }

    @Override
    public Post findByTopicInjectable(String topic) {
        String query = "select * from post where "+Post.COLUMN_TOPIC+" = " + topic;
        return jdbcTemplate.queryForObject(
                query, createParamsMap(null),
                new PostRowMapper()
        );
    }

    @Override
    public Post findLast() {
        return jdbcTemplate.queryForObject(
                QUERY_LAST_RECORD,
                createParamsMap(null),
                new PostRowMapper()
        );
    }

    private Map<String, Object> createParamsMap(Post post) {
        Map<String, Object> params = new HashMap<>();
        if(post != null) {
            params.put(Post.TABLE_ID, post.getPostId());
            params.put(Post.COLUMN_TOPIC, post.getTopic());
            params.put(Post.COLUMN_MESSAGE, post.getMessage());
        }
        return params;
    }

    private static class PostRowMapper implements RowMapper<Post> {
        public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Post.PostBuilder()
                    .setId(rs.getInt(Post.TABLE_ID))
                    .setMessage(rs.getString(Post.COLUMN_MESSAGE))
                    .setTopic(rs.getString(Post.COLUMN_TOPIC))
                    .build();
        }
    }
}
