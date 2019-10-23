package com.ats.rusaaccessweb.reportnew.model;
 
public class GetMissions {

 	private int instMissionId;
	
	private String instMissionText;
	
 	private String instituteName;
	
	private int instituteId;

	public int getInstMissionId() {
		return instMissionId;
	}

	public void setInstMissionId(int instMissionId) {
		this.instMissionId = instMissionId;
	}

	public String getInstMissionText() {
		return instMissionText;
	}

	public void setInstMissionText(String instMissionText) {
		this.instMissionText = instMissionText;
	}

	public String getInstituteName() {
		return instituteName;
	}

	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}

	public int getInstituteId() {
		return instituteId;
	}

	public void setInstituteId(int instituteId) {
		this.instituteId = instituteId;
	}

	@Override
	public String toString() {
		return "GetMissions [instMissionId=" + instMissionId + ", instMissionText=" + instMissionText
				+ ", instituteName=" + instituteName + ", instituteId=" + instituteId + ", getInstMissionId()="
				+ getInstMissionId() + ", getInstMissionText()=" + getInstMissionText() + ", getInstituteName()="
				+ getInstituteName() + ", getInstituteId()=" + getInstituteId() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	
	
	
}
