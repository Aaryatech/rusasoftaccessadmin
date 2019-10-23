package com.ats.rusaaccessweb.reportnew.model;

public class ExpndGreenInitveWsteMgmt {

	private int wasteMngtBudgetId;
	private int budgetAllocated ;
	private int budgetUtilized; 
	private int ttlExpnd;
	private String finYear;
	private String instituteName;
	private String academicYear;
	public int getWasteMngtBudgetId() {
		return wasteMngtBudgetId;
	}
	public void setWasteMngtBudgetId(int wasteMngtBudgetId) {
		this.wasteMngtBudgetId = wasteMngtBudgetId;
	}
	
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public int getBudgetAllocated() {
		return budgetAllocated;
	}
	public void setBudgetAllocated(int budgetAllocated) {
		this.budgetAllocated = budgetAllocated;
	}
	public int getBudgetUtilized() {
		return budgetUtilized;
	}
	public void setBudgetUtilized(int budgetUtilized) {
		this.budgetUtilized = budgetUtilized;
	}
	public int getTtlExpnd() {
		return ttlExpnd;
	}
	public void setTtlExpnd(int ttlExpnd) {
		this.ttlExpnd = ttlExpnd;
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
	@Override
	public String toString() {
		return "ExpndGreenInitveWsteMgmt [wasteMngtBudgetId=" + wasteMngtBudgetId + ", budgetAllocated="
				+ budgetAllocated + ", budgetUtilized=" + budgetUtilized + ", ttlExpnd=" + ttlExpnd + ", finYear="
				+ finYear + ", instituteName=" + instituteName + ", academicYear=" + academicYear + "]";
	}

	
}
