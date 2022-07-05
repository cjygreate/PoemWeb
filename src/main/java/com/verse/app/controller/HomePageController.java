package com.verse.app.controller;

import com.verse.app.entity.Poem;
import com.verse.app.service.HomePageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@CrossOrigin
public class HomePageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HomePageController.class);
    @Autowired
    private HomePageService homePageService;

    @Value("${spring.elasticsearch.uris}")
    private String elasticSearchAddress;

    @RequestMapping("/api/poem/homesearch")
    public Poem queryPoem(String title) throws Exception {
        Poem poem = homePageService.queryPoemByTitle(title);
        return poem;
    }

}
