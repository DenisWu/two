package com.denis.phoneguard.activity;

import com.denis.phoneguard.AppConf;
import com.denis.phoneguard.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * �ֻ�������ҳ��
 * 
 * @author yongda
 *
 */
public class LostFindActivity extends Activity {
	private TextView tv_reset; // ���������ֻ�����
	private Context mContext;
	private TextView tv_findLostPhone; // �һ��ֻ��ĺ���
	private ImageView iv_isLock; // �Ƿ����ֻ�����

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		setContentView(R.layout.activity_lost_find);
		tv_reset = (TextView) findViewById(R.id.tv_reset);
		tv_findLostPhone = (TextView) findViewById(R.id.tv_findLostPhone);
		iv_isLock = (ImageView) findViewById(R.id.iv_isLock);
		tv_findLostPhone.setText(AppConf.getBindPhone(mContext));
		if (AppConf.getProtectON(mContext)) {
			iv_isLock.setImageResource(R.drawable.lock);
		} else {
			iv_isLock.setImageResource(R.drawable.unlock);
		}
		tv_reset.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, Setup1Activity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent);
				LostFindActivity.this.finish();
			}
		});
	}
}
