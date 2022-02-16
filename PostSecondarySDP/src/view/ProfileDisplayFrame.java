/*
 * Author: Murphy Lee
 */
package view;

import javax.swing.*;

import controller.PostSecondaryController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.Student;


@SuppressWarnings("serial")
public class ProfileDisplayFrame extends JFrame {
	// Instance Variables
	
	private MenuPanel menuPanel;
	public SelectedProgramsPanel selectedProgramsPanel;
	private ProfileInfoPanel profileInfoPanel;
	
	
	public ProfileDisplayFrame() {
		super("Post Secondary App");
		Student student = PostSecondaryController.loggedStudent;
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1920, 1080);
		
		menuPanel = new MenuPanel(student);
		menuPanel.logoutButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				PostSecondaryController.loggedStudent = new Student();
				PostSecondaryController.frameState = "Login";
				PostSecondaryController.newFrame = true;
			}
			
		});
		add(menuPanel);
		
		selectedProgramsPanel = new SelectedProgramsPanel(student);
		add(selectedProgramsPanel);
		
		profileInfoPanel = new ProfileInfoPanel(student);
		add(profileInfoPanel);
	}
	
}
