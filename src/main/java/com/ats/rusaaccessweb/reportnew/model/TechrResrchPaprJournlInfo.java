package com.ats.rusaaccessweb.reportnew.model;

public class TechrResrchPaprJournlInfo {

	private int journalId;
	private int journalType;
	private String coAuthor;
	private String title;
	private String journalName;
	private String publicationYear;
	private String facultyFirstName;
	private String deptName;
	private String academicYear;
	private String instituteName;
	public int getJournalId() {
		return journalId;
	}
	public void setJournalId(int journalId) {
		this.journalId = journalId;
	}
	
	public int getJournalType() {
		return journalType;
	}
	public void setJournalType(int journalType) {
		this.journalType = journalType;
	}
	public String getCoAuthor() {
		return coAuthor;
	}
	public void setCoAuthor(String coAuthor) {
		this.coAuthor = coAuthor;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getJournalName() {
		return journalName;
	}
	public void setJournalName(String journalName) {
		this.journalName = journalName;
	}
	public String getFacultyFirstName() {
		return facultyFirstName;
	}
	public void setFacultyFirstName(String facultyFirstName) {
		this.facultyFirstName = facultyFirstName;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
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
	
	public String getPublicationYear() {
		return publicationYear;
	}
	public void setPublicationYear(String publicationYear) {
		this.publicationYear = publicationYear;
	}
	@Override
	public String toString() {
		return "TechrResrchPaprJournlInfo [journalId=" + journalId + ", journalType=" + journalType + ", coAuthor="
				+ coAuthor + ", title=" + title + ", journalName=" + journalName + ", publicationYear="
				+ publicationYear + ", facultyFirstName=" + facultyFirstName + ", deptName=" + deptName
				+ ", academicYear=" + academicYear + ", instituteName=" + instituteName + "]";
	}
	

}
