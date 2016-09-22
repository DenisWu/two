package com.denis.phoneguard.activity;

import java.lang.reflect.Field;

import com.denis.phoneguard.AppConf;
import com.denis.phoneguard.R;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.opengl.Visibility;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 调整来电显示位置的activity
 * 
 * @author o
 *
 */
public class ShowLocationActivity extends Activity {
	private TextView tv_top;
	private TextView tv_bottom;
	private LinearLayout ll_toast;
	private WindowManager wm;
	private Context context;
	private int height;
	private int width;
	private int startX;
	private int startY;
	private int endX;
	private int endY;
	RelativeLayout.LayoutParams params;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		wm = (WindowManager) getSystemService(WINDOW_SERVICE);
		context = this;
		height = wm.getDefaultDisplay().getHeight();
		width = wm.getDefaultDisplay().getWidth();
		setContentView(R.layout.activity_show_location);
		tv_top = (TextView) findViewById(R.id.tv_top);
		tv_bottom = (TextView) findViewById(R.id.tv_bottom);
		ll_toast = (LinearLayout) findViewById(R.id.ll_toast);
		params = (RelativeLayout.LayoutParams) ll_toast.getLayoutParams();
		startX = AppConf.getRawX(context);
		startY = AppConf.getRawY(context);
		params.leftMargin = startX;
		params.topMargin = startY;
		if (params.topMargin < height / 3) {
			tv_top.setVisibility(View.INVISIBLE);
			tv_bottom.setVisibility(View.VISIBLE);
		}
		if (params.topMargin > 2 * height / 3) {
			tv_top.setVisibility(View.VISIBLE);
			tv_bottom.setVisibility(View.INVISIBLE);
		}
		ll_toast.setLayoutParams(params);
		ll_toast.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					startX = (int) event.getRawX();
					startY = (int) event.getRawY();
					Log.v("Shortcut", "ACTION_DOWN");

					break;
				case MotionEvent.ACTION_UP:
					AppConf.setRawX(context, ll_toast.getLeft());
					AppConf.setRawY(context, ll_toast.getTop());
					Log.v("Shortcut", "ACTION_UP");
					Log.v("Shortcut", "Left" + ll_toast.getLeft());
					Log.v("Shortcut", "Top" + ll_toast.getTop());

					break;
				case MotionEvent.ACTION_MOVE:
					endX = (int) event.getRawX();
					endY = (int) event.getRawY();
					int dx = endX - startX;
					int dy = endY - startY;
					if (params.leftMargin + dx < 0 | params.topMargin + dy < 0
							| params.leftMargin + dx > width - ll_toast.getWidth()
							| params.topMargin + dy > height - ll_toast.getHeight() - getStatusBarHeight()) {
					} else {
						params.leftMargin += dx;
						params.topMargin += dy;
						if (params.topMargin < height / 3) {
							tv_top.setVisibility(View.INVISIBLE);
							tv_bottom.setVisibility(View.VISIBLE);
						}
						if (params.topMargin > 2 * height / 3) {
							tv_top.setVisibility(View.VISIBLE);
							tv_bottom.setVisibility(View.INVISIBLE);
						}
						ll_toast.setLayoutParams(params);
						startX = (int) event.getRawX();
						startY = (int) event.getRawY();
						Log.v("Shortcut", "ACTION_MOVE");
					}
					break;

				default:
					break;
				}
				return true;
			}
		});

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	private int getStatusBarHeight() {
		Class<?> c = null;
		Object obj = null;
		Field field = null;
		int x = 0, sbar = 0;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			sbar = context.getResources().getDimensionPixelSize(x);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return sbar;
	}
}
