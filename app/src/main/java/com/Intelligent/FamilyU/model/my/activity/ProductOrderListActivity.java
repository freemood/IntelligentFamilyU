package com.Intelligent.FamilyU.model.my.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.Intelligent.FamilyU.R;
import com.Intelligent.FamilyU.adapter.ProductOrderListRecyclerViewAdapter;
import com.Intelligent.FamilyU.base.BaseFragmentActivity;
import com.Intelligent.FamilyU.model.my.entity.ProductOrderEntity;
import com.Intelligent.FamilyU.model.my.entity.ProductOrderListBean;
import com.Intelligent.FamilyU.model.my.iface.IProductOrderView;
import com.Intelligent.FamilyU.model.my.persenter.ProductOrderLisPresenter;
import com.Intelligent.FamilyU.utils.ToastUtils;
import com.Intelligent.FamilyU.utils.Utils;
import com.Intelligent.FamilyU.widget.RLoadingDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 设备型号
 */
public class ProductOrderListActivity extends BaseFragmentActivity implements IProductOrderView {
    @BindView(R.id.page_title)
    TextView titleTv;
    @BindView(R.id.page_right_tv)
    TextView pageRightTv;
    @BindView(R.id.page_right_rl)
    RelativeLayout pageRightRl;
    @BindView(R.id.my_recycler_view)
    RecyclerView mRecyclerView;
    private RLoadingDialog mLoadingDialog;
    private ProductOrderListRecyclerViewAdapter adapter;
    private List<ProductOrderEntity> mlist = new ArrayList<ProductOrderEntity>();
    private LinearLayoutManager mLayoutManager;
    private ProductOrderLisPresenter mProductOrderLisPresenter = new ProductOrderLisPresenter(this, this);
    private String mSerialNo;
    private ProductOrderListRecyclerViewAdapter.OnItemClickListener mOnItemClickListener = new ProductOrderListRecyclerViewAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(int postion) {
            ProductOrderEntity mb = mlist.get(postion);


        }

        @Override
        public void onItemLongClick(int postion) {

        }
    };

    @Override
    protected int getContentViewId() {
        return R.layout.activity_product_list;
    }

    @Override
    protected void initBundleData() {

    }

    @Override
    protected void initView() {
        mLoadingDialog = new RLoadingDialog(mContext, true);
        titleTv.setText(R.string.home_my_order);
        initAdapter();
        initList();
    }

    @Override
    protected void initData() {

    }

    private void initAdapter() {

        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)
        adapter = new ProductOrderListRecyclerViewAdapter(mContext, mlist, mOnItemClickListener);
        mRecyclerView.setAdapter(adapter);
    }

    protected void initList() {
        StringBuffer sb = new StringBuffer();
        sb.append(Utils.readSharedPreferences(getResources().getString(R.string.home_login_name))).append("_").append("serialNo");
        mSerialNo = Utils.readSharedPreferences(sb.toString());
        mProductOrderLisPresenter.productOrderLis(mSerialNo);
    }

    @Override
    public void showResult(ProductOrderListBean bean) {
        if (null == bean) {
            return;
        }
        adapter.clear();
        List<ProductOrderEntity> beanlist = bean.getList();
        int size = beanlist.size();
        for (int i = 0; i < size; i++) {
            ProductOrderEntity mProductOrderEntity = beanlist.get(i);
            adapter.addData(mProductOrderEntity, mlist.size());
        }
    }

    @OnClick({R.id.page_back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.page_back:
                finish();
                break;

        }
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
