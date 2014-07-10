package com.talentnetwork.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.talentnetwork.activity.JobFragment.OnbrakClickListener;
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

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 职位详情
 * 
 * @author李朋丽
 */
@SuppressLint("ValidFragment")
public class DetailedFragment extends Fragment implements View.OnClickListener {

	private HttpClientUtil hc = new HttpClientUtil();// 网络类

	private JobDetailed job = new JobDetailed();// 职位详情bean
	
	//接口
	private OnbrakClickListener onb=null;
		

	/**
	 * 构造方法（传递数据）
	 * 
	 * @param jobd
	 */
	public DetailedFragment(JobDetailed jobd) {
		if (jobd != null) {
			this.job = jobd;
		}
	}

	/**
	 * 接口
	 */
	private DetailedOnbrakClickListener deon = null;

	public interface DetailedOnbrakClickListener {
		public void back();
	}

	private CompanyIntroduction ci = null;// 企业简介bean

	private Button detailed_back;// 返回按钮
	private RelativeLayout tv_context, company_context;// RelativeLayout（职位详情，企业简介）
	private TextView tv_detailed_shape, tv_detailed_company;// 职位详情，企业简介的TextView

	private View detailed_progressBar;

	private TextView tv_detailed_title, tv_detailed_count,
			detailed_content_tv_companyname, detailed_content_tv_time,
			detailed_content_tv_address, detailed_content_tv_sex,
			detailed_content_tv_education, detailed_content_tv_experience,
			detailed_content_tv_salary, detailed_tv_station;

	private Button detailed_btn_apply;// 申请职位按钮
	
	private ListView company_listview;
	
	
	private TextView company_tv_phone_title,company_tv_phone, tv_company_home ;
	

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			deon = (DetailedOnbrakClickListener) activity;
			onb=(OnbrakClickListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnArticleSelectedListener");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.detailedfragment, container,
				false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
	}

	/**
	 * 初始化
	 */
	private void initView() {
		
		company_tv_phone_title=(TextView) getActivity().findViewById(R.id.company_tv_phone_title);
		
		detailed_progressBar = getView()
				.findViewById(R.id.detailed_progressBar);
		detailed_back = (Button) getView().findViewById(R.id.detailed_back);
		detailed_back.setOnClickListener(this);

		// 申请职位按钮
		detailed_btn_apply = (Button) getView().findViewById(
				R.id.detailed_btn_apply);
		detailed_btn_apply.setOnClickListener(this);

		tv_context = (RelativeLayout) getView().findViewById(R.id.context);
		company_context = (RelativeLayout) getView().findViewById(
				R.id.company_context);
		tv_detailed_shape = (TextView) getView().findViewById(
				R.id.tv_detailed_shape);
		tv_detailed_shape.setOnClickListener(this);
		tv_detailed_company = (TextView) getView().findViewById(
				R.id.tv_detailed_company);
		tv_detailed_company.setOnClickListener(this);
		tv_detailed_title = (TextView) getView().findViewById(
				R.id.tv_detailed_title);// 职位名称
		tv_detailed_title.setText(job.getJobName());
		tv_detailed_count = (TextView) getView().findViewById(
				R.id.tv_detailed_count);// 招聘人数
		tv_detailed_count.setText("（" + job.getPeopleCount() + "人）");
		detailed_content_tv_companyname = (TextView) getView().findViewById(
				R.id.detailed_content_tv_companyname);// 公司名称
		detailed_content_tv_companyname.setText(job.getCompanyName());
		detailed_content_tv_time = (TextView) getView().findViewById(
				R.id.detailed_content_tv_time);// 招聘期限
		detailed_content_tv_time.setText(job.getFinalTime());
		detailed_content_tv_address = (TextView) getView().findViewById(
				R.id.detailed_content_tv_address);// 工作地点
		detailed_content_tv_address.setText(job.getAddress());
		detailed_content_tv_sex = (TextView) getView().findViewById(
				R.id.detailed_content_tv_sex);// 性别要求
		detailed_content_tv_sex.setText(job.getSex());
		detailed_content_tv_education = (TextView) getView().findViewById(
				R.id.detailed_content_tv_education);// 教育经历
		detailed_content_tv_education.setText(job.getEducation());
		detailed_content_tv_experience = (TextView) getView().findViewById(
				R.id.detailed_content_tv_experience);// 工作经验
		detailed_content_tv_experience.setText(job.getExperience());
		detailed_content_tv_salary = (TextView) getView().findViewById(
				R.id.detailed_content_tv_salary);// 职位月薪
		detailed_content_tv_salary.setText(job.getSalary());
		detailed_tv_station = (TextView) getView().findViewById(
				R.id.detailed_tv_station);// 岗位描述
		detailed_tv_station.setText(job.getDescription());
		//企业登录后将投递简历按钮和联系方式
		if(MyApplication.getInstance().getUser()!=null){
			if(MyApplication.getInstance().getUser().getType()==1){
				detailed_btn_apply.setVisibility(View.GONE);
			}
		}
		
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.detailed_back:
			deon.back();
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
			if (NetworkInfoUtil.isAvailable(getActivity())) {
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
			
			if(NetworkInfoUtil.isAvailable(getActivity())){
				if (MyApplication.getInstance().getUser() != null) {
					// 投递简历
					detailed_btn_apply.setEnabled(false);
					detailed_progressBar.setVisibility(View.VISIBLE);
					new ApplyResumeTask(getActivity(), detailed_progressBar,detailed_btn_apply, job.getId()).execute();
					
				} else {
					hc.getToast(getActivity(), "请先登录");
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
			map.put("uid", job.getCompanyId() + "");
			String str=null;
			try {
				str = hc.getRequest(getActivity(), hc.URL + "Companys?", map);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return str;
		}

		@Override
		protected void onPostExecute(String result) {
			if (result == null || result.equals("")) {
				hc.getToast(getActivity(), "网络连接错误！");
			}else{
				try {
					JSONObject jo = new JSONObject(result);
					if (!jo.getString("companyname").equals("")) {
						ci = new CompanyIntroduction();
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
					hc.getToast(getActivity(), "服务器出现异常");
					e.printStackTrace();
				}
				if (ci != null) {
					setText();
				} else {
					hc.getToast(getActivity(), "没有查询到数据！");
				}
			}
			detailed_progressBar.setVisibility(View.GONE);
			tv_detailed_company.setEnabled(true);
		}
	}

	public void setText() {
		TextView tv_company_companyname = (TextView) getView().findViewById(
				R.id.tv_company_companyname);// 企业名称
		tv_company_companyname.setText(ci.getCompanyName());
		TextView tv_company_industry = (TextView) getView().findViewById(
				R.id.tv_company_industry);// 行情
		tv_company_industry.setText(ci.getIndustry());
		TextView tv_company_scale = (TextView) getView().findViewById(
				R.id.tv_company_scale);// 规模
		tv_company_scale.setText(ci.getScale());
		tv_company_home = (TextView) getView().findViewById(
				R.id.tv_company_home);// 主页
		tv_company_home.setText(ci.getHome());
		//连接地址
//		tv_company_home.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				
//				String url = tv_company_home.getText().toString();
////				Intent intent = new Intent(getActivity(), WebViewActivity.class);
////				intent.putExtra("url", url);
////				startActivity(intent);
//				
//				
//				 Uri uri = Uri.parse("http://"+url);  
//				  Intent it = new Intent(Intent.ACTION_VIEW, uri);  
//				  startActivity(it);
//				
//			}
//		});
//		
		TextView company_tv_introduction_context = (TextView) getView()
				.findViewById(R.id.company_tv_introduction_context);// 企业简介
		company_tv_introduction_context.setText(ci.getCompanyIn());
		company_tv_phone = (TextView) getView().findViewById(
				R.id.company_tv_phone);// 电话
		
		company_tv_phone.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (MyApplication.getInstance().getUser() != null) {
					if(MyApplication.getInstance().getUser().getType()==2){
						Intent intent = new Intent();
						intent.setAction(Intent.ACTION_CALL);
						intent.setData(Uri.parse("tel:"+company_tv_phone.getText().toString()));
						startActivity(intent);
					}
				}
				
			}
		});
		
		if (MyApplication.getInstance().getUser() != null) {
			if(MyApplication.getInstance().getUser().getType()==1){
				company_tv_phone_title.setVisibility(View.GONE);
				company_tv_phone.setVisibility(View.GONE);
			}else{
				company_tv_phone.setText(ci.getPhone());
			}
			
		} else {
			company_tv_phone.setText("请登录！");
			company_tv_phone.setTextColor(Color.BLUE);
		}

		// 企业简介中职位listview
		company_listview = (ListView) getView().findViewById(
				R.id.company_listview);
		Company_jobsAdapter adapter = new Company_jobsAdapter(getActivity(),
				ci.jobList, R.layout.jobs);
		company_listview.setAdapter(adapter);
		Utility.setListViewHeightBasedOnChildren(company_listview);
		company_listview
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> dapter, View view,
							int position, long arg3) {
						 if(NetworkInfoUtil.isAvailable(getActivity())){
							 	int jobId = (Integer) view.getTag();
							 	detailed_progressBar.setVisibility(View.VISIBLE);
							 	company_listview.setEnabled(false);//将listView设置不可点击
								new DetailedTask(getActivity(),detailed_progressBar,company_listview, jobId, onb)
										.execute();
						 }else{
							showDialog();
						 }
					}
				});

		tv_detailed_company.setBackgroundResource(R.drawable.shape_noborder);
		tv_detailed_shape.setBackgroundResource(R.drawable.shape);
		tv_context.setVisibility(View.GONE);
		company_context.setVisibility(View.VISIBLE);
	}
	public void showDialog(){
		NetworkInfoUtil.showDialog(getActivity(),NetworkInfoUtil.PROMPT);//提示网络是否连接
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

}
