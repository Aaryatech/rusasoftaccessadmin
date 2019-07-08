package com.ats.rusaaccessweb.model;

public class UserLogin {
	
	private int userId; 
	private int userType; 
	private String userName;
	private String pass;
	private int isBlock;
	private int regPrimaryKey;
	private int roleId; 
	private int exInt1;
	private int exInt2;

	private String exVar1;
	private String exVar2;
	private String joiningDate;
	
	private String firstName;
	private String middleName;
	private String lastName;
	private String designation;
	private String email;
	
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
	public int getExInt2() {
		return exInt2;
	}
	public void setExInt2(int exInt2) {
		this.exInt2 = exInt2;
	}
	public String getExVar1() {
		return exVar1;
	}
	public void setExVar1(String exVar1) {
		this.exVar1 = exVar1;
	}
	public String getExVar2() {
		return exVar2;
	}
	public void setExVar2(String exVar2) {
		this.exVar2 = exVar2;
	}
	public String getJoiningDate() {
		return joiningDate;
	}
	public void setJoiningDate(String joiningDate) {
		this.joiningDate = joiningDate;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "UserLogin [userId=" + userId + ", userType=" + userType + ", userName=" + userName + ", pass=" + pass
				+ ", isBlock=" + isBlock + ", regPrimaryKey=" + regPrimaryKey + ", roleId=" + roleId + ", exInt1="
				+ exInt1 + ", exInt2=" + exInt2 + ", exVar1=" + exVar1 + ", exVar2=" + exVar2 + ", joiningDate="
				+ joiningDate + ", firstName=" + firstName + ", middleName=" + middleName + ", lastName=" + lastName
				+ ", designation=" + designation + ", email=" + email + "]";
	}
	
	

}
