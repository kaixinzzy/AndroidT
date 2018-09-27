package com.zzy.android.anim;

/**
 * 动画
 * 1、View动画
 * 2、属性动画：更加灵活，功能更多
 * 3、AnimationDrawable 逐帧动画
 * 参考：
 *      https://www.jianshu.com/p/b117c974deaf
 */
public class AnimT {

    /*
    一、View动画 【补间动画】
    1、AlphaAnimation  透明度动画
    2、RotateAnimation 旋转动画
    3、ScaleAnimation  缩放动画
    4、TranslateAnimation 平移动画
    5、AnimationSet    动画集合。可以将多个补间动画以组合的形式显示出来

    继承关系:
    Object
        Animation(android.view.animation)
            AlphaAnimation(android.view.animation)
            RotateAnimation(android.view.animation)
            ScaleAnimation(android.view.animation)
            TranslateAnimation(android.view.animation)
            AnimationSet(android.view.animation)

     使用View动画框架可以在Views上执行补间动画。 补间动画是指只要指定动画的开始、动画结束的"关键帧"，
     而动画变化的"中间帧"由系统计算并补齐；无论动画怎样改变View的显示区域（移动或者改变大小），持有该
     动画的View的边界不会自动调整来适应View的显示区域， 即使如此，该动画仍将被绘制在超出其视图边界并
     且不会被剪裁， 但是，如果动画超过父视图的边界，则会出现裁剪。

    使用: /res/anim/xxx.xml
    loadAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.base_anmation_rotate);
    loadAnimation.setFillAfter(true);
    imageView.startAnimation(loadAnimation);
     */

    /*
    二、属性动画  全部继承自 android.animation.Animator
    View动画只改变了View的显示，点击事件的相应的区域，还在原来的位置。而属性动画不全了这个缺陷
    Android3.0（API 11）及后续版本引入

    继承关系
    Object
        Animator(android.animation)
            AnimatorSet(android.animation)
            ValueAnimator(android.animation)
                ObjectAnimator(android.animation)
                TimeAnimator(android.animation)
     */

    /*
    三、Drawable动画 逐帧动画
    加载多张Drawable图片并逐帧显示出来
     */
}
