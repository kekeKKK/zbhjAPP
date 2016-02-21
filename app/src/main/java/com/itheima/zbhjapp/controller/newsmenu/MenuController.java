package com.itheima.zbhjapp.controller.newsmenu;

import android.content.Context;
import android.view.View;

/**
 * @Author: LiuKe
 * @创建时间: 2016/2/18
 * @描述: TODO
 */
public abstract class MenuController {

    public Context mContext;
    public View mRootView;

    public MenuController(Context context){
        mContext = context;
        mRootView = initView(context);
    }

    protected abstract  View initView(Context context);

    public void initData(){}

    public View getRootView(){
        return mRootView;
    }


}
