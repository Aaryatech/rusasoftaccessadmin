package com.ats.rusaaccessweb.reportnew.model;
 
public class SportsCulturalActivityComp {
	
 	private int studentActivityId;
	
	private String activityName;
	
	private String  level;
	
	private String date;
	
	private int yearId;
	
	private String  instituteName;
	
	private String  academicYear;

	public int getStudentActivityId() {
		return studentActivityId;
	}

	public void setStudentActivityId(int studentActivityId) {
		this.studentActivityId = studentActivityId;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
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

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	@Override
	public String toString() {
		return "SportsCulturalActivityComp [studentActivityId=" + studentActivityId + ", activityName=" + activityName
				+ ", level=" + level + ", date=" + date + ", yearId=" + yearId + ", instituteName=" + instituteName
				+ ", academicYear=" + academicYear + ", getStudentActivityId()=" + getStudentActivityId()
				+ ", getActivityName()=" + getActivityName() + ", getLevel()=" + getLevel() + ", getDate()=" + getDate()
				+ ", getYearId()=" + getYearId() + ", getInstituteName()=" + getInstituteName() + ", getAcademicYear()="
				+ getAcademicYear() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	
	
	
	
	

}
