package com.Intelligent.FamilyU.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.Intelligent.FamilyU.R;
import com.maywide.uap.Constants;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler{
	
	private static final String TAG = "WXPayEntryActivity";
	
	private IWXAPI api;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        api = WXAPIFactory.createWXAPI(this, getResources().getString(R.string.weixin_app_id));
        api.handleIntent(getIntent(), this);
    }
    
    @Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);

		Intent intent = new Intent();
		
		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			intent.putExtra("code", resp.errCode + "");
			intent.setAction(Constants.WEIXIN_PAY_ACTION);
			sendBroadcast(intent);
			
			finish();
		}
	}
}