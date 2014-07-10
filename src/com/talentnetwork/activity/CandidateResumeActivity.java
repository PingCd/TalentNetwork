package com.talentnetwork.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.talentnetwork.adapter.JobItemAdapter;
import com.talentnetwork.bean.JobItem;
import com.talentnetwork.mytask.ResumeDetailedTask;
import com.talentnetwork.util.HttpClientUtil;
import com.talentnetwork.util.MyApplication;
import com.talentnetwork.util.NetworkInfoUtil;
import com.talentnetwork.util.PageUtil;
import com.talentnetwork.util.Utility;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
/**
 * 应聘者简历
 * @author Administrator
 *
 */
public class CandidateResumeActivity extends Activity implements OnClickListener{
	
	private ListView listview;
	
	private ArrayList<JobItem> list=new ArrayList<JobItem>();
	
	private Button cr_back;
	
	private Button botton_left,botton_right;
	
	private TextView tv_left,tv_right;
	
	private int page=1,maxPage=1;
	
	private int count=0;//总条数
	
	private JobItemAdapter adapter=null;
	
	private View candidate_progressBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.candidate_resume);
		initView();
		
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long arg3) {
				if(NetworkInfoUtil.isAvailable(CandidateResumeActivity.this)){
					int resumeId= (Integer) view.getTag();
					candidate_progressBar.setVisibility(View.VISIBLE);
					listview.setEnabled(false);
					new ResumeDetailedTask(CandidateResumeActivity.this,candidate_progressBar,listview, resumeId).execute();
				}else{
					showDialog();
				}
			}
			
		});
		
		
	}
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.cr_back:
			this.finish();
			break;
		case R.id.botton_left:
			if(page>1){
				page=page-1;
				if(NetworkInfoUtil.isAvailable(CandidateResumeActivity.this)){
					candidate_progressBar.setVisibility(View.VISIBLE);
					setEnabled(false);
					new CandidateResumeTask().execute();
				}else{
					showDialog();
				}
			}
			
			break;
		case R.id.botton_right:
			if(page<maxPage){
				page=page+1;
				if(NetworkInfoUtil.isAvailable(CandidateResumeActivity.this)){
					candidate_progressBar.setVisibility(View.VISIBLE);
					setEnabled(false);
					new CandidateResumeTask().execute();
				}else{
					showDialog();
				}
			}
			
			break;
		}
	}
	private void initView() {
		
		listview=(ListView) findViewById(R.id.cr_listview);
		cr_back=(Button) findViewById(R.id.cr_back);
		cr_back.setOnClickListener(this);
		tv_left=(TextView) findViewById(R.id.tv_left);
		tv_right=(TextView) findViewById(R.id.tv_right);
		botton_left=(Button) findViewById(R.id.botton_left);
		botton_left.setOnClickListener(this);
		botton_right=(Button) findViewById(R.id.botton_right);
		botton_right.setOnClickListener(this);
		candidate_progressBar=findViewById(R.id.candidate_progressBar);
		
		
		candidate_progressBar.setVisibility(View.VISIBLE);
		new CandidateResumeTask().execute();
		
		adapter=new JobItemAdapter(this, list, R.layout.job_item,null);
		listview.setAdapter(adapter);
		Utility.setListViewHeightBasedOnChildren(listview);
		
	}
	
	/***
	 * 应聘者简历
	 * @author Administrator
	 *
	 */
	public class CandidateResumeTask extends AsyncTask<Void, Void, String>{
		
		private HttpClientUtil hcu=new HttpClientUtil();
		@Override
		protected String doInBackground(Void... params) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("page", page+"");
			map.put("limit", MyApplication.getInstance().LIMIT+"");
			map.put("uid", MyApplication.getInstance().getUser().getUid()+"");
			String str = null;
			try {
				str = hcu.getRequest(CandidateResumeActivity.this, hcu.URL+ "CJob1s?", map);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return str;
		}
		int count=0;
		@Override
		protected void onPostExecute(String result) {
//			Log.i("aaaaaaaaaaddddddddddaa", result);
			list.clear();
			if(result==null||result.equals("")){
				hcu.getToast(CandidateResumeActivity.this, "网络连接错误！");
			}else{
				try {
					JSONObject jo=new JSONObject(result);
					count=Integer.parseInt(jo.getString("total"));
					if(count>0){
						JSONArray ja=jo.getJSONArray("roots");
						for (int i = 0; i < ja.length(); i++) {
							JSONObject j=ja.getJSONObject(i);
							JobItem item=new JobItem();
							item.setId(Integer.parseInt(j.getString("id")));
							item.setJobName(j.getString("title"));
							item.setJob_company(j.getString("name"));
							item.setTime(j.getString("time"));
							list.add(item);
						}
					}
					
				} catch (JSONException e) {
					hcu.getToast(CandidateResumeActivity.this, "服务器出现异常");
					e.printStackTrace();
				}
			}
			adapter.notifyDataSetChanged();
			Utility.setListViewHeightBasedOnChildren(listview);
			//最大页码数
			maxPage=PageUtil.getPage(count, MyApplication.getInstance().LIMIT);
			getPage(count,page,maxPage);;
			candidate_progressBar.setVisibility(View.GONE);//滚动条隐藏
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
		NetworkInfoUtil.showDialog(CandidateResumeActivity.this,NetworkInfoUtil.PROMPT);
	}
	/*
	 * 设置下面按钮是否可点击
	 */
	public void setEnabled(boolean enable){
		botton_left.setEnabled(enable);
		botton_right.setEnabled(enable);
	}
	
	@Override
	protected void onPause() {
		 candidate_progressBar.setVisibility(View.GONE);
		super.onPause();
	}
	
	@Override
	protected void onDestroy() {
		this.finish();
		super.onDestroy();
	}

}
