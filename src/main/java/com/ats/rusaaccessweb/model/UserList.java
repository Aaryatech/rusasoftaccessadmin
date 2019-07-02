package com.ats.rusaaccessweb.model;
 

public class UserList {
	
 
    private int userId; 
	private int userType; 
	private String userName; 
	private String pass; 
	private int isBlock; 
	private int regPrimaryKey; 
	private int roleId; 
	private int exInt1;  
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getUserType() {
		return userType;
	}
	public void setUserType(int userType) {
		this.userType = userType;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public int getIsBlock() {
		return isBlock;
	}
	public void setIsBlock(int isBlock) {
		this.isBlock = isBlock;
	}
	public int getRegPrimaryKey() {
		return regPrimaryKey;
	}
	public void setRegPrimaryKey(int regPrimaryKey) {
		this.regPrimaryKey = regPrimaryKey;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public int getExInt1() {
		return exInt1;
	}
	public void setExInt1(int exInt1) {
		this.exInt1 = exInt1;
	}
	@Override
	public String toString() {
		return "UserList [userId=" + userId + ", userType=" + userType + ", userName=" + userName + ", pass=" + pass
				+ ", isBlock=" + isBlock + ", regPrimaryKey=" + regPrimaryKey + ", roleId=" + roleId + ", exInt1="
				+ exInt1 + "]";
	}
	 

}
