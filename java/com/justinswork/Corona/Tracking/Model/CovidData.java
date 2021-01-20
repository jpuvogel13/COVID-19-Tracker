package com.justinswork.Corona.Tracking.Model;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import java.util.Locale;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;



@Component
public class CovidData  implements Comparable<CovidData>{

	private int confirmed;
	private int deaths;
	private int recovered;
	private int active;
	private Region region;
	@JsonProperty("confirmed_diff")
	private int confirmedDiff;
	@JsonProperty("deaths_diff")
	private int deathsDiff;
	@JsonProperty("recovered_diff")
	private int recoveredDiff;
	@JsonProperty("active_diff")
	private int activeDiff;
	@JsonProperty("fatality_rate")
	private double fatalityRate;

	public int getConfirmedDiff() {
		return confirmedDiff;
	}

	public int getDeathsDiff() {
		return deathsDiff;
	}

	public int getRecoveredDiff() {
		return recoveredDiff;
	}

	public int getActiveDiff() {
		return activeDiff;
	}

	public double getFatalityRate() {
		return fatalityRate;
	}

	public void setConfirmedDiff(int confirmedDiff) {
		this.confirmedDiff = confirmedDiff;
	}

	public void setDeathsDiff(int deathsDiff) {
		this.deathsDiff = deathsDiff;
	}

	public void setRecoveredDiff(int recoveredDiff) {
		this.recoveredDiff = recoveredDiff;
	}

	public void setActiveDiff(int activeDiff) {
		this.activeDiff = activeDiff;
	}

	public void setFatalityRate(double fatalityRate) {
		this.fatalityRate = fatalityRate;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public int getConfirmed() {
		return confirmed;
	}

	public int getRecovered() {
		return recovered;
	}

	public int getDeaths() {
		return deaths;
	}

	public int getActive() {
		return active;
	}


	public void setConfirmed(int confirmed) {
		this.confirmed = confirmed;
	}

	public void setRecovered(int recovered) {
		this.recovered = recovered;
	}

	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}

	public void setActive(int active) {
		this.active = active;
	}


	@Override
	public String toString() {
		return  "Province/State: " + region.getProvince() + "\n"
				+	"Confirmed Cases: " + addCommasToNumbers(confirmed) + "\n"
				+	 "Recovered Cases: " + addCommasToNumbers(recovered) + "\n"
				+ "Total Deaths: " + addCommasToNumbers(deaths) + "\n"
				+"Active Cases: " + addCommasToNumbers(active) + "\n" 
				+ "Confirmed difference: " + addCommasToNumbers(confirmedDiff) + "\n"
				+ "Recovered difference: " + addCommasToNumbers(recoveredDiff) + "\n"
				+ "Total death difference: " + addCommasToNumbers(deathsDiff) + "\n"
				+ "Active cases difference: " + addCommasToNumbers(activeDiff)  + "\n"
				+ "Fatality rate : " + formatFatalityRate(fatalityRate) +"% \n \n";
	}

	@Override
	public int compareTo(CovidData d) {
		if(region.getProvince() == null || d.getRegion().getProvince() == null) {
			return 0;
		}
		return region.getProvince().compareTo(d.getRegion().getProvince());
	}

	public String addCommasToNumbers(int number) {
		NumberFormat numberFormat  = NumberFormat.getNumberInstance(Locale.US);
		return numberFormat.format(number);
	}

	public double formatFatalityRate (double rate) {
		double fatalityRate = rate * 100;
		DecimalFormat df = new DecimalFormat("#.##");
		return Double.valueOf(df.format(fatalityRate));

	}


}
