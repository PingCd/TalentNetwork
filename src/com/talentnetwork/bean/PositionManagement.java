package com.talentnetwork.bean;

import java.io.Serializable;
/**
 * 教育经历item
 * @author Administrator
 *
 */
public class PositionManagement implements Serializable{
	private int id;//职位管理id
	
	private String positionName;//职位名称
	
	private String positionRelease;//发布时间
	
	private String positionBy;//截止时间
	
	private String state;//状态

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public String getPositionRelease() {
		return positionRelease;
	}

	public void setPositionRelease(String positionRelease) {
		this.positionRelease = positionRelease;
	}

	public String getPositionBy() {
		return positionBy;
	}

	public void setPositionBy(String positionBy) {
		this.positionBy = positionBy;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	
	

}
