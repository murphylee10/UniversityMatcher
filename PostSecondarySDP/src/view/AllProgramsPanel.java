/**
 * 
 * Author: Savannah
 *
 */

package view;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import model.Program;

// This class extends JPanel and implements ActionListener, displaying all of the matched programs
@SuppressWarnings("serial")
public class AllProgramsPanel extends JPanel implements ActionListener {
	// Instance Variables
	private ProgramPanel[] programPanels;
	private List<Program> programs;
	
	/*
	 * Constructor Method
	 */
	public AllProgramsPanel(List<Program> programs) {
		setLayout(null);
		this.programs = programs;
		
		programPanels = new ProgramPanel[programs.size()];
		
		displayPrograms();
		
		setVisible(true);
	}
	/*
	 * This method creates a new panel for each program
	 */
	private void displayPrograms() {
		for(int i = 0; i < programPanels.length; i++) {
			// Adds program panel to program panel array
			programPanels[i] = new ProgramPanel(this, programs.get(i), i);
			add(programPanels[i]);
			programPanels[i].setBounds(10, 10 + 60 * i, 1880, 60);
		}
		setPreferredSize(new Dimension(1700, 60 * programs.size()));
	}

	// Getters and Setters
	
	public void setProgramName(String programName, int programRank) {
		programPanels[programRank].getProgramNameLabel().setText(programName);
	}
	public void setUniversityName(String universityName, int programRank) {
		programPanels[programRank].getUniversityNameLabel().setText(universityName);
	}
	public void setProgramDescription(String description, int programRank) {
		programPanels[programRank].getProgramDescriptionLabel().setText(description);
	}

	/*
	 * ActionListener for the panel
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String className = e.getSource().getClass().getSimpleName();
		switch(className) {
			case "ProgramPanel":
				ProgramPanel curProgramPanel = (ProgramPanel)e.getSource();
				int rank = curProgramPanel.getRank();
			
				//add or subtract program panel's description label height to the all program panel
				//move programs up - true, move programs down - false
				movePrograms(rank, !programPanels[rank].isExpanded());
				Dimension dimension = this.getPreferredSize();
				dimension.height += (programPanels[rank].isExpanded() ? 1 : -1) * programPanels[rank].getDescriptionLabelSize();
				setPreferredSize(dimension);
		}
	}
	/*
	 * Move programs either up or down after collapsing or expanding a description
	 */
	public void movePrograms(int rank, boolean direction) {
		for(int i = programs.size() - 1; i > rank; i--) {
			programPanels[i].move(direction);
		}
	}
	// Get the program via their rank
	public Program getProgramByRank(int rank) {
		return programs.get(rank);
	}
	public ArrayList<Integer> getAllSelectedProgramRanks(){
		ArrayList<Integer> ranks = new ArrayList<Integer>();
		for(int i = 0; i < programPanels.length; i++) {
			if(programPanels[i].getSelected()) {
				ranks.add(programPanels[i].getRank());
			}
		}
		return ranks;
	}
}