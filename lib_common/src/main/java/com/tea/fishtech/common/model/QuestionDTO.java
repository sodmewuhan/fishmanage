package com.tea.fishtech.common.model;

/**
 * 远程协助的问题说明
 */
public class QuestionDTO {

    // 问题标题
    private String title;

    // 问题内容
    private String content;

    // 图片文件地址
    private String pictureUri;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPictureUri() {
        return pictureUri;
    }

    public void setPictureUri(String pictureUri) {
        this.pictureUri = pictureUri;
    }
}
