/*
 * Author: Savannah (90%), Murphy (10%)
 */

package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import controller.PostSecondaryController;

import java.util.ArrayList;
import java.util.List;

import model.Program;
import model.Student;

// This class extends JPanel, acting as the control panel for the user to save their choices
@SuppressWarnings("serial")
public class ProgramMatchResultPanel extends JPanel implements ActionListener{
	// Instance Variables
	/*panel contains:
	 * - menu panel
	 * - title panel
	 * - scroll pane
	 * - all programs panel on scroll pane*/
	
	private MenuPanel menuPanel = null;
	
	private JPanel titlePanel = new JPanel();
	private JLabel titleLabel = new JLabel("Top Matched Programs");
	private JButton saveProgramButton = new JButton("Save My Choice");
	private JPanel topPanelWrap = new JPanel();
	JScrollPane scrollPane = null;
	
	private JLabel dropdownLabel;
	private SortDropdown sortDropdown;
	
	private AllProgramsPanel allProgramsPanel;
	
	ArrayList<JCheckBox> radioButtonGroup = new ArrayList<JCheckBox>();
	
	private Student student;
	private List<Program> programs;
	
	// Unimatch color palette
	private UnimatchColorPalette colors = new UnimatchColorPalette();
	
	/*
	 * Constructor Method
	 */
	public ProgramMatchResultPanel(Student student, List<Program> programs) {
		this.student = student;
		this.programs = programs;
		
		setLayout(new BorderLayout());
		topPanelWrap.setLayout(new BoxLayout(topPanelWrap, BoxLayout.PAGE_AXIS));
		
		// Setup panels
		// Menu panel
		setupMenuPanel();
		
		
		// Title panel and top panel wrap
		topPanelWrap.add(titlePanel);
		add(topPanelWrap, BorderLayout.PAGE_START);
		setupTitlePanel();
		
		// Scroll pane
		setupScrollPane();
	}
	
	/*
	 * This method displays the menu panel
	 */
	private void setupMenuPanel() {
		menuPanel = new MenuPanel(student);
		menuPanel.setPreferredSize(new Dimension(1910, 120));
		topPanelWrap.add(menuPanel);
		menuPanel.setBackground(colors.getBLUECUSTOM());
		menuPanel.logoutButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				PostSecondaryController.programMatchFrame.dispose();
				PostSecondaryController.loggedStudent = new Student();
				PostSecondaryController.frameState = "Login";
				PostSecondaryController.newFrame = true;
				
			}
			
		});
		
		// Add ActionListener to the profile button
		menuPanel.profileButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				PostSecondaryController.programMatchFrame.dispose();
				PostSecondaryController.frameState = "Profile Display Frame";
				PostSecondaryController.newFrame = true;
			}
			
		});
		
	}
	
	// This method sets up the button to save program choices, and displays an overall header panel
	private void setupTitlePanel() {
		titlePanel.setLayout(null);
		titlePanel.setPreferredSize(new Dimension(1910, 80));
		titlePanel.setBackground(colors.getSKY_BLUE());
		
		// Title label and save program button
		titlePanel.add(titleLabel);	
		titlePanel.add(saveProgramButton);
		
		// Button to select program choices
		saveProgramButton.addActionListener(this);
		saveProgramButton.setToolTipText("Click this button to save your program choices");
		saveProgramButton.setBackground(colors.getNAVY_BLUE());
		saveProgramButton.setForeground(Color.WHITE);
		saveProgramButton.setOpaque(true);
		saveProgramButton.setBorderPainted(false);
		
		// Title label
		titleLabel.setBounds(20, 10, 500, 80);
		saveProgramButton.setBounds(1470, 20, 230, 40);
		
		saveProgramButton.setFont(new Font("Calibri", Font.BOLD, 20));
		titleLabel.setFont(new Font("Calibri", Font.BOLD, 25));
		
		saveProgramButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		// Sort dropdown label and combobox
		dropdownLabel = new JLabel("Sort Programs:");
		dropdownLabel.setFont(new Font("Sans Serif", Font.PLAIN, 16));
		dropdownLabel.setBounds(1730, 15, 200, 20);
		titlePanel.add(dropdownLabel);
		
		sortDropdown = new SortDropdown(student, programs);
		sortDropdown.setToolTipText("Use this dropdown to sort programs based on a given category");
		sortDropdown.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		sortDropdown.setBounds(1723, 35, 178, 35);
		titlePanel.add(sortDropdown);
		
		titlePanel.setVisible(true);
	}
	
	// This method adds programs to a scrollpane
	public void setupScrollPane() {
		allProgramsPanel = new AllProgramsPanel(programs);
		scrollPane = new JScrollPane(allProgramsPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setPreferredSize(new Dimension(1790, 600));
		add(scrollPane, BorderLayout.CENTER);
		scrollPane.setBackground(colors.getCOOKIE());
	}
	
	// ActionListener - sends saved program info to student info
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().getClass().getSimpleName().equals("JButton")) {
			List<Program> selectedPrograms = new ArrayList<Program>();
			
			ArrayList<Integer> selectedRanks = allProgramsPanel.getAllSelectedProgramRanks();
			for(int i = 0; i < selectedRanks.size(); i++) {
				selectedPrograms.add(allProgramsPanel.getProgramByRank(selectedRanks.get(i)));
			}
			System.out.println(selectedRanks);
			if (selectedPrograms.isEmpty()) {
				final JFrame error = new JFrame();
				JOptionPane.showMessageDialog(error, "No selected programs!");
				error.setVisible(true);
			}
			else
			{
				
				student.addProgramsToSelected(selectedPrograms);
//				PostSecondaryController.writeDataFromStudent();
				PostSecondaryController.programMatchFrame.dispose();
				PostSecondaryController.frameState = "Profile Display Frame";
				PostSecondaryController.newFrame = true;
			}
		}
	}
}
