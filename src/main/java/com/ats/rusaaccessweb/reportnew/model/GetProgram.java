package com.ats.rusaaccessweb.reportnew.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


public class GetProgram {
	
	
	private int programId;
	
	private int instituteId;
	
	private int yearId;
	
	private String nameOfProgram;
	
	private int programType;
	
	private int monthDuration;
	
	private int sanctionalIntake;
	
	private Date dateOfIntroduction;
	
	private String approvedBy;
	 
	private int isActive;
	
	private int delStatus;
	
	private Date makerdatetime;
	
	private int makerUserId;
	
	private int exInt1;
	
	private int exInt2;
	
	private String exVar1;
	
	private String exVar2;
	
	private String programName;

	public int getProgramId() {
		return programId;
	}

	public void setProgramId(int programId) {
		this.programId = programId;
	}

	public int getInstituteId() {
		return instituteId;
	}

	public void setInstituteId(int instituteId) {
		this.instituteId = instituteId;
	}

	public int getYearId() {
		return yearId;
	}

	public void setYearId(int yearId) {
		this.yearId = yearId;
	}

	public String getNameOfProgram() {
		return nameOfProgram;
	}

	public void setNameOfProgram(String nameOfProgram) {
		this.nameOfProgram = nameOfProgram;
	}

	public int getProgramType() {
		return programType;
	}

	public void setProgramType(int programType) {
		this.programType = programType;
	}

	public int getMonthDuration() {
		return monthDuration;
	}

	public void setMonthDuration(int monthDuration) {
		this.monthDuration = monthDuration;
	}

	public int getSanctionalIntake() {
		return sanctionalIntake;
	}

	public void setSanctionalIntake(int sanctionalIntake) {
		this.sanctionalIntake = sanctionalIntake;
	}
	@JsonFormat(locale = "ENGLISH", timezone = "Asia/Kolkata", pattern = "dd-MM-yyyy")
	public Date getDateOfIntroduction() {
		return dateOfIntroduction;
	}

	public void setDateOfIntroduction(Date dateOfIntroduction) {
		this.dateOfIntroduction = dateOfIntroduction;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public int getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}
	@JsonFormat(locale = "ENGLISH", timezone = "Asia/Kolkata", pattern = "dd-MM-yyyy")
	public Date getMakerdatetime() {
		return makerdatetime;
	}

	public void setMakerdatetime(Date makerdatetime) {
		this.makerdatetime = makerdatetime;
	}

	public int getMakerUserId() {
		return makerUserId;
	}

	public void setMakerUserId(int makerUserId) {
		this.makerUserId = makerUserId;
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

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	@Override
	public String toString() {
		return "GetProgram [programId=" + programId + ", instituteId=" + instituteId + ", yearId=" + yearId
				+ ", nameOfProgram=" + nameOfProgram + ", programType=" + programType + ", monthDuration="
				+ monthDuration + ", sanctionalIntake=" + sanctionalIntake + ", dateOfIntroduction="
				+ dateOfIntroduction + ", approvedBy=" + approvedBy + ", isActive=" + isActive + ", delStatus="
				+ delStatus + ", makerdatetime=" + makerdatetime + ", makerUserId=" + makerUserId + ", exInt1=" + exInt1
				+ ", exInt2=" + exInt2 + ", exVar1=" + exVar1 + ", exVar2=" + exVar2 + ", programName=" + programName
				+ "]";
	}
	
	

}
