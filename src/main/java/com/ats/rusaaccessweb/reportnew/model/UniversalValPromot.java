package com.ats.rusaaccessweb.reportnew.model;
 
public class UniversalValPromot {
	
	 
	private int actCndctId;
	
	private String ttleProgrmAct;	
	
	private String fromDate;
	
	private String toDate;
	
	private int noOfParticipant;
	
	private String yearId;
	
	private String instituteName;
	
	private String academicYear;

	public int getActCndctId() {
		return actCndctId;
	}

	public void setActCndctId(int actCndctId) {
		this.actCndctId = actCndctId;
	}

	public String getTtleProgrmAct() {
		return ttleProgrmAct;
	}

	public void setTtleProgrmAct(String ttleProgrmAct) {
		this.ttleProgrmAct = ttleProgrmAct;
	}

  	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

  	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public int getNoOfParticipant() {
		return noOfParticipant;
	}

	public void setNoOfParticipant(int noOfParticipant) {
		this.noOfParticipant = noOfParticipant;
	}

	public String getYearId() {
		return yearId;
	}

	public void setYearId(String yearId) {
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

	@Override
	public String toString() {
		return "UniversalValPromot [actCndctId=" + actCndctId + ", ttleProgrmAct=" + ttleProgrmAct + ", fromDate="
				+ fromDate + ", toDate=" + toDate + ", noOfParticipant=" + noOfParticipant + ", yearId=" + yearId
				+ ", instituteName=" + instituteName + ", academicYear=" + academicYear + ", getActCndctId()="
				+ getActCndctId() + ", getTtleProgrmAct()=" + getTtleProgrmAct() + ", getFromDate()=" + getFromDate()
				+ ", getToDate()=" + getToDate() + ", getNoOfParticipant()=" + getNoOfParticipant() + ", getYearId()="
				+ getYearId() + ", getInstituteName()=" + getInstituteName() + ", getAcademicYear()="
				+ getAcademicYear() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	
	
	
	
}
