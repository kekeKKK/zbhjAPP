package com.itheima.zbhjapp.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * @Author: LiuKe
 * @创建时间: 2016/2/19
 * @描述: 子控件ViewPager滑动时的事件传递机制处理
 */
public class TouchViewPager extends ViewPager {

    private static final String TAG = "TouchViewPager";
    private float mDownX = 0;
    private float mDownY = 0;

    public TouchViewPager(Context context) {
        super(context);
    }

    public TouchViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int currentItem = getCurrentItem();   //ViewPager当前位置ViewPager页签数量
        int count = getAdapter().getCount();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //点击时父控件不拦截
                //true:不拦截
                //false:拦截
                getParent().requestDisallowInterceptTouchEvent(true);
                mDownX = ev.getX();
                mDownY = ev.getY();

                Log.d(TAG,"点击时:downx="+ mDownX +",downY="+ mDownY);

                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = ev.getX();
                float moveY = ev.getY();

                Log.d(TAG,"滑动中:downx="+ mDownX +",downY="+ mDownY);

                if(Math.abs(moveX- mDownX) > Math.abs(moveY- mDownY)){
                    //水平方向滑动
                    if(currentItem==0 && moveX> mDownX){
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }else if(currentItem==count-1 && moveX< mDownX){
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }else {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                }else{
                    //竖直方向滑动,父控件需要拦截事件自行处理
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        return super.dispatchTouchEvent(ev);
    }

}
