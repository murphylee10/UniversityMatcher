/*
 * Author: Murphy (50%), Daniel (25%), Savannah (15%), Fawaz (10%)
 */
package controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import model.Program;
import model.Student;
import view.LoginFrame;
import view.ProfileDisplayFrame;
import view.ProfileInputFrame;
import view.ProgramMatchFrame;
import view.RegisterFrame;

// This class is the controller for connecting the GUI components and models
public class PostSecondaryController {
	// Instance Variables
	public static String frameState;
	public static boolean newFrame = true;
	public static Student loggedStudent;
	public static List<Program> allPrograms = new ArrayList<Program>();
	
	// Frames
	public static RegisterFrame registerFrame;
	public static LoginFrame loginFrame;
	public static ProfileInputFrame profileInputFrame;
	public static ProfileInputFrame profileInputFrameWithData;
	public static ProgramMatchFrame programMatchFrame;
	public static ProfileDisplayFrame profileDisplayFrame;
	
	/*
	 * Author: Murphy Lee
	 * Constructor Method
	 */
	public PostSecondaryController() {
		
		// Create a new student instance
		loggedStudent = new Student();
		
		// Read from the CSV
		readPrograms();
		
		frameState = "Login";
		
		// Infinite Loop for running frames
		while (true) {
			System.out.print("");
			if (newFrame == true) {
				// Login Page
				if (frameState.equals("Login")) {
					loginFrame = new LoginFrame();
					loginFrame.setVisible(true);
					newFrame = false;
				}

				// Register Page
				if (frameState.equals("Register")) {
					registerFrame = new RegisterFrame();
					registerFrame.setVisible(true);
					newFrame = false;
				}
				
				// Program Input Page
				if (frameState.equals("New Profile Input Frame")) {
					List<Integer> tempCourseCodeList = new ArrayList<Integer>();
					List<String> tempAverageList = new ArrayList<String>();
					
					for (int i = 0; i < 6; i++) {
						tempCourseCodeList.add(-1);
						tempAverageList.add("");
					}
					
					profileInputFrame = new ProfileInputFrame("", "", -1, -1, -1, 15000, 15000, "", tempCourseCodeList, tempAverageList, -1, -1 , -1, -1);
					profileInputFrame.setVisible(true);
					newFrame = false;
					
				}
				
				// Program Input Page, Loading with User Data
				if (frameState.equals("Program Input Frame With Data")) {
					instantiateStudentFromLogin();
					// Contains interest fields to access by index
					String[] interests = ProfileInputFrame.INTERESTS;
					String[] weightings = ProfileInputFrame.WEIGHTINGS;
					String[] courseCodes = ProfileInputFrame.COURSECODES;
					
					
					// The logged students' interests and preferred weighting
					String[] studentInterests = loggedStudent.getInterests();
					double[] studentWeightings = loggedStudent.getWeightings();

					// Temporary lists to hold the students' course codes and averages
					List<Integer> studentCourseCodes = new ArrayList<Integer>();
					List<String> studentAverages = new ArrayList<String>();
					
					for (Map.Entry<String, Integer> entry : loggedStudent.getCourses().entrySet())
					{
						studentCourseCodes.add(findIndex(courseCodes, entry.getKey()));
						studentAverages.add(String.valueOf(entry.getValue()));
					}
					
					// Constructor call for profile input frame with all user data passed into parameters
					profileInputFrame = new ProfileInputFrame(loggedStudent.getFirstName(), loggedStudent.getLastName(), findIndex(interests, studentInterests[0]),
							findIndex(interests, studentInterests[1]), findIndex(interests, studentInterests[2]), (int) loggedStudent.getMinBudget(), (int) loggedStudent.getMaxBudget(),
							loggedStudent.getAddress(), studentCourseCodes, studentAverages, findIndex(weightings, String.valueOf(studentWeightings[0])), findIndex(weightings, String.valueOf(studentWeightings[1])), 
							findIndex(weightings, String.valueOf(studentWeightings[2])), findIndex(weightings, String.valueOf(studentWeightings[3])));
					
					profileInputFrame.setVisible(true);
					newFrame = false;
				}
				
				// Matching Programs Page
				if (frameState.equals("Program Match Frame")) {				
					programMatchFrame = new ProgramMatchFrame(loggedStudent.topPrograms);
					programMatchFrame.setVisible(true);
					newFrame = false;
					
				}
				
				// Profile Data Display Page
				if (frameState.equals("Profile Display Frame")) {
					
					profileDisplayFrame = new ProfileDisplayFrame();
					profileDisplayFrame.setVisible(true);
					newFrame = false;
				}
				
				// Profile Data Display Page
				if (frameState.equals("Profile Display Frame From Login")) {
					instantiateStudentFromLogin();
					
					profileDisplayFrame = new ProfileDisplayFrame();
					profileDisplayFrame.setVisible(true);
					newFrame = false;
				}
				
			}
			
		}
		
		
	}
	
	// Utility Methods
	
	
	/*
	 * Author: Murphy
	 * This method adds the username and password to the registered accounts folder
	 */
	
	public static void addStudent() {
		try {
			Formatter formatter = new Formatter(new BufferedWriter(new FileWriter("files/RegisteredAccounts.txt", true)));
			formatter.format("%s %s %s%n", RegisterFrame.username, RegisterFrame.password, RegisterFrame.username + ".txt");
			formatter.close();
			
		}
		
		catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/*
	 * Author: Daniel
	 * This method reads data from the profile input frame and sets the corresponding values of the student
	 * 
	 */
	public static void instantiateStudentFromSetup() {
		loggedStudent.setFirstName(ProfileInputFrame.firstName);
		loggedStudent.setLastName(ProfileInputFrame.lastName);
		loggedStudent.setMinBudget(ProfileInputFrame.minBudget);
		loggedStudent.setMaxBudget(ProfileInputFrame.maxBudget);
		loggedStudent.setAddress(ProfileInputFrame.address);
		loggedStudent.setCourses(ProfileInputFrame.courseData);
		loggedStudent.setInterests(ProfileInputFrame.interests);
		loggedStudent.setWeightings(ProfileInputFrame.weightingsArray);
		
		// Write data to a file
		writeDataFromStudent();
	}
	
	/*
	 * Author: Murphy
	 * This method reloads the data from a text file when a user logs in
	 */
	public static boolean instantiateStudentFromLogin() {
		try {
			Scanner reader = new Scanner(new File("files/" + LoginFrame.username + ".txt"));

			// Names
			loggedStudent.setFirstName(reader.nextLine());
			loggedStudent.setLastName(reader.nextLine());
			
			// Budgets
			loggedStudent.setMinBudget(Double.parseDouble(reader.nextLine()));
			loggedStudent.setMaxBudget(Double.parseDouble(reader.nextLine()));
			
			// Address
			loggedStudent.setAddress(reader.nextLine());
			
			// Interests
			String[] tempInterestArray = new String[3];
			tempInterestArray[0] = reader.nextLine();
			tempInterestArray[1] = reader.nextLine();
			tempInterestArray[2] = reader.nextLine();
			loggedStudent.setInterests(tempInterestArray);
			
			// Weightings
			double[] tempWeightingArray = new double[4];
			tempWeightingArray[0] = Double.parseDouble(reader.next());
			tempWeightingArray[1] = Double.parseDouble(reader.next());
			tempWeightingArray[2] = Double.parseDouble(reader.next());
			tempWeightingArray[3] = Double.parseDouble(reader.next());			
			loggedStudent.setWeightings(tempWeightingArray);
			
			reader.nextLine();
			
			// Courses
			HashMap<String, Integer> marksHolder = new HashMap<String, Integer>();
			String textHolder = reader.nextLine();
			while (textHolder.charAt(0) == 'C') {
				String[] keyValue = textHolder.split(" ");
				marksHolder.put(keyValue[1], Integer.parseInt(keyValue[2]));
									
				textHolder = reader.nextLine();
			}
			loggedStudent.setCourses(marksHolder);
			
			// Admission average
			loggedStudent.setAdmissionAverage(Integer.parseInt(textHolder));
			
			// Matched programs & Selected programs
			List<Program> tempMatchList = new ArrayList<Program>();
			List<Program> tempTopList = new ArrayList<Program>();
			List<Program> tempSelectedList = new ArrayList<Program>();
			
			int matchCount = 0;
			int tempCount = 0;
			int selectedCount = 0;
			while (reader.hasNextLine()) {
				String[] tokens = reader.nextLine().split(",");
				// If it's a matched program
				if (tokens[0].charAt(0) == 'M') {
					tempMatchList.add(new Program(tokens[1], tokens[2], tokens[3], tokens[4], Integer.parseInt(tokens[5]), Integer.parseInt(tokens[6]), 
							Integer.parseInt(tokens[7]), Double.parseDouble(tokens[8]), tokens[10]));
					tempMatchList.get(matchCount).setMatchRating(Double.parseDouble(tokens[9]));
					
					matchCount++;
				}
				
				// If it's a selected program
				else if (tokens[0].charAt(0) == 'T') {
					tempTopList.add(new Program(tokens[1], tokens[2], tokens[3], tokens[4], Integer.parseInt(tokens[5]), Integer.parseInt(tokens[6]), 
							Integer.parseInt(tokens[7]), Double.parseDouble(tokens[8]), tokens[10]));
					tempTopList.get(tempCount).setMatchRating(Double.parseDouble(tokens[9]));
					
					tempCount++;
					
				}
				
				// If it's a selected program
				else if (tokens[0].charAt(0) == 'S') {
					tempSelectedList.add(new Program(tokens[1], tokens[2], tokens[3], tokens[4], Integer.parseInt(tokens[5]), Integer.parseInt(tokens[6]), 
							Integer.parseInt(tokens[7]), Double.parseDouble(tokens[8]), tokens[10]));
					tempSelectedList.get(selectedCount).setMatchRating(Double.parseDouble(tokens[9]));
					
					selectedCount++;
					
				}
			}
			loggedStudent.matchedPrograms = tempMatchList;
			loggedStudent.topPrograms = tempTopList;
			loggedStudent.setSelectedPrograms(tempSelectedList);

		} 
		
		catch (FileNotFoundException e) {
			// The user hasn't completed their profile
			return false;
		}
		
		return true;
	}
	
	/*
	 * Author: Daniel (75%) and Murphy (25%)
	 * This method creates a student object and adds data to the corresponding file
	 */
	public static void writeDataFromStudent() {
		try {
			Formatter formatter3;
			if (RegisterFrame.username == null) {
				formatter3 = new Formatter("files/" + LoginFrame.username + ".txt");
			}
			
			else {
				formatter3 = new Formatter("files/" + RegisterFrame.username + ".txt");
			}
			
			// Write names
			formatter3.format("%s%n", loggedStudent.getFirstName());
			formatter3.format("%s%n", loggedStudent.getLastName());
			
			// Write budget values
			formatter3.format("%.2f%n", loggedStudent.getMinBudget());
			formatter3.format("%.2f%n", loggedStudent.getMaxBudget());			
			
			// Weite address
			formatter3.format("%s%n", loggedStudent.getAddress());
			
			// Write interests
			formatter3.format("%s%n", loggedStudent.getInterests()[0]);
			formatter3.format("%s%n", loggedStudent.getInterests()[1]);
			formatter3.format("%s%n", loggedStudent.getInterests()[2]);
			
			// Write weightings
			formatter3.format("%.1f %.1f %.1f %.1f%n", loggedStudent.getWeightings()[0], loggedStudent.getWeightings()[1], loggedStudent.getWeightings()[2], loggedStudent.getWeightings()[3]);
			for (Map.Entry<String, Integer> entry : loggedStudent.getCourses().entrySet()) {
				formatter3.format("C %s %d%n", entry.getKey(), entry.getValue());
			}
			
			// Write admission average
			loggedStudent.calcAdmissionAverage();
			formatter3.format("%d%n", loggedStudent.getAdmissionAverage());
			
			// Write matched programs (if there are any)
			for (Program program : loggedStudent.matchedPrograms) {
				formatter3.format("M,%s,%s,%s,%s,%d,%d,%d,%.2f,%.2f,%s%n", program.getUniversityName(), program.getProgramName(), program.getInterestArea(), 
						program.getCoursePrerequisites(), program.getGradeMin(), program.getAvgSecondarySchoolAdmissionMarks(), program.getGradeMax(), 
						program.getTotalFee(), program.getMatchRating(), program.getLink());
			}
			
			// Write top programs (if there are any)
			for (Program program : loggedStudent.topPrograms) {
				formatter3.format("T,%s,%s,%s,%s,%d,%d,%d,%.2f,%.2f,%s%n", program.getUniversityName(), program.getProgramName(), program.getInterestArea(), 
						program.getCoursePrerequisites(), program.getGradeMin(), program.getAvgSecondarySchoolAdmissionMarks(), program.getGradeMax(), 
						program.getTotalFee(), program.getMatchRating(), program.getLink());
				
			}
			
			// Write selected programs (if there are any)
			for (Program program : loggedStudent.selectedPrograms) {
				formatter3.format("S,%s,%s,%s,%s,%d,%d,%d,%.2f,%.2f,%s%n", program.getUniversityName(), program.getProgramName(), program.getInterestArea(), 
						program.getCoursePrerequisites(), program.getGradeMin(), program.getAvgSecondarySchoolAdmissionMarks(), program.getGradeMax(), 
						program.getTotalFee(), program.getMatchRating(), program.getLink());
			}
			
			formatter3.close();
		}
		
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Author: Savannah
	 * This method reads files from the programs CSV and sends it to the programs arraylist
	 */
	public static void readPrograms() {
		try {
			Scanner reader = new Scanner(new File("files/Programs.csv"));
			reader.nextLine();
			while (reader.hasNextLine()) {
				Scanner sc = new Scanner(reader.nextLine());
				sc.useDelimiter(",");
				
				// Create new program and add it to the list
				allPrograms.add(new Program(sc.next(), sc.next(), sc.next(), sc.next(), Integer.parseInt(sc.next()), 
						Integer.parseInt(sc.next()), Integer.parseInt(sc.next()), Double.parseDouble(sc.next()), sc.next()));
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/*
	 * Author: Murphy Lee
	 */
	public static void matchingAlgorithm(Student student) {
		List<Program> tempMatchedPrograms = new ArrayList<Program>();
		
		// Loop through all programs found in the CSV
		for (Program program : allPrograms) {
			
			// Check first if the requirements for the course are met
			if (validProgram(program) == true && existsInSelectedPrograms(program) == false) {
				// Weighintgs of each field
				double interest1Weighting = student.getWeightings()[0];
				double interest2Weighting = student.getWeightings()[1];
				double interest3Weighting = student.getWeightings()[2];
				double budgetWeighting = student.getWeightings()[3];
				double gradeWeighting = 1;
				
				// Get the weighting of a program-interest field if it matches the users interests'. 
				double selectedInterestWeighting = student.getSelectedInterestWeighting(program.getInterestArea());
				
				// Calculate ratings for each program field with the student
				double interest1Rating = assignInterestRating(program, student.getInterests()[0]);
				double interest2Rating = assignInterestRating(program, student.getInterests()[1]);
				double interest3Rating = assignInterestRating(program, student.getInterests()[2]);
				double budgetRating = assignBudgetRating(program);
				double gradeRating = assignGradeRating(program);
				
				// Calculate percentage fulfilled by dividing the final score by the maximum possible score
				double maxRating = (selectedInterestWeighting * 5) + (budgetWeighting * 5) + (gradeWeighting * 5);
				double weightedRating = (interest1Rating * interest1Weighting) + (interest2Rating * interest2Weighting) + (interest3Rating * interest3Weighting) 
						+ (budgetRating * budgetWeighting) + (gradeRating * gradeWeighting);
				
				// Multiply the percentage by 5 to get a "rating" out of 5
				double score = 5 * (weightedRating / maxRating);
				program.setMatchRating(score);
				
				
				// Add program to the matched programs
				tempMatchedPrograms.add(program);
			}
		}
		
		student.matchedPrograms = tempMatchedPrograms;
		student.sortProgramsByRatingDesc(student.matchedPrograms);
		student.createTopPrograms();
	}
	
	/*
	 * Author: Murphy
	 * This method gives them a rating depending on if it fits the users' interest filed
	 */
	public static double assignInterestRating(Program program, String interest) {
		if (program.getInterestArea().equals(interest)) {
			return 5.0;
		}
		
		else {
			return 0.0;
		}
	}
	
	/*
	 * Author: Murphy
	 * This method gives them a rating depending on how well it fills the budget preferences
	 */
	public static double assignBudgetRating(Program program) {
		double studentMin = loggedStudent.getMinBudget();
		double studentMax = loggedStudent.getMaxBudget();
		
		double programFees = program.getTotalFee();
		
		// If fees are within grade range, return 5
		if (programFees >= studentMin && programFees <= studentMax) {
			return 5.0;
		}
			
		// If fees are less than or equal to $500 away, return 4.5
		else if ((programFees < studentMin && studentMin - programFees <= 500) || (programFees > studentMax && programFees - studentMax <= 500)) {
			return 4.5;
		}
		
		// If fees are less than or equal to $1000 away, return 4
		else if ((programFees < studentMin && studentMin - programFees <= 1000) || (programFees > studentMax && programFees - studentMax <= 1000)) {
			return 4.0;
		}
		
		// If fees are less than or equal to $1500 away, return 3.5
		else if ((programFees < studentMin && studentMin - programFees <= 1500) || (programFees > studentMax && programFees - studentMax <= 1500)) {
			return 3.5;
		}
		
		// If fees are less than or equal to $2000 away, return 3
		else if ((programFees < studentMin && studentMin - programFees <= 2000) || (programFees > studentMax && programFees - studentMax <= 2000)) {
			return 3.0;
		}
		
		// If fees are less than or equal to $2500 away, return 2.5
		else if ((programFees < studentMin && studentMin - programFees <= 2500) || (programFees > studentMax && programFees - studentMax <= 2500)) {
			return 2.5;
		}
		
		// If fees are less than or equal to $3000 away, return 2
		else if ((programFees < studentMin && studentMin - programFees <= 3000) || (programFees > studentMax && programFees - studentMax <= 3000)) {
			return 2.0;
		}
		
		// If fees are less than or equal to $3500 away, return 1.5
		else if ((programFees < studentMin && studentMin - programFees <= 3500) || (programFees > studentMax && programFees - studentMax <= 3500)) {
			return 1.5;
		}
		
		// If fees are more than $3500 away, return 1
		else  {
			return 1.0;
		}
	}
	
	/*
	 * Author: Murphy
	 * This method gives them a rating depending on how far the student is from the averaage grade of first year secondary school students
	 */
	public static double assignGradeRating(Program program) {
		int programMin = program.getGradeMin();
		int programMax = program.getGradeMax();
		int admissionAvg = loggedStudent.getAdmissionAverage();
		
		// If the student is within the program range, return 5
		if (admissionAvg >= programMin && admissionAvg <= programMax) {
			return 5.0;
		}
		
		// If the student is less than or equal to 2% away from program range, return 4.5
		else if ((admissionAvg < programMin && programMin - admissionAvg <= 2) || (admissionAvg > programMax && admissionAvg - programMax <= 2)) {
			return 4.5;
		}
		
		// If the student is less than or equal to 4% away from program range, return 4
		else if ((admissionAvg < programMin && programMin - admissionAvg <= 4) || (admissionAvg > programMax && admissionAvg - programMax <= 4)) {
			return 4;
		}
		
		// If the student is less than or equal to 6% away from program range, return 3.5
		else if ((admissionAvg < programMin && programMin - admissionAvg <= 6) || (admissionAvg > programMax && admissionAvg - programMax <= 6)) {
			return 3.5;
		}
		
		// If the student is less than or equal to 8% away from program range, return 3
		else if ((admissionAvg < programMin && programMin - admissionAvg <= 8) || (admissionAvg > programMax && admissionAvg - programMax <= 8)) {
			return 3;
		}
		
		// If the student is less than or equal to 10% away from program range, return 2.5
		else if ((admissionAvg < programMin && programMin - admissionAvg <= 10) || (admissionAvg > programMax && admissionAvg - programMax <= 10)) {
			return 2.5;
		}
		
		// If the student is less than or equal to 12% away from program range, return 2
		else if ((admissionAvg < programMin && programMin - admissionAvg <= 12) || (admissionAvg > programMax && admissionAvg - programMax <= 12)) {
			return 2;
		}
		
		// If the student is less than or equal to 14% away from program range, return 1.5
		else if ((admissionAvg < programMin && programMin - admissionAvg <= 14) || (admissionAvg > programMax && admissionAvg - programMax <= 14)) {
			return 1.5;
		}
		
		// If the student is over 14% away from program range, return 1
		else {
			return 1;
		}
	}
	
	/*
	 * Author: Savannah
	 * This method determines if a student fills the requirements of the course, by 
	 */
	private static boolean validProgram(Program program) {
		
		// All the course codes for the prerequisites are separated by a space
		String[] prerequisites = program.getCoursePrerequisites().split(" ");
		
		for (String course : prerequisites) {
			// If it conaints "/", then its a "one of the following courses" type requirements
			if (course.contains("/") == true) {
				String[] items = course.split("/");
				int currentCount = 0;
				int requiredNum = Integer.parseInt(items[0]);
				
				for (int i = 1; i < items.length; i++) {
					if (!(loggedStudent.getCourses().get(items[i]) == null)) {
						currentCount++;
					}
				}
				
				// If the user didn't take the required amount of conditional courses, return false
				if (currentCount < requiredNum) {
					return false;
				}
				
			}
			
			// Otherwise, just check if the course was taken by the student
			else {
				if (loggedStudent.getCourses().get(course) == null) {
					return false;
				}
			}
			
		}
		
		// Return false if the students average is lower than the cutoff
		if (loggedStudent.getAdmissionAverage() < program.getGradeMin()) {
			return false;
		}
		
		// The user has met the requirements
		return true;
	}
	
	/*
	 * Author: Murphy
	 * Linear-search function to find the index of an element
	 * https://www.geeksforgeeks.org/find-the-index-of-an-array-element-in-java/
	 */
    public static int findIndex(String arr[], String t)
    {
 
        // if array is Null
        if (arr == null) {
            return -1;
        }
 
        // find length of array
        int len = arr.length;
        int i = 0;
 
        // traverse in the array
        while (i < len) {
 
            // if the i-th element is t
            // then return the index
            if (arr[i].equals(t)) {
                return i;
            }
            else {
                i = i + 1;
            }
        }
        return -1;
    }
    
    /*
     * Author: Murphy
     * This method checks if a program exists in the selected programs list
     */
    private static boolean existsInSelectedPrograms(Program program)
    {
        boolean test = false;
        for (Program loopProgram : loggedStudent.selectedPrograms) {
            if (program.equals(loopProgram)) {
            	test = true;
            	break;
            }
        }
        
        return test;
        
    }
	
}
