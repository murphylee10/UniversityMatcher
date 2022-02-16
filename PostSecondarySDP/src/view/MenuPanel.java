/*
 * Author: Murphy Lee
 */
package view;

import javax.swing.*;
import java.awt.*;
import model.Student;

// This class extends JPanel, representing a menu bar with the logo, profile picture, and logout button
@SuppressWarnings("serial")
public class MenuPanel extends JPanel {
	// Instance Variables - Swing Components
	private JLabel logoLabel;
	public JButton profileButton;
	public JButton logoutButton;
	
	// Constructor Method
	public MenuPanel(Student student) {
		setLayout(null);
		setBounds(0, 0, 1920, 120);
//		setPreferredSize(new Dimension(1920, 120));
		setupDisplay(student);
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		setBackground(new Color(225, 225, 230));
	}
	
	// Utility Methods
	
	// This method adds the logo, profile button, and logout button to the panel
	private void setupDisplay(Student student) {
		// Add Logo to the center
		logoLabel = new JLabel();
		logoLabel.setIcon(new ImageIcon("images/Logo.png"));
		logoLabel.setBounds(650, 15, 602, 90);
		add(logoLabel);
		
		// Get the first letter of the students name, using it for the file path
		char firstInitial = student.getFirstName().charAt(0);
		String filePath = "images/" + String.valueOf(firstInitial) + ".png";
		
		// Create the button, setting the icon to the image specified by the file path
		profileButton = new JButton();
		profileButton.setIcon(new ImageIcon(filePath));
		profileButton.setBackground(new Color(225, 225, 230));
		profileButton.setOpaque(true);
		profileButton.setBorderPainted(false);
		
		// Change mouse to a pointer on hover
		profileButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		profileButton.setBounds(1830, 9, 65, 65);
		add(profileButton);
		
		// Button to log the user out of the page
		logoutButton = new JButton("Logout");
		logoutButton.setBounds(1828, 74, 68, 43);
		logoutButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		add(logoutButton);
		
	}
}