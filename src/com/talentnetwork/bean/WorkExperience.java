package com.talentnetwork.bean;

import java.io.Serializable;
/**
 * 工作经验item
 * @author Administrator
 *
 */
public class WorkExperience implements Serializable{
	
	private int id;
	
	private String time;//时间
	
	private String company;//公司名
	
	private String inposition;//从事职位
	
	private String description;//工作描述

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getInposition() {
		return inposition;
	}

	public void setInposition(String inposition) {
		this.inposition = inposition;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	

}
