package com.itheima.zbhjapp.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * @Author: LiuKe
 * @创建时间: 2016/2/16
 * @描述: TODO
 */
public class GuideViewPagerAdapter extends PagerAdapter {

    Context mContext;
    List<ImageView> mViews;

    public GuideViewPagerAdapter(Context context, List<ImageView> list) {
        mContext = context;
        mViews = list;
    }

    @Override
    public int getCount() {
        return mViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = mViews.get(position);
        container.addView(view);

        return  view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((View) object);
    }
}
