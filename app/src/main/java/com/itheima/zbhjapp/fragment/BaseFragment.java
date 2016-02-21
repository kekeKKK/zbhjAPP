package com.itheima.zbhjapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @Author: LiuKe
 * @创建时间: 2016/2/17
 * @描述: 1.抽取基类,放置公共方法与共享参数
 *        2.子类不必关注生命周期,只需要实现功能逻辑即可
 *        3.父类定义抽象方法强制子类实现
 */
public abstract class BaseFragment extends Fragment {

    Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return  initView(mContext);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initData();
        initListener();
    }


    /**
     * 相关参数初始化
     */
    protected void init(){
        mContext = getActivity();
    }


    /**
     * 初始化监听事件
     */
    public void initListener(){

    }

    /**
     * 初始化数据,需要自己去请求并加载数据
     */
    public  void initData() {
    }

    /**
     * 初始化View,子类必须实现
     * @return
     */
    public abstract View initView(Context context);



}
