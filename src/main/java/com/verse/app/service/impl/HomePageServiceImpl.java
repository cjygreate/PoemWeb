package com.verse.app.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.verse.app.entity.Poem;
import com.verse.app.service.HomePageService;
import com.verse.app.utils.ESUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Service
public class HomePageServiceImpl implements HomePageService {

    @Override
    public Poem queryPoemByTitle(String title) throws Exception {
        SearchResponse response;
        Poem poem = new Poem();
        try {
            response = ESUtils.queryESForBoolMustTerm("poem", "_doc", "title", title);
            //获取到结果
            SearchHits hits = response.getHits();
            Iterator<SearchHit> iterator = hits.iterator();
            if(iterator.hasNext()) {
                SearchHit searchHit = iterator.next();
                poem = JSONObject.parseObject(searchHit.getSourceAsString(), Poem.class);
                List<String> list = new ArrayList<>();
                String[] split = poem.getContent().split("。");
                for (String s : split) {
                    if(s.contains("！")) {
                        String[] split1 = s.split("！");
                        for (String s1 : split1) {
                            list.add(s1 + "！");
                        }
                    }else {
                        list.add(s + "。");
                    }
                }
                poem.setContentList(list);
                poem.setId(searchHit.getId());
            }
        }catch (Exception e) {
            throw e;
        }

        return poem;
    }
}
