package com.ats.rusaaccessweb.model.dashb;

 
public class QualityIniCountsForGraph {
	
 	private String yearId;
	
	private String academicYear;
	
	private int count1;
	
	private int count2;
	
	private int count3;

	public String getYearId() {
		return yearId;
	}

	public void setYearId(String yearId) {
		this.yearId = yearId;
	}

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	public int getCount1() {
		return count1;
	}

	public void setCount1(int count1) {
		this.count1 = count1;
	}

	public int getCount2() {
		return count2;
	}

	public void setCount2(int count2) {
		this.count2 = count2;
	}

	public int getCount3() {
		return count3;
	}

	public void setCount3(int count3) {
		this.count3 = count3;
	}

	@Override
	public String toString() {
		return "QualityIniCountsForGraph [yearId=" + yearId + ", academicYear=" + academicYear + ", count1=" + count1
				+ ", count2=" + count2 + ", count3=" + count3 + "]";
	}


}
