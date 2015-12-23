package org.agileengine.callboard.service;

import org.agileengine.callboard.service.exception.ApplicationException;
import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.agileengine.callboard.application.configuration.TestConfig;
import org.agileengine.callboard.model.persistence.Post;
import org.agileengine.callboard.persistence.dao.PostDAO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class PostServiceTest {

    @Autowired
    private PostService postService;

    private PostDAO postDAO;

    @Before
    public void setUp() {
        postDAO = mock(PostDAO.class);
    }

    @Test
    public void testFindAll() {
        when(postDAO.findAll()).thenReturn(generatePosts());
        postService.setPostDAO(postDAO);
        Assert.assertEquals(generatePosts(), postService.findAll());
    }

    @Test
    public void testCreate() {
        try {
            postService.create(null);
            Assert.fail();
        } catch(ApplicationException e) {
            Assert.assertEquals("Topic and message couldn't be empty!", e.getMessage());
        }
        try {
            postService.create("topic=testTopic");
            Assert.fail();
        } catch(ApplicationException e) {
            Assert.assertEquals("Topic and message couldn't be empty!", e.getMessage());
        }
        try {
            postService.create("message=testMessage");
        } catch(ApplicationException e) {
            Assert.assertEquals("Topic and message couldn't be empty!", e.getMessage());
        }

        try {
            postService.create("topic=" +
                    StringUtils.leftPad("", Post.MAX_TOPIC_SIZE + 1,"c") +
                    "&message=testMessage");
            Assert.fail();
        } catch(ApplicationException e) {
            Assert.assertEquals("Topic length should be not greater than " + Post.MAX_TOPIC_SIZE,
                    e.getMessage());
        }

        try {
            postService.create("topic=xx&message=" +
                    StringUtils.leftPad("", Post.MAX_MESSAGE_SIZE + 1,"c"));
            Assert.fail();
        } catch(ApplicationException e) {
            Assert.assertEquals("Message length should be not greater than " + Post.MAX_MESSAGE_SIZE,
                    e.getMessage());
        }

        Post expectedPost = postDAO.create(generatePosts().get(0));

        when(postDAO.create(generatePosts().get(0)))
                .thenReturn(expectedPost);

        postService.setPostDAO(postDAO);

        Assert.assertEquals(expectedPost,
                postService.create("topic=" +
                        StringUtils.leftPad("", Post.MAX_TOPIC_SIZE, "c") +
                        "&message=testMessage"));

        Assert.assertEquals(expectedPost,
                postService.create("topic=xx&message=" +
                        StringUtils.leftPad("", Post.MAX_MESSAGE_SIZE, "c")));
    }

    @Test
    public void testUpdate() {
        try {
            postService.update(null);
            Assert.fail();
        } catch(ApplicationException e) {
            Assert.assertEquals("Id, topic and message couldn't be empty!", e.getMessage());
        }
        try {
            postService.update("topic=testTopic");
            Assert.fail();
        } catch(ApplicationException e) {
            Assert.assertEquals("Id, topic and message couldn't be empty!", e.getMessage());
        }
        try {
            postService.update("message=testMessage");
        } catch(ApplicationException e) {
            Assert.assertEquals("Id, topic and message couldn't be empty!", e.getMessage());
        }

        try {
            postService.update(Post.FIELD_ID + "=1&topic=" +
                    StringUtils.leftPad("", Post.MAX_TOPIC_SIZE + 1, "c") +
                    "&message=testMessage");
            Assert.fail();
        } catch(ApplicationException e) {
            Assert.assertEquals("Topic length should be not greater than " + Post.MAX_TOPIC_SIZE,
                    e.getMessage());
        }

        try {
            postService.update(Post.FIELD_ID + "=1&topic=xx&message=" +
                    StringUtils.leftPad("", Post.MAX_MESSAGE_SIZE + 1, "c"));
            Assert.fail();
        } catch(ApplicationException e) {
            Assert.assertEquals("Message length should be not greater than " + Post.MAX_MESSAGE_SIZE,
                    e.getMessage());
        }

        Post expectedPost = postDAO.update(generatePosts().get(0));

        when(postDAO.update(generatePosts().get(0)))
                .thenReturn(expectedPost);

        postService.setPostDAO(postDAO);

        Assert.assertEquals(expectedPost,
                postService.update(Post.FIELD_ID + "=1&topic=" +
                        StringUtils.leftPad("", Post.MAX_TOPIC_SIZE, "c") +
                        "&message=testMessage"));

        Assert.assertEquals(expectedPost,
                postService.update(Post.FIELD_ID + "=1&topic=xx&message=" +
                        StringUtils.leftPad("", Post.MAX_MESSAGE_SIZE, "c")));
    }

    @Test
    public void testRemove() {

        try {
            postService.remove(null);
            Assert.fail();
        } catch(ApplicationException e) {
            Assert.assertEquals("Id couldn't be null!", e.getMessage());
        }

        try {
            postService.remove("id=");
            Assert.fail();
        } catch(ApplicationException e) {
            Assert.assertEquals("Id couldn't be null!", e.getMessage());
        }

        Post expectedPost = postDAO.remove(generatePosts().get(0));

        when(postDAO.remove(generatePosts().get(0)))
                .thenReturn(expectedPost);

        postService.setPostDAO(postDAO);

        Assert.assertEquals(expectedPost,
                postService.remove(Post.FIELD_ID + "=1"));

    }

    private List<Post> generatePosts() {
        List<Post> posts = new ArrayList<Post>();
        posts.add(new Post.PostBuilder()
                .setId(1)
                .setMessage("firstMessage")
                .setTopic("firstTopic").build());
        posts.add(new Post.PostBuilder()
                .setId(2)
                .setMessage("secondMessage")
                .setTopic("secondTopic").build());
        return posts;
    }
}
