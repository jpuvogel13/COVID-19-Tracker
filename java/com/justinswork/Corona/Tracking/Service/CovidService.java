package com.justinswork.Corona.Tracking.Service;



import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import com.justinswork.Corona.Tracking.Model.CountryLatestData;
import com.justinswork.Corona.Tracking.Model.GlobalCovidWrapper;
import com.justinswork.Corona.Tracking.Model.CovidWrapper;


@Service
public class CovidService {

	@Autowired 
	private CovidWrapper covidWrapper;
	@Autowired
	private CountryLatestData countryLatestData;
	@Autowired
	private GlobalCovidWrapper globalCovidWrapper;


	public CovidService() {
		super();

	}
	
	// Makes call to external API, and receives the latest global data
	public GlobalCovidWrapper getGlobalLatestTotals() {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("x-rapidapi-key", "8f2e4461f2msha37e474fac5d055p1af07cjsn53d280da44d7");
		headers.add("x-rapidapi-host", "covid-19-statistics.p.rapidapi.com");
		HttpEntity<String> httpEntity = new HttpEntity<String>(headers);

		RestTemplate template = new RestTemplate();
		
		// make an HTTP GET request with headers
		ResponseEntity<GlobalCovidWrapper> response = template.exchange(
				"https://covid-19-statistics.p.rapidapi.com/reports/total",
				HttpMethod.GET,
				httpEntity, new ParameterizedTypeReference<GlobalCovidWrapper>() {
				});

		globalCovidWrapper = response.getBody();
		return globalCovidWrapper;

	}
	
	//Makes call to API and receives the data for a specified country on a specified date
	public  CovidWrapper getDailyDataByCountry(String country, String date){

		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("x-rapidapi-key", "8f2e4461f2msha37e474fac5d055p1af07cjsn53d280da44d7");
		headers.add("x-rapidapi-host", "covid-19-statistics.p.rapidapi.com");
		HttpEntity<String> httpEntity = new HttpEntity<String>(headers);

		RestTemplate template = new RestTemplate();

		String url = "https://covid-19-statistics.p.rapidapi.com/reports?region_name=" + country + "&date=" + date;

		// make an HTTP GET request with headers
		ResponseEntity<CovidWrapper> response = template.exchange(
				url, HttpMethod.GET,
				httpEntity, new ParameterizedTypeReference<CovidWrapper>()  {
				});

		covidWrapper = response.getBody();

		return covidWrapper;
	}

	//Makes call to API and receives the global data for a specified date
	public GlobalCovidWrapper getDailyReportByDate(String date) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("x-rapidapi-key", "8f2e4461f2msha37e474fac5d055p1af07cjsn53d280da44d7");
		headers.add("x-rapidapi-host", "covid-19-statistics.p.rapidapi.com");
		HttpEntity<String> httpEntityDaily = new HttpEntity<String>(headers);
		
		RestTemplate template = new RestTemplate();
		
		// make an HTTP GET request with headers
		ResponseEntity<GlobalCovidWrapper> response = template.exchange(
				"https://covid-19-statistics.p.rapidapi.com/reports/total?date={date}",
				HttpMethod.GET, httpEntityDaily, new ParameterizedTypeReference<GlobalCovidWrapper>() {
				}, date);

		globalCovidWrapper = response.getBody();
		
		return globalCovidWrapper;

	}
	
	//Makes call to API and receives a countries latest data
	public CountryLatestData getCountryTotals(String country) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("x-rapidapi-key", "8f2e4461f2msha37e474fac5d055p1af07cjsn53d280da44d7");
		headers.add("x-rapidapi-host", "covid-19-data.p.rapidapi.com");
		HttpEntity<String> httpEntityDaily = new HttpEntity<String>(headers);
		
		RestTemplate template = new RestTemplate();

		
		if(country.equals("US")) {
			ResponseEntity<List<CountryLatestData>> response =  template.exchange(
					"https://covid-19-data.p.rapidapi.com/country?name={country}",
					HttpMethod.GET, httpEntityDaily, new ParameterizedTypeReference<List<CountryLatestData>>() {
					}, "USA");
			List<CountryLatestData> list = response.getBody();
			countryLatestData = list.get(0);
		}else {
			ResponseEntity<List<CountryLatestData>> response =  template.exchange(
					"https://covid-19-data.p.rapidapi.com/country?name={country}",
					HttpMethod.GET, httpEntityDaily, new ParameterizedTypeReference<List<CountryLatestData>>() {
					}, country);
			List<CountryLatestData> list = response.getBody();
			countryLatestData = list.get(0);
		}

		return countryLatestData;

	}	

}
