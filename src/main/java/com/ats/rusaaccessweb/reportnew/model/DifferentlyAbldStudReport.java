package com.ats.rusaaccessweb.reportnew.model;

public class DifferentlyAbldStudReport {

	private String uniqId; 
	private String academicYear;
	private String instituteName;
	private int noOfPwdStud;
	private int totalStudEnrolled;
	public String getUniqId() {
		return uniqId;
	}
	public void setUniqId(String uniqId) {
		this.uniqId = uniqId;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public String getInstituteName() {
		return instituteName;
	}
	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}
	public int getNoOfPwdStud() {
		return noOfPwdStud;
	}
	public void setNoOfPwdStud(int noOfPwdStud) {
		this.noOfPwdStud = noOfPwdStud;
	}
	public int getTotalStudEnrolled() {
		return totalStudEnrolled;
	}
	public void setTotalStudEnrolled(int totalStudEnrolled) {
		this.totalStudEnrolled = totalStudEnrolled;
	}
	@Override
	public String toString() {
		return "DifferentlyAbldStudReport [uniqId=" + uniqId + ", academicYear=" + academicYear + ", instituteName="
				+ instituteName + ", noOfPwdStud=" + noOfPwdStud + ", totalStudEnrolled=" + totalStudEnrolled + "]";
	}
	
	
	
}
