package com.ats.rusaaccessweb.reportnew.model;

public class FulTimFacultyWithPhd {
	
	private String uniqId;
	private String instituteName;
	private String fPassingYear;
	private int noOfPhdFac;
	public String getUniqId() {
		return uniqId;
	}
	public void setUniqId(String uniqId) {
		this.uniqId = uniqId;
	}

	public String getInstituteName() {
		return instituteName;
	}
	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}
	public String getfPassingYear() {
		return fPassingYear;
	}
	public void setfPassingYear(String fPassingYear) {
		this.fPassingYear = fPassingYear;
	}
	public int getNoOfPhdFac() {
		return noOfPhdFac;
	}
	public void setNoOfPhdFac(int noOfPhdFac) {
		this.noOfPhdFac = noOfPhdFac;
	}
	@Override
	public String toString() {
		return "FulTimFacultyWithPhd [uniqId=" + uniqId + ", instituteName=" + instituteName + ", fPassingYear="
				+ fPassingYear + ", noOfPhdFac=" + noOfPhdFac + "]";
	}
	
}
