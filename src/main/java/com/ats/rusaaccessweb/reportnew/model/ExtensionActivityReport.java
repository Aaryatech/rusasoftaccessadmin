package com.ats.rusaaccessweb.reportnew.model;
 
public class ExtensionActivityReport {

 	private int instExtensionActId;
	
	private String tActivityTitle;
	
	private float noOfStudParticipated;
	
	private float noOfFacultyParticipated;
	
	private float noOfStudInInst;
	
	private String academicYear;
	
	private String instituteName;
	
 	private float result;

	public int getInstExtensionActId() {
		return instExtensionActId;
	}

	public void setInstExtensionActId(int instExtensionActId) {
		this.instExtensionActId = instExtensionActId;
	}

	public String gettActivityTitle() {
		return tActivityTitle;
	}

	public void settActivityTitle(String tActivityTitle) {
		this.tActivityTitle = tActivityTitle;
	}

	public float getNoOfStudParticipated() {
		return noOfStudParticipated;
	}

	public void setNoOfStudParticipated(float noOfStudParticipated) {
		this.noOfStudParticipated = noOfStudParticipated;
	}

	public float getNoOfFacultyParticipated() {
		return noOfFacultyParticipated;
	}

	public void setNoOfFacultyParticipated(float noOfFacultyParticipated) {
		this.noOfFacultyParticipated = noOfFacultyParticipated;
	}

	public float getNoOfStudInInst() {
		return noOfStudInInst;
	}

	public void setNoOfStudInInst(float noOfStudInInst) {
		this.noOfStudInInst = noOfStudInInst;
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

	public float getResult() {
		return result;
	}

	public void setResult(float result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "ExtensionActivityReport [instExtensionActId=" + instExtensionActId + ", tActivityTitle="
				+ tActivityTitle + ", noOfStudParticipated=" + noOfStudParticipated + ", noOfFacultyParticipated="
				+ noOfFacultyParticipated + ", noOfStudInInst=" + noOfStudInInst + ", academicYear=" + academicYear
				+ ", instituteName=" + instituteName + ", result=" + result + ", getInstExtensionActId()="
				+ getInstExtensionActId() + ", gettActivityTitle()=" + gettActivityTitle()
				+ ", getNoOfStudParticipated()=" + getNoOfStudParticipated() + ", getNoOfFacultyParticipated()="
				+ getNoOfFacultyParticipated() + ", getNoOfStudInInst()=" + getNoOfStudInInst() + ", getAcademicYear()="
				+ getAcademicYear() + ", getInstituteName()=" + getInstituteName() + ", getResult()=" + getResult()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
 	
 	
 	
}
