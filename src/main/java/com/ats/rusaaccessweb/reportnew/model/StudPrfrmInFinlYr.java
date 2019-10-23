package com.ats.rusaaccessweb.reportnew.model;

public class StudPrfrmInFinlYr {

	private int studPerformId;
	private int noStudAppear;
	private int noStudPass;
	private int passingPer;
	private String nameOfProgram;
	private String programName;
	private String instituteName;
	private String acadYear;
	
	
	public String getAcadYear() {
		return acadYear;
	}
	public void setAcadYear(String acadYear) {
		this.acadYear = acadYear;
	}
	public int getStudPerformId() {
		return studPerformId;
	}
	public void setStudPerformId(int studPerformId) {
		this.studPerformId = studPerformId;
	}
	public int getNoStudPass() {
		return noStudPass;
	}
	
	public int getNoStudAppear() {
		return noStudAppear;
	}
	public void setNoStudAppear(int noStudAppear) {
		this.noStudAppear = noStudAppear;
	}
	public void setNoStudPass(int noStudPass) {
		this.noStudPass = noStudPass;
	}
	public int getPassingPer() {
		return passingPer;
	}
	public void setPassingPer(int passingPer) {
		this.passingPer = passingPer;
	}
	public String getNameOfProgram() {
		return nameOfProgram;
	}
	public void setNameOfProgram(String nameOfProgram) {
		this.nameOfProgram = nameOfProgram;
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
	@Override
	public String toString() {
		return "StudPrfrmInFinlYr [studPerformId=" + studPerformId + ", noStudAppear=" + noStudAppear + ", noStudPass="
				+ noStudPass + ", passingPer=" + passingPer + ", nameOfProgram=" + nameOfProgram + ", programName="
				+ programName + ", instituteName=" + instituteName + ", acadYear=" + acadYear + "]";
	}
	
	
}
