package com.ats.rusaaccessweb.reportnew.model;

public class FullTimeTechrInstResrchGuide {

	private String uniqId;
	private float noResearchGuide;
	private float fullTimeTeacher;
	private String academicYear;
	private String instituteName;
	public String getUniqId() {
		return uniqId;
	}
	public float getNoResearchGuide() {
		return noResearchGuide;
	}
	public float getFullTimeTeacher() {
		return fullTimeTeacher;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public String getInstituteName() {
		return instituteName;
	}
	public void setUniqId(String uniqId) {
		this.uniqId = uniqId;
	}
	public void setNoResearchGuide(float noResearchGuide) {
		this.noResearchGuide = noResearchGuide;
	}
	public void setFullTimeTeacher(float fullTimeTeacher) {
		this.fullTimeTeacher = fullTimeTeacher;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}
	@Override
	public String toString() {
		return "FullTimeTechrInstResrchGuide [uniqId=" + uniqId + ", noResearchGuide=" + noResearchGuide
				+ ", fullTimeTeacher=" + fullTimeTeacher + ", academicYear=" + academicYear + ", instituteName="
				+ instituteName + "]";
	}
	
	
}
