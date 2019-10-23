package com.ats.rusaaccessweb.reportnew.model;

public class PerProgCbseElectiveCourse {

	private String uid;
	private float count1;
	private String instituteName;
	public String getUid() {
		return uid;
	}
	public float getCount1() {
		return count1;
	}
	public String getInstituteName() {
		return instituteName;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public void setCount1(float count1) {
		this.count1 = count1;
	}
	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}
	@Override
	public String toString() {
		return "PerProgCbseElecticwCourse [uid=" + uid + ", count1=" + count1 + ", instituteName=" + instituteName
				+ "]";
	}
		
}
