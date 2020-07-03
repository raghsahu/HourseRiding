package com.riding.hourseriding.model;

public class TodayNewsModel {
    String rendered;
    String date;
    String sourceUrl;
    String id;

    public TodayNewsModel(String rendered, String date, String sourceUrl, String id) {
        this.rendered = rendered;
        this.date = date;
        this.sourceUrl = sourceUrl;
        this.id = id;
    }

    public TodayNewsModel(String date, String id) {
        this.id = id;
        this.date = date;
    }

    public String getRendered() {
        return rendered;
    }

    public void setRendered(String rendered) {
        this.rendered = rendered;
    }

    public String getDate() {
        return date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }
}
