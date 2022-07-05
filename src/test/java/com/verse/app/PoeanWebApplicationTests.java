package com.verse.app;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.verse.app.dao.PoetDao;
import com.verse.app.entity.Poem;
import com.verse.app.entity.Poet;
import com.verse.app.utils.ESUtils;
import org.apache.commons.io.FileUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.document.DocumentField;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@SpringBootTest
class PoeanWebApplicationTests {

    private static final Logger logger = LoggerFactory.getLogger(PoeanWebApplicationTests.class);

    @Test
    void contextLoads() {
    }

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Autowired
    private PoetDao poetDao;

    @Test
    void testEsInsert() throws IOException {
        // IndexRequest
        IndexRequest indexRequest = new IndexRequest("poem");

        Poem poem = new Poem();
        poem.setAuthor("杜甫");
        poem.setDynasty("唐朝");
        poem.setEmotionType("悲凉");
        poem.setGenreType("七言律诗");
        poem.setTitle("登高");
        List<String> list = new ArrayList<>();
        list.add("风急天高猿啸哀，渚清沙白鸟飞回。");
        list.add("无边落木萧萧下，不尽长江滚滚来。");
        list.add("万里悲秋常作客，百年多病独登台。");
        list.add("艰难苦恨繁霜鬓，潦倒新停浊酒杯。");
        poem.setContentList(list);
        String source = JSONObject.toJSONString(poem);
        indexRequest.source(source, XContentType.JSON);
        // 操作ES
        IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        System.out.println(indexResponse);
    }

    @Test
    void testEsQuery() throws IOException {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("poem");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("title.keyword", "静夜思");
        boolQueryBuilder.must().add(termQueryBuilder);
        //忽略格式错误
//        matchQueryBuilder.lenient();
        searchSourceBuilder.query(boolQueryBuilder);
        //组装语句
        searchRequest.source(searchSourceBuilder);
        SearchResponse response;
        Poem poem = new Poem();
        try {
            response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            //获取到结果
            SearchHits hits = response.getHits();
            Iterator<SearchHit> iterator = hits.iterator();
            if(iterator.hasNext()) {

                SearchHit searchHit = iterator.next();
                poem = JSONObject.parseObject(searchHit.getSourceAsString(), Poem.class);
                poem.setId(searchHit.getId());
            }
        }catch (Exception e) {
            throw e;
        }
        System.out.println(poem);

    }


    @Test
    public void InsertES() {
        try {
            String s = FileUtils.readFileToString(new File("C:\\project\\temp\\poem.txt"), StandardCharsets.UTF_8);
            List<Poem> poems = JSONArray.parseArray(s, Poem.class);
            System.out.println(poems);
            ESUtils.insertToES("poem", null, poems);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testMybatis() {
        Poet poet = poetDao.queryPoetById(1l);
        System.out.println(poet);
    }

    @Test
    public void testLogger() {
        logger.info("testlog......");
    }


}
