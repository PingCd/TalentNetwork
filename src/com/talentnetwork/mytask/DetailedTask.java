package com.talentnetwork.mytask;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.talentnetwork.activity.DetailedActivity;
import com.talentnetwork.activity.JobFragment.OnbrakClickListener;
import com.talentnetwork.bean.JobDetailed;
import com.talentnetwork.util.HttpClientUtil;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
/**
 * 职位详情
 * @author Administrator
 *
 */
public class DetailedTask extends AsyncTask<Void, Void, String> {
	
	private JobDetailed jobd=null;//职位详情bean
	
	private int jobId;
	
	private Context context=null;
	
	private OnbrakClickListener onb=null;
	
	private View pro;
	
	private ListView listView;
	
	
	public DetailedTask(Context context,View pro,ListView listView,int jobId,OnbrakClickListener onb) {
		this.context=context;
		this.jobId=jobId;
		this.onb=onb;
		this.pro=pro;
		this.listView=listView;
		
	}
	

	HttpClientUtil hcu=new HttpClientUtil();

	@Override
	protected String doInBackground(Void... params) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id",jobId+"");
		String str=null;
		try {
			str = hcu.getRequest(context, hcu.URL
					+ "CJobs?", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
	
	@Override
	protected void onPostExecute(String result) {
		
		if(result==null||result.equals("")){
			hcu.getToast(context, "网络连接错误！");
		}else if(result.equals("null")){
			hcu.getToast(context, "该职位已被删除！");
		}else{
			try {
				//将String转为json然后给对象赋值
				JSONObject jo=new JSONObject(result);
				jobd=new JobDetailed();
				jobd.setId(Integer.parseInt(jo.getString("job_id")));
				jobd.setJobName(jo.getString("jobs_name"));
				jobd.setCompanyId(Integer.parseInt(jo.getString("uid")));
				jobd.setCompanyName(jo.getString("companyname"));
				jobd.setFinalTime(jo.getString("deadline"));
				jobd.setAddress(jo.getString("district_cn"));
				jobd.setSex(jo.getString("sex_cn"));
				jobd.setPeopleCount(Integer.parseInt(jo.getString("amount")));
				jobd.setEducation(jo.getString("education_cn"));
				jobd.setExperience(jo.getString("experience_cn"));
				jobd.setSalary(jo.getString("wage_cn"));
				jobd.setDescription(jo.getString("contents"));
				if(jobd!=null){
					//跳到详情界面将jobd对象作为参数传过去
					if(onb!=null){
						Log.i("----fffffffffff----", onb.toString());
						onb.onback(jobd);
					}else{
//						if(situ!=0){
//				//				将数据传递到DetailedFragment类中		
//						}else{
							Intent intent=new Intent(context ,DetailedActivity.class);
							intent.putExtra("jobd", jobd);
							context.startActivity(intent);
//						}
					}
					
				}else{
					hcu.getToast(context, "没有获取到数据");
				}
			} catch (JSONException e) {
				hcu.getToast(context, "网络连接错误！");
				e.printStackTrace();
			}
		}
		setProgressIsGone();
		setlistViewEnabled();
	}
	//设置listview获取焦点
		public void setlistViewEnabled(){
			listView.setEnabled(true);
		}
		//设置progress隐藏
		public void setProgressIsGone(){
			pro.setVisibility(View.GONE);
		}

	
	
}
