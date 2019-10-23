package com.ats.rusaaccessweb.reportnew.model;

public class TechrResrchPaprJournlRatio {

	private int yearId;
	private int fullTimeTeacher;
	private int  publishedInUgc;
	private String academicYear;
	private String instituteName;
	public int getYearId() {
		return yearId;
	}
	public void setYearId(int yearId) {
		this.yearId = yearId;
	}
	public int getFullTimeTeacher() {
		return fullTimeTeacher;
	}
	public void setFullTimeTeacher(int fullTimeTeacher) {
		this.fullTimeTeacher = fullTimeTeacher;
	}
	public int getPublishedInUgc() {
		return publishedInUgc;
	}
	public void setPublishedInUgc(int publishedInUgc) {
		this.publishedInUgc = publishedInUgc;
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
		return "TechrResrchPaprJournlRatio [yearId=" + yearId + ", fullTimeTeacher=" + fullTimeTeacher
				+ ", publishedInUgc=" + publishedInUgc + ", academicYear=" + academicYear + ", instituteName="
				+ instituteName + "]";
	}
	
	
}
