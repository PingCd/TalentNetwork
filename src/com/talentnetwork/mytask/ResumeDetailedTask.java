package com.talentnetwork.mytask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.talentnetwork.activity.ResumeActivity;
import com.talentnetwork.bean.Education;
import com.talentnetwork.bean.MyResume;
import com.talentnetwork.bean.WorkExperience;
import com.talentnetwork.util.HttpClientUtil;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ListView;
/**
 * 简历详情
 * @author Administrator
 *
 */
public class ResumeDetailedTask extends AsyncTask<Void, Void, String>{
	
	HttpClientUtil hcu=new HttpClientUtil();
	
	private Context context=null;
	
	private int resumeId=-1;
	
	private View progress;
	
	private ListView listView;
	
	public ResumeDetailedTask(Context context,View progress,ListView listView,int resumeId) {
		this.context=context;
		this.resumeId=resumeId;
		this.progress=progress;
		this.listView=listView;
	}

	@Override
	protected String doInBackground(Void... params) {
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("id",resumeId+"");
		String str=null;
		try {
			str = hcu.getRequest(context, hcu.URL
					+ "MyResumeServer?", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
	@Override
	protected void onPostExecute(String result) {
//		Log.i("aaaaaaaaaaaaaaaaaaaaaaa", result);
		JSONObject jo;
		MyResume resume=null;
		
		if(result==null||result.equals("")||result.equals("null")){
			setlistViewEnabled();
			hcu.getToast(context, "查询不到数据");
		}else{
			try {
				jo = new JSONObject(result);
				resume=new MyResume();
				 int complete=Integer.parseInt(jo.getString("complete"));
				 if(complete==0){
					 hcu.getToast(context, "请到电脑端完善简历");
				 }else{
					 
				resume.setTitle(jo.getString("title"));
				resume.setRefreshtime(jo.getString("refreshtime"));
				resume.setClick(Integer.parseInt(jo.getString("click")));
				resume.setFullname(jo.getString("fullname"));
				resume.setSex_cn(jo.getString("sex_cn"));
				resume.setAge(Integer.parseInt(jo.getString("age")));
				resume.setMarriage_cn(jo.getString("marriage_cn"));
				resume.setEducation_cn(jo.getString("education_cn"));
				resume.setEndtime(jo.getString("endtime"));
				resume.setExperience_cn(jo.getString("experience_cn"));
				resume.setAddress(jo.getString("address"));
				resume.setTag1(jo.getString("tag1"));
				resume.setJobs_name(jo.getString("jobs_name"));
				resume.setCategory_cn(jo.getString("category_cn"));
				resume.setNature_cn(jo.getString("nature_cn"));
				resume.setDistrict_cn(jo.getString("district_cn"));
				resume.setTag2(jo.getString("tag2"));
				resume.setWage_cn(jo.getString("wage_cn"));
				resume.setSpecialty(jo.getString("specialty"));
				resume.setTelephone(jo.getString("telephone"));
				
				JSONArray  educationarray=jo.getJSONArray("set");//教育经历
				List<Education> eduList=new ArrayList<Education>();
				for (int i = 0; i < educationarray.length(); i++) {
					JSONObject j=educationarray.getJSONObject(i);
					Education education=new Education();
					education.setEducationTime(j.getString("start")+"到"+j.getString("endtime"));
					education.setEducation(jo.getString("education_cn"));
					education.setSchool(j.getString("school"));
					education.setProfessional(j.getString("speciality"));
					eduList.add(education);
				}
				resume.setEducation(eduList);
				
				List<WorkExperience> expeList=new ArrayList<WorkExperience>();//工作经历
				JSONArray  jaexpe=jo.getJSONArray("set2");
				for (int i = 0; i < jaexpe.length(); i++) {
					JSONObject j=jaexpe.getJSONObject(i);
					WorkExperience work=new WorkExperience();
					work.setTime(j.getString("start")+"到"+j.getString("endtime"));
					work.setCompany(j.getString("companyname"));
					work.setInposition(j.getString("jobs"));
					work.setDescription(j.getString("achievements"));
					expeList.add(work);
				}
				resume.setExperience(expeList);
			 }
			} catch (JSONException e) {
				e.printStackTrace();
			}
			if(resume!=null){
				Intent intent=new Intent(context,ResumeActivity.class);
				intent.putExtra("resume", resume);
				context.startActivity(intent);
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
		progress.setVisibility(View.GONE);
	}

}
