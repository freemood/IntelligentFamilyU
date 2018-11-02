package com.Intelligent.FamilyU.model.plugin.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.TextView;

import com.Intelligent.FamilyU.R;
import com.Intelligent.FamilyU.base.BaseFragmentActivity;
import com.Intelligent.FamilyU.base.BasePagerAdapter;
import com.Intelligent.FamilyU.model.plugin.fragment.PluginListFragment;
import com.Intelligent.FamilyU.view.CustomViewPager;

import java.lang.ref.SoftReference;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class PluginActivity extends BaseFragmentActivity {
    @BindView(R.id.view_pager)
    CustomViewPager viewPager;
    @BindView(R.id.page_title)
    TextView titleTv;


    private BasePagerAdapter mPagerAdapter;
    private ArrayList<Fragment> mFragmentList = new ArrayList<>();
    private SoftReference<Fragment> mUserListFragment = null;
    private FragmentManager mFragmentManager = null;
    //   private Fragment currentFragment = null;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_user;
    }

    @Override
    protected void initBundleData() {

    }

    @Override
    protected void initView() {
        mUserListFragment = new SoftReference(new PluginListFragment());
        mFragmentList.add(mUserListFragment.get());
        mFragmentManager = getSupportFragmentManager();

        //viewPager = findViewById(R.id.view_pager);
        mPagerAdapter = new BasePagerAdapter(mFragmentManager, mFragmentList);
        //设置Adapter
        viewPager.setAdapter(mPagerAdapter);
        viewPager.setScanScroll(false);
        viewPager.setCurrentItem(0);
        // currentFragment = mFragmentList.get(0);
        titleTv.setText(R.string.home_drawer_my_plug);
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.page_back})
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.download_file_task_rl:
//                currentFragment = switchFragment(mFragmentManager, currentFragment, mFragmentList.get(0));
//                viewPager.setCurrentItem(0);
//                titleTv.setText(R.string.home_user_list);
//                break;
            case R.id.page_back:
                finish();
                break;
        }
    }

}
