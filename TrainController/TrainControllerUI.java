<<<<<<< HEAD
package TrainController;
=======
package com.BennieAndTheJets.TrainController;
>>>>>>> 6d2b9428abe1c66a9fea66ffc5d3acf5e93ea78c


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JSlider;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JRadioButton;
import java.awt.TextArea;
import javax.swing.JTextPane;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.BevelBorder;
import javax.swing.JTextField;
import java.awt.Font;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JSeparator;

public class TrainControllerUI {

	public JFrame frame;
	private final ButtonGroup controlModes = new ButtonGroup();
	private JTextField velocityFeedbackTestOutput;
	private JTextField setpointVelocityTestInput;
	private JTextField powerCommandTestOutput;
	private TrainController trainController;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;

	/**
	 * Create the application.
	 */
	public TrainControllerUI(TrainController train) {
		trainController = train;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.getContentPane().setBackground(Color.DARK_GRAY);
		frame.getContentPane().setForeground(SystemColor.info);
		frame.setBounds(100, 100, 754, 674);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 752, 639);
		frame.getContentPane().add(tabbedPane);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setForeground(SystemColor.windowBorder);
		mainPanel.setBackground(SystemColor.controlHighlight);
		tabbedPane.addTab("Main", null, mainPanel, null);
		mainPanel.setLayout(null);
		
		JRadioButton autoMode = new JRadioButton("Automatic");
		autoMode.setSelected(true);
		autoMode.setBackground(SystemColor.controlHighlight);
		autoMode.setBounds(391, 13, 83, 24);
		mainPanel.add(autoMode);
		controlModes.add(autoMode);
		
		JRadioButton manMode = new JRadioButton("Manual");
		manMode.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				
			}
		});
		manMode.setBackground(SystemColor.controlHighlight);
		manMode.setBounds(478, 13, 93, 24);
		mainPanel.add(manMode);
		controlModes.add(manMode);
		
		JSlider velocityControl = new JSlider();
		velocityControl.setEnabled(false);
		velocityControl.setPaintLabels(true);
		velocityControl.setValue(35);
		velocityControl.setMaximum(70);
		velocityControl.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider)e.getSource();
				if(!source.getValueIsAdjusting()) {
					trainController.setTargetVelocity(0); 
				}
			}
		});
		velocityControl.setBounds(258, 174, 86, 298);
		mainPanel.add(velocityControl);
		velocityControl.setMinorTickSpacing(1);
		velocityControl.setMajorTickSpacing(10);
		velocityControl.setPaintTicks(true);
		velocityControl.setBackground(SystemColor.controlHighlight);
		velocityControl.setOrientation(SwingConstants.VERTICAL);
		
		JLabel lblAnnouncements = new JLabel("Announcements");
		lblAnnouncements.setBounds(158, 538, 93, 16);
		mainPanel.add(lblAnnouncements);
		lblAnnouncements.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JLabel lblNotifications = new JLabel("Notifications");
		lblNotifications.setBounds(180, 566, 71, 16);
		mainPanel.add(lblNotifications);
		lblNotifications.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JButton btnLeftDoors = new JButton("Left Doors");
		btnLeftDoors.setBounds(536, 188, 104, 26);
		mainPanel.add(btnLeftDoors);
		
		JButton btnRightDoors = new JButton("Right Doors");
		btnRightDoors.setBounds(536, 222, 104, 26);
		mainPanel.add(btnRightDoors);
		
		JButton btnLights = new JButton("Lights");
		btnLights.setBounds(536, 150, 104, 26);
		mainPanel.add(btnLights);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(221, 12, 131, 25);
		mainPanel.add(comboBox);
		
		JLabel lblSetpointVelocity = new JLabel("Setpoint Velocity");
		lblSetpointVelocity.setHorizontalAlignment(SwingConstants.CENTER);
		lblSetpointVelocity.setBounds(118, 113, 104, 28);
		mainPanel.add(lblSetpointVelocity);
		
		JLabel lbVelocityFeedback = new JLabel("Velocity Feedback");
		lbVelocityFeedback.setHorizontalAlignment(SwingConstants.CENTER);
		lbVelocityFeedback.setBounds(118, 181, 104, 28);
		mainPanel.add(lbVelocityFeedback);
		
		JLabel lblTargetVelocity = new JLabel("Target Velocity");
		lblTargetVelocity.setHorizontalAlignment(SwingConstants.CENTER);
		lblTargetVelocity.setBounds(245, 113, 104, 28);
		mainPanel.add(lblTargetVelocity);
		
		JSeparator middleSeperator = new JSeparator();
		middleSeperator.setForeground(new Color(0, 0, 0));
		middleSeperator.setBackground(SystemColor.textInactiveText);
		middleSeperator.setOrientation(SwingConstants.VERTICAL);
		middleSeperator.setBounds(372, 63, 14, 409);
		mainPanel.add(middleSeperator);
		
		JSeparator topSeperator = new JSeparator();
		topSeperator.setForeground(Color.BLACK);
		topSeperator.setBackground(SystemColor.textInactiveText);
		topSeperator.setBounds(118, 49, 510, 2);
		mainPanel.add(topSeperator);
		
		JSeparator bottomSeperator = new JSeparator();
		bottomSeperator.setForeground(Color.BLACK);
		bottomSeperator.setBackground(SystemColor.textInactiveText);
		bottomSeperator.setBounds(118, 498, 510, 2);
		mainPanel.add(bottomSeperator);
		
		JLabel lblVelocityControl = new JLabel("Velocity Control");
		lblVelocityControl.setHorizontalAlignment(SwingConstants.CENTER);
		lblVelocityControl.setFont(new Font("Dialog", Font.BOLD, 15));
		lblVelocityControl.setBounds(159, 66, 157, 35);
		mainPanel.add(lblVelocityControl);
		
		JLabel lblSubsystemControl = new JLabel("Subsystem Control");
		lblSubsystemControl.setHorizontalAlignment(SwingConstants.CENTER);
		lblSubsystemControl.setFont(new Font("Dialog", Font.BOLD, 15));
		lblSubsystemControl.setBounds(445, 70, 157, 26);
		mainPanel.add(lblSubsystemControl);
		
		JLabel lblTrain = new JLabel("Train #");
		lblTrain.setFont(new Font("Dialog", Font.BOLD, 13));
		lblTrain.setBounds(163, 17, 55, 16);
		mainPanel.add(lblTrain);
		
		JButton btnBrake = new JButton("Brake");
		btnBrake.setBounds(118, 387, 105, 26);
		mainPanel.add(btnBrake);
		
		JButton btnEBrake = new JButton("E-Brake");
		btnEBrake.setBounds(118, 425, 104, 26);
		mainPanel.add(btnEBrake);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.BLACK);
		separator.setBackground(SystemColor.textInactiveText);
		separator.setBounds(391, 275, 242, 2);
		mainPanel.add(separator);
		
		JLabel lblFaults = new JLabel("Faults");
		lblFaults.setHorizontalAlignment(SwingConstants.CENTER);
		lblFaults.setFont(new Font("Dialog", Font.BOLD, 15));
		lblFaults.setBounds(445, 289, 157, 26);
		mainPanel.add(lblFaults);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(118, 149, 114, 20);
		mainPanel.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setEditable(false);
		textField_1.setColumns(10);
		textField_1.setBounds(245, 149, 114, 20);
		mainPanel.add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setEditable(false);
		textField_2.setColumns(10);
		textField_2.setBounds(118, 212, 114, 20);
		mainPanel.add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setEditable(false);
		textField_3.setColumns(10);
		textField_3.setBounds(410, 153, 114, 20);
		mainPanel.add(textField_3);
		
		textField_4 = new JTextField();
		textField_4.setEditable(false);
		textField_4.setColumns(10);
		textField_4.setBounds(410, 191, 114, 20);
		mainPanel.add(textField_4);
		
		textField_5 = new JTextField();
		textField_5.setEditable(false);
		textField_5.setColumns(10);
		textField_5.setBounds(410, 225, 114, 20);
		mainPanel.add(textField_5);
		
		textField_6 = new JTextField();
		textField_6.setColumns(10);
		textField_6.setBounds(272, 536, 296, 20);
		mainPanel.add(textField_6);
		
		textField_7 = new JTextField();
		textField_7.setColumns(10);
		textField_7.setBounds(272, 564, 296, 20);
		mainPanel.add(textField_7);
		
		JPanel testPanel = new JPanel();
		testPanel.setBackground(SystemColor.controlHighlight);
		tabbedPane.addTab("Test", null, testPanel, null);
		tabbedPane.setBackgroundAt(1, SystemColor.control);
		testPanel.setLayout(null);
		
		JLabel lblTest = new JLabel("TEST");
		lblTest.setHorizontalAlignment(SwingConstants.CENTER);
		lblTest.setFont(new Font("Dialog", Font.BOLD, 18));
		lblTest.setBounds(349, 45, 103, 50);
		testPanel.add(lblTest);
		
		velocityFeedbackTestOutput = new JTextField();
		velocityFeedbackTestOutput.setBackground(Color.WHITE);
		velocityFeedbackTestOutput.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				
			}
		});
		velocityFeedbackTestOutput.setBounds(349, 180, 103, 20);
		testPanel.add(velocityFeedbackTestOutput);
		velocityFeedbackTestOutput.setColumns(10);
		
		JLabel lblVelocityFeedback = new JLabel("Velocity Feedback");
		lblVelocityFeedback.setHorizontalAlignment(SwingConstants.RIGHT);
		lblVelocityFeedback.setBounds(235, 180, 103, 20);
		testPanel.add(lblVelocityFeedback);
		
		setpointVelocityTestInput = new JTextField();
		setpointVelocityTestInput.setBounds(349, 148, 103, 20);
		testPanel.add(setpointVelocityTestInput);
		setpointVelocityTestInput.setColumns(10);
		
		JLabel lblTestSetpointVelocity = new JLabel("Setpoint Velocity");
		lblTestSetpointVelocity.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTestSetpointVelocity.setBounds(235, 150, 103, 16);
		testPanel.add(lblTestSetpointVelocity);
		
		powerCommandTestOutput = new JTextField();
		powerCommandTestOutput.setBackground(SystemColor.window);
		powerCommandTestOutput.setEditable(false);
		powerCommandTestOutput.setBounds(349, 212, 103, 20);
		testPanel.add(powerCommandTestOutput);
		powerCommandTestOutput.setColumns(10);
		
		JLabel lblPowerCommand = new JLabel("Power Command");
		lblPowerCommand.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPowerCommand.setBounds(225, 206, 113, 33);
		testPanel.add(lblPowerCommand);
		
		JButton btnSubmit = new JButton("SUBMIT");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String velFdbkString = velocityFeedbackTestOutput.getText();
				double velFdbk = Double.parseDouble(velFdbkString);
				String stptVelString = setpointVelocityTestInput.getText();
				double stptVel = Double.parseDouble(stptVelString);
				
				trainController.setSetpointVelocity(stptVel);
				trainController.setVelocityFeedback(velFdbk);
				
				powerCommandTestOutput.setText(Double.toString(trainController.calculatePower()));
			}
		});
		btnSubmit.setBounds(349, 262, 103, 26);
		testPanel.add(btnSubmit);
		
		JButton btnReset = new JButton("RESET");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				velocityFeedbackTestOutput.setText(Double.toString(trainController.setVelocityFeedback(0)));
				setpointVelocityTestInput.setText(Double.toString(trainController.setSetpointVelocity(0)));
			}
		});
		btnReset.setBounds(349, 303, 103, 26);
		testPanel.add(btnReset);
	}
}
