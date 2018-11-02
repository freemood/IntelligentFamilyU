package com.Intelligent.FamilyU.model.plugin.presenter;

import com.Intelligent.FamilyU.base.BasePresenter;
import com.Intelligent.FamilyU.http.observer.HttpRxCallback;
import com.Intelligent.FamilyU.model.plugin.bean.PluginListPageBean;
import com.Intelligent.FamilyU.model.plugin.bean.PluginPageEntity;
import com.Intelligent.FamilyU.model.plugin.biz.PluginListPageBiz;
import com.Intelligent.FamilyU.model.plugin.iface.IPluginListPageView;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.List;

public class PluginListPagePresenter extends BasePresenter<IPluginListPageView, LifecycleProvider> {
    private final String TAG = PluginListPagePresenter.class.getSimpleName();

    public PluginListPagePresenter(IPluginListPageView view, LifecycleProvider activity) {
        super(view, activity);
    }

    /**
     * 查询所有插件版本信息
     */
    public void pluginListQuery(String gatewayId) {

        if (getView() != null)
            getView().showLoading();


        HttpRxCallback httpRxCallback = new HttpRxCallback() {
            @Override
            public void onSuccess(Object... object) {
                if (getView() != null) {
                    getView().closeLoading();
                    getView().showResult((List<PluginPageEntity.PluginGatewayInfo>) object[0]);
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

        new PluginListPageBiz().pluginListQuery(gatewayId,getActivity(), httpRxCallback);
    }
}
