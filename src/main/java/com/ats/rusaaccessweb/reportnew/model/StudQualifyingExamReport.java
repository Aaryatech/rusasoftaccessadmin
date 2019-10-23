package com.ats.rusaaccessweb.reportnew.model;
  
public class StudQualifyingExamReport {
	
	 
	private int studExmId;
	
	private String nameQualifExam;
	
	private String levelExam;
	
	private String noStudAppeared;
	
	private String noStudQualified;
	
	private float prcnt;
	
	private String instituteName;
	
	private String academicYear;

	public int getStudExmId() {
		return studExmId;
	}

	public void setStudExmId(int studExmId) {
		this.studExmId = studExmId;
	}

	public String getNameQualifExam() {
		return nameQualifExam;
	}

	public void setNameQualifExam(String nameQualifExam) {
		this.nameQualifExam = nameQualifExam;
	}

	public String getLevelExam() {
		return levelExam;
	}

	public void setLevelExam(String levelExam) {
		this.levelExam = levelExam;
	}

	public String getNoStudAppeared() {
		return noStudAppeared;
	}

	public void setNoStudAppeared(String noStudAppeared) {
		this.noStudAppeared = noStudAppeared;
	}

	public String getNoStudQualified() {
		return noStudQualified;
	}

	public void setNoStudQualified(String noStudQualified) {
		this.noStudQualified = noStudQualified;
	}

	public float getPrcnt() {
		return prcnt;
	}

	public void setPrcnt(float prcnt) {
		this.prcnt = prcnt;
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
		return "StudQualifyingExam [studExmId=" + studExmId + ", nameQualifExam=" + nameQualifExam + ", levelExam="
				+ levelExam + ", noStudAppeared=" + noStudAppeared + ", noStudQualified=" + noStudQualified + ", prcnt="
				+ prcnt + ", instituteName=" + instituteName + ", academicYear=" + academicYear + ", getStudExmId()="
				+ getStudExmId() + ", getNameQualifExam()=" + getNameQualifExam() + ", getLevelExam()=" + getLevelExam()
				+ ", getNoStudAppeared()=" + getNoStudAppeared() + ", getNoStudQualified()=" + getNoStudQualified()
				+ ", getPrcnt()=" + getPrcnt() + ", getInstituteName()=" + getInstituteName() + ", getAcademicYear()="
				+ getAcademicYear() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	
	
	
	
	
	
	

}
