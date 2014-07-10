package com.talentnetwork.bean;

import java.io.Serializable;
/**
 * 职位item
 * @author Administrator
 *
 */
public class JobItem implements Serializable{
	
	private int id;
	
	private String jobName;
	
	private String job_company;
	
	private String time;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJob_company() {
		return job_company;
	}

	public void setJob_company(String job_company) {
		this.job_company = job_company;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	
	

}
