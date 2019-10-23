package com.ats.rusaaccessweb.reportnew.model;

import java.util.Date;

public class IntelectulPropRightReport {
	private int conId;
	private String conName;
	private String conFromdt;
	private String conTodt;
	private int conPcount;
	private String academicYear;
	private String instituteName;
	public int getConId() {
		return conId;
	}
	public void setConId(int conId) {
		this.conId = conId;
	}
	public String getConName() {
		return conName;
	}
	public void setConName(String conName) {
		this.conName = conName;
	}
	public String getConFromdt() {
		return conFromdt;
	}
	public void setConFromdt(String conFromdt) {
		this.conFromdt = conFromdt;
	}
	public String getConTodt() {
		return conTodt;
	}
	public void setConTodt(String conTodt) {
		this.conTodt = conTodt;
	}
	public int getConPcount() {
		return conPcount;
	}
	public void setConPcount(int conPcount) {
		this.conPcount = conPcount;
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
		return "IntelectulPropRightReport [conId=" + conId + ", conName=" + conName + ", conFromdt=" + conFromdt
				+ ", conTodt=" + conTodt + ", conPcount=" + conPcount + ", academicYear=" + academicYear
				+ ", instituteName=" + instituteName + "]";
	}
	
	
}
