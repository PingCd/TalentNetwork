package com.talentnetwork.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.talentnetwork.adapter.JobItemAdapter;
import com.talentnetwork.bean.JobItem;
import com.talentnetwork.mytask.DetailedTask;
import com.talentnetwork.util.HttpClientUtil;
import com.talentnetwork.util.MyApplication;
import com.talentnetwork.util.NetworkInfoUtil;
import com.talentnetwork.util.PageUtil;
import com.talentnetwork.util.Utility;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
/**
 * 已投职位
 * @author Administrator
 *
 */
public class CastResumeActivity extends Activity implements OnClickListener{
	
	HttpClientUtil hcu=new HttpClientUtil();
	
	private ArrayList<JobItem> jobItemList=new ArrayList<JobItem>();
	
	private ListView cr_listview;
	
	private int jobId=0;//职位id
	
	private JobItemAdapter adapter;
	
	private Button cast_resume_btn_back;//
	
	private TextView cast_resume_text;//提示
	
	//按钮
	private TextView tv_left,tv_right;
	private Button botton_left,botton_right;
	
	private View castR_progressBar;//进度条
	
	private int page=1,maxPage;//页码数
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cast_resume);
		initView();
		adapter=new JobItemAdapter(this, jobItemList, R.layout.job_item,null);
		cr_listview.setAdapter(adapter);
		Utility.setListViewHeightBasedOnChildren(cr_listview);
		cr_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long arg3) {
					if(NetworkInfoUtil.isAvailable(CastResumeActivity.this)){
						jobId=(Integer) view.getTag();
						castR_progressBar.setVisibility(View.VISIBLE);
						cr_listview.setEnabled(false);
						new DetailedTask(CastResumeActivity.this,castR_progressBar,cr_listview,jobId,null).execute();
					}else{
						showDialog();
					}
			}
		});
	}
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.cast_resume_btn_back:
			this.finish();
			break;
		case R.id.botton_left://上一页
			if(NetworkInfoUtil.isAvailable(CastResumeActivity.this)){
				if(page>1){
					castR_progressBar.setVisibility(View.VISIBLE);
					setEnabled(false);
					page=page-1;
					new CastTask().execute();
				}
			}else{
				showDialog();
			}
			break;
		case R.id.botton_right://下一页
			if(NetworkInfoUtil.isAvailable(CastResumeActivity.this)){
				if(page<maxPage){
					castR_progressBar.setVisibility(View.VISIBLE);
					setEnabled(false);
					page=page+1;
					new CastTask().execute();
				}
			}else{
				showDialog();
			}
			break;
		}
	}
	int count=0;
	private void initView() {
		
		cr_listview=(ListView) findViewById(R.id.cast_resume_listview);
		cast_resume_btn_back=(Button) findViewById(R.id.cast_resume_btn_back);
		cast_resume_btn_back.setOnClickListener(this);
		cast_resume_text=(TextView) findViewById(R.id.cast_resume_text);//提示
		castR_progressBar=findViewById(R.id.castR_progressBar);//进度条
		tv_left=(TextView) findViewById(R.id.tv_left);
		tv_right=(TextView) findViewById(R.id.tv_right);
		botton_left=(Button) findViewById(R.id.botton_left);
		botton_right=(Button) findViewById(R.id.botton_right);
		botton_left.setOnClickListener(this);
		botton_right.setOnClickListener(this);
		if(NetworkInfoUtil.isAvailable(CastResumeActivity.this)){
			castR_progressBar.setVisibility(View.VISIBLE);
			new CastTask().execute();
		}else{
			showDialog();
		}
		
	}
	/*
	 * 已投职位
	 * 访问网络取数据
	 */
	class CastTask extends AsyncTask<Void, Void, String>{

		@Override
		protected String doInBackground(Void... params) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("page", page+"");
			map.put("limit", MyApplication.getInstance().LIMIT+"");
			map.put("uid", MyApplication.getInstance().getUser().getUid()+"");
			String str = null;
			try {
				str = hcu.getRequest(CastResumeActivity.this, hcu.URL
						+ "Job3Server?", map);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return str;
		}
		@Override
		protected void onPostExecute(String result) {
			Log.i("job",result);
			jobItemList.clear();
			if(result==null||result.equals("")){
				hcu.getToast(CastResumeActivity.this, "网络连接错误！");
			}else{
				try {
					JSONObject jo=new JSONObject(result);
					count=Integer.parseInt(jo.getString("total"));
					JSONArray ja=jo.getJSONArray("roots");
					for (int i = 0; i < ja.length(); i++) {
						JSONObject joi=ja.getJSONObject(i);
						JobItem jobitem=new JobItem();
						jobitem.setId(Integer.parseInt(joi.getString("did")));
						jobitem.setJobName(joi.getString("jobs_name"));
						jobitem.setJob_company(joi.getString("company_name"));
						jobitem.setTime(joi.getString("apply_addtime"));
						jobItemList.add(jobitem);
					}
					if(jobItemList.size()!=0){
						cast_resume_text.setVisibility(View.GONE);
						adapter.notifyDataSetChanged();
					}else{
						cast_resume_text.setVisibility(View.VISIBLE);
					}
					
				} catch (JSONException e) {
					hcu.getToast(CastResumeActivity.this, "服务器出现异常");
					e.printStackTrace();
				}
			}
			adapter.notifyDataSetChanged();
			Utility.setListViewHeightBasedOnChildren(cr_listview);
			//最大页码数
			maxPage=PageUtil.getPage(count, MyApplication.getInstance().LIMIT);
			getPage(count,page,maxPage);
			castR_progressBar.setVisibility(View.GONE);//滚动条隐藏
			setEnabled(true);
		}
		
	}
	/**
	 * 设置页码
	 */
	public void getPage(int count,int page,int maxPage){
		if(count==0){
			tv_left.setText("1");
		}else{
			tv_left.setText(page+"");
		}
		tv_right.setText(""+maxPage);
	}
	
	
	/**
	 * 对话框
	 * @param message
	 */
	private void showDialog(String message){
//		this.dialogid=id;
		AlertDialog.Builder builder=new AlertDialog.Builder(CastResumeActivity.this);
		builder.setTitle("提示");
		builder.setMessage(message);
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				NetworkInfoUtil.openWifi(CastResumeActivity.this);
			}
		});
		builder.setNegativeButton("取消", null);
		builder.create().show();
	}
	
	
	/*
	 * 提示网络是否连接
	 */
	public void showDialog(){
		NetworkInfoUtil.showDialog(CastResumeActivity.this,NetworkInfoUtil.PROMPT);
	}
	/*
	 * 设置下面按钮是否可点击
	 */
	public void setEnabled(boolean enable){
		botton_left.setEnabled(enable);
		botton_right.setEnabled(enable);
	}
	
	@Override
	protected void onDestroy() {
		this.finish();
		super.onDestroy();
	}
	

}
