package com.zzy.dagger.component;

import com.zzy.dagger.DaggerActivity;
import com.zzy.dagger.module.CatModule;
import com.zzy.dagger.module.DogModule;

import dagger.Component;

// 可以同时实现多个Module
@Component(modules = {CatModule.class, DogModule.class})
public interface CatComponent {

    void inject(DaggerActivity app);

}
