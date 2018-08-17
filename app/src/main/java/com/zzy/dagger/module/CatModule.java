package com.zzy.dagger.module;

import com.zzy.dagger.bean.Cat;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class CatModule {

    // @Named 当返回值类型相同时，用于区分谁是谁
    @Provides @Named("Null")
    Cat provideCatNull() {
        return new Cat();
    }

    @Provides @Named("White")
    Cat provideCatWhite() {
        return new Cat("白猫");
    }

    @Provides @Named("Black")
    Cat provideCatBlack() {
        return new Cat("黑猫");
    }

}
