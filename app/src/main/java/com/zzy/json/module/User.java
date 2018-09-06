package com.zzy.json.module;

import com.google.gson.annotations.SerializedName;

public class User {

    // 这样当将json转化成bean时，会兼容name、Name、user_name （当API对应有误差时会用到）
    @SerializedName(value = "name", alternate = {"name","Name","user_name"})
    private transient String name;
    // serialize指定该属性生成json时，是否过滤掉该字段
    // deserialize指定该属性生成bean时，是否过滤掉该字段
    // 需要配合GsonBuilder才生效
//    @Expose(serialize = true, deserialize = true)
    private int age;
    private boolean sex;

    public User(String name, int age, boolean sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                '}';
    }
}
