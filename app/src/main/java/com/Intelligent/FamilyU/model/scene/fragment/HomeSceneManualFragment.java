package com.Intelligent.FamilyU.model.scene.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.Intelligent.FamilyU.R;
import com.Intelligent.FamilyU.base.BaseFragment;
import com.Intelligent.FamilyU.model.scene.activity.ScenceEditDelutListActivity;
import com.Intelligent.FamilyU.model.scene.entity.ScenceDefultListBean;
import com.Intelligent.FamilyU.model.scene.entity.SceneOperation;
import com.Intelligent.FamilyU.model.scene.entity.VolSceneEntity;
import com.Intelligent.FamilyU.model.scene.iface.IHomeCustomScenceView;
import com.Intelligent.FamilyU.model.scene.presenter.HomeScenceCustomListPresenter;
import com.Intelligent.FamilyU.utils.ToastUtils;
import com.Intelligent.FamilyU.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 场景Fragment
 */
public class HomeSceneManualFragment extends BaseFragment implements IHomeCustomScenceView {

    @BindView(R.id.scene_sleep_pattern)
    TextView sceneSleepPatternTv;
    @BindView(R.id.scene_getup_pattern_tv)
    TextView sceneGetupPatternTv;
    @BindView(R.id.scene_gethome_pattern_tv)
    TextView sceneGethomePatternTv;
    @BindView(R.id.scene_awayhome_pattern_tv)
    TextView sceneAwayhomePatternTv;

    @BindView(R.id.scene_sleep_number_tv)
    TextView sceneSleepNumberTv;
    @BindView(R.id.scene_getup_number_tv)
    TextView sceneGetupNumberTv;
    @BindView(R.id.scene_gethome_number_tv)
    TextView sceneGethomeNumberTv;
    @BindView(R.id.scene_awayhome_number_tv)
    TextView sceneAwayhomeNumberTv;
    //private HomeScenceDefultListPresenter mHomeScenceDefultListPresenter = new HomeScenceDefultListPresenter(this, this);
    private HomeScenceCustomListPresenter mHomeScenceCustomListPresenter = new HomeScenceCustomListPresenter(this, this);
    // private RLoadingDialog mLoadingDialog;
    private String mSerialNo;
    private List<VolSceneEntity> list;
    private VolSceneEntity sleepEntitySleep = new VolSceneEntity();
    private VolSceneEntity getupEntitySleep = new VolSceneEntity();
    private VolSceneEntity gethomeEntitySleep = new VolSceneEntity();
    private VolSceneEntity awayhomeEntitySleep = new VolSceneEntity();

    @Override
    protected View getContentView() {
        return LayoutInflater.from(mContext).inflate(R.layout.activity_manual_sence, null);
    }

    @Override
    protected void initBundleData() {

    }

    @Override
    protected void initView() {
        //   mLoadingDialog = new RLoadingDialog(mContext, true);

    }

    @Override
    protected void initData() {

    }

    @Override
    public void showResult(ScenceDefultListBean bean) {
        if (null == bean) {
            return;
        }
        int sleepNumber = 0;
        int getupNumber = 0;
        int gethomeNumber = 0;
        int awayhomeNumber = 0;

        list = bean.getList();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            VolSceneEntity volSceneEntity = list.get(i);
            List<SceneOperation> operationsList = volSceneEntity.getOperations();
            if (null == operationsList) {
                operationsList = new ArrayList<>();
            }
            String sceneName = volSceneEntity.getSceneName();
            if (sceneName.contains("睡眠")) {
                sleepEntitySleep = volSceneEntity;
                sleepNumber = operationsList.size();
            } else if (sceneName.contains("起床")) {
                getupEntitySleep = volSceneEntity;
                getupNumber = operationsList.size();
            } else if (sceneName.contains("回家")) {
                gethomeEntitySleep = volSceneEntity;
                gethomeNumber = operationsList.size();
            } else if (sceneName.contains("离家")) {
                awayhomeEntitySleep = volSceneEntity;
                awayhomeNumber = operationsList.size();
            }
        }
        if (0 != sleepNumber) {
            sceneSleepNumberTv.setText(mContext.getResources().getString(R.string.home_scence_defult_number_decen, sleepNumber));
        }
        if (0 != getupNumber) {
            sceneGetupNumberTv.setText(mContext.getResources().getString(R.string.home_scence_defult_number_decen, getupNumber));
        }
        if (0 != gethomeNumber) {
            sceneGethomeNumberTv.setText(mContext.getResources().getString(R.string.home_scence_defult_number_decen, gethomeNumber));
        }
        if (0 != awayhomeNumber) {
            sceneAwayhomeNumberTv.setText(mContext.getResources().getString(R.string.home_scence_defult_number_decen, awayhomeNumber));
        }
    }

    @OnClick({R.id.scene_sleep_pattern_rl, R.id.scene_getup_pattern_rl, R.id.scene_gethome_pattern_rl, R.id.scene_awayhome_pattern_rl, R.id.scene_sleep_number_shoudong_tv, R.id.scene_getup_number_shoudong_tv, R.id.scene_gethome_number_shoudong_tv, R.id.scene_awayhome_number_shoudong_tv})
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.scene_sleep_pattern_rl:
                sleepEntitySleep.setTitle(sceneSleepPatternTv.getText().toString());
                EventBus.getDefault().postSticky(sleepEntitySleep);
                startActivity(new Intent(mContext, ScenceEditDelutListActivity.class));
                break;
            case R.id.scene_getup_pattern_rl:
                getupEntitySleep.setTitle(sceneGetupPatternTv.getText().toString());
                EventBus.getDefault().postSticky(getupEntitySleep);
                startActivity(new Intent(mContext, ScenceEditDelutListActivity.class));
                break;
            case R.id.scene_gethome_pattern_rl:
                gethomeEntitySleep.setTitle(sceneGethomePatternTv.getText().toString());
                EventBus.getDefault().postSticky(gethomeEntitySleep);
                startActivity(new Intent(mContext, ScenceEditDelutListActivity.class));
                break;
            case R.id.scene_awayhome_pattern_rl:
                awayhomeEntitySleep.setTitle(sceneAwayhomePatternTv.getText().toString());
                EventBus.getDefault().postSticky(awayhomeEntitySleep);
                startActivity(new Intent(mContext, ScenceEditDelutListActivity.class));
                break;
            case R.id.scene_sleep_number_shoudong_tv:
            case R.id.scene_getup_number_shoudong_tv:
            case R.id.scene_gethome_number_shoudong_tv:
            case R.id.scene_awayhome_number_shoudong_tv:
                showToast("手动执行完成");
                break;
        }
    }

    private void initQuery() {
        StringBuffer sb = new StringBuffer();
        sb.append(Utils.readSharedPreferences(getResources().getString(R.string.home_login_name))).append("_").append("serialNo");
        mSerialNo = Utils.readSharedPreferences(sb.toString());
        if (TextUtils.isEmpty(mSerialNo)) {
            return;
        }
        //mHomeScenceDefultListPresenter.scenceDefultListQuery(mSerialNo);
        mHomeScenceCustomListPresenter.scenceDefultListQuery(mSerialNo);
    }

    @Override
    public void onResume() {
        super.onResume();
        initQuery();
    }

    @Override
    public void showLoading() {
        //   mLoadingDialog.show();
    }

    @Override
    public void closeLoading() {
        //   mLoadingDialog.dismiss();
    }

    @Override
    public void showToast(String msg) {
        ToastUtils.showToast(mContext, msg);
    }
}
