package com.zzy.java.thread;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 定时任务
 */
public class TimerTaskSimple {

    public TimerTaskSimple() {

        /*
         TimerTask class 抽象类 ---------------------------------------------------------------------
         方法说明：
         run()                          //抽象方法，任务在这里面执行
         boolean cancel()               //取消此计时器任务。
         long scheduledExecutionTime()  //返回此任务最近实际执行的安排执行时间。
         */
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Date date = new Date(this.scheduledExecutionTime());//上次执行任务的时间
                System.currentTimeMillis();//系统当前时间
                System.out.println("本次执行该线程的时间为：" + date);
            }
        };

        /*
         Timer class 说明 ---------------------------------------------------------------------
         Timer缺陷：
         1、Timer 对调度的支持是基于绝对时间的，而不是相对时间，所以它对系统时间的改变非常敏感。
         2、Timer 是单线程的，线程是不会捕获异常，如果 TimerTask 抛出的了未try/catch的异常则会导致 Timer 线程终止，
         同时 Timer 也不会重新恢复线程的执行，他会错误的认为整个 Timer 线程都会取消。同时，已经被安排单尚未执行的 TimerTask 也不会再执行了，新的任务也不能被调度。
         补救措施：我们可以用ScheduledThreadPoolExecutor替换Timer

         方法说明：
         //指定绝对时间，执行任务 [当时间达到time 或 当时间大于time 就会执行任务]
         schedule(TimerTask task, Date time)
         schedule(TimerTask task, Date firstTime, long period)           //重复执行任务，非固定速率【上一个任务执行完，间隔period秒，再次执行下一个任务】
         scheduleAtFixedRate(TimerTask task, Date firstTime, long period) //重复执行任务，间隔固定速率【执行无论上个任务是否执行完成，间隔period秒后，都会执行下个任务】【理解有误，暂时不用】

         //指定延时（相对时间），执行任务
         schedule(TimerTask task, long delay)
         schedule(TimerTask task, long delay, long period)               //重复执行任务，非固定速率【上一个任务执行完，间隔period秒，再次执行下一个任务】
         scheduleAtFixedRate(TimerTask task, long delay, long period)    //重复执行任务，间隔固定速率【执行无论上个任务是否执行完成，间隔period秒后，都会执行下个任务】【理解有误，暂时不用】
         */
        Timer timer = new Timer();

        /*
         ScheduledExecutorService class 说明 ---------------------------------------------------------------------
         优点：
         1、ScheduledThreadPoolExecutor 是基于相对时间的
         2、ScheduledThreadPoolExecutor 内部是个线程池，所以可以支持多个任务并发执行。
         缺点：
         1、如果任务执行过程中抛出了异常，ScheduledExecutorService就会停止执行任务，且也不会再周期地执行该任务了。所以你如果想保住任务都一直被周期执行，那么catch一切可能的异常。
         方法说明：
         schedule(Runnable command, long delay, TimeUnit unit) //延迟后，执行一次任务
         //公共：//首次延迟时间initialDelay，每次间隔时间period，循环执行任务
         scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnitunit)   //【执行无论上个任务是否执行完成，间隔period秒后，都会执行下个任务】
         scheduleWithFixedDelay(Runnable command, long initialDelay, long delay,TimeUnit unit) //【上一个任务执行完，间隔period秒，再次执行下一个任务】
         */
        ScheduledExecutorService mSerialportWDExecutorService = Executors.newSingleThreadScheduledExecutor();
        mSerialportWDExecutorService.scheduleAtFixedRate(timerTask, 60 * 1000, 10 * 1000, TimeUnit.MILLISECONDS);

    }

    public Date getTimeForSimpleDateFormat(){
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date startDate = null;
        try {
            startDate = dateFormatter.parse("2010/11/26 00:20:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return startDate;
    }

    public Date getTime(){
        Calendar calendar = Calendar.getInstance();//获取日历
        calendar.set(Calendar.HOUR_OF_DAY, 11);//时
        calendar.set(Calendar.MINUTE, 39);//分
        calendar.set(Calendar.SECOND, 00);//秒

        return calendar.getTime();
    }

}
