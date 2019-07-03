package com.ats.rusaaccessweb.model;

import java.util.Date;


import com.fasterxml.jackson.annotation.JsonFormat;

public class Staff {

	
	
	private int facultyId;
	private int instituteId;
	private String deptId;
	private String facultyFirstName;
	private String facultyMiddelName;
	private String facultyLastName;
	private int highestQualification;
	private int teachingTo;
	private String hightestQualificationYear;
	private int currentDesignationId;
	private String joiningDate;
	private int isWorking;
	private String realivingDate;
	private String contactNo;
	private String email;
	private int delStatus;
	private int isActive;
	private int makerUserId;
	private String makerEnterDatetime;
	private int editUserId;
	private String lastUpdatedDatetime;
	private int checkerUserId;
	private String checkerDatetime;
	private int extraint1;
	private String extravarchar1;

	// new field added Sachin 9/Apr/2019
	private String roleIds;
	private String password;
	private int type;

	// new field added Sachin 10/Apr/2019

	private int isPrincipal;
	private int isIqac;
	private int isHod;
	private int isFaculty;
	private int isAccOff;
	private int isDean;
	private int isLibrarian;
	private int isStudent;
	private int isEnrolled;////default 0 once login then 1
	private int isBlocked; //default 0 if blocked then 1
	private int isTpo;
	private int isExtActOff;
	
	
	private int isSame;

	public int getIsBlocked() {
		return isBlocked;
	}

	public void setIsBlocked(int isBlocked) {
		this.isBlocked = isBlocked;
	}

	public int getIsEnrolled() {
		return isEnrolled;
	}

	public void setIsEnrolled(int isEnrolled) {
		this.isEnrolled = isEnrolled;
	}

	public int getIsPrincipal() {
		return isPrincipal;
	}

	public void setIsPrincipal(int isPrincipal) {
		this.isPrincipal = isPrincipal;
	}

	public int getIsIqac() {
		return isIqac;
	}

	public void setIsIqac(int isIqac) {
		this.isIqac = isIqac;
	}

	public int getIsHod() {
		return isHod;
	}

	public void setIsHod(int isHod) {
		this.isHod = isHod;
	}

	public int getIsFaculty() {
		return isFaculty;
	}

	public void setIsFaculty(int isFaculty) {
		this.isFaculty = isFaculty;
	}

	public int getIsAccOff() {
		return isAccOff;
	}

	public void setIsAccOff(int isAccOff) {
		this.isAccOff = isAccOff;
	}

	public int getIsDean() {
		return isDean;
	}

	public void setIsDean(int isDean) {
		this.isDean = isDean;
	}

	public int getIsLibrarian() {
		return isLibrarian;
	}

	public void setIsLibrarian(int isLibrarian) {
		this.isLibrarian = isLibrarian;
	}

	public int getIsStudent() {
		return isStudent;
	}

	public void setIsStudent(int isStudent) {
		this.isStudent = isStudent;
	}

	public String getDeptId() {
		return deptId;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public int getFacultyId() {
		return facultyId;
	}

	public void setFacultyId(int facultyId) {
		this.facultyId = facultyId;
	}

	public int getInstituteId() {
		return instituteId;
	}

	public void setInstituteId(int instituteId) {
		this.instituteId = instituteId;
	}

	public String getFacultyFirstName() {
		return facultyFirstName;
	}

	public void setFacultyFirstName(String facultyFirstName) {
		this.facultyFirstName = facultyFirstName;
	}

	public String getFacultyMiddelName() {
		return facultyMiddelName;
	}

	public void setFacultyMiddelName(String facultyMiddelName) {
		this.facultyMiddelName = facultyMiddelName;
	}

	public String getFacultyLastName() {
		return facultyLastName;
	}

	public void setFacultyLastName(String facultyLastName) {
		this.facultyLastName = facultyLastName;
	}

	public int getHighestQualification() {
		return highestQualification;
	}

	public void setHighestQualification(int highestQualification) {
		this.highestQualification = highestQualification;
	}

	public int getTeachingTo() {
		return teachingTo;
	}

	public void setTeachingTo(int teachingTo) {
		this.teachingTo = teachingTo;
	}

	public String getHightestQualificationYear() {
		return hightestQualificationYear;
	}

	public void setHightestQualificationYear(String hightestQualificationYear) {
		this.hightestQualificationYear = hightestQualificationYear;
	}

	public int getCurrentDesignationId() {
		return currentDesignationId;
	}

	public void setCurrentDesignationId(int currentDesignationId) {
		this.currentDesignationId = currentDesignationId;
	}

	
	public String getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(String joiningDate) {
		this.joiningDate = joiningDate;
	}

	public int getIsWorking() {
		return isWorking;
	}

	public void setIsWorking(int isWorking) {
		this.isWorking = isWorking;
	}

	
	public String getRealivingDate() {
		return realivingDate;
	}

	public void setRealivingDate(String realivingDate) {
		this.realivingDate = realivingDate;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public int getMakerUserId() {
		return makerUserId;
	}

	public void setMakerUserId(int makerUserId) {
		this.makerUserId = makerUserId;
	}

	public String getMakerEnterDatetime() {
		return makerEnterDatetime;
	}

	public void setMakerEnterDatetime(String makerEnterDatetime) {
		this.makerEnterDatetime = makerEnterDatetime;
	}

	public int getEditUserId() {
		return editUserId;
	}

	public void setEditUserId(int editUserId) {
		this.editUserId = editUserId;
	}

	public String getLastUpdatedDatetime() {
		return lastUpdatedDatetime;
	}

	public void setLastUpdatedDatetime(String lastUpdatedDatetime) {
		this.lastUpdatedDatetime = lastUpdatedDatetime;
	}

	public int getCheckerUserId() {
		return checkerUserId;
	}

	public void setCheckerUserId(int checkerUserId) {
		this.checkerUserId = checkerUserId;
	}

	public String getCheckerDatetime() {
		return checkerDatetime;
	}

	public void setCheckerDatetime(String checkerDatetime) {
		this.checkerDatetime = checkerDatetime;
	}

	public int getExtraint1() {
		return extraint1;
	}

	public void setExtraint1(int extraint1) {
		this.extraint1 = extraint1;
	}

	public String getExtravarchar1() {
		return extravarchar1;
	}

	public void setExtravarchar1(String extravarchar1) {
		this.extravarchar1 = extravarchar1;
	}
	

	public int getIsTpo() {
		return isTpo;
	}

	public void setIsTpo(int isTpo) {
		this.isTpo = isTpo;
	}

	public int getIsExtActOff() {
		return isExtActOff;
	}

	public void setIsExtActOff(int isExtActOff) {
		this.isExtActOff = isExtActOff;
	}
	
	public int getIsSame() {
		return isSame;
	}

	public void setIsSame(int isSame) {
		this.isSame = isSame;
	}

	@Override
	public String toString() {
		return "Staff [facultyId=" + facultyId + ", instituteId=" + instituteId + ", deptId=" + deptId
				+ ", facultyFirstName=" + facultyFirstName + ", facultyMiddelName=" + facultyMiddelName
				+ ", facultyLastName=" + facultyLastName + ", highestQualification=" + highestQualification
				+ ", teachingTo=" + teachingTo + ", hightestQualificationYear=" + hightestQualificationYear
				+ ", currentDesignationId=" + currentDesignationId + ", joiningDate=" + joiningDate + ", isWorking="
				+ isWorking + ", realivingDate=" + realivingDate + ", contactNo=" + contactNo + ", email=" + email
				+ ", delStatus=" + delStatus + ", isActive=" + isActive + ", makerUserId=" + makerUserId
				+ ", makerEnterDatetime=" + makerEnterDatetime + ", editUserId=" + editUserId + ", lastUpdatedDatetime="
				+ lastUpdatedDatetime + ", checkerUserId=" + checkerUserId + ", checkerDatetime=" + checkerDatetime
				+ ", extraint1=" + extraint1 + ", extravarchar1=" + extravarchar1 + ", roleIds=" + roleIds
				+ ", password=" + password + ", type=" + type + ", isPrincipal=" + isPrincipal + ", isIqac=" + isIqac
				+ ", isHod=" + isHod + ", isFaculty=" + isFaculty + ", isAccOff=" + isAccOff + ", isDean=" + isDean
				+ ", isLibrarian=" + isLibrarian + ", isStudent=" + isStudent + ", isEnrolled=" + isEnrolled
				+ ", isBlocked=" + isBlocked + ", isTpo=" + isTpo + ", isExtActOff=" + isExtActOff + ", isSame="
				+ isSame + "]";
	}

	
	
}
