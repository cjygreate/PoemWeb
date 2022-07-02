package com.verse.app.controller;


import com.verse.app.entity.SelfContent;
import com.verse.app.service.SelfContentService;
import com.verse.common.data.VerseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
public class PersonPoemController {

    @Autowired
    private SelfContentService contentService;

    @PostMapping("/auth/person/islogin")
    public VerseResponse isLogin() {
        return new VerseResponse().success();
    }

    @PostMapping("/auth/person/submit")
    public VerseResponse submit(@RequestBody SelfContent content) {
        contentService.submitContent(content);
        VerseResponse verseResponse = new VerseResponse();
        return verseResponse.success();
    }

    @RequestMapping("/person/submit2")
    public VerseResponse submit2() {
        VerseResponse verseResponse = new VerseResponse();

        return verseResponse.success();
    }


}
