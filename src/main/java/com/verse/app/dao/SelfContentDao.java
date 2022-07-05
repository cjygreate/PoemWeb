package com.verse.app.dao;

import com.verse.app.entity.SelfContent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SelfContentDao {

    long insert(SelfContent content);

    /**
     * 根据用户id查询作品
     * @param id
     * @return
     */
    List<SelfContent> queryContent(@Param("id") Long id);
}
