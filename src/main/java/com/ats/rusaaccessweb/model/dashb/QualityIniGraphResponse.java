package com.ats.rusaaccessweb.model.dashb;

import java.util.List;

 
public class QualityIniGraphResponse {
	
	
	List<QualityIniCountsForGraph> naacRes;
	
	List<QualityIniCountsForGraph> nbaRes;
	
	List<QualityIniCountsForGraph> nirfcRes;
	
	List<QualityIniCountsForGraph> theRes;
	
	List<QualityIniCountsForGraph> autonomousRes;

	public List<QualityIniCountsForGraph> getNaacRes() {
		return naacRes;
	}

	public void setNaacRes(List<QualityIniCountsForGraph> naacRes) {
		this.naacRes = naacRes;
	}

	public List<QualityIniCountsForGraph> getNbaRes() {
		return nbaRes;
	}

	public void setNbaRes(List<QualityIniCountsForGraph> nbaRes) {
		this.nbaRes = nbaRes;
	}

	public List<QualityIniCountsForGraph> getNirfcRes() {
		return nirfcRes;
	}

	public void setNirfcRes(List<QualityIniCountsForGraph> nirfcRes) {
		this.nirfcRes = nirfcRes;
	}

	public List<QualityIniCountsForGraph> getTheRes() {
		return theRes;
	}

	public void setTheRes(List<QualityIniCountsForGraph> theRes) {
		this.theRes = theRes;
	}
	
	

	public List<QualityIniCountsForGraph> getAutonomousRes() {
		return autonomousRes;
	}

	public void setAutonomousRes(List<QualityIniCountsForGraph> autonomousRes) {
		this.autonomousRes = autonomousRes;
	}

	@Override
	public String toString() {
		return "QualityIniGraphResponse [naacRes=" + naacRes + ", nbaRes=" + nbaRes + ", nirfcRes=" + nirfcRes
				+ ", theRes=" + theRes + ", autonomousRes=" + autonomousRes + "]";
	}

	 

	 
	
	 

}
