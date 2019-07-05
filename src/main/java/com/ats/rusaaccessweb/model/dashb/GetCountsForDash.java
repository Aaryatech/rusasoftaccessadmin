package com.ats.rusaaccessweb.model.dashb;

public class GetCountsForDash {
	
	private String id; 
	private int count1;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getCount1() {
		return count1;
	}
	public void setCount1(int count1) {
		this.count1 = count1;
	}
	@Override
	public String toString() {
		return "GetCountsForDash [id=" + id + ", count1=" + count1 + "]";
	}
	
	

}
