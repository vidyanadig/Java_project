package edu.scu.oop.bookmarkers.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.SpringLayout;

import edu.scu.oop.bookmarkers.model.Library;
import edu.scu.oop.bookmarkers.model.LibraryAdmin;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JList;
import javax.swing.JComboBox;

public class LibraryAdminPane extends JScrollPane {
	JTabbedPane libAdminTabs;
	JPanel findNumOfItems;
	Window parentWindow;
	LoginDialog loginDialog;
	JLabel itemsCheckedOutLabel = new JLabel("");
	JLabel numMembersLabel = new JLabel("");
	String[] daysList = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11","12","13", "14","15","16",
			"17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"}; 
	JButton calcFinesButton = new JButton("Calculate fines");


	String[] years = {"2013","2014","2015"};
	final JComboBox fromDay = new JComboBox(daysList);
	String[] months = {"01 Jan", "02 Feb", "03 March", "04 April", "05 May", "06 June", "07 July", "08 Aug", "09 Sept", "10 Oct", "11 Nov", 
	"12 Dec"};
	final JComboBox monthList = new JComboBox(months);
	final JComboBox fromMonth = new JComboBox(months);

	JComboBox fromYear = new JComboBox(years);
	JComboBox toDay = new JComboBox(daysList);
	JComboBox toMonth = new JComboBox(months);
	JComboBox toYear = new JComboBox(years);
	JLabel finesCollectedLabel = new JLabel("0.0");
	private final JLabel graphChartLabel = new JLabel("Graph showing library items");
	
	
	public LibraryAdminPane() {
		initialize();
	}
	
	private void initialize() {
		
		libAdminTabs = new JTabbedPane(JTabbedPane.TOP);
		findNumOfItems = new JPanel();
    	libAdminTabs.setVisible(false);

    	parentWindow = SwingUtilities.windowForComponent(this); 
    	loginDialog = new LoginDialog(parentWindow, libAdminTabs);
		
    	this.setViewportView(libAdminTabs);

		this.addComponentListener ( new ComponentAdapter() {
	        public void componentShown ( ComponentEvent e ) {
	        	libAdminTabs.setEnabled(false);
	        	libAdminTabs.setVisible(false);
	        	loginDialog.setVisible(true);
	        	libAdminTabs.repaint();
	        }
	        public void componentHidden ( ComponentEvent e ) {
	        	libAdminTabs.setEnabled(true);
	        	libAdminTabs.setVisible(true);
	        	libAdminTabs.repaint();
	        	loginDialog.setVisible(false);
	        }
		});
	
		libAdminTabs.addTab("Library items graph chart", null, findNumOfItems, null);
		SpringLayout sl_findNumOfItems = new SpringLayout();
		sl_findNumOfItems.putConstraint(SpringLayout.WEST, graphChartLabel, 226, SpringLayout.WEST, findNumOfItems);
		sl_findNumOfItems.putConstraint(SpringLayout.SOUTH, graphChartLabel, -41, SpringLayout.SOUTH, findNumOfItems);
		sl_findNumOfItems.putConstraint(SpringLayout.EAST, graphChartLabel, -204, SpringLayout.EAST, findNumOfItems);
		findNumOfItems.setLayout(sl_findNumOfItems);
		
		JScrollPane scrollPaneForGraph = new JScrollPane();
		sl_findNumOfItems.putConstraint(SpringLayout.NORTH, graphChartLabel, 6, SpringLayout.SOUTH, scrollPaneForGraph);
		sl_findNumOfItems.putConstraint(SpringLayout.NORTH, scrollPaneForGraph, 5, SpringLayout.NORTH, findNumOfItems);
		sl_findNumOfItems.putConstraint(SpringLayout.WEST, scrollPaneForGraph, 119, SpringLayout.WEST, findNumOfItems);
		scrollPaneForGraph.setPreferredSize(new Dimension(500, 500));
		findNumOfItems.add(scrollPaneForGraph);
		BarChart barChart = new BarChart();
		scrollPaneForGraph.setViewportView(barChart);
		barChart.setLayout(new SpringLayout());
		graphChartLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		
		findNumOfItems.add(graphChartLabel);

		
		JPanel numberOfItemsMembers = new JPanel();
		libAdminTabs.addTab("Number of Items and Members", null, numberOfItemsMembers, null);
		SpringLayout sl_numberOfItemsMembers = new SpringLayout();
		sl_numberOfItemsMembers.putConstraint(SpringLayout.WEST, finesCollectedLabel, 615, SpringLayout.WEST, numberOfItemsMembers);
		sl_numberOfItemsMembers.putConstraint(SpringLayout.EAST, finesCollectedLabel, -10, SpringLayout.EAST, numberOfItemsMembers);
		sl_numberOfItemsMembers.putConstraint(SpringLayout.WEST, fromYear, 6, SpringLayout.EAST, fromMonth);
		sl_numberOfItemsMembers.putConstraint(SpringLayout.EAST, fromYear, 0, SpringLayout.EAST, toYear);
		sl_numberOfItemsMembers.putConstraint(SpringLayout.WEST, fromMonth, 406, SpringLayout.WEST, numberOfItemsMembers);
		sl_numberOfItemsMembers.putConstraint(SpringLayout.EAST, fromMonth, -227, SpringLayout.EAST, numberOfItemsMembers);
		sl_numberOfItemsMembers.putConstraint(SpringLayout.WEST, toYear, 6, SpringLayout.EAST, toMonth);
		sl_numberOfItemsMembers.putConstraint(SpringLayout.EAST, toYear, -132, SpringLayout.EAST, numberOfItemsMembers);
		sl_numberOfItemsMembers.putConstraint(SpringLayout.NORTH, toYear, 0, SpringLayout.NORTH, toDay);
		sl_numberOfItemsMembers.putConstraint(SpringLayout.NORTH, toDay, 41, SpringLayout.SOUTH, fromDay);
		sl_numberOfItemsMembers.putConstraint(SpringLayout.WEST, toDay, 326, SpringLayout.WEST, numberOfItemsMembers);
		sl_numberOfItemsMembers.putConstraint(SpringLayout.EAST, fromDay, -6, SpringLayout.WEST, fromMonth);
		sl_numberOfItemsMembers.putConstraint(SpringLayout.WEST, toMonth, 0, SpringLayout.WEST, fromMonth);
		sl_numberOfItemsMembers.putConstraint(SpringLayout.EAST, toMonth, 0, SpringLayout.EAST, fromMonth);
		numberOfItemsMembers.setLayout(sl_numberOfItemsMembers);
		
		JLabel headingNumItemsMem = new JLabel("Number of Items and Members");
		sl_numberOfItemsMembers.putConstraint(SpringLayout.EAST, monthList, -48, SpringLayout.EAST, headingNumItemsMem);
		sl_numberOfItemsMembers.putConstraint(SpringLayout.NORTH, headingNumItemsMem, 10, SpringLayout.NORTH, numberOfItemsMembers);
		sl_numberOfItemsMembers.putConstraint(SpringLayout.WEST, headingNumItemsMem, 212, SpringLayout.WEST, numberOfItemsMembers);
		sl_numberOfItemsMembers.putConstraint(SpringLayout.SOUTH, headingNumItemsMem, 63, SpringLayout.NORTH, numberOfItemsMembers);
		sl_numberOfItemsMembers.putConstraint(SpringLayout.EAST, headingNumItemsMem, -208, SpringLayout.EAST, numberOfItemsMembers);
		headingNumItemsMem.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		numberOfItemsMembers.add(headingNumItemsMem);
		
		JLabel lblNumberOfItems = new JLabel("Number of Items Checked out: ");
		sl_numberOfItemsMembers.putConstraint(SpringLayout.WEST, itemsCheckedOutLabel, 12, SpringLayout.EAST, lblNumberOfItems);
		sl_numberOfItemsMembers.putConstraint(SpringLayout.NORTH, lblNumberOfItems, 46, SpringLayout.SOUTH, headingNumItemsMem);
		sl_numberOfItemsMembers.putConstraint(SpringLayout.SOUTH, lblNumberOfItems, 80, SpringLayout.SOUTH, headingNumItemsMem);
		sl_numberOfItemsMembers.putConstraint(SpringLayout.EAST, lblNumberOfItems, 322, SpringLayout.WEST, numberOfItemsMembers);
		lblNumberOfItems.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		numberOfItemsMembers.add(lblNumberOfItems);
		
		
		itemsCheckedOutLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		sl_numberOfItemsMembers.putConstraint(SpringLayout.NORTH, itemsCheckedOutLabel, 57, SpringLayout.SOUTH, headingNumItemsMem);
		numberOfItemsMembers.add(itemsCheckedOutLabel);
		
		JLabel numOfMembers = new JLabel("Number of Registered Members: ");
		sl_numberOfItemsMembers.putConstraint(SpringLayout.NORTH, numOfMembers, 58, SpringLayout.SOUTH, lblNumberOfItems);
		sl_numberOfItemsMembers.putConstraint(SpringLayout.EAST, numOfMembers, -415, SpringLayout.EAST, numberOfItemsMembers);
		sl_numberOfItemsMembers.putConstraint(SpringLayout.WEST, lblNumberOfItems, 0, SpringLayout.WEST, numOfMembers);
		numOfMembers.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		numberOfItemsMembers.add(numOfMembers);

		numMembersLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		sl_numberOfItemsMembers.putConstraint(SpringLayout.WEST, numMembersLabel, 18, SpringLayout.EAST, numOfMembers);
		sl_numberOfItemsMembers.putConstraint(SpringLayout.SOUTH, numMembersLabel, 0, SpringLayout.SOUTH, numOfMembers);
		numberOfItemsMembers.add(numMembersLabel);
		
		JLabel lblMostCheckedoutBook = new JLabel("Most Checkedout Book \nin the month: ");
		sl_numberOfItemsMembers.putConstraint(SpringLayout.NORTH, monthList, 9, SpringLayout.NORTH, lblMostCheckedoutBook);
		sl_numberOfItemsMembers.putConstraint(SpringLayout.WEST, monthList, 6, SpringLayout.EAST, lblMostCheckedoutBook);
		sl_numberOfItemsMembers.putConstraint(SpringLayout.NORTH, lblMostCheckedoutBook, 54, SpringLayout.SOUTH, numOfMembers);
		sl_numberOfItemsMembers.putConstraint(SpringLayout.WEST, lblMostCheckedoutBook, 0, SpringLayout.WEST, numOfMembers);
		sl_numberOfItemsMembers.putConstraint(SpringLayout.SOUTH, lblMostCheckedoutBook, -269, SpringLayout.SOUTH, numberOfItemsMembers);
		sl_numberOfItemsMembers.putConstraint(SpringLayout.EAST, lblMostCheckedoutBook, 299, SpringLayout.WEST, numOfMembers);
		lblMostCheckedoutBook.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		numberOfItemsMembers.add(lblMostCheckedoutBook);
		
		final JLabel mostCheckedOutBookLabel = new JLabel("");
		sl_numberOfItemsMembers.putConstraint(SpringLayout.WEST, mostCheckedOutBookLabel, 261, SpringLayout.WEST, numberOfItemsMembers);
		sl_numberOfItemsMembers.putConstraint(SpringLayout.EAST, mostCheckedOutBookLabel, -75, SpringLayout.EAST, numberOfItemsMembers);
		sl_numberOfItemsMembers.putConstraint(SpringLayout.NORTH, fromDay, 25, SpringLayout.SOUTH, mostCheckedOutBookLabel);
		sl_numberOfItemsMembers.putConstraint(SpringLayout.NORTH, mostCheckedOutBookLabel, 12, SpringLayout.SOUTH, lblMostCheckedoutBook);
		sl_numberOfItemsMembers.putConstraint(SpringLayout.SOUTH, mostCheckedOutBookLabel, 54, SpringLayout.SOUTH, lblMostCheckedoutBook);
		mostCheckedOutBookLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		numberOfItemsMembers.add(mostCheckedOutBookLabel);
		monthList.setMaximumRowCount(5);
		monthList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 ;
				mostCheckedOutBookLabel.setText(
						LibraryAdmin.getAdminInstance().findTitleOfBookCheckedoutMost((monthList.getSelectedIndex())));
			}
		});
		numberOfItemsMembers.add(monthList);
		
		JLabel lblNewLabel = new JLabel("Fines collected during the perid:");
		sl_numberOfItemsMembers.putConstraint(SpringLayout.NORTH, fromMonth, -2, SpringLayout.NORTH, lblNewLabel);
		sl_numberOfItemsMembers.putConstraint(SpringLayout.NORTH, fromYear, -2, SpringLayout.NORTH, lblNewLabel);
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		sl_numberOfItemsMembers.putConstraint(SpringLayout.NORTH, lblNewLabel, 79, SpringLayout.SOUTH, lblMostCheckedoutBook);
		sl_numberOfItemsMembers.putConstraint(SpringLayout.WEST, lblNewLabel, 66, SpringLayout.WEST, numberOfItemsMembers);
		numberOfItemsMembers.add(lblNewLabel);
		sl_numberOfItemsMembers.putConstraint(SpringLayout.WEST, fromDay, 6, SpringLayout.EAST, lblNewLabel);
		fromDay.setMaximumRowCount(5);
		numberOfItemsMembers.add(fromDay);
		fromMonth.setMaximumRowCount(5);
		numberOfItemsMembers.add(fromMonth);
		numberOfItemsMembers.add(fromYear);
		
		JLabel lblNewLabel_1 = new JLabel("to");
		sl_numberOfItemsMembers.putConstraint(SpringLayout.EAST, lblNewLabel_1, -160, SpringLayout.WEST, finesCollectedLabel);
		sl_numberOfItemsMembers.putConstraint(SpringLayout.NORTH, finesCollectedLabel, 0, SpringLayout.NORTH, lblNewLabel_1);
		sl_numberOfItemsMembers.putConstraint(SpringLayout.SOUTH, finesCollectedLabel, 20, SpringLayout.NORTH, lblNewLabel_1);
		sl_numberOfItemsMembers.putConstraint(SpringLayout.NORTH, toMonth, 6, SpringLayout.SOUTH, lblNewLabel_1);
		sl_numberOfItemsMembers.putConstraint(SpringLayout.NORTH, lblNewLabel_1, 14, SpringLayout.SOUTH, fromMonth);
		lblNewLabel_1.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		numberOfItemsMembers.add(lblNewLabel_1);
		toDay.setMaximumRowCount(5);
		numberOfItemsMembers.add(toDay);
		numberOfItemsMembers.add(toMonth);
		numberOfItemsMembers.add(toYear);
		
		finesCollectedLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		numberOfItemsMembers.add(finesCollectedLabel);
		
		
		sl_numberOfItemsMembers.putConstraint(SpringLayout.NORTH, calcFinesButton, 6, SpringLayout.SOUTH, toDay);
		sl_numberOfItemsMembers.putConstraint(SpringLayout.EAST, calcFinesButton, 0, SpringLayout.EAST, fromMonth);
		numberOfItemsMembers.add(calcFinesButton);
		
		this.setViewportView(libAdminTabs);

	}
	
	private void displayOtherComponentsInLibAdminPane () throws ParseException {
		System.out.println("Number of checkedout items updating" + LibraryAdmin.getAdminInstance().findNumberOfItemsCheckedOut());
		itemsCheckedOutLabel.setText(LibraryAdmin.getAdminInstance().findNumberOfItemsCheckedOut().toString());
		numMembersLabel.setText(LibraryAdmin.getAdminInstance().findTotalNumberOfMembers().toString());
		
		calcFinesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DateFormat newDateFormat = new SimpleDateFormat("dd-MM-yy HH:mm:ss");  
				String fromDateStr = String.format((fromDay.getSelectedIndex() +1) + "-"+(fromMonth.getSelectedIndex()+1)+"-"+(fromYear.getSelectedItem().toString()) 
						+ " 23:59:00"); 
				String toDateStr = String.format((toDay.getSelectedIndex() +1) + "-"+(toMonth.getSelectedIndex()+1)+"-"+(toYear.getSelectedItem().toString()) 
						+ " 23:59:00"); 
				System.out.println(" From :" + fromDateStr);
				System.out.println(" TO :" + toDateStr);
				Date fromDate;
				Date toDate;
				try {
					fromDate = newDateFormat.parse(fromDateStr);
					toDate = newDateFormat.parse(toDateStr);
					finesCollectedLabel.setText("$"+LibraryAdmin.getAdminInstance().findTotalFineCollected(fromDate, toDate).toString());
					
				} catch (ParseException e1) {
					finesCollectedLabel.setText("Invalid date input");
				}
				libAdminTabs.repaint();

				}
		});
		libAdminTabs.repaint();

	}
	
	
	private class LoginDialog extends JDialog {
	    private JTextField tfUsername;
	    private JPasswordField pfPassword;
	    private JLabel lbUsername;
	    private JLabel lbPassword;
	    private JButton btnLogin;
	    private boolean succeeded;
	 
	    public LoginDialog(final Window parent, final JTabbedPane parentPane ) {
	        super(parent, "Login", ModalityType.DOCUMENT_MODAL);
	        //
	        JPanel panel = new JPanel(new GridBagLayout());
	        GridBagConstraints cs = new GridBagConstraints();
	 
	        cs.fill = GridBagConstraints.HORIZONTAL;
	 
	        lbUsername = new JLabel("Username: ");
	        cs.gridx = 0;
	        cs.gridy = 0;
	        cs.gridwidth = 1;
	        panel.add(lbUsername, cs);
	 
	        tfUsername = new JTextField(20);
	        cs.gridx = 1;
	        cs.gridy = 0;
	        cs.gridwidth = 2;
	        panel.add(tfUsername, cs);
	 
	        lbPassword = new JLabel("Password: ");
	        cs.gridx = 0;
	        cs.gridy = 1;
	        cs.gridwidth = 1;
	        panel.add(lbPassword, cs);
	 
	        pfPassword = new JPasswordField(20);
	        cs.gridx = 1;
	        cs.gridy = 1;
	        cs.gridwidth = 2;
	        panel.add(pfPassword, cs);
	        panel.setBorder(new LineBorder(Color.GRAY));
	 
	        btnLogin = new JButton("Login");
	 
	        btnLogin.addActionListener(new ActionListener() {
				
				@Override
	            public void actionPerformed(ActionEvent e) {
	                if (authenticate(getUsername(), getPassword())) {
	                    succeeded = true;
	                    parentPane.setEnabled(true);
	                    parentPane.setVisible(true);

	                    tfUsername.setText("");
	                    pfPassword.setText("");
	                    dispose();
	                    try {
							displayOtherComponentsInLibAdminPane();
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
	                } else {
	                    JOptionPane.showMessageDialog(LoginDialog.this,
	                            "Invalid username or password",
	                            "Login",
	                            JOptionPane.ERROR_MESSAGE);
	                    // reset username and password
	                    tfUsername.setText("");
	                    pfPassword.setText("");
	                    succeeded = false;
	                    parentPane.setEnabled(false);
	                    parentPane.setVisible(false);

	                }
	            }
	        });
	        JPanel bp = new JPanel();
	        bp.add(btnLogin);
	 
	        getContentPane().add(panel, BorderLayout.CENTER);
	        getContentPane().add(bp, BorderLayout.PAGE_END);
	 
	        pack();
	        setResizable(false);
	        setLocationRelativeTo(parent);
	        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	    }
	 
	    public String getUsername() {
	        return tfUsername.getText().trim();
	    }
	 
	    public String getPassword() {
	        return new String(pfPassword.getPassword());
	    }
	 
	    public boolean isSucceeded() {
	        return succeeded;
	    }
	    
	    private boolean authenticate(String username, String password) {
	        // TODO: Call the DB to lookup the right username and password
	        if (username.equals("vidya") && password.equals("secret")) {
	            return true;
	        }
	        return false;
	    }
	}
}
