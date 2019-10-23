package com.ats.rusaaccessweb.reportnew.model;
 
public class QualInitiativeAssurance {
	
	 
	private int qualityId;
	
	private String qualityFromdt;
	
	private String qualityTodt;
	
	private float qualityPcount;

	private String instituteName;
	
	private String academicYear;
	
	private String qualityInitiativeName;

	public int getQualityId() {
		return qualityId;
	}

	public void setQualityId(int qualityId) {
		this.qualityId = qualityId;
	}

	public String getQualityFromdt() {
		return qualityFromdt;
	}

	public void setQualityFromdt(String qualityFromdt) {
		this.qualityFromdt = qualityFromdt;
	}

	public String getQualityTodt() {
		return qualityTodt;
	}

	public void setQualityTodt(String qualityTodt) {
		this.qualityTodt = qualityTodt;
	}

	public float getQualityPcount() {
		return qualityPcount;
	}

	public void setQualityPcount(float qualityPcount) {
		this.qualityPcount = qualityPcount;
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

	public String getQualityInitiativeName() {
		return qualityInitiativeName;
	}

	public void setQualityInitiativeName(String qualityInitiativeName) {
		this.qualityInitiativeName = qualityInitiativeName;
	}

	@Override
	public String toString() {
		return "QualInitiativeAssurance [qualityId=" + qualityId + ", qualityFromdt=" + qualityFromdt + ", qualityTodt="
				+ qualityTodt + ", qualityPcount=" + qualityPcount + ", instituteName=" + instituteName
				+ ", academicYear=" + academicYear + ", qualityInitiativeName=" + qualityInitiativeName
				+ ", getQualityId()=" + getQualityId() + ", getQualityFromdt()=" + getQualityFromdt()
				+ ", getQualityTodt()=" + getQualityTodt() + ", getQualityPcount()=" + getQualityPcount()
				+ ", getInstituteName()=" + getInstituteName() + ", getAcademicYear()=" + getAcademicYear()
				+ ", getQualityInitiativeName()=" + getQualityInitiativeName() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	
	

	

}
