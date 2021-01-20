package com.justinswork.Corona.Tracking.Model;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.stereotype.Component;

import GUI.UserInterface;
@Component
public class CountryLatestData {


	private String country;
	private int confirmed;
	private int recovered;
	private int deaths;
	private int critical;
	private String lastUpdate;

	public CountryLatestData () {

	}

	public CountryLatestData(String country, int confirmed, int recovered, int deaths, int critical,
			String lastUpdate) {
		this.country = country;
		this.confirmed = confirmed;
		this.recovered = recovered;
		this.deaths = deaths;
		this.critical = critical;
		this.lastUpdate = lastUpdate;
	}

	public String getCountry() {
		return country;
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

	public int getCritical() {
		return critical;
	}


	public String getLastUpdate() {
		return lastUpdate;
	}

	public void setCountry(String country) {
		this.country = country;
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

	public void setCritical(int critical) {
		this.critical = critical;
	}

	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String addCommasToNumbers(int number) {
		NumberFormat numberFormat  = NumberFormat.getNumberInstance(Locale.US);
		return numberFormat.format(number);
	}

	@Override
	public String toString() {
		return "Here is the latest Covid totals for " + getCountry() + ":\n \n"
				+ "Confirmed Cases: " + addCommasToNumbers(confirmed) + "\n"
				+ "Recovered Cases: " + addCommasToNumbers(recovered) + "\n"
				+ "Critical Cases: "+ addCommasToNumbers(critical)+"\n"
				+ "Total Deaths: " + addCommasToNumbers(deaths) + "\n" 
				+ "Fatality rate: " + calculateFatalityRate(confirmed, deaths) + "% \n"
				+ "This data was last updated on: " + formatLastUpdate(lastUpdate);

	}

	public String formatLastUpdate(String d) {
		String updatedDate = d.substring(0, 10);
		System.out.println(updatedDate);
		return UserInterface.formatDate(updatedDate);
	}

	public double calculateFatalityRate(int confirmed, int death) {
		Double confirmedDouble = (double) confirmed;
		Double deathDouble = (double) death;
		DecimalFormat df = new DecimalFormat("#.##");
		return Double.valueOf(df.format((deathDouble/confirmedDouble)*100));
	}



}

