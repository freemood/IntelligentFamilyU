package com.Intelligent.FamilyU.model.shopping.fragment;

import android.app.AlertDialog;
import android.content.ContentQueryMap;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.Intelligent.FamilyU.R;
import com.Intelligent.FamilyU.base.BaseFragment;
import com.Intelligent.FamilyU.base.Constants;
import com.Intelligent.FamilyU.model.gateway.activity.GatewayDetailActivity;
import com.Intelligent.FamilyU.model.gateway.activity.GatewayDetailInfoNameActivity;
import com.Intelligent.FamilyU.model.gateway.entity.GatewayEventBus;
import com.Intelligent.FamilyU.model.main.MainActivity;
import com.Intelligent.FamilyU.utils.ToastUtils;
import com.Intelligent.FamilyU.utils.Utils;
import com.Intelligent.FamilyU.view.IBaseView;
import com.Intelligent.FamilyU.widget.RLoadingDialog;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 商城ragment
 *
 * @author ZhongDaFeng
 */
public class HomeShopingFragment extends BaseFragment implements IBaseView {
    @BindView(R.id.main_web)
    WebView webView;
    private RLoadingDialog mLoadingDialog;


    @Override
    protected void initView() {
        mLoadingDialog = new RLoadingDialog(mContext, true);
        webView.setWebChromeClient(webChromeClient);
        webView.setWebViewClient(webViewClient);
//        webView.loadUrl("file:///android_asset/html5test.html");http://172.30.31.4:8848/store/index.h
//        webView.loadUrl("http://192.168.2.107:8848/store/index.html");
//        webView.loadUrl("http://172.23.81.4:8848/store/index.html");http://192.168.2.107:8848/store/index.html
        //http://39.104.124.86:80/store/index.html
        webView.loadUrl("http://39.104.124.86:80/store/index.html");
//        http://127.0.0.1:8848/store/index.htmlah ao
        //_plugin为与JS交互的插件类，H5Plugin为JS中的调用原生方法的插件名称
        webView.addJavascriptInterface(new H5Plugin(), "H5Plugin");

        // 设置浏览器属性
        WebSettings settings = webView.getSettings();
        settings.setDefaultTextEncodingName("utf-8"); // 默认编码
        settings.setJavaScriptEnabled(true); // JS 交互
        settings.setLoadsImagesAutomatically(true);//设置自动加载图片
        settings.setUseWideViewPort(true);//可任意比例缩放
        settings.setLoadWithOverviewMode(true);
        settings.setAllowFileAccessFromFileURLs(true);

        // 缓存设置
        String cacheDir = mContext.getCacheDir().getAbsolutePath();
        settings.setAppCachePath(cacheDir);
        settings.setAppCacheEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setAllowContentAccess(true);
        settings.setAllowFileAccess(true);

        // 数据库设置
        settings.setDatabaseEnabled(true);
        if (android.os.Build.VERSION.SDK_INT < 18) {
            settings.setDatabasePath(cacheDir);
        }
        // 定位设置
        settings.setGeolocationEnabled(true);
        settings.setGeolocationDatabasePath(cacheDir);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        webView.setLongClickable(false);
        webView.requestFocusFromTouch();

    }

    @Override
    protected void initData() {

    }

    /**
     * 尝试提交
     */
    private void attemptSubmit() {
//        String phone = etPhone.getText().toString();
//        if (TextUtils.isEmpty(phone)) {
//            Toast.makeText(mContext, getString(R.string.hint_phone), Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        mPhonePst.phoneQuery(phone);

    }


    @Override
    public void showLoading() {
        mLoadingDialog.show();
    }

    @Override
    public void closeLoading() {
        mLoadingDialog.dismiss();
    }

    @Override
    public void showToast(String msg) {
        ToastUtils.showToast(mContext, msg);
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        Log.i("ansen", "是否有上一个页面:" + webView.canGoBack());
//        if (webView.canGoBack() && keyCode == KeyEvent.KEYCODE_BACK) {//点击返回按钮的时候判断有没有上一页
//            webView.goBack(); // goBack()表示返回webView的上一页面
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    /**
     * JS调用android的方法
     *
     * @param str
     * @return
     */
    @JavascriptInterface //仍然必不可少
    public void getClient(String str) {
        Log.i("ansen", "html调用客户端:" + str);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != webView) {

            //释放资源
            webView.destroy();
            webView = null;
        }
    }

    @Override
    protected View getContentView() {
        return LayoutInflater.from(mContext).inflate(R.layout.activity_main_shopping, null);
    }

    @Override
    protected void initBundleData() {

    }

    //WebViewClient主要帮助WebView处理各种通知、请求事件
    private WebViewClient webViewClient = new WebViewClient() {
        @Override
        public void onPageFinished(WebView view, String url) {//页面加载完成
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {//页面开始加载
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
            if (Build.VERSION.SDK_INT < 26) {
                view.loadUrl(url);
                return true;
            }
            return false;
        }

    };

    //WebChromeClient主要辅助WebView处理Javascript的对话框、网站图标、网站title、加载进度等
    private WebChromeClient webChromeClient = new WebChromeClient() {
        //不支持js的alert弹窗，需要自己监听然后通过dialog弹窗
        @Override
        public boolean onJsAlert(WebView webView, String url, String message, JsResult result) {
            AlertDialog.Builder localBuilder = new AlertDialog.Builder(webView.getContext());
            localBuilder.setMessage(message).setPositiveButton("确定", null);
            localBuilder.setCancelable(false);
            localBuilder.create().show();

            //注意:
            //必须要这一句代码:result.confirm()表示:
            //处理结果为确定状态同时唤醒WebCore线程
            //否则不能继续点击按钮
            result.confirm();
            return true;
        }

        //获取网页标题
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            Log.i("ansen", "网页标题:" + title);
        }

        //加载进度回调
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
        }
    };

    public class H5Plugin {
        //4.4版本以上，本地方法要加上注解@JavascriptInterface，否则会找不到方法。
        @JavascriptInterface
        public void showToast(String toastMsg) {

            Log.i("H5Plugin", "toastMsg:" + toastMsg);
            if (!TextUtils.isEmpty(toastMsg)) {
                Toast.makeText(mContext, toastMsg, Toast.LENGTH_SHORT).show();  Toast.makeText(mContext, toastMsg, Toast.LENGTH_SHORT).show();
            }
        }

        @JavascriptInterface
        public String getAttribute() {
            StringBuffer sb = new StringBuffer();
            sb.append(Utils.readSharedPreferences(getResources().getString(R.string.home_login_name))).append("_").append("serialNo");
            String sn = Utils.readSharedPreferences(sb.toString());
//            String sn= Utils.readSharedPreferences(getResources().getString(R.string.home_network_detail_sn));
         //   Toast.makeText(mContext, sn, Toast.LENGTH_SHORT).show();
            if (!TextUtils.isEmpty(Constants.APP_TOKEN)&&!TextUtils.isEmpty(sn))
            {
              // JS 解析格式 token=value,sn=value
                return  "token="+Constants.APP_TOKEN+",sn="+sn;
            }
            return  null;
        }



    @JavascriptInterface
        public void newACtivity() {
            Log.i("H5Plugin", "newActivity");
            mContext.startActivity(new Intent(mContext, MainActivity.class));
        }

        @JavascriptInterface public void openPlugin() {
            StringBuffer sb = new StringBuffer();
            sb.append(Utils.readSharedPreferences(getResources().getString(R.string.home_login_name))).append("_").append("serialNo");
            String sn = Utils.readSharedPreferences(sb.toString());
            GatewayEventBus mGatewayEventBus = new GatewayEventBus();
            mGatewayEventBus.setSerialNo(sn);
            EventBus.getDefault().postSticky(mGatewayEventBus);
            mContext.startActivity(new Intent(mContext, GatewayDetailActivity.class));
        }

    }

    public WebView getWebView() {
        return webView;
    }

}
