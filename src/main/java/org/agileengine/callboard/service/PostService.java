package org.agileengine.callboard.service;

import org.agileengine.callboard.controller.RequestUtil;
import org.agileengine.callboard.application.logging.Loggable;
import org.agileengine.callboard.model.persistence.Post;
import org.agileengine.callboard.persistence.dao.PostDAO;
import org.agileengine.callboard.service.exception.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class PostService {

    @Autowired
    private PostDAO postDAO;

    public List<Post> findAll() {
        return postDAO.findAll();
    }

    @Loggable
    public Post create(String paramString) {
        Post post = createPostFromParamString(paramString);
        validateForCreate(post);
        return postDAO.create(post);
    }

    @Loggable
    public Post update(String paramString) {
        Post post = createPostFromParamString(paramString);
        validateForUpdate(post);
        return postDAO.update(post);
    }

    @Loggable
    public Post remove(String paramString) {
        Post post = createPostFromParamString(paramString);
        validateForRemove(post);
        return postDAO.remove(post);
    }

    private void validateForCreate(Post post) {
        if(post.getMessage() == null || post.getTopic() == null)
            throw new ApplicationException("Topic and message couldn't be empty!");
        validateSize(post);
    }

    private void validateForUpdate(Post post) {
        if(post.getPostId() == null ||
                post.getMessage() == null ||
                post.getTopic() == null)
            throw new ApplicationException("Id, topic and message couldn't be empty!");
        validateSize(post);
    }

    private void validateForRemove(Post post) {
        if(post.getPostId() == null)
            throw new ApplicationException("Id couldn't be null!");
    }

    private void validateSize(Post post) {
        if(post.getMessage().length() > Post.MAX_MESSAGE_SIZE)
            throw new ApplicationException("Message length should be not greater than " + Post.MAX_MESSAGE_SIZE);
        if(post.getTopic().length() > Post.MAX_TOPIC_SIZE)
            throw new ApplicationException("Topic length should be not greater than " + Post.MAX_TOPIC_SIZE);
    }

    private static Post createPostFromParamString(String paramString) {
        Map<String, String> params = RequestUtil.getParamMap(paramString);
        Post.PostBuilder builder =  new Post.PostBuilder();
        String id = params.get(Post.FIELD_ID);
        String topic = params.get(Post.COLUMN_TOPIC);
        String message = params.get(Post.COLUMN_MESSAGE);
        if(id != null) {
            builder.setId(Integer.parseInt(id));
        }
        builder.setTopic(topic);
        builder.setMessage(message);
        return builder.build();
    }

    public void setPostDAO(PostDAO postDAO) {
        this.postDAO = postDAO;
    }
}
