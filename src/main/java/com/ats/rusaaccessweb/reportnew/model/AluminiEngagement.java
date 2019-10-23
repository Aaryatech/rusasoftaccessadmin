package com.ats.rusaaccessweb.reportnew.model;
 
public class AluminiEngagement {

 	private int yearId;

	private String instituteName;

	private String academicYear;
	
	private float contri;
	
	private String aluminiAsso;
	
	private String estbYear;

	public int getYearId() {
		return yearId;
	}

	public void setYearId(int yearId) {
		this.yearId = yearId;
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

	public float getContri() {
		return contri;
	}

	public void setContri(float contri) {
		this.contri = contri;
	}

	public String getAluminiAsso() {
		return aluminiAsso;
	}

	public void setAluminiAsso(String aluminiAsso) {
		this.aluminiAsso = aluminiAsso;
	}

	public String getEstbYear() {
		return estbYear;
	}

	public void setEstbYear(String estbYear) {
		this.estbYear = estbYear;
	}

	@Override
	public String toString() {
		return "AluminiEngagement [yearId=" + yearId + ", instituteName=" + instituteName + ", academicYear="
				+ academicYear + ", contri=" + contri + ", aluminiAsso=" + aluminiAsso + ", estbYear=" + estbYear
				+ ", getYearId()=" + getYearId() + ", getInstituteName()=" + getInstituteName() + ", getAcademicYear()="
				+ getAcademicYear() + ", getContri()=" + getContri() + ", getAluminiAsso()=" + getAluminiAsso()
				+ ", getEstbYear()=" + getEstbYear() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
	
	
	

}
