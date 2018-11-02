package com.Intelligent.FamilyU.model.scene.fragment;

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
public class HomeSceneMainFragment extends BaseFragment implements IBaseView {

    private RLoadingDialog mLoadingDialog;
    private BasePagerAdapter mPagerAdapter;
    private ArrayList<Fragment> mFragmentList = new ArrayList<>();
    private SoftReference<Fragment> mHomeSceneManualFragment = null;
    private SoftReference<Fragment> mHomeSceneAutomaticFragment = null;
    private FragmentManager mFragmentManager = null;
    private Fragment currentFragment = null;

    @BindView(R.id.vPager)
    CustomViewPager viewPager;

    @BindView(R.id.iv_tab_bottom_img2)
    ImageView bottomImg2;
    @BindView(R.id.iv_tab_bottom_img1)
    ImageView bottomImg1;
    @BindView(R.id.manual_tv)
    TextView manualTv;
    @BindView(R.id.automatic_tv)
    TextView automaticTv;

    @Override
    protected View getContentView() {
        return LayoutInflater.from(mContext).inflate(R.layout.activity_main_sence, null);
    }

    @Override
    protected void initBundleData() {

    }

    @Override
    protected void initView() {
        mLoadingDialog = new RLoadingDialog(mContext, true);
        mHomeSceneManualFragment = new SoftReference(new HomeSceneManualFragment());
        mHomeSceneAutomaticFragment = new SoftReference(new HomeSceneAutomaticFragment());
        mFragmentList.add(mHomeSceneManualFragment.get());
        mFragmentList.add(mHomeSceneAutomaticFragment.get());
        mFragmentManager = getActivity().getSupportFragmentManager();

        mPagerAdapter = new BasePagerAdapter(mFragmentManager, mFragmentList);
        //设置Adapter
        viewPager.setAdapter(mPagerAdapter);
        viewPager.setScanScroll(false);
        viewPager.setCurrentItem(0);
        currentFragment = mFragmentList.get(0);
        bottomImg1.setVisibility(View.VISIBLE);
        bottomImg2.setVisibility(View.GONE);
        manualTv.setTextColor(getResources().getColor(R.color.white));
        automaticTv.setTextColor(getResources().getColor(R.color.home_black));
    }

    @Override
    protected void initData() {

    }

   @OnClick({R.id.device_tv, R.id.system_tv})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.device_tv:
                currentFragment = switchFragment(mFragmentManager, currentFragment, mFragmentList.get(0));
                viewPager.setCurrentItem(0);
                bottomImg1.setVisibility(View.VISIBLE);
                bottomImg2.setVisibility(View.GONE);
                manualTv.setTextColor(getResources().getColor(R.color.white));
                automaticTv.setTextColor(getResources().getColor(R.color.home_black));
                break;
            case R.id.system_tv:
                currentFragment = switchFragment(mFragmentManager, currentFragment, mFragmentList.get(1));
                viewPager.setCurrentItem(1);
                bottomImg1.setVisibility(View.GONE);
                bottomImg2.setVisibility(View.VISIBLE);
                manualTv.setTextColor(getResources().getColor(R.color.home_black));
                automaticTv.setTextColor(getResources().getColor(R.color.white));
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
}
