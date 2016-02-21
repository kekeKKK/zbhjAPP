package com.itheima.zbhjapp.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.itheima.zbhjapp.R;
import com.itheima.zbhjapp.fragment.ContentFragment;
import com.itheima.zbhjapp.fragment.MenuFragment;
import com.itheima.zbhjapp.utils.DensityUtil;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

/**
 * @Author: LiuKe
 * @创建时间: 2016/2/16
 * @描述: TODO
 */
public class MainUI extends SlidingFragmentActivity{

    private static final String TAG_CONTENT = "content";
    private static final String TAG_MENU = "menu" ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();

        initSlidingMenu();

        initFragment();


    }

    /**
     * 设置content与菜单
     */
    private void initView() {
        //设置内容
        setContentView(R.layout.activity_main_content);
        //设置菜单
        setBehindContentView(R.layout.activity_main_menu);
    }

    /**
     * 设置侧滑菜单相关属性
     */
    private void initSlidingMenu() {
        SlidingMenu menu = getSlidingMenu();

        menu.setBehindWidth(DensityUtil.dp2px(this,180));

        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
       //menu.setTouchModeBehind(SlidingMenu.TOUCHMODE_FULLSCREEN);设置后菜单栏无法点击
        menu.setMode(SlidingMenu.LEFT);


        menu.setBehindScrollScale(.5f);
        menu.setShadowDrawable(R.drawable.shadow);
        menu.setShadowWidth(DensityUtil.dp2px(this, 15));
        menu.setFadeDegree(.5f);
    }

    /**
     * 用Fragment替换MainUI中的ContentView与MenuView
     * 抽取出来单独实现
     */
    private void initFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        transaction.replace(R.id.main_content, new ContentFragment(), TAG_CONTENT);
        transaction.replace(R.id.main_menu, new MenuFragment(), TAG_MENU);

        transaction.commit();
    }

    /**
     * 获取ContentFragment实例
     * @return
     */
    public ContentFragment getContentFragment(){
        FragmentManager manager = getSupportFragmentManager();
        return (ContentFragment) manager.findFragmentByTag(TAG_CONTENT);
    }

    /**
     * 获取MenuFragment实例
     * @return
     */
    public MenuFragment getMenuFragment(){
        FragmentManager manager = getSupportFragmentManager();
        return (MenuFragment) manager.findFragmentByTag(TAG_MENU);
    }


}
