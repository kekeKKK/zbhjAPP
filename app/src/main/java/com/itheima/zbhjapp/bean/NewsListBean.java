package com.itheima.zbhjapp.bean;

import java.util.List;

/**
 * @Author: LiuKe
 * @创建时间: 2016/2/19
 * @描述: TODO
 */
public class NewsListBean {
    public NewsListDataBean data;
    public int retcode;

    public class NewsListDataBean{
        public String countcommenturl	;
        public String more	;
        public String title	;
        public List<NewsListNewsBean> news	;
        public List<NewsListTopicBean> topic	;
        public List<NewsListTopNewsBean> topnews;
    }

    public class NewsListNewsBean{
        public boolean comment;
        public String commentlist;
        public String commenturl;
        public long id;
        public String listimage;
        public String pubdate;
        public String title	;
        public String type;
        public String url;
    }

    public class NewsListTopicBean{
        public String description;
        public String id;
        public String listimage	;
        public String sort;
        public String title;
        public String url;
    }

    public class NewsListTopNewsBean{
        public boolean comment	;
        public String commentlist;
        public String commenturl;
        public long id;
        public String pubdate	;
        public String title;
        public String topimage;
        public String type;
        public String url;
    }


}
