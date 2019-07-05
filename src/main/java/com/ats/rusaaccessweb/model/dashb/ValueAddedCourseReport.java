package com.ats.rusaaccessweb.model.dashb;

 
public class ValueAddedCourseReport {

	private int valueAddedCourseId;

	private int instituteId;

	private String instituteName;

	private int noOfStudentsEnrolled;

	private String valueAddedCourseName;

	private int noOfStudentsCompletedCourse;

	private String courseCode;
	
	private String  academicYear;

	public int getValueAddedCourseId() {
		return valueAddedCourseId;
	}

	public void setValueAddedCourseId(int valueAddedCourseId) {
		this.valueAddedCourseId = valueAddedCourseId;
	}

	public int getInstituteId() {
		return instituteId;
	}

	public void setInstituteId(int instituteId) {
		this.instituteId = instituteId;
	}

	public String getInstituteName() {
		return instituteName;
	}

	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}

	public int getNoOfStudentsEnrolled() {
		return noOfStudentsEnrolled;
	}

	public void setNoOfStudentsEnrolled(int noOfStudentsEnrolled) {
		this.noOfStudentsEnrolled = noOfStudentsEnrolled;
	}

	public String getValueAddedCourseName() {
		return valueAddedCourseName;
	}

	public void setValueAddedCourseName(String valueAddedCourseName) {
		this.valueAddedCourseName = valueAddedCourseName;
	}

	public int getNoOfStudentsCompletedCourse() {
		return noOfStudentsCompletedCourse;
	}

	public void setNoOfStudentsCompletedCourse(int noOfStudentsCompletedCourse) {
		this.noOfStudentsCompletedCourse = noOfStudentsCompletedCourse;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	@Override
	public String toString() {
		return "ValueAddedCourseReport [valueAddedCourseId=" + valueAddedCourseId + ", instituteId=" + instituteId
				+ ", instituteName=" + instituteName + ", noOfStudentsEnrolled=" + noOfStudentsEnrolled
				+ ", valueAddedCourseName=" + valueAddedCourseName + ", noOfStudentsCompletedCourse="
				+ noOfStudentsCompletedCourse + ", courseCode=" + courseCode + ", academicYear=" + academicYear
				+ ", getValueAddedCourseId()=" + getValueAddedCourseId() + ", getInstituteId()=" + getInstituteId()
				+ ", getInstituteName()=" + getInstituteName() + ", getNoOfStudentsEnrolled()="
				+ getNoOfStudentsEnrolled() + ", getValueAddedCourseName()=" + getValueAddedCourseName()
				+ ", getNoOfStudentsCompletedCourse()=" + getNoOfStudentsCompletedCourse() + ", getCourseCode()="
				+ getCourseCode() + ", getAcademicYear()=" + getAcademicYear() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

	
	 

}	 