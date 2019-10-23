package com.ats.rusaaccessweb.reportnew.model;
 
public class GetTeachersUsingICT {
	 
 	private int yearId;
	
	private float techersUsingIct;
	
	private float totalFaculty;
	
	private String academicYear;

	private String instituteName;

	public int getYearId() {
		return yearId;
	}

	public void setYearId(int yearId) {
		this.yearId = yearId;
	}

	 
	public float getTechersUsingIct() {
		return techersUsingIct;
	}

	public void setTechersUsingIct(float techersUsingIct) {
		this.techersUsingIct = techersUsingIct;
	}

	public float getTotalFaculty() {
		return totalFaculty;
	}

	public void setTotalFaculty(float totalFaculty) {
		this.totalFaculty = totalFaculty;
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

	@Override
	public String toString() {
		return "GetTeachersUsingICT [yearId=" + yearId + ", techersUsingIct=" + techersUsingIct + ", totalFaculty="
				+ totalFaculty + ", academicYear=" + academicYear + ", instituteName=" + instituteName
				+ ", getYearId()=" + getYearId() + ", getTechersUsingIct()=" + getTechersUsingIct()
				+ ", getTotalFaculty()=" + getTotalFaculty() + ", getAcademicYear()=" + getAcademicYear()
				+ ", getInstituteName()=" + getInstituteName() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}

	 

}
