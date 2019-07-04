package com.ats.rusaaccessweb.model.dashb;
 
public class PlacementUgPgStud {
	
 	private int instituteId;
	
	private String instituteName;
	
	private String minPck;
	
	private String maxPck;;
	
	
	private String studPlaced;


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


	public String getMinPck() {
		return minPck;
	}


	public void setMinPck(String minPck) {
		this.minPck = minPck;
	}


	public String getMaxPck() {
		return maxPck;
	}


	public void setMaxPck(String maxPck) {
		this.maxPck = maxPck;
	}


	public String getStudPlaced() {
		return studPlaced;
	}


	public void setStudPlaced(String studPlaced) {
		this.studPlaced = studPlaced;
	}


	@Override
	public String toString() {
		return "PlacementUgPgStud [instituteId=" + instituteId + ", instituteName=" + instituteName + ", minPck="
				+ minPck + ", maxPck=" + maxPck + ", studPlaced=" + studPlaced + "]";
	}
	
	
	
	
	

}
