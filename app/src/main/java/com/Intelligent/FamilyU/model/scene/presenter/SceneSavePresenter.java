package com.Intelligent.FamilyU.model.scene.presenter;

import com.Intelligent.FamilyU.base.BasePresenter;
import com.Intelligent.FamilyU.http.observer.HttpRxCallback;
import com.Intelligent.FamilyU.model.scene.biz.ScenceSaveBiz;
import com.Intelligent.FamilyU.model.scene.biz.ScoketUntieBiz;
import com.Intelligent.FamilyU.model.scene.entity.ScenceSaveBean;
import com.Intelligent.FamilyU.model.scene.entity.SceneExecCondition;
import com.Intelligent.FamilyU.model.scene.entity.SceneOperation;
import com.Intelligent.FamilyU.model.scene.entity.ScoketRestartBean;
import com.Intelligent.FamilyU.model.scene.iface.IScenceSaveView;
import com.Intelligent.FamilyU.model.scene.iface.ISocketUntieView;
import com.trello.rxlifecycle2.LifecycleProvider;

import org.json.JSONArray;

import java.util.List;
import java.util.TreeMap;

import okhttp3.RequestBody;

/**
 * 保存修改 presenter
 */

public class SceneSavePresenter extends BasePresenter<IScenceSaveView, LifecycleProvider> {

    private final String TAG = SceneSavePresenter.class.getSimpleName();

    public SceneSavePresenter(IScenceSaveView view, LifecycleProvider activity) {
        super(view, activity);
    }

    /**
     * 获取信息
     */
    public void scenceSave(String serialNo, String id, String sceneName, List<SceneOperation> operations, List<SceneExecCondition> conditions, String sceneType) {

        //loading
        if (getView() != null)
            getView().showLoading();

        //请求
        new ScenceSaveBiz().scenceSave(serialNo, id, sceneName, operations, conditions, sceneType, getActivity(), new HttpRxCallback() {
            @Override
            public void onSuccess(Object... object) {
                if (getView() != null) {
                    getView().closeLoading();
                    getView().showResult((ScenceSaveBean) object[0]);
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
