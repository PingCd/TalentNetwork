package com.talentnetwork.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.talentnetwork.adapter.ResumeAdapter;
import com.talentnetwork.bean.ResumeItem;
import com.talentnetwork.mytask.ResumeDetailedTask;
import com.talentnetwork.util.HttpClientUtil;
import com.talentnetwork.util.MyApplication;
import com.talentnetwork.util.NetworkInfoUtil;
import com.talentnetwork.util.PageUtil;
import com.talentnetwork.util.Utility;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
/**
 * 我的简历
 * @author Administrator
 *
 */
public class ResumeItemActivity extends Activity implements OnClickListener{
	
	private Button cast_resume_btn_back;
	
	private TextView cr_tv_case_title;//标题  我的简历
	
	private ListView cast_resume_listview;
	
	private List<ResumeItem> list=new ArrayList<ResumeItem>();
	
	private int page=1,maxPage=1;//初始页数为1
	
	private View castR_progressBar;
	
	private TextView tv_left,tv_right;
	
	private Button botton_left,botton_right;
	
	private ScrollView cr_sv;
	
	private ResumeAdapter adapter;
	
	private HttpClientUtil hcu=new HttpClientUtil();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cast_resume);//和已投职位公用一个xml
		initView();
	}
	private void initView() {
		cr_sv=(ScrollView) findViewById(R.id.cr_sv);
		cast_resume_btn_back=(Button) findViewById(R.id.cast_resume_btn_back);
		cast_resume_btn_back.setOnClickListener(this);
		cr_tv_case_title=(TextView) findViewById(R.id.cr_tv_case_title);
		cr_tv_case_title.setText("我的简历");
		castR_progressBar=findViewById(R.id.castR_progressBar);
		//button
		tv_left=(TextView) findViewById(R.id.tv_left);
		tv_right=(TextView) findViewById(R.id.tv_right);
		botton_left=(Button) findViewById(R.id.botton_left);
		botton_right=(Button) findViewById(R.id.botton_right);
		
		
		if(NetworkInfoUtil.isAvailable(ResumeItemActivity.this)){
			castR_progressBar.setVisibility(View.VISIBLE);
			castR_progressBar.setEnabled(false);
			new ResumeTask().execute();
		}else{
			showDialog();
		}
		
		cast_resume_listview=(ListView) findViewById(R.id.cast_resume_listview);
		adapter=new ResumeAdapter(this, list,R.layout.resume_item );
		cast_resume_listview.setAdapter(adapter);
		Utility.setListViewHeightBasedOnChildren(cast_resume_listview);
		
		cast_resume_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long arg3) {
					if(NetworkInfoUtil.isAvailable(ResumeItemActivity.this)){
						int resumeId=(Integer) view.getTag();
						castR_progressBar.setVisibility(View.VISIBLE);
						Log.i("ddddddddddddd", resumeId+"");
						cast_resume_listview.setEnabled(false);
						new ResumeDetailedTask(ResumeItemActivity.this,castR_progressBar,cast_resume_listview, resumeId).execute();
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
			if(NetworkInfoUtil.isAvailable(ResumeItemActivity.this)){
				if(page>1){
					castR_progressBar.setVisibility(View.VISIBLE);
					setEnabled(false);
					page=page-1;
					new ResumeTask().execute();
				}
			}else{
				showDialog();
			}
			break;
		case R.id.botton_right://下一页
			if(NetworkInfoUtil.isAvailable(ResumeItemActivity.this)){
				if(page<maxPage){
					castR_progressBar.setVisibility(View.VISIBLE);
					setEnabled(false);
					page=page+1;
					new ResumeTask().execute();
				}
			}else{
				showDialog();
			}
			break;
		}
	}
	/**
	 * 连接网络查询我的简历
	 */
	class ResumeTask extends AsyncTask<Void, Void, String>{
		@Override
		protected String doInBackground(Void... params) {
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("uid", MyApplication.getInstance().getUser().getUid()+"");
			map.put("limit", MyApplication.getInstance().LIMIT+"");
			map.put("page", page+"");
			String str = null;
			try {
				str = hcu.getRequest(ResumeItemActivity.this, hcu.URL
						+ "MyJobServer?", map);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return str;
		}
		@Override
		protected void onPostExecute(String result) {
			Log.i("qqqqqqqqqqqqq", result);
			int count=0;
			list.clear();
			if(result==null||result.equals("")){
				hcu.getToast(ResumeItemActivity.this, "网络连接错误！");
			}else{
				try {
					JSONObject jo=new  JSONObject(result);
					count=Integer.parseInt(jo.getString("total"));
					JSONArray ja=jo.getJSONArray("roots");
					for (int i = 0; i < ja.length(); i++) {
						JSONObject j=ja.getJSONObject(i);
						ResumeItem ri=new ResumeItem();
						ri.setId(Integer.parseInt(j.getString("id")));
						ri.setResumeName(j.getString("title"));
						ri.setTime(j.getString("addtime"));
						list.add(ri);
					}
					
				} catch (JSONException e) {
					hcu.getToast(ResumeItemActivity.this, "服务器出现异常");
					e.printStackTrace();
				}
			}
			//最大页码数
			   	adapter.notifyDataSetChanged();
				Utility.setListViewHeightBasedOnChildren(cast_resume_listview);
				maxPage=PageUtil.getPage(count, MyApplication.getInstance().LIMIT);
				getPage(count,page,maxPage);
				castR_progressBar.setVisibility(View.GONE);
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
	
	/*
	 * 提示网络是否连接
	 */
	public void showDialog(){
		NetworkInfoUtil.showDialog(ResumeItemActivity.this,NetworkInfoUtil.PROMPT);
	}
	/*
	 * 设置下面按钮是否可点击
	 */
	public void setEnabled(boolean enable){
		botton_left.setEnabled(enable);
		botton_right.setEnabled(enable);
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		castR_progressBar.setVisibility(View.GONE);
	}
	

}
