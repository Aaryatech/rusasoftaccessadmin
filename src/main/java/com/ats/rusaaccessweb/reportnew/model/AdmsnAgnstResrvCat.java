package com.ats.rusaaccessweb.reportnew.model;

public class AdmsnAgnstResrvCat {

	private String uniqId;
	private int studentCatId;
	private String castName;
	private int castId;
	private int catTotStudent;
	private int seatsAvailable;
	private String academic_year;
	private int yearId;
	private String instituteName;
	public String getUniqId() {
		return uniqId;
	}
	public void setUniqId(String uniqId) {
		this.uniqId = uniqId;
	}
	public int getStudentCatId() {
		return studentCatId;
	}
	public void setStudentCatId(int studentCatId) {
		this.studentCatId = studentCatId;
	}
	public String getCastName() {
		return castName;
	}
	public void setCastName(String castName) {
		this.castName = castName;
	}
	public int getCastId() {
		return castId;
	}
	public void setCastId(int castId) {
		this.castId = castId;
	}
	public int getCatTotStudent() {
		return catTotStudent;
	}
	public void setCatTotStudent(int catTotStudent) {
		this.catTotStudent = catTotStudent;
	}
	public int getSeatsAvailable() {
		return seatsAvailable;
	}
	public void setSeatsAvailable(int seatsAvailable) {
		this.seatsAvailable = seatsAvailable;
	}
	public String getAcademic_year() {
		return academic_year;
	}
	public void setAcademic_year(String academic_year) {
		this.academic_year = academic_year;
	}
	public int getYearId() {
		return yearId;
	}
	public void setYearId(int yearId) {
		this.yearId = yearId;
	}
	public String getInstituteName() {
		return instituteName;
	}
	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}
	@Override
	public String toString() {
		return "AdmsnAgnstResrvCat [uniqId=" + uniqId + ", studentCatId=" + studentCatId + ", castName=" + castName
				+ ", castId=" + castId + ", catTotStudent=" + catTotStudent + ", seatsAvailable=" + seatsAvailable
				+ ", academic_year=" + academic_year + ", yearId=" + yearId + ", instituteName=" + instituteName + "]";
	}
	
	
}
