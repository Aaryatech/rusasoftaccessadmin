package com.ats.rusaaccessweb.reportnew.model;

public class NoOfPrograms {
	
	private int programId;
	
	private int instituteId;
	
	private String nameOfProgram;
	
	private int monthDuration;
	
	private String approvedBy;
	private String yearOfIntrod;

	private String levelOfProgram;
	
	private String instituteName;

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

	public String getNameOfProgram() {
		return nameOfProgram;
	}

	public void setNameOfProgram(String nameOfProgram) {
		this.nameOfProgram = nameOfProgram;
	}

	public int getMonthDuration() {
		return monthDuration;
	}

	public void setMonthDuration(int monthDuration) {
		this.monthDuration = monthDuration;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public String getYearOfIntrod() {
		return yearOfIntrod;
	}

	public void setYearOfIntrod(String yearOfIntrod) {
		this.yearOfIntrod = yearOfIntrod;
	}

	public String getLevelOfProgram() {
		return levelOfProgram;
	}

	public void setLevelOfProgram(String levelOfProgram) {
		this.levelOfProgram = levelOfProgram;
	}

	public String getInstituteName() {
		return instituteName;
	}

	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}

	@Override
	public String toString() {
		return "NoOfPrograms [programId=" + programId + ", instituteId=" + instituteId + ", nameOfProgram="
				+ nameOfProgram + ", monthDuration=" + monthDuration + ", approvedBy=" + approvedBy + ", yearOfIntrod="
				+ yearOfIntrod + ", levelOfProgram=" + levelOfProgram + ", instituteName=" + instituteName + "]";
	}
	
	
}
