package com.denis.phoneguard.broadcast;

import com.denis.phoneguard.AppConf;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;

/**
 * �����㲥������
 * 
 * @author yongda
 *
 */
public class BootCompleteReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if (AppConf.getProtectON(context)) {
			TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			if (!AppConf.getSIMSerialNum(context).equals(tm.getSimSerialNumber())) {
				SmsManager smsManager = SmsManager.getDefault();
				smsManager.sendTextMessage(AppConf.getBindPhone(context), null, "SIM���Ѿ������仯��", null, null);
			}
		}

	}

}
