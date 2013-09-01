package edu.scu.oop.bookmarkers.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import edu.scu.oop.bookmarkers.model.Item;
import edu.scu.oop.bookmarkers.model.ItemStates;

public class SearchResultsItemsTable extends JTable {

	
	public SearchResultsItemsTable() {
		super(new ItemsTableModel());
	}

	public void addItems(List<Item> items) {
		((ItemsTableModel)this.getModel()).add(items);
	}
	
	public void clear() {
		((ItemsTableModel)this.getModel()).clear();
	}
	
	private static class ItemsTableModel extends AbstractTableModel {
		
		List<Item> items;
	    String[] header = {"Title", "ItemID", "Type", "Author/Director", "State"};
	    
	    public ItemsTableModel() {
	    	items = new ArrayList<Item>();
	    }
		
		void add(List<Item> items) {
			if (null != items)
				this.items.addAll(items);
			fireTableDataChanged();
		}
		
		void clear() {
			this.items.clear();
			fireTableDataChanged();
		}
		
		@Override
		public int getRowCount() {
			if (items == null)
				return 0;
			return items.size();
		}

		@Override
		public int getColumnCount() {
			return header.length;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			switch (columnIndex) {
			case 0:
				return items.get(rowIndex).getItemTitle();
			case 1:
				return items.get(rowIndex).getItemID();
			case 2:
				return items.get(rowIndex).getItemType();
			case 3:
				return items.get(rowIndex).getAuthor();
			case 4:
				return items.get(rowIndex).getItemStateString();
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
