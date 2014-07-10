package com.talentnetwork.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.talentnetwork.adapter.JobItemAdapter;
import com.talentnetwork.bean.JobItem;
import com.talentnetwork.mytask.DetailedInvitationTask;
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
 * 面试邀请
 * @author Administrator
 *
 */
public class InvitationActivity extends Activity implements OnClickListener{
	
	private Button cast_resume_btn_back;//返回按钮
	
	private TextView title;
	
	private ListView cast_resume_listview;//lsitView
	
	private JobItemAdapter adapter=null;
	
	private List<JobItem> list=new ArrayList<JobItem>();
	
	private HttpClientUtil hcu=new HttpClientUtil();
	
	private int page=1,maxPage=1;
	
	private int count=0;
	
	private View castR_progressBar;
	
	private TextView tv_left,tv_right;
	private Button botton_left,botton_right;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cast_resume);
		initViwe();
		if(NetworkInfoUtil.isAvailable(this)){
			castR_progressBar.setVisibility(View.VISIBLE);
			new InvitatlionTask().execute();
		}else{
			showDialog();
		}
		
		
	}
	private void initViwe() {
		cast_resume_btn_back=(Button) findViewById(R.id.cast_resume_btn_back);
		cast_resume_btn_back.setOnClickListener(this);
		title=(TextView) findViewById(R.id.cr_tv_case_title);
		title.setText("面试邀请");
		cast_resume_listview=(ListView) findViewById(R.id.cast_resume_listview);
		castR_progressBar=findViewById(R.id.castR_progressBar);
		
		tv_left=(TextView) findViewById(R.id.tv_left);
		tv_right=(TextView) findViewById(R.id.tv_right);
		botton_left=(Button) findViewById(R.id.botton_left);
		botton_left.setOnClickListener(this);
		botton_right=(Button) findViewById(R.id.botton_right);
		botton_right.setOnClickListener(this);
		
		castR_progressBar.setVisibility(View.VISIBLE);
		adapter=new JobItemAdapter(this, list, R.layout.job_item,null);
		cast_resume_listview.setAdapter(adapter);
		Utility.setListViewHeightBasedOnChildren(cast_resume_listview);
		cast_resume_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long arg3) {
				if(NetworkInfoUtil.isAvailable(InvitationActivity.this)){
					int id=(Integer) view.getTag();
					castR_progressBar.setVisibility(View.VISIBLE);
					cast_resume_listview.setEnabled(false);
					new DetailedInvitationTask(InvitationActivity.this, id, castR_progressBar,cast_resume_listview).execute();
				}else{
					showDialog();
				}
				
			}
		});
		
	}
	
	class InvitatlionTask extends AsyncTask<Void,Void, String>{

		@Override
		protected String doInBackground(Void... params) {
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("page", page+"");
			map.put("limit", MyApplication.getInstance().LIMIT+"");
			map.put("uid", MyApplication.getInstance().getUser().getUid()+"");
			String str=null;
			try {
				str = hcu.getRequest(InvitationActivity.this, hcu.URL + "Job4Server?", map);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return str;
		}
		@Override
		protected void onPostExecute(String result) {
			list.clear();
			if(result==null||result.equals("")){
				hcu.getToast(InvitationActivity.this, "网络连接错误！");
			}else{
				try {
					JSONObject jo=new JSONObject(result);
					count=Integer.parseInt(jo.getString("total"));
					if(count!=0){
						JSONArray ja=jo.getJSONArray("roots");
						for (int i = 0; i < ja.length(); i++) {
							JobItem item=new JobItem();
							JSONObject j=ja.getJSONObject(i);
							item.setId(Integer.parseInt(j.getString("did")));
							item.setJobName(j.getString("jobs_name"));
							item.setJob_company(j.getString("company_name"));
							item.setTime(j.getString("interview_addtime"));
							list.add(item);
						}
					}
				} catch (JSONException e) {
					hcu.getToast(InvitationActivity.this, "服务器出现异常");
					e.printStackTrace();
				}
			}
			castR_progressBar.setVisibility(View.GONE);//滚动条隐藏
			setEnabled(true);
			//最大页码数
			maxPage=PageUtil.getPage(count, MyApplication.getInstance().LIMIT);
			getPage(count,page,maxPage);
			adapter.notifyDataSetChanged();
			Utility.setListViewHeightBasedOnChildren(cast_resume_listview);
			
		}
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.cast_resume_btn_back:
			this.finish();
			break;
		case R.id.botton_left://上一页
			if(NetworkInfoUtil.isAvailable(InvitationActivity.this)){
				if(page>1){
					castR_progressBar.setVisibility(View.VISIBLE);
					setEnabled(false);
					page=page-1;
					new InvitatlionTask().execute();
				}
			}else{
				showDialog();
			}
			
			break;
		case R.id.botton_right://下一页
			if(NetworkInfoUtil.isAvailable(InvitationActivity.this)){
				if(page<maxPage){
					castR_progressBar.setVisibility(View.VISIBLE);
					setEnabled(false);
					page=page+1;
					new InvitatlionTask().execute();
				}
			}else{
				showDialog();
			}
			break;
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
	
	public void showDialog(){
		NetworkInfoUtil.showDialog(InvitationActivity.this,NetworkInfoUtil.PROMPT);//提示网络是否连接
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
