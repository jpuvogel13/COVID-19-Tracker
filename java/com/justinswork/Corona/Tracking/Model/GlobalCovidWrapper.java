package com.justinswork.Corona.Tracking.Model;

import org.springframework.stereotype.Component;

@Component
public class GlobalCovidWrapper {

	private GlobalCovidData data;

	public GlobalCovidData getData() {
		return data;
	}

	public void setData(GlobalCovidData data) {
		this.data = data;
	}

	public String toString() {
		return data.toString();
	}

}
