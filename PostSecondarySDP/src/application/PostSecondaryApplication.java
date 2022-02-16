/*
 * Name(s): Murphy (25%), Fawaz (25%), Savannah (25%), Daniel (25%)
 * Roles:
 *  - Murphy: Project Managaer, Controller-Lead, Profile Display Frame, Algorithm lead, Connection of Frames, Validation Helper
 *  - Fawaz: Login & Register Frame, CSV Helper, Colouring & Design Helper, Algorithm Helper
 *  - Savannah: Displaying Matched Programs, Controller Helper, CSV Lead
 *  - Daniel: User Input lead, ProfileInputFrame, File Read & Write Lead, Validation Helper, Aesthetic Lead
 * Date of Submission: December 16, 2021
 * Course: ICS4U1-01
 * Teacher: Mr.Fernandes 
 * Title: UNIMATCH - The Post Secondary Application
 * Description: This program uses an algorithm to recommend top-notch university programs to students. This algorithm takes the data of the user into account, such as
 *     their name, grades, interests, and more. This information is displayed in a logical and aesthetically-pleasing format, in order to become a reliable source of information
 *     for grade 12 students that need to start thinking about the application process
 * Major Skills: Java Swing, File Reading & Writing, Object Oriented Programming, Hashmaps, 
 * 				Access Modifiers, Conditional Loops, For & While Loops, ImageIcons, ActionListeners, Importing Files, Math Calculations
 * Added features: 
 *  - Sorting of programs (selected programs can be sorted by university name, program name, grade range, etc)
 *  - Links that open the program website
 *  
 * Area of concerns:
 *  - Requires java.desktop for GUI components
 *  - A monitor that supports 1920x1080p is recommended to support the frame size
 *  - Major use of swing (javax) and AWT components
 * External Resources:
 *    - Changing to a finger-cursor on hover: https://stackoverflow.com/questions/11686938/how-to-change-the-mouse-pointer-to-finger-pointer-in-swing
 *    - Finding index of element with linear search: https://www.geeksforgeeks.org/find-the-index-of-an-array-element-in-java/
 *    - Using a DefaultListModel with a JList to add and remove items: https://www.tabnine.com/code/java/classes/javax.swing.DefaultListModel
 *    - Creating 2D array from hashmap: https://stackoverflow.com/questions/2265266/convert-hash-map-to-2d-array
 */

package application;

import controller.PostSecondaryController;

public class PostSecondaryApplication {
	public static void main(String[] args) {
		new PostSecondaryController();
		
	}
}
