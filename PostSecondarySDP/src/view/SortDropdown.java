/*
 * Author: Murphy 
 */
package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import controller.PostSecondaryController;
import model.Program;
import model.Student;

// This class extends JComboBox, and is used to sort programs
@SuppressWarnings("serial")
public class SortDropdown extends JComboBox<String> {
	// Instance Variables
	public static final String[] OPTIONS = {"University Name", "Program Name", "Grade Range (Desc)", "Grade Range (Asc)", "Tuition (Desc)", "Tuition (Asc)", "Match Rating (Desc)", "Match Rating (Asc)"};
	
	// Constructor Method
	public SortDropdown(Student student, List<Program> programs) {
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>(OPTIONS);
		setModel(model);
		
		setSelectedIndex(6);
	        
		
		// When the user selects a new item, initiate a new sorting algorithm
        addActionListener(new ActionListener()
        {	
			@Override
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unchecked")
				JComboBox<String> cb = (JComboBox<String>) e.getSource();
				String selectedText = cb.getSelectedItem().toString();
				
				if (selectedText.equals("University Name")) {
					student.sortProgramsByUniversity(programs);
				}
				
				else if (selectedText.equals("Program Name")) {
					student.sortProgramsByProgramName(programs);
				}
				
				else if (selectedText.equals("Grade Range (Desc)")) {
					student.sortProgramsByGradeDesc(programs);
				}
				
				else if (selectedText.equals("Grade Range (Asc)")) {
					student.sortProgramsByGradeAsc(programs);
				}
				
				else if (selectedText.equals("Tuition (Desc)")) {
					student.sortProgramsByTuitionDesc(programs);
				}
				
				else if (selectedText.equals("Tuition (Asc)")) {
					student.sortProgramsByTuitionAsc(programs);
				}
				
				else if (selectedText.equals("Match Rating (Desc)")) {
					student.sortProgramsByRatingDesc(programs);
				}
				
				else if (selectedText.equals("Match Rating (Asc)")) {
					student.sortProgramsByRatingAsc(programs);
				}
				
				
				for (Program program : programs) {
					System.out.println(program);
					
				}
				
				// If the action was done by the program match frame, rearrange the panels
				if (PostSecondaryController.frameState.equals("Program Match Frame")) {
					ProgramMatchResultPanel p = PostSecondaryController.programMatchFrame.programMatchResultPanel;
					p.remove(p.scrollPane);
					p.setupScrollPane();
					PostSecondaryController.programMatchFrame.revalidate();
					PostSecondaryController.programMatchFrame.repaint();
				}
				
				// If the action was done by the profile display page, rearrange the list
				else if (PostSecondaryController.frameState.equals("Profile Display Frame")) {
					SelectedProgramsPanel s = PostSecondaryController.profileDisplayFrame.selectedProgramsPanel;
					s.remove(s.scrollPane);
					s.setupList(student);
					PostSecondaryController.profileDisplayFrame.revalidate();
					PostSecondaryController.profileDisplayFrame.repaint();
					
				}
			}
		
	    });
	}

}
