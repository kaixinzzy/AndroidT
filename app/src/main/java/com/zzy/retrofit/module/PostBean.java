package com.zzy.retrofit.module;

/**
 * Created by BCLZzy on 2018/6/13.
 */

public class PostBean {
    private String useName;
    private String pwd;

    public PostBean(String useName, String pwd) {
        this.useName = useName;
        this.pwd = pwd;
    }

    public String getUseName() {
        return useName;
    }

    public void setUseName(String useName) {
        this.useName = useName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
