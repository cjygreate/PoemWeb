package com.verse.app.entity;

public class UserContentRel {

    private Long id;

    private Long userId;

    private Long contentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    @Override
    public String toString() {
        return "UserContentRel{" +
                "id=" + id +
                ", userId=" + userId +
                ", contentId=" + contentId +
                '}';
    }
}
