package TrackController;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JRadioButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.TextField;


public class UI {

	private JFrame frame;
	private TrackController owner;
	private JLabel lblNewLabel;
	private JTable train_table;
	DefaultTableModel trainModel;
	private JTable switch_table;
	DefaultTableModel switchModel;
	private JTextField PLCName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UI window = new UI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public UI() {
		//this.owner = new TrackCtrlWrapper();
		initialize();
	}
	
	public UI(TrackController own) {
		this.owner = own;
		initialize();
	}
	
	public void setvisible(boolean tf) {
		if (tf) {
			frame.setVisible(true);
		}
		else {
			frame.setVisible(false);
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		
		Object[][] trainData = {{}};
		String[] trainColumnNames = new String[] {"Train Number", "Position"};
		Object[][] switchData = {{}};
		String[] switchColumnNames = {"Switch Root", "Pointing to"};
		
		train_table = new JTable();
		trainModel = new DefaultTableModel(0,0);
		trainModel.setColumnIdentifiers(trainColumnNames);
		train_table.setModel(trainModel);
		train_table.setBounds(6, 23, 204, 143);
		panel.add(train_table);
		
		switch_table = new JTable(switchData, switchColumnNames);
		switchModel = new DefaultTableModel(0,0);
		switchModel.setColumnIdentifiers(switchColumnNames);
		switch_table.setModel(switchModel);
		switch_table.setBounds(222, 23, 222, 143);
		panel.add(switch_table);
		
		PLCName = new JTextField();
		PLCName.setBounds(6, 206, 162, 29);
		panel.add(PLCName);
		PLCName.setColumns(10);
		
		JButton loadPLCBtn = new JButton("Load PLC");
		loadPLCBtn.setBounds(164, 206, 117, 29);
		panel.add(loadPLCBtn);
		loadPLCBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String filename = PLCName.getText();
				owner.loadPLC(filename);
			}
		});
		
		
	}
	public void addTrain(int trainNum, int pos) {
		DefaultTableModel model = (DefaultTableModel) train_table.getModel();
		model.addRow(new Object[]{Integer.toString(trainNum), Integer.toString(pos)});
	}
	public void updatePosition(int trainNum, int newPos) {
		DefaultTableModel model = (DefaultTableModel) train_table.getModel();

		for (int row = 0; row < model.getRowCount(); row++) {
			int num = (Integer) model.getValueAt(row, 0);
			
			if (num == trainNum) {
				model.setValueAt(Integer.toString(newPos), row, 1);
			}
		}
	}
	
	public void addSwitch(int switchRoot, int pos) {
		DefaultTableModel model = (DefaultTableModel) train_table.getModel();
		model.addRow(new Object[]{Integer.toString(switchRoot), Integer.toString(pos)});
	}
	public void updateSwitchPoint(int switchRoot, int newPoint) {
		DefaultTableModel model = (DefaultTableModel) train_table.getModel();

		for (int row = 0; row < model.getRowCount(); row++) {
			int num = (Integer) model.getValueAt(row, 0);
			
			if (num == switchRoot) {
				model.setValueAt(Integer.toString(newPoint), row, 1);
			}
		}
	}
	
	public void updateSwitches() {
		
	}
	public void updateCrossing() {
	}
}