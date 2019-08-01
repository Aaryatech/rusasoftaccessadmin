package com.ats.rusaaccessweb.model.dashb;

public class GetInstInfoCount {

	private String id;
	private int govt;
	private int aided;
	private int unaided;
	private int autonmous;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getGovt() {
		return govt;
	}
	public void setGovt(int govt) {
		this.govt = govt;
	}
	public int getAided() {
		return aided;
	}
	public void setAided(int aided) {
		this.aided = aided;
	}
	public int getUnaided() {
		return unaided;
	}
	public void setUnaided(int unaided) {
		this.unaided = unaided;
	}
	public int getAutonmous() {
		return autonmous;
	}
	public void setAutonmous(int autonmous) {
		this.autonmous = autonmous;
	}
	@Override
	public String toString() {
		return "GetInstInfoCount [id=" + id + ", govt=" + govt + ", aided=" + aided + ", unaided=" + unaided
				+ ", autonmous=" + autonmous + "]";
	}
	
}
