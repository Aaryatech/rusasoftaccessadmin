package com.ats.rusaaccessweb.reportnew.model;

import com.fasterxml.jackson.annotation.JsonFormat;

public class FunctionalMou {

	private int instReasearchDevMouId;
	private String orgName;
	private String durFromdt;
	private String durTodt;
	private int no_benif;
	private String academicYear;
	private String instituteName;
	public int getInstReasearchDevMouId() {
		return instReasearchDevMouId;
	}
	public void setInstReasearchDevMouId(int instReasearchDevMouId) {
		this.instReasearchDevMouId = instReasearchDevMouId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	@JsonFormat(locale = "hi",timezone = "Asia/Kolkata", pattern = "dd-MM-yyyy")
	public String getDurFromdt() {
		return durFromdt;
	}
	public void setDurFromdt(String durFromdt) {
		this.durFromdt = durFromdt;
	}
	@JsonFormat(locale = "hi",timezone = "Asia/Kolkata", pattern = "dd-MM-yyyy")
	public String getDurTodt() {
		return durTodt;
	}
	public void setDurTodt(String durTodt) {
		this.durTodt = durTodt;
	}
	public int getNo_benif() {
		return no_benif;
	}
	public void setNo_benif(int no_benif) {
		this.no_benif = no_benif;
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
		return "FunctionalMou [instReasearchDevMouId=" + instReasearchDevMouId + ", orgName=" + orgName + ", durFromdt="
				+ durFromdt + ", durTodt=" + durTodt + ", no_benif=" + no_benif + ", academicYear=" + academicYear
				+ ", instituteName=" + instituteName + "]";
	}
	
	
}
