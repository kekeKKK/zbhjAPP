package com.itheima.zbhjapp.controller.tabs;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.itheima.zbhjapp.bean.NewsCenterBean;
import com.itheima.zbhjapp.controller.newsmenu.InteractMenuController;
import com.itheima.zbhjapp.controller.newsmenu.MenuController;
import com.itheima.zbhjapp.controller.newsmenu.NewsMenuController;
import com.itheima.zbhjapp.controller.newsmenu.PicsMenuController;
import com.itheima.zbhjapp.controller.newsmenu.TopicMenuController;
import com.itheima.zbhjapp.fragment.MenuFragment;
import com.itheima.zbhjapp.ui.MainUI;
import com.itheima.zbhjapp.utils.MyConstants;
import com.itheima.zbhjapp.utils.SpUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: LiuKe
 * @创建时间: 2016/2/17
 * @描述: TODO
 */
public class NewsCenterController extends TabController {

    private static final String TAG = "NewsCenterController";

    private FrameLayout mNewsCenterContentContainer;

    private List<MenuController> mMenuControllers = new ArrayList<MenuController>();

    private List<NewsCenterBean.NewsCenterMenu> mNewsCenterMenus;

    public NewsCenterController(Context context) {

        super(context);
    }

    @Override
    public View initContentView(Context context) {

        //新闻中心侧边栏的所有界面都要在这里分别进行展示
        //每次展示一个,用容器进行装载界面
        mNewsCenterContentContainer = new FrameLayout(context);

        return mNewsCenterContentContainer;
    }

    @Override
    protected void initTitleBar() {
        mTitle.setText("新闻中心");
    }

    @Override
    public void initData() {

        //1.实时更新,先加载缓存再向服务器请求数据
        //读取缓存
//        String data = SpUtil.getString(mContext, MyConstants.URL.NEWSCENTERURL, null);
//        if(!TextUtils.isEmpty(data)){
//            processData(data);
//            Log.d(TAG,"**********读取缓存");
//        }

        //2.延时加载,设置时间阀值
        String data = SpUtil.getString(mContext, MyConstants.URL.NEWSCENTERURL, null);
        if(!TextUtils.isEmpty(data)){
            //判断缓存保存时间是否超时
            long saveTime = SpUtil.getLong(mContext,MyConstants.CACHESAVETIME,0);

            if((saveTime+MyConstants.CACHEDATADELAY) < System.currentTimeMillis()){
          //      Log.d(TAG,"缓存数据超时,需要重新请求数据");
                processData(data);
            }else{
            //    Log.d(TAG,"只加载缓存");
                processData(data);
                return;
            }
        }


        loadNetNewsData();


    }

    private void loadNetNewsData() {
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(new StringRequest(Request.Method.GET, MyConstants.URL.NEWSCENTERURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                processData(response);

            //    Log.d(TAG, "新闻数据:" + response);

                //将请求到的新闻数据缓存
                SpUtil.putString(mContext, MyConstants.URL.NEWSCENTERURL, response);
                //保存缓存存储时间
                SpUtil.putLong(mContext,MyConstants.CACHESAVETIME,System.currentTimeMillis());

             //   Log.d(TAG,"=====新闻数据缓存");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, "网络异常", Toast.LENGTH_SHORT).show();
            }
        }));
    }

    /**
     * 解析数据
     *
     * @param data
     */
    private void processData(String data) {
        //数据解析
        Gson gson = new Gson();
        NewsCenterBean newsCenterBean = gson.fromJson(data, NewsCenterBean.class);
        mNewsCenterMenus = newsCenterBean.data;

        //数据加载到侧边栏
        MenuFragment menuFragment = ((MainUI) mContext).getMenuFragment();
        menuFragment.setData(mNewsCenterMenus);

        //给newsCenterContentContainer添加数据
        for (NewsCenterBean.NewsCenterMenu newsCenterMenu : mNewsCenterMenus) {

            int type = newsCenterMenu.type;

            MenuController menuController = null;

            switch (type) {
                case 1:  //新闻菜单
                    menuController = new NewsMenuController(mContext,mNewsCenterMenus);
                    break;
                case 10: //专题菜单
                    menuController = new TopicMenuController(mContext);
                    break;
                case 2:  //组图菜单
                    menuController = new PicsMenuController(mContext);
                    break;
                case 3:  //互动菜单
                    menuController = new InteractMenuController(mContext);
                    break;
            }

            mMenuControllers.add(menuController);
        }
        //默认加载新闻菜单
        switchContent(0);
    }

    /**
     * 点击左侧菜单ListView时候,新闻中心界面相应改变
     * @param position 菜单ListView item position
     */
    @Override
    public void switchContent(int position){

        //设置标题
        mTitle.setText(mNewsCenterMenus.get(position).title);

        //容器中数据清空
        mNewsCenterContentContainer.removeAllViews();

        //提供视图
        MenuController menuController = mMenuControllers.get(position);
        mNewsCenterContentContainer.addView(menuController.mRootView);

        //加载数据
        menuController.initData();

    }


}
