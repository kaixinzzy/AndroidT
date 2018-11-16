package com.zzy.java.proxy.abstracts;

import com.zzy.java.proxy.beans.Cat;
import com.zzy.java.proxy.beans.Dog;
import com.zzy.java.proxy.beans.Pig;
import com.zzy.java.proxy.interfaces.Animal;

public class AnimalFactory {

    public static Animal getAnimal(String name) {
        switch (name) {
            case "cat":
                return new Cat();
            case "dog":
                return new Dog();
            case "pig":
                return new Pig();
        }
        return null;
    }
}
