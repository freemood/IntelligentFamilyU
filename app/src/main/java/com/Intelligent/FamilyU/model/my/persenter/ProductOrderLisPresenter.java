package com.Intelligent.FamilyU.model.my.persenter;

import com.Intelligent.FamilyU.base.BasePresenter;
import com.Intelligent.FamilyU.http.observer.HttpRxCallback;
import com.Intelligent.FamilyU.model.my.biz.ProductOrderListBiz;
import com.Intelligent.FamilyU.model.my.entity.ProductOrderListBean;
import com.Intelligent.FamilyU.model.my.iface.IProductOrderView;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.ArrayList;

/**
 */

public class ProductOrderLisPresenter extends BasePresenter<IProductOrderView, LifecycleProvider> {

    private final String TAG = ProductOrderLisPresenter.class.getSimpleName();

    public ProductOrderLisPresenter(IProductOrderView view, LifecycleProvider activity) {
        super(view, activity);
    }

    /**
     * 获取信息
     */
    public void productOrderLis(String serialNo) {

        //loading
        if (getView() != null)
            getView().showLoading();

        //请求
        new ProductOrderListBiz().productOrderList(serialNo, getActivity(), new HttpRxCallback() {
            @Override
            public void onSuccess(Object... object) {
                if (getView() != null) {
                    getView().closeLoading();
                    getView().showResult((ProductOrderListBean) object[0]);
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
