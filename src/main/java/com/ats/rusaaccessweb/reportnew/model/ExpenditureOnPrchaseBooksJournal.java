package com.ats.rusaaccessweb.reportnew.model;

public class ExpenditureOnPrchaseBooksJournal {

	private int bookPurchaseId;
	private int costOfBooks;
	private int costOfJournal;
	private int costOfEjournal;
	private int totalExpenditures;
	private String academicYear;
	private String instituteName;
	public int getBookPurchaseId() {
		return bookPurchaseId;
	}
	public void setBookPurchaseId(int bookPurchaseId) {
		this.bookPurchaseId = bookPurchaseId;
	}
	public int getCostOfBooks() {
		return costOfBooks;
	}
	public void setCostOfBooks(int costOfBooks) {
		this.costOfBooks = costOfBooks;
	}
	public int getCostOfJournal() {
		return costOfJournal;
	}
	public void setCostOfJournal(int costOfJournal) {
		this.costOfJournal = costOfJournal;
	}
	public int getCostOfEjournal() {
		return costOfEjournal;
	}
	public void setCostOfEjournal(int costOfEjournal) {
		this.costOfEjournal = costOfEjournal;
	}
	public int getTotalExpenditures() {
		return totalExpenditures;
	}
	public void setTotalExpenditures(int totalExpenditures) {
		this.totalExpenditures = totalExpenditures;
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
		return "ExpenditureOnPrchaseBooksJournal [bookPurchaseId=" + bookPurchaseId + ", costOfBooks=" + costOfBooks
				+ ", costOfJournal=" + costOfJournal + ", costOfEjournal=" + costOfEjournal + ", totalExpenditures="
				+ totalExpenditures + ", academicYear=" + academicYear + ", instituteName=" + instituteName + "]";
	}
	
	
}
