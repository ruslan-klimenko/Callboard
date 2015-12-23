package org.agileengine.callboard.persistence.dao;

import org.agileengine.callboard.model.persistence.Post;

public interface PostDAO extends GenericDAO<Post, Integer> {
    Post findByTopic(String topic);

    /**
     * FOR TESTING ONLY!
     * @param topic
     * @return
     */
    Post findByTopicInjectable(String topic);

}
