package com.ats.rusaaccessweb.reportnew.model;

public class StudTeachrRatio {
	
	private int infoDetailId;
	private String uniqId;
	private int noOfFulltimeFaculty;
	private int noCurrentAdmitedStnt;
	private String academicYear;
	private String instituteName;
	
	
	public int getInfoDetailId() {
		return infoDetailId;
	}
	public void setInfoDetailId(int infoDetailId) {
		this.infoDetailId = infoDetailId;
	}
	
	public int getNoOfFulltimeFaculty() {
		return noOfFulltimeFaculty;
	}
	public void setNoOfFulltimeFaculty(int noOfFulltimeFaculty) {
		this.noOfFulltimeFaculty = noOfFulltimeFaculty;
	}
	public int getNoCurrentAdmitedStnt() {
		return noCurrentAdmitedStnt;
	}
	public void setNoCurrentAdmitedStnt(int noCurrentAdmitedStnt) {
		this.noCurrentAdmitedStnt = noCurrentAdmitedStnt;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public String getUniqId() {
		return uniqId;
	}
	public void setUniqId(String uniqId) {
		this.uniqId = uniqId;
	}
	public String getInstituteName() {
		return instituteName;
	}
	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}
	@Override
	public String toString() {
		return "StudTeachrRatio [infoDetailId=" + infoDetailId + ", uniqId=" + uniqId + ", noOfFulltimeFaculty="
				+ noOfFulltimeFaculty + ", noCurrentAdmitedStnt=" + noCurrentAdmitedStnt + ", academicYear="
				+ academicYear + ", instituteName=" + instituteName + "]";
	}
		
}
