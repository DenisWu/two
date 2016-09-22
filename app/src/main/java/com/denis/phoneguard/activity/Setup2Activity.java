package com.denis.phoneguard.activity;

import com.denis.phoneguard.AppConf;
import com.denis.phoneguard.R;
import com.denis.phoneguard.view.UpdateRelativeLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

/**
 * 手机防盗初始化页面1
 * 
 * @author yongda
 *
 */
public class Setup2Activity extends Activity {
	private UpdateRelativeLayout ur_bindSIM;
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		setContentView(R.layout.activity_setup2);
		ur_bindSIM = (UpdateRelativeLayout) findViewById(R.id.ur_bindSIM);
		if (AppConf.getBindSIM(context)) {
			ur_bindSIM.setChecked(true);
			ur_bindSIM.setDetail("已经绑定SIM卡");
		} else {
			ur_bindSIM.setChecked(false);
			ur_bindSIM.setDetail("SIM卡没有绑定");
		}
		ur_bindSIM.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (ur_bindSIM.isChecked()) {
					ur_bindSIM.setChecked(false);
					ur_bindSIM.setDetail("SIM卡没有绑定");
				} else {
					ur_bindSIM.setChecked(true);
					ur_bindSIM.setDetail("已经绑定SIM卡");
				}
			}
		});
	}

	/**
	 * 下一步
	 * 
	 * @param view
	 */
	public void next(View view) {
		if (ur_bindSIM.isChecked()) {
			AppConf.setBindSIM(context, true);
		} else {
			Toast.makeText(context, "必须要绑定SIM卡", Toast.LENGTH_SHORT).show();
			return;
		}
		TelephonyManager teleManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		String simSerialNum = teleManager.getSimSerialNumber();
		Log.v("Shortcut", "SIM卡序列号：" + simSerialNum);
		AppConf.setSIMSerialNum(context, simSerialNum);
		Intent intent = new Intent(Setup2Activity.this, Setup3Activity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		startActivity(intent);
		this.finish();
		overridePendingTransition(R.anim.acitvity_next_enter, R.anim.acitvity_next_out);
	}

	/**
	 * 上一步
	 */
	public void previous(View view) {
		Intent intent = new Intent(Setup2Activity.this, Setup1Activity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		startActivity(intent);
		this.finish();
		overridePendingTransition(R.anim.acitvity_previous_enter, R.anim.acitvity_previous_out);
	}
}
