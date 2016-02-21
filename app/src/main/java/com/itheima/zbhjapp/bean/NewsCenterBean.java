package com.itheima.zbhjapp.bean;

import java.util.List;

/**
 * @Author: LiuKe
 * @创建时间: 2016/2/18
 * @描述: TODO
 */
public class NewsCenterBean {
    public List<NewsCenterMenu> data	;//Array
    public  List extend	;//Array
    public int retcode	;//200

    public class NewsCenterMenu {
        public List<NewsCenterChild> children;
        public String id;
        public String title;
        public int type;
        public String url;
        public String url1;
        public String dayu;
        public String excu;
        public String weekurl;
    }

    public class NewsCenterChild{
        public String id;
        public String title;
        public String type;
        public String url;
    }
}
