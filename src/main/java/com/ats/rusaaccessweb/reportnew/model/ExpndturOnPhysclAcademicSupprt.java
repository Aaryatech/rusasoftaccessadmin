package com.ats.rusaaccessweb.reportnew.model;

public class ExpndturOnPhysclAcademicSupprt {

	private String unqId;
	private int expdOnPhyAcad;
	private int ttlExpd;
	private String finYear;
	private String instituteName;
	
	private int budgetAllocated;
	
	
	public String getUnqId() {
		return unqId;
	}
	public void setUnqId(String unqId) {
		this.unqId = unqId;
	}
	public int getExpdOnPhyAcad() {
		return expdOnPhyAcad;
	}
	public void setExpdOnPhyAcad(int expdOnPhyAcad) {
		this.expdOnPhyAcad = expdOnPhyAcad;
	}
	public int getTtlExpd() {
		return ttlExpd;
	}
	public void setTtlExpd(int ttlExpd) {
		this.ttlExpd = ttlExpd;
	}
	public String getFinYear() {
		return finYear;
	}
	public void setFinYear(String finYear) {
		this.finYear = finYear;
	}
	public String getInstituteName() {
		return instituteName;
	}
	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}


	public int getBudgetAllocated() {
		return budgetAllocated;
	}
	public void setBudgetAllocated(int budgetAllocated) {
		this.budgetAllocated = budgetAllocated;
	}
	
	@Override
	public String toString() {
		return "ExpndturOnPhysclAcademicSupprt [unqId=" + unqId + ", expdOnPhyAcad=" + expdOnPhyAcad + ", ttlExpd="
				+ ttlExpd + ", finYear=" + finYear + ", instituteName=" + instituteName + ", budgetAllocated="
				+ budgetAllocated + "]";
	}
	
}
