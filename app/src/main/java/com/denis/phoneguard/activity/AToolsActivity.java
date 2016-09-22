package com.denis.phoneguard.activity;

import com.denis.phoneguard.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 *�߼�����
 * 
 * @author yongda
 *
 */
public class AToolsActivity extends Activity implements OnClickListener {
	private TextView tv_checkAddress;
	private TextView tv_backupSMS;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_atools);
		tv_backupSMS = (TextView) findViewById(R.id.tv_backupSMS);
		tv_checkAddress = (TextView) findViewById(R.id.tv_checkAddress);
		tv_checkAddress.setOnClickListener(this);
		tv_backupSMS.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_checkAddress:
			startActivity(new Intent(this, CheckAddressActivity.class));
			break;
		case R.id.tv_backupSMS:

			break;

		default:
			break;
		}
	}
}
