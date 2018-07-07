package com.zzy.retrofit.module;

import java.util.ArrayList;

/**
 * Created by BCLZzy on 2018/6/13.
 */

public class TopicMo {
    public String id;
    public String createAt;
    public ArrayList<NewsMo> newsArray;
    public long order;
    public String publishDate;
    public String summary;
    public String title;
    public String updateAt;
    public TimeLineMo timeline;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public ArrayList<NewsMo> getNewsArray() {
        return newsArray;
    }

    public void setNewsArray(ArrayList<NewsMo> newsArray) {
        this.newsArray = newsArray;
    }

    public long getOrder() {
        return order;
    }

    public void setOrder(long order) {
        this.order = order;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public TimeLineMo getTimeline() {
        return timeline;
    }

    public void setTimeline(TimeLineMo timeline) {
        this.timeline = timeline;
    }
}
