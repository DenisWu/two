package com.denis.phoneguard.broadcast;

import com.denis.phoneguard.R;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.telephony.SmsMessage;

/**
 * 短信接受者
 * 
 * @author yongda
 *
 */
public class SMSReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Object[] objects = (Object[]) intent.getExtras().get("pdus");
		for (Object object : objects) {// 短信最多140字节,
										// 超出的话,会分为多条短信发送,所以是一个数组,因为我们的短信指令很短,所以for循环只执行一次
			SmsMessage message = SmsMessage.createFromPdu((byte[]) object);
			String originatingAddress = message.getOriginatingAddress();// 短信来源号码
			String messageBody = message.getMessageBody();// 短信内容
			switch (messageBody) {
			case "#*alarm*#":
				MediaPlayer player = MediaPlayer.create(context, R.raw.hurry_that_year);
				player.setLooping(true);
				player.setVolume(1f, 1f);
				player.start();
				break;
			case "#*location*#":
				break;
			default:
				break;
			}

		}
	}

}
