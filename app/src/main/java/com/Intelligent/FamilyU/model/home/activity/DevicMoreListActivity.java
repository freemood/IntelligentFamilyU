package com.Intelligent.FamilyU.model.home.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.Intelligent.FamilyU.R;
import com.Intelligent.FamilyU.base.BaseFragmentActivity;
import com.Intelligent.FamilyU.base.BasePagerAdapter;
import com.Intelligent.FamilyU.model.gateway.fragment.HomeGatewayDeviceFragment;
import com.Intelligent.FamilyU.model.home.fragment.HomeHomeDeviceFragment;
import com.Intelligent.FamilyU.model.network.fragment.HomeNetworkDeviceFragment;
import com.Intelligent.FamilyU.model.home.fragment.HomePluginDeviceFragment;
import com.Intelligent.FamilyU.view.CustomViewPager;

import java.lang.ref.SoftReference;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 下挂设备
 */
public class DevicMoreListActivity extends BaseFragmentActivity {
    @BindView(R.id.vPager)
    CustomViewPager viewPager;
    @BindView(R.id.page_title)
    TextView titleTv;
    @BindView(R.id.page_right_rl)
    RelativeLayout pageRightRl;

    @BindView(R.id.page_right_tv)
    TextView pageRightTv;
    @BindView(R.id.iv_tab_bottom_img2)
    ImageView bottomImg2;
    @BindView(R.id.iv_tab_bottom_img1)
    ImageView bottomImg1;
    @BindView(R.id.iv_tab_bottom_img3)
    ImageView bottomImg3;
    @BindView(R.id.iv_tab_bottom_img4)
    ImageView bottomImg4;

    private BasePagerAdapter mPagerAdapter;
    private ArrayList<Fragment> mFragmentList = new ArrayList<>();
    private SoftReference<Fragment> mHomeGatewayDeviceFragment = null;
    private SoftReference<Fragment> mHomeNetworkDeviceFragment = null;
    private SoftReference<Fragment> mHomeHomeDeviceFragment = null;
    private SoftReference<Fragment> mHomePluginDeviceFragment = null;
    private FragmentManager mFragmentManager = null;
    private Fragment currentFragment = null;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_xiagua_device_list;
    }

    @Override
    protected void initBundleData() {

    }

    @Override
    protected void initView() {
        mHomeGatewayDeviceFragment = new SoftReference(new HomeGatewayDeviceFragment());
        mHomeNetworkDeviceFragment = new SoftReference(new HomeNetworkDeviceFragment());
        mHomeHomeDeviceFragment = new SoftReference(new HomeHomeDeviceFragment());
        mHomePluginDeviceFragment = new SoftReference(new HomePluginDeviceFragment());
        mFragmentList.add(mHomeGatewayDeviceFragment.get());
        mFragmentList.add(mHomeNetworkDeviceFragment.get());
        mFragmentList.add(mHomeHomeDeviceFragment.get());
        mFragmentList.add(mHomePluginDeviceFragment.get());
        mFragmentManager = getSupportFragmentManager();

        //viewPager = findViewById(R.id.view_pager);
        mPagerAdapter = new BasePagerAdapter(mFragmentManager, mFragmentList);
        //设置Adapter
        viewPager.setAdapter(mPagerAdapter);
        viewPager.setScanScroll(false);
        viewPager.setCurrentItem(0);
        currentFragment = mFragmentList.get(0);
        titleTv.setText(R.string.home_my_more_device);
        bottomImg1.setVisibility(View.VISIBLE);
        bottomImg2.setVisibility(View.GONE);
        bottomImg3.setVisibility(View.GONE);
        bottomImg4.setVisibility(View.GONE);
        pageRightTv.setText(getResources().getString(R.string.home_my_pull_black_device));
        pageRightRl.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.gateway_device_tv, R.id.networking_device_tv, R.id.home_device_tv, R.id.plugin_device_tv, R.id.page_back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.gateway_device_tv:
                currentFragment = switchFragment(mFragmentManager, currentFragment, mFragmentList.get(0));
                viewPager.setCurrentItem(0);
                bottomImg1.setVisibility(View.VISIBLE);
                bottomImg2.setVisibility(View.GONE);
                bottomImg3.setVisibility(View.GONE);
                bottomImg4.setVisibility(View.GONE);
                break;
            case R.id.networking_device_tv:
                currentFragment = switchFragment(mFragmentManager, currentFragment, mFragmentList.get(1));
                viewPager.setCurrentItem(1);
                bottomImg1.setVisibility(View.GONE);
                bottomImg2.setVisibility(View.VISIBLE);
                bottomImg3.setVisibility(View.GONE);
                bottomImg4.setVisibility(View.GONE);
                break;
            case R.id.home_device_tv:
                currentFragment = switchFragment(mFragmentManager, currentFragment, mFragmentList.get(2));
                viewPager.setCurrentItem(2);
                bottomImg1.setVisibility(View.GONE);
                bottomImg2.setVisibility(View.GONE);
                bottomImg3.setVisibility(View.VISIBLE);
                bottomImg4.setVisibility(View.GONE);
                break;
            case R.id.plugin_device_tv:
                currentFragment = switchFragment(mFragmentManager, currentFragment, mFragmentList.get(3));
                viewPager.setCurrentItem(3);
                bottomImg1.setVisibility(View.GONE);
                bottomImg2.setVisibility(View.GONE);
                bottomImg3.setVisibility(View.GONE);
                bottomImg4.setVisibility(View.VISIBLE);
                break;
            case R.id.page_back:
                finish();
                break;
        }
    }

}
