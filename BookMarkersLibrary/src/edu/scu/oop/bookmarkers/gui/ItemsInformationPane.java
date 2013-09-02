package edu.scu.oop.bookmarkers.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import edu.scu.oop.bookmarkers.model.Item;
import edu.scu.oop.bookmarkers.model.ItemStates;
import edu.scu.oop.bookmarkers.model.Library;
import edu.scu.oop.bookmarkers.model.Transaction;

public class ItemsInformationPane extends JScrollPane {
	public ItemsInformationPane() {
		initialize();
		registerListeners();
		registerQueryTabListeners();
	}
	
	private SearchResultsItemsTable table;
	private QueryTransactionTable queryResults;

	private JTextField itemIDReturnText;
	private JTextField memIDTextwhileReturning;
	private JTextField needToPayText;
	private JTextField memIDTextInPayFines;
	private JTextField searchItemstext;
	private JTextField memIDForCheckoutReserve;
	private JTextField queryTransText;
	
	private JButton returnItemButton;
	private JLabel returnSuccessMsg;
	private JButton payFinesButton;
	private JLabel successLabel;
	private JButton lookupFineButton;
	private JLabel checkoutReserveStatusLabel;
	private JButton reserveButton;
	private JButton checkoutButton;
	private JButton searchItemsButton;
	private JButton queryTransButton;
	private JLabel querySuccessMsg;
	
	private void initialize() {
		JTabbedPane tabbedPaneInsideItemsInLib = new JTabbedPane(JTabbedPane.TOP);
		this.setViewportView(tabbedPaneInsideItemsInLib);
		
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
		
		returnItemButton = new JButton("Return Item");
		sl_returnTab.putConstraint(SpringLayout.SOUTH, itemIDReturnText, -149, SpringLayout.NORTH, returnItemButton);
		sl_returnTab.putConstraint(SpringLayout.EAST, returnItemButton, -299, SpringLayout.EAST, returnTab);
		returnItemButton.setEnabled(false);
		returnTab.add(returnItemButton);
		
		returnSuccessMsg = new JLabel("");
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
		
		lookupFineButton = new JButton("Lookup Fines");
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
		
		payFinesButton = new JButton("OK, pay my fines");
		sl_payFineTab.putConstraint(SpringLayout.NORTH, payFinesButton, 2, SpringLayout.NORTH, needToPayLabel);
		sl_payFineTab.putConstraint(SpringLayout.WEST, payFinesButton, 21, SpringLayout.EAST, needToPayText);
		payFinesButton.setEnabled(false);
		payFineTab.add(payFinesButton);
		
		successLabel = new JLabel("");
		sl_payFineTab.putConstraint(SpringLayout.WEST, successLabel, 0, SpringLayout.WEST, needToPayLabel);
		sl_payFineTab.putConstraint(SpringLayout.EAST, successLabel, 553, SpringLayout.WEST, payFineTab);
		successLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		sl_payFineTab.putConstraint(SpringLayout.NORTH, successLabel, 77, SpringLayout.SOUTH, payFinesButton);
		sl_payFineTab.putConstraint(SpringLayout.SOUTH, successLabel, 120, SpringLayout.SOUTH, payFinesButton);
		payFineTab.add(successLabel);
		
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

		
		searchItemsButton = new JButton("Search for Item");
		searchItemsButton.setBounds(401, 32, 115, 34);
		searchPane.add(searchItemsButton);
		searchItemsButton.setEnabled(false);
		searchItemsButton.setMaximumSize(new Dimension(20,10));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 78, 698, 406);
		searchPane.add(scrollPane);
		
		table = new SearchResultsItemsTable();
		scrollPane.setViewportView(table);
		
		checkoutReserveStatusLabel = new JLabel("");
		checkoutReserveStatusLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		checkoutReserveStatusLabel.setBounds(121, 588, 371, 31);
		searchPane.add(checkoutReserveStatusLabel);
		
		reserveButton = new JButton("Reserve Item");
		reserveButton.setEnabled(false);
		reserveButton.setBounds(111, 547, 117, 29);
		searchPane.add(reserveButton);
		
		JLabel memIDForCheckoutReserveLabel = new JLabel("Enter member ID to reserve or checkout:");
		memIDForCheckoutReserveLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		memIDForCheckoutReserveLabel.setBounds(69, 499, 322, 36);
		searchPane.add(memIDForCheckoutReserveLabel);
	
		checkoutButton = new JButton("Checkout Item");
		checkoutButton.setEnabled(false);
		checkoutButton.setBounds(316, 547, 117, 29);
		searchPane.add(checkoutButton);
		
		memIDForCheckoutReserve = new JTextField();
		memIDForCheckoutReserve.setBounds(401, 504, 134, 28);
		searchPane.add(memIDForCheckoutReserve);
		memIDForCheckoutReserve.setColumns(5);
	
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
		

		queryTransButton = new JButton("Query transaction");
		sl_queryTransTab.putConstraint(SpringLayout.NORTH, queryScrollPane, 70, SpringLayout.SOUTH, queryTransButton);
		sl_queryTransTab.putConstraint(SpringLayout.SOUTH, queryScrollPane, 490, SpringLayout.SOUTH, queryTransButton);

		sl_queryTransTab.putConstraint(SpringLayout.NORTH, queryResults, 439, SpringLayout.SOUTH, queryTransButton);
		sl_queryTransTab.putConstraint(SpringLayout.WEST, queryResults, 165, SpringLayout.WEST, queryTransTab);
		sl_queryTransTab.putConstraint(SpringLayout.SOUTH, queryResults, 96, SpringLayout.SOUTH, queryTransText);
		sl_queryTransTab.putConstraint(SpringLayout.EAST, queryResults, -59, SpringLayout.EAST, queryTransTab);
				
		querySuccessMsg = new JLabel("");
		sl_queryTransTab.putConstraint(SpringLayout.NORTH, querySuccessMsg, 23, SpringLayout.SOUTH, queryTransButton);
		sl_queryTransTab.putConstraint(SpringLayout.WEST, querySuccessMsg, 200, SpringLayout.WEST, queryTransTab);
		sl_queryTransTab.putConstraint(SpringLayout.SOUTH, querySuccessMsg, -19, SpringLayout.NORTH, queryScrollPane);
		sl_queryTransTab.putConstraint(SpringLayout.EAST, querySuccessMsg, -148, SpringLayout.EAST, queryTransTab);
		queryTransTab.add(querySuccessMsg);
		
		sl_queryTransTab.putConstraint(SpringLayout.NORTH, queryTransButton, 5,
				SpringLayout.NORTH, queryTransTab);
		sl_queryTransTab.putConstraint(SpringLayout.WEST, queryTransButton,
				459, SpringLayout.WEST, queryTransTab);
		queryTransButton.setEnabled(false);
		queryTransTab.add(queryTransButton);
	}
	
	private void registerListeners() {
		// So that the pay fines button is enabled when user types something
		itemIDReturnText.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent keyEvent) {
				returnSuccessMsg.setText("");
				if (itemIDReturnText.getText().length() > 0) {
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
					int rc = Library.getInstance()
							.newReturnTransactionByMember(
									memIDTextwhileReturning.getText(),
									itemIDReturnText.getText());
					switch (rc) {
					case -1:
						returnSuccessMsg.setText("MemID/ItemID not valid");
						break;
					case -2:
						returnSuccessMsg
								.setText("No transactions found for this memID");
						break;
					case -3:
						returnSuccessMsg
								.setText("Maybe you had not borrowed this item..");
						break;
					case 0:
						returnSuccessMsg.setText("Successfully returned item.");
						break;
					}
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}

			}
		});

		// So that the pay fines button is enabled when user types something
		memIDTextInPayFines.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent keyEvent) {
				successLabel.setText("");
				if (memIDTextInPayFines.getText().length() > 0) {
					lookupFineButton.setEnabled(true);
				} else {
					lookupFineButton.setEnabled(false);
				}

			}
		});

		lookupFineButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				successLabel.setText("");
				Double f = Library.getInstance().lookupFinesForLibraryMember(
						memIDTextInPayFines.getText());
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
				boolean success = Library.getInstance()
						.finesPaidByLibraryMember(
								memIDTextInPayFines.getText(),
								Library.getInstance()
										.lookupFinesForLibraryMember(
												memIDTextInPayFines.getText()));
				payFinesButton.setEnabled(false);
				successLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
				if (success != true) {
					successLabel
							.setText("Enter correct member ID and retry payment");
				} else {
					successLabel
							.setText("Payment successful. You have no fines to pay.");
				}
			}
		});
		
		reserveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkoutReserveStatusLabel.setText(null);

				switch (Library.getInstance().reserveItem((String) (table.getValueAt(table.getSelectedRow(), 1)), memIDForCheckoutReserve.getText())) {
				case -1:
					checkoutReserveStatusLabel.setText("MemID wrong. Please reenter");
					break;
				case -2:
					checkoutReserveStatusLabel.setText("Item is not in checkout state, so cannot reserve");
					break;
				case 1:
					checkoutReserveStatusLabel.setText("Item reserved successfully!");
					break;
				}
			}

		});
		
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
				checkoutReserveStatusLabel.setText(null);

				if (searchItemstext.getText().length() > 0) {
					searchItemsButton.setEnabled(true);
				} else {
					// Disable all buttons if nothing is searched
					searchItemsButton.setEnabled(false);
			        table.clear();
			        checkoutButton.setEnabled(false);
					reserveButton.setEnabled(false);
					checkoutReserveStatusLabel.setText(null);
				}

			}
		});
		
		checkoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		
		
		
		// So that the button is enabled when user types something
		memIDForCheckoutReserve.addKeyListener(new KeyAdapter() {
					public void keyReleased(KeyEvent keyEvent) {
						checkoutReserveStatusLabel.setText(null);
						if (memIDForCheckoutReserve.getText().length() == 5 && (table.getSelectedRows()).length != 0 ) {
						    
							Item i = Library.getInstance().getItemIfPresent((String) (table.getValueAt(table.getSelectedRow(), 1)));
				            if (i != null) {
				            	if (i.getItemState() == ItemStates.AVAILABLE ) {
				            		checkoutButton.setEnabled(true);
				            	} else if (i.getItemState() == ItemStates.RESERVED) {
				            		if (i.getItemReservedBy().toLowerCase().equals(memIDForCheckoutReserve.getText().toLowerCase())) {
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
				checkoutReserveStatusLabel.setText(null);

				if (table.getSelectedRows().length != 0 ) {
					checkoutButton.setEnabled(false);
					reserveButton.setEnabled(false);
					Item i = Library.getInstance().getItemIfPresent((String) (table.getValueAt(table.getSelectedRow(), 1)));
					if (i != null && memIDForCheckoutReserve.getText().length() == 5) {
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
	}
	
	private void registerQueryTabListeners() {
		// So that the button is enabled when user types something
		queryTransText.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent keyEvent) {
				querySuccessMsg.setText(null);
				if (queryTransText.getText().length() == 5) {
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
				ArrayList<Transaction> t = (ArrayList<Transaction>) Library
						.getInstance().queryForTransactionHistory(
								queryTransText.getText());
				queryResults.clear();
				queryResults.addItems(t);
				if (t == null) {
					querySuccessMsg
							.setText("No previous transaction found for "
									+ queryTransText.getText());
				}
			}
		});
	}
}
