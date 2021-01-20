package com.justinswork.Corona.Tracking.Model;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ComputedCovidData extends CovidData {


	private int confirmed;
	private int deaths;
	private int recovered;
	private int active;
	@JsonProperty("confirmed_diff")
	private int confirmedDiff;
	@JsonProperty("deaths_diff")
	private int deathsDiff;
	@JsonProperty("recovered_diff")
	private int recoveredDiff;
	@JsonProperty("active_diff")
	private int activeDiff;


	public ComputedCovidData(int confirmed, int deaths, int recovered, int active, int confirmedDiff,
			int deathsDiff, int recoveredDiff, int activeDiff) {
		super();
		this.confirmed = confirmed;
		this.deaths = deaths;
		this.recovered = recovered;
		this.active = active;
		this.confirmedDiff = confirmedDiff;
		this.deathsDiff = deathsDiff;
		this.recoveredDiff = recoveredDiff;
		this.activeDiff = activeDiff;
	}

	public ComputedCovidData () {

	}

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
	/*
	public double getFatalityRate() {
		return fatalityRate;
	}
	 */
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
		return  "Here is the cummulated data: " + "\n \n" 
				+	"Confirmed Cases: " + addCommasToNumbers(confirmed) + "\n"
				+	 "Recovered Cases: " + addCommasToNumbers(recovered) + "\n"
				+ "Total Deaths: " + addCommasToNumbers(deaths) + "\n"
				+"Active Cases: " + addCommasToNumbers(active) + "\n" 
				+ "Confirmed difference: " + addCommasToNumbers(confirmedDiff) + "\n"
				+ "Recovered difference: " + addCommasToNumbers(recoveredDiff) + "\n"
				+ "Total death difference: " + addCommasToNumbers(deathsDiff) + "\n"
				+ "Active cases difference: " + addCommasToNumbers(activeDiff) + "\n"
				+  "Fatality rate: " + computeFatalityRate(confirmed, deaths)  + "% \n\n"
				+	"Beginning here is a province/state breakdown: " + "\n\n";
	}

	public String addCommasToNumbers(int number) {
		NumberFormat numberFormat  = NumberFormat.getNumberInstance(Locale.US);
		return numberFormat.format(number);
	}

	public double computeFatalityRate(int confirmed, int deaths) {
		double fatalityRate = (Double.valueOf(deaths)/Double.valueOf(confirmed) * 100);
		DecimalFormat dFormat = new DecimalFormat("#.##");
		return Double.valueOf(dFormat.format(fatalityRate));
	}



}
