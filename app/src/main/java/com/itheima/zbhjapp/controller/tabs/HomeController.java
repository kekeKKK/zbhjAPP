package com.itheima.zbhjapp.controller.tabs;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

/**
 * @Author: LiuKe
 * @创建时间: 2016/2/17
 * @描述: TODO
 */
public class HomeController extends TabController {

    public HomeController(Context context) {

        super(context);
    }

    @Override
    public View initContentView(Context context) {

        TextView tv = new TextView(context);
        tv.setText(getClass().getSimpleName());
        tv.setGravity(Gravity.CENTER);

        return tv;
    }

    @Override
    protected void initTitleBar() {
        mIv.setVisibility(View.GONE);
        mTitle.setText("首页");
    }
}
