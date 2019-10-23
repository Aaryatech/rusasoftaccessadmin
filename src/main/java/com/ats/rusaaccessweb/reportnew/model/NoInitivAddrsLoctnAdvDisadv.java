package com.ats.rusaaccessweb.reportnew.model;

public class NoInitivAddrsLoctnAdvDisadv {
	
	private int ttlInitives;
	private String academicYear;
	private String instituteName;
	public int getTtlInitives() {
		return ttlInitives;
	}
	public void setTtlInitives(int ttlInitives) {
		this.ttlInitives = ttlInitives;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public String getInstituteName() {
		return instituteName;
	}
	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}
	@Override
	public String toString() {
		return "NoInitivAddrsLoctnAdvDisadv [ttlInitives=" + ttlInitives + ", academicYear=" + academicYear
				+ ", instituteName=" + instituteName + "]";
	}
	
	

}
