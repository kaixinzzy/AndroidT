package com.zzy.dagger.bean;

public class Cat {
    private String name = "无名";

    public Cat() {}

    public Cat(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
