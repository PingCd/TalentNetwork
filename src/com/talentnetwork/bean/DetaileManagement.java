package com.talentnetwork.bean;

import java.io.Serializable;

public class DetaileManagement implements Serializable{
	private String companyname;//公司名称
	private String category_cn;//工作部门 
	private String jobs_name;//职位名称
	private String scale_cn;//招聘人数
	private String trade_cn;//职位类别
	private String nature_cn;//工作性质
	private String district_cn;//工作地点
	private String deadline;//截止日期
	private String contents;//职位描述
	
	private String wage_cn;//工资标准
	private String tag;//其它福利
	
	private String sex_cn;//性别要求
	private String education_cn;//学历要求
	private String experience_cn;//工作经验
	private String add_mode;//自动过滤
	
	
	
	
	public DetaileManagement(){
		
	}
	
	
	
	
	public DetaileManagement(String companyname, String category_cn, String jobs_name,
			String scale_cn, String trade_cn, String nature_cn,
			String district_cn, String deadline, String contents,
			String wage_cn, String tag, String sex_cn, String education_cn,
			String experience_cn, String add_mode) {
		super();
		this.companyname = companyname;
		this.category_cn = category_cn;
		this.jobs_name = jobs_name;
		this.scale_cn = scale_cn;
		this.trade_cn = trade_cn;
		this.nature_cn = nature_cn;
		this.district_cn = district_cn;
		this.deadline = deadline;
		this.contents = contents;
		this.wage_cn = wage_cn;
		this.tag = tag;
		this.sex_cn = sex_cn;
		this.education_cn = education_cn;
		this.experience_cn = experience_cn;
		this.add_mode = add_mode;
	}
	public String getCompanyname() {
		return companyname;
	}
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
	public String getCategory_cn() {
		return category_cn;
	}
	public void setCategory_cn(String category_cn) {
		this.category_cn = category_cn;
	}
	public String getJobs_name() {
		return jobs_name;
	}
	public void setJobs_name(String jobs_name) {
		this.jobs_name = jobs_name;
	}
	public String getScale_cn() {
		return scale_cn;
	}
	public void setScale_cn(String scale_cn) {
		this.scale_cn = scale_cn;
	}
	public String getTrade_cn() {
		return trade_cn;
	}
	public void setTrade_cn(String trade_cn) {
		this.trade_cn = trade_cn;
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
	public String getDeadline() {
		return deadline;
	}
	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getWage_cn() {
		return wage_cn;
	}
	public void setWage_cn(String wage_cn) {
		this.wage_cn = wage_cn;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getSex_cn() {
		return sex_cn;
	}
	public void setSex_cn(String sex_cn) {
		this.sex_cn = sex_cn;
	}
	public String getEducation_cn() {
		return education_cn;
	}
	public void setEducation_cn(String education_cn) {
		this.education_cn = education_cn;
	}
	public String getExperience_cn() {
		return experience_cn;
	}
	public void setExperience_cn(String experience_cn) {
		this.experience_cn = experience_cn;
	}




	public String getAdd_mode() {
		return add_mode;
	}




	public void setAdd_mode(String add_mode) {
		this.add_mode = add_mode;
	}
	
}
