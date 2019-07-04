package com.ats.rusaaccessweb.model.dashb;

 
public class InstituteAccredationReport {
	
 	private int instituteId;
	
	private String instituteName;
	
	private int NBA;
	
	private int  NAAC;
	
	private int  NIRF;
	

	private int THE;


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


	public int getNBA() {
		return NBA;
	}


	public void setNBA(int nBA) {
		NBA = nBA;
	}


	public int getNAAC() {
		return NAAC;
	}


	public void setNAAC(int nAAC) {
		NAAC = nAAC;
	}


	public int getNIRF() {
		return NIRF;
	}


	public void setNIRF(int nIRF) {
		NIRF = nIRF;
	}


	public int getTHE() {
		return THE;
	}


	public void setTHE(int tHE) {
		THE = tHE;
	}


	@Override
	public String toString() {
		return "InstituteAccredationReport [instituteId=" + instituteId + ", instituteName=" + instituteName + ", NBA="
				+ NBA + ", NAAC=" + NAAC + ", NIRF=" + NIRF + ", THE=" + THE + "]";
	}
	
	
	
	
	
	
	
	
	

}
