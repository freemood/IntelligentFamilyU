package com.Intelligent.FamilyU.model.home.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.Intelligent.FamilyU.R;
import com.Intelligent.FamilyU.base.BaseFragmentActivity;
import com.Intelligent.FamilyU.base.BasePagerAdapter;
import com.Intelligent.FamilyU.base.Constants;
import com.Intelligent.FamilyU.model.home.fragment.DeviceModelFragment;
import com.Intelligent.FamilyU.model.home.fragment.DeviceZxingFragment;
import com.Intelligent.FamilyU.view.CustomViewPager;

import java.lang.ref.SoftReference;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class DeviceAddActivity extends BaseFragmentActivity {
    @BindView(R.id.view_pager)
    CustomViewPager viewPager;
    @BindView(R.id.page_title)
    TextView titleTv;

    @BindView(R.id.add_device_zxing_bottom)
    RelativeLayout zxingBottom;
    @BindView(R.id.add_device_model_bottom)
    RelativeLayout modelBottom;

    private BasePagerAdapter mPagerAdapter;
    private ArrayList<Fragment> mFragmentList = new ArrayList<>();
    private SoftReference<Fragment> mDeviceZxingFragment = null;
    private SoftReference<Fragment> mDeviceModelFragment = null;
    private FragmentManager mFragmentManager = null;
    private Fragment currentFragment = null;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_add_device;
    }

    @Override
    protected void initBundleData() {

    }

    @Override
    protected void initView() {
        mDeviceZxingFragment = new SoftReference(new DeviceZxingFragment());
        mDeviceModelFragment = new SoftReference(new DeviceModelFragment());
        mFragmentList.add(mDeviceZxingFragment.get());
        mFragmentList.add(mDeviceModelFragment.get());
        mFragmentManager = getSupportFragmentManager();

        //viewPager = findViewById(R.id.view_pager);
        mPagerAdapter = new BasePagerAdapter(mFragmentManager, mFragmentList);
        //设置Adapter
        viewPager.setAdapter(mPagerAdapter);
        viewPager.setScanScroll(false);
        viewPager.setCurrentItem(0);
        currentFragment = mFragmentList.get(0);
        zxingBottom.setBackgroundResource(R.color.home_main);
        modelBottom.setBackgroundResource(R.color.white);
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.add_device_zxing_bottom, R.id.add_device_model_bottom, R.id.page_back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_device_zxing_bottom:
                currentFragment = switchFragment(mFragmentManager, currentFragment, mFragmentList.get(0));
                viewPager.setCurrentItem(0);
                titleTv.setText(R.string.add_device_zxing);
                zxingBottom.setBackgroundResource(R.color.home_main);
                modelBottom.setBackgroundResource(R.color.white);

                break;
            case R.id.add_device_model_bottom:
                currentFragment = switchFragment(mFragmentManager, currentFragment, mFragmentList.get(1));
                viewPager.setCurrentItem(1);
                titleTv.setText(R.string.add_device_version);

                zxingBottom.setBackgroundResource(R.color.white);
                modelBottom.setBackgroundResource(R.color.home_main);
                break;
            case R.id.page_back:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == Constants.REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY) {
            if (null != mDeviceZxingFragment) {
                mDeviceZxingFragment.get().onActivityResult(requestCode, resultCode, data);
            }
        }
    }

}
