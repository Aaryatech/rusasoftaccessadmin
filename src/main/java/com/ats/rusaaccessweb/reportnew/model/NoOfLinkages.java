package com.ats.rusaaccessweb.reportnew.model;

public class NoOfLinkages {

	private int facultyStudLinkageId;
	private String linkageTitle;
	private String partneringInstitute;
	private String estbYear;
	private String academicYear;
	private String instituteName;
	private String natureOfLinkage;
	public int getFacultyStudLinkageId() {
		return facultyStudLinkageId;
	}
	public void setFacultyStudLinkageId(int facultyStudLinkageId) {
		this.facultyStudLinkageId = facultyStudLinkageId;
	}
	
	public String getNatureOfLinkage() {
		return natureOfLinkage;
	}
	public void setNatureOfLinkage(String natureOfLinkage) {
		this.natureOfLinkage = natureOfLinkage;
	}
	public String getLinkageTitle() {
		return linkageTitle;
	}
	public void setLinkageTitle(String linkageTitle) {
		this.linkageTitle = linkageTitle;
	}
	public String getPartneringInstitute() {
		return partneringInstitute;
	}
	public void setPartneringInstitute(String partneringInstitute) {
		this.partneringInstitute = partneringInstitute;
	}
	public String getEstbYear() {
		return estbYear;
	}
	public void setEstbYear(String estbYear) {
		this.estbYear = estbYear;
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
	@Override
	public String toString() {
		return "NoOfLinkages [facultyStudLinkageId=" + facultyStudLinkageId + ", linkageTitle=" + linkageTitle
				+ ", partneringInstitute=" + partneringInstitute + ", estbYear=" + estbYear + ", academicYear="
				+ academicYear + ", instituteName=" + instituteName + "]";
	}
	
	
}
