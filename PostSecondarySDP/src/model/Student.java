package model;

import java.util.*;

import controller.PostSecondaryController;

public class Student {
	// Instance Variables
	
	private String firstName;
	private String lastName;
	
	private double minBudget;
	private double maxBudget;
	
	private int admissionAverage;
	
	private String address;
	
	private String[] interests;
	private double[] weightings;
		
	public List<Program> matchedPrograms = new ArrayList<Program>();
	public List<Program> selectedPrograms = new ArrayList<Program>();
	public List<Program> topPrograms = new ArrayList<Program>();

	private Map<String, Integer> courses;

	// Getters and Setters
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public double getMinBudget() {
		return minBudget;
	}

	public void setMinBudget(double minBudget) {
		this.minBudget = minBudget;
	}

	public double getMaxBudget() {
		return maxBudget;
	}

	public void setMaxBudget(double maxBudget) {
		this.maxBudget = maxBudget;
	}

	public String[] getInterests() {
		return interests;
	}

	public void setInterests(String[] interests) {
		this.interests = interests;
	}

	public double[] getWeightings() {
		return weightings;
	}

	public void setWeightings(double[] weightings) {
		this.weightings = weightings;
	}

	public List<Program> getSelectedPrograms() {
		return selectedPrograms;
	}
	
	public void setSelectedPrograms(List<Program> selectedPrograms) {
		this.selectedPrograms = selectedPrograms;
	}

	public Map<String, Integer> getCourses() {
		return courses;
	}

	public void setCourses(Map<String, Integer> courses) {
		this.courses = courses;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public int getAdmissionAverage() {
		return admissionAverage;
	}
	
	public void setAdmissionAverage(int average) {
		this.admissionAverage = average;
	}
	
	
	public double getSelectedInterestWeighting(String interestField) {
		if (interestField.equals(interests[0])) {
			return weightings[0];
		}
		else if (interestField.equals(interests[1])) {
			return weightings[1];
		}
		else if (interestField.equals(interests[2])) {
			return weightings[2];
		}
		
		// Return the highest weighting
		else {
			return Math.min(weightings[0], Math.min(weightings[1], weightings[2]));
		}
	}
	
	// Utility methods
	
	/*
	 * This methods creates a new list of the top 10 rated programs
	 */
	public void createTopPrograms() {
		List<Program> tempProgramsList = new ArrayList<Program>();
		// Loop through all matched programs if there are less than 10
		if (matchedPrograms.size() <= 9) {
			for (int index = 0; index < matchedPrograms.size(); index++) {
				Program program = matchedPrograms.get(index);
				tempProgramsList.add(program);
				matchedPrograms.remove(index);
			}
		}
		
		// Otherwise, only loop through the first 10 elements
		else {
			for (int index = 0; index < 10; index++) {
				Program program = matchedPrograms.get(index);
				tempProgramsList.add(program);
				matchedPrograms.remove(index);
			}
		}
		
		topPrograms = tempProgramsList;
	}
	
	/*
	 * This method adds a list of preograms to a users selected programs
	 */
	public void addProgramsToSelected(List<Program> programs) {
		for (Program program : programs) {
			// Transfer programs to selected list
			
			selectedPrograms.add(program);
			topPrograms.remove(program);
			
			// Fill gaps in top programs with programs from matched programs
			Program program2 = matchedPrograms.get(0);
			topPrograms.add(program2);
			matchedPrograms.remove(program2);
		}
		
		sortProgramsByRatingDesc(selectedPrograms);
		sortProgramsByRatingDesc(topPrograms);
		PostSecondaryController.writeDataFromStudent();
	}
	
	/*
	 * This method removes programs from the selected programs list
	 */
	public void removeProgramsFromSelected(List<Program> programs) {
		for (Program program : programs) {
			// Transfer program from selected programs to top programs
			selectedPrograms.remove(program);
			topPrograms.add(0, program);
			
			sortProgramsByRatingDesc(topPrograms);
			
			// Transfer bottom program from top programs to matched programs if there are more than 10 programs
			if (topPrograms.size() > 10) {
				Program program2 = topPrograms.get(topPrograms.size() - 1);
				topPrograms.remove(program2);
				matchedPrograms.add(program2);
				
			}
		}
		sortProgramsByRatingDesc(topPrograms);
		sortProgramsByRatingDesc(matchedPrograms);
		
		PostSecondaryController.writeDataFromStudent();
	}
	
	// This method sorts programs by their match rating (descending order)
	public void sortProgramsByRatingDesc(List<Program> programs) {
		Collections.sort(programs, new Comparator<Program>() {
			
			@Override
			public int compare(Program o1, Program o2) {
				return Double.compare(o2.getMatchRating(), o1.getMatchRating());
			}
			
		});
	}
	
	/*
	 * This method sorts programs by their match rating (ascending order)
	 */
	public void sortProgramsByRatingAsc(List<Program> programs) {
		Collections.sort(programs, new Comparator<Program>() {
			
			@Override
			public int compare(Program o1, Program o2) {
				return Double.compare(o1.getMatchRating(), o2.getMatchRating());
			}
			
		});
	}
	
	/*
	 * This method sorts programs by their university
	 */
	public void sortProgramsByUniversity(List<Program> programs) {
		Collections.sort(programs, new Comparator<Program>() {

			@Override
			public int compare(Program o1, Program o2) {
				return o1.getUniversityName().compareTo(o2.getUniversityName());
			}
		});
	}
	
	/*
	 * This method sorts programs by their name
	 */
	public void sortProgramsByProgramName(List<Program> programs) {
		Collections.sort(programs, new Comparator<Program>() {
			
			@Override
			public int compare(Program o1, Program o2) {
				return o1.getProgramName().compareTo(o2.getProgramName());
			}
		});
		
	}
	
	/*
	 * This method sorts programs by grade range (descending order)
	 */
	public void sortProgramsByGradeDesc(List<Program> programs) {
		Collections.sort(programs, new Comparator<Program>() {
			
			@Override
			public int compare(Program o1, Program o2) {
				return o2.getGradeMin() - o1.getGradeMin();
			}
		});
		
	}
	
	/*
	 * This method sorts the program by grade range (ascending order)
	 */
	public void sortProgramsByGradeAsc(List<Program> programs) {
		Collections.sort(programs, new Comparator<Program>() {
			
			@Override
			public int compare(Program o1, Program o2) {
				return o1.getGradeMin() - o2.getGradeMin();
			}
		});
	}
	
	/*
	 * This method sorts the program by tuition (ascending order)
	 */
	public void sortProgramsByTuitionAsc(List<Program> programs) {
		Collections.sort(programs, new Comparator<Program>() {
			
			@Override
			public int compare(Program o1, Program o2) {
				return Double.compare(o1.getTotalFee(), o2.getTotalFee());
			}
		});
		
	}
	
	/*
	 * This method sorts the program by tuition (descending order)
	 */
	public void sortProgramsByTuitionDesc(List<Program> programs) {
		Collections.sort(programs, new Comparator<Program>() {
			
			@Override
			public int compare(Program o1, Program o2) {
				return Double.compare(o2.getTotalFee(), o1.getTotalFee());
			}
		});
		
	}
	
	/*
	 * This method calculates the students admission average
	 */
	public void calcAdmissionAverage() {
		int sum = 0;
		
		List<Integer> marks = new ArrayList<Integer>(courses.values());
		
		Collections.reverse(marks);
		for (int i = 0; i < 6; i++) {
			sum += marks.get(i);
		}
		
		admissionAverage = (int) Math.round((double) sum / 6) ;
	}
}

