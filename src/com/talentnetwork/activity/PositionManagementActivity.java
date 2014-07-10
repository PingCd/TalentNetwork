package com.talentnetwork.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.talentnetwork.adapter.PositionManagementAdapter;
import com.talentnetwork.bean.PositionManagement;
import com.talentnetwork.mytask.DetaileManagementTask;
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
 * 职位管理
 * @author Administrator
 *
 */
public class PositionManagementActivity extends Activity implements OnClickListener{
	
	private ListView pm_listView;
	
	private ArrayList<PositionManagement> pmList=new ArrayList<PositionManagement>();
	
	private Button pm_btn_back;
	
	private int page=1,maxPage=1;//初始页
	
	private int count=0;
	
	private PositionManagementAdapter pmAdapter=null;
	
	//buttom
	private TextView tv_left,tv_right;
	private Button botton_left,botton_right;
	
	private View position_progressBar;//滚动条
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.position_management);
		initView();
		pmAdapter=new PositionManagementAdapter(this, pmList, R.layout.position_management);
		pm_listView.setAdapter(pmAdapter);
		Utility.setListViewHeightBasedOnChildren(pm_listView);
		//设置监听查询发布的职位
		pm_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long arg3) {
				if(NetworkInfoUtil.isAvailable(PositionManagementActivity.this)){
					int id=(Integer) view.getTag();
					TextView pmi_tv_stateValue=(TextView) view.findViewById(R.id.pmi_tv_stateValue);
					if(pmi_tv_stateValue.getText().toString().equals("已审核")){
						position_progressBar.setVisibility(View.VISIBLE);
						pm_listView.setEnabled(false);
						new DetaileManagementTask(PositionManagementActivity.this, id,pm_listView).execute();
					}
					
				}else{
					showDialog();
				}
				
				
			}
		});
		
	}
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.pm_btn_back:
			this.finish();
		break;
		case R.id.botton_left://上一页
			if(page>1){
				if(NetworkInfoUtil.isAvailable(PositionManagementActivity.this)){
					page=page-1;
					position_progressBar.setVisibility(View.VISIBLE);
					setEnabled(false);
					new ManagementTask().execute();
				}else{
					showDialog();
				}
			}
			break;
		case R.id.botton_right://下一页
			if(page<maxPage){
				if(NetworkInfoUtil.isAvailable(PositionManagementActivity.this)){
					page=page+1;
					position_progressBar.setVisibility(View.VISIBLE);
					setEnabled(false);
					new ManagementTask().execute();
				}else{
					showDialog();
				}
				break;
			}
			
		}
		
	}
	private void initView() {
		
		
		pm_listView=(ListView) findViewById(R.id.pm_listView);
		pm_btn_back=(Button) findViewById(R.id.pm_btn_back);
		pm_btn_back.setOnClickListener(this);
		tv_left=(TextView) findViewById(R.id.tv_left);
		tv_right=(TextView) findViewById(R.id.tv_right);
		botton_left=(Button) findViewById(R.id.botton_left);
		botton_left.setOnClickListener(this);
		botton_right=(Button) findViewById(R.id.botton_right);
		botton_right.setOnClickListener(this);
		position_progressBar=findViewById(R.id.position_progressBar);
		
		if(NetworkInfoUtil.isAvailable(PositionManagementActivity.this)){
			position_progressBar.setVisibility(View.VISIBLE);
			new ManagementTask().execute();
		}else{
			showDialog();
		}
		
	}
	
	/**
	 * 网络连接查询职位
	 * @author Administrator
	 *
	 */
	public class ManagementTask extends AsyncTask<Void, Void, String>{
		
		 HttpClientUtil hcu=new HttpClientUtil();
		
		@Override
		protected String doInBackground(Void... params) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("page",page+"");
			map.put("limit",MyApplication.getInstance().LIMIT+"");
			map.put("uid",MyApplication.getInstance().getUser().getUid()+"");
			String str=null;
			try {
				str = hcu.getRequest(PositionManagementActivity.this, hcu.URL
						+ "CJob3s?", map);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return str;
		}
		@Override
		protected void onPostExecute(String result) {
			pmList.clear();
			if(result==null||result.equals("")){
				return;
			}else{
				try {
					JSONObject jo=new JSONObject(result);
					count=Integer.parseInt(jo.getString("total"));
					if(count>0){
						JSONArray ja=jo.getJSONArray("roots");
						for (int i = 0; i < ja.length(); i++) {
							JSONObject j=ja.getJSONObject(i);
							PositionManagement pm=new PositionManagement();
							pm.setId(Integer.parseInt(j.getString("id")));
							pm.setPositionName(j.getString("jobs_name"));
							pm.setPositionRelease(j.getString("addtime"));
							pm.setPositionBy(j.getString("deadline"));
							if(j.getString("audit").equals("0")){
								pm.setState("未审核");
							}else{
								pm.setState("已审核");
							}
							pmList.add(pm);
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			pmAdapter.notifyDataSetChanged();
			Utility.setListViewHeightBasedOnChildren(pm_listView);
			//最大页码数
			maxPage=PageUtil.getPage(count, MyApplication.getInstance().LIMIT);
			getPage(count,page,maxPage);
			position_progressBar.setVisibility(View.GONE);//滚动条隐藏
			setEnabled(false);
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
		NetworkInfoUtil.showDialog(PositionManagementActivity.this,NetworkInfoUtil.PROMPT);
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
		position_progressBar.setVisibility(View.GONE);//滚动条隐藏
		super.onPause();
	}
	
	@Override
	protected void onDestroy() {
		this.finish();
		super.onDestroy();
	}

}
