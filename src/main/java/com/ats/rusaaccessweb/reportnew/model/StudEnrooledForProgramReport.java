package com.ats.rusaaccessweb.reportnew.model;

 
public class StudEnrooledForProgramReport {
	
 	private int yearId;
	
	private String  academicYear;
	
	private String programName;
	
	private String instituteName;
	
	private float progStudEnrolled;
	
	private float totalStud;

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

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public String getInstituteName() {
		return instituteName;
	}

	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}

	public float getProgStudEnrolled() {
		return progStudEnrolled;
	}

	public void setProgStudEnrolled(float progStudEnrolled) {
		this.progStudEnrolled = progStudEnrolled;
	}

	public float getTotalStud() {
		return totalStud;
	}

	public void setTotalStud(float totalStud) {
		this.totalStud = totalStud;
	}

	@Override
	public String toString() {
		return "StudEnrooledForProgramReport [yearId=" + yearId + ", academicYear=" + academicYear + ", programName="
				+ programName + ", instituteName=" + instituteName + ", progStudEnrolled=" + progStudEnrolled
				+ ", totalStud=" + totalStud + ", getYearId()=" + getYearId() + ", getAcademicYear()="
				+ getAcademicYear() + ", getProgramName()=" + getProgramName() + ", getInstituteName()="
				+ getInstituteName() + ", getProgStudEnrolled()=" + getProgStudEnrolled() + ", getTotalStud()="
				+ getTotalStud() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	
	
	

}
