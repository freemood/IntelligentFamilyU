package com.Intelligent.FamilyU.model.main;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.Intelligent.FamilyU.R;
import com.Intelligent.FamilyU.base.BaseFragmentActivity;
import com.Intelligent.FamilyU.base.BasePagerAdapter;
import com.Intelligent.FamilyU.base.Constants;
import com.Intelligent.FamilyU.manager.ActivityStackManager;
import com.Intelligent.FamilyU.model.gateway.iface.IAddGetawary;
import com.Intelligent.FamilyU.model.home.activity.DeviceAddActivity;
import com.Intelligent.FamilyU.model.home.entity.WearherBean;
import com.Intelligent.FamilyU.model.home.fragment.HomeFurnishingFragment;
import com.Intelligent.FamilyU.model.message.activity.MessageListActivity;
import com.Intelligent.FamilyU.model.my.fragment.HomeMyFragment;
import com.Intelligent.FamilyU.model.scene.activity.ScenceCustomAddListActivity;
import com.Intelligent.FamilyU.model.scene.fragment.HomeSceneMainFragment;
import com.Intelligent.FamilyU.model.shopping.fragment.HomeShopingFragment;
import com.Intelligent.FamilyU.utils.NetworkUtils;
import com.Intelligent.FamilyU.utils.RxEasyUtils;
import com.Intelligent.FamilyU.utils.ToastUtils;
import com.Intelligent.FamilyU.utils.Utils;
import com.Intelligent.FamilyU.view.CustomViewPager;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.jpushdemo.ExampleUtil;
import com.example.jpushdemo.LocalBroadcastManager;
import com.google.gson.Gson;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 首頁
 */
public class MainActivity extends BaseFragmentActivity {

    private LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    private StringBuffer homeTitle = new StringBuffer();
    private BasePagerAdapter mPagerAdapter;
    private ArrayList<Fragment> mFragmentList = new ArrayList<>();
    private SoftReference<Fragment> mHomeFurnishingFragment = null;
    private SoftReference<Fragment> mHomeSceneFragment = null;
    private SoftReference<Fragment> mHomeShopingFragment = null;
    private SoftReference<Fragment> mHomeMyFragment = null;
    private FragmentManager mFragmentManager = null;
    private Fragment currentFragment = null;
    private long clickTime = 0; // 第一次点击的时间
    private IAddGetawary mIAddGetawary = null;
    private int swichType = 9;


    @BindView(R.id.view_pager)
    CustomViewPager viewPager;
    @BindView(R.id.page_title)
    TextView titleTv;
    @BindView(R.id.page_right_tv)
    TextView pageRightTv;
    @BindView(R.id.page_right_rl)
    RelativeLayout pageRightRl;
    @BindView(R.id.page_cancle)
    TextView pageCancle;
    @BindView(R.id.iv_title)
    ImageView ivTitle;

    @BindView(R.id.home_main_page_iv)
    TextView homeMainPageIv;
    @BindView(R.id.home_main_page_tv)
    TextView homeMainPageTv;
    @BindView(R.id.home_scene_iv)
    TextView homeSceneIv;
    @BindView(R.id.home_scene_tv)
    TextView homeSceneTv;
    @BindView(R.id.home_shopping_iv)
    TextView homeShoppingIv;
    @BindView(R.id.home_shopping_tv)
    TextView homeShoppingTv;
    @BindView(R.id.home_my_iv)
    TextView homeMyIv;
    @BindView(R.id.home_my_tv)
    TextView homeMyTv;
    private static final int MSG_SET_ALIAS = 1001;
    //for receive customer msg from jpush server
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";
    private android.os.Handler mhandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //处理天气
            switch (msg.what) {
                case 0:
                    Map<String, String> map = new HashMap<>();
                    String result = (String) msg.obj;
                    if (TextUtils.isEmpty(result)) {
                        showOldWearther();
                        return;
                    }
                    Gson gson = new Gson();
                    WearherBean mWearherBean = gson.fromJson(result, WearherBean.class);
                    if (null == mWearherBean.getResults()) {
                        showOldWearther();
                        return;
                    }
                    String pmStr = mWearherBean.getResults().get(0).getPm25();
                    int pm = Integer.parseInt(pmStr);
                    String[] pmArray = getResources().getStringArray(R.array.pm_value);
                    String pmValue = "";
                    if (pm <= 50) {
                        pmValue = pmArray[0];
                    } else if (pm <= 100 && pm > 50) {
                        pmValue = pmArray[1];
                    } else if (pm <= 150 && pm > 100) {
                        pmValue = pmArray[2];
                    } else if (pm <= 200 && pm > 150) {
                        pmValue = pmArray[3];
                    } else if (pm <= 300 && pm > 200) {
                        pmValue = pmArray[4];
                    } else {
                        pmValue = pmArray[5];
                    }
                    homeTitle = new StringBuffer();
                    String temperatureStr = mWearherBean.getResults().get(0).getWeather_data().get(0).getDate().replace("(", "").replace(")", "");
                    String[] temperatures = temperatureStr.split("：");
                    map.put("temperatures", "");
                    if (temperatures.length >= 2) {
                        homeTitle.append(temperatures[1]);
                        map.put("temperatures", temperatures[1]);
                    }
                    map.put("pmValue", pmValue);
                    map.put("pm", pmStr);

                    String source = mWearherBean.getResults().get(0).getWeather_data().get(0).getDayPictureUrl();
                    map.put("url", source);
                    homeTitle.append("  ").append(getResources().getString(R.string.home_air_quality)).append(pmValue);
                    Fragment fragment = mHomeFurnishingFragment.get();
                    if (currentFragment == fragment) {
                        Glide.with(mContext).load(source).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).into(ivTitle);
                        ivTitle.setVisibility(View.VISIBLE);
                        titleTv.setText(homeTitle.toString());
                    }
                    Utils.writeSharedPreferences(Constants.WEARTHER_SHAR_NAME, map);
                    break;
                case 99:
                    showOldWearther();
                    break;
                case MSG_SET_ALIAS:
                    // 调用 JPush 接口来设置别名。
                    JPushInterface.setAliasAndTags(MainActivity.this,
                            (String) msg.obj,
                            null,
                            mAliasCallback);
                    break;

            }
        }
    };

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main_new;
    }

    @Override
    protected void initBundleData() {

    }

    @Override
    protected void initView() {

        // 初始化 JPush。如果已经初始化，但没有登录成功，则执行重新登录。
        initJPush();
        registerMessageReceiver();  // used for receive msg
    }

    @Override
    protected void initData() {
        mHomeFurnishingFragment = new SoftReference(new HomeFurnishingFragment());
        mHomeSceneFragment = new SoftReference(new HomeSceneMainFragment());
        mHomeShopingFragment = new SoftReference(new HomeShopingFragment());
        mHomeMyFragment = new SoftReference(new HomeMyFragment());

        mFragmentList.add(mHomeFurnishingFragment.get());
        mFragmentList.add(mHomeSceneFragment.get());
        mFragmentList.add(mHomeShopingFragment.get());
        mFragmentList.add(mHomeMyFragment.get());
        mFragmentManager = getSupportFragmentManager();

        mPagerAdapter = new BasePagerAdapter(mFragmentManager, mFragmentList);
        //设置Adapter
        viewPager.setAdapter(mPagerAdapter);
        viewPager.setScanScroll(false);
        viewPager.setCurrentItem(0);
        currentFragment = mFragmentList.get(0);
        swichType = 0;
        homeTitle.append(getResources().getString(R.string.home_furnishing));
        titleTv.setText(homeTitle.toString());

        pageRightTv.setBackgroundResource(R.mipmap.dlna_saomiao);
        pageCancle.setBackgroundResource(R.mipmap.icon_message);
        pageRightRl.setVisibility(View.VISIBLE);
        ivTitle.setVisibility(View.GONE);

        homeMainPageIv.setBackgroundResource(R.mipmap.home_main_page_on);
        homeSceneIv.setBackgroundResource(R.mipmap.home_scene_off);
        homeShoppingIv.setBackgroundResource(R.mipmap.home_shopping_off);
        homeMyIv.setBackgroundResource(R.mipmap.home_my_off);

        homeMainPageTv.setTextColor(getResources().getColor(R.color.home_black));
        homeSceneTv.setTextColor(getResources().getColor(R.color.home_grgray_1));
        homeShoppingTv.setTextColor(getResources().getColor(R.color.home_grgray_1));
        homeMyTv.setTextColor(getResources().getColor(R.color.home_grgray_1));

        initBauDuMap();
    }

    private void initJPush() {
        JPushInterface.init(getApplicationContext());
    }

    private void initBauDuMap() {
        mLocationClient = new LocationClient(mContext);
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        if (NetworkUtils.isNetworkConnected()) {
            option.setOpenGps(false);
            option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);
        } else {
            option.setOpenGps(true);
        }
        option.setOpenAutoNotifyMode();
        mLocationClient.setLocOption(option);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            mLocationClient.start();
        } else {
            mapRequestPermissions();
        }
    }


    @OnClick({R.id.main_page, R.id.main_scene, R.id.main_shopping, R.id.main_my, R.id.page_back, R.id.page_right_rl})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_page:
                swichType = 0;
                swichTypeFragment(mHomeFurnishingFragment.get(), swichType);
                titleTv.setText(homeTitle.toString());
                pageRightTv.setBackgroundResource(R.mipmap.dlna_saomiao);
                pageRightRl.setVisibility(View.VISIBLE);
                pageCancle.setVisibility(View.VISIBLE);

                homeMainPageIv.setBackgroundResource(R.mipmap.home_main_page_on);
                homeSceneIv.setBackgroundResource(R.mipmap.home_scene_off);
                homeShoppingIv.setBackgroundResource(R.mipmap.home_shopping_off);
                homeMyIv.setBackgroundResource(R.mipmap.home_my_off);

                homeMainPageTv.setTextColor(getResources().getColor(R.color.home_black));
                homeSceneTv.setTextColor(getResources().getColor(R.color.home_grgray_1));
                homeShoppingTv.setTextColor(getResources().getColor(R.color.home_grgray_1));
                homeMyTv.setTextColor(getResources().getColor(R.color.home_grgray_1));
                break;
            case R.id.main_scene:
                swichType = 1;
                swichTypeFragment(mHomeSceneFragment.get(), swichType);
                titleTv.setText(R.string.home_scene);
                ivTitle.setVisibility(View.GONE);
                pageRightTv.setBackgroundResource(R.mipmap.icon_add4);
                pageRightRl.setVisibility(View.VISIBLE);
//                pageRightTv.setBackgroundResource(R.mipmap.icon_add4);
//                pageRightRl.setVisibility(View.VISIBLE);

                homeMainPageIv.setBackgroundResource(R.mipmap.home_main_page_off);
                homeSceneIv.setBackgroundResource(R.mipmap.home_scene_on);
                homeShoppingIv.setBackgroundResource(R.mipmap.home_shopping_off);
                homeMyIv.setBackgroundResource(R.mipmap.home_my_off);

                homeMainPageTv.setTextColor(getResources().getColor(R.color.home_grgray_1));
                homeSceneTv.setTextColor(getResources().getColor(R.color.home_black));
                homeShoppingTv.setTextColor(getResources().getColor(R.color.home_grgray_1));
                homeMyTv.setTextColor(getResources().getColor(R.color.home_grgray_1));
                break;
            case R.id.main_shopping:
                showsShopping();
                break;
            case R.id.main_my:
                swichType = 3;
                swichTypeFragment(mHomeMyFragment.get(), swichType);
                titleTv.setText(R.string.home_my);
                pageRightRl.setVisibility(View.GONE);
                ivTitle.setVisibility(View.GONE);

                homeMainPageIv.setBackgroundResource(R.mipmap.home_main_page_off);
                homeSceneIv.setBackgroundResource(R.mipmap.home_scene_off);
                homeShoppingIv.setBackgroundResource(R.mipmap.home_shopping_off);
                homeMyIv.setBackgroundResource(R.mipmap.home_my_on);

                homeMainPageTv.setTextColor(getResources().getColor(R.color.home_grgray_1));
                homeSceneTv.setTextColor(getResources().getColor(R.color.home_grgray_1));
                homeShoppingTv.setTextColor(getResources().getColor(R.color.home_grgray_1));
                homeMyTv.setTextColor(getResources().getColor(R.color.home_black));
                break;
            case R.id.page_right_rl:
                switch (swichType) {
                    case 0:
                        startActivity(new Intent(mContext, DeviceAddActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(mContext, ScenceCustomAddListActivity.class));
                        break;
                    case 2:

                        break;
                    case 3:
                        break;
                }
                break;
            case R.id.page_back:
                startActivity(new Intent(mContext, MessageListActivity.class));
                break;
        }
    }

    private void showsShopping() {
        swichType = 2;
        swichTypeFragment(mHomeShopingFragment.get(), swichType);
        titleTv.setText(R.string.home_shopping);
        pageRightRl.setVisibility(View.GONE);
        ivTitle.setVisibility(View.GONE);

        homeMainPageIv.setBackgroundResource(R.mipmap.home_main_page_off);
        homeSceneIv.setBackgroundResource(R.mipmap.home_scene_off);
        homeShoppingIv.setBackgroundResource(R.mipmap.home_shopping_on);
        homeMyIv.setBackgroundResource(R.mipmap.home_my_off);

        homeMainPageTv.setTextColor(getResources().getColor(R.color.home_grgray_1));
        homeSceneTv.setTextColor(getResources().getColor(R.color.home_grgray_1));
        homeShoppingTv.setTextColor(getResources().getColor(R.color.home_black));
        homeMyTv.setTextColor(getResources().getColor(R.color.home_grgray_1));
    }

    private void swichTypeFragment(Fragment mFragment, int swichType) {
        if (!mFragment.isAdded()) {
            mFragmentList.set(swichType, mFragment);
        }
        currentFragment = switchFragment(mFragmentManager, currentFragment, mFragment);
        viewPager.setCurrentItem(swichType);
    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取地址相关的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明
            if (TextUtils.isEmpty(location.getCity())) {
                showOldWearther();
                return;
            }

            String city = location.getCity().replace("市", "");    //获取城市
            StringBuffer url = new StringBuffer();
            url.append(Constants.APP_WEARTHER_URL)
                    .append("?location=")
                    .append(city)
                    .append("&output=json&ak=")
                    .append(Constants.APP_BAIDU_AK)
                    .append("&mcode=")
                    .append(Constants.APP_BAIDU_SHA);
            RxEasyUtils.startGetObservable(url.toString(), mhandler);
        }

    }

    private void mapRequestPermissions() {
        // SDK 小于23默认已经授权
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }
        String[] perms = {
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE};
        String[] permsStr = {
                getResources().getString(R.string.home_baidu_location),
                getResources().getString(R.string.home_baidu_file_location),
                getResources().getString(R.string.home_baidu_sdcard),
                getResources().getString(R.string.home_baidu_phoone_state)};
        int length = perms.length;
        for (int i = 0; i < length; i++) {
            if (!EasyPermissions.hasPermissions(mContext, perms)) {
                EasyPermissions.requestPermissions(this, permsStr[i],
                        i, perms);
            }
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, mContext);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
        mLocationClient.start();
    }

    private void showOldWearther() {
        homeTitle = new StringBuffer();
        homeTitle.append(Utils.readSharedPreferences(Constants.WEARTHER_SHAR_NAME, "temperatures"));
        String source = Utils.readSharedPreferences(Constants.WEARTHER_SHAR_NAME, "url");
        homeTitle.append("  ").append(getResources().getString(R.string.home_air_quality)).append(Utils.readSharedPreferences(Constants.WEARTHER_SHAR_NAME, "pmValue"));
        Fragment fragment = mHomeFurnishingFragment.get();
        if (currentFragment == fragment) {
            Glide.with(mContext).load(source).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).into(ivTitle);
            titleTv.setText(homeTitle.toString());
        }
    }

    @Override
    public void onBackPressed() {
        exit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //这是一个监听用的按键的方法，keyCode 监听用户的动作，如果是按了返回键，同时Webview要返回的话，WebView执行回退操作，因为mWebView.canGoBack()返回的是一个Boolean类型，所以我们把它返回为true

        // 是否触发按键为back键
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            WebView mWebView = null;
            if (mHomeShopingFragment.get() instanceof HomeShopingFragment) {
                mWebView = ((HomeShopingFragment) mHomeShopingFragment.get()).getWebView();
            }
            if (null != mWebView && mWebView.canGoBack()) {
                showsShopping();
                mWebView.goBack();
            } else {
                onBackPressed();
            }
            return true;
        } else {
            // 如果不是back键正常响应
            return super.onKeyDown(keyCode, event);
        }
    }

    private void exit() {
        if ((System.currentTimeMillis() - clickTime) > 2000) {
            ToastUtils.showToast(this, getResources().getString(R.string.home_exits));
            clickTime = System.currentTimeMillis();
        } else {
            ActivityStackManager.getManager().exitApp(mContext);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (null == intent) {
            return;
        }
        String key = intent.getStringExtra("type");
        if ("add_gateway".equals(key) && null != mIAddGetawary) {
            mIAddGetawary.addGetawary();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(mContext).unregisterReceiver(mMessageReceiver);
        if (null != mLocationClient)
            mLocationClient.stop();
        ActivityStackManager.getManager().exitApp(mContext);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(mContext).registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                    String messge = intent.getStringExtra(KEY_MESSAGE);
                    String extras = intent.getStringExtra(KEY_EXTRAS);
                    StringBuilder showMsg = new StringBuilder();
                    showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                    if (!ExampleUtil.isEmpty(extras)) {
                        showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                    }
                    //setCostomMsg(showMsg.toString());
                }
            } catch (Exception e) {
            }
        }
    }

    // 这是来自 JPush Example 的设置别名的 Activity 里的代码。一般 App 的设置的调用入口，在任何方便的地方调用都可以。
    public void setAlias(String serialNo) {
//        StringBuffer sb = new StringBuffer();
//        sb.append(Utils.readSharedPreferences(getResources().getString(R.string.home_login_name)));
        JPushInterface.resumePush(getApplicationContext());
        String alias = serialNo;
        if (TextUtils.isEmpty(alias)) {
            return;
        }
        if (!ExampleUtil.isValidTagAndAlias(alias)) {
            return;
        }

        // 调用 Handler 来异步设置别名
        mhandler.sendMessage(mhandler.obtainMessage(MSG_SET_ALIAS, alias));
    }

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    // logs = "Set tag and alias success";
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    break;
                case 6002:
                    //    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    // 延迟 60 秒来调用 Handler 设置别名
                    mhandler.sendMessageDelayed(mhandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    break;
                default:
                    logs = "Failed with errorCode = " + code;
            }
            // ExampleUtil.showToast(logs, mContext.getApplicationContext());
        }
    };

    public void setIAddGetawary(IAddGetawary iAddGetawary) {
        mIAddGetawary = iAddGetawary;
    }
}
