package com.itheima.zbhjapp.controller.newsmenu;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.itheima.zbhjapp.R;
import com.itheima.zbhjapp.bean.NewsCenterBean;
import com.itheima.zbhjapp.controller.newsmenu.newslist.NewsListController;
import com.itheima.zbhjapp.ui.MainUI;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @Author: LiuKe
 * @创建时间: 2016/2/18
 * @描述: TODO
 */
public class NewsMenuController extends MenuController implements View.OnClickListener {

    private final List<NewsCenterBean.NewsCenterChild> mChildren;

    @Bind(R.id.menu_news_tab)
    TabPageIndicator mTabs;
    @Bind(R.id.menu_news_viewpager)
    ViewPager mViewPager;
    @Bind(R.id.menu_news_arr)
    ImageButton mIbArr;

    public NewsMenuController(Context context, List<NewsCenterBean.NewsCenterMenu> newsCenterMenus) {

        super(context);

        mChildren = newsCenterMenus.get(0).children;
    }

    @Override
    protected View initView(Context context) {
        View view = View.inflate(context, R.layout.menu_newscenter_news, null);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void initData() {
        //adapter设置数据
        mViewPager.setAdapter(new NewsMenuPagerAdapter());
        //TabIndicator绑定ViewPager
        mTabs.setViewPager(mViewPager);

        //ViewPager切换效果
        //mTabs.setOnPageChangeListener(new NewsMenuPagerChangeListener());  ok
        mViewPager.addOnPageChangeListener(new NewsMenuPagerChangeListener());

        //点击箭头切换页签
        mIbArr.setOnClickListener(this);

    }

    public class NewsMenuPagerChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            SlidingMenu slidingMenu = ((MainUI) mContext).getSlidingMenu();

            slidingMenu.setTouchModeAbove(position == 0 ? SlidingMenu.TOUCHMODE_FULLSCREEN : SlidingMenu.TOUCHMODE_NONE);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

            switch (state) {
                case ViewPager.SCROLL_STATE_IDLE:
                    //滑动停止后,轮播图继续轮播
                    notifyPagerIdle();
                 //   Log.d("NewsListController","父控件Idlel .......");
                    break;
                case ViewPager.SCROLL_STATE_DRAGGING:
                    //滑动开始,轮播图停止轮播
                    notifyPagerDragging();
                    break;
            }

        }
    }

    /**接口编程用于通过内侧的轮播图ViewPager滑动父控件的ViewPager的回调*/
    private List<PagerScrollListener> mListeners = new ArrayList<PagerScrollListener>();

    public interface PagerScrollListener {
        void startScroll();
        void stopScroll();
    }

    public void addOnPagerScrollListener(PagerScrollListener listener){
        if(!mListeners.contains(listener)){

            mListeners.add(listener);
        }
    }
    public void removeOnPagerScrollListener(PagerScrollListener listener){
        if(mListeners != null){

            mListeners.remove(listener);
        }
    }

    public void notifyPagerIdle(){
        //遍历所有的PagerScrollListener实现类并调用方法
        ListIterator<PagerScrollListener> iterator = mListeners.listIterator();
        while(iterator.hasNext()){
            PagerScrollListener listener = iterator.next();
            listener.startScroll();
        }
    }

    public void notifyPagerDragging(){
        //遍历所有的PagerScrollListener实现类并调用停止轮播方法
        ListIterator<PagerScrollListener> iterator = mListeners.listIterator();
        while(iterator.hasNext()){
            PagerScrollListener listener = iterator.next();
            listener.stopScroll();
        }
    }


    public class NewsMenuPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            if (mChildren != null) {
                return mChildren.size();
            }
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            NewsCenterBean.NewsCenterChild newsCenterChild = mChildren.get(position);
            //提供视图,并将请求到的数据传递给拆分出来的Controller
            NewsListController controller = new NewsListController(mContext,newsCenterChild);
//            View rootView = controller.mRootView;
            View rootView = controller.getRootView();
            container.addView(rootView);

            addOnPagerScrollListener(controller);

            rootView.setTag(controller);

            //加载数据
            controller.initData();

            return  rootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);

            View rootView = (View) object;

            NewsListController controller = (NewsListController) rootView.getTag();

            removeOnPagerScrollListener(controller);
        }

        /**
         * 设置TabIndicator标题
         * @param position
         * @return
         */
        @Override
        public CharSequence getPageTitle(int position) {
            return mChildren.get(position).title;
        }
    }

    @Override
    public void onClick(View v) {
        int currentItem = mViewPager.getCurrentItem();
        if(currentItem != mChildren.size()-1){
            mViewPager.setCurrentItem(currentItem+1);
        }
    }


}
