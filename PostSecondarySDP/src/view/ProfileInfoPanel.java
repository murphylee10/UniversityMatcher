/*
 * Author: Murphy Lee
 */

package view;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;

import model.Student;

// This class extends JPanel, displaying the student information to the profile frame
@SuppressWarnings("serial")
public class ProfileInfoPanel extends JPanel {
	// Instance Variables
	private String headerText;	
	private JLabel listHeader;

	private List<JLabel> categoryColumn = new ArrayList<JLabel>();
	private List<JLabel> categoryData = new ArrayList<JLabel>();
	
	/*
	 * Constructor Method
	 */
	public ProfileInfoPanel(Student student) {
		setLayout(null);
		setBackground(new Color(245, 235, 234));
		setBounds(0, 650, 1920, 460);
		
		// Header label
		setupHeader(student);
		
		// Table displaying student information
		setupInfoTable(student);
		
		// Table displaying course code and admission averages
		setupCourseTable(student);
       
	}
	
	/*
	 * This method displays a label, showing the students name
	 */
	private void setupHeader(Student student) {
		// Selected programs' header label - Add an "'s" unless the first name ends with an "s"
		String firstName = student.getFirstName();
		if (firstName.charAt(firstName.length() - 1) == 's') {
			headerText = student.getFirstName() + "'" + " Profile Data"; 			
		}
		else {
			headerText = student.getFirstName() + "'s" + " Profile Data"; 	
		}
		
		// Header Label for student data
		listHeader = new JLabel(headerText);
		listHeader.setFont(new Font("Helvetica", Font.PLAIN, 38));
		listHeader.setForeground(new Color(52, 31, 235));
		listHeader.setBounds(0, 20, 1920, 50);

		listHeader.setHorizontalAlignment(SwingConstants.CENTER);
		add(listHeader);
	}

	/*
	 * This method creates a table (JLabels) showing student information
	 */
	private void setupInfoTable(Student student) {
		// Create JLabels
		categoryColumn.add(new JLabel("Category"));
		categoryColumn.add(new JLabel("Admission Average (%)"));
		categoryColumn.add(new JLabel("Preferred Tuition Range (%)"));
		categoryColumn.add(new JLabel("Areas of Interest"));
		
		categoryData.add(new JLabel(String.format("%s's Results", student.getFirstName())));
		categoryData.add(new JLabel(String.format("%d", student.getAdmissionAverage())));
		categoryData.add(new JLabel(String.format("%.2f - %.2f", student.getMinBudget(), student.getMaxBudget())));
		categoryData.add(new JLabel(String.format("%s, %s, %s ", student.getInterests()[0], student.getInterests()[1], student.getInterests()[2])));
 
		// Styling for each label in the list
       for (int i = 0; i < categoryColumn.size(); i++) {
    	   categoryColumn.get(i).setFont(new Font("Sans Serif", Font.PLAIN, 16));
    	   categoryColumn.get(i).setBackground(new Color(235, 234, 222));
    	   categoryColumn.get(i).setOpaque(true);
    	   categoryColumn.get(i).setBorder(new LineBorder(Color.BLACK));
    	   categoryColumn.get(i).setHorizontalAlignment(SwingConstants.CENTER);
    	   categoryColumn.get(i).setBounds(100, 100 + (60 * i), 400, 60);
    	   add(categoryColumn.get(i));
    	   
    	   categoryData.get(i).setFont(new Font("Sans Serif", Font.PLAIN, 16));
    	   categoryData.get(i).setBackground(new Color(235, 234, 222));
    	   categoryData.get(i).setOpaque(true);
    	   categoryData.get(i).setBorder(new LineBorder(Color.BLACK));
    	   categoryData.get(i).setHorizontalAlignment(SwingConstants.CENTER);
    	   categoryData.get(i).setBounds(500, 100 + (60 * i), 400, 60);
    	   add(categoryData.get(i));
       }
 
       // Special background colours for header labels
       categoryColumn.get(0).setBackground(new Color(196, 232, 228));
       categoryData.get(0).setBackground(new Color(196, 232, 228));
	}
	
	/*
	 * This method displays a table of admission data
	 */
	private void setupCourseTable(Student student) {
		// Create 2 labels to indicate what the column shows
		JLabel courseCodeLabel = new JLabel("Course Code:");
	       courseCodeLabel.setBackground(new Color(196, 232, 228));
	       courseCodeLabel.setFont(new Font("Sans Serif", Font.PLAIN, 16));
	       courseCodeLabel.setOpaque(true);
	       courseCodeLabel.setBorder(new LineBorder(Color.BLACK));
	       courseCodeLabel.setHorizontalAlignment(SwingConstants.CENTER);
	       courseCodeLabel.setBounds(1000, 100, 400, 60);
	       add(courseCodeLabel);
	       
	       JLabel gradeLabel = new JLabel("Admission Mark:");
	       gradeLabel.setBackground(new Color(196, 232, 228));
	       gradeLabel.setFont(new Font("Sans Serif", Font.PLAIN, 16));
	       gradeLabel.setOpaque(true);
	       gradeLabel.setBorder(new LineBorder(Color.BLACK));
	       gradeLabel.setHorizontalAlignment(SwingConstants.CENTER);
	       gradeLabel.setBounds(1400, 100, 400, 60);
	       add(gradeLabel);
	       
	       // Create JTable
	       // Create 2D array from the hashmap
	       // https://stackoverflow.com/questions/2265266/convert-hash-map-to-2d-array
	       String[] columns = {"Course Code:", "Admission Mark:"};
	       Object[][] courses = new Object[student.getCourses().size()][2];
	       Set<Map.Entry<String, Integer>> entries = student.getCourses().entrySet();
	       Iterator<Map.Entry<String, Integer>> entriesIterator = entries.iterator();

	       int i = 0;
	       while(entriesIterator.hasNext()){

	           Map.Entry<String, Integer> mapping = (Map.Entry<String, Integer>) entriesIterator.next();

	           courses[i][0] = mapping.getKey();
	           courses[i][1] = mapping.getValue();

	           i++;
	       }
	       
	       // Create JTable from 2D array
	       JTable courseTable = new JTable(courses, columns);
	       courseTable.setRowHeight(60);
	       
	       // Stying of each cell
	       DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
	       cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
	       cellRenderer.setBackground(new Color(235, 234, 222));
	       cellRenderer.setFont(new Font("Sans Serif", Font.PLAIN, 16));
	       cellRenderer.setOpaque(true);
	       cellRenderer.setBorder(new LineBorder(Color.BLACK));
	       
	       // Dimensions of each cell
	       courseTable.getColumnModel().getColumn(0).setWidth(400);
	       courseTable.getColumnModel().getColumn(0).setMaxWidth(400);
	       courseTable.getColumnModel().getColumn(0).setMinWidth(400);
	       courseTable.getColumnModel().getColumn(0).setPreferredWidth(400);
	       courseTable.getColumnModel().getColumn(0).setCellRenderer(cellRenderer);
	       
	       courseTable.getColumnModel().getColumn(1).setWidth(400);
	       courseTable.getColumnModel().getColumn(1).setMaxWidth(400);
	       courseTable.getColumnModel().getColumn(1).setMinWidth(400);
	       courseTable.getColumnModel().getColumn(1).setPreferredWidth(400);
	       courseTable.getColumnModel().getColumn(1).setCellRenderer(cellRenderer);

	       // Add the table to a scroll pane
	       JScrollPane scrollpane = new JScrollPane(courseTable);
	       scrollpane.setPreferredSize(new Dimension(800, 190));
	       scrollpane.setBounds(1000, 144, 800, 198);
	       add(scrollpane);
	}
}
