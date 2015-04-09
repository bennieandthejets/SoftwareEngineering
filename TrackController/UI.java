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


public class UI {

	private JFrame frame;
	private TrackCtrlWrapper owner;
	private JLabel lblNewLabel;
	private JTable train_table;
	private JTable switch_table;

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
	
	public UI(TrackCtrlWrapper own) {
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
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		
		
		train_table = new JTable(0, 2);
		train_table.setBounds(6, 23, 204, 143);
		train_table.getColumnModel().getColumn(0).setHeaderValue("Train Number");
		train_table.getColumnModel().getColumn(1).setHeaderValue("Position");
		panel.add(train_table);
		
		switch_table = new JTable(0, 2);
		switch_table.setBounds(222, 23, 222, 143);
		train_table.getColumnModel().getColumn(0).setHeaderValue("Switch Root");
		train_table.getColumnModel().getColumn(1).setHeaderValue("Pointing To");
		panel.add(switch_table);
		
		
	}
	public void updatePosition(int newPos) {
	}
	public void updateSwitches() {
		
	}
	public void updateCrossing() {
	}
}