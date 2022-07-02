package com.verse.app.service;

import com.verse.app.entity.SelfContent;

public interface SelfContentService {


    void submitContent(SelfContent selfContent);

    SelfContent queryContent();

}
