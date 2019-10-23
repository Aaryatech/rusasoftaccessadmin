package com.ats.rusaaccessweb.reportnew.model;

 
public class RareBookManuscriptSpec {

	 
private int rareBookInfoId;
	
	private String rareBookname;
	
	private String publisher;
	
	private String author;
	
	private String bookCopies;
	
	private String publicationYear;
	
	private String instituteName;

	public int getRareBookInfoId() {
		return rareBookInfoId;
	}

	public void setRareBookInfoId(int rareBookInfoId) {
		this.rareBookInfoId = rareBookInfoId;
	}

	public String getRareBookname() {
		return rareBookname;
	}

	public void setRareBookname(String rareBookname) {
		this.rareBookname = rareBookname;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getBookCopies() {
		return bookCopies;
	}

	public void setBookCopies(String bookCopies) {
		this.bookCopies = bookCopies;
	}

	public String getPublicatioYear() {
		return publicationYear;
	}

	public void setPublicatioYear(String publicatioYear) {
		this.publicationYear = publicatioYear;
	}

	public String getInstituteName() {
		return instituteName;
	}

	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}

	@Override
	public String toString() {
		return "RareBookManuscriptSpec [rareBookInfoId=" + rareBookInfoId + ", rareBookname=" + rareBookname
				+ ", publisher=" + publisher + ", author=" + author + ", bookCopies=" + bookCopies + ", publicatioYear="
				+ publicationYear + ", instituteName=" + instituteName + ", getRareBookInfoId()=" + getRareBookInfoId()
				+ ", getRareBookname()=" + getRareBookname() + ", getPublisher()=" + getPublisher() + ", getAuthor()="
				+ getAuthor() + ", getBookCopies()=" + getBookCopies() + ", getPublicatioYear()=" + getPublicatioYear()
				+ ", getInstituteName()=" + getInstituteName() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	
}
