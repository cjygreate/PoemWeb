package com.verse.app.service;

import com.verse.app.entity.SelfContent;

import java.util.List;

public interface SelfContentService {


    void submitContent(SelfContent selfContent);

    List<SelfContent> queryContent();

}
