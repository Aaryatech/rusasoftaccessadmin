package com.ats.rusaaccessweb.model;

public class ProgramType {
	
	private int programId;
	
	private String programName; 
	
	private int delStatus; 	
	private String sequence;
	
	private int isActive;

	public int getProgramId() {
		return programId;
	}

	public void setProgramId(int programId) {
		this.programId = programId;
	}

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public int getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return "ProgramType [programId=" + programId + ", programName=" + programName + ", delStatus=" + delStatus
				+ ", sequence=" + sequence + ", isActive=" + isActive + "]";
	} 	


}
