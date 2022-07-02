package com.verse.app.dao;

import com.verse.app.entity.UserContentRel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserContentRelDao {

    void insert(UserContentRel userContentRel);

}
