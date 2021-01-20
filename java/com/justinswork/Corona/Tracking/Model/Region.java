package com.justinswork.Corona.Tracking.Model;

import org.springframework.stereotype.Component;


@Component
public class Region {

	private String iso;
	private String name;
	private String province;
	private String lat;


	public String getIso() {
		return iso;
	}



	public String getName() {
		return name;
	}



	public String getProvince() {
		return province;
	}



	public String getLat() {
		return lat;
	}



	public void setIso(String iso) {
		this.iso = iso;
	}



	public void setName(String name) {
		this.name = name;
	}



	public void setProvince(String province) {
		this.province = province;
	}



	public void setLat(String lat) {
		this.lat = lat;
	}	

}
