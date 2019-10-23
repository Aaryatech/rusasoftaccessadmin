package com.ats.rusaaccessweb.reportnew.model;

public class FacAgnstSanctnPost {
	
	private String uniqId;
	private int infoDetailId;
	private int yearId;
	private String instituteName;
	private String academicYear;
	private int noOfFulltimeFaculty;
	private int sanctionedPost;
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
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public int getNoOfFulltimeFaculty() {
		return noOfFulltimeFaculty;
	}
	public void setNoOfFulltimeFaculty(int noOfFulltimeFaculty) {
		this.noOfFulltimeFaculty = noOfFulltimeFaculty;
	}
	public int getSanctionedPost() {
		return sanctionedPost;
	}
	public void setSanctionedPost(int sanctionedPost) {
		this.sanctionedPost = sanctionedPost;
	}
	
	public int getInfoDetailId() {
		return infoDetailId;
	}
	public void setInfoDetailId(int infoDetailId) {
		this.infoDetailId = infoDetailId;
	}
	public int getYearId() {
		return yearId;
	}
	public void setYearId(int yearId) {
		this.yearId = yearId;
	}
	@Override
	public String toString() {
		return "FacAgnstSanctnPost [uniqId=" + uniqId + ", infoDetailId=" + infoDetailId + ", yearId=" + yearId
				+ ", instituteName=" + instituteName + ", academicYear=" + academicYear + ", noOfFulltimeFaculty="
				+ noOfFulltimeFaculty + ", sanctionedPost=" + sanctionedPost + "]";
	}
		
	
}
