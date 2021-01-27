package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import org.jdatepicker.DateModel;
import org.jdatepicker.JDatePicker;
import org.jdatepicker.UtilDateModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.justinswork.Corona.Tracking.Model.GlobalCovidWrapper;
import com.justinswork.Corona.Tracking.Model.ListOfCountries;
import com.justinswork.Corona.Tracking.Model.ComputedCovidData;
import com.justinswork.Corona.Tracking.Model.CovidData;
import com.justinswork.Corona.Tracking.Model.CovidWrapper;
import com.justinswork.Corona.Tracking.Service.CovidService;

@Component
public class UserInterface implements Runnable {

	@Autowired
	private CovidWrapper covidWrapper;
	@Autowired
	private GlobalCovidWrapper globalCovidWrapper;


	public UserInterface (){

	}


	@Override
	public void run() {
		mainFrame();
	}

	//Creating the welcome frame
	private void mainFrame() {
		JFrame frame = new JFrame("Corona Tracking");


		final JLabel label = new JLabel("<html>Welcome to Covid Tracking. The difference data is the difference between the date chosen, and the day before. Please choose the data you would like to view.<html>");
		label.setBounds(10, 18, 330, 100);
		label.setFont(new Font("Times New Roman", Font.PLAIN, 16));


		JButton button = new JButton("Select");
		button.setBounds(110, 150, 80, 50);

		String choices[]= {"Global Totals", "Daily Global Totals", "Daily Country Totals", "Country Totals"};
		@SuppressWarnings({ "unchecked", "rawtypes" })
		final JComboBox cBox = new JComboBox(choices);
		cBox.setBounds(50, 120, 200, 20);
		frame.add(cBox); 
		frame.add(label); 
		frame.add(button);
		frame.setSize(350,300);
		frame.setLayout(null);
		frame.setVisible(true);

		//Adding action listener to select button
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String userCommand = cBox.getSelectedItem().toString();
				if(userCommand.equalsIgnoreCase("Global totals")) {
					createGlobalTotalsJFrame();
					frame.setVisible(false);

				}else if (userCommand.equalsIgnoreCase("Daily global totals")) {
					createDateSearchFrame();
					frame.setVisible(false);
				}else if (userCommand.equalsIgnoreCase("Daily country totals")) {
					createCountryAndDateSearchFrame();
					frame.setVisible(false);
				}else if (userCommand.equalsIgnoreCase("Country totals")) {
					createCountrySearchFrame();
					frame.setVisible(false);
				}

			}
		});

	}

	//***HERE ARE GLOBAL FRAMES**********
	
	//Creating the Global Totals frame
	private void createGlobalTotalsJFrame() {
		JFrame globalTotalsFrame = new JFrame("Global Totals");

		JButton backButton = backToMainMenuButton(globalTotalsFrame);

		JLabel welcomeLabel = new JLabel("<html>These are the global numbers as of today, " + formatDate(LocalDate.now().toString()) + "<html>");
		welcomeLabel.setBounds(10, 100, 380, 80);
		welcomeLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));

		CovidService service = new CovidService();
		globalCovidWrapper = service.getGlobalLatestTotals();

		JTextPane globalCovidData = new JTextPane();
		globalCovidData.setSize(200, 300);
		globalCovidData.setBounds(10, 200, 350, 350);
		globalCovidData.setEditable(false);
		globalCovidData.setText(globalCovidWrapper.toString());

		globalTotalsFrame.add(welcomeLabel);
		globalTotalsFrame.add(backButton);
		globalTotalsFrame.add(globalCovidData);
		globalTotalsFrame.setSize(400, 500);
		globalTotalsFrame.setLayout(null);
		globalTotalsFrame.setVisible(true);
	}


	// Creating Global stats by a specified date frame
	private void createGlobalByDateFrame(String date) {
		JFrame dailyCasesFrame = new JFrame( formatDate(date) + " Global Cases");
		dailyCasesFrame.setSize(400,500);
		dailyCasesFrame.setLayout(null);
		dailyCasesFrame.setVisible(true);

		JButton backButton = backToMainMenuButton(dailyCasesFrame);
		JButton backToSearchFrame = new JButton("Back to date search");
		backToSearchFrame.setBounds(210, 20, 180, 20);
		backToSearchFrame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createDateSearchFrame();
				dailyCasesFrame.setVisible(false);
			}
		});

		JLabel differenceInfoLabel = new JLabel("<html>The differences for each statistic is the increase/decrease from the day prior.<html>");
		differenceInfoLabel.setBounds(15, 90, 390, 90);
		differenceInfoLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));

		CovidService service = new CovidService();
		globalCovidWrapper = service.getDailyReportByDate(date);

		JTextPane dailyData = new JTextPane();
		dailyData.setSize(200, 300);
		dailyData.setBounds(10, 200, 350, 350);
		dailyData.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		dailyData.setText(globalCovidWrapper.toString());
		dailyData.setEditable(false);

		dailyCasesFrame.add(differenceInfoLabel);
		dailyCasesFrame.add(backToSearchFrame);
		dailyCasesFrame.add(backButton);
		dailyCasesFrame.add(dailyData);
	}


	//******HERE ARE BY COUNTRY FRAMES
	
	//Creating a countries stats on a specified date frame
	private void createCountryByDateFrame(String country, String date) {

		JFrame dailyByCountryFrame = new JFrame();
		dailyByCountryFrame.setSize(400,500);
		dailyByCountryFrame.setLayout(null);

		JButton backButton = backToMainMenuButton(dailyByCountryFrame);

		JLabel searchInfoLabel = new JLabel("<html>Here is the covid information for " + country + " on " + formatDate(date) + "<html>");
		searchInfoLabel.setBounds(10, 40, 400, 60);

		JButton backToSearchFrame = new JButton("Back to country search");
		backToSearchFrame.setBounds(210, 20, 190, 20);

		backToSearchFrame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createCountryAndDateSearchFrame();
				dailyByCountryFrame.setVisible(false);
			}
		});

		CovidService service = new CovidService();
		covidWrapper = service.getDailyDataByCountry(country, date);

		JTextPane countryDailyData = new JTextPane();

		Collections.sort(covidWrapper.getData());

		//Here I am testing if the data includes province/state data, as well as removing unwanted characters.
		if(covidWrapper.getData().size() == 1) {
			countryDailyData.setText(covidWrapper.toString().replace("[", "").replace("]", "").replace("Province/State:", ""));
			countryDailyData.setFont(new Font("Times New Roman", Font.PLAIN, 16));

		}else {
			//Getting the computed countries total stats (based off the raw state/province data)
			String computedString = computeTotalCountryCovidData(covidWrapper.getData()).toString();
			countryDailyData.setText(covidWrapper.toString().replace("[", "").replace("]", "").replace(", Prov", "Prov"));

			try {
				// Adding the computed data to the already established JtextPane
				countryDailyData.getStyledDocument().insertString(0, computedString, null);
				countryDailyData.setFont(new Font("Times New Roman", Font.PLAIN, 16));

			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
		}
		countryDailyData.setEditable(false);

		/*THIS IS IMPORTANT!!!
			This will have the scrollpane at the top of the texpane when opened, instead
			of it being at the bottom */
		countryDailyData.setCaretPosition(0);


		JScrollPane scrollPane = new JScrollPane(countryDailyData);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(50, 90, 250, 410);			


		dailyByCountryFrame.add(searchInfoLabel);
		dailyByCountryFrame.add(backToSearchFrame);
		dailyByCountryFrame.add(backButton);	
		dailyByCountryFrame.add(scrollPane);
		dailyByCountryFrame.setVisible(true);
	}

	//Creating Countries latest statistics frame
	public void createCountryLatestFrame(String country) {
		JFrame countryLatestFrame = new JFrame();
		countryLatestFrame.setSize(400,500);
		countryLatestFrame.setLayout(null);

		JButton backToMainMenuButton = backToMainMenuButton(countryLatestFrame);

		JButton backButton = new JButton("Back to country search");
		backButton.setBounds(210, 20, 180, 20);
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createCountrySearchFrame();
				countryLatestFrame.setVisible(false);
			}
		});

		CovidService service = new CovidService();


		JTextPane countryLatestData = new JTextPane();
		countryLatestData.setBounds(10, 200, 350, 350);
		countryLatestData.setText(service.getCountryTotals(country).toString());
		countryLatestData.setFont(new Font("Times New Roman", Font.PLAIN, 16));

		countryLatestFrame.add(backButton);
		countryLatestFrame.add(countryLatestData);
		countryLatestFrame.add(backToMainMenuButton);

		countryLatestFrame.setVisible(true);
		countryLatestFrame.setLayout(null);
	}


	///*******HERE ARE SEARCH FRAMES*******
	
	//Creating a country search frame
	public void createCountrySearchFrame() {
		JFrame countrySearchFrame = new JFrame();
		countrySearchFrame.setSize(400,350); 
		countrySearchFrame.setLayout(null);
		countrySearchFrame.setVisible(true);

		JButton backButton = backToMainMenuButton(countrySearchFrame);

		final JLabel instructionLabel = new JLabel("<html> Please choose the country you'd like to view.<html>");
		instructionLabel.setBounds(75, 110, 270 , 40);

		JComboBox<String> countryDropBox = new JComboBox<String>(ListOfCountries.getCountriesArray());
		countryDropBox.setBounds(110, 155, 180, 40);

		JButton submitCountryButton = new JButton("Submit");
		submitCountryButton.setBounds(150, 211, 80, 40);

		submitCountryButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String choice = (String)countryDropBox.getSelectedItem();
				createCountryLatestFrame(choice);
				countrySearchFrame.setVisible(false);

			}
		});
		countrySearchFrame.add(instructionLabel);
		countrySearchFrame.add(backButton);
		countrySearchFrame.add(countryDropBox);
		countrySearchFrame.add(submitCountryButton);

	}

	//Creating the frame where users choose to see global statistics for a specified date
	public void createDateSearchFrame() {
		JFrame dateSearchFrame = new JFrame();
		dateSearchFrame.setSize(400, 350);

		JButton backButton = backToMainMenuButton(dateSearchFrame);

		JTextField dateTextField = new JTextField();
		dateTextField.setBounds(130, 170, 150, 40);



		String yesterdaysDate = LocalDate.now().minusDays(1).toString();

		JLabel dateInstructionTextField = new JLabel("<html>Data is available for dates starting on 01/22/2020 and up to yesterdays' date: " + formatDate(yesterdaysDate) + "*****<html>");
		dateInstructionTextField.setBounds(60, 90, 300, 80);
		dateInstructionTextField.setFont(new Font("Times New Roman", Font.PLAIN, 16));

		DateModel<Date> dateModel = new UtilDateModel();
		JDatePicker datePicker = new JDatePicker(dateModel, "yyyy-MM-dd");
		datePicker.setBounds(115, 170, 150, 40);
		JFormattedTextField tf = datePicker.getFormattedTextField();
		tf.setPreferredSize(new Dimension(150,40));


		JButton submitDateButton = new JButton("Submit");
		submitDateButton.setBounds(125, 211, 80, 50);

		submitDateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedDate = datePicker.getFormattedTextField().getText();
				if(selectedDate.isBlank() || (!(isDateValid(selectedDate)))) {
					createDateIsInvalidGlobalFrame();
					dateSearchFrame.setVisible(false);
				}else {
					createGlobalByDateFrame(datePicker.getFormattedTextField().getText());
					dateSearchFrame.setVisible(false);
				}
			}
		});

		dateSearchFrame.add(backButton);
		dateSearchFrame.add(dateInstructionTextField);
		dateSearchFrame.add(submitDateButton);
		dateSearchFrame.add(datePicker);
		dateSearchFrame.setLayout(null);
		dateSearchFrame.setVisible(true);	
	}

	//Creating a frame that allows users to search for statistics for a specified country on a specified date
	public void createCountryAndDateSearchFrame() {
		JFrame countryAndDateSearchFrame = new JFrame();
		countryAndDateSearchFrame.setSize(400,350);
		countryAndDateSearchFrame.setLayout(null);
		countryAndDateSearchFrame.setVisible(true);


		JButton backButton = backToMainMenuButton(countryAndDateSearchFrame);

		JComboBox<String> countryDropBox = new JComboBox<String>(ListOfCountries.getCountriesArray());
		countryDropBox.setBounds(5, 165, 210, 40);

		DateModel<Date> dateModel = new UtilDateModel();
		JDatePicker datePicker = new JDatePicker(dateModel, "yyyy-MM-dd");
		datePicker.setBounds(215, 170, 150, 40);

		JButton submitCountryAndDateButton = new JButton("Submit");
		submitCountryAndDateButton.setBounds(150, 211, 80, 40);



		String yesterdaysDate = LocalDate.now().minusDays(1).toString();

		JLabel dateInstructionTextField = new JLabel("<html>Data is available for dates starting from 01/22/2020 and up to yesterdays' date: " + formatDate(yesterdaysDate) + "<html>");
		dateInstructionTextField.setBounds(40, 90, 320, 90);
		dateInstructionTextField.setFont(new Font("Times New Roman", Font.PLAIN, 16));

		submitCountryAndDateButton.addActionListener(new ActionListener() {
			@Override

			public void actionPerformed(ActionEvent e) {
				String selectedDate = datePicker.getFormattedTextField().getText();
				String selectedField = (String) countryDropBox.getSelectedItem();

				if(selectedDate.isBlank() || !(isDateValid(selectedDate))) {
					createDateIsInvalidCountryFrame();
					countryAndDateSearchFrame.setVisible(false);
				}else {
					createCountryByDateFrame(selectedField, selectedDate);
				}
				countryAndDateSearchFrame.setVisible(false);
			}
		});

		countryAndDateSearchFrame.add(dateInstructionTextField);
		countryAndDateSearchFrame.add(datePicker);
		countryAndDateSearchFrame.add(countryDropBox);
		countryAndDateSearchFrame.add(backButton);
		countryAndDateSearchFrame.add(submitCountryAndDateButton);

	}


	//INVALID/ERROR FRAMES
	//Creating the date is out of the specified bounds for countries frame
	public void createDateIsInvalidCountryFrame() {
		JFrame dateIsInvalidCountryFrame = new JFrame();
		dateIsInvalidCountryFrame.setSize(400,350);
		dateIsInvalidCountryFrame.setLayout(null);
		dateIsInvalidCountryFrame.setVisible(true);


		JButton backButton = backToMainMenuButton(dateIsInvalidCountryFrame);

		JComboBox<String> countryDropBox = new JComboBox<String>(ListOfCountries.getCountriesArray());
		countryDropBox.setBounds(5, 165, 210, 40);

		JButton submitCountryAndDateButton = new JButton("Submit");
		submitCountryAndDateButton.setBounds(150, 211, 80, 40);

		JDatePicker datePicker = createCalendar();
		datePicker.setBounds(215, 170, 150, 40);

		String yesterdaysDate = LocalDate.now().minusDays(1).toString();

		JLabel dateInstructionTextField = new JLabel("<html>****The date you chose is not valid. Remember, data is available "
				+ " for dates starting on 01/22/2020 and up to yesterdays' date: " + formatDate(yesterdaysDate) + "****<html>",JLabel.CENTER);
		dateInstructionTextField.setBounds(40, 80, 320, 90);
		dateInstructionTextField.setFont(new Font("Times New Roman", Font.BOLD, 16));
		dateInstructionTextField.setForeground(Color.RED);


		submitCountryAndDateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String dateChosen = datePicker.getFormattedTextField().getText();

				if( dateChosen.isBlank()  ||!(isDateValid(dateChosen))) {
					createDateIsInvalidCountryFrame();
					dateIsInvalidCountryFrame.setVisible(false);
				}else {
					createCountryByDateFrame((String) countryDropBox.getSelectedItem(), dateChosen);	
					dateIsInvalidCountryFrame.setVisible(false);			
				}}});

		dateIsInvalidCountryFrame.add(dateInstructionTextField);
		dateIsInvalidCountryFrame.add(datePicker);
		dateIsInvalidCountryFrame.add(countryDropBox);
		dateIsInvalidCountryFrame.add(backButton);
		dateIsInvalidCountryFrame.add(submitCountryAndDateButton);

	}
	
`	//Creating the date is out of the specified bounds for Global statistics frame
	public void createDateIsInvalidGlobalFrame() {
		JFrame dateIsInvalidFrame = new JFrame();
		dateIsInvalidFrame.setSize(400,350);
		dateIsInvalidFrame.setLayout(null);
		dateIsInvalidFrame.setVisible(true);


		JButton backButton = backToMainMenuButton(dateIsInvalidFrame);


		JButton submitDateButton = new JButton("Submit");
		submitDateButton.setBounds(125, 211, 80, 40);

		JDatePicker datePicker = createCalendar();
		datePicker.setBounds(110, 170, 150, 40);


		String yesterdaysDate = LocalDate.now().minusDays(1).toString();

		JLabel dateInstructionTextField = new JLabel("<html>****The date you chose is not valid. Remember, data is available "
				+ " for dates starting from 01/22/2020 and up to yesterdays' date: " + formatDate(yesterdaysDate) + "****<html>",JLabel.CENTER);
		dateInstructionTextField.setBounds(40, 90, 320, 80);
		dateInstructionTextField.setFont(new Font("Times New Roman", Font.BOLD, 16));
		dateInstructionTextField.setForeground(Color.RED);

		submitDateButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String dateChosen = datePicker.getFormattedTextField().getText();
				if( dateChosen.isBlank()  ||!(isDateValid(dateChosen))) {
					createDateIsInvalidGlobalFrame();
					dateIsInvalidFrame.setVisible(false);
				}else {
					createGlobalByDateFrame(dateChosen);
					dateIsInvalidFrame.setVisible(false);
				}
			}
		});

		dateIsInvalidFrame.add(backButton);
		dateIsInvalidFrame.add(submitDateButton);
		dateIsInvalidFrame.add(datePicker);
		dateIsInvalidFrame.add(dateInstructionTextField);
	}


	///****FUNCTIONS*****///

	//Computing a countries covid statistics. This is used when the countries data is returned in a province/state format (no total data for the country, only by state/province
	public CovidData computeTotalCountryCovidData(List<CovidData> data) {
		int confirmed = 0; int recovered = 0; int deaths = 0; int active = 0; int confirmedDifferences = 0;
		int recoveredDifferences = 0; int deathDifferences = 0; int activeDifference = 0;

		CovidData covidDataIncremented = new ComputedCovidData();
		for(int i = 0; i<data.size(); i++) {
			covidDataIncremented = data.get(i);
			confirmed += covidDataIncremented.getConfirmed(); recovered += covidDataIncremented.getRecovered(); deaths += covidDataIncremented.getDeaths();
			active += covidDataIncremented.getActive(); confirmedDifferences += covidDataIncremented.getConfirmedDiff(); recoveredDifferences += covidDataIncremented.getRecoveredDiff();
			deathDifferences += covidDataIncremented.getDeathsDiff(); activeDifference += covidDataIncremented.getActiveDiff();

		}

		return new ComputedCovidData(confirmed, deaths, recovered, active, confirmedDifferences, deathDifferences, recoveredDifferences, activeDifference);

	}
	//Formats the date into the correct format for external API
	public static String formatDate(String date) {
		String [] formattingDate = date.split("-");
		return formattingDate[1] + "/" + formattingDate[2] + "/" + formattingDate[0];
	} 
	//Creates a basic calendar
	public JDatePicker createCalendar () {
		DateModel<Date> dateModel = new UtilDateModel();
		JDatePicker datePicker = new JDatePicker(dateModel, "yyyy-MM-dd");
		datePicker.setVisible(true);
		return datePicker;

	}
	//Checks if the given date is within 1-22-2020 and yesterdays date
	public boolean isDateValid(String date) {

		String dateArray[] = date.split("-");		
		LocalDate earliestDate = LocalDate.of(2020, 1, 22); 
		LocalDate dateChosen = LocalDate.of(Integer.parseInt(dateArray[0]), Integer.parseInt(dateArray[1]), Integer.parseInt(dateArray[2]));

		if(dateChosen.isBefore(earliestDate) || 
				(dateChosen.isEqual(LocalDate.now()) || dateChosen.isAfter(LocalDate.now().minusDays(1)))){
			return false;
		}
		return true;
	}

	///****BUTTONS******

	public JButton backToMainMenuButton(JFrame frame) {
		JButton backButton = new JButton("Back to main menu");
		backButton.setBounds(0, 20, 200, 20);
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame();
				frame.setVisible(false);
			}
		});
		return backButton;

	}
}
