package com.tea.fishtech.common.model;

/**
 * 新闻类数据
 */
public class NewsData {

    private String newsTitle;//新闻标题

    private String newsDate; //新闻发布时间

    private String newsImgUrl; // 新闻图片Url地址

    private String newsUrl; //新闻详情Url地址

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsDate() {
        return newsDate;
    }

    public void setNewsDate(String newsDate) {
        this.newsDate = newsDate;
    }

    public String getNewsImgUrl() {
        return newsImgUrl;
    }

    public void setNewsImgUrl(String newsImgUrl) {
        this.newsImgUrl = newsImgUrl;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }
}
