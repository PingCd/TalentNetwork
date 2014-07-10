package com.talentnetwork.activity;

import java.util.ArrayList;
import java.util.List;

import com.talentnetwork.adapter.EducationAdapter;
import com.talentnetwork.adapter.WorkExperienceAdapter;
import com.talentnetwork.bean.Education;
import com.talentnetwork.bean.Language;
import com.talentnetwork.bean.MyResume;
import com.talentnetwork.bean.WorkExperience;
import com.talentnetwork.util.Utility;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 简历
 * 
 * @author Administrator
 * 
 */
public class ResumeActivity extends Activity implements OnClickListener {
	private ListView resume_listview;
	private ListView resume_tv_work;
	private List<Education> educationlist = null;
	private List<WorkExperience> weList = null;
	private List<Language> lanList = null;
	private Button back;// 返回按钮

	private MyResume resume = null;// 简历bean

	private TextView resume_tv_resumetitle, resume_tv_time, resume_tv_number,
			resume_tv_browsecount, resume_tv_userName, resume_tv_sex,
			resume_tv_age, resume_tv_marital, resume_tv_education,
			resume_tv_graduationTime, resume_tv_jobExperience,
			resume_tv_address, resume_tv_other;

	private TextView resume_tv_jobintention_intention,
			resume_tv_jobintention_category, resume_tv_jobintention_nature,
			resume_tv_jobintention_address, resume_tv_jobintention_requirement,
			resume_tv_jobintention_salary;
	
	private TextView resume_tv_Language,resume_tv_phone;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.resume);
		initView();
		if(resume!=null){
			setView();
			/** 教育经历 */
			EducationAdapter eadapter = new EducationAdapter(this, educationlist,
					R.layout.education_item);
			resume_listview.setAdapter(eadapter);
			Utility.setListViewHeightBasedOnChildren(resume_listview);
			/** 工作经验 */
			WorkExperienceAdapter wadapter = new WorkExperienceAdapter(this,
					weList, R.layout.work_experience_item);
			resume_tv_work.setAdapter(wadapter);
			Utility.setListViewHeightBasedOnChildren(resume_tv_work);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.resume_back:
			ResumeActivity.this.finish();
			break;
		}

	}

	private void initView() {
		Intent intent = getIntent();
		resume = (MyResume) intent.getSerializableExtra("resume");
		
		educationlist=new ArrayList<Education>();
		educationlist=resume.getEducation();
		weList=new ArrayList<WorkExperience>();
		weList=resume.getExperience();
		

		resume_listview = (ListView) findViewById(R.id.resume_listview);
		resume_tv_work = (ListView) findViewById(R.id.resume_listview_work);
		back = (Button) findViewById(R.id.resume_back);
		back.setOnClickListener(this);

		resume_tv_resumetitle = (TextView) findViewById(R.id.resume_tv_resumetitle);
		resume_tv_time = (TextView) findViewById(R.id.resume_tv_time);
		resume_tv_browsecount = (TextView) findViewById(R.id.resume_tv_browsecount);
		resume_tv_userName = (TextView) findViewById(R.id.resume_tv_userName);
		resume_tv_sex = (TextView) findViewById(R.id.resume_tv_sex);
		resume_tv_age = (TextView) findViewById(R.id.resume_tv_age);
		resume_tv_marital = (TextView) findViewById(R.id.resume_tv_marital);
		resume_tv_education = (TextView) findViewById(R.id.resume_tv_education);
		resume_tv_graduationTime = (TextView) findViewById(R.id.resume_tv_graduationTime);
		resume_tv_jobExperience = (TextView) findViewById(R.id.resume_tv_jobExperience);
		resume_tv_address = (TextView) findViewById(R.id.resume_tv_address);
		resume_tv_other = (TextView) findViewById(R.id.resume_tv_other);
		//求职简历
		resume_tv_jobintention_intention = (TextView) findViewById(R.id.resume_tv_jobintention_intention);
		resume_tv_jobintention_category = (TextView) findViewById(R.id.resume_tv_jobintention_category);
		resume_tv_jobintention_nature = (TextView) findViewById(R.id.resume_tv_jobintention_nature);
		resume_tv_jobintention_address=(TextView) findViewById(R.id.resume_tv_jobintention_address);
		resume_tv_jobintention_requirement=(TextView) findViewById(R.id.resume_tv_jobintention_requirement);
		resume_tv_jobintention_salary=(TextView) findViewById(R.id.resume_tv_jobintention_salary);
		//语言能力
		resume_tv_Language=(TextView) findViewById(R.id.resume_tv_Language);
		//联系方式
		resume_tv_phone=(TextView) findViewById(R.id.resume_tv_phone);
	}

	private void setView() {
		resume_tv_resumetitle.setText(resume.getTitle());
		resume_tv_time.setText(resume.getRefreshtime());
		resume_tv_browsecount.setText(resume.getClick()+"");
		resume_tv_userName.setText(resume.getFullname());
		resume_tv_sex.setText(resume.getSex_cn());
		resume_tv_age.setText(resume.getAge()+"");
		resume_tv_marital.setText(resume.getMarriage_cn());
		resume_tv_education.setText(resume.getEducation_cn());
		resume_tv_graduationTime.setText(resume.getEndtime());
		resume_tv_jobExperience.setText(resume.getExperience_cn());
		resume_tv_address.setText(resume.getAddress());
		resume_tv_other.setText(resume.getTag1());
		
		resume_tv_jobintention_intention.setText(resume.getJobs_name());
		resume_tv_jobintention_category.setText(resume.getCategory_cn());
		resume_tv_jobintention_nature.setText(resume.getNature_cn());
		resume_tv_jobintention_address.setText(resume.getDistrict_cn());
		resume_tv_jobintention_requirement.setText(resume.getTag2());
		resume_tv_jobintention_salary.setText(resume.getWage_cn());
		
		resume_tv_Language.setText(resume.getSpecialty());
		resume_tv_phone.setText(resume.getTelephone());

	}

	@Override
	protected void onDestroy() {
		this.finish();
		super.onDestroy();
	}

}
