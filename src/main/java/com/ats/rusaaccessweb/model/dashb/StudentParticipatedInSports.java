package com.ats.rusaaccessweb.model.dashb;
 
public class StudentParticipatedInSports {
	
	 
	private int instituteId;
	
	private String  instituteName;
	
	private String  academicYear;
	
	private String  sportCount;
	
	
	private String  culturalCount;


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


	 


	public String getAcademicYear() {
		return academicYear;
	}


	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}


	public String getSportCount() {
		return sportCount;
	}


	public void setSportCount(String sportCount) {
		this.sportCount = sportCount;
	}


	public String getCulturalCount() {
		return culturalCount;
	}


	public void setCulturalCount(String culturalCount) {
		this.culturalCount = culturalCount;
	}


	@Override
	public String toString() {
		return "StudentParticipatedInSports [instituteId=" + instituteId + ", instituteName=" + instituteName
				+ ", academicYear=" + academicYear + ", sportCount=" + sportCount + ", culturalCount=" + culturalCount
				+ "]";
	}

 
	
	 
}
