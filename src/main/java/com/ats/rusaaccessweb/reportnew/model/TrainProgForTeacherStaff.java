package com.ats.rusaaccessweb.reportnew.model;
 
public class TrainProgForTeacherStaff {
	
 	private int yearId;
	
	private String  academicYear;
	
	private String  instituteName;
	
	private float profDevCount;
	
	private float admDevCount;

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

	public String getInstituteName() {
		return instituteName;
	}

	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}

	public float getProfDevCount() {
		return profDevCount;
	}

	public void setProfDevCount(float profDevCount) {
		this.profDevCount = profDevCount;
	}

	public float getAdmDevCount() {
		return admDevCount;
	}

	public void setAdmDevCount(float admDevCount) {
		this.admDevCount = admDevCount;
	}

	@Override
	public String toString() {
		return "TrainProgForTeacherStaff [yearId=" + yearId + ", academicYear=" + academicYear + ", instituteName="
				+ instituteName + ", profDevCount=" + profDevCount + ", admDevCount=" + admDevCount + ", getYearId()="
				+ getYearId() + ", getAcademicYear()=" + getAcademicYear() + ", getInstituteName()="
				+ getInstituteName() + ", getProfDevCount()=" + getProfDevCount() + ", getAdmDevCount()="
				+ getAdmDevCount() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	
	


}
