package com.denis.phoneguard.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.denis.phoneguard.AppConf;
import com.denis.phoneguard.R;
import com.denis.phoneguard.bean.UpdateBean;
import com.denis.phoneguard.utils.StreamUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class SplashActivity extends Activity {
	private static final int MESSAGE_NEW_VERSION = 0;
	private static final int MESSAGE_JSON_ERROR = 1;
	private static final int MESSAGE_NO_UPDATE = 2;
	protected static final int MESSAGE_URL_ERROR = 3;
	protected static final int MESSAGE_IO_ERROR = 4;
	private long startTime;
	private int localVersionCode;
	private UpdateBean updateBean = new UpdateBean();
	private Message message = Message.obtain();
	private TextView tv_process, tv_version;
	private Context mContext;

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case MESSAGE_NEW_VERSION:
				showUpdateDialog();
				break;
			case MESSAGE_JSON_ERROR:
				Log.v("Shortcut", "JSON解析出错");
				enterHome();
				break;
			case MESSAGE_NO_UPDATE:
				Log.v("Shortcut", "没有更新可用");
				enterHome();
				break;
			case MESSAGE_URL_ERROR:
				Log.v("Shortcut", "URL出错");
				enterHome();
				break;
			case MESSAGE_IO_ERROR:
				Log.v("Shortcut", "IO出错");
				enterHome();
				break;
			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		startTime = System.currentTimeMillis();
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		mContext = this;
		copyDB("address.db");
		setContentView(R.layout.activity_splash);
		tv_version = (TextView) findViewById(R.id.tv_version);
		tv_process = (TextView) findViewById(R.id.tv_process);
		tv_version.setText("版本号" + getLocalVersionName());
		localVersionCode = getLocalVersionCode();
		if (AppConf.getAutoUpdate(mContext)) {
			getRemoteVersionCode();
		} else {
			message.what = MESSAGE_NO_UPDATE;
			handler.sendMessageDelayed(message, 3000);
		}

	}

	/**
	 * ��assert�ļ��и������ݿ⵽databaseĿ¼
	 * 
	 * @param string
	 */
	private void copyDB(String fileName) {
		AssetManager am = getAssets();
		InputStream is = null;
		OutputStream os = null;
		File desFile = new File(getFilesDir(), fileName);
		if (desFile.exists()) {
			Log.v("Shortcut", "���ݿ��Ѿ�����");
			return;
		}
		try {
			is = am.open(fileName, AssetManager.ACCESS_RANDOM);
			os = new FileOutputStream(desFile);
			int len = 0;
			byte[] b = new byte[1024];
			while ((len = is.read(b)) != -1) {
				os.write(b, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * ��ȡ�汾����
	 * 
	 * @return
	 */
	private String getLocalVersionName() {
		PackageManager pm = SplashActivity.this.getPackageManager();
		try {
			PackageInfo info = pm.getPackageInfo(SplashActivity.this.getPackageName(), PackageManager.GET_META_DATA);
			String versionName = info.versionName;
			return versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * ��ȡ�汾��
	 * 
	 * @return
	 */
	private int getLocalVersionCode() {
		PackageManager pm = SplashActivity.this.getPackageManager();
		try {
			PackageInfo info = pm.getPackageInfo(SplashActivity.this.getPackageName(), PackageManager.GET_META_DATA);
			int versionCode = info.versionCode;
			return versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return -1;
		}

	}

	/**
	 * ��ȡ�������˰汾��Ϣ
	 * 
	 * @return
	 */
	private void getRemoteVersionCode() {
		Thread t = new Thread() {
			@Override
			public void run() {
				try {
					String path = "http://192.168.2.24:8080/news/update.json";
					URL url = new URL(path);
					HttpURLConnection conn;
					conn = (HttpURLConnection) url.openConnection();
					conn.setRequestMethod("GET");
					conn.setConnectTimeout(3000);
					conn.setReadTimeout(3000);
					if (conn.getResponseCode() == 200) {
						InputStream is = conn.getInputStream();
						String info = StreamUtils.StreamToString(is);
						decodeJson(info);
						if (conn != null) {
							conn.disconnect();
						}
					} else {
						message.what = MESSAGE_NO_UPDATE;
					}
				} catch (MalformedURLException e) {
					message.what = MESSAGE_URL_ERROR;
					e.printStackTrace();
				} catch (IOException e) {
					message.what = MESSAGE_IO_ERROR;
					e.printStackTrace();
				}
				long endTime = System.currentTimeMillis();
				long duringTime = 0;
				if ((duringTime = endTime - startTime) < 3000) {
					try {
						sleep(3000 - duringTime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				handler.sendMessage(message);
			}
		};
		t.start();

	}

	/**
	 * ���ַ����н���Jason����
	 */
	private void decodeJson(String info) {
		try {
			JSONObject jsonOb = new JSONObject(info);
			updateBean.setVersionName(jsonOb.getString("versionName"));
			updateBean.setVersionCode(jsonOb.getInt("versionCode"));
			updateBean.setDescription(jsonOb.getString("description"));
			updateBean.setUrl(jsonOb.getString("downloadUrl"));
			if (updateBean.getVersionCode() > localVersionCode && localVersionCode != -1) {
				message.what = MESSAGE_NEW_VERSION;
			} else {
				message.what = MESSAGE_NO_UPDATE;
			}
		} catch (JSONException e) {
			e.printStackTrace();
			message.what = MESSAGE_JSON_ERROR;
		}
	}

	/**
	 * �������¶Ի���
	 */
	private void showUpdateDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
		builder.setTitle("�ֻ���ʿ���¹�������");
		builder.setPositiveButton("���ϸ���", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				downloadAPP();
			}
		});
		builder.setNegativeButton("�Ժ���˵", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				enterHome();
			}
		});
		builder.show();
	}

	/**
	 * ����Ӧ��
	 */
	private void downloadAPP() {
		HttpUtils http = new HttpUtils();
		http.download(updateBean.getUrl(), "sdcard/update.apk", null, false, new RequestCallBack<File>() {
			@Override
			public void onSuccess(ResponseInfo<File> file) {
				tv_process.setText("���ؽ��ȣ�100%");
				Intent intent_ = new Intent();
				intent_.setAction(Intent.ACTION_VIEW);
				intent_.addCategory(Intent.CATEGORY_DEFAULT);
				intent_.setDataAndType(Uri.fromFile(file.result), "application/vnd.android.package-archive");
				startActivityForResult(intent_, 0);
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				Log.v("Shortcut", "download fail!");
				enterHome();
			}

			@Override
			public void onLoading(long total, long current, boolean isUploading) {
				tv_process.setVisibility(View.VISIBLE);
				tv_process.setText("���ؽ��ȣ�" + current / total + "%");

			}
		});
	}

	/**
	 * ����������
	 */
	private void enterHome() {
		Intent intent = new Intent(SplashActivity.this, HomeAcitvity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		startActivity(intent);
		SplashActivity.this.finish();
	}

	/**
	 * ������֮��ȡ������
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		enterHome();
	}
}
