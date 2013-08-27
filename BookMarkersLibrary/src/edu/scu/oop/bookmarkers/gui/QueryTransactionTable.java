package edu.scu.oop.bookmarkers.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;


import edu.scu.oop.bookmarkers.model.Transaction;

public class QueryTransactionTable extends JTable {

	
	public QueryTransactionTable() {
		super(new TransactionTableModel());
		this.getColumnModel().getColumn(0).setPreferredWidth(20);
		this.getColumnModel().getColumn(1).setPreferredWidth(20);
		this.getColumnModel().getColumn(2).setPreferredWidth(200);
		this.getColumnModel().getColumn(3).setPreferredWidth(200);
	}

	public void addItems(List<Transaction> transactions) {
		((TransactionTableModel)this.getModel()).add(transactions);
	}
	
	public void clear() {
		((TransactionTableModel)this.getModel()).clear();

	}
	
	private static class TransactionTableModel extends AbstractTableModel {
		
		List<Transaction> transactions;
	    String[] header = {"Transaction ID", "ItemID", "BorrowDate", "ReturnDate"};
	    
	    public TransactionTableModel() {
	    	transactions = new ArrayList<Transaction>();
	    }
		
		void add(List<Transaction> transactions) {
			if (null != transactions)
				this.transactions.addAll(transactions);
			fireTableDataChanged();
		}
		
		void clear() {
			this.transactions.clear();
			fireTableDataChanged();
		}
		
		@Override
		public int getRowCount() {
			if (transactions == null)
				return 0;
			return transactions.size();
		}

		@Override
		public int getColumnCount() {
			return header.length;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			switch (columnIndex) {
			case 0:
				return transactions.get(rowIndex).getTransID();
			case 1:
				return transactions.get(rowIndex).getItemID();
			case 2:
				return transactions.get(rowIndex).getBorrowDate();
			case 3:
				return transactions.get(rowIndex).getReturnDate();
			default:
				return null;
			}
		}
		
		@Override
		public String getColumnName(int index) {
			return header[index];
		}
	}
}
