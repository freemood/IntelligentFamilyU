package com.Intelligent.FamilyU.model.message.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.Intelligent.FamilyU.R;
import com.Intelligent.FamilyU.base.BaseFragmentActivity;
import com.Intelligent.FamilyU.base.BasePagerAdapter;
import com.Intelligent.FamilyU.model.message.entity.MessageAllDeleteBean;
import com.Intelligent.FamilyU.model.message.entity.MessageAllReadBean;
import com.Intelligent.FamilyU.model.message.entity.MessageNotionBean;
import com.Intelligent.FamilyU.model.message.iface.IMessageAllDeleteView;
import com.Intelligent.FamilyU.model.message.iface.IMessageAllReadView;
import com.Intelligent.FamilyU.model.message.presenter.MessageAllDeletePresenter;
import com.Intelligent.FamilyU.model.message.presenter.MessageAllReadPresenter;
import com.Intelligent.FamilyU.utils.ToastUtils;
import com.Intelligent.FamilyU.utils.Utils;
import com.Intelligent.FamilyU.view.CustomViewPager;
import com.Intelligent.FamilyU.model.message.fragment.DeviceMessageFragment;
import com.Intelligent.FamilyU.model.message.fragment.SystemMessageFragment;
import com.Intelligent.FamilyU.widget.RLoadingDialog;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.SoftReference;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

/**
 * 设备型号
 */
public class MessageListActivity extends BaseFragmentActivity implements IMessageAllDeleteView, IMessageAllReadView {
    @BindView(R.id.vPager)
    CustomViewPager viewPager;
    @BindView(R.id.page_title)
    TextView titleTv;
    @BindView(R.id.page_right_tv)
    TextView pageRightTv;
    @BindView(R.id.page_right_rl)
    RelativeLayout pageRightRl;

    @BindView(R.id.iv_tab_bottom_img2)
    ImageView bottomImg2;
    @BindView(R.id.iv_tab_bottom_img1)
    ImageView bottomImg1;
    private RLoadingDialog mLoadingDialog;
    private BasePagerAdapter mPagerAdapter;
    private ArrayList<Fragment> mFragmentList = new ArrayList<>();
    private SoftReference<Fragment> mDeviceMessageFragment = null;
    private SoftReference<Fragment> mSystemMessageFragment = null;
    private FragmentManager mFragmentManager = null;
    private Fragment currentFragment = null;
    private MessageAllDeletePresenter mMessageAllDeletePresenter = new MessageAllDeletePresenter(this, this);
    private MessageAllReadPresenter mMessageAllReadPresenter = new MessageAllReadPresenter(this, this);
    private String mSerialNo;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_message_list;
    }

    @Override
    protected void initBundleData() {
        registeredEventBus();
        Intent intent = getIntent();
        try {
            if (null != intent) {
                Bundle bundle = getIntent().getExtras();
                //String title = null;
                String content = null;
                if (bundle != null) {
                    //title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
                    content = bundle.getString(JPushInterface.EXTRA_ALERT);
                    MessageNotionBean mn = new Gson().fromJson(content, MessageNotionBean.class);
                    EventBus.getDefault().postSticky(mn);
                }
            }
        } catch (Exception e) {

        }
    }

    @Override
    protected void initView() {
        mLoadingDialog = new RLoadingDialog(mContext, true);
        mDeviceMessageFragment = new SoftReference(new DeviceMessageFragment());
        mSystemMessageFragment = new SoftReference(new SystemMessageFragment());
        mFragmentList.add(mDeviceMessageFragment.get());
        mFragmentList.add(mSystemMessageFragment.get());
        mFragmentManager = getSupportFragmentManager();

        //viewPager = findViewById(R.id.view_pager);
        mPagerAdapter = new BasePagerAdapter(mFragmentManager, mFragmentList);
        //设置Adapter
        viewPager.setAdapter(mPagerAdapter);
        viewPager.setScanScroll(false);
        viewPager.setCurrentItem(0);
        currentFragment = mFragmentList.get(0);
        titleTv.setText(R.string.home_device_message);
        bottomImg1.setVisibility(View.VISIBLE);
        bottomImg2.setVisibility(View.GONE);
        pageRightRl.setVisibility(View.VISIBLE);
        pageRightTv.setBackgroundResource(R.mipmap.setting_message);
    }

    @Override
    protected void initData() {
        StringBuffer sb = new StringBuffer();
        sb.append(Utils.readSharedPreferences(getResources().getString(R.string.home_login_name))).append("_").append("serialNo");
        mSerialNo = Utils.readSharedPreferences(sb.toString());
    }


    @OnClick({R.id.device_tv, R.id.system_tv, R.id.page_back, R.id.page_right_rl, R.id.all_read_tv, R.id.all_delect_tv})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.device_tv:
                currentFragment = switchFragment(mFragmentManager, currentFragment, mFragmentList.get(0));
                viewPager.setCurrentItem(0);
                titleTv.setText(R.string.home_device_message);
                bottomImg1.setVisibility(View.VISIBLE);
                bottomImg2.setVisibility(View.GONE);
                break;
            case R.id.system_tv:
                currentFragment = switchFragment(mFragmentManager, currentFragment, mFragmentList.get(1));
                viewPager.setCurrentItem(1);
                titleTv.setText(R.string.home_system_message);
                bottomImg1.setVisibility(View.GONE);
                bottomImg2.setVisibility(View.VISIBLE);
                break;
            case R.id.page_right_rl:
                startActivity(new Intent(mContext, MessageSettingActivity.class));
                break;
            case R.id.page_back:
                finish();
                break;
            case R.id.all_read_tv:
                mMessageAllReadPresenter.allReadMessageList(mSerialNo);
                break;
            case R.id.all_delect_tv:
                mMessageAllDeletePresenter.allDeleteMessageList(mSerialNo);
                break;
        }
    }

    @Override
    public void showResult(MessageAllDeleteBean bean) {
        if (null == bean) {
            return;
        }
        refrenResult();
    }

    @Override
    public void showResult(MessageAllReadBean bean) {
        if (null == bean) {
            return;
        }
        refrenResult();
    }

    public void refrenResult() {
        ((DeviceMessageFragment) mDeviceMessageFragment.get()).initList();
        ((SystemMessageFragment) mSystemMessageFragment.get()).initList();
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
}
