package com.Intelligent.FamilyU.model.plugin.presenter;

import com.Intelligent.FamilyU.base.BasePresenter;
import com.Intelligent.FamilyU.http.observer.HttpRxCallback;
import com.Intelligent.FamilyU.model.plugin.bean.PluginListBean;
import com.Intelligent.FamilyU.model.plugin.biz.PluginListBiz;
import com.Intelligent.FamilyU.model.plugin.iface.IPluginListView;
import com.trello.rxlifecycle2.LifecycleProvider;

public class PluginListPresenter extends BasePresenter<IPluginListView, LifecycleProvider> {
    private final String TAG = PluginListPresenter.class.getSimpleName();

    public PluginListPresenter(IPluginListView view, LifecycleProvider activity) {
        super(view, activity);
    }

    /**
     * 查询所有插件版本信息
     */
    public void pluginListQuery() {

        if (getView() != null)
            getView().showLoading();


        HttpRxCallback httpRxCallback = new HttpRxCallback() {
            @Override
            public void onSuccess(Object... object) {
                if (getView() != null) {
                    getView().closeLoading();
                    getView().showResult((PluginListBean) object[0]);
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
        };

        new PluginListBiz().pluginListQuery(getActivity(), httpRxCallback);
    }
}
