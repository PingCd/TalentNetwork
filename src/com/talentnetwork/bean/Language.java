package com.talentnetwork.bean;

import java.io.Serializable;

public class Language implements Serializable{
	
	private int id;
	
	private String languageName;
	
	private String proficiency;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLanguageName() {
		return languageName;
	}

	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}

	public String getProficiency() {
		return proficiency;
	}

	public void setProficiency(String proficiency) {
		this.proficiency = proficiency;
	}
	

}
