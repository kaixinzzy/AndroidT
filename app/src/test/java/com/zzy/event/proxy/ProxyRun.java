package com.zzy.event.proxy;

import com.zzy.java.proxy.abstracts.AnimalFactory;
import com.zzy.java.proxy.interfaces.Animal;

import org.junit.Test;

public class ProxyRun {

    @Test
    public void Main() {
        Animal pig = AnimalFactory.getAnimal("pig");
        pig.Wow();
    }

}
