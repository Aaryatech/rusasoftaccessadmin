package com.ats.rusaaccessweb.reportnew.model;

public class TeacExpFullTimFac {

	private int facultyId;
	private String facultyFirstName;
	private String fPan;
	private String designationName;
	private String deptName;
	private String instituteName;
	private String curExp;
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
	public String getfPan() {
		return fPan;
	}
	public void setfPan(String fPan) {
		this.fPan = fPan;
	}
	public String getDesignationName() {
		return designationName;
	}
	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getInstituteName() {
		return instituteName;
	}
	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}
	public String getCurExp() {
		return curExp;
	}
	public void setCurExp(String curExp) {
		this.curExp = curExp;
	}
	@Override
	public String toString() {
		return "TeacExpFullTimFac [facultyId=" + facultyId + ", facultyFirstName=" + facultyFirstName + ", fPan=" + fPan
				+ ", designationName=" + designationName + ", deptName=" + deptName + ", instituteName=" + instituteName
				+ ", curExp=" + curExp + "]";
	}
	
	
}
