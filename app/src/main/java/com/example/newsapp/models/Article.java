package com.example.newsapp.models;

public class Article {
    private Source source;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private  String srcName;
    private String publishedAt;
    private String content;

    public Article(String title, String description, String url, String urlToImage) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;

    }
    public Article(String title, String description, String url, String urlToImage,String publishedAt,String content,Source source) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;

        this.publishedAt = publishedAt;
        this.content = content;
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        if(urlToImage!=null)
        return urlToImage;
        else return "no image";
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getSrcName() {
        return srcName;
    }

    public void setSrcName(String srcName) {
        this.srcName = srcName;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }
}
