package org.agileengine.callboard.controller;

import org.agileengine.callboard.application.Path;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping
public class AppController {
    @RequestMapping(value = Path.SLASH, method = RequestMethod.GET)
    public String getWelcomePage() {
        return Page.MAIN;
    }
}
