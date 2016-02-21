package com.itheima.zbhjapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import com.itheima.zbhjapp.R;
import com.itheima.zbhjapp.utils.MyConstants;
import com.itheima.zbhjapp.utils.SpUtil;

public class SplashUI extends AppCompatActivity {

    private RelativeLayout mSplashRl;
    private AnimationSet mSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_ui);

        initView();
        startAnimation();
        initListener();
    }

    private void initView() {
        mSplashRl = (RelativeLayout) findViewById(R.id.splash_rl);
    }

    private void startAnimation() {
        mSet = new AnimationSet(false);

        RotateAnimation ra = new RotateAnimation(0,360,
                Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        ra.setDuration(1500);

        ScaleAnimation sa = new ScaleAnimation(0,1,0,1,
                Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        sa.setDuration(1500);

        AlphaAnimation aa = new AlphaAnimation(0,1);
        aa.setDuration(1500);

        mSet.addAnimation(ra);
        mSet.addAnimation(sa);
        mSet.addAnimation(aa);

        mSplashRl.startAnimation(mSet);

    }

    private void initListener() {
        mSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                boolean isFirstGuide = SpUtil.getBoolean(SplashUI.this, MyConstants.ISFIRSTGUIDE, true);
                if(isFirstGuide){
                    startActivity(new Intent(SplashUI.this,GuideUI.class));
                }else {

                    startActivity(new Intent(SplashUI.this,MainUI.class));

                }

                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }
}
