package com.talentnetwork.util;

import java.util.ArrayList;

import com.talentnetwork.bean.User;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;

public class MyApplication extends Application {
	
	
	private boolean isDownload;
	
	
	public final static int LIMIT=10;//一页的条数
	
	
	private ArrayList<Activity> appList = new ArrayList<Activity>();
	private static MyApplication mInstance = null;
	public boolean m_bKeyRight = true;



	public void addList(Activity app) {
		appList.add(app);
	}
	public void removeList(Activity app) {
		appList.remove(app);
	}
	public void exit() {
		for (Activity app : appList) {
			app.finish();
		}
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
		isDownload = false;
	}
	

	public static MyApplication getInstance() {
		return mInstance;
	}
	
	 User user=null;

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	private Activity progressActivity=null;
	
	public void setActivity(Activity app){
		this.progressActivity=app;
	}
	public void finishActivity(){
		if(progressActivity!=null){
			this.progressActivity.finish();
		}
	}
	
	
	


	public boolean isDownload() {
		return isDownload;
	}

	public void setDownload(boolean isDownload) {
		this.isDownload = isDownload;
	}
	
	


}
