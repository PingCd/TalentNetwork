package com.talentnetwork.bean;

import java.io.Serializable;

/**
 * 企业简介中全部职位
 * @author Administrator
 *
 */
public class CompanyIn_Jobs implements Serializable{
	
	private int jobId;
	
	private String jobName;

	public int getJobId() {
		return jobId;
	}

	public void setJobId(int jobId) {
		this.jobId = jobId;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	
	

}
