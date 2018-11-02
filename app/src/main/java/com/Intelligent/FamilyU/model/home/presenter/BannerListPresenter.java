package com.Intelligent.FamilyU.model.home.presenter;

import com.Intelligent.FamilyU.base.BasePresenter;
import com.Intelligent.FamilyU.http.observer.HttpRxCallback;
import com.Intelligent.FamilyU.model.home.biz.BannerListBiz;
import com.Intelligent.FamilyU.model.home.entity.BannerListBean;
import com.Intelligent.FamilyU.model.home.iface.IBannerListView;
import com.trello.rxlifecycle2.LifecycleProvider;

/**
 * banner presenter
 *
 */

public class BannerListPresenter extends BasePresenter<IBannerListView, LifecycleProvider> {

    private final String TAG = BannerListPresenter.class.getSimpleName();

    public BannerListPresenter(IBannerListView view, LifecycleProvider activity) {
        super(view, activity);
    }

    /**
     * 获取信息
     *
     */
    public void bannerListQuery() {

        //loading
        if (getView() != null)
            getView().showLoading();

        //请求
        new BannerListBiz().bannerListQuery(getActivity(), new HttpRxCallback() {
            @Override
            public void onSuccess(Object... object) {
                if (getView() != null) {
                    getView().closeLoading();
                    getView().showResult((BannerListBean) object[0]);
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
