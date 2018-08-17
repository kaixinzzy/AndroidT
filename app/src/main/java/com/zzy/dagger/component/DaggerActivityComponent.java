package com.zzy.dagger.component;

import com.zzy.dagger.module.CommonModule;
import com.zzy.def.base.APP;

import dagger.Component;

/**
 * dependencies = CatComponent.class,
 */
@Component(modules = {CommonModule.class})
public interface DaggerActivityComponent {

    void inject(APP app);

}
