package com.ats.rusaaccessweb.model.dashb;


public class MaleFemaleRatioResponse {
	
	private int instituteId;
	
	private int maleFaculty;
	
	private int femaleFaculty;
	
	private int transFaculty;
	
	private int maleStudent;
	
	private int femaleStudent;
	
	private int transStudent;
	
	private String  instituteName;

	public int getInstituteId() {
		return instituteId;
	}

	public void setInstituteId(int instituteId) {
		this.instituteId = instituteId;
	}

	public int getMaleFaculty() {
		return maleFaculty;
	}

	public void setMaleFaculty(int maleFaculty) {
		this.maleFaculty = maleFaculty;
	}

	public int getFemaleFaculty() {
		return femaleFaculty;
	}

	public void setFemaleFaculty(int femaleFaculty) {
		this.femaleFaculty = femaleFaculty;
	}

	public int getTransFaculty() {
		return transFaculty;
	}

	public void setTransFaculty(int transFaculty) {
		this.transFaculty = transFaculty;
	}

	public int getMaleStudent() {
		return maleStudent;
	}

	public void setMaleStudent(int maleStudent) {
		this.maleStudent = maleStudent;
	}

	public int getFemaleStudent() {
		return femaleStudent;
	}

	public void setFemaleStudent(int femaleStudent) {
		this.femaleStudent = femaleStudent;
	}

	public int getTransStudent() {
		return transStudent;
	}

	public void setTransStudent(int transStudent) {
		this.transStudent = transStudent;
	}

	public String getInstituteName() {
		return instituteName;
	}

	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}

	@Override
	public String toString() {
		return "MaleFemaleRatioResponse [instituteId=" + instituteId + ", maleFaculty=" + maleFaculty
				+ ", femaleFaculty=" + femaleFaculty + ", transFaculty=" + transFaculty + ", maleStudent=" + maleStudent
				+ ", femaleStudent=" + femaleStudent + ", transStudent=" + transStudent + ", instituteName="
				+ instituteName + "]";
	}
	
	

}
