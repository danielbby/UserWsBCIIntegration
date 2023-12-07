package com.bci.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Phone {


	@JsonProperty("number")
	private String number;
	
	@JsonProperty("citycode")
	private String citycode;
	
	@JsonProperty("contrycode")
	private String countrycode;
	
	/**
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}
	/**
	 * @param number the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}
	/**
	 * @return the cityCode
	 */
	public String getCityCode() {
		return citycode;
	}
	/**
	 * @param cityCode the cityCode to set
	 */
	public void setCityCode(String cityCode) {
		this.citycode = cityCode;
	}
	/**
	 * @return the countrycode
	 */
	public String getCountryCode() {
		return countrycode;
	}
	/**
	 * @param countryCode the countryCode to set
	 */
	public void setCountryCode(String countrycode) {
		this.countrycode = countrycode;
	}
	
}
