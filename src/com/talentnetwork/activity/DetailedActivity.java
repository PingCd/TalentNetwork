package com.talentnetwork.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.talentnetwork.adapter.Company_jobsAdapter;
import com.talentnetwork.bean.CompanyIn_Jobs;
import com.talentnetwork.bean.CompanyIntroduction;
import com.talentnetwork.bean.JobDetailed;
import com.talentnetwork.mytask.ApplyResumeTask;
import com.talentnetwork.mytask.DetailedTask;
import com.talentnetwork.util.HttpClientUtil;
import com.talentnetwork.util.MyApplication;
import com.talentnetwork.util.NetworkInfoUtil;
import com.talentnetwork.util.Utility;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DetailedActivity extends Activity implements OnClickListener {

	private HttpClientUtil hc = new HttpClientUtil();// 网络类

	private JobDetailed jobd = new JobDetailed();// 职位详情bean

	private CompanyIntroduction ci = null;// 企业简介bean

	private Button detailed_back;// 返回按钮
	private RelativeLayout tv_context, company_context;// RelativeLayout（职位详情，企业简介）
	private TextView tv_detailed_shape, tv_detailed_company;// 职位详情，企业简介的TextView

	private View detailed_progressBar;

	private ListView company_listview;

	private TextView tv_detailed_title, tv_detailed_count,
			detailed_content_tv_companyname, detailed_content_tv_time,
			detailed_content_tv_address, detailed_content_tv_sex,
			detailed_content_tv_education, detailed_content_tv_experience,
			detailed_content_tv_salary, detailed_tv_station,tv_company_home;
	
	private Button  detailed_btn_apply;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detailedfragment);
		Intent intent = getIntent();
		JobDetailed jobd = (JobDetailed) intent.getSerializableExtra("jobd");
		this.jobd = jobd;
		if (jobd != null) {
			initView();
		}
	}

	/**
	 * 初始化
	 */
	private void initView() {

		detailed_progressBar = findViewById(R.id.detailed_progressBar);
		detailed_back = (Button) findViewById(R.id.detailed_back);
		detailed_back.setOnClickListener(this);
		tv_context = (RelativeLayout) findViewById(R.id.context);
		company_context = (RelativeLayout) findViewById(R.id.company_context);
		tv_detailed_shape = (TextView) findViewById(R.id.tv_detailed_shape);
		tv_detailed_shape.setOnClickListener(this);
		tv_detailed_company = (TextView) findViewById(R.id.tv_detailed_company);
		tv_detailed_company.setOnClickListener(this);
		detailed_btn_apply=(Button) findViewById(R.id.detailed_btn_apply);
		detailed_btn_apply.setOnClickListener(this);
		
		tv_detailed_title = (TextView) findViewById(R.id.tv_detailed_title);// 职位名称
		tv_detailed_title.setText(jobd.getJobName());
		tv_detailed_count = (TextView) findViewById(R.id.tv_detailed_count);// 招聘人数
		tv_detailed_count.setText("（" + jobd.getPeopleCount() + "人）");
		detailed_content_tv_companyname = (TextView) findViewById(R.id.detailed_content_tv_companyname);// 公司名称
		detailed_content_tv_companyname.setText(jobd.getCompanyName());
		detailed_content_tv_time = (TextView) findViewById(R.id.detailed_content_tv_time);// 招聘期限
		detailed_content_tv_time.setText(jobd.getFinalTime());
		detailed_content_tv_address = (TextView) findViewById(R.id.detailed_content_tv_address);// 工作地点
		detailed_content_tv_address.setText(jobd.getAddress());
		detailed_content_tv_sex = (TextView) findViewById(R.id.detailed_content_tv_sex);// 性别要求
		detailed_content_tv_sex.setText(jobd.getSex());
		detailed_content_tv_education = (TextView) findViewById(R.id.detailed_content_tv_education);// 教育经历
		detailed_content_tv_education.setText(jobd.getEducation());
		detailed_content_tv_experience = (TextView) findViewById(R.id.detailed_content_tv_experience);// 工作经验
		detailed_content_tv_experience.setText(jobd.getExperience());
		detailed_content_tv_salary = (TextView) findViewById(R.id.detailed_content_tv_salary);// 职位月薪
		detailed_content_tv_salary.setText(jobd.getSalary());
		detailed_tv_station = (TextView) findViewById(R.id.detailed_tv_station);// 岗位描述
		detailed_tv_station.setText(jobd.getDescription());
		
//		tv_company_home = (TextView) findViewById(
//				R.id.tv_company_home);// 主页
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.detailed_back:
			onDestroy();
			break;
		case R.id.tv_detailed_shape:
			detailed_progressBar.setVisibility(View.GONE);
			tv_detailed_company.setEnabled(true);
			tv_detailed_shape.setBackgroundResource(R.drawable.shape_noborder);
			tv_detailed_company.setBackgroundResource(R.drawable.shape);
			tv_context.setVisibility(View.VISIBLE);
			company_context.setVisibility(View.GONE);
			break;
		case R.id.tv_detailed_company:
			if (NetworkInfoUtil.isAvailable(DetailedActivity.this)) {
				if (ci == null) {
					detailed_progressBar.setVisibility(View.VISIBLE);
					tv_detailed_company.setEnabled(false);
					new CompanyTask().execute();
				} else {
					tv_detailed_company
							.setBackgroundResource(R.drawable.shape_noborder);
					tv_detailed_shape.setBackgroundResource(R.drawable.shape);
					tv_context.setVisibility(View.GONE);
					company_context.setVisibility(View.VISIBLE);
				}
			} else {
				showDialog();
			}
			break;
		case R.id.detailed_btn_apply://投递简历
			if(NetworkInfoUtil.isAvailable(DetailedActivity.this)){
				if (MyApplication.getInstance().getUser() != null) {
					// 投递简历
					detailed_btn_apply.setEnabled(false);
					detailed_progressBar.setVisibility(View.VISIBLE);
					new ApplyResumeTask(DetailedActivity.this, detailed_progressBar,detailed_btn_apply, jobd.getId()).execute();
					
				} else {
					hc.getToast(DetailedActivity.this, "请先登录");
				}
			}else{
				showDialog();
			}
			break;
		}
	}

	/**
	 * 连接网络 查询企业简介
	 */
	class CompanyTask extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("uid", jobd.getCompanyId() + "");
			String str = null;
			try {
				str = hc.getRequest(DetailedActivity.this, hc.URL
						+ "Companys?", map);;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return str;
		}

		@Override
		protected void onPostExecute(String result) {
			if (result == null || result.equals("")) {
				return;
			}else{
				try {
					JSONObject jo = new JSONObject(result);
					ci = new CompanyIntroduction();
					if (!jo.getString("companyname").equals("")) {
						ci.setCompanyName(jo.getString("companyname"));
						ci.setIndustry(jo.getString("trade_cn"));
						ci.setScale(jo.getString("scale_cn"));
						ci.setHome(jo.getString("website"));
						JSONArray ja = jo.getJSONArray("m");
						for (int i = 0; i < ja.length(); i++) {
							JSONObject job = ja.getJSONObject(i);
							// 职位对象
							CompanyIn_Jobs jobs = new CompanyIn_Jobs();
							jobs.setJobId(Integer.parseInt(job.getString("id")));
							jobs.setJobName(job.getString("name"));
							ci.jobList.add(jobs);// 将对象放进list中
						}
						ci.setCompanyIn(jo.getString("contents"));
						ci.setPhone(jo.getString("telephone"));
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			if (ci != null) {
				setText();
			} else {
				hc.getToast(DetailedActivity.this, "没有查询到数据！");
			}
			detailed_progressBar.setVisibility(View.GONE);
			tv_detailed_company.setEnabled(false);
		}
	}

	public void setText() {
		TextView tv_company_companyname = (TextView) findViewById(R.id.tv_company_companyname);// 企业名称
		tv_company_companyname.setText(ci.getCompanyName());
		TextView tv_company_industry = (TextView) findViewById(R.id.tv_company_industry);// 行情
		tv_company_industry.setText(ci.getIndustry());
		TextView tv_company_scale = (TextView) findViewById(R.id.tv_company_scale);// 规模
		tv_company_scale.setText(ci.getScale());
		tv_company_home = (TextView) findViewById(R.id.tv_company_home);// 主页
		tv_company_home.setText(ci.getHome());
		TextView company_tv_introduction_context = (TextView) findViewById(R.id.company_tv_introduction_context);// 企业简介
		company_tv_introduction_context.setText(ci.getCompanyIn());
		TextView company_tv_phone = (TextView) findViewById(R.id.company_tv_phone);// 电话
		company_tv_phone.setText(ci.getPhone());
		// 职位名称listview
		company_listview = (ListView) findViewById(R.id.company_listview);
		Company_jobsAdapter adapter = new Company_jobsAdapter(
				DetailedActivity.this, ci.jobList, R.layout.jobs);
		company_listview.setAdapter(adapter);
		Utility.setListViewHeightBasedOnChildren(company_listview);
		company_listview
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long arg3) {
						int jobId = (Integer) view.getTag();
						if (NetworkInfoUtil.isAvailable(DetailedActivity.this)) {
							detailed_progressBar.setVisibility(View.VISIBLE);
							company_listview.setEnabled(false);
							new DetailedTask(DetailedActivity.this,
									detailed_progressBar, company_listview,
									jobId, null).execute();
						} else {
							showDialog();
						}

					}
				});

		tv_detailed_company.setBackgroundResource(R.drawable.shape_noborder);
		tv_detailed_shape.setBackgroundResource(R.drawable.shape);
		tv_context.setVisibility(View.GONE);
		company_context.setVisibility(View.VISIBLE);
		tv_company_home.setText(ci.getHome());
		//连接地址
				tv_company_home.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						
						String url = tv_company_home.getText().toString();
						Intent intent = new Intent(DetailedActivity.this, WebViewActivity.class);
						intent.putExtra("url", url);
						startActivity(intent);
						
					}
				});
	}
	
	
	/*
	 * 提示网络是否连接
	 */
	public void showDialog(){
		NetworkInfoUtil.showDialog(DetailedActivity.this,NetworkInfoUtil.PROMPT);
	}

	@Override
	protected void onStop() {
		onDestroy();
		super.onStop();
	}

	@Override
	public void onDestroy() {
		this.finish();
		super.onDestroy();
	}

}
