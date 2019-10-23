package com.ats.rusaaccessweb.reportnew.model;

 

public class ReaddressalOfStudGrievennce {
  	private String id;
	
	private String instituteName;

	private String academicYear;
	
	private String studGrievnce;
	
	private float totalAppealed;
	
	private float totalRedresed;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getStudGrievnce() {
		return studGrievnce;
	}

	public void setStudGrievnce(String studGrievnce) {
		this.studGrievnce = studGrievnce;
	}

	public float getTotalAppealed() {
		return totalAppealed;
	}

	public void setTotalAppealed(float totalAppealed) {
		this.totalAppealed = totalAppealed;
	}

	public float getTotalRedresed() {
		return totalRedresed;
	}

	public void setTotalRedresed(float totalRedresed) {
		this.totalRedresed = totalRedresed;
	}

	@Override
	public String toString() {
		return "ReaddressalOfStudGrievennce [id=" + id + ", instituteName=" + instituteName + ", academicYear="
				+ academicYear + ", studGrievnce=" + studGrievnce + ", totalAppealed=" + totalAppealed
				+ ", totalRedresed=" + totalRedresed + ", getId()=" + getId() + ", getInstituteName()="
				+ getInstituteName() + ", getAcademicYear()=" + getAcademicYear() + ", getStudGrievnce()="
				+ getStudGrievnce() + ", getTotalAppealed()=" + getTotalAppealed() + ", getTotalRedresed()="
				+ getTotalRedresed() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	
	
	
	
	
}
