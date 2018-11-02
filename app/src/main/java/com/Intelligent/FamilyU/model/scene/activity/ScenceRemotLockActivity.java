package com.Intelligent.FamilyU.model.scene.activity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.Intelligent.FamilyU.R;
import com.Intelligent.FamilyU.base.BaseFragmentActivity;
import com.Intelligent.FamilyU.utils.ToastUtils;
import com.Intelligent.FamilyU.view.IBaseView;
import com.Intelligent.FamilyU.widget.RLoadingDialog;

import butterknife.BindView;
import butterknife.OnClick;

public class ScenceRemotLockActivity extends BaseFragmentActivity implements IBaseView {
    @BindView(R.id.page_title)
    TextView titleTv;

    @BindView(R.id.page_right_rl)
    RelativeLayout pageRightRlTv;
    @BindView(R.id.page_right_tv)
    TextView pageRightTv;

    @BindView(R.id.tv_code1)
    TextView tvCode1;
    @BindView(R.id.tv_code2)
    TextView tvCode2;
    @BindView(R.id.tv_code3)
    TextView tvCode3;
    @BindView(R.id.tv_code4)
    TextView tvCode4;
    @BindView(R.id.tv_code5)
    TextView tvCode5;
    @BindView(R.id.et_alias)
    EditText et;


    private RLoadingDialog mLoadingDialog;
    private StringBuffer sbStr = new StringBuffer();

    @Override
    protected int getContentViewId() {
        return R.layout.activity_scence_remote_lock;
    }

    @Override
    protected void initBundleData() {

    }


    @Override
    protected void initView() {
        registeredEventBus();
        mLoadingDialog = new RLoadingDialog(mContext, true);
        titleTv.setText(R.string.home_scence_window_lock);
        pageRightRlTv.setVisibility(View.VISIBLE);
        pageRightTv.setText(R.string.btn_save);


        /**
         * 输入内容监听，投射到5个空格上
         */
        et.setCursorVisible(false);// 再次点击显示光标

        TextWatcher edtCodeChange = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int leng = s.length();
                if (leng == 1 && !TextUtils.isEmpty(s)) {
                    sbStr.append(s);
                    et.setText("");
                }

                switch (sbStr.length()) {
                    case 5:
                        tvCode5.setText(sbStr.subSequence(4, 5));
                    case 4:
                        tvCode4.setText(sbStr.subSequence(3, 4));
                    case 3:
                        tvCode3.setText(sbStr.subSequence(2, 3));
                    case 2:
                        tvCode2.setText(sbStr.subSequence(1, 2));
                    case 1:
                        tvCode1.setText(sbStr.subSequence(0, 1));
                    default:
                        break;

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
//                tvCode1.setText("");
//                tvCode2.setText("");
//                tvCode3.setText("");
//                tvCode4.setText("");
//                tvCode5.setText("");
//                switch (s.length()) {
//                    case 5:
//                        tvCode5.setText(s.subSequence(4, 5));
//                    case 4:
//                        tvCode4.setText(s.subSequence(3, 4));
//                    case 3:
//                        tvCode3.setText(s.subSequence(2, 3));
//                    case 2:
//                        tvCode2.setText(s.subSequence(1, 2));
//                    case 1:
//                        tvCode1.setText(s.subSequence(0, 1));
//                    default:
//                        break;
//
//                }
//                //输入完5个验证码 自动请求验证
//                if (s.length() == 5) {
//                    // clickNext();
//                }
            }
        };
        et.addTextChangedListener(edtCodeChange);
    }

    @Override
    protected void initData() {
    }


    @OnClick({R.id.page_back, R.id.page_right_rl})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.page_back:
                finish();
                break;

            case R.id.page_right_rl:
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

