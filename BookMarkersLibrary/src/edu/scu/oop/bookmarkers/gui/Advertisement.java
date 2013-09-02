package edu.scu.oop.bookmarkers.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JFrame;

import javax.swing.JLabel;
import javax.swing.SpringLayout;

import edu.scu.oop.bookmarkers.model.Item;
import edu.scu.oop.bookmarkers.model.Library;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.swing.border.LineBorder;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;

public class Advertisement extends JPanel{
	JLabel lblNewEntries;
	private SpringLayout springLayout;
	private JLabel newEntryLabel;
	private JLabel authorNewItem ;
	public static Thread adThread;
	private JLabel itemIDLabel;
	private JLabel picLabel;
	private JLabel prevItemID;
	private JLabel prevItemAuthor;
	private JLabel prevItemTitle;
	
	public Advertisement () {
		initialize();
		this.setBackground(Color.white);
		
	}
	
	private void initialize() {
		springLayout = new SpringLayout();
		setLayout(springLayout);
		lblNewEntries = new JLabel("New Entries!");
		lblNewEntries.setForeground(Color.MAGENTA);
		lblNewEntries.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		springLayout.putConstraint(SpringLayout.NORTH, lblNewEntries, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.EAST, lblNewEntries, 124, SpringLayout.WEST, this);
		lblNewEntries.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		this.add(lblNewEntries);
		
		
		newEntryLabel = new JLabel("");
		newEntryLabel.setForeground(Color.BLACK);
		springLayout.putConstraint(SpringLayout.NORTH, newEntryLabel, 41, SpringLayout.SOUTH, lblNewEntries);
		springLayout.putConstraint(SpringLayout.WEST, lblNewEntries, 0, SpringLayout.WEST, newEntryLabel);
		springLayout.putConstraint(SpringLayout.WEST, newEntryLabel, 20, SpringLayout.WEST, this);
		add(newEntryLabel);
		
		authorNewItem = new JLabel("");
		springLayout.putConstraint(SpringLayout.NORTH, authorNewItem, 31, SpringLayout.SOUTH, newEntryLabel);
		springLayout.putConstraint(SpringLayout.WEST, authorNewItem, 0, SpringLayout.WEST, lblNewEntries);
		add(authorNewItem);

		itemIDLabel = new JLabel("");
		itemIDLabel.setBorder(UIManager.getBorder("FormattedTextField.border"));
		itemIDLabel.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		itemIDLabel.setForeground(Color.BLUE);
		springLayout.putConstraint(SpringLayout.NORTH, itemIDLabel, 117, SpringLayout.SOUTH, lblNewEntries);
		springLayout.putConstraint(SpringLayout.WEST, itemIDLabel, 0, SpringLayout.WEST, lblNewEntries);
		add(itemIDLabel);
		
		picLabel = new JLabel("");
		springLayout.putConstraint(SpringLayout.NORTH, picLabel, -217, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.WEST, picLabel, 21, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, picLabel, -10, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, picLabel, 0, SpringLayout.EAST, lblNewEntries);
		add(picLabel);
		
		prevItemID = new JLabel("prevItemID");
		prevItemID.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		prevItemID.setBorder(UIManager.getBorder("FormattedTextField.border"));
		prevItemID.setForeground(Color.BLUE);
		springLayout.putConstraint(SpringLayout.WEST, prevItemID, 0, SpringLayout.WEST, lblNewEntries);
		springLayout.putConstraint(SpringLayout.SOUTH, prevItemID, -62, SpringLayout.NORTH, picLabel);
		add(prevItemID);
		
		prevItemAuthor = new JLabel("prevItemAuthor");
		springLayout.putConstraint(SpringLayout.WEST, prevItemAuthor, 0, SpringLayout.WEST, lblNewEntries);
		springLayout.putConstraint(SpringLayout.SOUTH, prevItemAuthor, -37, SpringLayout.NORTH, prevItemID);
		add(prevItemAuthor);
		
		prevItemTitle = new JLabel("prevItemTitle");
		springLayout.putConstraint(SpringLayout.WEST, prevItemTitle, 0, SpringLayout.WEST, lblNewEntries);
		springLayout.putConstraint(SpringLayout.SOUTH, prevItemTitle, -27, SpringLayout.NORTH, prevItemAuthor);
		add(prevItemTitle);
		
		adThread = (new Thread ( new AdvertisementThread()));
		adThread.start();
	}
	
	private class AdvertisementThread implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			boolean execute = true;
			BufferedImage myPicture = null;
			try {
				myPicture = ImageIO.read(new File("books.jpg"));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				execute = false;
			}
			
			if (execute == true) {
				picLabel.setIcon(new ImageIcon(myPicture));
				//add(picLabel);
			}
			
			while (execute == true) {
				List <Item> newPrevItem = Library.getInstance().getNewestItem();
			
				// List 0 has newest item, 1 has next newest item
				newEntryLabel.setText(newPrevItem.get(0).getItemTitle());
				authorNewItem.setText("By: " + newPrevItem.get(0).getAuthor());
				itemIDLabel.setText("ItemID: " + newPrevItem.get(0).getItemID());

				prevItemAuthor.setText(null);
				prevItemID.setText(null);
				
				prevItemTitle.setFont(new Font("Lucida Grande", Font.BOLD, 13));
				prevItemTitle.setText("Get it now!");

				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					execute = false;
				}
				

				prevItemTitle.setText(newPrevItem.get(1).getItemTitle());
				prevItemAuthor.setText("By: " + newPrevItem.get(1).getAuthor());
				prevItemID.setText("ItemID: " + newPrevItem.get(1).getItemID());

				newEntryLabel.setFont(new Font("Lucida Grande", Font.BOLD, 13));
				newEntryLabel.setText("Get it now!");
				authorNewItem.setText(null);
				itemIDLabel.setText(null);
				
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					execute = false;
				}
			}
		}
		
	}
}
