package com.ats.rusaaccessweb.reportnew.model;
 
public class ResearchProjNoPerTeacher {
 	
	private String id;
	
	private int facultyId;

	private String facultyFirstName;

	private int noOfProj;

	private String instituteName;

	private String academicyear;
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getFacultyId() {
		return facultyId;
	}

	public void setFacultyId(int facultyId) {
		this.facultyId = facultyId;
	}

	public String getFacultyFirstName() {
		return facultyFirstName;
	}

	public void setFacultyFirstName(String facultyFirstName) {
		this.facultyFirstName = facultyFirstName;
	}

	public int getNoOfProj() {
		return noOfProj;
	}

	public void setNoOfProj(int noOfProj) {
		this.noOfProj = noOfProj;
	}

	public String getInstituteName() {
		return instituteName;
	}

	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}

	public String getAcademicyear() {
		return academicyear;
	}

	public void setAcademicyear(String academicyear) {
		this.academicyear = academicyear;
	}

	@Override
	public String toString() {
		return "ResearchProjNoPerTeacher [id=" + id + ", facultyId=" + facultyId + ", facultyFirstName="
				+ facultyFirstName + ", noOfProj=" + noOfProj + ", instituteName=" + instituteName + ", academicyear="
				+ academicyear + ", getId()=" + getId() + ", getFacultyId()=" + getFacultyId()
				+ ", getFacultyFirstName()=" + getFacultyFirstName() + ", getNoOfProj()=" + getNoOfProj()
				+ ", getInstituteName()=" + getInstituteName() + ", getAcademicyear()=" + getAcademicyear()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}

	 
	
	

}
