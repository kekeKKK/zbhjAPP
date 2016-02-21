package com.itheima.zbhjapp.utils;

/**
 * @Author: LiuKe
 * @创建时间: 2016/2/16
 * @描述: TODO
 */
public interface MyConstants {

    /**
     * SharedPreference数据保存文件名
     */
    String CONFIGFILE = "guidesetfile";


    /**
     * 缓存数据保存时间
     */
    String CACHESAVETIME = "cachesavetime";

    /**
     * 缓存数据保存时间值,10s后从服务器重新加载数据
     */
    long CACHEDATADELAY = 10*1000;

    /**
     * 引导界面是否这是标识
     */
    String ISFIRSTGUIDE = "isfirstguide";

    public static interface URL{
        String BASEURL =  "http://188.188.5.14:8080/zhbj";
        String NEWSCENTERURL = BASEURL+"/categories.json";
        String OLDBASEURL = "http://10.0.2.2:8080/zhbj";
    }
}
