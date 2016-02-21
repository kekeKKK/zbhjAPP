package com.itheima.zbhjapp.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.itheima.zbhjapp.R;
import com.itheima.zbhjapp.bean.NewsCenterBean;
import com.itheima.zbhjapp.ui.MainUI;
import com.itheima.zbhjapp.utils.DensityUtil;

import java.util.List;

/**
 * @Author: LiuKe
 * @创建时间: 2016/2/17
 * @描述: TODO
 */
public class MenuFragment extends BaseFragment {

    private ListView mLv;

    /*当前选中的item*/
    private int mCurrSelectedIndex;

    private List<NewsCenterBean.NewsCenterMenu> mMenuDatas;
    private MenuAdapter mMenuAdapter;

    @Override
    public View initView(Context context) {
        //提供视图
        View view = View.inflate(context, R.layout.fragment_main_menu,null);
        mLv = (ListView) view.findViewById(R.id.menu_listview);

        return view;
    }

    /**
     * 从新闻中心获取菜单栏数据并绑定到UI
     * @param menuData
     */
    public void setData(List<NewsCenterBean.NewsCenterMenu> menuData) {
        mMenuDatas = menuData;
        mMenuAdapter = new MenuAdapter();
        mLv.setAdapter(mMenuAdapter);
        mLv.setSelector(new ColorDrawable(Color.TRANSPARENT));
        mLv.setCacheColorHint(Color.TRANSPARENT);
    }

    @Override
    public void initListener() {
        super.initListener();
        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //1.slidingmenu关闭
                ((MainUI)mContext).getSlidingMenu().toggle();

                if(mCurrSelectedIndex == position){
                    //点击界面就是当前的item,直接返回
                    return;
                }

                //2.item颜色变化
                mCurrSelectedIndex = position;
                mMenuAdapter.notifyDataSetChanged();

                //3.新闻中心界面切换

                //获取ContentFragment
                ContentFragment contentFragment = ((MainUI) mContext).getContentFragment();
                contentFragment.switchContentByContentFragment(position);
            }
        });
    }

    public class MenuAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            if(mMenuDatas != null){
                return mMenuDatas.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            if(mMenuDatas != null){
                return mMenuDatas.get(position);
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
            if(convertView == null){
                holder = new ViewHolder();
                convertView = View.inflate(mContext,R.layout.item_menu,null);
                holder.tv = (TextView) convertView;
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }

            NewsCenterBean.NewsCenterMenu newsCenterMenu = mMenuDatas.get(position);

            holder.tv.setText(newsCenterMenu.title);

            holder.tv.setPadding(DensityUtil.dp2px(mContext, 15), DensityUtil.dp2px(mContext, 80), 0, 0);

            holder.tv.setEnabled(mCurrSelectedIndex == position);

            return convertView;
        }

        class ViewHolder{
            TextView tv;
        }
    }
}
