package com.ats.rusaaccessweb.reportnew.model;

 
 public class CapabilityEnhancementDev {
	
	 private int sprtSchmId;
		
		private String schemeName;
		
		private String supportAgencyName;
		
		private String noStudentBenifited;

		private String academicYear;
		
		private String instituteName;

		public int getSprtSchmId() {
			return sprtSchmId;
		}

		public void setSprtSchmId(int sprtSchmId) {
			this.sprtSchmId = sprtSchmId;
		}

		public String getSchemeName() {
			return schemeName;
		}

		public void setSchemeName(String schemeName) {
			this.schemeName = schemeName;
		}

		public String getSupportAgencyName() {
			return supportAgencyName;
		}

		public void setSupportAgencyName(String supportAgencyName) {
			this.supportAgencyName = supportAgencyName;
		}

		public String getNoStudentBenifited() {
			return noStudentBenifited;
		}

		public void setNoStudentBenifited(String noStudentBenifited) {
			this.noStudentBenifited = noStudentBenifited;
		}

		public String getAcademicYear() {
			return academicYear;
		}

		public void setAcademicYear(String academicYear) {
			this.academicYear = academicYear;
		}
		
		

		public String getInstituteName() {
			return instituteName;
		}

		public void setInstituteName(String instituteName) {
			this.instituteName = instituteName;
		}

		@Override
		public String toString() {
			return "CapabilityEnhancementDev [sprtSchmId=" + sprtSchmId + ", schemeName=" + schemeName
					+ ", supportAgencyName=" + supportAgencyName + ", noStudentBenifited=" + noStudentBenifited
					+ ", academicYear=" + academicYear + ", instituteName=" + instituteName + ", getSprtSchmId()="
					+ getSprtSchmId() + ", getSchemeName()=" + getSchemeName() + ", getSupportAgencyName()="
					+ getSupportAgencyName() + ", getNoStudentBenifited()=" + getNoStudentBenifited()
					+ ", getAcademicYear()=" + getAcademicYear() + ", getClass()=" + getClass() + ", hashCode()="
					+ hashCode() + ", toString()=" + super.toString() + "]";
		}

	 
		
		
	}
