package com.talentnetwork.util;



import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkInfoUtil {
	
	
	 private static final String netACTION="android.net.conn.CONNECTIVITY_CHANGE";
	 
	 
	 public final static String PROMPT="请检查网络是否连接！";
	 
	 private static Context context;

	/**
	 * 判断网络连接是否可用
	 * 
	 * @return
	 */
	public static boolean isAvailable(Context context) {
		ConnectivityManager cManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cManager.getActiveNetworkInfo();
		if (info != null) {
			return info.isAvailable() && info.isConnectedOrConnecting();
		}
		return false;
	}
	/**
	 * 跳转到设置wifi界面
	 * @param context
	 */
	public static void openWifi(Context context){
	            Intent intent = new Intent("android.settings.WIRELESS_SETTINGS");
	            context.startActivity(intent);
	}
	
	
	
	/**
	 * 对话框
	 * @param message
	 */
	
	public static void showDialog(Context con,String message){
		context=con;
		AlertDialog.Builder builder=new AlertDialog.Builder(con);
		builder.setTitle("提示");
		builder.setMessage(message);
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				NetworkInfoUtil.openWifi(context);
			}
		});
		builder.setNegativeButton("取消", null);
		builder.create().show();
	}
	
	
}
