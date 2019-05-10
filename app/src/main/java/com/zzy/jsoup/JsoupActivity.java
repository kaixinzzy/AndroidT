package com.zzy.jsoup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.zzy.event.ac.R;
import com.zzy.jsoup.commons.Url;
import com.zzy.jsoup.data.Comic;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class JsoupActivity extends AppCompatActivity {
    private static final String TAG = "JsoupActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jsoup);

        new Thread() {
            @Override
            public void run() {
                super.run();
                getData();
            }
        }.start();

    }

    // 取消所有订阅，防止内存溢出
    private final CompositeDisposable compositeDisposable=new CompositeDisposable();

    public void getData() {
        Document document = null;
        try {
            // Banner
            document = Jsoup.connect(Url.TencentBanner).get();
            TencentComicAnalysis.TransToBanner(document);
            // 热门连载
            document = Jsoup.connect(Url.TencentHomePage).get();
            TencentComicAnalysis.TransToNewComic(document);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Observable.create(new ObservableOnSubscribe<List<Comic>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Comic>> emitter) throws Exception {
                // Banner
                try {
                    Document document = Jsoup.connect(Url.TencentBanner).get();
                    emitter.onNext(TencentComicAnalysis.TransToBanner(document));
                } catch (IOException e) {
                    emitter.onError(e);
                    e.printStackTrace();
                } finally {
                    emitter.onComplete();
                }

            }
        })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<List<Comic>>() {
            @Override
            public void onSubscribe(Disposable d) {
                // 管理订阅
                compositeDisposable.add(d);
            }

            @Override
            public void onNext(List<Comic> comics) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}
