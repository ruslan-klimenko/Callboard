package org.agileengine.callboard.controller.webservice;

import org.agileengine.callboard.application.Path;
import org.agileengine.callboard.model.dto.GenericCommonDTO;
import org.agileengine.callboard.model.dto.GenericCreateDTO;
import org.agileengine.callboard.model.dto.GenericErrorDTO;
import org.agileengine.callboard.model.dto.GenericGetDTO;
import org.agileengine.callboard.model.persistence.Post;
import org.agileengine.callboard.service.PostService;
import org.agileengine.callboard.service.exception.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(CallboardWebService.SERVICE_PATH)
public class CallboardWebService {

    public static final String SERVICE_PATH = "service" + Path.SLASH + "callboard";

    @Autowired
    private PostService postService;

    @RequestMapping(path = Path.SLASH + "get", method = RequestMethod.POST)
    public ResponseEntity<GenericGetDTO<Post>> get() {
        GenericGetDTO<Post> result = new GenericGetDTO<Post>(postService.findAll());
        return new ResponseEntity<GenericGetDTO<Post>>(result, HttpStatus.OK);
    }

    @RequestMapping(path = Path.SLASH + "create", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity create(@RequestBody String paramString) {
        try {
            Post post = postService.create(paramString);
            return new ResponseEntity<GenericCreateDTO<Post>>(new GenericCreateDTO<Post>(post), HttpStatus.OK);
        } catch(ApplicationException e) {
            return new ResponseEntity<GenericErrorDTO<Post>>(new GenericErrorDTO<Post>(e.getMessage()), HttpStatus.OK);
        }
    }

    @RequestMapping(path = Path.SLASH + "update", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity update(@RequestBody String paramString) {
        try {
            postService.update(paramString);
            return new ResponseEntity<GenericCommonDTO<Post>>(new GenericCommonDTO<Post>(), HttpStatus.OK);
        } catch(ApplicationException e) {
            return new ResponseEntity<GenericErrorDTO<Post>>(new GenericErrorDTO<Post>(e.getMessage()), HttpStatus.OK);
        }
    }

    @RequestMapping(path = Path.SLASH + "remove", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity remove(@RequestBody String paramString) {
        try {
            postService.remove(paramString);
            return new ResponseEntity<GenericCommonDTO<Post>>(new GenericCommonDTO<Post>(), HttpStatus.OK);
        } catch(ApplicationException e) {
            return new ResponseEntity<GenericErrorDTO<Post>>(new GenericErrorDTO<Post>(e.getMessage()), HttpStatus.OK);
        }
    }
}