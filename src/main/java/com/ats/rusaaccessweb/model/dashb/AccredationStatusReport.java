package com.ats.rusaaccessweb.model.dashb;
 
public class AccredationStatusReport {

	  
	private int instituteId;

	private String instituteName;

	private String qualityFromdt;

	private String qualityTodt;

	private String aisheCode;

	private String qualityInitiativeName;

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

	 

	public String getAisheCode() {
		return aisheCode;
	}

	public void setAisheCode(String aisheCode) {
		this.aisheCode = aisheCode;
	}

	public String getQualityInitiativeName() {
		return qualityInitiativeName;
	}

	public void setQualityInitiativeName(String qualityInitiativeName) {
		this.qualityInitiativeName = qualityInitiativeName;
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

	@Override
	public String toString() {
		return "AccredationStatusReport [instituteId=" + instituteId + ", instituteName=" + instituteName
				+ ", qualityFromdt=" + qualityFromdt + ", qualityTodt=" + qualityTodt + ", aisheCode=" + aisheCode
				+ ", qualityInitiativeName=" + qualityInitiativeName + "]";
	}
 
	
	

}
