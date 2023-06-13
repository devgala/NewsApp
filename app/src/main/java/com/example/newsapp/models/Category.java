package com.example.newsapp.models;

public class Category {
    private String categoryName;
    private String categoryImg;

    public Category(String categoryName, String categoryImg) {
        this.categoryName = categoryName;
        this.categoryImg = categoryImg;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryImg() {
        return categoryImg;
    }

    public void setCategoryImg(String categoryImg) {
        this.categoryImg = categoryImg;
    }
}
