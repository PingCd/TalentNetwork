package com.talentnetwork.activity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.talentnetwork.bean.User;
import com.talentnetwork.mytask.RefreshTask;
import com.talentnetwork.util.HttpClientUtil;
import com.talentnetwork.util.MyApplication;
import com.talentnetwork.util.NetworkInfoUtil;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 *个人中心
 * 
 * @author
 */
@SuppressLint("ValidFragment")
public class IndividualFragment extends Fragment implements View.OnClickListener{
	
	HttpClientUtil hcu = new HttpClientUtil();
	
	private int type=0;//判断企业（1）还是个人（2）
	
	private LinearLayout ll_login;
	
	private RelativeLayout ll_individual;
	
	private Button login_btn_submit,individual_refresh;
	
	private View login_progressBar;
	
	private TextView tv_exit;
	
	private RelativeLayout rl_content1,rl_content2,rl_content3,rl_content4,rl_content5,rl_content6;
	
	private EditText login_name,login_password;
	
	private String userName,userPassWord;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.individualfragment, container, false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
	}
	private void initView() {
		
		ll_login=(LinearLayout) getView().findViewById(R.id.ll_login);
		ll_individual=(RelativeLayout) getView().findViewById(R.id.ll_individual);
		login_btn_submit=(Button) getView().findViewById(R.id.login_btn_submit);
		login_btn_submit.setOnClickListener(this);
		individual_refresh=(Button) getView().findViewById(R.id.individual_refresh);
		individual_refresh.setOnClickListener(this);
		tv_exit=(TextView) getView().findViewById(R.id.tv_exit);
		tv_exit.setOnClickListener(this);
		login_name=(EditText) getView().findViewById(R.id.login_name);
		login_name.setOnClickListener(this);
		login_password=(EditText) getView().findViewById(R.id.login_password);
		login_password.setOnClickListener(this);
		login_progressBar=getView().findViewById(R.id.login_progressBar);
		
		
		
		rl_content1=(RelativeLayout) getView().findViewById(R.id.rl_content1);
		rl_content1.setOnClickListener(this);
		rl_content2=(RelativeLayout) getView().findViewById(R.id.rl_content2);
		rl_content2.setOnClickListener(this);
		rl_content3=(RelativeLayout) getView().findViewById(R.id.rl_content3);
		rl_content3.setOnClickListener(this);
		rl_content4=(RelativeLayout) getView().findViewById(R.id.rl_content4);
		rl_content4.setOnClickListener(this);
		rl_content5=(RelativeLayout) getView().findViewById(R.id.rl_content5);
		rl_content5.setOnClickListener(this);
		rl_content6=(RelativeLayout) getView().findViewById(R.id.rl_content6);
		rl_content6.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.login_btn_submit:
				if(NetworkInfoUtil.isAvailable(getActivity())){
					userName=login_name.getText().toString();
					userPassWord=login_password.getText().toString();
					if(userName.equals("")||userName==null||userPassWord.equals("")||userPassWord==null){
						hcu.getToast(getActivity(), "用户名或密码不能为空！");
					}else{
						login_progressBar.setVisibility(View.VISIBLE);
						login_btn_submit.setEnabled(false);
						new MyTask().execute();//获取后台数据
					}
				}else{
					showDialog();
				}
				break;
			case R.id.individual_refresh://刷新
				
				if(NetworkInfoUtil.isAvailable(getActivity())){
					login_progressBar.setVisibility(View.VISIBLE);
					individual_refresh.setEnabled(false);
					new RefreshTask(getActivity(), login_progressBar,individual_refresh).execute();
					judgment();
				}else{
					showDialog();
				}
				
				break;
			case R.id.tv_exit:
				showDialog(R.id.tv_exit,"确定退出登录？");
				break;
			case R.id.rl_content1://面试邀请
				if(Integer.parseInt(MyApplication.getInstance().getUser().getParam1())!=0){
					Intent intent=new Intent(getActivity(),InvitationActivity.class);
					startActivity(intent);
				}
				
				break;
			case R.id.rl_content2://已投职位
				if(Integer.parseInt(MyApplication.getInstance().getUser().getParam2())!=0){
					Intent intent2=new Intent(getActivity(),CastResumeActivity.class);
					startActivity(intent2);
				}
				break;
			case R.id.rl_content3://我的简历
				if(Integer.parseInt(MyApplication.getInstance().getUser().getParam3())!=0){
					Intent intent3=new Intent(getActivity(),ResumeItemActivity.class);
					startActivity(intent3);
				}else{
					hcu.getToast(getActivity(), "请到电脑端完善简历");
				}
				break;
			case R.id.rl_content4://应聘者简历
				if(Integer.parseInt(MyApplication.getInstance().getUser().getParam1())!=0){
					Intent intent4=new Intent(getActivity(),CandidateResumeActivity.class);
					startActivity(intent4);
				}
				break;
			case R.id.rl_content5://收信箱
				if(Integer.parseInt(MyApplication.getInstance().getUser().getParam2())!=0){
					Intent intent5=new Intent(getActivity(),InBoxActivity.class);
					startActivity(intent5);
				}
				
				break;
			case R.id.rl_content6://职位管理
				if(Integer.parseInt(MyApplication.getInstance().getUser().getParam3())!=0){
					Intent intent6=new Intent(getActivity(),PositionManagementActivity.class);
					startActivity(intent6);
				}
				break;
		}
	}
	/*
	 * 登录
	 * 访问网络取数据
	 */
	class MyTask extends AsyncTask<Void, Void,String >{
		
		@Override
		protected String doInBackground(Void... arg0) {
			Map<String, String> map = new HashMap<String, String>();
			try {
				userName = URLEncoder.encode(userName, "utf-8");
				userPassWord = URLEncoder.encode(userPassWord, "utf-8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			map.put("username", userName);
			map.put("userpass", userPassWord);
			String str = null;
			try {
				str = hcu.getRequest(getActivity(), hcu.URL+ "Login?", map);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return str;
		}
		@Override
		protected void onPostExecute(String result) {
//			Log.i("aaaaaaaaaaaaaa", result);
			if(result==null||result.equals("")){
				hcu.getToast(getActivity(), "登录失败！");
				login_progressBar.setVisibility(View.GONE);
				return;
			}else{
				try {
					JSONArray arr = new JSONArray(result);
					JSONObject o1 = (JSONObject) arr.get(0);
					String state = o1.getString("state");
					if(state.equals("1")){
						User user=new User();
						user.setUid(Integer.parseInt(o1.getString("uid")));
						user.setUserName(o1.getString("username"));
						user.setType(Integer.parseInt(o1.getString("type")));
						user.setParam1(o1.getString("personal1"));
						user.setParam2(o1.getString("personal2"));
						user.setParam3(o1.getString("personal3"));
						MyApplication.getInstance().setUser(user);//将用户信息放入全局中
						judgment();
						ll_login.setVisibility(View.GONE);
						ll_individual.setVisibility(View.VISIBLE);
					}else if(state.equals("0")){
						hcu.getToast(getActivity(), "用户名或密码错误！");
					}else{
						hcu.getToast(getActivity(), "服务器出现错误！");
					}
				} catch (JSONException e) {
					e.printStackTrace();
					hcu.getToast(getActivity(), "登录失败！");
				}
			}
			login_btn_submit.setEnabled(true);
			login_progressBar.setVisibility(View.GONE);
			login_btn_submit.setEnabled(true);
		}
	}
	
	
	
	/*
	 * 对话框
	 */
	private int dialogid;
	private void showDialog(int id,String message){
		this.dialogid=id;
		AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
		builder.setTitle("提示");
		builder.setMessage(message);
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				DialogPositive(dialogid);
			}
		});
		builder.setNegativeButton("取消", null);
		builder.create().show();
	}
	private void DialogPositive(int id){
		switch(id){
			case R.id.tv_exit:
				MyApplication.getInstance().setUser(null);//清除全局对象
				login_password.setText("");
				ll_login.setVisibility(View.VISIBLE);
				ll_individual.setVisibility(View.GONE);
				break;
			case R.id.login_btn_submit:
				NetworkInfoUtil.openWifi(getActivity());
				break;
		}
		
	}
	/**
	 * 根据id判断是个人还是企业
	 */
	public void judgment(){
		User user=new User();
		user=MyApplication.getInstance().getUser();
		int type=user.getType();//企业还是个人
		
		TextView tv_username=(TextView) getView().findViewById(R.id.tv_username);
		tv_username.setText(user.getUserName());
		
		RelativeLayout rl_content1=(RelativeLayout) getView().findViewById(R.id.rl_content1);
		RelativeLayout rl_content2=(RelativeLayout) getView().findViewById(R.id.rl_content2);
		RelativeLayout rl_content3=(RelativeLayout) getView().findViewById(R.id.rl_content3);
		RelativeLayout rl_content4=(RelativeLayout) getView().findViewById(R.id.rl_content4);
		RelativeLayout rl_content5=(RelativeLayout) getView().findViewById(R.id.rl_content5);
		RelativeLayout rl_content6=(RelativeLayout) getView().findViewById(R.id.rl_content6);
		
		if(type==1){
			rl_content1.setVisibility(View.GONE);
			rl_content2.setVisibility(View.GONE);
			rl_content3.setVisibility(View.GONE);
			rl_content4.setVisibility(View.VISIBLE);
//			rl_content5.setVisibility(View.VISIBLE);
			rl_content6.setVisibility(View.VISIBLE);
			
			
			TextView tv_candidates=(TextView) getView().findViewById(R.id.tv_candidates);//应聘者简历
			tv_candidates.setText(user.getParam1());
			TextView tv_inbox=(TextView) getView().findViewById(R.id.tv_inbox);//收信箱
			tv_inbox.setText(user.getParam2());
			TextView tv_management=(TextView) getView().findViewById(R.id.tv_management);
			tv_management.setText(user.getParam3());
			
		}else if(type==2){
			rl_content1.setVisibility(View.VISIBLE);
			rl_content2.setVisibility(View.VISIBLE);
			rl_content3.setVisibility(View.VISIBLE);
			rl_content4.setVisibility(View.GONE);
//			rl_content5.setVisibility(View.GONE);
			rl_content6.setVisibility(View.GONE);
			
			TextView tv_invitation=(TextView) getView().findViewById(R.id.tv_invitation);//面试邀请
			tv_invitation.setText(user.getParam1());
			TextView tv_cast=(TextView) getView().findViewById(R.id.tv_cast);//已投简历
			tv_cast.setText(user.getParam2());
			TextView tv_resume=(TextView) getView().findViewById(R.id.tv_resume);//我的简历
			tv_resume.setText(user.getParam3());
			
		}
	}
	public void showDialog(){
		NetworkInfoUtil.showDialog(getActivity(),NetworkInfoUtil.PROMPT);//提示网络是否连接
	}
	
	
	@Override
	public void onPause() {
		 login_progressBar.setVisibility(View.GONE);
		super.onPause();
	}
}
