package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.PostSecondaryController;

import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer;

// This class displays a register frame for the student
@SuppressWarnings("serial")
public class RegisterFrame extends JFrame {
	// Instance Variables
	private JPanel contentPane;
	private JTextField txtUsername;
	private JTextField txtPassword;
	private JTextField txtConfirmPassword;
	private JLabel errorLabel;
	public static String username;
	public static String password;

	// Color Palette
	private static final Color CREAM = new Color(245, 235, 234);
	private static final Color COOKIE = new Color(73, 59, 56);
	private static final Color SKY_BLUE = new Color(196, 232, 228);
	
	// Constructor Method
	public RegisterFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1920, 1080);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(CREAM);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// Top Menu Bar
		JPanel TitlePanel = new JPanel();
		TitlePanel.setBounds(0, 0, 1920, 74);
		TitlePanel.setBackground(SKY_BLUE);
		contentPane.add(TitlePanel);
		TitlePanel.setLayout(null);
		
		// Logo Label
		JLabel lblLogo = new JLabel();
		lblLogo.setIcon(new ImageIcon("images/Logo.png"));
		lblLogo.setBounds(0, 5, 1920, 64);
		lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
		TitlePanel.add(lblLogo);
		
		JPanel InputPanel = new JPanel();
		InputPanel.setBounds(508, 331, 904, 492);
		InputPanel.setBackground(SKY_BLUE);
		contentPane.add(InputPanel);
		InputPanel.setLayout(null);
		
		// Placeholder field to be the default selected field
		JTextField placeHolder = new JTextField();
		placeHolder.setText("");
		InputPanel.add(placeHolder);
		
		txtUsername = new JTextField();
		txtUsername.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (txtUsername.getText().equals("Username: ")) {
					txtUsername.setText("");	
				}
			}
		});
		
		// TextField for username
		txtUsername.setText("Username: ");
		txtUsername.setFont(new Font("Tahoma", Font.PLAIN, 25));
		txtUsername.setBounds(180, 43, 592, 90);
		InputPanel.add(txtUsername);
		txtUsername.setColumns(10);
		
		// TextField for password
		txtPassword = new JTextField();
		txtPassword.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (txtPassword.getText().equals("Password: ")) {
					txtPassword.setText("");	
				}
			}
		});
		txtPassword.setText("Password: ");
		txtPassword.setFont(new Font("Tahoma", Font.PLAIN, 25));
		txtPassword.setColumns(10);
		txtPassword.setBounds(180, 161, 592, 90);
		InputPanel.add(txtPassword);
		
		// Textfield to confirm the password
		txtConfirmPassword = new JTextField();
		txtConfirmPassword.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (txtConfirmPassword.getText().equals("Confirm Password: ")) {
					txtConfirmPassword.setText("");
				}
			}
		});
		txtConfirmPassword.setText("Confirm Password: ");
		txtConfirmPassword.setFont(new Font("Tahoma", Font.PLAIN, 25));
		txtConfirmPassword.setColumns(10);
		txtConfirmPassword.setBounds(180, 275, 592, 90);
		InputPanel.add(txtConfirmPassword);
		
		// Button to register user
		JButton btnRegister = new JButton("Register");
		btnRegister.setToolTipText("Click this button to register your account");
		btnRegister.setBounds(599, 377, 202, 43);
		InputPanel.add(btnRegister);
		
		// ActionListener - Validate input and display "Program Input Frame" if necessary
		btnRegister.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (validateInput() == true) {
					username = txtUsername.getText();
					password = txtPassword.getText();
					PostSecondaryController.addStudent();
					dispose();
					PostSecondaryController.frameState = "New Profile Input Frame";
					PostSecondaryController.newFrame = true;
					
				}
			}
		});
		
		// Button to go back to login page
		JButton btnBack = new JButton("Back");
		btnBack.setToolTipText("Click this button to go back to the login page");
		btnBack.setBounds(159, 376, 202, 43);
		InputPanel.add(btnBack);
		btnBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				PostSecondaryController.frameState = "Login";
				PostSecondaryController.newFrame = true;
			}
			
		});
		
		// Styling
		JPanel TopBorder = new JPanel();
		TopBorder.setBounds(0, 74, 1920, 20);
		TopBorder.setBackground(COOKIE);
		contentPane.add(TopBorder);
		
		JPanel BottomBorder = new JPanel();
		BottomBorder.setBounds(0, 1021, 1920, 20);
		BottomBorder.setBackground(COOKIE);
		contentPane.add(BottomBorder);
		
		JPanel LeftBorder = new JPanel();
		LeftBorder.setBounds(0, 84, 20, 996);
		LeftBorder.setBackground(COOKIE);
		contentPane.add(LeftBorder);
		
		JPanel RightBorder = new JPanel();
		RightBorder.setBounds(1884, 85, 20, 995);
		RightBorder.setBackground(COOKIE);
		contentPane.add(RightBorder);
		
		JPanel InputBorder = new JPanel();
		InputBorder.setBounds(478, 308, 962, 534);
		InputBorder.setBackground(Color.WHITE);
		contentPane.add(InputBorder);
		
		JPanel TitleBorder = new JPanel();
		TitleBorder.setBounds(855, 196, 248, 86);
		TitleBorder.setBackground(SKY_BLUE);
		contentPane.add(TitleBorder);
		TitleBorder.setLayout(null);
		
		JLabel RegisterLabel = new JLabel("     Register Now");
		RegisterLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
		RegisterLabel.setBounds(0, 0, 248, 86);
		TitleBorder.add(RegisterLabel);
		
		errorLabel = new JLabel();
		errorLabel.setFont(new Font("Helvetica", Font.BOLD, 20));
		errorLabel.setForeground(Color.RED);
		errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
		errorLabel.setBounds(0, 860, 1920, 30);
	}
	
	/*
	 * This method validates the input given by the user
	 */
	private boolean validateInput() {
		boolean valid = true;
		// The fields must be alphanumeric
		for (char character : txtUsername.getText().toCharArray()) {
			if (!(Character.isLetterOrDigit(character))) {
				errorLabel.setText("Username Can Only Contain AlphaNumeric Characters");
				valid = false;
			}
		}
		
		for (char character : txtPassword.getText().toCharArray()) {
			if (!(Character.isLetterOrDigit(character))) {
				errorLabel.setText("Password Can Only Contain AlphaNumeric Characters");
				valid = false;
			}
		}
		
		// The "password" and "confirm password" fields must match
		if (!(txtPassword.getText().equals(txtConfirmPassword.getText()))) {
			errorLabel.setText("Password and Confirm Password Don't Match");
			valid = false;
		}
		
		// The username and password must be longer than 4 characters
		else if (txtUsername.getText().length() <= 4) {
			errorLabel.setText("Username must be at least 5 characters");
			valid = false;
		}
		
		else if (txtPassword.getText().length() <= 4) {
			errorLabel.setText("Password must be at least 5 characters");
			valid = false;
		}
		
		// Check if the username is already taken
		Scanner scanner;
		try {
			scanner = new Scanner(new File("files/RegisteredAccounts.txt"));
			while (scanner.hasNextLine()) {
				StringTokenizer st = new StringTokenizer(scanner.nextLine());
				String atmpUser = st.nextToken();
				if (txtUsername.getText().equals(atmpUser)) {
					errorLabel.setText("This username is already in use. Choose another one");
					valid = false;
				}
			}
			
			scanner.close();
			
		} 
		catch (FileNotFoundException e) {
			valid = true;
		}
		
		
		getContentPane().add(errorLabel);
		revalidate();
		repaint();
		return valid;
	}
}