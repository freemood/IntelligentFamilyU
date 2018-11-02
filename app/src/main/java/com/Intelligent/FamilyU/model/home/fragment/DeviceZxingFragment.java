package com.Intelligent.FamilyU.model.home.fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.Intelligent.FamilyU.R;
import com.Intelligent.FamilyU.base.BaseFragment;
import com.Intelligent.FamilyU.base.Constants;
import com.Intelligent.FamilyU.model.gateway.activity.GatewayAddActivity;
import com.Intelligent.FamilyU.model.gateway.activity.GatewayAddOkActivity;
import com.Intelligent.FamilyU.model.network.activity.NetWorkAddOkActivity;
import com.Intelligent.FamilyU.model.scene.activity.ScenceAddOkActivity;
import com.Intelligent.FamilyU.utils.PhotoUtils;
import com.Intelligent.FamilyU.utils.ToastUtils;
import com.Intelligent.FamilyU.view.IBaseView;
import com.Intelligent.FamilyU.widget.RLoadingDialog;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Vibrator;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.bingoogolapple.qrcode.core.BarcodeType;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zbar.ZBarView;

/**
 * 添加设备Fragment
 */
public class DeviceZxingFragment extends BaseFragment implements IBaseView, QRCodeView.Delegate {
    private static final String TAG = DeviceZxingFragment.class.getSimpleName();
    @BindView(R.id.zbarview)
    ZBarView mZBarView;
    private RLoadingDialog mLoadingDialog;


    @Override
    protected View getContentView() {
        return LayoutInflater.from(mContext).inflate(R.layout.activity_test_scan, null);
    }

    @Override
    protected void initBundleData() {

    }

    @Override
    protected void initView() {
        mLoadingDialog = new RLoadingDialog(mContext, true);
        mZBarView.setDelegate(this);
    }

    @Override
    protected void initData() {

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

    @Override
    public void onStart() {
        super.onStart();
        mZBarView.startCamera(); // 打开后置摄像头开始预览，但是并未开始识别
//        mZBarView.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT); // 打开前置摄像头开始预览，但是并未开始识别

        mZBarView.startSpotAndShowRect(); // 显示扫描框，并且延迟0.5秒后开始识别
    }

    @Override
    public void onStop() {
        mZBarView.stopCamera(); // 关闭摄像头预览，并且隐藏扫描框
        super.onStop();
    }

    @Override
    public void onDestroy() {
        mZBarView.onDestroy(); // 销毁二维码扫描控件
        super.onDestroy();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        Log.i(TAG, "result:" + result);
        // setTitle("扫描结果为：" + result);
        ToastUtils.showToast(mContext, "扫描结果为：" + result);
        if (TextUtils.isEmpty(result)) {
            return;
        }
        if (result.length() == 19) {
            Constants.SERIAL_NO = result;
            startActivity(new Intent(mContext, GatewayAddOkActivity.class));
        } else if (result.length() == 12) {
            Constants.NETWORK_NO = result;
            startActivity(new Intent(mContext, NetWorkAddOkActivity.class));
        } else {
            Constants.SCENCE_NO = result;
            startActivity(new Intent(mContext, ScenceAddOkActivity.class));
        }

        vibrate();

        mZBarView.startSpot(); // 延迟0.5秒后开始识别
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.e(TAG, "打开相机出错");
    }


    @OnClick({R.id.choose_qrcde_from_gallery})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_preview:
                mZBarView.startCamera(); // 打开后置摄像头开始预览，但是并未开始识别
                break;
            case R.id.stop_preview:
                mZBarView.stopCamera(); // 关闭摄像头预览，并且隐藏扫描框
                break;
            case R.id.start_spot:
                mZBarView.startSpot(); // 延迟0.5秒后开始识别
                break;
            case R.id.stop_spot:
                mZBarView.stopSpot(); // 停止识别
                break;
            case R.id.start_spot_showrect:
                mZBarView.startSpotAndShowRect(); // 显示扫描框，并且延迟0.5秒后开始识别
                break;
            case R.id.stop_spot_hiddenrect:
                mZBarView.stopSpotAndHiddenRect(); // 停止识别，并且隐藏扫描框
                break;
            case R.id.show_scan_rect:
                mZBarView.showScanRect(); // 显示扫描框
                break;
            case R.id.hidden_scan_rect:
                mZBarView.hiddenScanRect(); // 隐藏扫描框
                break;
            case R.id.decode_scan_box_area:
                mZBarView.getScanBoxView().setOnlyDecodeScanBoxArea(true); // 仅识别扫描框中的码
                break;
            case R.id.decode_full_screen_area:
                mZBarView.getScanBoxView().setOnlyDecodeScanBoxArea(false); // 识别整个屏幕中的码
                break;
            case R.id.open_flashlight:
                mZBarView.openFlashlight(); // 打开闪光灯
                break;
            case R.id.close_flashlight:
                mZBarView.closeFlashlight(); // 关闭闪光灯
                break;
            case R.id.scan_one_dimension:
                mZBarView.changeToScanBarcodeStyle(); // 切换成扫描条码样式
                mZBarView.setType(BarcodeType.ONE_DIMENSION, null); // 只识别一维条码
                mZBarView.startSpotAndShowRect(); // 显示扫描框，并且延迟0.5秒后开始识别
                break;
            case R.id.scan_two_dimension:
                mZBarView.changeToScanQRCodeStyle(); // 切换成扫描二维码样式
                mZBarView.setType(BarcodeType.TWO_DIMENSION, null); // 只识别二维条码
                mZBarView.startSpotAndShowRect(); // 显示扫描框，并且延迟0.5秒后开始识别
                break;
            case R.id.scan_qr_code:
                mZBarView.changeToScanQRCodeStyle(); // 切换成扫描二维码样式
                mZBarView.setType(BarcodeType.ONLY_QR_CODE, null); // 只识别 QR_CODE
                mZBarView.startSpotAndShowRect(); // 显示扫描框，并且延迟0.5秒后开始识别
                break;
            case R.id.scan_code128:
                mZBarView.changeToScanBarcodeStyle(); // 切换成扫描条码样式
                mZBarView.setType(BarcodeType.ONLY_CODE_128, null); // 只识别 CODE_128
                mZBarView.startSpotAndShowRect(); // 显示扫描框，并且延迟0.5秒后开始识别
                break;
            case R.id.scan_ean13:
                mZBarView.changeToScanBarcodeStyle(); // 切换成扫描条码样式
                mZBarView.setType(BarcodeType.ONLY_EAN_13, null); // 只识别 EAN_13
                mZBarView.startSpotAndShowRect(); // 显示扫描框，并且延迟0.5秒后开始识别
                break;
            case R.id.scan_high_frequency:
                mZBarView.changeToScanQRCodeStyle(); // 切换成扫描二维码样式
                mZBarView.setType(BarcodeType.HIGH_FREQUENCY, null); // 只识别高频率格式，包括 QR_CODE、EAN_13、CODE_128
                mZBarView.startSpotAndShowRect(); // 显示扫描框，并且延迟0.5秒后开始识别
                break;
            case R.id.scan_all:
                mZBarView.changeToScanQRCodeStyle(); // 切换成扫描二维码样式
                mZBarView.setType(BarcodeType.ALL, null); // 识别所有类型的码
                mZBarView.startSpotAndShowRect(); // 显示扫描框，并且延迟0.5秒后开始识别
                break;
            case R.id.scan_custom:
                mZBarView.changeToScanQRCodeStyle(); // 切换成扫描二维码样式

                List<cn.bingoogolapple.qrcode.zbar.BarcodeFormat> formatList = new ArrayList<>();
                formatList.add(cn.bingoogolapple.qrcode.zbar.BarcodeFormat.QRCODE);
                formatList.add(cn.bingoogolapple.qrcode.zbar.BarcodeFormat.EAN13);
                formatList.add(cn.bingoogolapple.qrcode.zbar.BarcodeFormat.CODE128);
                mZBarView.setType(BarcodeType.CUSTOM, formatList); // 自定义识别的类型

                mZBarView.startSpotAndShowRect(); // 显示扫描框，并且延迟0.5秒后开始识别
                break;

            case R.id.choose_qrcde_from_gallery:
                /*
                从相册选取二维码图片，这里为了方便演示，使用的是
                https://github.com/bingoogolapple/BGAPhotoPicker-Android
                这个库来从图库中选择二维码图片，这个库不是必须的，你也可以通过自己的方式从图库中选择图片
                 */
//                Intent photoPickerIntent = new BGAPhotoPickerActivity.IntentBuilder(mContext)
//                        .cameraFileDir(null)
//                        .maxChooseCount(1)
//                        .selectedPhotos(null)
//                        .pauseOnScroll(false)
//                        .build();
//                startActivityForResult(photoPickerIntent, Constants.REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY);

                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, Constants.REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY);
                break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        mZBarView.showScanRect();

        if (resultCode == Activity.RESULT_OK && requestCode == Constants.REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY) {
            if (null != data) {
                Uri uri = data.getData();
                final String picturePath = PhotoUtils.getPath(mContext, uri);
                mZBarView.decodeQRCode(picturePath.replace("file:///", ""));
            }
        }
    }
}