package com.Intelligent.FamilyU.model.cloud.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.Intelligent.FamilyU.R;
import com.Intelligent.FamilyU.base.BaseFragmentActivity;
import com.Intelligent.FamilyU.base.BasePagerAdapter;
import com.Intelligent.FamilyU.model.cloud.entity.CloudFileBean;
import com.Intelligent.FamilyU.model.cloud.fragment.CloudMyFragment;
import com.Intelligent.FamilyU.model.cloud.fragment.CloudSearchListFragment;
import com.Intelligent.FamilyU.model.cloud.fragment.CloudTransmissionMainFragment;
import com.Intelligent.FamilyU.model.cloud.iface.IRefreshCloudDeleteList;
import com.Intelligent.FamilyU.utils.PhotoUtils;
import com.Intelligent.FamilyU.model.cloud.db.SQLiteUtil;
import com.Intelligent.FamilyU.utils.Utils;
import com.Intelligent.FamilyU.view.CustomViewPager;

import java.io.File;
import java.lang.ref.SoftReference;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 云存储首頁
 */
public class CloudStorageActivity extends BaseFragmentActivity {

    private BasePagerAdapter mPagerAdapter;
    private ArrayList<Fragment> mFragmentList = new ArrayList<>();
    private SoftReference<Fragment> mCloudSearchListFragment = null;
    private SoftReference<Fragment> mCloudTransmissionMainFragment = null;
    private SoftReference<Fragment> mCloudMyFragment = null;
    private FragmentManager mFragmentManager = null;
    private Fragment currentFragment = null;
    private int swichType = 9;


    @BindView(R.id.view_pager)
    CustomViewPager viewPager;
    @BindView(R.id.page_title)
    TextView titleTv;
    @BindView(R.id.page_right_tv)
    TextView pageRightTv;
    @BindView(R.id.page_right_rl)
    RelativeLayout pageRightRl;

    @BindView(R.id.home_main_page_iv)
    TextView homeMainPageIv;
    @BindView(R.id.home_main_page_tv)
    TextView homeMainPageTv;
    @BindView(R.id.home_scene_iv)
    TextView homeSceneIv;
    @BindView(R.id.home_scene_tv)
    TextView homeSceneTv;
    @BindView(R.id.home_my_iv)
    TextView homeMyIv;
    @BindView(R.id.home_my_tv)
    TextView homeMyTv;

    private android.os.Handler mhandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main_cloud;
    }

    @Override
    protected void initBundleData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        mCloudSearchListFragment = new SoftReference(new CloudSearchListFragment());
        mCloudTransmissionMainFragment = new SoftReference(new CloudTransmissionMainFragment());
        mCloudMyFragment = new SoftReference(new CloudMyFragment());

        mFragmentList.add(mCloudSearchListFragment.get());
        mFragmentList.add(mCloudTransmissionMainFragment.get());
        mFragmentList.add(mCloudMyFragment.get());
        mFragmentManager = getSupportFragmentManager();

        mPagerAdapter = new BasePagerAdapter(mFragmentManager, mFragmentList);
        //设置Adapter
        viewPager.setAdapter(mPagerAdapter);
        viewPager.setScanScroll(false);
        viewPager.setCurrentItem(0);
        currentFragment = mFragmentList.get(0);
        swichType = 0;
        titleTv.setText(R.string.home_cloud_title);

        pageRightTv.setBackgroundResource(R.mipmap.icon_add4);
        pageRightRl.setVisibility(View.VISIBLE);

        homeMainPageIv.setBackgroundResource(R.mipmap.cloud_caidan_on);
        homeSceneIv.setBackgroundResource(R.mipmap.cloud_chuanshu_off);
        homeMyIv.setBackgroundResource(R.mipmap.cloud_my_off);

        homeMainPageTv.setTextColor(getResources().getColor(R.color.home_black));
        homeSceneTv.setTextColor(getResources().getColor(R.color.home_grgray_1));
        homeMyTv.setTextColor(getResources().getColor(R.color.home_grgray_1));

    }


    @OnClick({R.id.main_page, R.id.main_scene, R.id.main_my, R.id.page_back, R.id.page_right_rl})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_page:
                swichType = 0;
                swichTypeFragment(mCloudSearchListFragment.get(), swichType);
                titleTv.setText(R.string.home_cloud);
                homeMainPageIv.setBackgroundResource(R.mipmap.cloud_caidan_on);
                homeSceneIv.setBackgroundResource(R.mipmap.cloud_chuanshu_off);
                homeMyIv.setBackgroundResource(R.mipmap.cloud_my_off);

                homeMainPageTv.setTextColor(getResources().getColor(R.color.home_black));
                homeSceneTv.setTextColor(getResources().getColor(R.color.home_grgray_1));
                homeMyTv.setTextColor(getResources().getColor(R.color.home_grgray_1));

                pageRightTv.setBackgroundResource(R.mipmap.icon_add4);
                pageRightRl.setVisibility(View.VISIBLE);
                break;
            case R.id.main_scene:
                swichType = 1;
                swichTypeFragment(mCloudTransmissionMainFragment.get(), swichType);
                titleTv.setText(R.string.home_cloud_chuanshu);

                homeMainPageIv.setBackgroundResource(R.mipmap.cloud_caidan_off);
                homeSceneIv.setBackgroundResource(R.mipmap.cloud_chuanshu_on);
                homeMyIv.setBackgroundResource(R.mipmap.cloud_my_off);

                homeMainPageTv.setTextColor(getResources().getColor(R.color.home_grgray_1));
                homeSceneTv.setTextColor(getResources().getColor(R.color.home_black));
                homeMyTv.setTextColor(getResources().getColor(R.color.home_grgray_1));

                pageRightRl.setVisibility(View.GONE);
                break;

            case R.id.main_my:
                swichType = 2;
                swichTypeFragment(mCloudMyFragment.get(), swichType);
                titleTv.setText(R.string.home_my);
                pageRightRl.setVisibility(View.GONE);

                homeMainPageIv.setBackgroundResource(R.mipmap.cloud_caidan_off);
                homeSceneIv.setBackgroundResource(R.mipmap.cloud_chuanshu_off);
                homeMyIv.setBackgroundResource(R.mipmap.cloud_my_on);

                homeMainPageTv.setTextColor(getResources().getColor(R.color.home_grgray_1));
                homeSceneTv.setTextColor(getResources().getColor(R.color.home_grgray_1));
                homeMyTv.setTextColor(getResources().getColor(R.color.home_black));

                pageRightRl.setVisibility(View.GONE);
                break;
            case R.id.page_right_rl:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                //intent.setType(“image/*”);//选择图片
                //intent.setType(“audio/*”); //选择音频
                //intent.setType(“video/*”); //选择视频 （mp4 3gp 是android支持的视频格式）
                //intent.setType(“video/*;image/*”);//同时选择视频和图片
                intent.setType("*/*");//无类型限制
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, 1);
                break;
            case R.id.page_back:
                finish();
                break;
        }
    }

    private void swichTypeFragment(Fragment mFragment, int swichType) {
        if (!mFragment.isAdded()) {
            mFragmentList.set(swichType, mFragment);
        }
        currentFragment = switchFragment(mFragmentManager, currentFragment, mFragment);
        viewPager.setCurrentItem(swichType);
    }

    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && null != data) {
            Uri uri = data.getData();
            if ("file".equalsIgnoreCase(uri.getScheme())) {//使用第三方应用打开
                String path = uri.getPath();
                add(path);
                return;
            }
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {//4.4以后
                String path = PhotoUtils.getPath(mContext, uri);
                add(path);
            } else {//4.4以下下系统调用方法
                String path = PhotoUtils.getPath(mContext, uri);
                add(path);
            }

        }
    }

    private void add(String path){
        if(!TextUtils.isEmpty(path)){
            File file = new File(path.replaceAll("file:///",""));
            CloudFileBean mCloudFileBean = new CloudFileBean();
            mCloudFileBean.setFileName(file.getName());
            mCloudFileBean.setType("up");
            //'build', 'start', 'continue', 'stop',  'delete',download,'complete'
            mCloudFileBean.setStatus("build");
            mCloudFileBean.setSdcardFilePath(path);
            mCloudFileBean.setFileDowdloadLength("0");
            mCloudFileBean.setFileLength(String.valueOf(file.length()));
            mCloudFileBean.setFileDownPath("");
            mCloudFileBean.setUserName(Utils.readSharedPreferences(getResources().getString(R.string.home_login_name)));
            StringBuffer sb = new StringBuffer();
            sb.append(Utils.readSharedPreferences(getResources().getString(R.string.home_login_name))).append("_").append("serialNo");
            mCloudFileBean.setSerialNo(Utils.readSharedPreferences(sb.toString()));
            //根目录
            mCloudFileBean.setFileUpPath("/");
            SQLiteUtil.add(mContext,mCloudFileBean);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

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

}
