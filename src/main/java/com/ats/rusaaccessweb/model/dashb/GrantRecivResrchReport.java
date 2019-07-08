package com.ats.rusaaccessweb.model.dashb;

public class GrantRecivResrchReport {
	
	private String id;
	private int instituteId;
	private String instituteName;
	private int grantAmt;
	private String academicYear;
	
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
	public int getGrantAmt() {
		return grantAmt;
	}
	public void setGrantAmt(int grantAmt) {
		this.grantAmt = grantAmt;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	@Override
	public String toString() {
		return "GrantRecivResrchReport [id=" + id + ", instituteId=" + instituteId + ", instituteName=" + instituteName
				+ ", grantAmt=" + grantAmt + ", academicYear=" + academicYear + "]";
	}	
	
}
