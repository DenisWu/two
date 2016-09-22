package com.denis.phoneguard.activity;

import com.denis.phoneguard.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

/**
 * 手机防盗初始化页面1
 * 
 * @author yongda
 *
 */
public class Setup1Activity extends Activity {
	private GestureDetector mGestureDetector;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup1);
		GestureDetector mGestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
				if (Math.abs(e1.getRawY() - e2.getRawY()) > 200) {
					return false;
				}
				// 上一步
				if ((e2.getRawX() - e1.getRawX()) > 200) {

				}
				if ((e1.getRawX() - e2.getRawX()) > 200) {

				}
				return super.onFling(e1, e2, velocityX, velocityY);

			}
		});
	}

	/**
	 * 下一步
	 * 
	 * @param view
	 */
	public void next(View view) {
		Intent intent = new Intent(Setup1Activity.this, Setup2Activity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		startActivity(intent);
		this.finish();
		overridePendingTransition(R.anim.acitvity_next_enter, R.anim.acitvity_next_out);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		mGestureDetector.onTouchEvent(event);
		return super.onTouchEvent(event);
	}
}
