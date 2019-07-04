package com.ats.rusaaccessweb.model.dashb;
 
public class AcademicYear {
	 
	private int yearId;

	private String academicYear;
	
	private int makerId;
	
	private String makerDatetime;
	
	private int type;
	
	private int delStatus;
	
	private int isClosed;
	
	private int isCurrent;
	
	
	
	

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

	public int getMakerId() {
		return makerId;
	}

	public void setMakerId(int makerId) {
		this.makerId = makerId;
	}

	public String getMakerDatetime() {
		return makerDatetime;
	}

	public void setMakerDatetime(String makerDatetime) {
		this.makerDatetime = makerDatetime;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}

	
	
	public int getIsClosed() {
		return isClosed;
	}

	public void setIsClosed(int isClosed) {
		this.isClosed = isClosed;
	}

	public int getIsCurrent() {
		return isCurrent;
	}

	public void setIsCurrent(int isCurrent) {
		this.isCurrent = isCurrent;
	}

	@Override
	public String toString() {
		return "AcademicYear [yearId=" + yearId + ", academicYear=" + academicYear + ", makerId=" + makerId
				+ ", makerDatetime=" + makerDatetime + ", type=" + type + ", delStatus=" + delStatus + ", isClosed="
				+ isClosed + ", isCurrent=" + isCurrent + "]";
	}

	
	
	
	
	
}
