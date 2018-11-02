package com.Intelligent.FamilyU.model.home.fragment;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.Intelligent.FamilyU.R;
import com.Intelligent.FamilyU.adapter.DeviceMoreListRecyclerViewAdapter;
import com.Intelligent.FamilyU.adapter.DownloadListRecyclerViewAdapter;
import com.Intelligent.FamilyU.base.BaseFragment;
import com.Intelligent.FamilyU.utils.ToastUtils;
import com.Intelligent.FamilyU.view.IBaseView;
import com.Intelligent.FamilyU.view.SwipeItemLayout;
import com.Intelligent.FamilyU.widget.RLoadingDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * 网关设备
 */
public class HomeHomeDeviceFragment extends BaseFragment implements IBaseView {
    @BindView(R.id.my_recycler_view)
    RecyclerView mRecyclerView;
    private RLoadingDialog mLoadingDialog;
    private DeviceMoreListRecyclerViewAdapter adapter;
    private List<HashMap<String, String>> mlist = new ArrayList<HashMap<String, String>>();
    private LinearLayoutManager mLayoutManager;
    private String[] nameStrings = null;
    private String[] messageStrings = null;
    private String[] timeStrings = null;
    private DeviceMoreListRecyclerViewAdapter.OnItemClickListener mOnItemClickListener = new DeviceMoreListRecyclerViewAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(int postion) {
            //dapter.notifyDataSetChanged();
        }

        @Override
        public void onItemLongClick(int postion) {

        }
    };

    @Override
    protected View getContentView() {
        return LayoutInflater.from(mContext).inflate(R.layout.activity_download_message_list, null);
    }

    @Override
    protected void initBundleData() {

    }

    @Override
    protected void initView() {
        mLoadingDialog = new RLoadingDialog(mContext, true);
        initAdapter();
    }

    @Override
    protected void initData() {

    }

    private void initAdapter() {
        nameStrings = new String[]{"电饭煲", "音响", "烟雾报警器"};
        messageStrings = new String[]{"BWM1257", "QWE279", "QWE279"};
        timeStrings = new String[]{"21:09", "01:06", "01:06"};
        int length = nameStrings.length;
        for (int i = 0; i < length; i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("title", nameStrings[i]);
            map.put("message", messageStrings[i]);
            map.put("time", timeStrings[i]);
            mlist.add(map);
        }

        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)
        adapter = new DeviceMoreListRecyclerViewAdapter(mContext, mlist, mOnItemClickListener);
        mRecyclerView.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeColors(Color.RED);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });
        mRecyclerView.setAdapter(adapter);
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

