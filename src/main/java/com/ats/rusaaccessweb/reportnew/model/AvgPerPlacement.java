package com.ats.rusaaccessweb.reportnew.model;

public class AvgPerPlacement {

	private int placementId;
	private int studPerformId;
	private int noStudentPlaced;
	private String empyrName;
	private String empyrAdd;
	private String contactDetail;
	private String minPackage;
	private String maxPackage;
	private int noStudPass; 
	private String academicYear;
	private String instituteName;
	public int getPlacementId() {
		return placementId;
	}
	public void setPlacementId(int placementId) {
		this.placementId = placementId;
	}
	public int getStudPerformId() {
		return studPerformId;
	}
	public void setStudPerformId(int studPerformId) {
		this.studPerformId = studPerformId;
	}
	public int getNoStudentPlaced() {
		return noStudentPlaced;
	}
	public void setNoStudentPlaced(int noStudentPlaced) {
		this.noStudentPlaced = noStudentPlaced;
	}
	public String getEmpyrName() {
		return empyrName;
	}
	public void setEmpyrName(String empyrName) {
		this.empyrName = empyrName;
	}
	public String getEmpyrAdd() {
		return empyrAdd;
	}
	public void setEmpyrAdd(String empyrAdd) {
		this.empyrAdd = empyrAdd;
	}
	public String getContactDetail() {
		return contactDetail;
	}
	public void setContactDetail(String contactDetail) {
		this.contactDetail = contactDetail;
	}
	public String getMinPackage() {
		return minPackage;
	}
	public void setMinPackage(String minPackage) {
		this.minPackage = minPackage;
	}
	public String getMaxPackage() {
		return maxPackage;
	}
	public void setMaxPackage(String maxPackage) {
		this.maxPackage = maxPackage;
	}
	public int getNoStudPass() {
		return noStudPass;
	}
	public void setNoStudPass(int noStudPass) {
		this.noStudPass = noStudPass;
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
		return "AvgPerPlacement [placementId=" + placementId + ", studPerformId=" + studPerformId + ", noStudentPlaced="
				+ noStudentPlaced + ", empyrName=" + empyrName + ", empyrAdd=" + empyrAdd + ", contactDetail="
				+ contactDetail + ", minPackage=" + minPackage + ", maxPackage=" + maxPackage + ", noStudPass="
				+ noStudPass + ", academicYear=" + academicYear + ", instituteName=" + instituteName + "]";
	}
	
	
	
}
