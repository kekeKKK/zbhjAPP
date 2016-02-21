package com.itheima.zbhjapp.controller.newsmenu.newslist;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.itheima.zbhjapp.R;
import com.itheima.zbhjapp.bean.NewsCenterBean;
import com.itheima.zbhjapp.bean.NewsListBean;
import com.itheima.zbhjapp.controller.newsmenu.MenuController;
import com.itheima.zbhjapp.controller.newsmenu.NewsMenuController;
import com.itheima.zbhjapp.utils.DensityUtil;
import com.itheima.zbhjapp.utils.MyConstants;
import com.itheima.zbhjapp.utils.SpUtil;
import com.itheima.zbhjapp.view.TouchViewPager;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @Author: LiuKe
 * @创建时间: 2016/2/19
 * @描述: 新闻中心菜单中的新闻栏目具体实现类
 */
public class NewsListController extends MenuController implements NewsMenuController.PagerScrollListener {


    private static final String TAG = "NewsListController";
    Context mContext;
    NewsCenterBean.NewsCenterChild mChild;
    @Bind(R.id.newslist_viewpager)
    TouchViewPager mNewslistViewpager;
    @Bind(R.id.newslist_tv_desc)
    TextView mNewslistTvDesc;
    @Bind(R.id.newslist_point_container)
    LinearLayout mNewslistPointContainer;
    @Bind(R.id.newslist_rl)
    RelativeLayout mNewslistRl;

    private List<NewsListBean.NewsListNewsBean> mNews;
    private List<NewsListBean.NewsListTopicBean> mTopic;
    private List<NewsListBean.NewsListTopNewsBean> mTopnews;
    private TopNewsAutoLoop mAutoLoop;
    private ListView mNewsListListview;

    public NewsListController(Context context, NewsCenterBean.NewsCenterChild child) {
        super(context);
        mContext = context;
        mChild = child;
    }

    @Override
    protected View initView(Context context) {
        View headView = View.inflate(context, R.layout.header_topnews, null);
        View listView = View.inflate(context, R.layout.menu_newscenter_newslist, null);

        ButterKnife.bind(this, headView);

        mNewsListListview = (ListView) listView.findViewById(R.id.newslist_listview);


        //给ListView添加头
        mNewsListListview.addHeaderView(mNewslistRl);

        return listView;
    }

    @Override
    public void initData() {
        final String url = MyConstants.URL.BASEURL + mChild.url;
        //加载缓存数据
        String cacheData = SpUtil.getString(mContext, url, null);
        if (!TextUtils.isEmpty(cacheData)) {

            processData(cacheData);
        }

        //网络请求数据
        Volley.newRequestQueue(mContext).add(new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //缓存数据
                SpUtil.putString(mContext, url, response);

                //解析数据
                processData(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, "网络异常", Toast.LENGTH_SHORT).show();
            }
        }));
    }

    /**
     * 解析从服务器请求来的数据,只有当数据全部解析完毕才可以进行其他的操作
     *
     * @param response 请求的数据
     */
    private void processData(String response) {
        Gson gson = new Gson();
        NewsListBean bean = gson.fromJson(response, NewsListBean.class);
        mNews = bean.data.news;
        mTopic = bean.data.topic;
        mTopnews = bean.data.topnews;

        //动态添加小圆点
        addTopNewsDots();

        //设置Adapter
        mNewslistViewpager.setAdapter(new TopNewsAdapter());

        //设置轮播图滑动监听
        mNewslistViewpager.addOnPageChangeListener(new TopNewsPageChangeListener());

        //设置ViewPager图片自动轮播
        setViewPagerAutoLoop();

        //设置ListView
        iniListView();
    }

    /**
     * 新闻列表的ListView初始化
     */
    private void iniListView() {
        mNewsListListview.setAdapter(new NewsAdapter());
    }

    public class NewsAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (mNews != null) {
                return mNews.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            if (mNews != null) {
                return mNews.get(position);
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(mContext, R.layout.item_newslist, null);
                holder.ivIcon = (ImageView) convertView.findViewById(R.id.item_newslist_icon);
                holder.tvTitle = (TextView) convertView.findViewById(R.id.item_newslist_title);
                holder.tvDate = (TextView) convertView.findViewById(R.id.item_newslist_date);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            NewsListBean.NewsListNewsBean bean = mNews.get(position);
            String iconUrl = bean.listimage;
            iconUrl = iconUrl.replace(MyConstants.URL.OLDBASEURL, MyConstants.URL.BASEURL);
            Picasso.with(mContext)
                    .load(iconUrl)
                    .placeholder(R.mipmap.pic_item_list_default)
                    .error(R.mipmap.pic_item_list_default)
                    .into(holder.ivIcon);
            mNews.get(position);
            holder.tvTitle.setText(bean.title);
            holder.tvDate.setText(bean.pubdate);

            return convertView;
        }

        private class ViewHolder {
            ImageView ivIcon, ivComment;
            TextView tvTitle, tvDate;
        }
    }

    /**
     * TopNews轮播图自动轮播设置
     */
    private void setViewPagerAutoLoop() {
        if (mAutoLoop == null) {

            mAutoLoop = new TopNewsAutoLoop();
        }
        mAutoLoop.startLoop();

        mNewslistViewpager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mAutoLoop.stopLoop();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_UP:
                        mAutoLoop.startLoop();
                        break;
                }
                return false;
            }
        });
    }

    /**
     * 滑动外围的Viewpager后放开,内部的ViewPager轮播图重新开始轮播
     * onPageScrollStateChanged中
     */

    @Override
    public void startScroll() {
        if (mAutoLoop == null) {
            mAutoLoop = new TopNewsAutoLoop();
        }
        mAutoLoop.startLoop();
    }

    @Override
    public void stopScroll() {
        if (mAutoLoop != null) {
            mAutoLoop.stopLoop();
        }
    }


    /**
     * 设置控制ViewPager图片自动滚动与否的类
     */
    private class TopNewsAutoLoop extends Handler implements Runnable {

        public void startLoop() {
            //开始前将handler中所有的消息清空
            removeCallbacksAndMessages(null);
            postDelayed(this, 2000);
        }

        public void stopLoop() {
            removeCallbacksAndMessages(null);
        }

        @Override
        public void run() {
            int currentItem = mNewslistViewpager.getCurrentItem();
            int count = mNewslistViewpager.getAdapter().getCount();
            currentItem = (currentItem + 1) % count;
            mNewslistViewpager.setCurrentItem(currentItem);
            //循环轮播
            postDelayed(this, 2000);
        }
    }


    /**
     * 给轮播图动态添加小圆点
     */
    private void addTopNewsDots() {
        mNewslistPointContainer.removeAllViews();
        //动态添加小圆点
        for (int i = 0; i < mTopnews.size(); i++) {
            ImageView iv = new ImageView(mContext);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dp2px(mContext, 10), DensityUtil.dp2px(mContext, 10));

            if (i != 0) {
                params.leftMargin = DensityUtil.dp2px(mContext, 10);
                iv.setImageResource(R.mipmap.dot_normal);
            } else {
                iv.setImageResource(R.mipmap.dot_focus);
                mNewslistTvDesc.setText(mTopnews.get(0).title);
            }

            iv.setLayoutParams(params);
            mNewslistPointContainer.addView(iv);
        }
    }

    public class TopNewsAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mTopnews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //动态轮播的都是图片
            ImageView iv = new ImageView(mContext);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            String imageUrl = mTopnews.get(position).topimage;
            //http://10.0.2.2:8080/zhbj/10007/1452327318UU91.jpg
            imageUrl = imageUrl.replace(MyConstants.URL.OLDBASEURL, MyConstants.URL.BASEURL);
            Picasso.with(mContext).load(imageUrl).into(iv);
            container.addView(iv);
            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    public class TopNewsPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            String desc = mTopnews.get(position).title;
            mNewslistTvDesc.setText(desc);

            //遍历小圆点并将改变当前的小圆点
            for (int i = 0; i < mNewslistPointContainer.getChildCount(); i++) {
                ImageView iv = (ImageView) mNewslistPointContainer.getChildAt(i);
                iv.setImageResource(i == position ? R.mipmap.dot_focus : R.mipmap.dot_normal);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
