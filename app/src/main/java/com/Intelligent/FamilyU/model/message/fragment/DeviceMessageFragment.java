package com.Intelligent.FamilyU.model.message.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.Intelligent.FamilyU.R;
import com.Intelligent.FamilyU.adapter.DeviceMessageListRecyclerViewAdapter;
import com.Intelligent.FamilyU.base.BaseFragment;
import com.Intelligent.FamilyU.model.message.entity.MessageBean;
import com.Intelligent.FamilyU.model.message.entity.MessageListBean;
import com.Intelligent.FamilyU.model.message.entity.MessageOneReadBean;
import com.Intelligent.FamilyU.model.message.iface.IMessageListView;
import com.Intelligent.FamilyU.model.message.iface.IMessageOneReadView;
import com.Intelligent.FamilyU.model.message.presenter.MessageListPresenter;
import com.Intelligent.FamilyU.model.message.presenter.MessageOneReadPresenter;
import com.Intelligent.FamilyU.utils.ToastUtils;
import com.Intelligent.FamilyU.utils.Utils;
import com.Intelligent.FamilyU.widget.RLoadingDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 */
public class DeviceMessageFragment extends BaseFragment implements IMessageListView, IMessageOneReadView {

    @BindView(R.id.my_recycler_view)
    RecyclerView mRecyclerView;
    private RLoadingDialog mLoadingDialog;
    private DeviceMessageListRecyclerViewAdapter adapter;
    private List<MessageBean> mlist = new ArrayList<MessageBean>();
    private LinearLayoutManager mLayoutManager;
    private MessageListPresenter mMessageListPresenter = new MessageListPresenter(this, this);
    private MessageOneReadPresenter mMessageOneReadPresenter = new MessageOneReadPresenter(this, this);
    private String mSerialNo;
    private DeviceMessageListRecyclerViewAdapter.OnItemClickListener mOnItemClickListener = new DeviceMessageListRecyclerViewAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(int postion) {
            MessageBean mb = mlist.get(postion);
            int isread = mb.getIsRead();
            if (isread == 1) {
                mMessageOneReadPresenter.readMessageList(mb.getId());
            }

        }

        @Override
        public void onItemLongClick(int postion) {

        }
    };

    @Override
    protected View getContentView() {
        return LayoutInflater.from(mContext).inflate(R.layout.activity_device_message_list, null);
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

        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)
        adapter = new DeviceMessageListRecyclerViewAdapter(mContext, mlist, mOnItemClickListener);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void showResult(MessageListBean bean) {
        if (null == bean) {
            return;
        }

        adapter.clear();
        List<MessageBean> list = bean.getList();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            MessageBean messageBean = list.get(i);
            if (2 != messageBean.getType() && 2 != messageBean.getStatus()) {
                adapter.addData(list.get(i), mlist.size());
            }
        }
    }

    @Override
    public void showResult(MessageOneReadBean bean) {
        if (null == bean) {
            return;
        }
        if (bean.isResult()) {
            initList();
        }

    }

    public void initList() {
        StringBuffer sb = new StringBuffer();
        sb.append(Utils.readSharedPreferences(getResources().getString(R.string.home_login_name))).append("_").append("serialNo");
        mSerialNo = Utils.readSharedPreferences(sb.toString());
        mMessageListPresenter.messageListQuery(mSerialNo);
    }


    @Override
    public void onResume() {
        super.onResume();
        initList();
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
