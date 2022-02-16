/**
/**
* 
* @author Savannah
*
*/

package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;
import javax.swing.*;

import model.Program;

@SuppressWarnings("serial")
public class ProgramPanel extends JPanel implements ActionListener {
	//attributes
	//radio button - used to select this program as program of choice
	private JCheckBox programSelectButton = new JCheckBox();
	
	//label - name of program
	private JLabel programNameLabel = new JLabel();
	
	//button - used to expand this program's description
	private JButton expandDescriptionButton = new JButton();
	private JLabel universityNameLabel = new JLabel();
	private JLabel programDescriptionLabel = new JLabel();
	
	//labels for program rank number, match rating, and score
	private JLabel rankNumberLabel = new JLabel();
	private JLabel matchScoreStarLabel = new JLabel();
	private JLabel matchRatingLabel = new JLabel();
	
	//labels for min grade, max grade, fee, and program link
	private JLabel minimumGradeLabel = new JLabel();
	private JLabel maximumGradeLabel = new JLabel();
	private JLabel totalFeeLabel = new JLabel();
	private JLabel getMoreInfoLabel = new JLabel("For more information please visit: ");
	private JLabel programHyperlink = new JLabel();
	
	//action listener
	private AllProgramsPanel allProgramPanel = null;
	
	//rank number of this program
	private int rank;
	
	//file image path
	private String imgPath = "images/";
	
	//button, label; relevant sizes, etc
	private int buttonAndLabelSize = 30;
	private int nameLabelLength = 600; 
	private int descriptionLabelSize = 120;
	private int tab = 20;
	
	//based on whether description label is visible/expanded or not
	private boolean isExpanded = false;
	
	private Program program = null;
	
	//image icons for expandDescriptionButton (plus and minus sign)
	private ImageIcon plusSign = new ImageIcon(scaleImg(imgPath + "Plus_Sign.png", buttonAndLabelSize, buttonAndLabelSize));
	private ImageIcon minusSign = new ImageIcon(scaleImg(imgPath + "Minus_Sign.png", buttonAndLabelSize, buttonAndLabelSize - 20));
	
	//unimatch color palette
	private UnimatchColorPalette colors = new UnimatchColorPalette();
	
	public ProgramPanel(AllProgramsPanel allProgramPanel, Program program, int rank) throws HeadlessException{
		this.allProgramPanel = allProgramPanel;
		this.program = program;
		this.rank = rank;
		
		setLayout(null);
		
		//setup
		setupTopOfProgramPanel();
		setupDescription();
		setMatchScoreIcon();
		
		programNameLabel.setFont(new Font("Calibri", Font.BOLD, 20));
		universityNameLabel.setFont(new Font("Calibri", Font.PLAIN, 18));
		rankNumberLabel.setFont(new Font("Calibri", Font.BOLD, 18));
		matchRatingLabel.setFont(new Font("Calibri", Font.BOLD, 18));
		
		minimumGradeLabel.setFont(new Font("Calibri", Font.PLAIN, 16));
		maximumGradeLabel.setFont(new Font("Calibri", Font.PLAIN, 16));
		getMoreInfoLabel.setFont(new Font("Calibri", Font.PLAIN, 16));
		programHyperlink.setFont(new Font("Calibri", Font.PLAIN, 16));
		totalFeeLabel.setFont(new Font("Calibri", Font.PLAIN, 16));
	}
	
	//utility methods
	public boolean isExpanded() {
		return isExpanded;
	}
	public void setExpanded(boolean isExpanded) {
		this.isExpanded = isExpanded;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		//each time the expandDescription button is clicked, it will go from being expanded to not expanded or vice versa
		this.isExpanded = !this.isExpanded;
		
		//if isExpanded is true, the description label being set visible is also true (and vice versa)
		programDescriptionLabel.setVisible(isExpanded);
		
		//change the icon of the expand description button (between plus or minus sign) depending on whether it is expanded or not
		expandDescriptionButton.setIcon(this.isExpanded ? minusSign : plusSign);
		
		//set program size collapsed - false, set program size expanded - true
		this.setSize(this.getSize().width, this.getSize().height + (this.isExpanded ? 1 : -1) * descriptionLabelSize);
		
		//notify allProgramPanel of action performed
		ActionEvent f = new ActionEvent((Object)this, ActionEvent.ACTION_PERFORMED, "");
		this.allProgramPanel.actionPerformed(f);
		
	}
	//method to move location of program panel (utilized when a panel above is collapsed)
	public void move(boolean direction) {
		this.setLocation(this.getX(), this.getY() + (direction ? -1 : 1) * descriptionLabelSize + (direction ? -1 : 1) * 10);
	}
	public int getDescriptionLabelSize() {
		return descriptionLabelSize;
	}
	private void setupTopOfProgramPanel() {
		//program name and program select radio button
		programSelectButton.setBounds(0, 0, buttonAndLabelSize, buttonAndLabelSize);
		programSelectButton.setActionCommand("" + rank);
		add(programSelectButton);
		
		programNameLabel.setBounds(buttonAndLabelSize, 0, nameLabelLength, buttonAndLabelSize);
		programNameLabel.setText(program.getProgramName());
		add(programNameLabel);
		
		//rank number and match score
		rankNumberLabel.setBounds(1570, 0, 50, buttonAndLabelSize);
		rankNumberLabel.setText("#" + (this.rank + 1));
		add(rankNumberLabel);
		
		matchScoreStarLabel.setBounds(1600 + buttonAndLabelSize, 0, 200, buttonAndLabelSize);
		add(matchScoreStarLabel);
		
		matchRatingLabel.setBounds(1700 + buttonAndLabelSize, 0, 200, buttonAndLabelSize);
		matchRatingLabel.setText(String.format("%.2f/5%n", program.getMatchRating()));
		add(matchRatingLabel);
		
		//add action listener
		expandDescriptionButton.addActionListener(this);
		
		universityNameLabel.setBounds(2 * buttonAndLabelSize + 10, buttonAndLabelSize, 
				nameLabelLength, buttonAndLabelSize);
		universityNameLabel.setText(program.getUniversityName());
		add(universityNameLabel);
	}
	//sets up description
	private void setupDescription() {
		
		programDescriptionLabel.setBounds(3 * buttonAndLabelSize, 2 * buttonAndLabelSize, 1740, descriptionLabelSize);
		programDescriptionLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		programDescriptionLabel.setBackground(colors.getHWITE());
		programDescriptionLabel.setVisible(false);
		add(programDescriptionLabel);
		
		//expand description button and university name
		expandDescriptionButton.setBounds(buttonAndLabelSize, buttonAndLabelSize, buttonAndLabelSize, buttonAndLabelSize);
		expandDescriptionButton.setIcon(plusSign);
		add(expandDescriptionButton);
		
		setupDescriptionContents();
	}
	private void setupDescriptionContents() {
		//min grade, max grade, and fee
		minimumGradeLabel.setBounds(tab, 0, 200, buttonAndLabelSize);
		maximumGradeLabel.setBounds(tab, buttonAndLabelSize, 200, buttonAndLabelSize);
		totalFeeLabel.setBounds(tab, 2 * buttonAndLabelSize, 200, buttonAndLabelSize);
		
		programDescriptionLabel.add(minimumGradeLabel);
		programDescriptionLabel.add(maximumGradeLabel);
		programDescriptionLabel.add(totalFeeLabel);
		
		
		minimumGradeLabel.setText("Minimum Grade: " + Integer.toString(program.getGradeMin()) + "%");
		maximumGradeLabel.setText("Maximum Grade: " + Integer.toString(program.getGradeMax()) + "%");
		totalFeeLabel.setText("Total Fees: " + Double.toString(program.getTotalFee()) + "$");
		
		//link and get more info label
		programHyperlink.setBounds(305, 3 * buttonAndLabelSize, 900, buttonAndLabelSize);
		getMoreInfoLabel.setBounds(tab, 3 * buttonAndLabelSize, 300, buttonAndLabelSize);
		
		programDescriptionLabel.add(getMoreInfoLabel);
		programDescriptionLabel.add(programHyperlink);
		
		programHyperlink.setText(program.getLink());
		programHyperlink.setForeground(Color.BLUE.darker());
		
		setProgramHyperlinkAsClickable();
	}
	private void setProgramHyperlinkAsClickable() {
		//https://www.codejava.net/java-se/swing/how-to-create-hyperlink-with-jlabel-in-java-swing
		programHyperlink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(program.getLink()));
                } catch (IOException | URISyntaxException e1) {
                    e1.printStackTrace();
                }
            }
 
            @Override
            public void mouseExited(MouseEvent e) {
            	programHyperlink.setText(program.getLink());
            }
 
            @Override
            public void mouseEntered(MouseEvent e) {
            	programHyperlink.setText("<html><a href=''>" + program.getLink() + "</a></html>");
            }
 
        });
		programHyperlink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}
	private void setMatchScoreIcon() {
		//score will be provided as double (0, 0.5, 1, 1.5, 2, 2.5 ... 5)
		double matchScore = program.getMatchRating();
		String starImgNum = "0";
		
		//get star image
		if(matchScore > 0 && matchScore <= 0.5) 
		{
			starImgNum = "0-5";
		}
		else if(matchScore > 0.5 && matchScore <= 1) 
		{
			starImgNum = "1";
		}
		else if(matchScore > 1 && matchScore <= 1.5) 
		{
			starImgNum = "1-5";
		}
		else if(matchScore > 1.5 && matchScore <= 2) 
		{
			starImgNum = "2";
		}
		else if(matchScore > 2 && matchScore <= 2.5) 
		{
			starImgNum = "2-5";
		}
		else if(matchScore > 2.5 && matchScore <= 3) 
		{
			starImgNum = "3";
		}
		else if(matchScore > 3 && matchScore <= 3.5) 
		{
			starImgNum = "3-5";
		}
		else if(matchScore > 3.5 && matchScore <= 4) 
		{
			starImgNum = "4";
		}		
		else if(matchScore > 4 && matchScore <= 4.5) 
		{
			starImgNum = "4-5";
		}
		else if(matchScore > 4.5 && matchScore <= 5) 
		{
			starImgNum = "5";
		}		
		
		//set icon to program panel label
		getMatchScoreStarLabel().setIcon(new ImageIcon(
		scaleImg(imgPath + starImgNum + "_star_rating.png", 90, 20)));
	}
	//scales images to fit labels, buttons, etc
	public Image scaleImg(String imgName, int width, int height) {
		//https://stackoverflow.com/questions/16343098/resize-a-picture-to-fit-a-jlabel
		BufferedImage img = null;
		
		try {
		    img = ImageIO.read(new File(imgName));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
		Image dimg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		return dimg;
	}
	//getters and setters
		public int getRank() {
			return rank;
		}
		public JLabel getProgramDescriptionLabel() {
			return programDescriptionLabel;
		}
		public void setProgramDescriptionLabel(JLabel programDescriptionLabel) {
			this.programDescriptionLabel = programDescriptionLabel;
		}
		public JCheckBox getProgramSelectButton() {
			return programSelectButton;
		}
		public void setProgramSelectButton(JCheckBox programSelectButton) {
			this.programSelectButton = programSelectButton;
		}
		public JLabel getProgramNameLabel() {
			return programNameLabel;
		}
		public void setProgramNameLabel(JLabel programNameLabel) {
			this.programNameLabel = programNameLabel;
		}
		public JLabel getUniversityNameLabel() {
			return universityNameLabel;
		}
		public void setUniversityNameLabel(JLabel universityNameLabel) {
			this.universityNameLabel = universityNameLabel;
		}
		public JLabel getMatchScoreStarLabel() {
			return matchScoreStarLabel;
		}
		public void setMatchScoreStarLabel(JLabel matchScoreStarLabel) {
			this.matchScoreStarLabel = matchScoreStarLabel;
		}
	public boolean getSelected() {
		return programSelectButton.isSelected();
	}
}