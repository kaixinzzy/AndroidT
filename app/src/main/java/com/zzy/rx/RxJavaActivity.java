package com.zzy.rx;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;
import com.zzy.event.ac.R;
import com.zzy.rx.module.News;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Rx 响应式编程
 * https://www.jianshu.com/p/0cd258eecf60
 */
public class RxJavaActivity extends AppCompatActivity {
    private static final String TAG = RxJavaActivity.class.getSimpleName();

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);

        init();

        Observable.create(new ObservableOnSubscribe<Response>() {
            @Override
            public void subscribe(ObservableEmitter<Response> emitter) throws Exception {
                Request.Builder builder = new Request.Builder()
                        .url("http://c.m.163.com/nc/article/headline/T1348647909107/60-20.html")
                        .get();
                Request request = builder.build();
                Call call = new OkHttpClient().newCall(request);
                Response response = call.execute();
                emitter.onNext(response);
            }
        }).map(new Function<Response, News>() {
            @Override
            public News apply(Response response) throws Exception {
                Log.d(TAG, "map 线程：" + Thread.currentThread().getName() + "\n");
                if (response.isSuccessful()) {
                    ResponseBody body = response.body();
                    if (body != null) {
                        Log.d(TAG, "map:转换前:" + response.body());
                        return new Gson().fromJson(body.string(), News.class);
                    }
                }

                return null;
            }
        }).observeOn(AndroidSchedulers.mainThread())// 为doOnNext 指定在UI线程，否则报错
        .doOnNext(new Consumer<News>() {// 数据接收前，数据处理
            @Override
            public void accept(News news) throws Exception {
                Log.d(TAG, "doOnNext 线程：" + Thread.currentThread().getName() + "\n");
                Log.d(TAG, "可以做保存数据到本地等处理逻辑");
            }
        }).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<News>() {
            @Override
            public void accept(News news) throws Exception {
                Log.d(TAG, "subscribe 线程：" + Thread.currentThread().getName() + "\n");
                if (news != null) {
                    Log.d(TAG, "数据不为空" + news.getT1348647909107().get(0).getLtitle());
                } else {
                    Log.d(TAG, "数据为空");
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.d(TAG, "subscribe 线程：" + Thread.currentThread().getName() + "\n");
                Log.d(TAG, "数据请求失败 " + throwable.getMessage());
            }
        });
    }

    // 操作符
    @SuppressLint("CheckResult")
    public void init() {
        /*
        Log
        onSubscribe false
        onNext = 1
        onNext = 2
        onNext = 3
        onComplete
         */
        // 被观察者，被订阅者
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
                emitter.onNext(4); // onComplete后调用onNext，观察者是接收不到的
            }
        })
        // 观察者、订阅者
        .subscribe(new Observer<Integer>() {
            @Override // 它会在事件还未发送之前被调用，可以用来做一些准备操作。而里面的Disposable则是用来切断上下游的关系的。
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe " + String.valueOf(d.isDisposed()));
            }

            @Override // 普通的事件。将要处理的事件添加到队列中。
            public void onNext(Integer integer) {
                Log.d(TAG,  "onNext = " + integer);
            }

            @Override // 事件队列异常，在事件处理过程中出现异常情况时，此方法会被调用。同时队列将会终止，也就是不允许在有事件发出。
            public void onError(Throwable e) {
                Log.d(TAG, "onError");
            }

            @Override // 事件队列完成。rxjava不仅把每个事件单独处理。而且会把他们当成一个队列。当不再有onNext事件发出时，需要触发onComplete方法作为完成标识。
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }
        });

        /*
        map 拦截发送的每一条消息，并进行转化或执行一个函数后，再转交个观察者
        Log
        apply 我将所有发送的数据统一进行了处理 当前类型是String 1
        accept 我将所有发送的数据统一进行了处理 当前类型是String 1
        apply 我将所有发送的数据统一进行了处理 当前类型是String 2
        accept 我将所有发送的数据统一进行了处理 当前类型是String 2
         */
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
            }
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                String s = "我将所有发送的数据统一进行了处理 当前类型是String " + integer;
                Log.d(TAG, "apply " + s);
                return s;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, "accept " + s);
            }
        });

        /*
        zip 专用于合并事件，该合并不是连接（连接操作符后面会说），而是两两配对，也就意味着，最终配对出的 Observable 发射事件数目只和少的那个相同。
        Log a1; b2;
         */
        Observable.zip(Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("a");
                emitter.onNext("b");
                emitter.onNext("c");
            }
        }), Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
            }
        }), new BiFunction<String, Integer, String>() {
            @Override
            public String apply(String s, Integer integer) throws Exception {
                return s + integer;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, "zip accept " + s);
            }
        });

        /*
        concat 连接器，将两个发射器连接成一个，一次发送
        在操作符 concat 中，只有调用 onComplete 之后才会执行下一个 Observable，即3
        Log 1; 2; 3;
         */
        Observable.concat(Observable.just(1,2), Observable.just(3))
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "concat accept " + integer);
                    }
                });

        /*

        flatMap 把一个发射器  Observable 通过某种方法转换为多个 Observables，然后再把这些分散的 Observables装进一个单一的发射器 Observable。
            但有个需要注意的是，flatMap 并不能保证事件的顺序，如果需要保证，需要用到我们下面要讲的 ConcatMap。
        concatMap 跟flatMap的区别就在于它是有序的。下面代码中直接替换flatMap即可。
         */
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    list.add("I am value " + integer);
                }
                int delayTime = (int)(1 + Math.random() * 10);
                return Observable.fromIterable(list).delay(delayTime, TimeUnit.MILLISECONDS);
            }
        }).subscribeOn(Schedulers.newThread())// 发送者运行在当前线程
                .observeOn(AndroidSchedulers.mainThread())// 接收者运行在UI线程
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.d(TAG, "flatMap accept " + s);
                    }
                });

        /*
        distinct 去除发射器中重复的项
        Log 1; 2; 3;
         */
        Observable.just(1,2,3,2,1)
                .distinct()
                .subscribe(new Consumer<Number>() {
                    @Override
                    public void accept(Number number) throws Exception {
                        Log.d(TAG, "distinct accept " + number);
                    }
                });

        /*
        filter 过滤器，过滤掉不符合我们条件的值
        Log 20; 65;
         */
        Observable.just(1,20,65,7,9)
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        // 只有大于10值，才可以发射出去
                        if (integer > 10) {
                            return true;
                        }
                        return false;
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.d(TAG, "filter accept " + integer);
            }
        });

        /*
        buffer(3,2) 将发射器拆分，拆分后的每个发射器长度为3，没间隔2个拆分一次
        拆分后生成三个发射器分别是
        Observable.just(1,2,3)
        Observable.just(3,4,5)
        Observable.just(5)
         */
        Observable.just(1,2,3,4,5)
                .buffer(3,2)
                .subscribe(new Consumer<List<Integer>>() {
                    @Override
                    public void accept(List<Integer> integers) throws Exception {
                        Log.d(TAG, "buffer " + integers);
                    }
                });
        /*
        timer 定时器
        记得销毁定时任务
         */
        mTimerDisposable = Observable.timer(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())// timer 默认在新线程，所以需要切换回主线程
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        // 两秒后，会执行这里的逻辑
                    }
                });
        /*
        interval 间隔时间执行某个操作
        记得销毁定时任务
         */
        mIntervalDisposable = Observable.interval(3, 2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())// interval 默认在新线程，所以需要切换回主线程
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        // 第一次延迟3秒后，会执行到这里，以后每个2秒执行到这里一次。
                    }
                });
        /*
        doOnNext 在订阅者在接收到数据之前干点事情，比如保存数据到数据库等
         */
        Observable.just(1,2,3)
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        // 将数据保存到数据库
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {

            }
        });
        /*
        skip 跳过发送器中一定长度的数据
        Log skip 3
         */
        Observable.just(1,2,3)
                .skip(2)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "skip " + integer);
                    }
                });
        /*
        take 至多接收多少个发送器的长度的数据
        Log take 1
         */
        Observable.just(1,2,3)
                .take(1)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "take " + integer);
                    }
                });
        /*
        just 简单的实现发射器，内部会依次调用onNext方法
        Log a; b; c;
         */
        Observable.just("a","b", "c")
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.d(TAG, "just " + s);
                    }
                });
        /*
        Single 只会接收一个参数，而SingleObserver只会调用onSuccess或者onError
        Log onSubscribe; onSuccess
         */
        Single.just(new Random().nextInt())
                .subscribe(new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "Single onSubscribe");
                    }

                    @Override
                    public void onSuccess(Integer integer) {
                        Log.d(TAG, "Single onSuccess");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "Single onError");

                    }
                });
        /*
        debounce 去除发送频率过快的项
        Log 2;4;5
         */
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                Thread.sleep(400);// 如果暂停时间小于500，则过滤掉之前的消息
                emitter.onNext(2);// 通过
                Thread.sleep(505);
                emitter.onNext(3);
                Thread.sleep(100);
                emitter.onNext(4);// 通过
                Thread.sleep(605);
                emitter.onNext(5);// 通过
//                Thread.sleep(510);
                emitter.onComplete();
            }
        })
                // 去除发送时间间隔小于500毫秒的发射事件
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "debounce " + integer);
                    }
                });
        /*
        defer 如果订阅就会产生新的Observable
        Log onSubscribe; 1; 2; 3; onComplete;
         */
        Observable<Integer> observable = Observable.defer(new Callable<ObservableSource<? extends Integer>>() {
            @Override
            public ObservableSource<? extends Integer> call() throws Exception {
                return Observable.just(1,2,3);
            }
        });
        observable.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "defer onSubscribe");
            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "defer " + integer);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                Log.d(TAG, "defer onComplete");
            }
        });
        /*
        last 仅取出可观察到的最后一个值，或者是满足某些条件的最后一项。
        Log 3;
         */
        Observable.just(1,2,3)
                .last(4)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "last " + integer);
                    }
                });

    }

    private Disposable mTimerDisposable;
    private Disposable mIntervalDisposable;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 销毁定时任务
        if (mTimerDisposable != null && !mTimerDisposable.isDisposed()) {
            mTimerDisposable.dispose();
        }
        if (mIntervalDisposable != null && !mIntervalDisposable.isDisposed()) {
            mIntervalDisposable.dispose();
        }
    }
}
