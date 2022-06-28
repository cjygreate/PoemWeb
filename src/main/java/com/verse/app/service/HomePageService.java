package com.verse.app.service;

import com.verse.app.entity.Poem;

import java.io.IOException;

public interface HomePageService {


    Poem queryPoemByTitle(String title) throws Exception;


}
