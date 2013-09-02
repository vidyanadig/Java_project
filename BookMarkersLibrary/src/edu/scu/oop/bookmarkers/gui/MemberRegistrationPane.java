package edu.scu.oop.bookmarkers.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import org.apache.commons.lang3.StringUtils;

import edu.scu.oop.bookmarkers.model.Library;
import edu.scu.oop.bookmarkers.model.LibraryMember;

public class MemberRegistrationPane extends JPanel {
	
	private JTextField fullNameText;
	private JLabel newMemRegLabel;
	private JLabel fullNameLabel;
	private JTextField addressText;
	private JTextField phoneNumText;
	private JTextField countyText;
	private JTextField emailText;
	private JButton registerButton;
	private JButton resetButton;
	private JLabel newMemSuccessMsg;
	
	public MemberRegistrationPane() {
		initialize();
		registerListeners();
	}

	public void initialize() {
		SpringLayout sl_newMemberRegPanel = new SpringLayout();
		this.setLayout(sl_newMemberRegPanel);
		
	    fullNameLabel = new JLabel("Full name:");
		fullNameLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		this.add(fullNameLabel);
		
	    newMemRegLabel = new JLabel("New member registration");
		newMemRegLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		sl_newMemberRegPanel.putConstraint(SpringLayout.NORTH, newMemRegLabel, 30, SpringLayout.NORTH, this);
		sl_newMemberRegPanel.putConstraint(SpringLayout.WEST, newMemRegLabel, 169, SpringLayout.WEST, this);
		sl_newMemberRegPanel.putConstraint(SpringLayout.SOUTH, newMemRegLabel, 55, SpringLayout.NORTH, this);
		sl_newMemberRegPanel.putConstraint(SpringLayout.EAST, newMemRegLabel, 453, SpringLayout.WEST, this);
		this.add(newMemRegLabel);
		
		fullNameText = new JTextField();
		sl_newMemberRegPanel.putConstraint(SpringLayout.NORTH, fullNameText, 38, SpringLayout.SOUTH, newMemRegLabel);
		sl_newMemberRegPanel.putConstraint(SpringLayout.WEST, fullNameText, 232, SpringLayout.WEST, this);
		sl_newMemberRegPanel.putConstraint(SpringLayout.SOUTH, fullNameLabel, 0, SpringLayout.SOUTH, fullNameText);
		sl_newMemberRegPanel.putConstraint(SpringLayout.EAST, fullNameLabel, -7, SpringLayout.WEST, fullNameText);
		fullNameText.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		this.add(fullNameText);
		fullNameText.setColumns(20);
		
		addressText = new JTextField();
		sl_newMemberRegPanel.putConstraint(SpringLayout.WEST, addressText, 0, SpringLayout.WEST, fullNameText);
		addressText.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		addressText.setColumns(10);
		this.add(addressText);
		
		JLabel addrCityLabel = new JLabel("Address (City):");
		sl_newMemberRegPanel.putConstraint(SpringLayout.NORTH, addressText, -6, SpringLayout.NORTH, addrCityLabel);
		sl_newMemberRegPanel.putConstraint(SpringLayout.NORTH, addrCityLabel, 35, SpringLayout.SOUTH, fullNameLabel);
		sl_newMemberRegPanel.putConstraint(SpringLayout.EAST, addrCityLabel, 0, SpringLayout.EAST, fullNameLabel);
		addrCityLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		this.add(addrCityLabel);
		
		JLabel phoneNumLabel = new JLabel("Phone Number:");
		sl_newMemberRegPanel.putConstraint(SpringLayout.NORTH, phoneNumLabel, 48, SpringLayout.SOUTH, addrCityLabel);
		sl_newMemberRegPanel.putConstraint(SpringLayout.WEST, phoneNumLabel, 101, SpringLayout.WEST, this);
		phoneNumLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		this.add(phoneNumLabel);
		
		phoneNumText = new JTextField();
		sl_newMemberRegPanel.putConstraint(SpringLayout.NORTH, phoneNumText, -6, SpringLayout.NORTH, phoneNumLabel);
		sl_newMemberRegPanel.putConstraint(SpringLayout.WEST, phoneNumText, 0, SpringLayout.WEST, fullNameText);
		phoneNumText.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		phoneNumText.setColumns(10);
		this.add(phoneNumText);
		
		countyText = new JTextField();
		sl_newMemberRegPanel.putConstraint(SpringLayout.WEST, countyText, 0, SpringLayout.WEST, fullNameText);
		countyText.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		countyText.setColumns(10);
		this.add(countyText);
		
		JLabel countyLabel = new JLabel("County:");
		sl_newMemberRegPanel.putConstraint(SpringLayout.EAST, countyLabel, -17, SpringLayout.WEST, countyText);
		sl_newMemberRegPanel.putConstraint(SpringLayout.NORTH, countyText, 0, SpringLayout.NORTH, countyLabel);
		sl_newMemberRegPanel.putConstraint(SpringLayout.NORTH, countyLabel, 41, SpringLayout.SOUTH, phoneNumLabel);
		countyLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		this.add(countyLabel);
		
		emailText = new JTextField();
		sl_newMemberRegPanel.putConstraint(SpringLayout.WEST, emailText, 0, SpringLayout.WEST, fullNameText);
		emailText.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		emailText.setColumns(10);
		this.add(emailText);
		
		JLabel emailLabel = new JLabel("Email ID:");
		sl_newMemberRegPanel.putConstraint(SpringLayout.NORTH, emailText, -6, SpringLayout.NORTH, emailLabel);
		sl_newMemberRegPanel.putConstraint(SpringLayout.NORTH, emailLabel, 42, SpringLayout.SOUTH, countyLabel);
		sl_newMemberRegPanel.putConstraint(SpringLayout.WEST, emailLabel, 0, SpringLayout.WEST, fullNameLabel);
		emailLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		this.add(emailLabel);
		
		registerButton = new JButton("Register");
		sl_newMemberRegPanel.putConstraint(SpringLayout.NORTH, registerButton, 64, SpringLayout.SOUTH, emailText);
		sl_newMemberRegPanel.putConstraint(SpringLayout.WEST, registerButton, 0, SpringLayout.WEST, newMemRegLabel);
		registerButton.setEnabled(false);
		this.add(registerButton);
		
		resetButton = new JButton("Reset");
		sl_newMemberRegPanel.putConstraint(SpringLayout.NORTH, resetButton, 0, SpringLayout.NORTH, registerButton);
		sl_newMemberRegPanel.putConstraint(SpringLayout.WEST, resetButton, 68, SpringLayout.EAST, registerButton);
		resetButton.setEnabled(false);
		this.add(resetButton);
		
		newMemSuccessMsg = new JLabel("");
		sl_newMemberRegPanel.putConstraint(SpringLayout.WEST, newMemSuccessMsg, 87, SpringLayout.WEST, this);
		sl_newMemberRegPanel.putConstraint(SpringLayout.SOUTH, newMemSuccessMsg, 126, SpringLayout.SOUTH, registerButton);
		sl_newMemberRegPanel.putConstraint(SpringLayout.EAST, newMemSuccessMsg, 464, SpringLayout.WEST, fullNameLabel);
		newMemSuccessMsg.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		sl_newMemberRegPanel.putConstraint(SpringLayout.NORTH, newMemSuccessMsg, 85, SpringLayout.SOUTH, registerButton);
		this.add(newMemSuccessMsg);
	}
	
	private void registerListeners() {
		// So that the register/reset button is enabled when user types something
				fullNameText.addKeyListener(
						new KeyAdapter() {
							public void keyReleased(KeyEvent keyEvent) {
								if (fullNameText.getText().length() > 0 && addressText.getText().length() > 0 
										&& phoneNumText.getText().length() == 10 && countyText.getText().length() > 0 
										&& emailText.getText().length() > 0 && StringUtils.isNumeric(phoneNumText.getText())) {
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
								if (fullNameText.getText().length() > 0 && addressText.getText().length() > 0 
										&& phoneNumText.getText().length() == 10 && countyText.getText().length() > 0 
										&& emailText.getText().length() > 0 && StringUtils.isNumeric(phoneNumText.getText())) {
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
								if (fullNameText.getText().length() > 0 && addressText.getText().length() > 0 
										&& phoneNumText.getText().length() == 10 && countyText.getText().length() > 0 
										&& emailText.getText().length() > 0 && StringUtils.isNumeric(phoneNumText.getText())) {
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
								if (fullNameText.getText().length() > 0 && addressText.getText().length() > 0 
										&& phoneNumText.getText().length() == 10 && countyText.getText().length() > 0 
										&& emailText.getText().length() > 0 && StringUtils.isNumeric(phoneNumText.getText())) {
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
								if (fullNameText.getText().length() > 0 && addressText.getText().length() > 0 
										&& phoneNumText.getText().length() == 10 && countyText.getText().length() > 0 
										&& emailText.getText().length() > 0 && StringUtils.isNumeric(phoneNumText.getText())) {
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
	}
}
