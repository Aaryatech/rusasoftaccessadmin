package com.ats.rusaaccessweb.reportnew.model;

public class NoOfBookReport {

	private int yearId;

	private String academicYear;

	private String instituteName;

	private float paperCount;

	private float bookCount;

	private float noOfFulltimeFaculty;

	public int getYearId() {
		return yearId;
	}

	public void setYearId(int yearId) {
		this.yearId = yearId;
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

	public float getPaperCount() {
		return paperCount;
	}

	public void setPaperCount(float paperCount) {
		this.paperCount = paperCount;
	}

	public float getNoOfFulltimeFaculty() {
		return noOfFulltimeFaculty;
	}

	public void setNoOfFulltimeFaculty(float noOfFulltimeFaculty) {
		this.noOfFulltimeFaculty = noOfFulltimeFaculty;
	}

	public float getBookCount() {
		return bookCount;
	}

	public void setBookCount(float bookCount) {
		this.bookCount = bookCount;
	}

	@Override
	public String toString() {
		return "NoOfBookReport [yearId=" + yearId + ", academicYear=" + academicYear + ", instituteName="
				+ instituteName + ", paperCount=" + paperCount + ", bookCount=" + bookCount + ", noOfFulltimeFaculty="
				+ noOfFulltimeFaculty + ", getYearId()=" + getYearId() + ", getAcademicYear()=" + getAcademicYear()
				+ ", getInstituteName()=" + getInstituteName() + ", getPaperCount()=" + getPaperCount()
				+ ", getNoOfFulltimeFaculty()=" + getNoOfFulltimeFaculty() + ", getBookCount()=" + getBookCount()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}

}
