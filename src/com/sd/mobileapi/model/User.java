package com.sd.mobileapi.model;

import java.util.Date;

public class User {
	private int id;
	private String mobile;
	private String username;
	private String pwd;
	private String role;
	private String img;
	private String token;
	private Date createtime;
	private String accid;
	private String usernumber;
	private String patientcode;
	private String outtime;
	private String bedcode;
	private String duty;
	private String staytime;
	
	
	
	
	public String getStaytime() {
		return staytime;
	}
	public void setStaytime(String staytime) {
		this.staytime = staytime;
	}
	public String getDuty() {
		return duty;
	}
	public void setDuty(String duty) {
		this.duty = duty;
	}
	public String getUsernumber() {
		return usernumber;
	}
	public void setUsernumber(String usernumber) {
		this.usernumber = usernumber;
	}
	public String getPatientcode() {
		return patientcode;
	}
	public void setPatientcode(String patientcode) {
		this.patientcode = patientcode;
	}
	public String getOuttime() {
		return outtime;
	}
	public void setOuttime(String outtime) {
		this.outtime = outtime;
	}
	public String getBedcode() {
		return bedcode;
	}
	public void setBedcode(String bedcode) {
		this.bedcode = bedcode;
	}
	public String getAccid() {
		return accid;
	}
	public void setAccid(String accid) {
		this.accid = accid;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
	
	
	
}
