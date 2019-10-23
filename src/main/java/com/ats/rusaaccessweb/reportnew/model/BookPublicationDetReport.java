package com.ats.rusaaccessweb.reportnew.model;
 
public class BookPublicationDetReport {

 	private int bookId;

	private int facultyId;

	private String bookTitle;

	private String bookPublisher;

	private String bookIsbn;

	private String bookPubYear;

	private String facultyFirstName;

	private String facultyLastName;

	private String academicYear;

	private String instituteName;

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public int getFacultyId() {
		return facultyId;
	}

	public void setFacultyId(int facultyId) {
		this.facultyId = facultyId;
	}

	public String getBookTitle() {
		return bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public String getBookPublisher() {
		return bookPublisher;
	}

	public void setBookPublisher(String bookPublisher) {
		this.bookPublisher = bookPublisher;
	}

	public String getBookIsbn() {
		return bookIsbn;
	}

	public void setBookIsbn(String bookIsbn) {
		this.bookIsbn = bookIsbn;
	}

	public String getBookPubYear() {
		return bookPubYear;
	}

	public void setBookPubYear(String bookPubYear) {
		this.bookPubYear = bookPubYear;
	}

	public String getFacultyFirstName() {
		return facultyFirstName;
	}

	public void setFacultyFirstName(String facultyFirstName) {
		this.facultyFirstName = facultyFirstName;
	}

	public String getFacultyLastName() {
		return facultyLastName;
	}

	public void setFacultyLastName(String facultyLastName) {
		this.facultyLastName = facultyLastName;
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
		return "BookPublicationDetReport [bookId=" + bookId + ", facultyId=" + facultyId + ", bookTitle=" + bookTitle
				+ ", bookPublisher=" + bookPublisher + ", bookIsbn=" + bookIsbn + ", bookPubYear=" + bookPubYear
				+ ", facultyFirstName=" + facultyFirstName + ", facultyLastName=" + facultyLastName + ", academicYear="
				+ academicYear + ", instituteName=" + instituteName + ", getBookId()=" + getBookId()
				+ ", getFacultyId()=" + getFacultyId() + ", getBookTitle()=" + getBookTitle() + ", getBookPublisher()="
				+ getBookPublisher() + ", getBookIsbn()=" + getBookIsbn() + ", getBookPubYear()=" + getBookPubYear()
				+ ", getFacultyFirstName()=" + getFacultyFirstName() + ", getFacultyLastName()=" + getFacultyLastName()
				+ ", getAcademicYear()=" + getAcademicYear() + ", getInstituteName()=" + getInstituteName()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
	
	
	

}
