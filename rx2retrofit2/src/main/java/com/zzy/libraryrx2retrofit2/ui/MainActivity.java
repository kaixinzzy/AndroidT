package com.zzy.libraryrx2retrofit2.ui;

import android.os.Bundle;
import android.util.Log;

import com.zzy.libraryrx2retrofit2.R;
import com.zzy.libraryrx2retrofit2.api.ApiService;
import com.zzy.libraryrx2retrofit2.api.bean.Article;
import com.zzy.libraryrx2retrofit2.api.bean.ArticleData;
import com.zzy.libraryrx2retrofit2.api.bean.HttpResult;
import com.zzy.libraryrx2retrofit2.base.BaseActivity;
import com.zzy.libraryrx2retrofit2.rx.BaseObserver;

import java.util.List;

/**
 *
 * 封装基础 https://blog.csdn.net/yrmao9893/article/details/69791519
 * 封装晋级 https://www.jianshu.com/p/bd758f51742e
 * RxJava  https://juejin.im/post/5a43a842f265da432d2863ab
 * 数据接口 http://www.wanandroid.com/blog/show/2
 */
public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ApiService.getInstance().API()
                .getArticleList(0)
                // BaseActivity统一线程切换
                .compose(this.<HttpResult<ArticleData>>setThread())
                // 在自定义Observer中对错误代码等逻辑进行统一处理
                .subscribe(new BaseObserver<ArticleData>() {
                    @Override
                    protected void onSuccess(HttpResult<ArticleData> articleDataHttpResult) throws Exception {
                        if (articleDataHttpResult.getErrorCode() == 0) {
                            List<Article> articleList = articleDataHttpResult.getData().getDatas();
                            for (Article article : articleList) {
                                Log.d(TAG, article.getAuthor() + " " + article.getTitle());
                            }
                        } else {
                            Log.d(TAG, "失败原因：" + articleDataHttpResult.getErrorMsg());
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }
}
