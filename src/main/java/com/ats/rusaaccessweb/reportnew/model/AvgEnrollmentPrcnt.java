package com.ats.rusaaccessweb.reportnew.model;

 
public class AvgEnrollmentPrcnt {
 
	 
	private int yearId;
	
	private String academicYear;
	
	private String instituteName;
	
	private float totalSanctIntake;
	
	private float  noCurrentAdmitedStnt;

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

	public float getTotalSanctIntake() {
		return totalSanctIntake;
	}

	public void setTotalSanctIntake(float totalSanctIntake) {
		this.totalSanctIntake = totalSanctIntake;
	}

	public float getNoCurrentAdmitedStnt() {
		return noCurrentAdmitedStnt;
	}

	public void setNoCurrentAdmitedStnt(float noCurrentAdmitedStnt) {
		this.noCurrentAdmitedStnt = noCurrentAdmitedStnt;
	}

	@Override
	public String toString() {
		return "AvgEnrollmentPrcnt [yearId=" + yearId + ", academicYear=" + academicYear + ", instituteName="
				+ instituteName + ", totalSanctIntake=" + totalSanctIntake + ", noCurrentAdmitedStnt="
				+ noCurrentAdmitedStnt + ", getYearId()=" + getYearId() + ", getAcademicYear()=" + getAcademicYear()
				+ ", getInstituteName()=" + getInstituteName() + ", getTotalSanctIntake()=" + getTotalSanctIntake()
				+ ", getNoCurrentAdmitedStnt()=" + getNoCurrentAdmitedStnt() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	
	
}
