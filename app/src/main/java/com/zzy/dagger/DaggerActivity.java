package com.zzy.dagger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.zzy.dagger.bean.Cat;
import com.zzy.dagger.bean.Product;
import com.zzy.event.ac.R;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Dagger
 * 依赖注入 Dependency Injection 简称DI
 * 控制反转 Inversion of Control 简称IOC
 * 注意：
 *      关闭代码混淆，或混淆时过滤关于Dagger的代码
 *      自动生成代码请使用Ctrl+F9
 *      一个Activity不可以交由两个Component注入，会导致生成文件出错
 * 参考：https://blog.csdn.net/u010961631/article/category/6931544
 */
public class DaggerActivity extends AppCompatActivity {
    private static final String TAG = "DaggerActivity";

    @Inject
    Product mProduct;
//    @Inject
//    OkHttpClient mOkHttpClient;
    @Inject @Named("Null")
    Cat mCat;
    @Inject @Named("White")
    Cat mCatWhite;
    @Inject @Named("Black")
    Cat mCatBlack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagger);
        // DaggerActivityComponent通过手动点击make project会自动在目录"/build/generated/source/apt/debug/{包名}/"下生成DaggerDaggerActivityComponent类
        // 通过调用它自定义的inject方法，将本类与Component关联起来
        // Component是注入者(activity)与被注入者(mProduct)之间联系的桥梁
//        DaggerDaggerActivityComponent.create().inject(this);
//        Log.d(TAG, mCat.getName());
//        Log.d(TAG, mCatWhite.getName());
//        Log.d(TAG, mCatBlack.getName());

    }
}
