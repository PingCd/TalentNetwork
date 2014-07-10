package com.talentnetwork.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 我的简历bean
 * @author Administrator
 *
 */
public class MyResume implements Serializable{
	
	private String title;
	private String refreshtime;//最新时间
	private int  click;//点击次数
	

	private String   fullname;//姓名
	private String sex_cn;//性别
	private int age;//年龄
	private String marriage_cn;//婚姻
	private String education_cn;//学历
	private String endtime;//毕业时间
	private String experience_cn;//工作经验
	private String address;//当前地点、
	private String tag1;//其它能力
	
	
	private String jobs_name;//工作
	private String category_cn;//工作类别
	private String nature_cn;//工作性制
	private String district_cn;//工作地
	private String tag2;//行业要求
    private List<Education> education=new ArrayList<Education>();
	private String wage_cn;//薪水
	
	 private List<WorkExperience> experience=new ArrayList<WorkExperience>();//工作经历
	
	 private String specialty;//特点
	 
	 private String  telephone;//手机号
	 
	public MyResume(){
		
	}

	public MyResume(String title,String refreshtime, int click, String fullname, String sex_cn,
			int age, String marriage_cn, String education_cn, String endtime,
			String experience_cn, String address, String tag1,
			String jobs_name, String category_cn, String nature_cn,
			String district_cn, String tag2,List<Education> education,
			String wage_cn,List<WorkExperience> experience, String specialty,
			String telephone) {
		super();
		this.title=title;
		this.refreshtime = refreshtime;
		this.click = click;
		this.fullname = fullname;
		this.sex_cn = sex_cn;
		this.age = age;
		this.marriage_cn = marriage_cn;
		this.education_cn = education_cn;
		this.endtime = endtime;
		this.experience_cn = experience_cn;
		this.address = address;
		this.tag1 = tag1;
		this.jobs_name = jobs_name;
		this.category_cn = category_cn;
		this.nature_cn = nature_cn;
		this.district_cn = district_cn;
		this.tag2 = tag2;
		this.education = education;
		this.wage_cn = wage_cn;
		this.experience = experience;
		this.specialty = specialty;
		this.telephone = telephone;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}


	public String getRefreshtime() {
		return refreshtime;
	}

	public void setRefreshtime(String refreshtime) {
		this.refreshtime = refreshtime;
	}

	public int getClick() {
		return click;
	}


	public void setClick(int click) {
		this.click = click;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getSex_cn() {
		return sex_cn;
	}



	public void setSex_cn(String sex_cn) {
		this.sex_cn = sex_cn;
	}

	public int getAge() {
		return age;
	}


	public void setAge(int age) {
		this.age = age;
	}

	public String getMarriage_cn() {
		return marriage_cn;
	}

	public void setMarriage_cn(String marriage_cn) {
		this.marriage_cn = marriage_cn;
	}

	public String getEducation_cn() {
		return education_cn;
	}


	public void setEducation_cn(String education_cn) {
		this.education_cn = education_cn;
	}


	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getExperience_cn() {
		return experience_cn;
	}

	public void setExperience_cn(String experience_cn) {
		this.experience_cn = experience_cn;
	}
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTag1() {
		return tag1;
	}

	public void setTag1(String tag1) {
		this.tag1 = tag1;
	}

	public String getJobs_name() {
		return jobs_name;
	}

	public void setJobs_name(String jobs_name) {
		this.jobs_name = jobs_name;
	}


	public String getCategory_cn() {
		return category_cn;
	}


	public void setCategory_cn(String category_cn) {
		this.category_cn = category_cn;
	}

	public String getNature_cn() {
		return nature_cn;
	}


	public void setNature_cn(String nature_cn) {
		this.nature_cn = nature_cn;
	}


	public String getDistrict_cn() {
		return district_cn;
	}



	public void setDistrict_cn(String district_cn) {
		this.district_cn = district_cn;
	}



	public String getTag2() {
		return tag2;
	}


	public void setTag2(String tag2) {
		this.tag2 = tag2;
	}

	
	public String getWage_cn() {
		return wage_cn;
	}

	public void setWage_cn(String wage_cn) {
		this.wage_cn = wage_cn;
	}

	public List<Education> getEducation() {
		return education;
	}

	public void setEducation(List<Education> education) {
		this.education = education;
	}

	public List<WorkExperience> getExperience() {
		return experience;
	}

	public void setExperience(List<WorkExperience> experience) {
		this.experience = experience;
	}
	
}
