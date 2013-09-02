package edu.scu.oop.bookmarkers.gui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import edu.scu.oop.bookmarkers.model.Library;

public class LibraryGUI {

	private JFrame frame;
		
	// Launch the application.
	 
	public static void main(String[] args) {
		try {
			Library.getInstance().loadValuesFromDB();
			
		
		} catch (Exception e) {
			// TODO handle  this exception
			System.out.println("There are issues with the .ser files. Please check");
			System.exit(0);

		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				LibraryGUI gui = new LibraryGUI();
				gui.frame.setVisible(true);
				gui.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				gui.frame.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing (WindowEvent we) {
						try {
							System.out.println("Interrupting Ad thread now so it stops");
							Advertisement.adThread.interrupt();
							System.out.println("Writing all values into files");
							Library.getInstance().writeValuesToDB();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							System.out.println("There are issues with the .ser files. Please check");
							e.printStackTrace();
						} 
						System.exit(0);
					}
				}); 
			}
		});
		
		 
	}

	/**
	 * Create the application.
	 */
	public LibraryGUI() {
		initialize();
		this.frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setMinimumSize(new Dimension(30, 0));
		frame.setResizable(false);
		frame.setBounds(200, 100, 900, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);
		
		
		// Move on to something else
	
		
		JLabel welcomeLabel = new JLabel();
		springLayout.putConstraint(SpringLayout.NORTH, welcomeLabel, 0, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, welcomeLabel, 0, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, welcomeLabel, 900, SpringLayout.WEST, frame.getContentPane());
		welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		welcomeLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
		welcomeLabel.setText("Welcome to BookMarkers Library!");
		frame.getContentPane().add(welcomeLabel);
		
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		springLayout.putConstraint(SpringLayout.NORTH, tabbedPane, 36, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, tabbedPane, 0, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, tabbedPane, 778, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, tabbedPane, 728, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(tabbedPane);
		
		// Start our advertisement
		System.out.println("Now starting Advertisement");

		JPanel adPanel = new Advertisement();
		springLayout.putConstraint(SpringLayout.SOUTH, adPanel, 732, SpringLayout.SOUTH, welcomeLabel);
		springLayout.putConstraint(SpringLayout.NORTH, adPanel, 6, SpringLayout.SOUTH, welcomeLabel);
		springLayout.putConstraint(SpringLayout.WEST, adPanel, 6, SpringLayout.EAST, tabbedPane);
		springLayout.putConstraint(SpringLayout.EAST, adPanel, 162, SpringLayout.EAST, tabbedPane);
		frame.getContentPane().add(adPanel);
		
		//Add member registration panel
		JPanel newMemberRegPanel = new MemberRegistrationPane();
		tabbedPane.addTab("New Member Registration", null, newMemberRegPanel, null);
		
		// Add items in library panel
		JScrollPane itemsInLibScrollPane = new ItemsInformationPane();
		tabbedPane.addTab("Items In Library", null, itemsInLibScrollPane, null);

		//Add library admin panel
		JScrollPane adminScrollPane = new LibraryAdminPane();		
		tabbedPane.addTab("Library Admin", null, adminScrollPane, null);
		
	}
}
