/*
 * Author: Murphy
 */

package model;

public class Program {
	// Attributes
	private String universityName;
	private String programName;
	private String interestArea;
	private String coursePrerequisites;
	private int gradeMin;
	private int avgSecondarySchoolAdmissionMarks;
	private int gradeMax;
	private double totalFee;
	private double matchRating;
	private String link;
	
	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	// Constructor Method

	public Program(String universityName, String programName, String interestArea, String coursePrerequisites,
			int gradeMin, int avgSecondarySchoolAdmissionMarks, int gradeMax, double totalFee, String link) {
		super();
		this.universityName = universityName;
		this.programName = programName;
		this.interestArea = interestArea;
		this.coursePrerequisites = coursePrerequisites;
		this.gradeMin = gradeMin;
		this.avgSecondarySchoolAdmissionMarks = avgSecondarySchoolAdmissionMarks;
		this.gradeMax = gradeMax;
		this.totalFee = totalFee;
		this.link = link;
	}

	// toString() method
	@Override
	public String toString() {
		return String.format("%s; %s; %d-%d; %.2f; %.2f", getUniversityName(), getProgramName(), getGradeMin(), getGradeMax(), getTotalFee(), getMatchRating());
	}
	
	public String toString2() {
		return String.format("%.2f %s %s %s %s %d %d %.2f %s%n", getMatchRating(), getUniversityName(), getProgramName(), getInterestArea(), getCoursePrerequisites(), getGradeMin(), getGradeMax(), getTotalFee(), getLink());
	}
	
	// Getters and Setters

	public String getUniversityName() {
		return universityName;
	}

	public void setUniversityName(String universityName) {
		this.universityName = universityName;
	}

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public String getInterestArea() {
		return interestArea;
	}

	public void setInterestArea(String interestArea) {
		this.interestArea = interestArea;
	}

	public String getCoursePrerequisites() {
		return coursePrerequisites;
	}

	public void setCoursePrerequisites(String coursePrerequisites) {
		this.coursePrerequisites = coursePrerequisites;
	}

	public int getGradeMin() {
		return gradeMin;
	}

	public void setGradeMin(int gradeMin) {
		this.gradeMin = gradeMin;
	}

	public int getAvgSecondarySchoolAdmissionMarks() {
		return avgSecondarySchoolAdmissionMarks;
	}

	public void setAvgSecondarySchoolAdmissionMarks(int avgSecondarySchoolAdmissionMarks) {
		this.avgSecondarySchoolAdmissionMarks = avgSecondarySchoolAdmissionMarks;
	}

	public int getGradeMax() {
		return gradeMax;
	}

	public void setGradeMax(int gradeMax) {
		this.gradeMax = gradeMax;
	}

	public double getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(double totalFee) {
		this.totalFee = totalFee;
	}

	public double getMatchRating() {
		return matchRating;
	}

	public void setMatchRating(double matchRating) {
		this.matchRating = matchRating;
	}
}
