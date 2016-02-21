package com.itheima.zbhjapp.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.itheima.zbhjapp.controller.tabs.TabController;

import java.util.List;

/**
 * @Author: LiuKe
 * @创建时间: 2016/2/17
 * @描述: TODO
 */
public class ContentViewPagerAdapter extends PagerAdapter {

    private static final String TAG = "ContentViewPagerAdapter";
    List<TabController> mPagerData;

    public ContentViewPagerAdapter(List<TabController> list) {
        mPagerData = list;
    }

    @Override
    public int getCount() {
        if(mPagerData != null){

            return mPagerData.size();
        }

        return  0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

    //    Log.d(TAG,"ViewPager加载:"+position);

        TabController tabController = mPagerData.get(position);
        View rootView = tabController.mRootView;

        //提供视图
        container.addView(rootView);

        //加载数据
        tabController.initData();

        return rootView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
     //   Log.d(TAG,"ViewPager销毁:"+position);
        container.removeView((View) object);
    }
}
