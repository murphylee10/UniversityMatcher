/*
 * Author: Murphy Lee
 */

package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

import javax.swing.*;

import controller.PostSecondaryController;
import model.Program;
import model.Student;

@SuppressWarnings("serial")
// This class extends JPanel, displaying all selected programs to the screen
public class SelectedProgramsPanel extends JPanel {
	// Instance Variables
	private String headerText;
	private JLabel listHeader;
	private JLabel exampleLabel;
	private ArrayList<String> studentPrograms;
	public JList<String> programsList;
	public JScrollPane scrollPane;
	private DefaultListModel<String> model;
	
	// GUI Components
	private JButton addProgramButton;
	private JButton removeProgramButton;
	private JButton editProfileButton;
	private JLabel dropdownLabel;
	private SortDropdown sortDropdown;
	
	// Constructor Method
	public SelectedProgramsPanel(Student student) {
		setLayout(null);
		setBackground(new Color(245, 235, 234));
		setBounds(0, 120, 1920, 540);
		
		setupLabel(student);
		
		setupSortDropdown(student);
		
		setupList(student);
		
		setupButtons(student);
	}
	
	/*
	 * This method sets up the header label for the text
	 */
	private void setupLabel(Student student) {
		// Selected programs' header label - Add an "'s" unless the first name ends with an "s"
		String firstName = student.getFirstName();
		if (firstName.charAt(firstName.length() - 1) == 's') {
			headerText = student.getFirstName() + "'" + " Selected Programs"; 
		}
		else {
			headerText = student.getFirstName() + "'s" + " Selected Programs"; 	
		}
		
		// Header label for selected programs
		listHeader = new JLabel(headerText);
		listHeader.setFont(new Font("Helvetica", Font.PLAIN, 38));
		listHeader.setForeground(new Color(52, 31, 235));
		listHeader.setBounds(0, 10, 1920, 100);
		listHeader.setHorizontalAlignment(SwingConstants.CENTER);
		add(listHeader);
		
		// Example label showing how text should be read
		exampleLabel = new JLabel("Program Name ; University Name ; Grade Range ; Total Fees (Tuition + Ancillary ; Rating (/5)");
		exampleLabel.setFont(new Font("Helvetica", Font.PLAIN, 22));
		exampleLabel.setBackground(new Color(196, 232, 228));
		exampleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		exampleLabel.setOpaque(true);
		exampleLabel.setBounds(450, 90, 1000, 50);
		add(exampleLabel);
	}
	
	private void setupSortDropdown(Student student) {
		// Sort dropdown label and combobox
		dropdownLabel = new JLabel("Sort Programs:");
		dropdownLabel.setFont(new Font("Sans Serif", Font.PLAIN, 16));
		dropdownLabel.setBounds(1310, 30, 200, 20);
		add(dropdownLabel);
		
		sortDropdown = new SortDropdown(student, student.selectedPrograms);
		sortDropdown.setToolTipText("Use this dropdown to sort programs based on a given category");
		sortDropdown.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		sortDropdown.setBounds(1273, 50, 178, 35);
		add(sortDropdown);
	}

	/*
	 * This method creates & displays a JList of all the selected programs
	 */
	public void setupList(Student student) {
		// List<String> of programs selected by the user
		studentPrograms = new ArrayList<String>();
		for (int i = 0; i < student.getSelectedPrograms().size(); i++) {
			studentPrograms.add(student.getSelectedPrograms().get(i).toString());
		}
		
		// JList of selected student programs
		// https://www.tabnine.com/code/java/classes/javax.swing.DefaultListModel
		model = new DefaultListModel<String>();
		programsList = new JList<String>();
		programsList.setModel(model);
		for (String program : studentPrograms) {
			model.addElement(program);
		}
		
		programsList.setFont(new Font("Helvetica", Font.PLAIN, 24));
		programsList.setFixedCellHeight(50);

		// Align program text to the center
		DefaultListCellRenderer renderer =  (DefaultListCellRenderer) programsList.getCellRenderer();  
		renderer.setHorizontalAlignment(JLabel.CENTER);
		
		// Add JList to a scrollpane, making the list scrollable
		scrollPane = new JScrollPane();
		scrollPane.setBounds(450, 140, 1000, 300);
		scrollPane.setViewportView(programsList);
		programsList.setLayoutOrientation(JList.VERTICAL);
		add(scrollPane);
	}
	
	/*
	 * This method creates the buttons to add & remove a program, and edit a profile
	 */
	private void setupButtons(Student student) {
		// Create and style the "Remove" button
		removeProgramButton = new JButton("Remove");
		removeProgramButton.setToolTipText("Click this button to remove the selected program");
		removeProgramButton.setFont(new Font("Helvetica", Font.PLAIN, 22));
		removeProgramButton.setBackground(new Color(247, 89, 89));
		removeProgramButton.setOpaque(true);
		removeProgramButton.setBorderPainted(false);
		removeProgramButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		removeProgramButton.setHorizontalAlignment(SwingConstants.CENTER);
		removeProgramButton.setBounds(1303, 450, 145, 50);
		add(removeProgramButton);
		
		// ActionListener for the remove button - Remove item from JList
		removeProgramButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int[] indices = programsList.getSelectedIndices();
				
				// Show an error message if the user didn't select a program
				if (indices.length == 0) {
					
					final JFrame error = new JFrame();
					JOptionPane.showMessageDialog(error, "You must select a program first!");
					error.setVisible(true);
					
				}
				
				// Otherwise, remove the selected programs
				else {
					List<Program> tempList = new ArrayList<Program>();
					
					// Store all the indices of selected programs
					for (int index : indices) {
						tempList.add(student.getSelectedPrograms().get(index));
						System.out.println(index + "\n");
					}
					
					// Remove JList items
					int ind = programsList.getSelectedIndices().length - 1;
					while (programsList.getSelectedIndices().length != 0) {
						model.removeElementAt(programsList.getSelectedIndices()[ind--]);
						
					}
					
					student.removeProgramsFromSelected(tempList);
					
					PostSecondaryController.writeDataFromStudent();
				}
				
			}
			
		});
		
		
		// Create and style the "Add" button
		addProgramButton = new JButton("Add");
		addProgramButton.setToolTipText("Click this button to add more programs");
		addProgramButton.setFont(new Font("Helvetica", Font.PLAIN, 22));
		addProgramButton.setBackground(new Color(167, 255, 146));
		addProgramButton.setOpaque(true);
		addProgramButton.setBorderPainted(false);
		addProgramButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		addProgramButton.setHorizontalAlignment(SwingConstants.CENTER);
		addProgramButton.setBounds(1148, 450, 145, 50);
		add(addProgramButton);
		
		// ActionListener for the add button - bring to "Program Match Frame" to select programs
		addProgramButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PostSecondaryController.profileDisplayFrame.dispose();
				PostSecondaryController.frameState = "Program Match Frame";
				PostSecondaryController.newFrame = true;
			}
			
		});
		
		// Create and style the "Edit Profile" button
		editProfileButton = new JButton("Edit Profile");
		editProfileButton.setToolTipText("Click this button to edit your profile");
		editProfileButton.setFont(new Font("Helvetica", Font.PLAIN, 22));
		editProfileButton.setBackground(new Color(247, 242, 161));
		editProfileButton.setOpaque(true);
		editProfileButton.setBorderPainted(false);
		editProfileButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		editProfileButton.setHorizontalAlignment(SwingConstants.CENTER);
		editProfileButton.setBounds(963, 450, 175, 50);
		add(editProfileButton);
		
		// ActionListener for the "Edit Profile" button - Bring to "Profile Input Frame" to update data
		editProfileButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PostSecondaryController.profileDisplayFrame.dispose();
				PostSecondaryController.frameState = "Program Input Frame With Data";
				PostSecondaryController.newFrame = true;
			}
			
		});
	}
}
