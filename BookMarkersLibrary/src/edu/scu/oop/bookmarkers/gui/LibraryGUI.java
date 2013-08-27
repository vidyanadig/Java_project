package edu.scu.oop.bookmarkers.gui;

import java.awt.EventQueue;
import edu.scu.oop.bookmarkers.model.*;

import javax.swing.JFrame;
import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.GridLayout;
import javax.swing.SpringLayout;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.swing.*;

import edu.scu.oop.bookmarkers.model.Library;
import javax.swing.table.DefaultTableModel;

public class LibraryGUI {

	private JFrame frame;
	private JTextField searchItemstext;
	private JTextField itemIDReturnText;
	private JTextField memIDTextInPayFines;
	private JTextField queryTransText;
	private JTextField fullNameText;
	private JTextField addressText;
	private JTextField phoneNumText;
	private JTextField countyText;
	private JTextField emailText;
	private JTextField needToPayText;
	private SearchResultsItemsTable table;
	private JTextField memIDForCheckoutReserve;
	private QueryTransactionTable queryResults;
	private JTextField memIDTextwhileReturning;

	
	// Launch the application.
	 
	public static void main(String[] args) {
		try {
			Library.getInstance().loadValuesFromDB();
			
		
		
		} catch (Exception e) {
			// TODO handle  this exception
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
							Library.getInstance().writeValuesToDB();
						} catch (Exception e) {
							// TODO Auto-generated catch block
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
		frame.setResizable(false);
		frame.setBounds(200, 100, 900, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.WEST);
		
		JLabel lblNewEntries = new JLabel("Our New Entries!");
		lblNewEntries.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		panel.add(lblNewEntries);
		
		JLabel welcomeLabel = new JLabel();
		welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		welcomeLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
		welcomeLabel.setText("Welcome to BookMarkers Library!");
		frame.getContentPane().add(welcomeLabel, BorderLayout.NORTH);
		
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		
		JPanel newMemberRegPanel = new JPanel();
		tabbedPane.addTab("New Member Registration", null, newMemberRegPanel, null);
		SpringLayout sl_newMemberRegPanel = new SpringLayout();
		newMemberRegPanel.setLayout(sl_newMemberRegPanel);
		
		JLabel fullNameLabel = new JLabel("Full name:");
		fullNameLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		newMemberRegPanel.add(fullNameLabel);
		
		JLabel newMemRegLabel = new JLabel("New member registration");
		newMemRegLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		sl_newMemberRegPanel.putConstraint(SpringLayout.NORTH, newMemRegLabel, 30, SpringLayout.NORTH, newMemberRegPanel);
		sl_newMemberRegPanel.putConstraint(SpringLayout.WEST, newMemRegLabel, 169, SpringLayout.WEST, newMemberRegPanel);
		sl_newMemberRegPanel.putConstraint(SpringLayout.SOUTH, newMemRegLabel, 55, SpringLayout.NORTH, newMemberRegPanel);
		sl_newMemberRegPanel.putConstraint(SpringLayout.EAST, newMemRegLabel, 453, SpringLayout.WEST, newMemberRegPanel);
		newMemberRegPanel.add(newMemRegLabel);
		
		fullNameText = new JTextField();
		sl_newMemberRegPanel.putConstraint(SpringLayout.NORTH, fullNameText, 38, SpringLayout.SOUTH, newMemRegLabel);
		sl_newMemberRegPanel.putConstraint(SpringLayout.WEST, fullNameText, 232, SpringLayout.WEST, newMemberRegPanel);
		sl_newMemberRegPanel.putConstraint(SpringLayout.SOUTH, fullNameLabel, 0, SpringLayout.SOUTH, fullNameText);
		sl_newMemberRegPanel.putConstraint(SpringLayout.EAST, fullNameLabel, -7, SpringLayout.WEST, fullNameText);
		fullNameText.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		newMemberRegPanel.add(fullNameText);
		fullNameText.setColumns(20);
		
		addressText = new JTextField();
		sl_newMemberRegPanel.putConstraint(SpringLayout.WEST, addressText, 0, SpringLayout.WEST, fullNameText);
		addressText.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		addressText.setColumns(10);
		newMemberRegPanel.add(addressText);
		
		JLabel addrCityLabel = new JLabel("Address (City):");
		sl_newMemberRegPanel.putConstraint(SpringLayout.NORTH, addressText, -6, SpringLayout.NORTH, addrCityLabel);
		sl_newMemberRegPanel.putConstraint(SpringLayout.NORTH, addrCityLabel, 35, SpringLayout.SOUTH, fullNameLabel);
		sl_newMemberRegPanel.putConstraint(SpringLayout.EAST, addrCityLabel, 0, SpringLayout.EAST, fullNameLabel);
		addrCityLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		newMemberRegPanel.add(addrCityLabel);
		
		JLabel phoneNumLabel = new JLabel("Phone Number:");
		sl_newMemberRegPanel.putConstraint(SpringLayout.NORTH, phoneNumLabel, 48, SpringLayout.SOUTH, addrCityLabel);
		sl_newMemberRegPanel.putConstraint(SpringLayout.WEST, phoneNumLabel, 101, SpringLayout.WEST, newMemberRegPanel);
		phoneNumLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		newMemberRegPanel.add(phoneNumLabel);
		
		phoneNumText = new JTextField();
		sl_newMemberRegPanel.putConstraint(SpringLayout.NORTH, phoneNumText, -6, SpringLayout.NORTH, phoneNumLabel);
		sl_newMemberRegPanel.putConstraint(SpringLayout.WEST, phoneNumText, 0, SpringLayout.WEST, fullNameText);
		phoneNumText.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		phoneNumText.setColumns(10);
		newMemberRegPanel.add(phoneNumText);
		
		countyText = new JTextField();
		sl_newMemberRegPanel.putConstraint(SpringLayout.WEST, countyText, 0, SpringLayout.WEST, fullNameText);
		countyText.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		countyText.setColumns(10);
		newMemberRegPanel.add(countyText);
		
		JLabel countyLabel = new JLabel("County:");
		sl_newMemberRegPanel.putConstraint(SpringLayout.EAST, countyLabel, -17, SpringLayout.WEST, countyText);
		sl_newMemberRegPanel.putConstraint(SpringLayout.NORTH, countyText, 0, SpringLayout.NORTH, countyLabel);
		sl_newMemberRegPanel.putConstraint(SpringLayout.NORTH, countyLabel, 41, SpringLayout.SOUTH, phoneNumLabel);
		countyLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		newMemberRegPanel.add(countyLabel);
		
		emailText = new JTextField();
		sl_newMemberRegPanel.putConstraint(SpringLayout.WEST, emailText, 0, SpringLayout.WEST, fullNameText);
		emailText.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		emailText.setColumns(10);
		newMemberRegPanel.add(emailText);
		
		JLabel emailLabel = new JLabel("Email ID:");
		sl_newMemberRegPanel.putConstraint(SpringLayout.NORTH, emailText, -6, SpringLayout.NORTH, emailLabel);
		sl_newMemberRegPanel.putConstraint(SpringLayout.NORTH, emailLabel, 42, SpringLayout.SOUTH, countyLabel);
		sl_newMemberRegPanel.putConstraint(SpringLayout.WEST, emailLabel, 0, SpringLayout.WEST, fullNameLabel);
		emailLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		newMemberRegPanel.add(emailLabel);
		
		final JButton registerButton = new JButton("Register");
		sl_newMemberRegPanel.putConstraint(SpringLayout.NORTH, registerButton, 64, SpringLayout.SOUTH, emailText);
		sl_newMemberRegPanel.putConstraint(SpringLayout.WEST, registerButton, 0, SpringLayout.WEST, newMemRegLabel);
		registerButton.setEnabled(false);
		newMemberRegPanel.add(registerButton);
		
		final JButton resetButton = new JButton("Reset");
		sl_newMemberRegPanel.putConstraint(SpringLayout.NORTH, resetButton, 0, SpringLayout.NORTH, registerButton);
		sl_newMemberRegPanel.putConstraint(SpringLayout.WEST, resetButton, 68, SpringLayout.EAST, registerButton);
		resetButton.setEnabled(false);
		newMemberRegPanel.add(resetButton);
		
		final JLabel newMemSuccessMsg = new JLabel("");
		sl_newMemberRegPanel.putConstraint(SpringLayout.WEST, newMemSuccessMsg, 87, SpringLayout.WEST, newMemberRegPanel);
		sl_newMemberRegPanel.putConstraint(SpringLayout.SOUTH, newMemSuccessMsg, 126, SpringLayout.SOUTH, registerButton);
		sl_newMemberRegPanel.putConstraint(SpringLayout.EAST, newMemSuccessMsg, 464, SpringLayout.WEST, fullNameLabel);
		newMemSuccessMsg.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		sl_newMemberRegPanel.putConstraint(SpringLayout.NORTH, newMemSuccessMsg, 85, SpringLayout.SOUTH, registerButton);
		newMemberRegPanel.add(newMemSuccessMsg);
		
		// So that the register/reset button is enabled when user types something
		fullNameText.addKeyListener(
				new KeyAdapter() {
					public void keyReleased(KeyEvent keyEvent) {
						if (fullNameText.getDocument().getLength() > 0 && addressText.getDocument().getLength() > 0 
								&& phoneNumText.getDocument().getLength() > 0 && countyText.getDocument().getLength() > 0 
								&& emailText.getDocument().getLength() > 0) {
							registerButton.setEnabled(true);
						} else {
							registerButton.setEnabled(false);
							resetButton.setEnabled(true);
						}

					}
				});

		addressText.addKeyListener(
				new KeyAdapter() {
					public void keyReleased(KeyEvent keyEvent) {
						if (fullNameText.getDocument().getLength() > 0 && addressText.getDocument().getLength() > 0 
								&& phoneNumText.getDocument().getLength() > 0 && countyText.getDocument().getLength() > 0 
								&& emailText.getDocument().getLength() > 0) {
							registerButton.setEnabled(true);
						} else {
							registerButton.setEnabled(false);
							resetButton.setEnabled(true);
						}

					}
				});

				
		phoneNumText.addKeyListener(
				new KeyAdapter() {
					public void keyReleased(KeyEvent keyEvent) {
						if (fullNameText.getDocument().getLength() > 0 && addressText.getDocument().getLength() > 0 
								&& phoneNumText.getDocument().getLength() > 0 && countyText.getDocument().getLength() > 0 
								&& emailText.getDocument().getLength() > 0) {
							registerButton.setEnabled(true);
						} else {
							registerButton.setEnabled(false);
							resetButton.setEnabled(true);
						}

					}
				});
		
		countyText.addKeyListener(
				new KeyAdapter() {
					public void keyReleased(KeyEvent keyEvent) {
						if (fullNameText.getDocument().getLength() > 0 && addressText.getDocument().getLength() > 0 
								&& phoneNumText.getDocument().getLength() > 0 && countyText.getDocument().getLength() > 0 
								&& emailText.getDocument().getLength() > 0) {
							registerButton.setEnabled(true);
						} else {
							registerButton.setEnabled(false);
							resetButton.setEnabled(true);
						}

					}
				});
		
		emailText.addKeyListener(
				new KeyAdapter() {
					public void keyReleased(KeyEvent keyEvent) {
						if (fullNameText.getDocument().getLength() > 0 && addressText.getDocument().getLength() > 0 
								&& phoneNumText.getDocument().getLength() > 0 && countyText.getDocument().getLength() > 0 
								&& emailText.getDocument().getLength() > 0) {
							registerButton.setEnabled(true);
						} else {
							registerButton.setEnabled(false);
							resetButton.setEnabled(true);
						}

					}
				});
		
		registerButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				LibraryMember l = Library.getInstance().newLibraryMemberRegistration(fullNameText.getText(), emailText.getText(), addressText.getText(),
						phoneNumText.getText(), countyText.getText());

				if (l != null) {
					newMemSuccessMsg.setText("You have successfully been registered. Your member ID is: " + l.getMembershipCardId());
				}
				
				registerButton.setEnabled(false);
				resetButton.setEnabled(true);
			}
		 
		});
		
		resetButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				fullNameText.setText(null);
				emailText.setText(null);
				addressText.setText(null);
				countyText.setText(null);
				phoneNumText.setText(null);
				registerButton.setEnabled(false);
				resetButton.setEnabled(false);
				newMemSuccessMsg.setText(null);
			}
		 
		});
		
		JScrollPane itemsInLibScrollPane = new JScrollPane();
		tabbedPane.addTab("Items In Library", null, itemsInLibScrollPane, null);
		
		JTabbedPane tabbedPaneInsideItemsInLib = new JTabbedPane(JTabbedPane.TOP);
		itemsInLibScrollPane.setViewportView(tabbedPaneInsideItemsInLib);
		
		JPanel returnTab = new JPanel();
		tabbedPaneInsideItemsInLib.addTab("Return Items", null, returnTab, null);
		SpringLayout sl_returnTab = new SpringLayout();
		returnTab.setLayout(sl_returnTab);
		
		JLabel itemIDReturnLabel = new JLabel("ItemID of the returned Item");
		itemIDReturnLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		returnTab.add(itemIDReturnLabel);
		
		itemIDReturnText = new JTextField();
		sl_returnTab.putConstraint(SpringLayout.NORTH, itemIDReturnLabel, 5, SpringLayout.NORTH, itemIDReturnText);
		sl_returnTab.putConstraint(SpringLayout.EAST, itemIDReturnLabel, -37, SpringLayout.WEST, itemIDReturnText);
		sl_returnTab.putConstraint(SpringLayout.NORTH, itemIDReturnText, 8, SpringLayout.NORTH, returnTab);
		sl_returnTab.putConstraint(SpringLayout.WEST, itemIDReturnText, 396, SpringLayout.WEST, returnTab);
		sl_returnTab.putConstraint(SpringLayout.EAST, itemIDReturnText, -147, SpringLayout.EAST, returnTab);
		itemIDReturnText.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		returnTab.add(itemIDReturnText);
		itemIDReturnText.setColumns(10);
		
		final JButton returnItemButton = new JButton("Return Item");
		sl_returnTab.putConstraint(SpringLayout.SOUTH, itemIDReturnText, -149, SpringLayout.NORTH, returnItemButton);
		sl_returnTab.putConstraint(SpringLayout.EAST, returnItemButton, -299, SpringLayout.EAST, returnTab);
		returnItemButton.setEnabled(false);
		returnTab.add(returnItemButton);
		
		final JLabel returnSuccessMsg = new JLabel("");
		sl_returnTab.putConstraint(SpringLayout.NORTH, returnSuccessMsg, 263, SpringLayout.NORTH, returnTab);
		sl_returnTab.putConstraint(SpringLayout.SOUTH, returnSuccessMsg, -315, SpringLayout.SOUTH, returnTab);
		sl_returnTab.putConstraint(SpringLayout.SOUTH, returnItemButton, -48, SpringLayout.NORTH, returnSuccessMsg);
		sl_returnTab.putConstraint(SpringLayout.WEST, returnSuccessMsg, 199, SpringLayout.WEST, returnTab);
		sl_returnTab.putConstraint(SpringLayout.EAST, returnSuccessMsg, -174, SpringLayout.EAST, returnTab);
		returnTab.add(returnSuccessMsg);
		
		JLabel memIDWhileReturning = new JLabel("Please enter your member ID:");
		sl_returnTab.putConstraint(SpringLayout.NORTH, memIDWhileReturning, 35, SpringLayout.SOUTH, itemIDReturnLabel);
		sl_returnTab.putConstraint(SpringLayout.WEST, memIDWhileReturning, 127, SpringLayout.WEST, returnTab);
		sl_returnTab.putConstraint(SpringLayout.SOUTH, memIDWhileReturning, 64, SpringLayout.SOUTH, itemIDReturnLabel);
		sl_returnTab.putConstraint(SpringLayout.EAST, memIDWhileReturning, 0, SpringLayout.EAST, itemIDReturnLabel);
		memIDWhileReturning.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		returnTab.add(memIDWhileReturning);
		
		memIDTextwhileReturning = new JTextField();
		memIDTextwhileReturning.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		sl_returnTab.putConstraint(SpringLayout.WEST, memIDTextwhileReturning, 41, SpringLayout.EAST, memIDWhileReturning);
		sl_returnTab.putConstraint(SpringLayout.SOUTH, memIDTextwhileReturning, 0, SpringLayout.SOUTH, memIDWhileReturning);
		returnTab.add(memIDTextwhileReturning);
		memIDTextwhileReturning.setColumns(5);
		
		// So that the pay fines button is enabled when user types something
		itemIDReturnText.addKeyListener(
				new KeyAdapter() {
					public void keyReleased(KeyEvent keyEvent) {
						returnSuccessMsg.setText("");
						if (itemIDReturnText.getDocument().getLength() > 0) {
							returnItemButton.setEnabled(true);
						} else {
							returnItemButton.setEnabled(false);
						}

					}
				});
		
		returnItemButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				returnSuccessMsg.setText("");
				try {
					int rc = Library.getInstance().newReturnTransactionByMember(memIDTextwhileReturning.getText(), itemIDReturnText.getText());
					switch (rc) {
					case -1:
						returnSuccessMsg.setText("MemID/ItemID not valid");
						break;
					case -2:
						returnSuccessMsg.setText("No transactions found for this memID");
						break;
					case -3:
						returnSuccessMsg.setText("Maybe you had not borrowed this item..");
						break;
					case 0:
						returnSuccessMsg.setText("Successfully returned item.");
						break;
					}
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			} 
		});
		
		JPanel payFineTab = new JPanel();
		tabbedPaneInsideItemsInLib.addTab("Pay Fines", null, payFineTab, null);
		SpringLayout sl_payFineTab = new SpringLayout();
		payFineTab.setLayout(sl_payFineTab);
		
		JLabel enterMemIDLabelInPayFines = new JLabel("Enter your Member ID:");
		sl_payFineTab.putConstraint(SpringLayout.SOUTH, enterMemIDLabelInPayFines, -592, SpringLayout.SOUTH, payFineTab);
		enterMemIDLabelInPayFines.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		payFineTab.add(enterMemIDLabelInPayFines);
		
		memIDTextInPayFines = new JTextField();
		sl_payFineTab.putConstraint(SpringLayout.EAST, enterMemIDLabelInPayFines, -6, SpringLayout.WEST, memIDTextInPayFines);
		sl_payFineTab.putConstraint(SpringLayout.NORTH, memIDTextInPayFines, -6, SpringLayout.NORTH, enterMemIDLabelInPayFines);
		memIDTextInPayFines.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		payFineTab.add(memIDTextInPayFines);
		memIDTextInPayFines.setColumns(5);
		
		final JButton lookupFineButton = new JButton("Lookup Fines");
		sl_payFineTab.putConstraint(SpringLayout.WEST, lookupFineButton, 349, SpringLayout.WEST, payFineTab);
		sl_payFineTab.putConstraint(SpringLayout.EAST, memIDTextInPayFines, -20, SpringLayout.WEST, lookupFineButton);
		sl_payFineTab.putConstraint(SpringLayout.NORTH, lookupFineButton, -2, SpringLayout.NORTH, enterMemIDLabelInPayFines);
		lookupFineButton.setEnabled(false);
		payFineTab.add(lookupFineButton);
		
		JLabel needToPayLabel = new JLabel("You need to pay:");
		sl_payFineTab.putConstraint(SpringLayout.NORTH, needToPayLabel, 34, SpringLayout.SOUTH, enterMemIDLabelInPayFines);
		sl_payFineTab.putConstraint(SpringLayout.WEST, needToPayLabel, 100, SpringLayout.WEST, payFineTab);
		sl_payFineTab.putConstraint(SpringLayout.SOUTH, needToPayLabel, -530, SpringLayout.SOUTH, payFineTab);
		sl_payFineTab.putConstraint(SpringLayout.EAST, needToPayLabel, 0, SpringLayout.EAST, enterMemIDLabelInPayFines);
		needToPayLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		payFineTab.add(needToPayLabel);
		
		needToPayText = new JTextField();
		sl_payFineTab.putConstraint(SpringLayout.NORTH, needToPayText, -2, SpringLayout.NORTH, needToPayLabel);
		sl_payFineTab.putConstraint(SpringLayout.WEST, needToPayText, 240, SpringLayout.WEST, payFineTab);
		sl_payFineTab.putConstraint(SpringLayout.EAST, needToPayText, 0, SpringLayout.EAST, memIDTextInPayFines);
		needToPayText.setEditable(false);
		needToPayText.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		payFineTab.add(needToPayText);
		needToPayText.setColumns(10);
		
		final JButton payFinesButton = new JButton("OK, pay my fines");
		sl_payFineTab.putConstraint(SpringLayout.NORTH, payFinesButton, 2, SpringLayout.NORTH, needToPayLabel);
		sl_payFineTab.putConstraint(SpringLayout.WEST, payFinesButton, 21, SpringLayout.EAST, needToPayText);
		payFinesButton.setEnabled(false);
		payFineTab.add(payFinesButton);
		
		final JLabel successLabel = new JLabel("");
		sl_payFineTab.putConstraint(SpringLayout.WEST, successLabel, 0, SpringLayout.WEST, needToPayLabel);
		sl_payFineTab.putConstraint(SpringLayout.EAST, successLabel, 553, SpringLayout.WEST, payFineTab);
		successLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		sl_payFineTab.putConstraint(SpringLayout.NORTH, successLabel, 77, SpringLayout.SOUTH, payFinesButton);
		sl_payFineTab.putConstraint(SpringLayout.SOUTH, successLabel, 120, SpringLayout.SOUTH, payFinesButton);
		payFineTab.add(successLabel);
		

		// So that the pay fines button is enabled when user types something
		memIDTextInPayFines.addKeyListener(
				new KeyAdapter() {
					public void keyReleased(KeyEvent keyEvent) {
						successLabel.setText("");
						if (memIDTextInPayFines.getDocument().getLength() > 0) {
							lookupFineButton.setEnabled(true);
						} else {
							lookupFineButton.setEnabled(false);
						}

					}
				});

		lookupFineButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				successLabel.setText("");
				Double f = Library.getInstance().lookupFinesForLibraryMember(memIDTextInPayFines.getText());
				if (f > 0) {
					payFinesButton.setEnabled(true);
					needToPayText.setText("$".concat(f.toString()));
				} else if (f < 0) {
					successLabel.setText("Enter correct member ID and retry !");
					lookupFineButton.setEnabled(false);
				} else if (f == 0) {
					successLabel.setText("Hurray, No fines to pay!");
					needToPayText.setText("");

				}
			} 
		});

		payFinesButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				boolean success = Library.getInstance().finesPaidByLibraryMember(memIDTextInPayFines.getText(), 
						Library.getInstance().lookupFinesForLibraryMember(memIDTextInPayFines.getText()));
				payFinesButton.setEnabled(false);
				successLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
				if (success != true) {
					successLabel.setText("Enter correct member ID and retry payment");
				} else {
					successLabel.setText("Payment successful. You have no fines to pay.");
				}
			} 
		});
		
		JPanel searchPane = new JPanel();
		searchPane.setBackground(UIManager.getColor("List.background"));
		tabbedPaneInsideItemsInLib.addTab("Search Items", null, searchPane, null);
		searchPane.setLayout(null);
		
		JLabel searchItemsLabel = new JLabel("Enter search keyword:");
		searchItemsLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		searchItemsLabel.setBounds(33, 30, 195, 36);
		searchPane.add(searchItemsLabel);
		searchItemsLabel.setHorizontalAlignment(SwingConstants.LEFT);
		
		searchItemstext = new JTextField();
		searchItemstext.setBounds(240, 33, 138, 31);
		searchPane.add(searchItemstext);
		searchItemstext.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		searchItemstext.setToolTipText("Enter text to search - ItemID/Title");

		searchItemstext.setColumns(10);

		
		final JButton searchItemsButton = new JButton("Search for Item");
		searchItemsButton.setBounds(401, 32, 115, 34);
		searchPane.add(searchItemsButton);
		searchItemsButton.setEnabled(false);
		searchItemsButton.setMaximumSize(new Dimension(20,10));

	
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 78, 603, 406);
		searchPane.add(scrollPane);
		
		table = new SearchResultsItemsTable();
		scrollPane.setViewportView(table);
		
		final JButton reserveButton = new JButton("Reserve Item");
		reserveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		reserveButton.setEnabled(false);
		reserveButton.setBounds(111, 547, 117, 29);
		searchPane.add(reserveButton);
		
		JLabel memIDForCheckoutReserveLabel = new JLabel("Enter member ID to reserve or checkout:");
		memIDForCheckoutReserveLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		memIDForCheckoutReserveLabel.setBounds(69, 499, 322, 36);
		searchPane.add(memIDForCheckoutReserveLabel);
		
		final JLabel checkoutReserveStatusLabel = new JLabel("");
		checkoutReserveStatusLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		checkoutReserveStatusLabel.setBounds(121, 588, 371, 31);
		searchPane.add(checkoutReserveStatusLabel);
		
		final JButton checkoutButton = new JButton("Checkout Item");
		checkoutButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				switch (Library.getInstance().newCheckoutTransactionByMember(memIDForCheckoutReserve.getText(),
						(String) (table.getValueAt(table.getSelectedRow(), 1)))) {
				case -1:
					checkoutReserveStatusLabel.setText("Fine not paid or memID not found");
					break;
				case -2:
					checkoutReserveStatusLabel.setText("ItemID not found");
					break;
				case -3:
					checkoutReserveStatusLabel.setText("Somebody else has already reserved Item");
					break;
				case -4:
					checkoutReserveStatusLabel.setText("Invalid Item state");
					break;
				case -5:
					checkoutReserveStatusLabel.setText("You have checked out max items of this type");
					break;
				case 1:
					checkoutReserveStatusLabel.setText("Item checkedout successfully!");
					break;
				}
		       // table.clear();
				checkoutButton.setEnabled(false);
				reserveButton.setEnabled(false);
		        
						
			}
		});
		
		// So that the button is enabled when user types something
		searchItemstext.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent keyEvent) {
				if (searchItemstext.getDocument().getLength() > 0) {
					searchItemsButton.setEnabled(true);
				} else {
					// Disable all buttons if nothing is searched
					searchItemsButton.setEnabled(false);
			        table.clear();
			        checkoutButton.setEnabled(false);
					reserveButton.setEnabled(false);
			        
				}

			}
		});
		
		
		checkoutButton.setEnabled(false);
		checkoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		checkoutButton.setBounds(316, 547, 117, 29);
		searchPane.add(checkoutButton);
		
		memIDForCheckoutReserve = new JTextField();
		memIDForCheckoutReserve.setBounds(401, 504, 134, 28);
		searchPane.add(memIDForCheckoutReserve);
		memIDForCheckoutReserve.setColumns(5);
		
		
		
		// So that the button is enabled when user types something
		memIDForCheckoutReserve.addKeyListener(new KeyAdapter() {
					public void keyReleased(KeyEvent keyEvent) {
						if (memIDForCheckoutReserve.getDocument().getLength() == 5 && (table.getSelectedRows()).length != 0 ) {
						    
							Item i = Library.getInstance().getItemIfPresent((String) (table.getValueAt(table.getSelectedRow(), 1)));
				            if (i != null) {
				            	if (i.getItemState() == ItemStates.AVAILABLE ) {
				            		checkoutButton.setEnabled(true);
				            	} else if (i.getItemState() == ItemStates.RESERVED) {
				            		if (i.getItemReservedBy() == memIDForCheckoutReserve.getText()) {
					            		checkoutButton.setEnabled(true);
				            		}
				            	} else if (i.getItemState() == ItemStates.CHECKEDOUT) {
				            		reserveButton.setEnabled(true);
				            	}
				            }				            
					
						} else {
							checkoutButton.setEnabled(false);
							reserveButton.setEnabled(false);

						}

					}
				});
				
		searchItemsButton.addActionListener(new ActionListener() { 
		    public void actionPerformed(ActionEvent e) { 
				checkoutReserveStatusLabel.setText(null);
		        ArrayList<Item> l = (ArrayList<Item>) Library.getInstance().queryIfItemAvailable(searchItemstext.getText());
		        table.clear();
		        table.addItems(l);
		    } 
		});
		
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent event) {
				// do some actions here, for example
				// print first column value from selected row
				if (table.getSelectedRows().length != 0 ) {
					checkoutButton.setEnabled(false);
					reserveButton.setEnabled(false);
					Item i = Library.getInstance().getItemIfPresent((String) (table.getValueAt(table.getSelectedRow(), 1)));
					if (i != null && memIDForCheckoutReserve.getDocument().getLength() == 5) {
						if (i.getItemState() == ItemStates.AVAILABLE ) {
							checkoutButton.setEnabled(true);
						} else if (i.getItemState() == ItemStates.RESERVED) {
							if (i.getItemReservedBy() == memIDForCheckoutReserve.getText()) {
								checkoutButton.setEnabled(true);
							}
						} else if (i.getItemState() == ItemStates.CHECKEDOUT) {
							reserveButton.setEnabled(true);
						}
					}				            

				} else {
					checkoutButton.setEnabled(false);
					reserveButton.setEnabled(false);
				}
			}
		});
		
		
		JPanel queryTransTab = new JPanel();
		tabbedPaneInsideItemsInLib.addTab("Query previous transactions", null, queryTransTab, null);
		SpringLayout sl_queryTransTab = new SpringLayout();
		queryTransTab.setLayout(sl_queryTransTab);
		
		JLabel queryLabel = new JLabel("Query your previous transactions");
		sl_queryTransTab.putConstraint(SpringLayout.NORTH, queryLabel, 11, SpringLayout.NORTH, queryTransTab);
		sl_queryTransTab.putConstraint(SpringLayout.WEST, queryLabel, 106, SpringLayout.WEST, queryTransTab);
		queryLabel.setToolTipText("Enter your member ID here");
		queryTransTab.add(queryLabel);
		
		queryTransText = new JTextField();
		sl_queryTransTab.putConstraint(SpringLayout.NORTH, queryTransText, 5, SpringLayout.NORTH, queryTransTab);
		sl_queryTransTab.putConstraint(SpringLayout.WEST, queryTransText, 320, SpringLayout.WEST, queryTransTab);
		queryTransTab.add(queryTransText);
		queryTransText.setColumns(10);
		
		JScrollPane queryScrollPane = new JScrollPane();
		sl_queryTransTab.putConstraint(SpringLayout.WEST, queryScrollPane, 56, SpringLayout.WEST, queryTransTab);
		sl_queryTransTab.putConstraint(SpringLayout.EAST, queryScrollPane, -54, SpringLayout.EAST, queryTransTab);
		//queryScrollPane.setBounds(6, 78, 603, 406);
		queryTransTab.add(queryScrollPane);
		
		
		queryResults = new QueryTransactionTable();
	

		queryResults.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		queryScrollPane.setViewportView(queryResults);

		queryResults.setRowSelectionAllowed(false);
		

		final JButton queryTransButton = new JButton("Query transaction");
		sl_queryTransTab.putConstraint(SpringLayout.NORTH, queryScrollPane, 70, SpringLayout.SOUTH, queryTransButton);
		sl_queryTransTab.putConstraint(SpringLayout.SOUTH, queryScrollPane, 490, SpringLayout.SOUTH, queryTransButton);

		sl_queryTransTab.putConstraint(SpringLayout.NORTH, queryResults, 439, SpringLayout.SOUTH, queryTransButton);
		sl_queryTransTab.putConstraint(SpringLayout.WEST, queryResults, 165, SpringLayout.WEST, queryTransTab);
		sl_queryTransTab.putConstraint(SpringLayout.SOUTH, queryResults, 96, SpringLayout.SOUTH, queryTransText);
		sl_queryTransTab.putConstraint(SpringLayout.EAST, queryResults, -59, SpringLayout.EAST, queryTransTab);
		
		
				
		final JLabel querySuccessMsg = new JLabel("");
		sl_queryTransTab.putConstraint(SpringLayout.NORTH, querySuccessMsg, 23, SpringLayout.SOUTH, queryTransButton);
		sl_queryTransTab.putConstraint(SpringLayout.WEST, querySuccessMsg, 262, SpringLayout.WEST, queryTransTab);
		sl_queryTransTab.putConstraint(SpringLayout.SOUTH, querySuccessMsg, -19, SpringLayout.NORTH, queryScrollPane);
		sl_queryTransTab.putConstraint(SpringLayout.EAST, querySuccessMsg, -235, SpringLayout.EAST, queryTransTab);
		queryTransTab.add(querySuccessMsg);
		
		// So that the button is enabled when user types something
				queryTransText.addKeyListener(new KeyAdapter() {
							public void keyReleased(KeyEvent keyEvent) {
					        	querySuccessMsg.setText(null);
								if (queryTransText.getDocument().getLength() == 5) {
									queryTransButton.setEnabled(true);
								} else {
									queryTransButton.setEnabled(false);
									queryResults.clear();

								}

							}
						});
				
		queryTransButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList<Transaction> t = (ArrayList<Transaction>)Library.getInstance().queryForTransactionHistory(queryTransText.getText());
				queryResults.clear();
		        queryResults.addItems(t);
		        if (t == null) {
		        	querySuccessMsg.setText("Please check the member ID again");
		        }
			}
		});
		
		sl_queryTransTab.putConstraint(SpringLayout.NORTH, queryTransButton, 5, SpringLayout.NORTH, queryTransTab);
		sl_queryTransTab.putConstraint(SpringLayout.WEST, queryTransButton, 459, SpringLayout.WEST, queryTransTab);
		queryTransButton.setEnabled(false);
		queryTransTab.add(queryTransButton);
		
		
		
	
		
		JScrollPane adminScrollPane = new JScrollPane();
		JTabbedPane libAdminTabs = new JTabbedPane(JTabbedPane.TOP);
		adminScrollPane.setViewportView(libAdminTabs);
		
		JPanel findNumOfItems = new JPanel();
		libAdminTabs.addTab("Items in Library", null, findNumOfItems, null);
		
		JPanel panel_2 = new JPanel();
		libAdminTabs.addTab("New tab", null, panel_2, null);
		
		JPanel panel_3 = new JPanel();
		libAdminTabs.addTab("New tab", null, panel_3, null);
		tabbedPane.addTab("Library Admin", null, adminScrollPane, null);
	}
}
