package com.verse.app.dao;

import com.verse.app.entity.Poet;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface PoetDao {

    Poet queryPoetById(Long id);

}
