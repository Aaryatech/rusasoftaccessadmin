package com.ats.rusaaccessweb.reportnew.model;
 
 
 public class GetAluminiEngagementReport {
	
  	private int almAssocActId;
	
	private String nameAlumniAssoc;
	
	private String  alumniRegNo;
	
	private String  estYear ;
	
	private String  instituteName;

	public int getAlmAssocActId() {
		return almAssocActId;
	}

	public void setAlmAssocActId(int almAssocActId) {
		this.almAssocActId = almAssocActId;
	}

	public String getNameAlumniAssoc() {
		return nameAlumniAssoc;
	}

	public void setNameAlumniAssoc(String nameAlumniAssoc) {
		this.nameAlumniAssoc = nameAlumniAssoc;
	}

	public String getAlumniRegNo() {
		return alumniRegNo;
	}

	public void setAlumniRegNo(String alumniRegNo) {
		this.alumniRegNo = alumniRegNo;
	}

	public String getEstYear() {
		return estYear;
	}

	public void setEstYear(String estYear) {
		this.estYear = estYear;
	}

	public String getInstituteName() {
		return instituteName;
	}

	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}

	@Override
	public String toString() {
		return "GetAluminiEngagementReport [almAssocActId=" + almAssocActId + ", nameAlumniAssoc=" + nameAlumniAssoc
				+ ", alumniRegNo=" + alumniRegNo + ", estYear=" + estYear + ", instituteName=" + instituteName
				+ ", getAlmAssocActId()=" + getAlmAssocActId() + ", getNameAlumniAssoc()=" + getNameAlumniAssoc()
				+ ", getAlumniRegNo()=" + getAlumniRegNo() + ", getEstYear()=" + getEstYear() + ", getInstituteName()="
				+ getInstituteName() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	
	
	
	

}
