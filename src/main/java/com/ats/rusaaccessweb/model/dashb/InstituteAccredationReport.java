package com.ats.rusaaccessweb.model.dashb;

 
public class InstituteAccredationReport {
	
private int instituteId;
	
	private String instituteName;
	
	private String  NBA;
	
	private String  NAAC;
	
	private String  NIRF;
	

	private String THE;


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


	public String getNBA() {
		return NBA;
	}


	public void setNBA(String nBA) {
		NBA = nBA;
	}


	public String getNAAC() {
		return NAAC;
	}


	public void setNAAC(String nAAC) {
		NAAC = nAAC;
	}


	public String getNIRF() {
		return NIRF;
	}


	public void setNIRF(String nIRF) {
		NIRF = nIRF;
	}


	public String getTHE() {
		return THE;
	}


	public void setTHE(String tHE) {
		THE = tHE;
	}


	@Override
	public String toString() {
		return "InstituteAccredationReport [instituteId=" + instituteId + ", instituteName=" + instituteName + ", NBA="
				+ NBA + ", NAAC=" + NAAC + ", NIRF=" + NIRF + ", THE=" + THE + "]";
	}

 
	
	
	
	
	
	
	

}