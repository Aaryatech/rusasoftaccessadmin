package com.ats.rusaaccessweb.reportnew.model;

public class ProgSanctnIntake {
	
	private String nameOfProgram;
	private int sancIntake;
	private String programName;
	private int totalAdmitted;
	
	public String getNameOfProgram() {
		return nameOfProgram;
	}
	public int getSancIntake() {
		return sancIntake;
	}
	public String getProgramName() {
		return programName;
	}
	public int getTotalAdmitted() {
		return totalAdmitted;
	}
	public void setNameOfProgram(String nameOfProgram) {
		this.nameOfProgram = nameOfProgram;
	}
	public void setSancIntake(int sancIntake) {
		this.sancIntake = sancIntake;
	}
	public void setProgramName(String programName) {
		this.programName = programName;
	}
	public void setTotalAdmitted(int totalAdmitted) {
		this.totalAdmitted = totalAdmitted;
	}
	@Override
	public String toString() {
		return "ProgSanctnIntake [nameOfProgram=" + nameOfProgram + ", sancIntake=" + sancIntake + ", programName="
				+ programName + ", totalAdmitted=" + totalAdmitted + "]";
	}
	
}
