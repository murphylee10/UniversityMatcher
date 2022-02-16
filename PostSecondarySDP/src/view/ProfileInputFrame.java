/*
 * Author: Daniel
 */
package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import controller.PostSecondaryController;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// This class gets student data to influence matching algorithm
@SuppressWarnings("serial")
public class ProfileInputFrame extends JFrame {
	// Instance Variables
	public static final String[] INTERESTS = {"Business", "Coding", "Design", "Earth Science", "Engineering", "Health & Physical Education", 
			"Law", "Linguistics", "Mathematics", "Medical Science", "Public Service", "Space Science", 
			"Research-Based Science", "Social Sciences", "Technology", "Teaching", "The Arts", "World Studies"};
	
	public static final String[] COURSECODES = {"ATC4M", "ADA4M", "AMU4M", "AVI4M", "BBB4M", "BOH4M", "CIA4U", "CGW4U", "CGU4U", "CGR4M", "CGO4M", "CHI4U", "CHY4U", "CLN4U", 
			"CPW4U", "EWC4U", "ETS4U", "ENG4U", "ETS4U", "EWC4U", "FSF4U", "FEF4U", "FIF4U", "ICS4M", "ICS4U", "HFA4M", "HHS4M", "HHG4M", "HSB4M", "HZT4U", "MCV4U", "MHF4U", "MDM4U", "PSK4U",
			"SBI4U", "SCH4U", "SES4U", "SPH4U", "PSE4U", "TDJ4M", "TGJ4M"};


	public static final String[] WEIGHTINGS = {"5.0", "4.5", "4.0", "3.5", "3.0", "2.5", "2.0", "1.5", "1.0"};
	public static List<Integer> weightings;
	public static int rowCount;
	
	
	private static final Color CREAM = new Color(245, 235, 234);
	private static final Color COOKIE = new Color(73, 59, 56);
	private static final Color SKY_BLUE = new Color(196, 232, 228);
	
	public static String firstName;
	public static String lastName;
	public static String[] interests = new String[3];
	public static int minBudget;
	public static int maxBudget;
	public static String address;
	public static Map<String, Integer> courseData = new HashMap<String, Integer>();
	public static double[] weightingsArray = new double[4];
	
	// GUI Components
	
	// Header Section Labels
	public static JLabel lblSection1;
	public static JLabel lblSection2;
	public static JLabel lblSection3;
	
	// Name Components
	public static JLabel lblFirstName;
	public static JLabel lblLastName;
	public static JTextField txtFirstName;
	public static JTextField txtLastName;
	
	// Interest Components
	public static JLabel lblInterests;
	public static JLabel lblInterest1;
	public static JLabel lblInterest2;
	public static JLabel lblInterest3;
	public static JComboBox<String> boxInterest1;
	public static JComboBox<String> boxInterest2;
	public static JComboBox<String> boxInterest3;
	
	// Budget Components
	public static JLabel lblBudgetMin;
	public static JLabel lblBudgetMax;
	public static JSlider sliderBMin;
	public static JSlider sliderBMax;
	
	// Address Components
	public static JLabel lblAddress;
	public static JTextField txtAddress;
	
	// Course & Average Components
	
	public static JLabel lblCourseCode;
	public static JLabel lblAverage;
	public static List<JComboBox<String>> courseCodeFields;
	public static List<JTextField> averageFields;
	public static JButton addCoursebutton;
	
	// Submit Button
	public static JLabel lblSubmit;
	public static JButton submitButton;
	public static JLabel lblError;
	
	// Weightings
	public static JComboBox<String> interest1Weighting;
	public static JComboBox<String> interest2Weighting;
	public static JComboBox<String> interest3Weighting;
	public static JComboBox<String> budgetWeighting;
	
	// Constructor Method
	public ProfileInputFrame(String firstNameValue, String lastNameValue, int interest1Index, int interest2Index, int interest3Index, 
			int minBudgetValue, int maxBudgetValue, String addressValue, List<Integer> courseCodeIndices, List<String> averageValues, int weighting1Index, int weighting2Index, int weighting3Index, int weighting4Index) {
		setSize(1920, 1080);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		getContentPane().setBackground(CREAM);
		
		// Create Top Panel
		JPanel panel = new JPanel();
		panel.setBackground(SKY_BLUE);
		panel.setBounds(0, 0, 1920, 88);
		getContentPane().add(panel);
		
		JPanel TopBorder = new JPanel();
		TopBorder.setBounds(0, 88, 1920, 20);
		TopBorder.setBackground(COOKIE);
		getContentPane().add(TopBorder);
		
		JPanel BottomBorder = new JPanel();
		BottomBorder.setBounds(0, 1021, 1920, 20);
		BottomBorder.setBackground(COOKIE);
		getContentPane().add(BottomBorder);
		
		JPanel LeftBorder = new JPanel();
		LeftBorder.setBounds(0, 84, 20, 996);
		LeftBorder.setBackground(COOKIE);
		getContentPane().add(LeftBorder);
		
		JPanel RightBorder = new JPanel();
		RightBorder.setBounds(1884, 85, 20, 995);
		RightBorder.setBackground(COOKIE);
		getContentPane().add(RightBorder);
		
		// Add Logo label
		JLabel lblNewLabel_2 = new JLabel();
		lblNewLabel_2.setIcon(new ImageIcon("images/Logo.png"));
		lblNewLabel_2.setBounds(0, 5, 1920, 64);
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_2);
		
		// Section 1 Header
		lblSection1 = new JLabel("SECTION ONE");
		lblSection1.setFont(new Font("Monospace", Font.BOLD, 34));
		lblSection1.setHorizontalAlignment(SwingConstants.CENTER);
		lblSection1.setBounds(55, 85 + 100, 245, 70);
		getContentPane().add(lblSection1);
		
		nameInput(firstNameValue, lastNameValue);         // Setup textfields for names
		interestsInput(interest1Index, interest2Index, interest3Index);    // Setup combo boxes for interests
		
		// Section 2 Header
		lblSection2 = new JLabel("SECTION TWO");
		lblSection2.setFont(new Font("Monospace", Font.BOLD, 34));
		lblSection2.setHorizontalAlignment(SwingConstants.CENTER);
		lblSection2.setBounds(590, 85 + 100, 245, 70);
		getContentPane().add(lblSection2);
		
		budgetInput(minBudgetValue, maxBudgetValue);     // Setup sliders for budget range
		
		weightingsInput(weighting1Index, weighting2Index, weighting3Index, weighting4Index); // Setup comboboxes for weightings
		
		addressInput(addressValue);    // Setup textfield for address
		
		// Section 3 Header
		lblSection3 = new JLabel("SECTION THREE");
		lblSection3.setFont(new Font("Monospace", Font.BOLD, 34));
		lblSection3.setHorizontalAlignment(SwingConstants.CENTER);
		lblSection3.setBounds(1080, 85 + 100, 275, 70);
		getContentPane().add(lblSection3);
		
		coursesInput(courseCodeIndices, averageValues);   // Setup textfields for courses
		
		submitButton();   // Setup button to submit
	}

	/*
	 * This method sets up the name input fields
	 */
	private void nameInput(String firstNameValue, String lastNameValue) {
		lblFirstName = new JLabel("What is your First Name?");
		lblFirstName.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblFirstName.setBounds(59, 165 + 100, 305, 28);
		getContentPane().add(lblFirstName);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(SKY_BLUE);
		panel_1.setBounds(59, 165 + 100, 285, 28);
		panel_1.setBackground(SKY_BLUE);
		getContentPane().add(panel_1);
		
		txtFirstName = new JTextField();
		txtFirstName.setText(firstNameValue);
		txtFirstName.setBounds(59, 200 + 100, 305, 40);
		getContentPane().add(txtFirstName);
		txtFirstName.setColumns(10);
		
		lblLastName = new JLabel("What is your Last Name?");
		lblLastName.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblLastName.setBounds(59, 280 + 100, 305, 28);
		getContentPane().add(lblLastName);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(SKY_BLUE);
		panel_2.setBounds(59, 280 + 100, 285, 28);
		panel_2.setBackground(SKY_BLUE);
		getContentPane().add(panel_2);
		
		txtLastName = new JTextField();
		txtLastName.setText(lastNameValue);
		txtLastName.setColumns(10);
		txtLastName.setBounds(59, 315 + 100, 305, 40);
		getContentPane().add(txtLastName);
	}
	
	/*
	 * This method sets up the interest input fields
	 */
	private void interestsInput(int interest1Value, int interest2Value, int interest3Value) {
		lblInterests = new JLabel("What are your 3 areas of interest?");
		lblInterests.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblInterests.setBounds(59, 415 + 100, 420, 28);
		getContentPane().add(lblInterests);

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(SKY_BLUE);
		panel_3.setBounds(59, 415 + 100, 390, 28);
		panel_3.setBackground(SKY_BLUE);
		getContentPane().add(panel_3);
		
		// Combo box for interest 1
		lblInterest1 = new JLabel("Interest 1");
		lblInterest1.setHorizontalAlignment(SwingConstants.CENTER);
		lblInterest1.setBounds(59, 485 + 100, 304, 20);
		getContentPane().add(lblInterest1);
		
		boxInterest1 = new JComboBox<String>(INTERESTS);
		boxInterest1.setSelectedIndex(interest1Value);
		boxInterest1.setMaximumRowCount(18);
		boxInterest1.setBounds(59, 505 + 100, 305, 34);
		getContentPane().add(boxInterest1);
		
		// Combo box for interest 2
		lblInterest2 = new JLabel("Interest 2");
		lblInterest2.setHorizontalAlignment(SwingConstants.CENTER);
		lblInterest2.setBounds(59, 585 + 100, 304, 20);
		getContentPane().add(lblInterest2);
		
		boxInterest2 = new JComboBox<String>(INTERESTS);
		boxInterest2.setSelectedIndex(interest2Value);
		boxInterest2.setMaximumRowCount(18);
		boxInterest2.setBounds(59, 605 + 100, 305, 34);
		getContentPane().add(boxInterest2);
		
		// Combo box for interest 3
		lblInterest3 = new JLabel("Interest 3");
		lblInterest3.setHorizontalAlignment(SwingConstants.CENTER);
		lblInterest3.setBounds(59, 685 + 100, 304, 20);
		getContentPane().add(lblInterest3);
		
		boxInterest3 = new JComboBox<String>(INTERESTS);
		boxInterest3.setSelectedIndex(interest3Value);
		boxInterest3.setMaximumRowCount(18);
		boxInterest3.setBounds(59, 705 + 100, 305, 34);
		getContentPane().add(boxInterest3);
	}

	/*
	 * This method sets up the budget input fields
	 */
	private void budgetInput(int minBudgetValue, int maxBudgetValue) {
		// Slider for min budget
		lblBudgetMin = new JLabel("What is your minimum budget?");
		lblBudgetMin.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblBudgetMin.setBounds(590, 165 + 100, 420, 40);
		getContentPane().add(lblBudgetMin);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(SKY_BLUE);
		panel_4.setBounds(590, 165 + 100, 360, 40);
		panel_4.setBackground(SKY_BLUE);
		getContentPane().add(panel_4);
		
		sliderBMin = new JSlider(JSlider.HORIZONTAL, 5000, 25000, minBudgetValue);
		sliderBMin.setMajorTickSpacing(5000);
		sliderBMin.setMinorTickSpacing(1000);
		sliderBMin.setPaintTicks(true);
		sliderBMin.setPaintLabels(true);
		sliderBMin.setBounds(579, 230 + 100, 305, 40);  
		getContentPane().add(sliderBMin);
		
		// Slider for max budget
		lblBudgetMax = new JLabel("What is your maximum budget?");
		lblBudgetMax.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblBudgetMax.setBounds(590, 300 + 100, 420, 40);
		getContentPane().add(lblBudgetMax);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBackground(SKY_BLUE);
		panel_5.setBounds(590, 300 + 100, 360, 40);
		panel_5.setBackground(SKY_BLUE);
		getContentPane().add(panel_5);
		
		sliderBMax = new JSlider(JSlider.HORIZONTAL, 5000, 25000, maxBudgetValue);
		sliderBMax.setMajorTickSpacing(5000);
		sliderBMax.setMinorTickSpacing(1000);
		sliderBMax.setPaintTicks(true);
		sliderBMax.setPaintLabels(true);
		sliderBMax.setBounds(579, 365 + 100, 305, 40);
		getContentPane().add(sliderBMax);
	}
	
	/*
	 * This method sets up the address input field
	 */
	private void addressInput(String addressValue) {
		// Textfield for Address
		lblAddress = new JLabel("Enter Your Address");
		lblAddress.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblAddress.setBounds(590, 530 + 100, 280, 30);
		getContentPane().add(lblAddress);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBackground(SKY_BLUE);
		panel_6.setBounds(590, 530 + 100, 220, 30);
		panel_6.setBackground(SKY_BLUE);
		getContentPane().add(panel_6);
		
		txtAddress = new JTextField();
		txtAddress.setText(addressValue);
		txtAddress.setBounds(590, 570 + 100, 400, 40);
		getContentPane().add(txtAddress);
		txtAddress.setColumns(10);
	}
	
	
	/*
	 * This method sets up the courses & marks input fields
	 */
	private void coursesInput(List<Integer> courseCodeIndices, List<String> averageValues) {
		courseCodeFields = new ArrayList<JComboBox<String>>();
		averageFields = new ArrayList<JTextField>();
		
		rowCount = 0;
		for (int i = 0; i < courseCodeIndices.size(); i++) {
			courseCodeFields.add(new JComboBox<String>(COURSECODES));
			averageFields.add(new JTextField());
		}
		
		for (int i = 0; i < courseCodeIndices.size(); i++) {
			courseCodeFields.get(i).setSelectedIndex(courseCodeIndices.get(i));
			courseCodeFields.get(i).setBounds(1080, (250 + 100 + rowCount * 50), 180, 35);
			getContentPane().add(courseCodeFields.get(i));
			
			averageFields.get(i).setText(averageValues.get(i));
			averageFields.get(i).setBounds(1280, (250 + 100 + rowCount * 50), 70, 35);
			getContentPane().add(averageFields.get(i));
			
			rowCount++;
		}
		
		addCoursebutton = new JButton("Add Course");
		addCoursebutton.setToolTipText("Click this button to add a course field");
		addCoursebutton.setBounds(1255, 150 + 100, 100, 40);
		getContentPane().add(addCoursebutton);
		
		addCoursebutton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Hi");
				JComboBox<String> tempCourseCodeField = new JComboBox<String>(COURSECODES);
				JTextField tempAveragesField = new JTextField();
				
				tempCourseCodeField.setBounds(1080, (250 + 100 + rowCount * 50), 180, 35);
				tempAveragesField.setBounds(1280, (250 + 100 + rowCount * 50), 70, 35);
				
				courseCodeFields.add(tempCourseCodeField);
				averageFields.add(tempAveragesField);
				getContentPane().add(courseCodeFields.get(rowCount));
				getContentPane().add(averageFields.get(rowCount));
				revalidate();
				repaint();
				
				rowCount++;
			}
			
		});
		
		lblCourseCode = new JLabel("Course Code");
		lblCourseCode.setBounds(1080, 230 + 100, 180, 20);
		lblCourseCode.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblCourseCode);
		
		JPanel panel_7 = new JPanel();
		panel_7.setBackground(SKY_BLUE);
		panel_7.setBounds(1080, 230 + 100, 180, 20);
		panel_7.setBackground(SKY_BLUE);
		getContentPane().add(panel_7);
		
		lblAverage = new JLabel("Average");
		lblAverage.setBounds(1280, 230 + 100, 70, 20);
		lblAverage.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblAverage);
		
		JPanel panel_8 = new JPanel();
		panel_8.setBackground(SKY_BLUE);
		panel_8.setBounds(1280, 230 + 100, 70, 20);
		panel_8.setBackground(SKY_BLUE);
		getContentPane().add(panel_8);
	}
	
	/*
	 * This method sets up the submit button
	 */
	private void submitButton() {
		// Setup label and button to submit information
		lblSubmit = new JLabel("Click the image to complete your profile");
		lblSubmit.setFont(new Font("Helvetica", Font.BOLD, 20));
		lblSubmit.setBounds(1400, 400, 480, 20);
		lblSubmit.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblSubmit);
		
		JPanel panel_7 = new JPanel();
		panel_7.setBackground(SKY_BLUE);
		panel_7.setBounds(1400, 390, 480, 40);
		panel_7.setBackground(SKY_BLUE);
		getContentPane().add(panel_7);
		
		// Set up error label
		lblError = new JLabel();
		lblError.setFont(new Font("Helvetica", Font.BOLD, 20));
		lblError.setForeground(Color.RED);
		lblError.setHorizontalAlignment(SwingConstants.CENTER);
		lblError.setBounds(1400, 950, 480, 20);
		
		// Load image and scale
		Image unscaledImage;
		try {
			unscaledImage = ImageIO.read(new File("images/SubmitButton.png"));
			Image scaledImage = unscaledImage.getScaledInstance(480, 480, Image.SCALE_SMOOTH);
			submitButton = new JButton();
			submitButton.setToolTipText("Click this image to complete your profile setup");
			submitButton.setIcon((new ImageIcon(scaledImage)));
			submitButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			submitButton.setBounds(1400, 450, 480, 480);
			getContentPane().add(submitButton);
		} 
		
		catch (IOException e) {
			e.printStackTrace();
		}
		
		// Add ActionListener to the button
		submitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (validateInput() == true) {
					firstName = txtFirstName.getText();
					lastName = txtLastName.getText();
					interests[0] = boxInterest1.getSelectedItem().toString();
					interests[1] = boxInterest2.getSelectedItem().toString();
					interests[2] = boxInterest3.getSelectedItem().toString();
					minBudget = sliderBMin.getValue();
					maxBudget = sliderBMax.getValue();
					address = txtAddress.getText();
					weightingsArray[0] = Double.parseDouble(interest1Weighting.getSelectedItem().toString());
					weightingsArray[1] = Double.parseDouble(interest2Weighting.getSelectedItem().toString());
					weightingsArray[2] = Double.parseDouble(interest3Weighting.getSelectedItem().toString());
					weightingsArray[3] = Double.parseDouble(budgetWeighting.getSelectedItem().toString());
					
					for (int i = 0; i < courseCodeFields.size(); i++) {
						System.out.println(averageFields.get(i).getText());
						courseData.put(courseCodeFields.get(i).getSelectedItem().toString(), Integer.parseInt(averageFields.get(i).getText()));
					}
					
					PostSecondaryController.instantiateStudentFromSetup();
					PostSecondaryController.matchingAlgorithm(PostSecondaryController.loggedStudent);
					PostSecondaryController.writeDataFromStudent();
					dispose();
					PostSecondaryController.frameState = "Program Match Frame";
					PostSecondaryController.newFrame = true;
				}
				
			}
			
		});
	}
	
	/*
	 * This method sets up the combo boxes for the weightings
	 */
	private void weightingsInput(int weighting1Index, int weighting2Index, int weighting3Index, int weighting4Index) {
		// Weighting 1 is for interest 1
		JLabel lblWeighting1 = new JLabel("Weighting");
		lblWeighting1.setBounds(430, 485 + 100, 100, 20);
		getContentPane().add(lblWeighting1);
		
		interest1Weighting = new JComboBox<String>(WEIGHTINGS);
		interest1Weighting.setSelectedIndex(weighting1Index);
		interest1Weighting.setMaximumRowCount(8);
		interest1Weighting.setBounds(420, 505 + 100, 100, 34);
		getContentPane().add(interest1Weighting);
		
		// Weighting 2 is for interest 2
		JLabel lblWeighting2 = new JLabel("Weighting");
		lblWeighting2.setBounds(430, 585 + 100, 100, 20);
		getContentPane().add(lblWeighting2);
		
		interest2Weighting = new JComboBox<String>(WEIGHTINGS);
		interest2Weighting.setSelectedIndex(weighting2Index);
		interest2Weighting.setMaximumRowCount(8);
		interest2Weighting.setBounds(420, 605 + 100, 100, 34);
		getContentPane().add(interest2Weighting);
		
		// Weighting 3 is for interest 3
		JLabel lblWeighting3 = new JLabel("Weighting");
		lblWeighting3.setBounds(430, 685 + 100, 100, 20);
		getContentPane().add(lblWeighting3);
		
		interest3Weighting = new JComboBox<String>(WEIGHTINGS);
		interest3Weighting.setSelectedIndex(weighting3Index);
		interest3Weighting.setMaximumRowCount(8);
		interest3Weighting.setBounds(420, 705 + 100, 100, 34);
		getContentPane().add(interest3Weighting);
		
		// Weighting 4 is for the budget
		JLabel lblWeighting4 = new JLabel("Budget Weighting");
		lblWeighting4.setBounds(680, 450 + 100, 120, 20);
		getContentPane().add(lblWeighting4);
		
		budgetWeighting = new JComboBox<String>(WEIGHTINGS);
		budgetWeighting.setSelectedIndex(weighting4Index);
		budgetWeighting.setMaximumRowCount(8);
		budgetWeighting.setBounds(680, 470 + 100, 100, 34);
		getContentPane().add(budgetWeighting);
	}
	
	/*
	 * This method checks if all the fields have been filled
	 */
	private boolean validateInput() {
		boolean valid = true;
		// Make sure all fields are filled
		if (txtFirstName.getText().equals("") || txtLastName.getText().equals("")) {
			lblError.setText("Name Labels Cannot Be Empty");
			valid = false;
		}
	
		else if (boxInterest1.getSelectedIndex() == -1 || boxInterest2.getSelectedIndex() == -1 || boxInterest3.getSelectedIndex() == -1) {
			lblError.setText("You Must Pick 3 Interests");
			valid = false;
		}
		
		else if (boxInterest1.getSelectedIndex() == boxInterest2.getSelectedIndex() || boxInterest2.getSelectedIndex() == boxInterest3.getSelectedIndex() 
				|| boxInterest1.getSelectedIndex() == boxInterest3.getSelectedIndex()) {
			lblError.setText("You Must Pick 3 Distinct Interests");
			valid = false;
		}
		
		// The max budget must be set higher than the min budget
		else if (sliderBMax.getValue() <= sliderBMin.getValue()) {
			lblError.setText("The Max Budget Must Be Higher");
			valid = false;
		}
		
		else if (txtAddress.getText().equals("")) {
			lblError.setText("You Must Provide An Address");
			valid = false;
		}
		
		else if (interest1Weighting.getSelectedIndex() == -1 || interest2Weighting.getSelectedIndex() == -1 || interest3Weighting.getSelectedIndex() == -1 || budgetWeighting.getSelectedIndex() == -1) {
			lblError.setText("You Must Specify All 4 Weightings");
			valid = false;
		}
		
		// If any 2 weightings are equal, don't allow
		else if (interest1Weighting.getSelectedIndex() == interest2Weighting.getSelectedIndex() || interest1Weighting.getSelectedIndex() == interest3Weighting.getSelectedIndex() 
				|| interest1Weighting.getSelectedIndex() == budgetWeighting.getSelectedIndex() || interest2Weighting.getSelectedIndex() == interest3Weighting.getSelectedIndex()
				|| interest2Weighting.getSelectedIndex() == budgetWeighting.getSelectedIndex() || interest3Weighting.getSelectedIndex() == budgetWeighting.getSelectedIndex()) {
			lblError.setText("All Weightings Must Be Distinct");
			valid = false;
			
		}

		// The user must enter at least 6 distinct courses
//		else if (courseData.size() < 6) {
//			lblError.setText("You Must Give at least 6 Distinct Courses");
//			valid = false;
//		}
		
		// The user must fill out the course fields
		else {
			// Make sure the user didn't pick the same course twice
			for (int i = 0; i < courseCodeFields.size(); i++) {
				for (int j = i + 1; j < courseCodeFields.size(); j++) {
					if (sameSelectedIndex(courseCodeFields.get(i), courseCodeFields.get(j))) {
						lblError.setText("You Have Inputted Duplicate Courses");
						valid = false;
					}
				}
			}
			// Make sure all comboboxes for courses and fields for marks are filled
			for (int i = 0; i < courseCodeFields.size(); i++) {
				if (courseCodeFields.get(i).getSelectedIndex() == -1 || averageFields.get(i).getText().equals("")) {
					lblError.setText("All Course Fields Must Be Filled");
					valid = false;
				}
			}
		}
		
		getContentPane().add(lblError);
		revalidate();
		repaint();
		return valid;
	}
	
	/*
	 * This method checks if 2 comboboxes have the same selected index
	 */
	private boolean sameSelectedIndex(JComboBox<String> c1, JComboBox<String> c2) {
		if (c1.getSelectedIndex() == c2.getSelectedIndex()) {
			return true;
		}
		return false;
	}
}