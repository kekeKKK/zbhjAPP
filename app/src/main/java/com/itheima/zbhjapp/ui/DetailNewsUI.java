package com.itheima.zbhjapp.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.itheima.zbhjapp.R;
import com.itheima.zbhjapp.utils.MyConstants;
import com.itheima.zbhjapp.utils.SpUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @作者: LiuKe
 * @创建时间: 2016/2/21
 * @描述:
 */
public class DetailNewsUI extends Activity {

    public static final String TAG = "DetailNewsUI";
    @Bind(R.id.detail_news_webview)
    WebView mDetailNewsWebview;
    private WebSettings mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_news_ui);

        ButterKnife.bind(this);

        initWebView();

    }

    private void initWebView() {
        Intent intent = getIntent();
        String url = intent.getStringExtra(MyConstants.DETAILEDNEWSURL);
        url = url.replace(MyConstants.URL.OLDBASEURL, MyConstants.URL.BASEURL);

        mDetailNewsWebview.loadUrl(url);

        mDetailNewsWebview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Log.d(TAG, "页面加载开始");
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d(TAG, "页面加载结束");
                super.onPageFinished(view, url);
            }
        });
        mDetailNewsWebview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                Log.d(TAG, "加载进度:" + newProgress);
            }
        });

        //开启WebView的设定
        mSettings = mDetailNewsWebview.getSettings();
        mSettings.setJavaScriptEnabled(true);

        //获取字体大小,默认正常字体
        mCheckedId = SpUtil.getInt(this, MyConstants.TEXTSIZE, 2);
        processTextSize();


    }

    private String[] mItems = {
            "超大号字体",
            "大字体",
            "正常字体",
            "小号字体",
            "超小号字体"
    };
    private int mCheckedId;

    /**
     * ImageView的点击事件处理
     *
     * @param view
     */
    @OnClick({R.id.detail_news_back, R.id.detail_news_share, R.id.detail_news_textsize})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.detail_news_back:
                finish();
                break;
            case R.id.detail_news_share:
                Toast.makeText(this, "点击分享", Toast.LENGTH_SHORT).show();
                break;
            case R.id.detail_news_textsize:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("选择字体")
                        .setSingleChoiceItems(mItems, mCheckedId, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mCheckedId = which;
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                processTextSize();

                                //将选中的字体保存
                                SpUtil.putInt(DetailNewsUI.this,MyConstants.TEXTSIZE,mCheckedId);
                            }
                        })
                        .show();

                break;
        }
    }

    private void processTextSize() {
        switch (mCheckedId) {
            case 0:   //超大号字体
               mSettings.setTextZoom(200);
                break;
            case 1:
                mSettings.setTextZoom(150);
                break;
            case 2:
                mSettings.setTextZoom(100);
                break;
            case 3:
                mSettings.setTextZoom(50);
                break;
            case 4:
                mSettings.setTextZoom(25);
                break;
        }
    }
}
