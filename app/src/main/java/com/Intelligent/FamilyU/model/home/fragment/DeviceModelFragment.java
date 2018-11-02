package com.Intelligent.FamilyU.model.home.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.Intelligent.FamilyU.R;
import com.Intelligent.FamilyU.adapter.GridSpacingItemDecoration;
import com.Intelligent.FamilyU.adapter.MyGridLayoutManager;
import com.Intelligent.FamilyU.adapter.DeviceModelRecyclerViewAdapter;
import com.Intelligent.FamilyU.base.BaseFragment;
import com.Intelligent.FamilyU.utils.ToastUtils;
import com.Intelligent.FamilyU.view.IBaseView;
import com.Intelligent.FamilyU.widget.RLoadingDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * 设备型号
 */
public class DeviceModelFragment extends BaseFragment implements IBaseView {
    @BindView(R.id.my_recycler_view)
    RecyclerView mRecyclerView;
    private RLoadingDialog mLoadingDialog;
    private DeviceModelRecyclerViewAdapter adapter;
    private List<HashMap<String, String>> mlist = new ArrayList<HashMap<String, String>>();
    private MyGridLayoutManager mLayoutManager;
    private String[] fruitStrings = null;
    private DeviceModelRecyclerViewAdapter.OnItemClickListener mOnItemClickListener = new DeviceModelRecyclerViewAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(int postion) {
//            Intent intent = new Intent(mContext, DeviceModelListActivity.class);
//            mContext.startActivity(intent);
//            //dapter.notifyDataSetChanged();
        }

        @Override
        public void onItemLongClick(int postion) {

        }
    };

    @Override
    protected View getContentView() {
        return LayoutInflater.from(mContext).inflate(R.layout.activity_device_model, null);
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
//        fruitStrings = getResources().getStringArray(R.array.device_name);
//        int length = fruitStrings.length;
//        for (int i = 0; i < length; i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("name", "智能插座");
            mlist.add(map);
//        }
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new MyGridLayoutManager(mContext, 4);
        mLayoutManager.setScrollEnabled(false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(mContext, 4, 20));
        adapter = new DeviceModelRecyclerViewAdapter(mContext, mlist, mOnItemClickListener);
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
