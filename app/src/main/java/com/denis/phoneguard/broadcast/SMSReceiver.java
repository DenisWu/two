package com.denis.phoneguard.broadcast;

import com.denis.phoneguard.R;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.telephony.SmsMessage;

/**
 * ���Ž�����
 * 
 * @author yongda
 *
 */
public class SMSReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Object[] objects = (Object[]) intent.getExtras().get("pdus");
		for (Object object : objects) {// �������140�ֽ�,
										// �����Ļ�,���Ϊ�������ŷ���,������һ������,��Ϊ���ǵĶ���ָ��ܶ�,����forѭ��ִֻ��һ��
			SmsMessage message = SmsMessage.createFromPdu((byte[]) object);
			String originatingAddress = message.getOriginatingAddress();// ������Դ����
			String messageBody = message.getMessageBody();// ��������
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
