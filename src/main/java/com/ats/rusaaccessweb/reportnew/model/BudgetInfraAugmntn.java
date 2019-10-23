package com.ats.rusaaccessweb.reportnew.model;

public class BudgetInfraAugmntn {

	private int infraBudgetId;
	private String budgetAllocated;
	private String budgetUtilized;
	private int exInt1;
	private int finYearId;
	private int acYearId;
	private String instituteName;	
	private String finYear;
	private String academicYear;
	public int getInfraBudgetId() {
		return infraBudgetId;
	}
	public void setInfraBudgetId(int infraBudgetId) {
		this.infraBudgetId = infraBudgetId;
	}
	public String getBudgetAllocated() {
		return budgetAllocated;
	}
	public void setBudgetAllocated(String budgetAllocated) {
		this.budgetAllocated = budgetAllocated;
	}
	public String getBudgetUtilized() {
		return budgetUtilized;
	}
	public void setBudgetUtilized(String budgetUtilized) {
		this.budgetUtilized = budgetUtilized;
	}
	public int getExInt1() {
		return exInt1;
	}
	public void setExInt1(int exInt1) {
		this.exInt1 = exInt1;
	}
	public int getFinYearId() {
		return finYearId;
	}
	public void setFinYearId(int finYearId) {
		this.finYearId = finYearId;
	}
	public int getAcYearId() {
		return acYearId;
	}
	public void setAcYearId(int acYearId) {
		this.acYearId = acYearId;
	}
	public String getInstituteName() {
		return instituteName;
	}
	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}
	public String getFinYear() {
		return finYear;
	}
	public void setFinYear(String finYear) {
		this.finYear = finYear;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	@Override
	public String toString() {
		return "BudgetInfraAugmntn [infraBudgetId=" + infraBudgetId + ", budgetAllocated=" + budgetAllocated
				+ ", budgetUtilized=" + budgetUtilized + ", exInt1=" + exInt1 + ", finYearId=" + finYearId
				+ ", acYearId=" + acYearId + ", instituteName=" + instituteName + ", finYear=" + finYear
				+ ", academicYear=" + academicYear + "]";
	}
	
	
}
