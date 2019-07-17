package com.ats.rusaaccessweb.model;

 
public class GetChangePrincipalDetails {
	
 	private int facultyId;
	
	private String facultyFirstName;
	
	private String facultyLastName;
	
	private String contactNo;
	
	private String email;
	
	private int instituteId;
	
	private String instituteName;

	public int getFacultyId() {
		return facultyId;
	}

	public void setFacultyId(int facultyId) {
		this.facultyId = facultyId;
	}

	public String getFacultyFirstName() {
		return facultyFirstName;
	}

	public void setFacultyFirstName(String facultyFirstName) {
		this.facultyFirstName = facultyFirstName;
	}

	public String getFacultyLastName() {
		return facultyLastName;
	}

	public void setFacultyLastName(String facultyLastName) {
		this.facultyLastName = facultyLastName;
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

	@Override
	public String toString() {
		return "GetChangePrincipalDetails [facultyId=" + facultyId + ", facultyFirstName=" + facultyFirstName
				+ ", facultyLastName=" + facultyLastName + ", contactNo=" + contactNo + ", email=" + email
				+ ", instituteId=" + instituteId + ", instituteName=" + instituteName + ", getFacultyId()="
				+ getFacultyId() + ", getFacultyFirstName()=" + getFacultyFirstName() + ", getFacultyLastName()="
				+ getFacultyLastName() + ", getContactNo()=" + getContactNo() + ", getEmail()=" + getEmail()
				+ ", getInstituteId()=" + getInstituteId() + ", getInstituteName()=" + getInstituteName()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
	

}
