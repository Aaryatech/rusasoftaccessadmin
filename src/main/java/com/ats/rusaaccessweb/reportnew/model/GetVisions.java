package com.ats.rusaaccessweb.reportnew.model;
 
public class GetVisions {

 	private int instVisionId;
	
	private String instituteName;
	
	private String instVisionText;

	public int getInstVisionId() {
		return instVisionId;
	}

	public void setInstVisionId(int instVisionId) {
		this.instVisionId = instVisionId;
	}

	public String getInstituteName() {
		return instituteName;
	}

	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}

	public String getInstVisionText() {
		return instVisionText;
	}

	public void setInstVisionText(String instVisionText) {
		this.instVisionText = instVisionText;
	}

	@Override
	public String toString() {
		return "GetVisions [instVisionId=" + instVisionId + ", instituteName=" + instituteName + ", instVisionText="
				+ instVisionText + ", getInstVisionId()=" + getInstVisionId() + ", getInstituteName()="
				+ getInstituteName() + ", getInstVisionText()=" + getInstVisionText() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	
	
}
