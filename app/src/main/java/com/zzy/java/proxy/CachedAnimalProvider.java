package com.zzy.java.proxy;

import com.zzy.java.proxy.abstracts.AnimalFactory;
import com.zzy.java.proxy.interfaces.Animal;
import com.zzy.java.proxy.interfaces.AnimalProvider;

import java.util.HashMap;
import java.util.Map;

public class CachedAnimalProvider implements AnimalProvider {

    private Map<String, Animal> cached;

    public CachedAnimalProvider() {
        cached = new HashMap<>();
    }

    @Override
    public Animal getAnimal(String name) {

        Animal animal = cached.get(name);
        if (animal == null) {
            animal = AnimalFactory.getAnimal("pig");
            cached.put(name, animal);
        }
        return animal;
    }
}
