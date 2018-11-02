package com.Intelligent.FamilyU.model.download.activity;

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
import com.Intelligent.FamilyU.model.download.fragment.RemoteDownloadDeleteListFragment;
import com.Intelligent.FamilyU.model.download.fragment.RemoteDownloadListFragment;
import com.Intelligent.FamilyU.model.download.fragment.RemoteDownloadOkListFragment;
import com.Intelligent.FamilyU.model.download.iface.IRefreshDeleteList;
import com.Intelligent.FamilyU.model.download.iface.IRefreshDowdLoadList;
import com.Intelligent.FamilyU.model.download.iface.IRemoteBuildTaskView;
import com.Intelligent.FamilyU.view.CustomViewPager;

import java.lang.ref.SoftReference;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 远程下载
 */
public class RemotedownloadActivity extends BaseFragmentActivity {
    @BindView(R.id.vPager)
    CustomViewPager viewPager;
    @BindView(R.id.page_title)
    TextView titleTv;
    @BindView(R.id.page_right_tv)
    TextView pageRightTv;
    @BindView(R.id.page_right_rl)
    RelativeLayout pageRightRl;
    @BindView(R.id.iv_tab_bottom_img3)
    ImageView bottomImg3;
    @BindView(R.id.iv_tab_bottom_img2)
    ImageView bottomImg2;
    @BindView(R.id.iv_tab_bottom_img1)
    ImageView bottomImg1;

    private BasePagerAdapter mPagerAdapter;
    private ArrayList<Fragment> mFragmentList = new ArrayList<>();
    private SoftReference<Fragment> mRemoteDownloadingListFragment = null;
    private SoftReference<Fragment> mRemoteDownloadOkListFragment = null;
    private SoftReference<Fragment> mRemoteDownloadDeleteListFragment = null;
    private FragmentManager mFragmentManager = null;
    private Fragment currentFragment = null;
    private IRemoteBuildTaskView mIRemoteBuildTaskView;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_remote_download;
    }

    @Override
    protected void initBundleData() {

    }

    @Override
    protected void initView() {
        mRemoteDownloadingListFragment = new SoftReference(new RemoteDownloadListFragment());
        mRemoteDownloadOkListFragment = new SoftReference(new RemoteDownloadOkListFragment());
        mRemoteDownloadDeleteListFragment = new SoftReference(new RemoteDownloadDeleteListFragment());
        mFragmentList.add(mRemoteDownloadingListFragment.get());
        mFragmentList.add(mRemoteDownloadOkListFragment.get());
        mFragmentList.add(mRemoteDownloadDeleteListFragment.get());
        mFragmentManager = getSupportFragmentManager();

        //viewPager = findViewById(R.id.view_pager);
        mPagerAdapter = new BasePagerAdapter(mFragmentManager, mFragmentList);
        //设置Adapter
        viewPager.setAdapter(mPagerAdapter);
        viewPager.setScanScroll(false);
        viewPager.setCurrentItem(0);
        currentFragment = mFragmentList.get(0);
        titleTv.setText(R.string.home_remote_down);
        pageRightTv.setBackgroundResource(R.mipmap.file_dir);
        pageRightRl.setVisibility(View.VISIBLE);
        bottomImg1.setVisibility(View.VISIBLE);
        bottomImg2.setVisibility(View.INVISIBLE);
        bottomImg3.setVisibility(View.INVISIBLE);



    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.downing_tv, R.id.down_ok_tv, R.id.down_delete_tv, R.id.page_back, R.id.page_right_rl})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.downing_tv:
                currentFragment = switchFragment(mFragmentManager, currentFragment, mFragmentList.get(0));
                viewPager.setCurrentItem(0);
                titleTv.setText(R.string.home_remote_downing);
                bottomImg1.setVisibility(View.VISIBLE);
                bottomImg2.setVisibility(View.INVISIBLE);
                bottomImg3.setVisibility(View.INVISIBLE);
                break;
            case R.id.down_ok_tv:
                currentFragment = switchFragment(mFragmentManager, currentFragment, mFragmentList.get(1));
                viewPager.setCurrentItem(1);
                titleTv.setText(R.string.home_remote_ok);
                bottomImg1.setVisibility(View.INVISIBLE);
                bottomImg2.setVisibility(View.VISIBLE);
                bottomImg3.setVisibility(View.INVISIBLE);
                break;
            case R.id.down_delete_tv:
                currentFragment = switchFragment(mFragmentManager, currentFragment, mFragmentList.get(2));
                viewPager.setCurrentItem(2);
                titleTv.setText(R.string.home_remote_delete);
                bottomImg1.setVisibility(View.INVISIBLE);
                bottomImg2.setVisibility(View.INVISIBLE);
                bottomImg3.setVisibility(View.VISIBLE);
                break;
            case R.id.page_back:
                finish();
                break;
            case R.id.page_right_rl:
                startActivity(new Intent(mContext, RemotedownloadFileDirActivity.class));
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int reCode, Intent intent) {
        if (RESULT_OK == reCode && null != intent) {
            Bundle bundle = intent.getExtras();
            String path = bundle.getString("path", "");
            mIRemoteBuildTaskView.getFileDirPath(path);
        } else {
            mIRemoteBuildTaskView.getFileDirPath("");
        }
    }

    public void setRefreshDowdLoadList(IRefreshDowdLoadList refreshDowdLoadList) {
        if (mRemoteDownloadingListFragment.get() instanceof RemoteDownloadListFragment) {
            ((RemoteDownloadListFragment) mRemoteDownloadingListFragment.get()).setRefreshDowdLoadList(refreshDowdLoadList);
        }
    }

    public void setRefreshDeleteList(IRefreshDeleteList refreshDeleteList) {
        if (mRemoteDownloadOkListFragment.get() instanceof RemoteDownloadOkListFragment) {
            ((RemoteDownloadOkListFragment) mRemoteDownloadOkListFragment.get()).setRefreshDeleteList(refreshDeleteList);
        }
    }

    public void setICallFileDirPathListener(IRemoteBuildTaskView iRemoteBuildTaskView) {
        mIRemoteBuildTaskView = iRemoteBuildTaskView;
    }

}
