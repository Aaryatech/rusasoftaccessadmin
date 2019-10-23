package com.ats.rusaaccessweb.reportnew.model;

 
public class TeacherStudUsingLib {
	
private int yearId;
	
	private int  instituteId;
	
	private float noOfFullTimeFaculty;
	
	private float noOfCurrentAdmitedStnt;
	
	private float avgTeacher;
	
	private float avgStudent;
	
	private String instituteName;
	
	private String academicYear;

	public int getYearId() {
		return yearId;
	}

	public void setYearId(int yearId) {
		this.yearId = yearId;
	}

	public int getInstituteId() {
		return instituteId;
	}

	public void setInstituteId(int instituteId) {
		this.instituteId = instituteId;
	}

	public float getNoOfFullTimeFaculty() {
		return noOfFullTimeFaculty;
	}

	public void setNoOfFullTimeFaculty(float noOfFullTimeFaculty) {
		this.noOfFullTimeFaculty = noOfFullTimeFaculty;
	}

	public float getNoOfCurrentAdmitedStnt() {
		return noOfCurrentAdmitedStnt;
	}

	public void setNoOfCurrentAdmitedStnt(float noOfCurrentAdmitedStnt) {
		this.noOfCurrentAdmitedStnt = noOfCurrentAdmitedStnt;
	}

	public float getAvgTeacher() {
		return avgTeacher;
	}

	public void setAvgTeacher(float avgTeacher) {
		this.avgTeacher = avgTeacher;
	}

	public float getAvgStudent() {
		return avgStudent;
	}

	public void setAvgStudent(float avgStudent) {
		this.avgStudent = avgStudent;
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

	@Override
	public String toString() {
		return "TeacherStudUsingLib [yearId=" + yearId + ", instituteId=" + instituteId + ", noOfFullTimeFaculty="
				+ noOfFullTimeFaculty + ", noOfCurrentAdmitedStnt=" + noOfCurrentAdmitedStnt + ", avgTeacher="
				+ avgTeacher + ", avgStudent=" + avgStudent + ", instituteName=" + instituteName + ", academicYear="
				+ academicYear + ", getYearId()=" + getYearId() + ", getInstituteId()=" + getInstituteId()
				+ ", getNoOfFullTimeFaculty()=" + getNoOfFullTimeFaculty() + ", getNoOfCurrentAdmitedStnt()="
				+ getNoOfCurrentAdmitedStnt() + ", getAvgTeacher()=" + getAvgTeacher() + ", getAvgStudent()="
				+ getAvgStudent() + ", getInstituteName()=" + getInstituteName() + ", getAcademicYear()="
				+ getAcademicYear() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

	  

}
