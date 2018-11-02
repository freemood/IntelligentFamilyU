package com.Intelligent.FamilyU.model.home.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.Intelligent.FamilyU.R;
import com.Intelligent.FamilyU.adapter.DeviceListRecyclerViewAdapter;
import com.Intelligent.FamilyU.base.BaseFragmentActivity;
import com.Intelligent.FamilyU.widget.RLoadingDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 设备型号
 */
public class DeviceModelListActivity extends BaseFragmentActivity {
    @BindView(R.id.my_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.page_title)
    TextView titleTv;
    private RLoadingDialog mLoadingDialog;
    private DeviceListRecyclerViewAdapter adapter;
    private List<HashMap<String, String>> mlist = new ArrayList<HashMap<String, String>>();
    private LinearLayoutManager mLayoutManager;
    private String[] nameStrings = null;
    private String[] modelStrings = null;
    private DeviceListRecyclerViewAdapter.OnItemClickListener mOnItemClickListener = new DeviceListRecyclerViewAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(int postion) {
            //dapter.notifyDataSetChanged();
        }

        @Override
        public void onItemLongClick(int postion) {

        }
    };

    @Override
    protected int getContentViewId() {
        return R.layout.activity_device_model_list;
    }

    @Override
    protected void initBundleData() {

    }

    @Override
    protected void initView() {
        mLoadingDialog = new RLoadingDialog(mContext, true);
        titleTv.setText(R.string.add_device_list);
        initAdapter();
    }

    @Override
    protected void initData() {

    }

    private void initAdapter() {
        nameStrings = new String[]{"烟雾报警", "空调"};
        modelStrings = new String[]{"BWM1257", "QWE279"};
        int length = nameStrings.length;
        for (int i = 0; i < length; i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("name", nameStrings[i]);
            map.put("model", modelStrings[i]);
            mlist.add(map);
        }
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)
        adapter = new DeviceListRecyclerViewAdapter(mContext, mlist, mOnItemClickListener);
        mRecyclerView.setAdapter(adapter);
    }

    @OnClick({R.id.page_back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.page_back:
                finish();
                break;
        }
    }

    /**
     * 尝试提交
     */
    private void attemptSubmit() {
//        String phone = etPhone.getText().toString();
//        if (TextUtils.isEmpty(phone)) {
//            Toast.makeText(mContext, getString(R.string.hint_phone), Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        mPhonePst.phoneQuery(phone);

    }

}
