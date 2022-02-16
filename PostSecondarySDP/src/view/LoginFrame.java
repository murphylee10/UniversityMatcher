/*
 * Author: Fawaz
 */

package view;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.PostSecondaryController;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JButton;

// This class extends JFrame, and displays a login page for the student
@SuppressWarnings("serial")
public class LoginFrame extends JFrame {
	// Instance Variables
	
	private JPanel contentPane;
	private JTextField txtUsername;
	private JTextField txtPassword;
	
	public static String username;

	private static final Color CREAM = new Color(245, 235, 234);
	private static final Color COOKIE = new Color(73, 59, 56);
	private static final Color SKY_BLUE = new Color(196, 232, 228);
	
	// Constructor Method
	public LoginFrame() {
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1920, 1080);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(CREAM);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel topPanel = new JPanel();
		topPanel.setBounds(0, 0, 1920, 74);
		topPanel.setBackground(SKY_BLUE);
		contentPane.add(topPanel);
		topPanel.setLayout(null);
		
		JLabel lblLogo = new JLabel();
		lblLogo.setIcon(new ImageIcon("images/Logo.png"));
		lblLogo.setBounds(0, 5, 1920, 64);
		lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
		topPanel.add(lblLogo);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(508, 331, 904, 492);
		panel_1.setBackground(SKY_BLUE);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		// Placeholder field to be the default selected field
		JTextField placeHolder = new JTextField();
		placeHolder.setText("");
		panel_1.add(placeHolder);
		
		// TextField for obtaining username
		txtUsername = new JTextField();
		txtUsername.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (txtUsername.getText().equals("Username: ")) {
					txtUsername.setText("");	
				}
			}
		});
		txtUsername.setFont(new Font("Tahoma", Font.PLAIN, 25));
		txtUsername.setBounds(190, 100, 592, 90);
		txtUsername.setText("Username: ");
		panel_1.add(txtUsername);
		txtUsername.setColumns(10);
		
		txtPassword = new JTextField();
		txtPassword.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (txtPassword.getText().equals("Password: ")) {
					txtPassword.setText("");	
				}
			}
		});
		
		// Textfield for obtaining password
		txtPassword.setText("Password: ");
		txtPassword.setFont(new Font("Tahoma", Font.PLAIN, 25));
		txtPassword.setColumns(10);
		txtPassword.setBounds(190, 200, 592, 90);
		panel_1.add(txtPassword);
		
		// Login button
		JButton loginButton = new JButton("Login");
		loginButton.setToolTipText("Click this button to log in");
		loginButton.setBounds(600, 350, 202, 43);
		panel_1.add(loginButton);
		
		// ActionListener for login button - Validate, and bring to new frame if necessary
		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (validateInput() == true) {
					username = txtUsername.getText();
					dispose();
					if (PostSecondaryController.instantiateStudentFromLogin() == false) {
						PostSecondaryController.frameState = "New Profile Input Frame";
						PostSecondaryController.newFrame = true;
					}
					
					else {
						PostSecondaryController.frameState = "Profile Display Frame From Login";
						PostSecondaryController.newFrame = true;
						
					}
				}
			}
		});
		
		JLabel notUserLabel = new JLabel("Not a User?");
		notUserLabel.setBounds(200, 300, 261, 36);
		panel_1.add(notUserLabel);
		
		// Button to bring user to register frame
		JButton registerButton = new JButton("Register Now");
		registerButton.setToolTipText("Click this button to register a new account");
		registerButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				PostSecondaryController.frameState = "Register";
				PostSecondaryController.newFrame = true;				
			}
			
		});
		registerButton.setBounds(200, 350, 208, 36);
		panel_1.add(registerButton);
		
		JLabel headerLabel = new JLabel("UNIMATCH LOGIN");
		headerLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
		headerLabel.setBounds(855, 196, 242, 86);
		contentPane.add(headerLabel);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(0, 74, 1920, 20);
		panel_2.setBackground(COOKIE);
		contentPane.add(panel_2);
		
		JPanel panel_2_1 = new JPanel();
		panel_2_1.setBounds(0, 1060, 1920, 20);
		panel_2_1.setBackground(COOKIE);
		contentPane.add(panel_2_1);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(0, 84, 20, 996);
		panel_3.setBackground(COOKIE);
		contentPane.add(panel_3);
		
		JPanel panel_3_1 = new JPanel();
		panel_3_1.setBounds(1884, 85, 20, 995);
		panel_3_1.setBackground(COOKIE);
		contentPane.add(panel_3_1);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBounds(478, 308, 962, 534);
		panel_4.setBackground(Color.WHITE);
		contentPane.add(panel_4);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBounds(845, 196, 252, 86);
		panel_5.setBackground(SKY_BLUE);
		contentPane.add(panel_5);
		
		
		
	}
	
	/*
	 * This method checks if the username is found in "RegisteredAccounts.txt", and also checks if the password matches
	 */
	private boolean validateInput() {
		// This label is displayed when the username or passwrod is invalid
		JLabel errorLabel = new JLabel();
		errorLabel.setFont(new Font("Helvetica", Font.BOLD, 20));
		errorLabel.setForeground(Color.RED);
		errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
		errorLabel.setBounds(0, 860, 1920, 30);
		
		Scanner scanner;
		try {
			scanner = new Scanner(new File("files/RegisteredAccounts.txt"));
			while (scanner.hasNextLine()) {
				StringTokenizer st = new StringTokenizer(scanner.nextLine());
				String username = st.nextToken();
				String password = st.nextToken();
				
				if (txtUsername.getText().equals(username) && txtPassword.getText().equals(password)) {
					return true;
				}
				
				else {
					errorLabel.setText("Invalid Username or Password");		
				}
			}
			
			scanner.close();
			
		} 
		catch (FileNotFoundException e) {
		}
		
		errorLabel.setText("Invalid Username or Password");		
		
		// Add error label with text
		getContentPane().add(errorLabel);
		revalidate();
		repaint();
		return false;
	}

}