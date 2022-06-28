package com.verse.app.controller;


import com.verse.common.data.VerseResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonPoemController {


    @RequestMapping("/auth/person/submit")
    public VerseResponse submit() {
        VerseResponse verseResponse = new VerseResponse();

        return verseResponse.success();
    }

    @RequestMapping("/person/submit2")
    public VerseResponse submit2() {
        VerseResponse verseResponse = new VerseResponse();

        return verseResponse.success();
    }


}
