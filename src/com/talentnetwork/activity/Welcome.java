package com.talentnetwork.activity;


import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.talentnetwork.bean.UpdataInfo;
import com.talentnetwork.util.MyApplication;
import com.talentnetwork.util.NetworkInfoUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;


public class Welcome extends Activity{
	
	private static final String TAG = "welcome";
	private MyApplication app;
	private String versiontext;
	private UpdataInfo info;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		
		app = (MyApplication) getApplication();
		
		versiontext = getVersion();
		
		Log.i("versiontext", versiontext);
		
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(2000);
					handler.sendEmptyMessage(0);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}finally{
//					Intent intent=new Intent(Welcome.this,MainActivity.class);
//					startActivity(intent);
//					Welcome.this.finish();
				}
			}
		}).start();
	
		
	}
	
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch(msg.what){
			case 0:
				try {
					if(NetworkInfoUtil.isAvailable(Welcome.this)){
						getUpdataInfo(R.string.updataurl);
					}else{
						NetworkInfoUtil.showDialog(Welcome.this,NetworkInfoUtil.PROMPT);//提示网络是否连接
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case 1:
				// 判断服务器版本号 和客户端的版本号 是否相同
				if (isNeedUpdate(versiontext)) {
					Log.i(TAG, "弹出来升级对话框");
					showUpdateDialog();
				}
				break;
			
			}
			
		}
	};
	
	/**
	 * 
	 * @param versiontext
	 *            当前客户端的版本号信息
	 * @return 是否需要更新
	 */
	private boolean isNeedUpdate(String versiontext) {
//		UpdataInfoService service = new UpdataInfoService(this);
		try {
//			info = service.getUpdataInfo(R.string.updataurl);
			boolean isnull=true;
			while(isnull){
				if(info!=null){
					isnull=false;
				}
				Thread.sleep(1000);
			}
			
				String version = info.getVersion();
			
				Log.i("version", versiontext+"----"+version);//,,,,,,,,,,,,,,,,,,,,,,
				
			if (versiontext.equals(version)) {
				Log.i(TAG, "版本相同,无需升级, 进入主界面");
				loadMainUI();
				return false;
			} else {
				Log.i(TAG, "版本不同,需要升级");
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(this, "获取更新信息异常", 0).show();
			Log.i(TAG, "获取更新信息异常, 进入主界面");
			loadMainUI();
			return false;
		}

	}
	private void showUpdateDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("检测到新版本");
		builder.setMessage("是否下载更新?");
		builder.setPositiveButton("下载", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Intent it = new Intent(Welcome.this, NotificationUpdateActivity.class);
				startActivity(it);
//				MapApp.isDownload = true;
				app.setDownload(true);
				//loadMainUI();
//				finish();
			}
		}).setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				loadMainUI();
			}
		});
		builder.show();
	}
	
	
	/**
	 * 获取当前应用程序的版本号
	 * 
	 * @return
	 */
	private String getVersion() {
		try {
			PackageManager manager = getPackageManager();
			PackageInfo info = manager.getPackageInfo(getPackageName(), 0);
//			Log.i("aaaaaaaaaaaaaaaa", info.versionName);
			return info.versionName;
		} catch (Exception e) {
			
			Intent intent=new Intent(Welcome.this,MainActivity.class);
			startActivity(intent);
			Welcome.this.finish();

			e.printStackTrace();
			return "版本号未知";
		}
	}
	private void loadMainUI() {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		finish(); // 把当前activity从任务栈里面移除

	}
	
	
	/**
	 * 
	 * @param urlid 服务器路径string对应的id
	 * @return 更新的信息
	 */
	private  int url;
	public void getUpdataInfo(int urlid) throws Exception{
		url=urlid;
		new  Thread(new Runnable() {
			@Override
			public void run() {
				String path = Welcome.this.getResources().getString(url);
				URL url;
				try {
					url = new URL(path);
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setConnectTimeout(5000);
					conn.setRequestMethod("GET");
					InputStream is = conn.getInputStream();
					handler.sendEmptyMessage(1);
					info=UpdataInfoParser.getUpdataInfo(is);
					handler.sendEmptyMessage(1);
				}  catch (Exception e) {
					Intent intent=new Intent(Welcome.this,MainActivity.class);
					startActivity(intent);
					Welcome.this.finish();
					e.printStackTrace();
				}
				
			}
		}).start();
	}
	//捕捉返回键
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK && event.getRepeatCount()==0){
			return  true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
