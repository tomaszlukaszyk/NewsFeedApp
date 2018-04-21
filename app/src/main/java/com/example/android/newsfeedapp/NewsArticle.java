package com.example.android.newsfeedapp;

public class NewsArticle {

    private final String section;
    private final String date;
    private final String title;
    private final String url;
    private final String author;

    NewsArticle(String section, String date, String title, String url, String author) {
        this.section = section;
        this.date = date;
        this.title = title;
        this.url = url;
        this.author = author;
    }

    public String getSection() {
        return section;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getAuthor() {
        return author;
    }
}
