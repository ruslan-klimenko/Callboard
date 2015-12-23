package org.agileengine.callboard.model.persistence;

public class Post extends AbstractModel{
    public static final String TABLE_NAME = "post";
    public static final String TABLE_ID = "id_post";
    public static final String FIELD_ID = "postId";
    public static final String COLUMN_TOPIC = "topic";
    public static final String COLUMN_MESSAGE = "message";

    public static final int MAX_TOPIC_SIZE = 40;
    public static final int MAX_MESSAGE_SIZE = 500;

    private Integer postId;
    private String topic;
    private String message;

    private Post(Integer postId, String topic, String message) {
        this.postId = postId;
        this.topic = topic;
        this.message = message;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class PostBuilder {
        private Integer id;
        private String topic;
        private String message;

        public PostBuilder setId(Integer id) {
            this.id = id;
            return this;
        }

        public PostBuilder setTopic(String topic) {
            this.topic = topic;
            return this;
        }

        public PostBuilder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Post build() {
            return new Post(id,topic,message);
        }
    }
}
