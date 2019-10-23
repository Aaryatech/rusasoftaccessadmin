package com.ats.rusaaccessweb.reportnew.model;
 
public class StakeHolderFBReport {
	
 	private int stakFbId;
	
	private String fbProcess;
	
	private String feedbackFrom;
	
	private String instituteName;
	
	private String academicYear;
	
	private int fbYesno;

	public int getStakFbId() {
		return stakFbId;
	}

	public void setStakFbId(int stakFbId) {
		this.stakFbId = stakFbId;
	}

	public String getFbProcess() {
		return fbProcess;
	}

	public void setFbProcess(String fbProcess) {
		this.fbProcess = fbProcess;
	}

	public String getFeedbackFrom() {
		return feedbackFrom;
	}

	public void setFeedbackFrom(String feedbackFrom) {
		this.feedbackFrom = feedbackFrom;
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

	public int getFbYesno() {
		return fbYesno;
	}

	public void setFbYesno(int fbYesno) {
		this.fbYesno = fbYesno;
	}

	@Override
	public String toString() {
		return "StakeHolderFBReport [stakFbId=" + stakFbId + ", fbProcess=" + fbProcess + ", feedbackFrom="
				+ feedbackFrom + ", instituteName=" + instituteName + ", academicYear=" + academicYear + ", fbYesno="
				+ fbYesno + ", getStakFbId()=" + getStakFbId() + ", getFbProcess()=" + getFbProcess()
				+ ", getFeedbackFrom()=" + getFeedbackFrom() + ", getInstituteName()=" + getInstituteName()
				+ ", getAcademicYear()=" + getAcademicYear() + ", getFbYesno()=" + getFbYesno() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	
	
	

}
