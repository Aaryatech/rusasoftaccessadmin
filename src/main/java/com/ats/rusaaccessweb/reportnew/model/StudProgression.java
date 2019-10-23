package com.ats.rusaaccessweb.reportnew.model;

public class StudProgression {

	private int yearId;
	private int noStudent;
	private int noStudPass;
	private String instituteName;
	private String academicYear;
	public int getYearId() {
		return yearId;
	}
	public void setYearId(int yearId) {
		this.yearId = yearId;
	}
	public int getNoStudent() {
		return noStudent;
	}
	public void setNoStudent(int noStudent) {
		this.noStudent = noStudent;
	}
	public int getNoStudPass() {
		return noStudPass;
	}
	public void setNoStudPass(int noStudPass) {
		this.noStudPass = noStudPass;
	}
	public String getInstituteName() {
		return instituteName;
	}
	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	@Override
	public String toString() {
		return "StudProgression [yearId=" + yearId + ", noStudent=" + noStudent + ", noStudPass=" + noStudPass
				+ ", instituteName=" + instituteName + ", academicYear=" + academicYear + "]";
	}
	
	
}
