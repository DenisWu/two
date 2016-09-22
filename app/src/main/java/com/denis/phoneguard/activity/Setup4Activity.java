package com.denis.phoneguard.activity;

import com.denis.phoneguard.AppConf;
import com.denis.phoneguard.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

/**
 * 手机防盗初始化页面1
 * 
 * @author yongda
 *
 */
public class Setup4Activity extends Activity {
	private Context mContext;
	private CheckBox cb_check;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		setContentView(R.layout.activity_setup4);
		cb_check = (CheckBox) findViewById(R.id.cb_check);
		if (AppConf.getProtectON(mContext)) {
			cb_check.setChecked(true);
			cb_check.setText("您已经开启手机防盗保护");
		} else {
			cb_check.setChecked(false);
			cb_check.setText("您没有开启手机防盗保护");
		}
		cb_check.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					cb_check.setText("您已经开启手机防盗保护");
				} else {
					cb_check.setText("您没有开启手机防盗保护");
				}
			}
		});
	}

	/**
	 * 设置完成
	 * 
	 * @param view
	 */
	public void finishSetup(View view) {
		AppConf.setIsSetup(mContext, true);
		AppConf.setProtectON(mContext, cb_check.isChecked());
		Intent intent = new Intent(Setup4Activity.this, LostFindActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
		this.finish();
		overridePendingTransition(R.anim.acitvity_next_enter, R.anim.acitvity_next_out);
	}

	/**
	 * 上一步
	 */
	public void previous(View view) {
		Intent intent = new Intent(Setup4Activity.this, Setup3Activity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		startActivity(intent);
		this.finish();
		overridePendingTransition(R.anim.acitvity_previous_enter, R.anim.acitvity_previous_out);
	}
}
