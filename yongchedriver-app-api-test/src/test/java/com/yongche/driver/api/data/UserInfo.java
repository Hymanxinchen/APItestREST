package com.yongche.driver.api.data;

public class UserInfo {

	public String phoneNumber;
	public String userName;
	public String password;
	
	public UserInfo(){
	}
	public UserInfo(String phoneNumber,String userName){
		this.phoneNumber = phoneNumber;
		this.userName = userName;
	}
	
	public UserInfo(String phoneNumber,String userName,String password){
		this.phoneNumber = phoneNumber;
		this.userName = userName;
		this.password = password;
	}
	
	public UserInfo orderUser(){
		UserInfo orderUserInfo = new UserInfo("16800009988", "kk","111111");
		return orderUserInfo;
	}
}
