package com.talentnetwork.activity;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.talentnetwork.adapter.JobItemAdapter;
import com.talentnetwork.bean.JobDetailed;
import com.talentnetwork.bean.JobItem;
import com.talentnetwork.mytask.DetailedTask;
import com.talentnetwork.util.HttpClientUtil;
import com.talentnetwork.util.MyApplication;
import com.talentnetwork.util.NetworkInfoUtil;
import com.talentnetwork.util.PageUtil;
import com.talentnetwork.util.Utility;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

/**
 *职位
 * 
 * @author
 */
@SuppressLint("ValidFragment")
public class JobFragment extends Fragment implements OnClickListener{
	
	private String search="";//搜索内容
	private int searchType=1;//搜索类型（如职位或公司）
	
	//构造方法
		public JobFragment(String str,int type) {
			search=str;
			searchType=type;
		}
	
	private int page=1,maxPage=1;//页码数
	
	HttpClientUtil hu=new HttpClientUtil();
	
	private EditText et_search;//搜索内容
	//接口
	private OnbrakClickListener onb=null;
	
	public interface OnbrakClickListener{
		public void onback(JobDetailed job);
	}
	private Spinner search_spinner=null;//下拉列表
	
	private ListView job_listview=null;
	private List<JobItem> jobItemList=new ArrayList<JobItem>();
	private JobItemAdapter adapter=null;
	
	private TextView newjob_count; //职位总数量
	
	private TextView job_tv_text;//提示
	
	private View job_progressBar;
	//button
	private Button job_botton_left,job_botton_right;
	private TextView job_tv_page_left,job_tv_page_right;
	private Button btn_search;//搜索按钮
	
	private int jobId=0;//职位id
	
	private JobDetailed jobd=null;//职位详情bean
	
	public JobFragment() {
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	  @Override
	    public void onAttach(Activity activity) {
	        super.onAttach(activity);
	        try {
	        	onb = (OnbrakClickListener) activity;
	        } catch (ClassCastException e) {
	            throw new ClassCastException(activity.toString() + " must implement OnArticleSelectedListener");
	        }
	    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.jobfragment, container, false);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		search_spinner=(Spinner) getView().findViewById(R.id.home_spinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource( getActivity(), R.array.search_arry, android.R.layout.simple_spinner_item); 
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
		search_spinner.setAdapter(adapter);
		search_spinner.setSelection(searchType-1);
		initView();
		//选择职位or公司
				search_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int p, long arg3) {
						if(p==0){
							searchType=1;
						}else{
							searchType=2;
						}
					}
					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						
					}
				});
	}
	private void initView() {
		//共多少职位
		newjob_count=(TextView) getView().findViewById(R.id.newjob_count);
		//progressbar
		job_progressBar=getView().findViewById(R.id.job_progressBar);
		//button
		job_botton_left=(Button) getView().findViewById(R.id.job_botton_left);
		job_botton_right=(Button) getView().findViewById(R.id.job_botton_right);
		job_botton_left.setOnClickListener(this);
		job_botton_right.setOnClickListener(this);
		job_tv_page_left=(TextView) getView().findViewById(R.id.job_tv_page_left);
		job_tv_page_right=(TextView) getView().findViewById(R.id.job_tv_page_right);
		//搜索
		et_search=(EditText) getView().findViewById(R.id.et_search);
		et_search.setText(search);
		btn_search=(Button) getView().findViewById(R.id.btn_search);
		btn_search.setOnClickListener(this);
		//listView
		job_listview=(ListView) getView().findViewById(R.id.job_listview);
		//提示
		job_tv_text=(TextView) getView().findViewById(R.id.job_tv_text);
		//异步任务查询工作职位列表
		if(NetworkInfoUtil.isAvailable(getActivity())){
			job_progressBar.setVisibility(View.VISIBLE);
			new MyTask().execute();
		}else{
			showDialog();
		}
		adapter=new JobItemAdapter(getActivity(), jobItemList, R.layout.job_item,onb);
		job_listview.setAdapter(adapter);
		//设置监听查询工作详情
		job_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long arg3) {
				if(onb!=null){
					if(NetworkInfoUtil.isAvailable(getActivity())){
						jobId=(Integer) view.getTag();
//						Log.i("jobid", "--"+jobId);
						job_progressBar.setVisibility(View.VISIBLE);
						job_listview.setEnabled(false);
						new DetailedTask(getActivity(),job_progressBar,job_listview,jobId,onb).execute();
					}else{
						showDialog();
					}
				}
			}
		});
	}
	/**
	 * 连接网络
	 * 查询工作职位列表
	 */
	private int count=0;
	class MyTask extends AsyncTask<Void, Void, String>{
		@Override
		protected String doInBackground(Void... params) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("page", page+"");
			map.put("limit", MyApplication.getInstance().LIMIT+"");
			try {
				String str=URLEncoder.encode(search, "utf-8");
				map.put("content",str  );
			} catch (UnsupportedEncodingException e) {
			}
			map.put("k", searchType+"");
			String str=null;
			try {
				str = hu.getRequest(getActivity(), hu.URL
						+ "JobServer?", map);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return str;
		}
		@Override
		protected void onPostExecute(String result) {
			jobItemList.clear();
			if(result==null||result.equals("")){
				hu.getToast(getActivity(), "网络连接错误！");
			}else{
				try {
					JSONObject jo=new JSONObject(result);
					count=Integer.parseInt(jo.getString("total"));
					if(count<=0){
						job_tv_text.setVisibility(View.VISIBLE);
						Utility.setListViewHeightBasedOnChildren(job_listview);
					}else{
						JSONArray ja=jo.getJSONArray("roots");
						for (int i = 0; i < ja.length(); i++) {
							JSONObject joi=ja.getJSONObject(i);
							JobItem jobitem=new JobItem();
							jobitem.setId(Integer.parseInt(joi.getString("id")));
							jobitem.setJobName(joi.getString("jobs_name"));
							jobitem.setJob_company(joi.getString("companyname"));
							jobitem.setTime(joi.getString("addtime"));
							jobItemList.add(jobitem);
						}
							job_tv_text.setVisibility(View.GONE);
					}
				} catch (JSONException e) {
					hu.getToast(getActivity(), "服务器出现异常");
					e.printStackTrace();
				}
				//最大页码数
			}
			maxPage=PageUtil.getPage(count, MyApplication.getInstance().LIMIT);
			getPage(count,page,maxPage);
			job_progressBar.setVisibility(View.GONE);//滚动条隐藏
			job_listview.setEnabled(true);
			setEnabled(true);
			adapter.notifyDataSetChanged();
			Utility.setListViewHeightBasedOnChildren(job_listview);
		}
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btn_search://搜索按钮
			if(NetworkInfoUtil.isAvailable(getActivity())){
				search=et_search.getText().toString();
				job_progressBar.setVisibility(View.VISIBLE);
				page=1;
				new MyTask().execute(); //获取后台数据
			}else{
				showDialog();
			}
			break;
		case R.id.job_botton_left://上一页
			if(NetworkInfoUtil.isAvailable(getActivity())){
				if(page>1){
					job_progressBar.setVisibility(View.VISIBLE);
					setEnabled(false);
					page=page-1;
					new MyTask().execute();
				}
			}else{
				showDialog();
			}
			break;
		case R.id.job_botton_right://下一页
			if(NetworkInfoUtil.isAvailable(getActivity())){
				if(page<maxPage){
					job_progressBar.setVisibility(View.VISIBLE);
					setEnabled(false);
					page=page+1;
					new MyTask().execute();
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
			job_tv_page_left.setText("1");
		}else{
			job_tv_page_left.setText(page+"");
		}
		newjob_count.setText(count+"");
		job_tv_page_right.setText(""+maxPage);
	}
	/*
	 * 提示网络是否连接
	 */
	public void showDialog(){
		NetworkInfoUtil.showDialog(getActivity(),NetworkInfoUtil.PROMPT);
	}
	/*
	 * 设置下面按钮是否可点击
	 */
	public void setEnabled(boolean enable){
		job_botton_left.setEnabled(enable);
		job_botton_right.setEnabled(enable);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
