package com.ats.rusaaccessweb.reportnew.model;

public class EContntDevFacReport {
	
	private int instEContentDevFacilityId;
	private  String eContentDevFacility;
	private String nameEcontentDevFacility;
	private String videoLink;
	private String exVar1;
	private String instituteName;
	public int getInstEContentDevFacilityId() {
		return instEContentDevFacilityId;
	}
	public void setInstEContentDevFacilityId(int instEContentDevFacilityId) {
		this.instEContentDevFacilityId = instEContentDevFacilityId;
	}
	public String geteContentDevFacility() {
		return eContentDevFacility;
	}
	public void seteContentDevFacility(String eContentDevFacility) {
		this.eContentDevFacility = eContentDevFacility;
	}
	public String getNameEcontentDevFacility() {
		return nameEcontentDevFacility;
	}
	public void setNameEcontentDevFacility(String nameEcontentDevFacility) {
		this.nameEcontentDevFacility = nameEcontentDevFacility;
	}
	public String getVideoLink() {
		return videoLink;
	}
	public void setVideoLink(String videoLink) {
		this.videoLink = videoLink;
	}
	public String getExVar1() {
		return exVar1;
	}
	public void setExVar1(String exVar1) {
		this.exVar1 = exVar1;
	}
	public String getInstituteName() {
		return instituteName;
	}
	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}
	@Override
	public String toString() {
		return "EContntDevFacReport [instEContentDevFacilityId=" + instEContentDevFacilityId + ", eContentDevFacility="
				+ eContentDevFacility + ", nameEcontentDevFacility=" + nameEcontentDevFacility + ", videoLink="
				+ videoLink + ", exVar1=" + exVar1 + ", instituteName=" + instituteName + "]";
	}
	

	
}
