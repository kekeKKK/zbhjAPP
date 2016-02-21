package com.itheima.zbhjapp.controller.newsmenu;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

/**
 * @Author: LiuKe
 * @创建时间: 2016/2/18
 * @描述: TODO
 */
public class PicsMenuController extends MenuController {

    public PicsMenuController(Context context) {
        super(context);
    }

    @Override
    protected View initView(Context context) {

        TextView tv = new TextView(context);
        tv.setText(getClass().getSimpleName());
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(Color.BLUE);
        tv.setTextSize(20);


        return tv;
    }
}
