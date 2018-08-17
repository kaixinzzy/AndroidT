package com.zzy.dagger.module;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

/**
 * 使用@Inject标记构造函数来提供依赖的对象实例的方法不是万能的，在以下集中场景中无法使用
 * 1、接口没有构造函数
 * 2、第三方库的类不能被标记
 * 3、构造函数中的参数必须配置
 *
 * 所以提供了第二种方案@Module
 * 这时，就可以用@Provides标注的方法来提供依赖实例，方法的返回值就是依赖的对象实例
 */
@Module
public class CommonModule {

    @Provides
    OkHttpClient provideOkHttpClient() {
        return new OkHttpClient();
    }

}
