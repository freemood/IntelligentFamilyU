package com.Intelligent.FamilyU.model.my.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.Intelligent.FamilyU.R;
import com.Intelligent.FamilyU.base.BaseFragmentActivity;
import com.Intelligent.FamilyU.model.my.entity.ProblemReportBean;
import com.Intelligent.FamilyU.model.my.entity.UpdateAppBean;
import com.Intelligent.FamilyU.model.my.iface.IProblemView;
import com.Intelligent.FamilyU.model.my.iface.IUpdateAppView;
import com.Intelligent.FamilyU.model.my.persenter.ProblemReportPresenter;
import com.Intelligent.FamilyU.model.my.upload.ApiUtil;
import com.Intelligent.FamilyU.utils.ToastUtils;
import com.Intelligent.FamilyU.widget.RLoadingDialog;
import com.google.gson.JsonObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.adapter.rxjava2.Result;

import static android.support.constraint.Constraints.TAG;

public class ProblemReportActivity extends BaseFragmentActivity implements IProblemView {

    @BindView(R.id.page_title)
    TextView titleTv;
    @BindView(R.id.page_right_rl)
    RelativeLayout pageRightRlTv;
    @BindView(R.id.page_right_tv)
    TextView pageRightTv;

    @BindView(R.id.type)
    Spinner typeTv;
    @BindView(R.id.message)
    TextView messageTv;
    @BindView(R.id.number)
    TextView numberTv;

    @BindView(R.id.photo1_tv)
    TextView photo1Tv;
    @BindView(R.id.photo2_tv)
    TextView photo2Tv;
    @BindView(R.id.photo3_tv)
    TextView photo3Tv;
    @BindView(R.id.photo4_tv)
    TextView photo4Tv;

    private RLoadingDialog mLoadingDialog;
    private String problemType;
    private List<String> dataList = new ArrayList<String>();
    private String[] array;
    private String mPictureFile;
    private String filePath;
    private List<String> pathList = new ArrayList<>();
    private ArrayList<String> upPathList = new ArrayList<>();
    private ProblemReportPresenter mProblemReportPresenter = new ProblemReportPresenter(this, this);

    @Override
    protected int getContentViewId() {
        return R.layout.activity_report_submit;
    }

    @Override
    protected void initBundleData() {

    }

    @Override
    protected void initView() {
        mLoadingDialog = new RLoadingDialog(mContext, true);
        //  applyWritePermission();

        titleTv.setText(R.string.home_my_feedback);
        pageRightRlTv.setVisibility(View.VISIBLE);
        pageRightTv.setText(R.string.submit);
        String memoryStr = mContext.getResources().getString(R.string.home_my_feedback_max_length);
        String result = String.format(memoryStr, String.valueOf(0));
        numberTv.setText(result.replace("%", ""));
//        String result = String.format(memoryStr, map.get("memory"));
        messageTv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String memoryStr = mContext.getResources().getString(R.string.home_my_feedback_max_length);
                String result = String.format(memoryStr, String.valueOf(charSequence.length()));
                numberTv.setText(result.replace("%", ""));
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        initAdpter();
    }

    private void initAdpter() {
        array = getResources().getStringArray(R.array.spnner_value);
        problemType = array[0];
        // 声明一个ArrayAdapter用于存放简单数据
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ProblemReportActivity.this, android.R.layout.simple_spinner_item, getData());
        typeTv.setAdapter(adapter);
        // 为第一个Spinner设定选中事件
        typeTv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // 在选中之后触发
                String type = (String) parent.getItemAtPosition(position);
                int size = dataList.size();
                for (int i = 0; i < size; i++) {
                    if (type.equals(dataList.get(i))) {
                        problemType = array[i];
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 这个一直没有触发，我也不知道什么时候被触发。
                //在官方的文档上说明，为back的时候触发，但是无效，可能需要特定的场景
            }
        });
    }

    private List<String> getData() {
// 数据源 'OTHER', 'PLUGINS', 'GATEWAY', 'NETWORKING', 'HOME', 'SHOP'
        String[] array = getResources().getStringArray(R.array.data_value);
        int size = array.length;
        for (int i = 0; i < size; i++) {
            dataList.add(array[i]);
        }
//        dataList.add("网关设备");
//        dataList.add("组网设备");
//        dataList.add("家居设备");
//        dataList.add("插件设备");
//        dataList.add("商城问题");
//        dataList.add("其他问题");
        return dataList;
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.page_right_rl, R.id.page_back, R.id.photo1, R.id.photo2, R.id.photo3, R.id.photo4})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.page_right_rl:
                if (null == pathList || pathList.size() == 0) {
                    mProblemReportPresenter.remoteStartTaskQuery(problemType, messageTv.getText().toString(), upPathList);
                    return;
                }
                showLoading();
                final int path = pathList.size();
                for (int i = 0; i < path; i++) {
                    ApiUtil.uploadMemberIcon(pathList.get(i)).enqueue(new Callback<Result<String>>() {//返回结果
                        @Override
                        public void onResponse(Call<Result<String>> call, Response<Result<String>> response) {
                            if (response.isSuccessful()) {
                                Request result = response.raw().request();
                                upPathList.add(result.url().toString());
                                Log.i(TAG, "上传成功啦" + response.toString());
                                if (path == upPathList.size()) {
                                    mProblemReportPresenter.remoteStartTaskQuery(problemType, messageTv.getText().toString(), upPathList);
                                }
                            } else {
                                Log.i(TAG, "上传失败啦");
                                closeLoading();
                            }

                        }

                        @Override
                        public void onFailure(Call<Result<String>> call, Throwable t) {
                            closeLoading();
                            finish();
                        }
                    });
                }

                break;
            case R.id.photo1:
                applyWritePermission();
                break;
            case R.id.photo2:
                applyWritePermission();
                break;
            case R.id.photo3:
                applyWritePermission();
                break;
            case R.id.photo4:
                applyWritePermission();
                break;
            case R.id.page_back:
                finish();
                break;
        }
    }

    @Override
    public void showResult(ProblemReportBean bean) {
        if (null == bean) {
            return;
        }
        showToast("提交成功");
        finish();
    }

    public void applyWritePermission() {

        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};


        if (Build.VERSION.SDK_INT >= 23) {
            int check = ContextCompat.checkSelfPermission(this, permissions[0]);
            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
            if (check == PackageManager.PERMISSION_GRANTED) {
                //调用相机
                useCamera();
            } else {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        } else {
            useCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            useCamera();
        } else {
            // 没有获取 到权限，从新请求，或者关闭app
            Toast.makeText(this, "需要存储权限", Toast.LENGTH_SHORT).show();
        }

    }


    /**
     * 使用相机
     */
    private void useCamera() {
        // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // 调用系统相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        // 取当前时间为照片名
        mPictureFile = DateFormat.format("yyyyMMdd_hhmmss",
                Calendar.getInstance(Locale.CHINA))
                + ".jpg";
        filePath = getPhotoPath() + mPictureFile;
        // 通过文件创建一个uri中
        Uri imageUri = Uri.fromFile(new File(filePath));
        // 保存uri对应的照片于指定路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, 1);


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("filePath", filePath);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (TextUtils.isEmpty(filePath)) {
            filePath = savedInstanceState.getString("filePath");
        }
    }


    /**
     * 获得照片路径
     *
     * @return
     */
    private String getPhotoPath() {
        return Environment.getExternalStorageDirectory() + "/DCIM/";
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bitmap bitmap = BitmapFactory.decodeFile(filePath);
            pathList.add(filePath);
            Drawable drawable = new BitmapDrawable(thumbImageWithInSampleSize(bitmap.getWidth(), bitmap.getHeight()));
            int size = pathList.size();
            if (1 == size) {
                photo1Tv.setBackground(drawable);
            } else if (2 == size) {
                photo2Tv.setBackground(drawable);
            } else if (3 == size) {
                photo3Tv.setBackground(drawable);
            } else if (4 == size) {
                photo4Tv.setBackground(drawable);
            }
        }
    }

    public Bitmap thumbImageWithInSampleSize(float destWidth, float destHeight) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(filePath, opt);
        double scaleW = Math.max(destWidth, opt.outWidth) / (Math.min(destWidth, opt.outWidth) * 1.0);
        double scaleH = Math.max(destHeight, opt.outHeight) / (Math.min(destHeight, opt.outHeight) * 1.0);
        opt.inSampleSize = (int) Math.max(scaleW, scaleH);

        opt.inJustDecodeBounds = false;

        Bitmap bitmap = BitmapFactory.decodeFile(filePath, opt);
        return bitmap;
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
