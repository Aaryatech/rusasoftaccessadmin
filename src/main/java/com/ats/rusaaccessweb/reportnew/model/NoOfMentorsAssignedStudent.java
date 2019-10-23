package com.ats.rusaaccessweb.reportnew.model;
 
 public class NoOfMentorsAssignedStudent {

 	private int instituteId;
	
	private String instituteName;
	
	private float totalFacMentor;
	
	private float totalStudMentoring;

	public int getInstituteId() {
		return instituteId;
	}

	public void setInstituteId(int instituteId) {
		this.instituteId = instituteId;
	}

	public String getInstituteName() {
		return instituteName;
	}

	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}

	public float getTotalFacMentor() {
		return totalFacMentor;
	}

	public void setTotalFacMentor(float totalFacMentor) {
		this.totalFacMentor = totalFacMentor;
	}

	public float getTotalStudMentoring() {
		return totalStudMentoring;
	}

	public void setTotalStudMentoring(float totalStudMentoring) {
		this.totalStudMentoring = totalStudMentoring;
	}

	@Override
	public String toString() {
		return "NoOfMentorsAssignedStudent [instituteId=" + instituteId + ", instituteName=" + instituteName
				+ ", totalFacMentor=" + totalFacMentor + ", totalStudMentoring=" + totalStudMentoring
				+ ", getInstituteId()=" + getInstituteId() + ", getInstituteName()=" + getInstituteName()
				+ ", getTotalFacMentor()=" + getTotalFacMentor() + ", getTotalStudMentoring()="
				+ getTotalStudMentoring() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
	
	
}
