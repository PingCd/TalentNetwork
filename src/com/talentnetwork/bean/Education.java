package com.talentnetwork.bean;

import java.io.Serializable;
/**
 * 教育经历item
 * @author Administrator
 *
 */
public class Education implements  Serializable{
	
	private int id;
	
	private String educationTime;//时间范围
	
	private String education;//学历
	
	private String school;//毕业学校
	
	private String professional;//所学专业

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEducationTime() {
		return educationTime;
	}

	public void setEducationTime(String educationTime) {
		this.educationTime = educationTime;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getProfessional() {
		return professional;
	}

	public void setProfessional(String professional) {
		this.professional = professional;
	}
	
	
	

}
