package com.ats.rusaaccessweb.reportnew.model;
 
public class PlagarismCodeEthicsReport {
	
 	private int plagCodeEthcId;

 	private int isSoftwrAvabl;
	
	private String urlLink;
	
	private String mechDetectPlag;
	
	private String instituteName;
	
	private String academicYear;

	public int getPlagCodeEthcId() {
		return plagCodeEthcId;
	}

	public void setPlagCodeEthcId(int plagCodeEthcId) {
		this.plagCodeEthcId = plagCodeEthcId;
	}

	public int getIsSoftwrAvabl() {
		return isSoftwrAvabl;
	}

	public void setIsSoftwrAvabl(int isSoftwrAvabl) {
		this.isSoftwrAvabl = isSoftwrAvabl;
	}

	public String getUrlLink() {
		return urlLink;
	}

	public void setUrlLink(String urlLink) {
		this.urlLink = urlLink;
	}

	public String getMechDetectPlag() {
		return mechDetectPlag;
	}

	public void setMechDetectPlag(String mechDetectPlag) {
		this.mechDetectPlag = mechDetectPlag;
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
		return "PlagarismCodeEthicsReport [plagCodeEthcId=" + plagCodeEthcId + ", isSoftwrAvabl=" + isSoftwrAvabl
				+ ", urlLink=" + urlLink + ", mechDetectPlag=" + mechDetectPlag + ", instituteName=" + instituteName
				+ ", academicYear=" + academicYear + ", getPlagCodeEthcId()=" + getPlagCodeEthcId()
				+ ", getIsSoftwrAvabl()=" + getIsSoftwrAvabl() + ", getUrlLink()=" + getUrlLink()
				+ ", getMechDetectPlag()=" + getMechDetectPlag() + ", getInstituteName()=" + getInstituteName()
				+ ", getAcademicYear()=" + getAcademicYear() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}

	 
	
}
