package com.Intelligent.FamilyU.model.home.presenter;

import com.Intelligent.FamilyU.base.BasePresenter;
import com.Intelligent.FamilyU.http.observer.HttpRxCallback;
import com.Intelligent.FamilyU.model.home.biz.CpuAndMemoryBiz;
import com.Intelligent.FamilyU.model.home.entity.CpuAndMemoryBean;
import com.Intelligent.FamilyU.model.home.iface.ICallCpuAndMemoryView;
import com.trello.rxlifecycle2.LifecycleProvider;

/**
 * cpu resenter
 *
 */

public class CpuAndMemoryPresenter extends BasePresenter<ICallCpuAndMemoryView, LifecycleProvider> {

    private final String TAG = CpuAndMemoryPresenter.class.getSimpleName();

    public CpuAndMemoryPresenter(ICallCpuAndMemoryView view, LifecycleProvider activity) {
        super(view, activity);
    }

    /**
     * 获取信息
     *
     */
    public void queryCpuAndMemory(String serialNo) {

        //loading
        if (getView() != null)
            getView().showLoading();

        //请求
        new CpuAndMemoryBiz().queryCpuAndMemory(serialNo, getActivity(), new HttpRxCallback() {
            @Override
            public void onSuccess(Object... object) {
                if (getView() != null) {
                    getView().closeLoading();
                    getView().showResult((CpuAndMemoryBean) object[0]);
                }
            }

            @Override
            public void onError(int code, String desc) {
                if (getView() != null) {
                    getView().closeLoading();
                    getView().showToast(desc);
                }
            }

            @Override
            public void onCancel() {
                if (getView() != null) {
                    getView().closeLoading();
                }
            }
        });


    }


}
