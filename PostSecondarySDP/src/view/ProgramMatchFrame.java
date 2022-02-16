/*
 * Author: Savannah
 */
package view;


import java.awt.BorderLayout;
import java.awt.Container;
import java.util.List;

import javax.swing.*;

import controller.PostSecondaryController;
import model.Program;
import model.Student;

// This class extends JFrame, showing matched programs
@SuppressWarnings("serial")
public class ProgramMatchFrame extends JFrame {
	// Instance Variables
	Student student = PostSecondaryController.loggedStudent;
	public ProgramMatchResultPanel programMatchResultPanel;
	
	/*
	 * Constructor Method
	 */
	public ProgramMatchFrame(List<Program> programs) {
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container panel = this.getContentPane();
		programMatchResultPanel = new ProgramMatchResultPanel(student, programs);
		panel.add(programMatchResultPanel, BorderLayout.CENTER);
		
		pack();
	}
}

