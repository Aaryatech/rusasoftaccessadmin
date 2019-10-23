package com.ats.rusaaccessweb.reportnew.model;

public class NoFacultyFinSupp {

private int yearId;
	
	private String academicYear;
	
	private String instituteName;
	
	private float noOfFacSupport;
	
	private float totalFaculty;

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

	public float getNofFacSupport() {
		return noOfFacSupport;
	}

	public void setNofFacSupport(float nofFacSupport) {
		this.noOfFacSupport = nofFacSupport;
	}

	public float getTotalFaculty() {
		return totalFaculty;
	}

	public void setTotalFaculty(float totalFaculty) {
		this.totalFaculty = totalFaculty;
	}

	@Override
	public String toString() {
		return "NoFacultyFinSupp [yearId=" + yearId + ", academicYear=" + academicYear + ", instituteName="
				+ instituteName + ", noOfFacSupport=" + noOfFacSupport + ", totalFaculty=" + totalFaculty
				+ ", getYearId()=" + getYearId() + ", getAcademicYear()=" + getAcademicYear() + ", getInstituteName()="
				+ getInstituteName() + ", getNofFacSupport()=" + getNofFacSupport() + ", getTotalFaculty()="
				+ getTotalFaculty() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

	 
}
