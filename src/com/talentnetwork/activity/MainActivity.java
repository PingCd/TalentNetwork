package com.talentnetwork.activity;

import java.util.Set;

import com.talentnetwork.activity.DetailedFragment.DetailedOnbrakClickListener;
import com.talentnetwork.activity.HomeFragment.OnHomeBrakClickListener;
import com.talentnetwork.activity.JobFragment.OnbrakClickListener;
import com.talentnetwork.bean.JobDetailed;
import com.talentnetwork.util.MyApplication;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

/**
 * 主页
 * 
 * @author Administrator
 * 
 */
public class MainActivity extends FragmentActivity implements TagAliasCallback,
		View.OnClickListener, OnbrakClickListener, DetailedOnbrakClickListener,
		OnHomeBrakClickListener {

	private FragmentManager mManager;

	private RadioButton tab_rb_1, tab_rb_2, tab_rb_3, tab_rb_4;

	private static final String ALIAS = "changlong";
	private static final String TAG_HOME = "home";
	private static final String TAG_JOB = "job";
	private static final String TAG_GONE = "gone";
	private static final String TAG_INDIVIDUAL = "individual";

	private int mId;
	private String mTag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		MyApplication.getInstance().addList(this);

		init();
		mManager = getSupportFragmentManager();

		JPushInterface.setDebugMode(true);
		JPushInterface.init(this);
		JPushInterface.setAliasAndTags(getApplicationContext(), ALIAS, null,
				this);

		FragmentTransaction transaction = mManager.beginTransaction();
		HomeFragment homeFrag = new HomeFragment();
		transaction.add(R.id.realtabcontent, homeFrag, TAG_HOME);
		transaction.addToBackStack(TAG_HOME);
		transaction.commit();
		this.mId = R.id.tab_rb_1;
		this.mTag = TAG_HOME;
		tab_rb_1.setChecked(true);
		tab_rb_1.setTextColor(Color.YELLOW);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void init() {
		tab_rb_1 = (RadioButton) findViewById(R.id.tab_rb_1);
		tab_rb_1.setOnClickListener(this);
		tab_rb_2 = (RadioButton) findViewById(R.id.tab_rb_2);
		tab_rb_2.setOnClickListener(this);
		tab_rb_3 = (RadioButton) findViewById(R.id.tab_rb_3);
		tab_rb_3.setOnClickListener(this);
		tab_rb_4 = (RadioButton) findViewById(R.id.tab_rb_4);
		tab_rb_4.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == mId) {
			return;
		}
		this.mId = id;

		tab_rb_1.setTextColor(Color.WHITE);
		tab_rb_2.setTextColor(Color.WHITE);
		tab_rb_3.setTextColor(Color.WHITE);
		tab_rb_4.setTextColor(Color.WHITE);

		FragmentTransaction transaction = mManager.beginTransaction();

		transaction.hide(mManager.findFragmentByTag(mTag));
		Fragment fragment = null;
		switch (id) {
		case R.id.tab_rb_1: // 首页
			tab_rb_1.setTextColor(Color.YELLOW);
			fragment = mManager.findFragmentByTag(TAG_HOME);
			if (fragment != null) {
				transaction.show(fragment);
			} else {
				HomeFragment homeFrag = new HomeFragment();
				transaction.add(R.id.realtabcontent, homeFrag, TAG_HOME);
			}
			mTag = TAG_HOME;
			transaction.addToBackStack(TAG_HOME);
			break;
		case R.id.tab_rb_2: // 找工作
			try {
			tab_rb_2.setTextColor(Color.YELLOW);
			fragment = mManager.findFragmentByTag(TAG_JOB);
			if (fragment != null) {
				transaction.show(fragment);
			} else {
				JobFragment jobFrag = new JobFragment("",1);
				transaction.add(R.id.realtabcontent, jobFrag, TAG_JOB);
			}
			mTag = TAG_JOB;
			transaction.addToBackStack(TAG_JOB);
			} catch (Exception e) {
				// TODO: handle exception
				Toast.makeText(getApplicationContext(), JobFragment.class.getName()+"throw 2", 0);
			}
			break;
		case R.id.tab_rb_3: // 详情
			try {
				tab_rb_3.setTextColor(Color.YELLOW);
				fragment = mManager.findFragmentByTag(TAG_GONE);
				if (fragment != null) {
					transaction.show(fragment);
				} else {
					DetailedFragment detailedFrag = new DetailedFragment(null);
					transaction.add(R.id.realtabcontent, detailedFrag, TAG_GONE);
				}
				mTag = TAG_GONE;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
		case R.id.tab_rb_4: // 个人中心
			try {
				
			tab_rb_4.setTextColor(Color.YELLOW);
			fragment = mManager.findFragmentByTag(TAG_INDIVIDUAL);
			if (fragment != null) {
				transaction.show(fragment);
			} else {
				IndividualFragment individualFrag = new IndividualFragment();
				transaction.add(R.id.realtabcontent, individualFrag,
						TAG_INDIVIDUAL);
			}
			mTag = TAG_INDIVIDUAL;
			transaction.addToBackStack(TAG_INDIVIDUAL);
			} catch (Exception e) {
				// TODO: handle exception
				Toast.makeText(getApplicationContext(), IndividualFragment.class.getName()+"throw", 0);
			}
			break;
		}
		transaction.commit();
	}

	@Override
	public void gotResult(int arg0, String arg1, Set<String> arg2) {

	}

	/*
	 * 跳转到详情界面
	 * 
	 * @see com.talentnetwork.activity.JobFragment.OnbrakClickListener#onback()
	 */
	@Override
	public void onback(JobDetailed job) {
		int id = R.id.tab_rb_3;
		this.mId = id;
		FragmentTransaction transaction = mManager.beginTransaction();
		transaction.hide(mManager.findFragmentByTag(mTag));
		Fragment fragment = null;
		fragment = mManager.findFragmentByTag(TAG_GONE);
		DetailedFragment detailedFrag = new DetailedFragment(job);
		transaction.add(R.id.realtabcontent, detailedFrag, TAG_GONE);
		mTag = TAG_GONE;
		transaction.commit();
	}

	/*
	 * 详情返回找工作界面
	 * 
	 * @see
	 * com.talentnetwork.activity.DetailedFragment.DetailedOnbrakClickListener
	 * #back()
	 */
	@Override
	public void back() {
		int id = R.id.tab_rb_2;

		if (id == mId) {
			return;
		}
		this.mId = id;
		FragmentTransaction transaction = mManager.beginTransaction();
		transaction.hide(mManager.findFragmentByTag(mTag));
		Fragment fragment = null;
		fragment = mManager.findFragmentByTag(TAG_JOB);
		if (fragment != null) {
			transaction.show(fragment);
		} else {
			JobFragment jobFrag = new JobFragment("",1);
			transaction.add(R.id.realtabcontent, jobFrag, TAG_JOB);
		}
		mTag = TAG_JOB;
		transaction.addToBackStack(TAG_JOB);
		transaction.commit();
	}
	
	
	@Override
	public void onHomeBack(String str, int type) {
		
		int id = R.id.tab_rb_2;
		if (id == mId) {
			return;
		}
		this.mId = id;
		
		FragmentTransaction transaction = mManager.beginTransaction();

		transaction.hide(mManager.findFragmentByTag(mTag));
		Fragment fragment = null;
		
		tab_rb_1.setTextColor(Color.WHITE);
		tab_rb_2.setTextColor(Color.YELLOW);
		tab_rb_2.setChecked(true);
		fragment = mManager.findFragmentByTag(TAG_JOB);
			JobFragment jobFrag = new JobFragment(str,type);
			transaction.add(R.id.realtabcontent, jobFrag, TAG_JOB);
		mTag = TAG_JOB;
		transaction.commit();
		
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			AlertDialog.Builder builder = new AlertDialog.Builder(
					MainActivity.this);
			builder.setMessage("确认是否退出？");
			builder.setPositiveButton("确认",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							MyApplication.getInstance().setUser(null);
							MainActivity.this.finish();
							MyApplication.getInstance().exit();
						}
					});
			builder.setNegativeButton("取消", null);

			builder.show();
			return true;

		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDestroy() {
		MyApplication.getInstance().removeList(this);
		super.onDestroy();
	}

	

}
