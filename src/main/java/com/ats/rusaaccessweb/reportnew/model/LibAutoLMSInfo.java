package com.ats.rusaaccessweb.reportnew.model;

 
public class LibAutoLMSInfo {
	
	 
	private int libInfoId;
	
	private int instituteId;
	
	private String softName;
	
	private String instituteName;
	
	private String softVersion;
	
	private String usersOfLms;
	
	private String year;

	public int getLibInfoId() {
		return libInfoId;
	}

	public void setLibInfoId(int libInfoId) {
		this.libInfoId = libInfoId;
	}

	public int getInstituteId() {
		return instituteId;
	}

	public void setInstituteId(int instituteId) {
		this.instituteId = instituteId;
	}

	public String getSoftName() {
		return softName;
	}

	public void setSoftName(String softName) {
		this.softName = softName;
	}

	public String getInstituteName() {
		return instituteName;
	}

	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}

	public String getSoftVersion() {
		return softVersion;
	}

	public void setSoftVersion(String softVersion) {
		this.softVersion = softVersion;
	}

	public String getUsersOfLms() {
		return usersOfLms;
	}

	public void setUsersOfLms(String usersOfLms) {
		this.usersOfLms = usersOfLms;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@Override
	public String toString() {
		return "LibAutoLMSInfo [libInfoId=" + libInfoId + ", instituteId=" + instituteId + ", softName=" + softName
				+ ", instituteName=" + instituteName + ", softVersion=" + softVersion + ", usersOfLms=" + usersOfLms
				+ ", year=" + year + ", getLibInfoId()=" + getLibInfoId() + ", getInstituteId()=" + getInstituteId()
				+ ", getSoftName()=" + getSoftName() + ", getInstituteName()=" + getInstituteName()
				+ ", getSoftVersion()=" + getSoftVersion() + ", getUsersOfLms()=" + getUsersOfLms() + ", getYear()="
				+ getYear() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	
	

}
