package com.itheima.zbhjapp.controller.newsmenu;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.itheima.zbhjapp.R;
import com.itheima.zbhjapp.bean.PicsBean;
import com.itheima.zbhjapp.controller.tabs.NewsCenterController;
import com.itheima.zbhjapp.utils.MyConstants;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @Author: LiuKe
 * @创建时间: 2016/2/18
 * @描述: TODO
 */
public class PicsMenuController extends MenuController implements NewsCenterController.iconClickListener {

    @Bind(R.id.pics_list)
    ListView mPicsList;
    @Bind(R.id.pics_grid)
    GridView mPicsGrid;


    private List<PicsBean.PicNews> mPicNews;

    private boolean isListView = true;


    public PicsMenuController(Context context) {
        super(context);
    }

    @Override
    protected View initView(Context context) {

        View view = View.inflate(context, R.layout.pics_list_or_grid, null);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void initData() {

        String url = MyConstants.URL.BASEURL+"/photos/photos_1.json";
        Volley.newRequestQueue(mContext).add(new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                processData(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, "网络问题", Toast.LENGTH_SHORT).show();
            }
        }));
    }

    private void processData(String response) {
        Gson gson = new Gson();
        PicsBean picsBean = gson.fromJson(response, PicsBean.class);
        mPicNews = picsBean.data.news;

        PicAdapter adapter = new PicAdapter();
        mPicsList.setAdapter(adapter);
        mPicsGrid.setAdapter(adapter);
    }

    /**
     * 图标点击的回调
     * @param iv
     */
    @Override
    public void click(ImageView iv) {


            iv.setImageResource(isListView ? R.mipmap.icon_pic_list_type : R.mipmap.icon_pic_grid_type);
            mPicsList.setVisibility(isListView ? View.GONE : View.VISIBLE);
            mPicsGrid.setVisibility(isListView ? View.VISIBLE : View.GONE);
            isListView = !isListView;



    }

    public class PicAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            if(mPicNews != null){
                return  mPicNews.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            if(mPicNews != null){
                return  mPicNews.get(position);
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
                convertView = View.inflate(mContext,R.layout.item_men_pics,null);
                holder = new ViewHolder();
                holder.photo = (ImageView) convertView.findViewById(R.id.item_pics_photo);
                holder.title = (TextView) convertView.findViewById(R.id.item_pics_title);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }

            String imageUrl = mPicNews.get(position).listimage;
            String title = mPicNews.get(position).title;
            imageUrl = imageUrl.replace(MyConstants.URL.OLDBASEURL,MyConstants.URL.BASEURL);

            Picasso.with(mContext).load(imageUrl).into(holder.photo);
            holder.title.setText(title);

            return convertView;
        }

        private class ViewHolder{
            ImageView photo;
            TextView title;
        }
    }
}
