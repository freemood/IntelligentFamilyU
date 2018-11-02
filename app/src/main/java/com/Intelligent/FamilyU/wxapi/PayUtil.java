package com.Intelligent.FamilyU.wxapi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import com.Intelligent.FamilyU.base.BaseApplication;
import com.maywide.uap.Constants;
import com.maywide.uap.api.PayApi;

public class PayUtil{
	private  final String TAG = getClass().getSimpleName();
	
	private Receiver receiver = new Receiver();
	
	public void pay() {
		PayApi.pay(BaseApplication.getContext(), "http://wt.maywide.com/payplatform/paytv/payplat!gateway?clienttype=02&servicecode=Pay&clientcode=1007&clientpwd=68ba07abf0d370630620ea43c68fabbd&requestid=10072016120700000021&requestContent=%7B%22orderInfo%22%3A%7B%22orderNo%22%3A%221312213%22%2C%22productList%22%3A%5B%7B%22keyno%22%3A%2240812326920%22%2C%22productName%22%3A%22%E6%AC%A0%E8%B4%B9%E7%BC%B4%E7%BA%B3%22%2C%22count%22%3A%221%22%2C%22fee%22%3A0.01%7D%5D%7D%2C%22custInfo%22%3A%7B%22custname%22%3A%22%E6%9D%9C%E9%89%B4%E5%9F%B9%22%2C%22custid%22%3A%223600840753%22%2C%22gdNo%22%3A%22%22%2C%22city%22%3A%22CY%22%2C%22area%22%3A%22681%22%2C%22cardNo%22%3A%2240812326920%22%2C%22servid%22%3Anull%7D%2C%22totalFee%22%3A0.01%2C%22payments%22%3A%2202~03~04%22%2C%22redirectUrl%22%3A%22http%3A%5C%2F%5C%2Fwww.96956.com.cn%5C%2Fmcrweb%5C%2Fpages%5C%2Faddrarrear%5C%2FaddrarrearSuccess.shtml%3Fisorder%3Dnull%26orderNo%3D1312213%22%2C%22noticeAction%22%3A%22http%3A%5C%2F%5C%2F210.21.65.82%5C%2Fmcrapp%5C%2Fmcr%5C%2Fcustserv%5C%2Fproduct!payBack%22%2C%22orderNum%22%3A%221312213%22%7D&dataSign=99FC3180C3762596AFC8864ECB500B1A");
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(Constants.WEIXIN_PAY_RESULT);
		BaseApplication.getContext().registerReceiver(receiver, intentFilter);
	}

	public void destroyPay() {
		BaseApplication.getContext().unregisterReceiver(receiver);
	}
	
	private class Receiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent data) {
			if (data.getAction().equals(Constants.WEIXIN_PAY_RESULT)) {
				Log.e(TAG, "resultCode = " + data.getStringExtra("resultCode")
						 + ", orderNo = " + data.getStringExtra("orderNo"));
			}
		}
	}
}
