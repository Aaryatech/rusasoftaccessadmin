package com.ats.rusaaccessweb.model.dashb;

public class SubjectResrchReport {

	private String id;
	private String instituteName;
	private String facultyName;
	private int affiliatedGuide;
	private int studentReg;
	private String academicYear;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getInstituteName() {
		return instituteName;
	}
	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}
	public String getFacultyName() {
		return facultyName;
	}
	public void setFacultyName(String facultyName) {
		this.facultyName = facultyName;
	}
	public int getAffiliatedGuide() {
		return affiliatedGuide;
	}
	public void setAffiliatedGuide(int affiliatedGuide) {
		this.affiliatedGuide = affiliatedGuide;
	}
	public int getStudentReg() {
		return studentReg;
	}
	public void setStudentReg(int studentReg) {
		this.studentReg = studentReg;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	@Override
	public String toString() {
		return "SubjectResrchReport [id=" + id + ", instituteName=" + instituteName + ", facultyName=" + facultyName
				+ ", affiliatedGuide=" + affiliatedGuide + ", studentReg=" + studentReg + ", academicYear="
				+ academicYear + "]";
	}
	
	
}
