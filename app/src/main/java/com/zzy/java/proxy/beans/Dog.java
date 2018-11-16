package com.zzy.java.proxy.beans;

import com.zzy.java.proxy.interfaces.Animal;

public class Dog implements Animal{

    @Override
    public void Wow() {
        System.out.println("旺旺");
    }
}
