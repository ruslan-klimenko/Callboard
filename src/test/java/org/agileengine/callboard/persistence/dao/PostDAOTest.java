package org.agileengine.callboard.persistence.dao;

import org.junit.Assert;
import org.agileengine.callboard.model.persistence.Post;
import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import java.util.List;

public class PostDAOTest extends AbstractDAOTest{
    public static final String DATA_SET = "Post.xml";
    public static final String FIRST_POST_TOPIC = "firstTopic";
    public static final String FIRST_POST_MESSAGE = "firstMessage";
    public static final String SECOND_POST_TOPIC = "secondTopic";
    public static final String SECOND_POST_MESSAGE = "secondMessage";
    public static final String TEST_POST_TOPIC = "testTopic";
    public static final String TEST_POST_MESSAGE = "testMessage";

    @Autowired
    private PostDAO postDAO;

    @Override
    protected IDataSet getDataSet() throws Exception {
        return new CompositeDataSet(new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream(DATA_SET)));
    }

    @Override
    @Test
    public void create() {
        Assert.assertEquals(2, postDAO.findAll().size());

        Post generatedPost = generatePost();

        try {
            postDAO.findByTopic(generatedPost.getTopic());
            Assert.fail("There shouldn't be post with such topic!");
        } catch(EmptyResultDataAccessException e) {}

        Post createdPost = postDAO.create(generatedPost);

        Assert.assertEquals(generatedPost.getTopic(),createdPost.getTopic());

        Assert.assertEquals(generatedPost.getMessage(),createdPost.getMessage());

        Assert.assertEquals(3, postDAO.findAll().size());
    }

    @Override
    @Test
    public void update() {
        String testTopic = TEST_POST_TOPIC;
        String testMessage = TEST_POST_MESSAGE;
        int postId = 1;

        Assert.assertEquals(2, postDAO.findAll().size());

        Post dbPost = postDAO.findById(postId);

        Assert.assertNotEquals(testTopic, dbPost.getTopic());
        Assert.assertNotEquals(testMessage, dbPost.getMessage());

        dbPost.setMessage(testMessage);
        dbPost.setTopic(testTopic);

        Post updatedDbPost = postDAO.update(dbPost);

        Assert.assertEquals(testTopic, updatedDbPost.getTopic());
        Assert.assertEquals(testMessage, updatedDbPost.getMessage());

        dbPost = postDAO.findById(postId);

        Assert.assertEquals(testTopic, dbPost.getTopic());
        Assert.assertEquals(testMessage, dbPost.getMessage());

        Assert.assertEquals(2, postDAO.findAll().size());
    }

    @Override
    @Test
    public void remove() {
        int postId = 1;

        Assert.assertEquals(2, postDAO.findAll().size());

        Post post = postDAO.findById(postId);

        Assert.assertNotNull(post);

        postDAO.remove(post);

        try {
            postDAO.findById(postId);
            Assert.fail("Post with such id should be deleted!");
        } catch(EmptyResultDataAccessException e) { }

        Assert.assertEquals(1, postDAO.findAll().size());
    }

    @Override
    @Test
    public void findById() {
        Integer id = 1;

        Post post = postDAO.findById(id);

        Assert.assertEquals(id, post.getPostId());

        Assert.assertEquals(FIRST_POST_TOPIC, post.getTopic());

        Assert.assertEquals(FIRST_POST_MESSAGE, post.getMessage());
    }

    @Override
    @Test
    public void findAll() {
        List<Post> posts = postDAO.findAll();
        Assert.assertEquals(2, posts.size());

        Assert.assertEquals(FIRST_POST_TOPIC, posts.get(0).getTopic());

        Assert.assertEquals(FIRST_POST_MESSAGE, posts.get(0).getMessage());

        Assert.assertEquals(SECOND_POST_TOPIC, posts.get(1).getTopic());

        Assert.assertEquals(SECOND_POST_MESSAGE, posts.get(1).getMessage());
    }

    @Override
    @Test
    public void findLast() {
        Post lastPost = postDAO.findLast();

        Assert.assertEquals(SECOND_POST_TOPIC, lastPost.getTopic());

        Assert.assertEquals(SECOND_POST_MESSAGE, lastPost.getMessage());
    }

    @Test
    public void testFindByTopic() {
        String topicWithInjection = "topic or 1 = 1";
        try {
            postDAO.findByTopicInjectable(topicWithInjection);
            Assert.fail();
        } catch(IncorrectResultSizeDataAccessException e) {
            Assert.assertEquals("Incorrect result size: expected 1, actual 2", e.getMessage());
        }

        try {
            postDAO.findByTopic(topicWithInjection);
            Assert.fail();
        } catch(EmptyResultDataAccessException e) {
            Assert.assertEquals("Incorrect result size: expected 1, actual 0", e.getMessage());
        }

        Post post = postDAO.findByTopic(FIRST_POST_TOPIC);
        Assert.assertEquals(FIRST_POST_TOPIC, post.getTopic());
    }

    public Post generatePost() {
        return new Post.PostBuilder().setTopic(TEST_POST_TOPIC).setMessage(TEST_POST_MESSAGE).build();
    }
}
