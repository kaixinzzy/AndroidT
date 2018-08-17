package com.zzy.dagger.module;

import com.zzy.dagger.bean.Dog;

import dagger.Module;
import dagger.Provides;

@Module
public class DogModule {

    @Provides
    Dog provideDog() {
        return new Dog();
    }

}
