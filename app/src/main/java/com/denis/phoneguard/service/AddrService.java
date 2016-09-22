package com.denis.phoneguard.service;

import com.denis.phoneguard.AppConf;
import com.denis.phoneguard.R;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 来电显示的service
 * 
 * @author o
 *
 */
public class AddrService extends Service {
	private Context context;
	private TelephonyManager tm;
	private MyPhoneStateListener myPhoneStateListener;
	private WindowManager wm;
	private View view;
	private final WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();

	@Override
	public void onCreate() {
		super.onCreate();
		context = this;
		Log.v("Shortcut", "AddressService is on running");
		wm = (WindowManager) getSystemService(WINDOW_SERVICE);
		tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		myPhoneStateListener = new MyPhoneStateListener();
		tm.listen(myPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
	}

	class MyPhoneStateListener extends PhoneStateListener {
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			switch (state) {
			// 电话空闲
			case TelephonyManager.CALL_STATE_IDLE:
				if (wm != null && view != null) {
					wm.removeView(view);
				}
				break;
			// 电话摘机
			case TelephonyManager.CALL_STATE_OFFHOOK:

				break;
			// 电话响铃
			case TelephonyManager.CALL_STATE_RINGING:
				Log.v("Shortcut", "Ringing");
				Toast.makeText(context, "电话铃响", 0).show();
				final WindowManager.LayoutParams params = mParams;
				params.x = AppConf.getRawX(context);
				params.y = AppConf.getRawY(context);
				Log.v("Shortcut", "params.x"+params.x+"params.y"+params.y);
				params.height = WindowManager.LayoutParams.WRAP_CONTENT;
				params.width = WindowManager.LayoutParams.WRAP_CONTENT;
				params.format = PixelFormat.TRANSLUCENT;
				params.type = WindowManager.LayoutParams.TYPE_TOAST;
				params.gravity=Gravity.LEFT+Gravity.TOP;
				params.setTitle("Toast");
				params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
						| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
				view = View.inflate(context, R.layout.toast_show_addr, null);
				TextView tv_addr = (TextView) view.findViewById(R.id.tv_addr);
				tv_addr.setText(checkAddress(incomingNumber));
				LinearLayout ll_toast = (LinearLayout) view.findViewById(R.id.ll_toast);
				switch (AppConf.getShowStyle(context)) {
				case 0:
					ll_toast.setBackgroundResource(R.drawable.call_locate_blue);
					break;
				case 1:
					ll_toast.setBackgroundResource(R.drawable.call_locate_orange);
					break;
				case 2:
					ll_toast.setBackgroundResource(R.drawable.call_locate_white);
					break;
				case 3:
					ll_toast.setBackgroundResource(R.drawable.call_locate_gray);
					break;
				case 4:
					ll_toast.setBackgroundResource(R.drawable.call_locate_green);
					break;
				default:
					break;
				}
				wm.addView(view, params);
				break;
			default:
				break;
			}
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	/**
	 * 根据号码查询归属地
	 * 
	 * @param number
	 * @return
	 */
	private String checkAddress(String number) {
		String address = "未知地址";
		switch (number.length()) {
		case 3:
			address = "报警电话";
			break;
		case 4:
			address = "模拟器";
			break;
		case 5:
			address = "客服电话";
			break;
		case 6:
		case 7:
		case 8:
			address = "本地电话";
			String path = getFilesDir() + "/address.db";
			SQLiteDatabase db = openOrCreateDatabase(path, MODE_PRIVATE, null);
			Cursor outkeyCursor = db.query("data1", new String[] { "outkey" }, "id=?", new String[] { number }, null,
					null, null);
			Cursor addrCursor = null;
			try {
				if (outkeyCursor != null) {
					outkeyCursor.moveToLast();
					String outKey = outkeyCursor.getString(0);
					Log.v("Shortcut", "outKey:" + outKey);
					addrCursor = db.query("data2", new String[] { "location" }, "id=?", new String[] { outKey }, null,
							null, null);
					if (addrCursor != null) {
						addrCursor.moveToNext();
						address = addrCursor.getString(0);
						Log.v("Shortcut", "address:" + addrCursor.getColumnName(0));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (outkeyCursor != null) {
					outkeyCursor.close();
				}
				if (addrCursor != null) {
					addrCursor.close();
				}
			}
			break;
		default:
			break;
		}
		return address;
	}
}
