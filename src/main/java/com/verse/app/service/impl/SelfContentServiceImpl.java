package com.verse.app.service.impl;

import com.verse.app.dao.SelfContentDao;
import com.verse.app.dao.UserContentRelDao;
import com.verse.app.entity.SelfContent;
import com.verse.app.entity.UserContentRel;
import com.verse.app.entity.UserInfo;
import com.verse.app.service.SelfContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SelfContentServiceImpl implements SelfContentService {

    @Autowired
    private SelfContentDao contentDao;

    @Autowired
    private UserContentRelDao userContentRelDao;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void submitContent(SelfContent selfContent) {
        long contentId = contentDao.insert(selfContent);
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserContentRel userContentRel = new UserContentRel();
        userContentRel.setUserId(userInfo.getId());
        userContentRel.setContentId(contentId);
        userContentRelDao.insert(userContentRel);
    }

    @Override
    public SelfContent queryContent() {
        return null;
    }
}
