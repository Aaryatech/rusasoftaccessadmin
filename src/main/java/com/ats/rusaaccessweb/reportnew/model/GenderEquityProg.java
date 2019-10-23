package com.ats.rusaaccessweb.reportnew.model;
 
public class GenderEquityProg {

 	private int gprogId;

	private String gprogName;

	private String academicYear;

	private String gprogFromdt;

	private String gprogTodt;

	private int pCount;

	private String instituteName;

	public int getGprogId() {
		return gprogId;
	}

	public void setGprogId(int gprogId) {
		this.gprogId = gprogId;
	}

	public String getGprogName() {
		return gprogName;
	}

	public void setGprogName(String gprogName) {
		this.gprogName = gprogName;
	}

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	public String getGprogFromdt() {
		return gprogFromdt;
	}

	public void setGprogFromdt(String gprogFromdt) {
		this.gprogFromdt = gprogFromdt;
	}

	public String getGprogTodt() {
		return gprogTodt;
	}

	public void setGprogTodt(String gprogTodt) {
		this.gprogTodt = gprogTodt;
	}

	public int getpCount() {
		return pCount;
	}

	public void setpCount(int pCount) {
		this.pCount = pCount;
	}

	public String getInstituteName() {
		return instituteName;
	}

	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}

	@Override
	public String toString() {
		return "GenderEquityProg [gprogId=" + gprogId + ", gprogName=" + gprogName + ", academicYear=" + academicYear
				+ ", gprogFromdt=" + gprogFromdt + ", gprogTodt=" + gprogTodt + ", pCount=" + pCount
				+ ", instituteName=" + instituteName + ", getGprogId()=" + getGprogId() + ", getGprogName()="
				+ getGprogName() + ", getAcademicYear()=" + getAcademicYear() + ", getGprogFromdt()=" + getGprogFromdt()
				+ ", getGprogTodt()=" + getGprogTodt() + ", getpCount()=" + getpCount() + ", getInstituteName()="
				+ getInstituteName() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	
	

}
