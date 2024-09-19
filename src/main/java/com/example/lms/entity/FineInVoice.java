package com.example.lms.entity;

public class FineInVoice {

	public String vendorName;
	
	public String fineAmonut;

	public FineInVoice() {}
	
	public FineInVoice(String vendorName, String fineAmonut) {
		super();
		this.vendorName = vendorName;
		this.fineAmonut = fineAmonut;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getFineAmonut() {
		return fineAmonut;
	}

	public void setFineAmonut(String fineAmonut) {
		this.fineAmonut = fineAmonut;
	}
	
	
	
}
