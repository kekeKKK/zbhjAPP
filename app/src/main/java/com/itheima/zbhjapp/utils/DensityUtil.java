package com.itheima.zbhjapp.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * @Author: LiuKe
 * @创建时间: 2016/2/16
 * @描述: TODO
 */
public class DensityUtil {

    public static int dp2px(Context context,int dp){

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int dpi = metrics.densityDpi;
        int px = Math.round(dp * (dpi / 160));
        return  px;
    }

    public static int px2dp(Context context,int px){
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int dpi = metrics.densityDpi;
        return Math.round(px*160/dpi);
    }
}
