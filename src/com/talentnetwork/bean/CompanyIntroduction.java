package com.talentnetwork.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * 企业简介bean
 * @author Administrator
 *
 */
public class CompanyIntroduction implements Serializable{
	
	public  List<CompanyIn_Jobs> jobList=new ArrayList<CompanyIn_Jobs>();
	
	private int companyId;
	
	private String companyName;
	
	private String industry;//行业
	
	private String scale;//规模
	
	private String home;//主页
	
	
	private String companyIn;//企业简介
	
	private String phone;//联系方式
	
	
	
	
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}




	public String getScale() {
		return scale;
	}




	public void setScale(String scale) {
		this.scale = scale;
	}




	public String getHome() {
		return home;
	}




	public void setHome(String home) {
		this.home = home;
	}


	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getCompanyIn() {
		return companyIn;
	}




	public void setCompanyIn(String companyIn) {
		this.companyIn = companyIn;
	}




	public String getPhone() {
		return phone;
	}




	public void setPhone(String phone) {
		this.phone = phone;
	}


	

}
