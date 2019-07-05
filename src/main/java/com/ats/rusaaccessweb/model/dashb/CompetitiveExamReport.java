package com.ats.rusaaccessweb.model.dashb;

public class CompetitiveExamReport {

	private int instituteId;
	
	private String instituteName;
	
	private String academicYear;
	 
	private int stateLevelAppear;
	
	private int nationalLevelAppear;
	
	
	private int intLevelAppear;


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


	public int getStateLevelAppear() {
		return stateLevelAppear;
	}


	public void setStateLevelAppear(int stateLevelAppear) {
		this.stateLevelAppear = stateLevelAppear;
	}


	public int getNationalLevelAppear() {
		return nationalLevelAppear;
	}


	public void setNationalLevelAppear(int nationalLevelAppear) {
		this.nationalLevelAppear = nationalLevelAppear;
	}


	public int getIntLevelAppear() {
		return intLevelAppear;
	}


	public void setIntLevelAppear(int intLevelAppear) {
		this.intLevelAppear = intLevelAppear;
	}


	@Override
	public String toString() {
		return "CompetitiveExamReport [instituteId=" + instituteId + ", instituteName=" + instituteName
				+ ", academicYear=" + academicYear + ", stateLevelAppear=" + stateLevelAppear + ", nationalLevelAppear="
				+ nationalLevelAppear + ", intLevelAppear=" + intLevelAppear + "]";
	}
	
	
	
	
	
	

}
