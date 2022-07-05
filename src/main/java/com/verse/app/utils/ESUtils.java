package com.verse.app.utils;

import com.alibaba.fastjson.JSONObject;
import com.verse.app.config.LocalProperties;
import org.apache.http.HttpHost;
import org.apache.lucene.index.IndexOptions;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Properties;

public class ESUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ESUtils.class);

    private static RestHighLevelClient restHighLevelClient = null;

    private final static String TERM_MATCH = ".keyword";

    static {
        try {
            String property = LocalProperties.getLocalProperty("elasticsearch.address");
            String[] address = property.split(":");
            String protocol = address[0];
            String host = address[1].substring(2);
            int port = Integer.parseInt(address[2]);
            ESUtils.restHighLevelClient = new RestHighLevelClient(RestClient.builder(new HttpHost(host, port, protocol)));
        } catch (IOException e) {
            LOGGER.error("application.properties属性文件读取异常");
            System.out.println("application.properties属性文件读取异常" + e);
        }

    }

    /**
     * 根据某个字段精确匹配
     * @param index 索引
     * @param type  类型
     * @param field 属性
     * @param value 属性值
     * @return
     * @throws IOException
     */
    public static SearchResponse queryESForBoolMustTerm(String index, String type, String field, String value) throws IOException {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(index);
        searchRequest.types(type);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery(field + TERM_MATCH, value);
        boolQueryBuilder.must().add(termQueryBuilder);
        searchSourceBuilder.query(boolQueryBuilder);
        //组装语句
        searchRequest.source(searchSourceBuilder);
        return restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
    }


    public static <T> void insertToES(String index, String type, List<T> T) {
        BulkRequest bulkRequest = new BulkRequest();
        for (T t : T) {
            String s = JSONObject.toJSONString(t);
            IndexRequest indexRequest = new IndexRequest(index);
            indexRequest.source(s, XContentType.JSON);
            indexRequest.type(type);
            bulkRequest.add(indexRequest);
        }
        try {
            BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            LOGGER.error("插入es数据异常，索引名称：{}，类型：{}，内容：{}", index, type, JSONObject.toJSONString(T));
            throw new RuntimeException(e);
        }
    }


}
