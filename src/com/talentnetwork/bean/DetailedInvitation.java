package com.talentnetwork.bean;

import java.io.Serializable;

public class DetailedInvitation implements Serializable {

	private String jobs_name="";//工作名称
	private String	companyname="";//公司名称
	private String interview_addtime="";//面试时间
	private String address="";//地址
	private String telephone="";//电话

	private String notes="";//备注
	
	
	public DetailedInvitation(){
		
	}

	public DetailedInvitation(String jobs_name, String companyname,
			String interview_addtime, String address, String telephone,
			String notes) {
		super();
		this.jobs_name = jobs_name;
		this.companyname = companyname;
		this.interview_addtime = interview_addtime;
		this.address = address;
		this.telephone = telephone;
		this.notes = notes;
	}



	public String getJobs_name() {
		return jobs_name;
	}



	public void setJobs_name(String jobs_name) {
		this.jobs_name = jobs_name;
	}



	public String getCompanyname() {
		return companyname;
	}



	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}



	public String getInterview_addtime() {
		return interview_addtime;
	}



	public void setInterview_addtime(String interview_addtime) {
		this.interview_addtime = interview_addtime;
	}



	public String getAddress() {
		return address;
	}



	public void setAddress(String address) {
		this.address = address;
	}



	public String getTelephone() {
		return telephone;
	}



	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}



	public String getNotes() {
		return notes;
	}



	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	
	
	
	
}
