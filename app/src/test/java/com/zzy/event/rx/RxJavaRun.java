package com.zzy.event.rx;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RxJavaRun {

    /**
     * 创建型操作符 create
     */
    @Test
    public void createCreate() {
        // 被观察者，被订阅者
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("1");
                emitter.onNext("2");
                emitter.onNext("3");
                emitter.onComplete();
                emitter.onNext("4"); // onComplete后调用onNext，观察者是接收不到的
            }
        }).
        // 观察者、订阅者
        subscribe(getObserver());
    }

    /**
     * 创建型操作符 just
     * 将传入的参数依次发出
     */
    @Test
    public void createJust() {
        Observable.just("1", "2" ,"3")
                .subscribe(getObserver());
        // 将会依次调用被观察者的：
        // onNext(1);
        // onNext(2);
        // onNext(3);
        // onCompleted();
    }

    /**
     * 创建型操作符 fromArray
     * 将传入的数组通过坐标一次发送
     */
    @Test
    public void createFromArray() {
        String[] words = {"RxJava2", "Retrofit", "OkHttp"};
        Observable.fromArray(words).subscribe(getObserver());
        // 将会依次调用被观察者的：
        // onNext("RxJava2");
        // onNext("Retrofit");
        // onNext("OkHttp");
        // onCompleted();
    }

    /**
     * 创建型操作符 interval
     */
    @Test
    public void createInterval() {
        // 2秒后，每隔6秒执行一次
        Observable.interval(2000, 3000, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        System.out.println("accept = " + aLong);
                    }
                });
    }

    /**
     * 转换型操作符 map
     * 把要发送的Integer类型的数据，通过map转换成了String类型的数据，发送给观察者
     */
    @Test
    public void convertMap() {
        Observable.just(1, 2 ,3)
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        return String.valueOf(integer);
                    }
                })
                .subscribe(getObserver());
    }

    /**
     * 转换型操作符 flatMap 无序
     * 把要发送的每一条数据，发散成多条数据，但顺序不定。有点类似于散弹枪
     */
    @Test
    public void convertFlatMap() {
        Observable.just(1, 2, 3)
                .flatMap(new Function<Integer, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<String> apply(Integer integer) throws Exception {
                        System.out.println("apply " + integer);
                        final List<String> list = new ArrayList<>();
                        for (int i = 0; i < 3; i++) {
                            list.add("" + integer);
                        }
                        // 延迟100毫秒,发射事件，这样才可以看出无序性
                        return Observable.fromIterable(list).delay(100, TimeUnit.MILLISECONDS);
                    }
                }).subscribe(getObserver());
    }

    /**
     * 转换型操作符 concatMap 有序
     * 把要发送的每一条数据，发散成多条数据，有点类似于散弹枪
     */
    @Test
    public void convertConcatMap() {
        Observable.just(1, 2, 3)
                .concatMap(new Function<Integer, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Integer integer) throws Exception {
                        System.out.println("apply " + integer);
                        final List<String> list = new ArrayList<>();
                        for (int i = 0; i < 3; i++) {
                            list.add("" + integer);
                        }
                        // 延迟100毫秒,发射事件，这样才可以看出无序性
                        return Observable.fromIterable(list).delay(100, TimeUnit.MILLISECONDS);
                    }
                }).subscribe(getObserver());
    }

    /**
     * 转换型操作符 compose
     * 统一操作，将发送过程在子线程中处理，接收在UI线程处理。
     */
    @Test
    public void convertCompose() {
        Observable.just(1, 2, 3)
                .compose(this.<Integer>applyObservableAsync())
                .subscribe(getObserver());
    }

    public <T> ObservableTransformer<T, T> applyObservableAsync() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 线程控制
     * RxJava内置线程选项如下：
     * 1、Schedulers.io() ：
     *      I/O操作（读写文件、数据库，及网络交互等）所使用的Scheduler。行为模式和newThread()差不多。区别在于io()的内部实现是用一个无数量上限的线程池。
     *      可以重用空闲的线程。因此多数情况下io()比newThread()更有效率。
     * 2、AndroidSchedulers.mainThread()：代表Android的主线程
     * 3、Schedulers.immediate()： 直接在当前线程运行。
     * 4、Schedulers.computation() ：
     *      计算所使用的Scheduler，例如图形的计算。这个Scheduler使用固定线程池，大小为CPU核数。不要把I/O操作放在computation中。否则I/O操作的等待会浪费CPU。
     * 5、Schedulers.newThread()：代表一个常规的新线程
     * 6、Schedulers.trampoline()
     *      当我们想在线程执行一个任务时（不是立即执行），可以用此方法将它加入队列。这个调度器将会处理它的队列并且按序执行队列中的每一个任务。
     */
    public void thread() {
        Observable.just(1, 2, 3)
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {

                    }
                });
    }

    // 创建观察者、订阅者
    public Observer getObserver() {
        return new Observer<String>() {

            // 它会在事件还未发送之前被调用，可以用来做一些准备操作。
            @Override
            public void onSubscribe(Disposable d) {
                // 终止队列，后面的所有方法将不会被执行。
                // d.dispose();
                System.out.println("onSubscribe " + String.valueOf(d.isDisposed()));
            }

            // 普通的事件。将要处理的事件添加到队列中。
            @Override
            public void onNext(String s) {
                System.out.println("onNext = " + s);
            }

            // 事件队列异常
            // 在事件处理过程中出现异常情况时，此方法会被调用。同时队列将会终止，也就是不允许在有事件发出。
            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            // 事件队列完成。
            // rxjava不仅把每个事件单独处理。而且会把他们当成一个队列。当不再有onNext事件发出时，需要触发onComplete方法作为完成标识。
            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        };
    }

}
