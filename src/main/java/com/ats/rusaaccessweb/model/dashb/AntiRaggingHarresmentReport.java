package com.ats.rusaaccessweb.model.dashb;
 
public class AntiRaggingHarresmentReport {

 	private int instituteId;

	private String instituteName;

	private String academicYear;

	private String sexualHarashCommitee;

	private String raggingCommitee;

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

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	public String getSexualHarashCommitee() {
		return sexualHarashCommitee;
	}

	public void setSexualHarashCommitee(String sexualHarashCommitee) {
		this.sexualHarashCommitee = sexualHarashCommitee;
	}

	public String getRaggingCommitee() {
		return raggingCommitee;
	}

	public void setRaggingCommitee(String raggingCommitee) {
		this.raggingCommitee = raggingCommitee;
	}

	@Override
	public String toString() {
		return "AntiRaggingHarresmentReport [instituteId=" + instituteId + ", instituteName=" + instituteName
				+ ", academicYear=" + academicYear + ", sexualHarashCommitee=" + sexualHarashCommitee
				+ ", raggingCommitee=" + raggingCommitee + "]";
	}
	
	

}
