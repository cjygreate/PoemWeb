package com.verse.app.entity;

import java.io.Serializable;
import java.util.List;

public class Poem implements Serializable {

    private String id;
    //标题
    private String title;
    //作者
    private String author;
    //朝代
    private String dynasty;
    //诗歌情感类型
    private String emotionType;
    //诗歌体裁类型
    private String genreType;
    //内容
    private String content;
    //内容VO
    private List<String> contentList;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDynasty() {
        return dynasty;
    }

    public void setDynasty(String dynasty) {
        this.dynasty = dynasty;
    }

    public String getEmotionType() {
        return emotionType;
    }

    public void setEmotionType(String emotionType) {
        this.emotionType = emotionType;
    }

    public String getGenreType() {
        return genreType;
    }

    public void setGenreType(String genreType) {
        this.genreType = genreType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getContentList() {
        return contentList;
    }

    public void setContentList(List<String> contentList) {
        this.contentList = contentList;
    }

    @Override
    public String toString() {
        return "Poem{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", dynasty='" + dynasty + '\'' +
                ", emotionType='" + emotionType + '\'' +
                ", genreType='" + genreType + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
