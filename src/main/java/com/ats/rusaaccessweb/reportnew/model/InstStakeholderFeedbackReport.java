package com.ats.rusaaccessweb.reportnew.model;

public class InstStakeholderFeedbackReport {

	private int stakFbId;
	private String instituteName;
	private String academicYear;	
	private int fbYesno;	
	private String fbProcess;
	private String feedbackFrom;
	public int getStakFbId() {
		return stakFbId;
	}
	public String getInstituteName() {
		return instituteName;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	
	public int getFbYesno() {
		return fbYesno;
	}
	
	public String getFbProcess() {
		return fbProcess;
	}
	public String getFeedbackFrom() {
		return feedbackFrom;
	}
	public void setStakFbId(int stakFbId) {
		this.stakFbId = stakFbId;
	}
	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	
	public void setFbYesno(int fbYesno) {
		this.fbYesno = fbYesno;
	}
	
	public void setFbProcess(String fbProcess) {
		this.fbProcess = fbProcess;
	}
	public void setFeedbackFrom(String feedbackFrom) {
		this.feedbackFrom = feedbackFrom;
	}
	@Override
	public String toString() {
		return "InstStakeholderFeedbackReport [stakFbId=" + stakFbId + ", instituteName=" + instituteName
				+ ", academicYear=" + academicYear + ", fbYesno=" + fbYesno + ", fbProcess="
				+ fbProcess + ", feedbackFrom=" + feedbackFrom + "]";
	}
	
	
}
