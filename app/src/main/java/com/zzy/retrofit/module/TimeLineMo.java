package com.zzy.retrofit.module;

import java.util.ArrayList;

/**
 * Created by BCLZzy on 2018/6/13.
 */

public class TimeLineMo {
    public ArrayList<TopicTimeLineMo> topics;
    public String message;
    public int errorCode;
    public ArrayList<String> keywords;

    public ArrayList<TopicTimeLineMo> getTopics() {
        return topics;
    }

    public void setTopics(ArrayList<TopicTimeLineMo> topics) {
        this.topics = topics;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public ArrayList<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(ArrayList<String> keywords) {
        this.keywords = keywords;
    }
}
