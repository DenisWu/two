package com.denis.phoneguard;

import com.denis.phoneguard.utils.SharedPreferencesUtil;

import android.content.Context;

/**
 * 鎼存梻鏁ら柊宥囩枂娣団剝浼�
 * 
 * @author yongda
 */
public class AppConf {

	private static String AUTO_UPDATE = "AUTO_UPDATE"; // 鏄惁鑷姩鏇存柊
	private static String LOST_PWD = "LOST_PWD"; // 鎵嬫満闃茬洍涓殑瀵嗙爜
	private static String IS_SETUP = "IS_SETUP"; // 鏄惁璁剧疆杩囨墜鏈洪槻鐩椾腑鐨勮缃悜瀵�
	private static String IS_BIND_SIM = "IS_BIND_SIM"; // 鏄惁缁戝畾SIM鍗�
	private static String BINDPHONE = "BINDPHONE"; // 瀹夊叏鍙风爜
	private static String ISUPDATEON = "ISUPDATEON"; // 鎵嬫満闃茬洍鏄惁寮�鍚�
	private static String SIMSERIAL = "SIMSERIAL"; // SIM鍗″簭鍒楀彿
	private static String SHOWADDR = "SHOWADDR"; // 是否显示来电地址
	private static String SHOWSTYLE = "SHOWSTYLE"; // 设置显示风格
	private static String RAWX = "RAWX"; // 归属地显示X坐标
	private static String RAWY = "RAWY"; // 归属地显示Y坐标

	/**
	 * 璁剧疆鏄惁鑷姩鏇存柊
	 * 
	 * @param context
	 * @param autoUpdate
	 */
	public static boolean setAutoUpdate(Context context, Boolean autoUpdate) {
		return SharedPreferencesUtil.putBoolean(context, AUTO_UPDATE, autoUpdate);
	}

	/**
	 * 鑾峰彇鏄惁鑷姩鏇存柊
	 */
	public static boolean getAutoUpdate(Context context) {
		return SharedPreferencesUtil.getBoolean(context, AUTO_UPDATE, true);
	}

	/**
	 * 璁剧疆鎵嬫満闃茬洍涓殑瀵嗙爜
	 */
	public static boolean setFindLostPwd(Context context, String pwd) {
		return SharedPreferencesUtil.putString(context, LOST_PWD, pwd);

	}

	/**
	 * 鑾峰彇鎵嬫満闃茬洍涓殑瀵嗙爜
	 */
	public static String getFindLostPwd(Context context) {
		return SharedPreferencesUtil.getString(context, LOST_PWD, "");
	}

	/**
	 * 鏄惁宸茬粡璁剧疆杩囪缃悜瀵�
	 */
	public static boolean setIsSetup(Context context, Boolean isSetup) {
		return SharedPreferencesUtil.putBoolean(context, IS_SETUP, isSetup);
	}

	/**
	 * 鑾峰彇鏄惁鑷姩鏇存柊
	 */
	public static boolean getsetIsSetup(Context context) {
		return SharedPreferencesUtil.getBoolean(context, IS_SETUP, false);
	}

	/**
	 * 璁剧疆鏄惁缁戝畾SIM鍗�
	 */
	public static boolean setBindSIM(Context context, Boolean isSetup) {
		return SharedPreferencesUtil.putBoolean(context, IS_BIND_SIM, isSetup);
	}

	/**
	 * 鑾峰彇鏄惁缁戝畾SIM鍗�
	 */
	public static boolean getBindSIM(Context context) {
		return SharedPreferencesUtil.getBoolean(context, IS_BIND_SIM, false);
	}

	/**
	 * 璁剧疆鎵嬫満闃茬洍涓殑缁戝畾鎵嬫満鍙�
	 */
	public static boolean setBindPhone(Context context, String phone) {
		return SharedPreferencesUtil.putString(context, BINDPHONE, phone);

	}

	/**
	 * 鑾峰彇鎵嬫満闃茬洍涓殑缁戝畾鎵嬫満鍙�
	 */
	public static String getBindPhone(Context context) {
		return SharedPreferencesUtil.getString(context, BINDPHONE, "");
	}

	/**
	 * 璁剧疆鎵嬫満闃茬洍鏄惁寮�鍚�
	 */
	public static boolean setProtectON(Context context, Boolean isUpdateON) {
		return SharedPreferencesUtil.putBoolean(context, ISUPDATEON, isUpdateON);
	}

	/**
	 * 鑾峰彇鎵嬫満鏄惁寮�鍚�
	 */
	public static boolean getProtectON(Context context) {
		return SharedPreferencesUtil.getBoolean(context, ISUPDATEON, false);
	}

	/**
	 * 璁剧疆SIM鍗″簭鍒楀彿
	 * 
	 * @param context
	 * @param serialNum
	 */
	public static void setSIMSerialNum(Context context, String serialNum) {
		SharedPreferencesUtil.putString(context, SIMSERIAL, serialNum);
	}

	/**
	 * 鑾峰彇SIM鍗″簭鍒楀彿
	 * 
	 * @param context
	 * @return
	 */
	public static String getSIMSerialNum(Context context) {
		return SharedPreferencesUtil.getString(context, SIMSERIAL);
	}

	/**
	 * 设置是否显示来电地址
	 */
	public static boolean setShowAddr(Context context, Boolean isShow) {
		return SharedPreferencesUtil.putBoolean(context, SHOWADDR, isShow);
	}

	/**
	 * 获取是否显示来电地址
	 */
	public static boolean getShowAddr(Context context) {
		return SharedPreferencesUtil.getBoolean(context, SHOWADDR, false);
	}

	/**
	 * 设置显示风格
	 */
	public static boolean setShowStyle(Context context, int style) {
		return SharedPreferencesUtil.putInt(context, SHOWSTYLE, style);
	}

	/**
	 * 获取显示风格
	 */
	public static int getShowStyle(Context context) {
		return SharedPreferencesUtil.getInt(context, SHOWSTYLE, 0);
	}

	/**
	 * 设置归属地显示坐标
	 */
	public static boolean setRawX(Context context, int x) {
		return SharedPreferencesUtil.putInt(context, RAWX, x);
	}

	/**
	 * 获取归属地显示坐标
	 */
	public static int getRawX(Context context) {
		return SharedPreferencesUtil.getInt(context, RAWX, 0);
	}

	/**
	 * 设置归属地显示坐标
	 */
	public static boolean setRawY(Context context, int y) {
		return SharedPreferencesUtil.putInt(context, RAWY, y);
	}

	/**
	 * 获取归属地显示坐标
	 */
	public static int getRawY(Context context) {
		return SharedPreferencesUtil.getInt(context, RAWY, 0);
	}

}
