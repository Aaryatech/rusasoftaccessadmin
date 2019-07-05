package com.ats.rusaaccessweb.model;

public class CurricularActivityCnts {
	
	private String id;
	private int instituteId;
	private String instituteName;
	private int curriCnt;
	private int coCurryCnt;
	private int extraCoCarryCnt;
	public String getId() {
		return id;
	}
	public int getInstituteId() {
		return instituteId;
	}
	public String getInstituteName() {
		return instituteName;
	}
	public int getCurriCnt() {
		return curriCnt;
	}
	public int getCoCurryCnt() {
		return coCurryCnt;
	}
	public int getExtraCoCarryCnt() {
		return extraCoCarryCnt;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setInstituteId(int instituteId) {
		this.instituteId = instituteId;
	}
	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}
	public void setCurriCnt(int curriCnt) {
		this.curriCnt = curriCnt;
	}
	public void setCoCurryCnt(int coCurryCnt) {
		this.coCurryCnt = coCurryCnt;
	}
	public void setExtraCoCarryCnt(int extraCoCarryCnt) {
		this.extraCoCarryCnt = extraCoCarryCnt;
	}
	@Override
	public String toString() {
		return "CurricularActivityCnts [id=" + id + ", instituteId=" + instituteId + ", instituteName=" + instituteName
				+ ", curriCnt=" + curriCnt + ", coCurryCnt=" + coCurryCnt + ", extraCoCarryCnt=" + extraCoCarryCnt
				+ "]";
	}
	
	

}
