package com.Intelligent.FamilyU.model.my.persenter;

import com.Intelligent.FamilyU.base.BasePresenter;
import com.Intelligent.FamilyU.http.observer.HttpRxCallback;
import com.Intelligent.FamilyU.model.my.biz.ProblemReportBiz;
import com.Intelligent.FamilyU.model.my.entity.ProblemReportBean;
import com.Intelligent.FamilyU.model.my.iface.IProblemView;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.ArrayList;

/**
 */

public class ProblemReportPresenter extends BasePresenter<IProblemView, LifecycleProvider> {

    private final String TAG = ProblemReportPresenter.class.getSimpleName();

    public ProblemReportPresenter(IProblemView view, LifecycleProvider activity) {
        super(view, activity);
    }


    /**
     * 获取信息
     */
    public void remoteStartTaskQuery(String problemType, String description, ArrayList<String> list) {

        //loading
        if (getView() != null)
            getView().showLoading();

        //请求
        new ProblemReportBiz().upProblemReport(problemType, description, list, getActivity(), new HttpRxCallback() {
            @Override
            public void onSuccess(Object... object) {
                if (getView() != null) {
                    getView().closeLoading();
                    getView().showResult((ProblemReportBean) object[0]);
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
