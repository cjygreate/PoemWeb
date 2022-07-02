package com.verse.app.dao;

import com.verse.app.entity.SelfContent;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SelfContentDao {

    long insert(SelfContent content);

}
