package com.Intelligent.FamilyU.model.cloud.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.Intelligent.FamilyU.R;
import com.Intelligent.FamilyU.base.BaseFragment;
import com.Intelligent.FamilyU.base.BasePagerAdapter;
import com.Intelligent.FamilyU.model.cloud.iface.IRefreshCloudDeleteList;
import com.Intelligent.FamilyU.model.cloud.iface.IRefreshCloudRecoveryList;
import com.Intelligent.FamilyU.utils.ToastUtils;
import com.Intelligent.FamilyU.view.CustomViewPager;
import com.Intelligent.FamilyU.view.IBaseView;
import com.Intelligent.FamilyU.widget.RLoadingDialog;

import java.lang.ref.SoftReference;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 手动Fragment
 */
public class CloudTransmissionMainFragment extends BaseFragment implements IBaseView {

    private RLoadingDialog mLoadingDialog;
    private BasePagerAdapter mPagerAdapter;
    private ArrayList<Fragment> mFragmentList = new ArrayList<>();
    private SoftReference<Fragment> mCloudDownListFragment = null;
    private SoftReference<Fragment> mCloudUploadListFragment = null;
    private SoftReference<Fragment> mCloudDownloadDeleteListFragment = null;
    private FragmentManager mFragmentManager = null;
    private Fragment currentFragment = null;
    private IRefreshCloudDeleteList iRefreshDeleteList;
    @BindView(R.id.vPager)
    CustomViewPager viewPager;

    @BindView(R.id.iv_tab_bottom_img2)
    ImageView bottomImg2;
    @BindView(R.id.iv_tab_bottom_img1)
    ImageView bottomImg1;
    @BindView(R.id.iv_tab_bottom_img3)
    ImageView bottomImg3;

    @BindView(R.id.starting_tv)
    TextView startingTv;
    @BindView(R.id.download_tv)
    TextView downloadTv;
    @BindView(R.id.delete_tv)
    TextView deleteTv;
    @Override
    protected View getContentView() {
        return LayoutInflater.from(mContext).inflate(R.layout.activity_cloud_chuanshu_main, null);
    }

    @Override
    protected void initBundleData() {

    }

    @Override
    protected void initView() {
        mLoadingDialog = new RLoadingDialog(mContext, true);
        mCloudDownListFragment = new SoftReference(new CloudDownListFragment());
        mCloudUploadListFragment = new SoftReference(new CloudUploadListFragment());
        mCloudDownloadDeleteListFragment = new SoftReference(new CloudDownloadDeleteListFragment());

        mFragmentList.add(mCloudDownListFragment.get());
        mFragmentList.add(mCloudUploadListFragment.get());
        mFragmentList.add(mCloudDownloadDeleteListFragment.get());
        mFragmentManager = getChildFragmentManager();

        mPagerAdapter = new BasePagerAdapter(mFragmentManager, mFragmentList);
        //设置Adapter
        viewPager.setAdapter(mPagerAdapter);
        viewPager.setScanScroll(false);
        viewPager.setCurrentItem(0);
        currentFragment = mFragmentList.get(0);

        bottomImg1.setVisibility(View.VISIBLE);
        bottomImg2.setVisibility(View.GONE);
        bottomImg3.setVisibility(View.GONE);
        startingTv.setTextColor(getResources().getColor(R.color.black));
        downloadTv.setTextColor(getResources().getColor(R.color.home_grgray_1));
        deleteTv.setTextColor(getResources().getColor(R.color.home_grgray_1));

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.starting_ll, R.id.download_ll, R.id.delete_ll})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.starting_ll:
                currentFragment = switchFragment(mFragmentManager, currentFragment, mFragmentList.get(0));
                viewPager.setCurrentItem(0);

                bottomImg1.setVisibility(View.VISIBLE);
                bottomImg2.setVisibility(View.GONE);
                bottomImg3.setVisibility(View.GONE);
                startingTv.setTextColor(getResources().getColor(R.color.black));
                downloadTv.setTextColor(getResources().getColor(R.color.home_grgray_1));
                deleteTv.setTextColor(getResources().getColor(R.color.home_grgray_1));
                break;
            case R.id.download_ll:
                currentFragment = switchFragment(mFragmentManager, currentFragment, mFragmentList.get(1));
                viewPager.setCurrentItem(1);

                bottomImg1.setVisibility(View.GONE);
                bottomImg2.setVisibility(View.VISIBLE);
                bottomImg3.setVisibility(View.GONE);
                startingTv.setTextColor(getResources().getColor(R.color.home_grgray_1));
                downloadTv.setTextColor(getResources().getColor(R.color.black));
                deleteTv.setTextColor(getResources().getColor(R.color.home_grgray_1));
                break;

            case R.id.delete_ll:
                currentFragment = switchFragment(mFragmentManager, currentFragment, mFragmentList.get(1));
                viewPager.setCurrentItem(3);

                bottomImg1.setVisibility(View.GONE);
                bottomImg2.setVisibility(View.GONE);
                bottomImg3.setVisibility(View.VISIBLE);
                startingTv.setTextColor(getResources().getColor(R.color.home_grgray_1));
                downloadTv.setTextColor(getResources().getColor(R.color.home_grgray_1));
                deleteTv.setTextColor(getResources().getColor(R.color.black));
                break;
        }
    }

    protected Fragment switchFragment(FragmentManager mFragmentManager, Fragment currentFragment, Fragment targetFragment) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.hide(currentFragment).show(targetFragment).commit();
        currentFragment = targetFragment;
        return currentFragment;
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

    public void setRefreshDeleteList(IRefreshCloudDeleteList refreshDeleteList) {
        if (mCloudUploadListFragment.get() instanceof CloudUploadListFragment) {
            ((CloudUploadListFragment) mCloudUploadListFragment.get()).setRefreshDeleteList(refreshDeleteList);
        }
    }

    public void setRefreshRecoveryList(IRefreshCloudRecoveryList refreshRecoveryList) {
        if (mCloudDownloadDeleteListFragment.get() instanceof CloudDownloadDeleteListFragment) {
            ((CloudDownloadDeleteListFragment) mCloudDownloadDeleteListFragment.get()).setRefreshRecoveryList(refreshRecoveryList);
        }
    }
}
