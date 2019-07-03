package com.ats.rusaaccessweb.model;


public class Institute {

	
	private int instituteId;

	private String instituteName;
	private String aisheCode;

	private int isRegistration;
	
	private String regDate;
	private String instituteAdd;
	private String village;
	private String taluka;
	private String district;
	private String state;
	private String pincode;

	private String trustName;
	private String trustAdd;
	private String trustContactNo;
	private String presidentName;
	private String presidenContact;
	private String presidentEmail;
	
	public String getPresidenContact() {
		return presidenContact;
	}

	public void setPresidenContact(String presidenContact) {
		this.presidenContact = presidenContact;
	}

	public String getPresidentEmail() {
		return presidentEmail;
	}

	public void setPresidentEmail(String presidentEmail) {
		this.presidentEmail = presidentEmail;
	}

	private String principalName;
	private String contactNo;
	private String email;
	
	private int delStatus;
	private int isActive;
	private int userType;
	private int isEnrollSystem;
	private int makerUserId;

	private String makerEnterDatetime;

	private int checkerUserId;
	
	private String checkerDatetime;
	private String lastUpdatedDatetime;

	// extra 4 columns
	private int exInt1;
	private int exInt2;

	private String exVar1;
	private String exVar2;

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

	public String getAisheCode() {
		return aisheCode;
	}

	public void setAisheCode(String aisheCode) {
		this.aisheCode = aisheCode;
	}

	public int getIsRegistration() {
		return isRegistration;
	}

	public void setIsRegistration(int isRegistration) {
		this.isRegistration = isRegistration;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getInstituteAdd() {
		return instituteAdd;
	}

	public void setInstituteAdd(String instituteAdd) {
		this.instituteAdd = instituteAdd;
	}

	public String getTrustName() {
		return trustName;
	}

	public void setTrustName(String trustName) {
		this.trustName = trustName;
	}

	public String getTrustAdd() {
		return trustAdd;
	}

	public void setTrustAdd(String trustAdd) {
		this.trustAdd = trustAdd;
	}

	public String getTrustContactNo() {
		return trustContactNo;
	}

	public void setTrustContactNo(String trustContactNo) {
		this.trustContactNo = trustContactNo;
	}

	public String getPresidentName() {
		return presidentName;
	}

	public void setPresidentName(String presidentName) {
		this.presidentName = presidentName;
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

	public int getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public int getIsEnrollSystem() {
		return isEnrollSystem;
	}

	public void setIsEnrollSystem(int isEnrollSystem) {
		this.isEnrollSystem = isEnrollSystem;
	}

	public int getMakerUserId() {
		return makerUserId;
	}

	public void setMakerUserId(int makerUserId) {
		this.makerUserId = makerUserId;
	}

	public String getMakerEnterDatetime() {
		return makerEnterDatetime;
	}

	public void setMakerEnterDatetime(String makerEnterDatetime) {
		this.makerEnterDatetime = makerEnterDatetime;
	}

	public int getCheckerUserId() {
		return checkerUserId;
	}

	public void setCheckerUserId(int checkerUserId) {
		this.checkerUserId = checkerUserId;
	}

	public String getCheckerDatetime() {
		return checkerDatetime;
	}

	public void setCheckerDatetime(String checkerDatetime) {
		this.checkerDatetime = checkerDatetime;
	}

	public String getLastUpdatedDatetime() {
		return lastUpdatedDatetime;
	}

	public void setLastUpdatedDatetime(String lastUpdatedDatetime) {
		this.lastUpdatedDatetime = lastUpdatedDatetime;
	}

	public int getExInt1() {
		return exInt1;
	}

	public void setExInt1(int exInt1) {
		this.exInt1 = exInt1;
	}

	public int getExInt2() {
		return exInt2;
	}

	public void setExInt2(int exInt2) {
		this.exInt2 = exInt2;
	}

	public String getVillage() {
		return village;
	}

	public void setVillage(String village) {
		this.village = village;
	}

	public String getTaluka() {
		return taluka;
	}

	public void setTaluka(String taluka) {
		this.taluka = taluka;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getExVar1() {
		return exVar1;
	}

	public void setExVar1(String exVar1) {
		this.exVar1 = exVar1;
	}

	public String getExVar2() {
		return exVar2;
	}

	public void setExVar2(String exVar2) {
		this.exVar2 = exVar2;
	}

	@Override
	public String toString() {
		return "Institute [instituteId=" + instituteId + ", instituteName=" + instituteName + ", aisheCode=" + aisheCode
				+ ", isRegistration=" + isRegistration + ", regDate=" + regDate + ", instituteAdd=" + instituteAdd
				+ ", village=" + village + ", taluka=" + taluka + ", district=" + district + ", state=" + state
				+ ", pincode=" + pincode + ", trustName=" + trustName + ", trustAdd=" + trustAdd + ", trustContactNo="
				+ trustContactNo + ", presidentName=" + presidentName + ", presidenContact=" + presidenContact
				+ ", presidentEmail=" + presidentEmail + ", principalName=" + principalName + ", contactNo=" + contactNo
				+ ", email=" + email + ", delStatus=" + delStatus + ", isActive=" + isActive + ", userType=" + userType
				+ ", isEnrollSystem=" + isEnrollSystem + ", makerUserId=" + makerUserId + ", makerEnterDatetime="
				+ makerEnterDatetime + ", checkerUserId=" + checkerUserId + ", checkerDatetime=" + checkerDatetime
				+ ", lastUpdatedDatetime=" + lastUpdatedDatetime + ", exInt1=" + exInt1 + ", exInt2=" + exInt2
				+ ", exVar1=" + exVar1 + ", exVar2=" + exVar2 + "]";
	}


	
}
