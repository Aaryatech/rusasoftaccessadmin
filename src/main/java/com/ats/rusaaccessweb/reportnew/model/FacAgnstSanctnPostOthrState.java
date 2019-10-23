package com.ats.rusaaccessweb.reportnew.model;

public class FacAgnstSanctnPostOthrState {

	private String uniqId;
	private String instituteName;	
	private int yearId;
	private String academicYear;
	private int noOfOtherStateFac;
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
	public int getYearId() {
		return yearId;
	}
	public void setYearId(int yearId) {
		this.yearId = yearId;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public int getNoOfOtherStateFac() {
		return noOfOtherStateFac;
	}
	public void setNoOfOtherStateFac(int noOfOtherStateFac) {
		this.noOfOtherStateFac = noOfOtherStateFac;
	}
	public int getSanctionedPost() {
		return sanctionedPost;
	}
	public void setSanctionedPost(int sanctionedPost) {
		this.sanctionedPost = sanctionedPost;
	}
	@Override
	public String toString() {
		return "FacAgnstSanctnPostOthrState [uniqId=" + uniqId + ", instituteName=" + instituteName + ", yearId="
				+ yearId + ", academicYear=" + academicYear + ", noOfOtherStateFac=" + noOfOtherStateFac
				+ ", sanctionedPost=" + sanctionedPost + "]";
	}
	
	
}
