package com.itheima.zbhjapp.bean;

import java.util.List;

/**
 * @作者: LiuKe
 * @创建时间: 2016/2/21
 * @描述:
 */
public class PicsBean {
    public PicData data;   //Object
    public int retcode;  //	200

    public class PicData {
        public String countcommenturl;  //	http://zhbj.qianlong.com/client/content/countComment/
        public String more;    //http://zhbj.qianlong.com/static/api/news/10003/list_2.json
        public String title;   //组图
        public List topic;   //Array
        public List<PicNews> news;   //Array
    }

    public class PicNews {
        public String comment;  //true
        public String commentlist;  //	http://zhbj.qianlong.com/static/api/news/10003/09/76909/comment_1.json
        public String commenturl;  //http://zhbj.qianlong.com/client/user/newComment/76909
        public String id;  //76909
        public String largeimage;  //http://zhbj.qianlong.com/static/images/2014/10/15/66/15917789896YFY.jpg
        public String listimage;  //	http://10.0.2.2:8080/zhbj/photos/images/1571461527JCBR.jpg
        public String pubdate;  //2014-10-15 19:25
        public String smallimage;  //http://zhbj.qianlong.com/static/images/2014/10/15/93/1592702510DM5B.jpg
        public String title;  //世界粮食日：饥饿的威胁
        public String type;  //news
        public String url;  //http://zhbj.qianlong.com/static/html/2014/10/15/72496D514D661071682A.html
    }


}
