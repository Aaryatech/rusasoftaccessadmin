package com.ats.rusaaccessweb.model;

public class GetInstituteList {
	
	private int instituteId;

	private String instituteName;
	
	private String principalName;
	private String contactNo;
	private String makerEnterDatetime;
	private String email;
	public int getInstituteId() {
		return instituteId;
	}
	public void setInstituteId(int instituteId) {
		this.instituteId = instituteId;
	}
	public String getInstituteName() {
		return instituteName;
	}
	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}
	public String getPrincipalName() {
		return principalName;
	}
	public void setPrincipalName(String principalName) {
		this.principalName = principalName;
	}
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getMakerEnterDatetime() {
		return makerEnterDatetime;
	}
	public void setMakerEnterDatetime(String makerEnterDatetime) {
		this.makerEnterDatetime = makerEnterDatetime;
	}
	@Override
	public String toString() {
		return "GetInstituteList [instituteId=" + instituteId + ", instituteName=" + instituteName + ", principalName="
				+ principalName + ", contactNo=" + contactNo + ", makerEnterDatetime=" + makerEnterDatetime + ", email="
				+ email + "]";
	}
	
}
