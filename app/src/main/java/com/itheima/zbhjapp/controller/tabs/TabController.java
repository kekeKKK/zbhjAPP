package com.itheima.zbhjapp.controller.tabs;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.itheima.zbhjapp.R;
import com.itheima.zbhjapp.ui.MainUI;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

/**
 * @Author: LiuKe
 * @创建时间: 2016/2/17
 * @描述: TODO
 */
public abstract class TabController{

    public View mRootView;
    public ImageView mIv;
    public TextView mTitle;
    public FrameLayout mContentContainer;

    public Context mContext;

    public TabController(Context context) {
        mContext = context;
        mRootView = initView(context);
    }

    /**
     * 抽取子类View共有特性
     * @param context
     * @return
     */
    protected  View initView(Context context){
        mRootView = View.inflate(context, R.layout.layout_tab_controller,null);

        mIv = (ImageView) mRootView.findViewById(R.id.tab_iv);
        mTitle = (TextView) mRootView.findViewById(R.id.tab_title);
        mContentContainer = (FrameLayout) mRootView.findViewById(R.id.tab_content_container);

        //设置标题栏
        initTitleBar();

        //设置栏目内容
        mContentContainer.addView(initContentView(context));

        //设置栏目点击监听事件
        mIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SlidingMenu slidingMenu = ((MainUI) mContext).getSlidingMenu();
                slidingMenu.toggle();

            }
        });

        return mRootView;
    }

    protected abstract View initContentView(Context context);

    protected abstract void initTitleBar();

    public void initData(){}

    protected  void initListener(){}

    public  void switchContent(int position){}

}
