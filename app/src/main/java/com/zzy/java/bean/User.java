package com.zzy.java.bean;

public class User {

    private String name;
    private int age;

    public User(String mName, int mAge){
        this.name = mName;
        this.age = mAge;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public int getAge() {
        return age;
    }

    public User setAge(int age) {
        this.age = age;
        return this;
    }

}
