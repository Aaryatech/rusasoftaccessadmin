package com.ats.rusaaccessweb.model.dashb;

public class FacultyResearchDetail {
	
	private String id;
	private int instituteId;
	private String instituteName;
	private String academicYear;
	private String facultyFirstName;
	private String projName;
	private int projTotalAmt;
	private String projSponsor;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getInstituteId() {
		return instituteId;
	}
	public void setInstituteId(int instituteId) {
		this.instituteId = instituteId;
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
	public String getFacultyFirstName() {
		return facultyFirstName;
	}
	public void setFacultyFirstName(String facultyFirstName) {
		this.facultyFirstName = facultyFirstName;
	}
	public String getProjName() {
		return projName;
	}
	public void setProjName(String projName) {
		this.projName = projName;
	}
	public int getProjTotalAmt() {
		return projTotalAmt;
	}
	public void setProjTotalAmt(int projTotalAmt) {
		this.projTotalAmt = projTotalAmt;
	}
	public String getProjSponsor() {
		return projSponsor;
	}
	public void setProjSponsor(String projSponsor) {
		this.projSponsor = projSponsor;
	}
	@Override
	public String toString() {
		return "FacultyResearchDetail [id=" + id + ", instituteId=" + instituteId + ", instituteName=" + instituteName
				+ ", academicYear=" + academicYear + ", facultyFirstName=" + facultyFirstName + ", projName=" + projName
				+ ", projTotalAmt=" + projTotalAmt + ", projSponsor=" + projSponsor + "]";
	}
	
	

}
