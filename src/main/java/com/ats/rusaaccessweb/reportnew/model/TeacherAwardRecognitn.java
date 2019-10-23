package com.ats.rusaaccessweb.reportnew.model;

public class TeacherAwardRecognitn {

	private int awardId;
	private String facultyFirstName;
	private String contactNo;
	private String fAddress;
	private String awardName;
	private String awardAuthority;
	private String awardDate;
	private String incentive;
	private String academicYear;
	private String instituteName;
	public int getAwardId() {
		return awardId;
	}
	public void setAwardId(int awardId) {
		this.awardId = awardId;
	}
	public String getFacultyFirstName() {
		return facultyFirstName;
	}
	public void setFacultyFirstName(String facultyFirstName) {
		this.facultyFirstName = facultyFirstName;
	}
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	public String getfAddress() {
		return fAddress;
	}
	public void setfAddress(String fAddress) {
		this.fAddress = fAddress;
	}
	public String getAwardName() {
		return awardName;
	}
	public void setAwardName(String awardName) {
		this.awardName = awardName;
	}
	public String getAwardAuthority() {
		return awardAuthority;
	}
	public void setAwardAuthority(String awardAuthority) {
		this.awardAuthority = awardAuthority;
	}
	public String getAwardDate() {
		return awardDate;
	}
	public void setAwardDate(String awardDate) {
		this.awardDate = awardDate;
	}
	public String getIncentive() {
		return incentive;
	}
	public void setIncentive(String incentive) {
		this.incentive = incentive;
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
		return "TeacherAwardRecognitn [awardId=" + awardId + ", facultyFirstName=" + facultyFirstName + ", contactNo="
				+ contactNo + ", fAddress=" + fAddress + ", awardName=" + awardName + ", awardAuthority="
				+ awardAuthority + ", awardDate=" + awardDate + ", incentive=" + incentive + ", academicYear="
				+ academicYear + ", instituteName=" + instituteName + "]";
	}
	
	
}
