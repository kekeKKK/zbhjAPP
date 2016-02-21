package com.itheima.zbhjapp.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.itheima.zbhjapp.R;
import com.itheima.zbhjapp.adapter.GuideViewPagerAdapter;
import com.itheima.zbhjapp.utils.DensityUtil;
import com.itheima.zbhjapp.utils.MyConstants;
import com.itheima.zbhjapp.utils.SpUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: LiuKe
 * @创建时间: 2016/2/16
 * @描述: TODO
 */
public class GuideUI extends Activity {

    private ViewPager mViewPager;
    private Button mBtn;
    private LinearLayout mPointContainer;
    private static int[] PICS = {
            R.mipmap.guide_1,
            R.mipmap.guide_2,
            R.mipmap.guide_3
    };
    private List<ImageView> mImageViews = new ArrayList<ImageView>();
    private View mRedPoint;
    private int mSpace;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initData();
        initListener();
    }
    private void initView() {
        setContentView(R.layout.activity_guide_ui);
        mViewPager = (ViewPager) findViewById(R.id.guide_viewpager);
        mBtn = (Button) findViewById(R.id.guide_btn_start);
        mPointContainer = (LinearLayout) findViewById(R.id.guide_point_container);
        mRedPoint = findViewById(R.id.guide_red_point);
    }

    private void initData() {
        //添加普通小圆点
        for (int i = 0; i < PICS.length; i++) {
            View normalPoint = new View(this);
            normalPoint.setBackgroundResource(R.drawable.guide_normal_point_shape);
            int px = DensityUtil.dp2px(this,10);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(px,px);
            if(i != 0){
                params.leftMargin = px;
            }
            normalPoint.setLayoutParams(params);
            mPointContainer.addView(normalPoint);

            //给引导页ViewPager添加数据
            ImageView iv = new ImageView(this);
            iv.setBackgroundResource(PICS[i]);
            mImageViews.add(iv);

        }

        mViewPager.setAdapter(new GuideViewPagerAdapter(this, mImageViews));
    }

    private void initListener() {

        mPointContainer.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //layout变化只监听一次
                mPointContainer.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                mSpace = mPointContainer.getChildAt(1).getLeft() - mPointContainer.getChildAt(0).getLeft();
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                int distance = Math.round((positionOffset + position) * mSpace);
                //   mRedPoint.setLeft(distance);红点位置不变化
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mRedPoint.getLayoutParams();
                params.leftMargin = distance;
                mRedPoint.setLayoutParams(params);
            }

            @Override
            public void onPageSelected(int position) {
                if (position == mImageViews.size() - 1) {
                    mBtn.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpUtil.putBoolean(GuideUI.this, MyConstants.ISFIRSTGUIDE,false);
                startActivity(new Intent(GuideUI.this,MainUI.class));
                finish();
            }
        });
    }


}