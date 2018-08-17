package com.zzy.dagger.bean;

import javax.inject.Inject;

public class Product {
    private String name;

    @Inject
    public Product() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
