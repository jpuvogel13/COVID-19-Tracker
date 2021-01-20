package com.justinswork.Corona.Tracking.Model;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class CovidWrapper {

	private  List<CovidData> data;


	public List<CovidData> getData() {
		return data;
	}

	public void setData(List<CovidData> data) {
		this.data = data;
	}

	public String toString() {
		return data.toString();
	}


}
