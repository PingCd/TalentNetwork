package com.talentnetwork.bean;

import java.io.Serializable;
/**
 * 用户表
 * @author Administrator
 *
 */
public class User implements Serializable{
	
	private int uid;//用户id
	
	private String userName;//用户名
	
	private int type;//用户类型
	
	private String param1;//用户参数1（面试邀请/应聘者简历）
	
	private String param2;
	
	private String param3;

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getParam1() {
		return param1;
	}

	public void setParam1(String param1) {
		this.param1 = param1;
	}

	public String getParam2() {
		return param2;
	}

	public void setParam2(String param2) {
		this.param2 = param2;
	}

	public String getParam3() {
		return param3;
	}

	public void setParam3(String param3) {
		this.param3 = param3;
	}
	
	
	

}
