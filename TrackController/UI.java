package com.BennieAndTheJets.TrackController;

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


public class UI {

	private JFrame frame;
	private JTextField textField;
	private TrackCtrlWrapper owner;
	
	private JRadioButton radioButton;
	private JRadioButton radioButton_1;
	private JRadioButton radioButton_2;
	private JRadioButton radioButton_3;
	private JLabel lblNewLabel;
	private JRadioButton rdbtnOn;
	private JButton btnRunStop; // = new JButton("Run");

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
		this.owner = new TrackCtrlWrapper();
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
		
		radioButton_3 = new JRadioButton(Integer.toString(owner.getSwitchHead(1,2)));
		radioButton_3.setBounds(278, 152, 115, 39);
		radioButton_3.setEnabled(false);
		panel.add(radioButton_3);
		
		radioButton_1 = new JRadioButton(Integer.toString(owner.getSwitchHead(0,2)));
		radioButton_1.setBounds(278, 96, 115, 39);
		radioButton_1.setEnabled(false);
		panel.add(radioButton_1);
		
		JLabel lblTrainPosition = new JLabel("Train Position");
		lblTrainPosition.setBounds(220, 34, 97, 16);
		panel.add(lblTrainPosition);
		
		textField = new JTextField();
		textField.setBounds(329, 28, 115, 28);
		panel.add(textField);
		textField.setColumns(10);
		
		//JButton 
		btnRunStop = new JButton("Run");
		btnRunStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				owner.startStop();
				
				if (btnRunStop.getText().equals("Run")) {
					btnRunStop.setText("Stop");
				}
				else {
					btnRunStop.setText("Run");
				}
				
			}
		});
		btnRunStop.setBounds(329, 55, 117, 29);
		panel.add(btnRunStop);
		
		JLabel lblSwitch = new JLabel("Switch at " + Integer.toString(owner.getSwitch(0)));
		lblSwitch.setBounds(220, 84, 124, 16);
		panel.add(lblSwitch);
		
		radioButton = new JRadioButton(Integer.toString(owner.getSwitchHead(0,1)));
		radioButton.setBounds(220, 96, 97, 39);
		radioButton.setEnabled(false);
		panel.add(radioButton);
		
		if (owner.getSwitchState(0) == 0)
			radioButton.setSelected(true);
		else
			radioButton_1.setSelected(true);
		
		JLabel lblSwitch_1 = new JLabel("Switch at " + Integer.toString(owner.getSwitch(1)));
		lblSwitch_1.setBounds(220, 136, 124, 16);
		panel.add(lblSwitch_1);
		
		radioButton_2 = new JRadioButton(Integer.toString(owner.getSwitchHead(1,1)));
		radioButton_2.setBounds(220, 152, 97, 39);
		radioButton_2.setEnabled(false);
		panel.add(radioButton_2);
		
		lblNewLabel = new JLabel("Crossing at " + Integer.toString(owner.getCrossing()));
		lblNewLabel.setBounds(220, 203, 124, 16);
		panel.add(lblNewLabel);
		
		rdbtnOn = new JRadioButton("On");
		rdbtnOn.setBounds(220, 220, 97, 39);
		rdbtnOn.setSelected(owner.getCrossingState());
		rdbtnOn.setEnabled(false);
		panel.add(rdbtnOn);
		
		if (owner.getSwitchState(1) == 0)
			radioButton_2.setSelected(true);
		else
			radioButton_3.setSelected(true);
	}
	public void updatePosition(int newPos) {
		textField.setText(Integer.toString(newPos));
	}
	public void updateSwitches() {
		//first switch
		if (owner.getSwitchState(0) == 0) {
			radioButton.setSelected(true);
			radioButton_1.setSelected(false);
		}
		else {
			radioButton_1.setSelected(true);
			radioButton.setSelected(false);
		}
		//second switch
				if (owner.getSwitchState(1) == 0) {
					radioButton_2.setSelected(true);
					radioButton_3.setSelected(false);
				}
				else {
					radioButton_3.setSelected(true);
					radioButton_2.setSelected(false);
				}
	}
	public void updateCrossing() {
		rdbtnOn.setSelected(owner.getCrossingState());
	}
	
}
