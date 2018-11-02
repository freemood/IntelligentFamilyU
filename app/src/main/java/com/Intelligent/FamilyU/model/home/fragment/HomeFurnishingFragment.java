package com.Intelligent.FamilyU.model.home.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.Intelligent.FamilyU.R;
import com.Intelligent.FamilyU.base.BaseFragment;
import com.Intelligent.FamilyU.base.BasePagerAdapter;
import com.Intelligent.FamilyU.model.gateway.fragment.GatewayDeviceFragment;
import com.Intelligent.FamilyU.model.home.activity.CpuAndMemoryUseListActivity;
import com.Intelligent.FamilyU.model.home.activity.DevicMoreListActivity;
import com.Intelligent.FamilyU.model.home.entity.BannerListBean;
import com.Intelligent.FamilyU.model.home.entity.CpuAndMemoryBean;
import com.Intelligent.FamilyU.model.home.entity.ProductBannerEntity;
import com.Intelligent.FamilyU.model.home.iface.IBannerListView;
import com.Intelligent.FamilyU.model.home.iface.ICallCpuAndMemoryView;
import com.Intelligent.FamilyU.model.home.presenter.BannerListPresenter;
import com.Intelligent.FamilyU.model.home.presenter.CpuAndMemoryPresenter;
import com.Intelligent.FamilyU.model.network.fragment.NetworkDeviceFragment;
import com.Intelligent.FamilyU.model.plugin.fragment.PluginDeviceFragment;
import com.Intelligent.FamilyU.model.plugin.iface.ICallPluginIdListener;
import com.Intelligent.FamilyU.model.plugin.iface.ICallserialNoListener;
import com.Intelligent.FamilyU.model.scene.fragment.HomeScenceDeviceFragment;
import com.Intelligent.FamilyU.model.webview.WebViewActivity;
import com.Intelligent.FamilyU.utils.GlideImageLoader;
import com.Intelligent.FamilyU.utils.ToastUtils;
import com.Intelligent.FamilyU.utils.Utils;
import com.Intelligent.FamilyU.view.CustomViewMorePager;
import com.Intelligent.FamilyU.view.SportProgressView;
import com.Intelligent.FamilyU.widget.RLoadingDialog;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 首页Fragment
 */
public class HomeFurnishingFragment extends BaseFragment implements ICallCpuAndMemoryView, IBannerListView {

    private List<String> images = new ArrayList<>();   //定义图片集合
    private List<ProductBannerEntity> list = new ArrayList<>();
    //private RLoadingDialog mLoadingDialog;

    private BasePagerAdapter mPagerAdapter;
    private ArrayList<Fragment> mFragmentList = new ArrayList<>();
    private SoftReference<Fragment> mGatewayDeviceFragment = null;
    private SoftReference<Fragment> mNetworkDeviceFragment = null;
    private SoftReference<Fragment> mHomeDeviceFragment = null;
    private SoftReference<Fragment> mPluginDeviceFragment = null;
    private FragmentManager mFragmentManager = null;
    private Fragment currentFragment = null;
    private CpuAndMemoryPresenter mGatewayCpuAndMemoryPresenter = new CpuAndMemoryPresenter(this, this);
    private BannerListPresenter mBannerListPresenter = new BannerListPresenter(this, this);
    private ICallPluginIdListener mICallPluginIdListener = null;
    private ICallserialNoListener mINetWorkserialNoListener = null;
    private ICallserialNoListener mIHomeserialNoListener = null;
    private String mSerialNo = "";
    private String mPluginId;
    @BindView(R.id.use_banner)
    RelativeLayout useBanner;

    @BindView(R.id.banner)
    Banner banner;

    @BindView(R.id.gatewaytv)
    TextView gatewayTv;
    @BindView(R.id.networkingtv)
    TextView networkingTv;
    @BindView(R.id.hometv)
    TextView homeTv;
    @BindView(R.id.plugintv)
    TextView pluginTv;
    @BindView(R.id.view_pager)
    CustomViewMorePager viewPager;
    @BindView(R.id.cpu_iv)
    SportProgressView sportviewCpu;
    @BindView(R.id.yjcs)
    SportProgressView sportviewMemory;
    @BindView(R.id.iv_tab_bottom_img2)
    ImageView bottomImg2;
    @BindView(R.id.iv_tab_bottom_img1)
    ImageView bottomImg1;
    @BindView(R.id.iv_tab_bottom_img3)
    ImageView bottomImg3;
    @BindView(R.id.iv_tab_bottom_img4)
    ImageView bottomImg4;
    @BindView(R.id.gatewary_list)
    RelativeLayout gatewaryList;

    @Override
    protected View getContentView() {
        return LayoutInflater.from(mContext).inflate(R.layout.activity_main_page, null);
    }

    @Override
    protected void initBundleData() {

    }

    @Override
    protected void initView() {
        //  mLoadingDialog = new RLoadingDialog(mContext, true);
        initBanner(images);

        mGatewayDeviceFragment = new SoftReference(new GatewayDeviceFragment());
        mNetworkDeviceFragment = new SoftReference(new NetworkDeviceFragment());
        mHomeDeviceFragment = new SoftReference(new HomeScenceDeviceFragment());
        mPluginDeviceFragment = new SoftReference(new PluginDeviceFragment());

        // mFragmentList.add(mGatewayDeviceFragment.get());
        mFragmentList.add(mNetworkDeviceFragment.get());
        mFragmentList.add(mHomeDeviceFragment.get());
        mFragmentList.add(mPluginDeviceFragment.get());
        mFragmentManager = getChildFragmentManager();

        mPagerAdapter = new BasePagerAdapter(mFragmentManager, mFragmentList);
        //设置Adapter
        viewPager.setAdapter(mPagerAdapter);
        viewPager.setScanScroll(true);
        viewPager.setCurrentItem(0);
        currentFragment = mFragmentList.get(0);

        // bottomImg1.setVisibility(View.VISIBLE);
        bottomImg2.setVisibility(View.VISIBLE);
        bottomImg3.setVisibility(View.GONE);
        bottomImg4.setVisibility(View.GONE);

        initGateway();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Fragment mFragment = null;
                switch (position) {
//                    case 0:
//                        mFragment = mGatewayDeviceFragment.get();
//                        if (!mFragment.isAdded()) {
//                            mFragmentList.set(0, mFragment);
//                        }
//                        currentFragment = switchFragment(mFragmentManager, currentFragment, mFragment);
//                        viewPager.setCurrentItem(0);
//                        gatewayTv.setTextColor(getResources().getColor(R.color.home_black));
//                        networkingTv.setTextColor(getResources().getColor(R.color.home_grgray_1));
//                        homeTv.setTextColor(getResources().getColor(R.color.home_grgray_1));
//                        pluginTv.setTextColor(getResources().getColor(R.color.home_grgray_1));
//                        bottomImg1.setVisibility(View.VISIBLE);
//                        bottomImg2.setVisibility(View.GONE);
//                        bottomImg3.setVisibility(View.GONE);
//                        bottomImg4.setVisibility(View.GONE);
//                        break;
                    case 0:
                        mFragment = mNetworkDeviceFragment.get();
                        if (!mFragment.isAdded()) {
                            mFragmentList.set(0, mFragment);
                        }
                        currentFragment = switchFragment(mFragmentManager, currentFragment, mFragment);
                        viewPager.setCurrentItem(0);
                        gatewayTv.setTextColor(getResources().getColor(R.color.home_grgray_1));
                        networkingTv.setTextColor(getResources().getColor(R.color.home_black));
                        homeTv.setTextColor(getResources().getColor(R.color.home_grgray_1));
                        pluginTv.setTextColor(getResources().getColor(R.color.home_grgray_1));
                        //  bottomImg1.setVisibility(View.GONE);
                        bottomImg2.setVisibility(View.VISIBLE);
                        bottomImg3.setVisibility(View.GONE);
                        bottomImg4.setVisibility(View.GONE);
                        callNetWorkSerialNo(mSerialNo);
                        break;
                    case 1:
                        mFragment = mHomeDeviceFragment.get();
                        if (!mFragment.isAdded()) {
                            mFragmentList.set(1, mFragment);
                        }
                        currentFragment = switchFragment(mFragmentManager, currentFragment, mFragment);
                        viewPager.setCurrentItem(1);
                        gatewayTv.setTextColor(getResources().getColor(R.color.home_grgray_1));
                        networkingTv.setTextColor(getResources().getColor(R.color.home_grgray_1));
                        homeTv.setTextColor(getResources().getColor(R.color.home_black));
                        pluginTv.setTextColor(getResources().getColor(R.color.home_grgray_1));
                        // bottomImg1.setVisibility(View.GONE);
                        bottomImg2.setVisibility(View.GONE);
                        bottomImg3.setVisibility(View.VISIBLE);
                        bottomImg4.setVisibility(View.GONE);
                        callHomeSerialNo(mSerialNo);
                        break;
                    case 2:
                        mFragment = mPluginDeviceFragment.get();
                        if (!mFragment.isAdded()) {
                            mFragmentList.set(2, mFragment);
                        }
                        currentFragment = switchFragment(mFragmentManager, currentFragment, mFragment);
                        viewPager.setCurrentItem(2);
                        gatewayTv.setTextColor(getResources().getColor(R.color.home_grgray_1));
                        networkingTv.setTextColor(getResources().getColor(R.color.home_grgray_1));
                        homeTv.setTextColor(getResources().getColor(R.color.home_grgray_1));
                        pluginTv.setTextColor(getResources().getColor(R.color.home_black));
                        //bottomImg1.setVisibility(View.GONE);
                        bottomImg2.setVisibility(View.GONE);
                        bottomImg3.setVisibility(View.GONE);
                        bottomImg4.setVisibility(View.VISIBLE);
                        callPluginGatewayPluginId(mPluginId);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        initCallGateway();
        initCpuAndMemory();
        mBannerListPresenter.bannerListQuery();
    }

    @Override
    protected void initData() {

    }

    private void initBanner(List<String> images) {
        //注册监听函数
//        images.add("http://120.27.23.105/images/ad/0.jpg");
//        images.add("http://120.27.23.105/images/ad/1.jpg");
//        images.add("http://120.27.23.105/images/ad/2.jpg");
//        images.add("http://120.27.23.105/images/ad/3.jpg");
        //  imagePath.add(R.mipmap.bitmap_banner);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置banner中显示图片
        banner.setImages(images);
        //设置banner动画效果
        // useBanner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
        // useBanner.setBannerTitles(Arrays.asList(titles));
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(1500);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //设置完毕后调用
        banner.start();

        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                ProductBannerEntity pe = list.get(position);
                if (TextUtils.isEmpty(pe.getResources())) {
                    return;
                }
                EventBus.getDefault().postSticky(list.get(position));

                startActivity(new Intent(getActivity(), WebViewActivity.class));
            }
        });

    }

    @OnClick({R.id.device_tv, R.id.details_tv, R.id.gatewaytv_ll, R.id.networkingtv_ll, R.id.hometv_ll, R.id.plugintv_ll})
    public void onClick(View v) {
        Fragment mFragment = null;
        switch (v.getId()) {
            case R.id.device_tv:
                startActivity(new Intent(mContext, DevicMoreListActivity.class));
                break;
//            case R.id.gatewaytv_ll:
//                mFragment = mGatewayDeviceFragment.get();
//                if (!mFragment.isAdded()) {
//                    mFragmentList.set(0, mFragment);
//                }
//                currentFragment = switchFragment(mFragmentManager, currentFragment, mFragment);
//                viewPager.setCurrentItem(0);
//                gatewayTv.setTextColor(getResources().getColor(R.color.home_black));
//                networkingTv.setTextColor(getResources().getColor(R.color.home_grgray_1));
//                homeTv.setTextColor(getResources().getColor(R.color.home_grgray_1));
//                pluginTv.setTextColor(getResources().getColor(R.color.home_grgray_1));
//                bottomImg1.setVisibility(View.VISIBLE);
//                bottomImg2.setVisibility(View.GONE);
//                bottomImg3.setVisibility(View.GONE);
//                bottomImg4.setVisibility(View.GONE);
//                break;
            case R.id.networkingtv_ll:
                mFragment = mNetworkDeviceFragment.get();
                if (!mFragment.isAdded()) {
                    mFragmentList.set(0, mFragment);
                }
                currentFragment = switchFragment(mFragmentManager, currentFragment, mFragment);
                viewPager.setCurrentItem(0);
                gatewayTv.setTextColor(getResources().getColor(R.color.home_grgray_1));
                networkingTv.setTextColor(getResources().getColor(R.color.home_black));
                homeTv.setTextColor(getResources().getColor(R.color.home_grgray_1));
                pluginTv.setTextColor(getResources().getColor(R.color.home_grgray_1));
                bottomImg1.setVisibility(View.GONE);
                bottomImg2.setVisibility(View.VISIBLE);
                bottomImg3.setVisibility(View.GONE);
                bottomImg4.setVisibility(View.GONE);
                callNetWorkSerialNo(mSerialNo);
                break;
            case R.id.hometv_ll:
                mFragment = mHomeDeviceFragment.get();
                if (!mFragment.isAdded()) {
                    mFragmentList.set(1, mFragment);
                }
                currentFragment = switchFragment(mFragmentManager, currentFragment, mFragment);
                viewPager.setCurrentItem(1);
                gatewayTv.setTextColor(getResources().getColor(R.color.home_grgray_1));
                networkingTv.setTextColor(getResources().getColor(R.color.home_grgray_1));
                homeTv.setTextColor(getResources().getColor(R.color.home_black));
                pluginTv.setTextColor(getResources().getColor(R.color.home_grgray_1));
                bottomImg1.setVisibility(View.GONE);
                bottomImg2.setVisibility(View.GONE);
                bottomImg3.setVisibility(View.VISIBLE);
                bottomImg4.setVisibility(View.GONE);
                callHomeSerialNo(mSerialNo);
                break;
            case R.id.plugintv_ll:
                mFragment = mPluginDeviceFragment.get();
                if (!mFragment.isAdded()) {
                    mFragmentList.set(2, mFragment);
                }
                currentFragment = switchFragment(mFragmentManager, currentFragment, mFragment);
                viewPager.setCurrentItem(2);
                gatewayTv.setTextColor(getResources().getColor(R.color.home_grgray_1));
                networkingTv.setTextColor(getResources().getColor(R.color.home_grgray_1));
                homeTv.setTextColor(getResources().getColor(R.color.home_grgray_1));
                pluginTv.setTextColor(getResources().getColor(R.color.home_black));
                bottomImg1.setVisibility(View.GONE);
                bottomImg2.setVisibility(View.GONE);
                bottomImg3.setVisibility(View.GONE);
                bottomImg4.setVisibility(View.VISIBLE);
                callPluginGatewayPluginId(mPluginId);
                break;
            case R.id.details_tv:
                startActivity(new Intent(mContext, CpuAndMemoryUseListActivity.class));
                break;
        }
    }

    private void initCpuAndMemory() {
//        sportviewCpu.setProgress(10);
//        sportviewMemory.setProgress(20);
        StringBuffer sb = new StringBuffer();
        sb.append(Utils.readSharedPreferences(getResources().getString(R.string.home_login_name))).append("_").append("serialNo");
        mSerialNo = Utils.readSharedPreferences(sb.toString());
        if (TextUtils.isEmpty(mSerialNo)) {
            return;
        }
        mGatewayCpuAndMemoryPresenter.queryCpuAndMemory(mSerialNo);
    }

    //监听网关的网络序列号
    private void initCallGateway() {
        Fragment mFragment = mGatewayDeviceFragment.get();
        if (mFragment instanceof GatewayDeviceFragment) {
            ((GatewayDeviceFragment) mFragment).setICallGatewayCpuAndMemory(this);
        }
    }

    @Override
    public void showResult(CpuAndMemoryBean bean) {
        if (bean == null) {
            return;
        }
        sportviewCpu.setProgress(Integer.parseInt(bean.getCpuUsage()));
        sportviewMemory.setProgress(Integer.parseInt(bean.getMemoryUsage()));
    }

    @Override
    public void showResult(BannerListBean bean) {
        if (bean == null) {
            return;
        }
        List<ProductBannerEntity> listType = new ArrayList<>();
        list = bean.getList();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            ProductBannerEntity pe = list.get(i);
            if ("1".equals(pe.getType())) {
                images.add(pe.getUrl());
            } else {
                listType.add(pe);
            }
        }
        list.removeAll(listType);
        initBanner(images);
    }

    @Override
    public void callGatewaySerialNo(String serialNo, String pluginId) {
        if (TextUtils.isEmpty(serialNo) || mSerialNo.equals(serialNo)) {
            return;
        }
        mSerialNo = serialNo;
        mPluginId = pluginId;
        callNetWorkSerialNo(serialNo);
        callHomeSerialNo(serialNo);
        mGatewayCpuAndMemoryPresenter.queryCpuAndMemory(serialNo);
    }

    @Override
    public void showLoading() {
        //   mLoadingDialog.show();
    }

    @Override
    public void closeLoading() {
        //       mLoadingDialog.dismiss();
    }

    @Override
    public void showToast(String msg) {
        ToastUtils.showToast(mContext, msg);
    }

    private void initGateway() {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        //创建fragment但是不绘制UI
        transaction.replace(R.id.gatewary_list, mGatewayDeviceFragment.get());
        transaction.commit();
    }

    protected Fragment switchFragment(FragmentManager mFragmentManager, Fragment currentFragment, Fragment targetFragment) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.hide(currentFragment).show(targetFragment).commit();
        currentFragment = targetFragment;
        return currentFragment;
    }

    /**
     * 插件监听网关sn
     *
     * @param iCallPluginIdListener
     */
    public void setICallPluginIdListener(ICallPluginIdListener iCallPluginIdListener) {
        mICallPluginIdListener = iCallPluginIdListener;
    }

    /**
     * 组网监听网关sn
     *
     * @param iCallserialNoListener
     */
    public void setINetWorkserialNoListener(ICallserialNoListener iCallserialNoListener) {
        mINetWorkserialNoListener = iCallserialNoListener;
    }

    /**
     * 家居监听网关sn
     */
    public void setHomeDeviceSerialNoListener(ICallserialNoListener iCallserialNoListener) {
        mIHomeserialNoListener = iCallserialNoListener;
    }

    private void callPluginGatewayPluginId(String pluginId) {
        if (null != mICallPluginIdListener) {
            mICallPluginIdListener.getCallPluginId(pluginId);
        }
    }

    private void callNetWorkSerialNo(String serialNo) {
        if (null != mINetWorkserialNoListener) {
            mINetWorkserialNoListener.getCallSerualNo(serialNo);
        }
    }

    private void callHomeSerialNo(String serialNo) {
        if (null != mIHomeserialNoListener) {
            mIHomeserialNoListener.getCallSerualNo(serialNo);
        }
    }
}
