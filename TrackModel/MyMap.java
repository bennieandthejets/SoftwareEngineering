package TrackModel;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
	 
	/**
	 * This is like TableDemo, except that it substitutes a
	 * Favorite Color column for the Last Name column and specifies
	 * a custom cell renderer and editor for the color data.
	 */
public class MyMap extends JPanel {
	 
	Object[][] data;
	JTable table;
	
	public MyMap(Object[][] data) 
	{
		super(new GridLayout(1,0));
	 
		this.data = data;
		
	    table = new JTable(new MyTableModel());
	    table.setPreferredScrollableViewportSize(new Dimension(500, 70));
	    table.setFillsViewportHeight(true);
	 
	        //Set up renderer and editor for the Favorite Color column.
	    table.setDefaultRenderer(Color.class, new ColorRenderer(true));
	    //table.setDefaultEditor(Color.class, new ColorEditor());
	 
	}
	 
	public JTable getTable()
	{
		return table;
	}
	
	class MyTableModel extends AbstractTableModel {
	 
	        public int getColumnCount() {
	            return data[0].length;
	        }
	 
	        public int getRowCount() {
	            return data.length;
	        }
	 
	        public Object getValueAt(int row, int col) {
	            return data[row][col];
	        }
	 
	        /*
	         * JTable uses this method to determine the default renderer/
	         * editor for each cell.  If we didn't implement this method,
	         * then the last column would contain text ("true"/"false"),
	         * rather than a check box.
	         */
	        public Class getColumnClass(int c) {
	            return getValueAt(0, c).getClass();
	        }
	 
	        public boolean isCellEditable(int row, int col) {
	            //Note that the data/cell address is constant,
	            //no matter where the cell appears onscreen.
	            if (col < 1) {
	                return false;
	            } else {
	                return true;
	            }
	        }
	 
	        public void setValueAt(Object value, int row, int col) {
	 
	            data[row][col] = value;
	            fireTableCellUpdated(row, col);
	 
	        }
	 
	    }
}