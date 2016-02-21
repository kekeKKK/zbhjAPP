package com.itheima.zbhjapp.fragment;

import android.content.Context;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.itheima.zbhjapp.R;
import com.itheima.zbhjapp.adapter.ContentViewPagerAdapter;
import com.itheima.zbhjapp.controller.tabs.GovAffairsController;
import com.itheima.zbhjapp.controller.tabs.HomeController;
import com.itheima.zbhjapp.controller.tabs.NewsCenterController;
import com.itheima.zbhjapp.controller.tabs.SettingController;
import com.itheima.zbhjapp.controller.tabs.SmartServiceController;
import com.itheima.zbhjapp.controller.tabs.TabController;
import com.itheima.zbhjapp.ui.MainUI;
import com.itheima.zbhjapp.view.NoScrollViewPager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @Author: LiuKe
 * @创建时间: 2016/2/17
 * @描述: TODO
 */
public class ContentFragment extends BaseFragment {

    @Bind(R.id.content_viewpager)
    public NoScrollViewPager mPager;

    @Bind(R.id.content_rg)
    public RadioGroup mRg;

    @Bind(R.id.content_home_rb)
    public RadioButton mRbHome;

    @Bind(R.id.content_newscenter_rb)
    public RadioButton mRbNewsCenter;

    @Bind(R.id.content_smartservice_rb)
    public RadioButton mRbSmartService;

    @Bind(R.id.content_govaffairs_rb)
    public RadioButton mRbGovAffairs;

    @Bind(R.id.content_setting_rb)
    public RadioButton mRbSetting;

    private List<TabController> mPagerData = new ArrayList<TabController>();

    private int mCurTabIndex;


    @Override
    public View initView(Context context) {
        View view = View.inflate(context, R.layout.fragment_main_content, null);

        ButterKnife.bind(this,view);

        return view;
    }

    @Override
    public void initData() {
       /* for (int i = 0; i < 5; i++) {
            TextView tv = new TextView(mContext);
            tv.setText("内容" + i);
            mPagerData.add(tv);
        }*/
        mPagerData.add(new HomeController(mContext));
        mPagerData.add(new NewsCenterController(mContext));
        mPagerData.add(new SmartServiceController(mContext));
        mPagerData.add(new GovAffairsController(mContext));
        mPagerData.add(new SettingController(mContext));

        //ViewPager设置数据
        mPager.setAdapter(new ContentViewPagerAdapter(mPagerData));

        //RadioGroup默认选中首页
        mRg.check(R.id.content_home_rb);

        //设置首页不可滑动侧边栏
        changSlidingMenuEnable(false);
    }

    @Override
    public void initListener() {
        mRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            boolean flag;

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.content_home_rb:
                        mCurTabIndex = 0;
                        flag = false;
                        break;
                    case R.id.content_newscenter_rb:
                        mCurTabIndex = 1;
                        flag = true;
                        break;
                    case R.id.content_smartservice_rb:
                        mCurTabIndex = 2;
                        flag = true;
                        break;
                    case R.id.content_govaffairs_rb:
                        mCurTabIndex = 3;
                        flag = true;
                        break;
                    case R.id.content_setting_rb:
                        mCurTabIndex = 4;
                        flag = false;
                        break;
                }

                mPager.setCurrentItem(mCurTabIndex);
                changSlidingMenuEnable(flag);

            }
        });
    }

    /**
     * 设置侧滑菜单是否可以滑动
     *
     * @param flag true 可以滑动;false 不能滑动
     */
    public void changSlidingMenuEnable(boolean flag) {
        SlidingMenu menu = ((MainUI) mContext).getSlidingMenu();
        if (flag) {

            menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

        } else {

            menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }
    }

    /**
     * 多个TabController都具有侧边栏,需要先获得具体的Controller
     * 在调用方法切换内容
     *
     * @param position
     */
    public void switchContentByContentFragment(int position) {

        //获取被选择的TabController实例
        TabController controller = mPagerData.get(mCurTabIndex);

        //TabController切换Content内容
        controller.switchContent(position);

    }


}
