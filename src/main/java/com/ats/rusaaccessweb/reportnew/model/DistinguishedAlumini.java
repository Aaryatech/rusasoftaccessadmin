package com.ats.rusaaccessweb.reportnew.model;

public class DistinguishedAlumini {

private int alumniDetailId;
	
	private int yearId;

	private String alumniName;
	
	private String passingYear;
	
	private String exInt1;
	
	private String instituteName;
	
	private String academicYear;
	
	private String currDesn;

	public int getAlumniDetailId() {
		return alumniDetailId;
	}

	public void setAlumniDetailId(int alumniDetailId) {
		this.alumniDetailId = alumniDetailId;
	}

	public int getYearId() {
		return yearId;
	}

	public void setYearId(int yearId) {
		this.yearId = yearId;
	}

	public String getAlumniName() {
		return alumniName;
	}

	public void setAlumniName(String alumniName) {
		this.alumniName = alumniName;
	}

	public String getPassingYear() {
		return passingYear;
	}

	public void setPassingYear(String passingYear) {
		this.passingYear = passingYear;
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

	public String getCurrDesn() {
		return currDesn;
	}

	public void setCurrDesn(String currDesn) {
		this.currDesn = currDesn;
	}

	public String getExInt1() {
		return exInt1;
	}

	public void setExInt1(String exInt1) {
		this.exInt1 = exInt1;
	}

	@Override
	public String toString() {
		return "DistinguishedAlumini [alumniDetailId=" + alumniDetailId + ", yearId=" + yearId + ", alumniName="
				+ alumniName + ", passingYear=" + passingYear + ", exInt1=" + exInt1 + ", instituteName="
				+ instituteName + ", academicYear=" + academicYear + ", currDesn=" + currDesn + ", getAlumniDetailId()="
				+ getAlumniDetailId() + ", getYearId()=" + getYearId() + ", getAlumniName()=" + getAlumniName()
				+ ", getPassingYear()=" + getPassingYear() + ", getInstituteName()=" + getInstituteName()
				+ ", getAcademicYear()=" + getAcademicYear() + ", getCurrDesn()=" + getCurrDesn() + ", getExInt1()="
				+ getExInt1() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

	 
	
	
	
}
