package com.ats.rusaaccessweb.reportnew.model;

 
public class AdmissionsAgainstCategory {

	private String id;
	
	private int castId;

	private int studentCatId;

	private float catTotStudent;

	private float seatsAvaailable;
	
	private String castName;
	
	private String academicYear;
	
	private String instituteName;
	
	private int yearId;
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getCastId() {
		return castId;
	}

	public void setCastId(int castId) {
		this.castId = castId;
	}

	public int getStudentCatId() {
		return studentCatId;
	}

	public void setStudentCatId(int studentCatId) {
		this.studentCatId = studentCatId;
	}

	public float getCatTotStudent() {
		return catTotStudent;
	}

	public void setCatTotStudent(float catTotStudent) {
		this.catTotStudent = catTotStudent;
	}

	public float getSeatsAvaailable() {
		return seatsAvaailable;
	}

	public void setSeatsAvaailable(float seatsAvaailable) {
		this.seatsAvaailable = seatsAvaailable;
	}

	public String getCastName() {
		return castName;
	}

	public void setCastName(String castName) {
		this.castName = castName;
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

	public int getYearId() {
		return yearId;
	}

	public void setYearId(int yearId) {
		this.yearId = yearId;
	}

	@Override
	public String toString() {
		return "AdmissionsAgainstCategory [id=" + id + ", castId=" + castId + ", studentCatId=" + studentCatId
				+ ", catTotStudent=" + catTotStudent + ", seatsAvaailable=" + seatsAvaailable + ", castName=" + castName
				+ ", academicYear=" + academicYear + ", instituteName=" + instituteName + ", yearId=" + yearId + "]";
	}

}
