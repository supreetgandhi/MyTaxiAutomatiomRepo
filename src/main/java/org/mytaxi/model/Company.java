package org.mytaxi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Company {

	private String name;
	private String catchphrase;
	private String bs;
	
	public Company() {
		super();
	}

	public Company(String name, String catchphrase, String bs) {
		super();
		this.name = name;
		this.catchphrase = catchphrase;
		this.bs = bs;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	  public String getCatchphrase() { return catchphrase; }
	  
	  public void setCatchphrase(String catchphrase) { this.catchphrase =
	  catchphrase; }
	 

	public String getBs() {
		return bs;
	}

	public void setBs(String bs) {
		this.bs = bs;
	}
	
}
