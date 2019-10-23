package com.ats.rusaaccessweb.reportnew.model;

public class ResrchProjectGrants {

	private int projId;
	private String projName;
	private String projInvName;
	private String projInvDept;
	private String projGrant;
	private String projFrdt;
	private String projTodt;
	private String proj_sponsor;
	private String instituteName;
	public int getProjId() {
		return projId;
	}
	public String getProjName() {
		return projName;
	}
	public String getProjInvName() {
		return projInvName;
	}
	public String getProjInvDept() {
		return projInvDept;
	}
	public String getProjGrant() {
		return projGrant;
	}
	public String getProjFrdt() {
		return projFrdt;
	}
	public String getProjTodt() {
		return projTodt;
	}
	public String getProj_sponsor() {
		return proj_sponsor;
	}
	public String getInstituteName() {
		return instituteName;
	}
	public void setProjId(int projId) {
		this.projId = projId;
	}
	public void setProjName(String projName) {
		this.projName = projName;
	}
	public void setProjInvName(String projInvName) {
		this.projInvName = projInvName;
	}
	public void setProjInvDept(String projInvDept) {
		this.projInvDept = projInvDept;
	}
	public void setProjGrant(String projGrant) {
		this.projGrant = projGrant;
	}
	public void setProjFrdt(String projFrdt) {
		this.projFrdt = projFrdt;
	}
	public void setProjTodt(String projTodt) {
		this.projTodt = projTodt;
	}
	public void setProj_sponsor(String proj_sponsor) {
		this.proj_sponsor = proj_sponsor;
	}
	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}
	@Override
	public String toString() {
		return "ResrchProjectGrants [projId=" + projId + ", projName=" + projName + ", projInvName=" + projInvName
				+ ", projInvDept=" + projInvDept + ", projGrant=" + projGrant + ", projFrdt=" + projFrdt + ", projTodt="
				+ projTodt + ", proj_sponsor=" + proj_sponsor + ", instituteName=" + instituteName + "]";
	}
	
}
