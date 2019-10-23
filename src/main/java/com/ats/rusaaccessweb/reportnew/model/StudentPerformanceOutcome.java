package com.ats.rusaaccessweb.reportnew.model;


public class StudentPerformanceOutcome {
	
	private int coId;
	
	private int subId;
	
	private String subCode;
	
	private String subName;
	
	private String coName;
	
	private String programName;
	
	private String nameOfProgram;
	
	private String instituteName;

	public int getCoId() {
		return coId;
	}

	public void setCoId(int coId) {
		this.coId = coId;
	}

	public int getSubId() {
		return subId;
	}

	public void setSubId(int subId) {
		this.subId = subId;
	}

	public String getSubCode() {
		return subCode;
	}

	public void setSubCode(String subCode) {
		this.subCode = subCode;
	}

	public String getSubName() {
		return subName;
	}

	public void setSubName(String subName) {
		this.subName = subName;
	}

	public String getCoName() {
		return coName;
	}

	public void setCoName(String coName) {
		this.coName = coName;
	}

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public String getNameOfProgram() {
		return nameOfProgram;
	}

	public void setNameOfProgram(String nameOfProgram) {
		this.nameOfProgram = nameOfProgram;
	}

	public String getInstituteName() {
		return instituteName;
	}

	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}

	@Override
	public String toString() {
		return "StudentPerformanceOutcome [coId=" + coId + ", subId=" + subId + ", subCode=" + subCode + ", subName="
				+ subName + ", coName=" + coName + ", programName=" + programName + ", nameOfProgram=" + nameOfProgram
				+ ", instituteName=" + instituteName + ", getCoId()=" + getCoId() + ", getSubId()=" + getSubId()
				+ ", getSubCode()=" + getSubCode() + ", getSubName()=" + getSubName() + ", getCoName()=" + getCoName()
				+ ", getProgramName()=" + getProgramName() + ", getNameOfProgram()=" + getNameOfProgram()
				+ ", getInstituteName()=" + getInstituteName() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}
	

	
	
	
}
