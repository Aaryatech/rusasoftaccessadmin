package com.ats.rusaaccessweb.reportnew.model;
 
public class ValueAddedCoursesReport {
	
 	private int valueAddedCourseId;
	
	private String valueAddedCourseName;
	
	private String courseCode;
	
	private String yearOfOffering;
	
	private String yearOfDiscontinuation;
	
	private float noOfStudentsEnrolled;
	
	private float noOfStudentsCompletedCourse;
	
	private String instituteName;
	
	private String academicYear;

	public int getValueAddedCourseId() {
		return valueAddedCourseId;
	}

	public void setValueAddedCourseId(int valueAddedCourseId) {
		this.valueAddedCourseId = valueAddedCourseId;
	}

	public String getValueAddedCourseName() {
		return valueAddedCourseName;
	}

	public void setValueAddedCourseName(String valueAddedCourseName) {
		this.valueAddedCourseName = valueAddedCourseName;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public String getYearOfOffering() {
		return yearOfOffering;
	}

	public void setYearOfOffering(String yearOfOffering) {
		this.yearOfOffering = yearOfOffering;
	}

	public String getYearOfDiscontinuation() {
		return yearOfDiscontinuation;
	}

	public void setYearOfDiscontinuation(String yearOfDiscontinuation) {
		this.yearOfDiscontinuation = yearOfDiscontinuation;
	}

	public float getNoOfStudentsEnrolled() {
		return noOfStudentsEnrolled;
	}

	public void setNoOfStudentsEnrolled(float noOfStudentsEnrolled) {
		this.noOfStudentsEnrolled = noOfStudentsEnrolled;
	}

	public float getNoOfStudentsCompletedCourse() {
		return noOfStudentsCompletedCourse;
	}

	public void setNoOfStudentsCompletedCourse(float noOfStudentsCompletedCourse) {
		this.noOfStudentsCompletedCourse = noOfStudentsCompletedCourse;
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
		return "ValueAddedCoursesReport [valueAddedCourseId=" + valueAddedCourseId + ", valueAddedCourseName="
				+ valueAddedCourseName + ", courseCode=" + courseCode + ", yearOfOffering=" + yearOfOffering
				+ ", yearOfDiscontinuation=" + yearOfDiscontinuation + ", noOfStudentsEnrolled=" + noOfStudentsEnrolled
				+ ", noOfStudentsCompletedCourse=" + noOfStudentsCompletedCourse + ", instituteName=" + instituteName
				+ ", academicYear=" + academicYear + ", getValueAddedCourseId()=" + getValueAddedCourseId()
				+ ", getValueAddedCourseName()=" + getValueAddedCourseName() + ", getCourseCode()=" + getCourseCode()
				+ ", getYearOfOffering()=" + getYearOfOffering() + ", getYearOfDiscontinuation()="
				+ getYearOfDiscontinuation() + ", getNoOfStudentsEnrolled()=" + getNoOfStudentsEnrolled()
				+ ", getNoOfStudentsCompletedCourse()=" + getNoOfStudentsCompletedCourse() + ", getInstituteName()="
				+ getInstituteName() + ", getAcademicYear()=" + getAcademicYear() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	
	

}
