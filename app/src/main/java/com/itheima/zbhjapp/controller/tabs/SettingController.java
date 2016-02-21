package com.itheima.zbhjapp.controller.tabs;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

/**
 * @Author: LiuKe
 * @创建时间: 2016/2/17
 * @描述: TODO
 */
public class SettingController extends TabController {

    public SettingController(Context context) {

        super(context);
    }

    @Override
    public View initContentView(Context context) {

        TextView tv = new TextView(context);
        tv.setText(getClass().getSimpleName());

        return tv;
    }

    @Override
    protected void initTitleBar() {
        mIv.setVisibility(View.GONE);
        mTitle.setText("设置");

    }
}
