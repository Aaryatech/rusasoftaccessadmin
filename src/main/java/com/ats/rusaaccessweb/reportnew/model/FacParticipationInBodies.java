package com.ats.rusaaccessweb.reportnew.model;


public class FacParticipationInBodies {

	private int conId;
	
	private String academicYear;
	
	private String facultyFirstName;
	private String conLevel;
	private String conName;
	private String conUniversity;
	
	private String conFrom;
	private String conTo;
	
	private String instituteName;
	private int instituteId;
	private int yearId;
	
	public int getConId() {
		return conId;
	}
	public void setConId(int conId) {
		this.conId = conId;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public String getFacultyFirstName() {
		return facultyFirstName;
	}
	public void setFacultyFirstName(String facultyFirstName) {
		this.facultyFirstName = facultyFirstName;
	}
	public String getConLevel() {
		return conLevel;
	}
	public void setConLevel(String conLevel) {
		this.conLevel = conLevel;
	}
	public String getConName() {
		return conName;
	}
	public void setConName(String conName) {
		this.conName = conName;
	}
	public String getConUniversity() {
		return conUniversity;
	}
	public void setConUniversity(String conUniversity) {
		this.conUniversity = conUniversity;
	}
	
	public String getConFrom() {
		return conFrom;
	}
	public void setConFrom(String conFrom) {
		this.conFrom = conFrom;
	}
	
	public String getConTo() {
		return conTo;
	}
	public void setConTo(String conTo) {
		this.conTo = conTo;
	}
	public String getInstituteName() {
		return instituteName;
	}
	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}
	public int getInstituteId() {
		return instituteId;
	}
	public void setInstituteId(int instituteId) {
		this.instituteId = instituteId;
	}
	public int getYearId() {
		return yearId;
	}
	public void setYearId(int yearId) {
		this.yearId = yearId;
	}

	@Override
	public String toString() {
		return "FacParticipationInBodies [conId=" + conId + ", academicYear=" + academicYear + ", facultyFirstName="
				+ facultyFirstName + ", conLevel=" + conLevel + ", conName=" + conName + ", conUniversity="
				+ conUniversity + ", conFrom=" + conFrom + ", conTo=" + conTo + ", instituteName=" + instituteName
				+ ", instituteId=" + instituteId + ", yearId=" + yearId + "]";
	}
	
}
