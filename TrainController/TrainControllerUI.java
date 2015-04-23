package TrainController;


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

import java.util.*;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

import java.text.DecimalFormat;

@SuppressWarnings("unused")
public class TrainControllerUI {

	public JFrame frame;
	
	private final double	MPS_TO_MPH = 2.23694;			//Conversion ratio for meters per second to miles per hour
	private final double	METERS_TO_MILES = 0.000621371;	//Conversion ratio for meters to miles
	
	private final DecimalFormat	formatter = new DecimalFormat("0.00");
	
	private final ButtonGroup controlModes = new ButtonGroup();
	private final JSlider velocitySlider = new JSlider();
	private final JComboBox<Integer> trainSelectBox = new JComboBox<Integer>();
	private final JButton eBrakeButton = new JButton("E-Brake");
	private final JButton brakeButton = new JButton("Brake");
	private final JRadioButton manRadio = new JRadioButton("Manual");
	private final JRadioButton autoRadio = new JRadioButton("Automatic");
	
	private JTextField setpointVelocityField;
	private JTextField targetVelocityField;
	private JTextField velocityFeedbackField;
	private JTextField lightStatusField;
	private JTextField leftDoorStatusField;
	private JTextField rightDoorStatusField;
	private JTextField announcementsField;
	private JTextField notificationsField;
	private JTextField brakeStatusField;
	private JTextField eBrakeStatusField;
	private JTextField heatStatusField;
	private JTextField acStatusField;
	private JTextField authorityField;
	
	
	

	
	private TrainControllerWrapper wrapper;
	private TrainController controller = null;
	private JTextField powerCommandField;

	/**
	 * Create the application.
	 */
	public TrainControllerUI(TrainControllerWrapper newWrapper) {
		wrapper = newWrapper;
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
		frame.setTitle("Train Controller");
		frame.setBounds(100, 100, 754, 654);
		frame.getContentPane().setLayout(null);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(null);
		mainPanel.setForeground(SystemColor.windowBorder);
		mainPanel.setBackground(SystemColor.controlHighlight);
		mainPanel.setBounds(0, 0, 748, 619);
		frame.getContentPane().add(mainPanel);
		
		autoRadio.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if(autoRadio.isSelected()) {
					switchMode(1);
				}
			}
		});
		controlModes.add(autoRadio);
		autoRadio.setSelected(true);
		autoRadio.setBackground(SystemColor.controlHighlight);
		autoRadio.setBounds(391, 13, 98, 24);
		mainPanel.add(autoRadio);
		
		manRadio.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if(manRadio.isSelected()) {
					switchMode(2);
				}
			}
		});
		controlModes.add(manRadio);
		manRadio.setBackground(SystemColor.controlHighlight);
		manRadio.setBounds(493, 13, 93, 24);
		mainPanel.add(manRadio);
		velocitySlider.setEnabled(false);
		
		velocitySlider.setValue(0);
		velocitySlider.setPaintTicks(true);
		velocitySlider.setOrientation(SwingConstants.VERTICAL);
		velocitySlider.setMinorTickSpacing(100);
		velocitySlider.setMaximum(4350);
		velocitySlider.setMajorTickSpacing(1000);
		velocitySlider.setBackground(SystemColor.controlHighlight);
		velocitySlider.setBounds(266, 174, 86, 298);
		Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
		for(int i = 0; i <= 4; i++) {
			labelTable.put( Integer.valueOf(i * 1000 ), new JLabel(Integer.toString(i * 10)));
		}
		velocitySlider.setLabelTable( labelTable );
		velocitySlider.setPaintLabels(true);
		velocitySlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                setTargetVelocity(velocitySlider.getValue() / 100);
            }
        });
		mainPanel.add(velocitySlider);
		
		JLabel announcementsLbl = new JLabel("Announcements");
		announcementsLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		announcementsLbl.setBounds(158, 538, 93, 16);
		mainPanel.add(announcementsLbl);
		
		JLabel notificationsLbl = new JLabel("Notifications");
		notificationsLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		notificationsLbl.setBounds(180, 566, 71, 16);
		mainPanel.add(notificationsLbl);
		
		JButton leftDoorButton = new JButton("Left Doors");
		leftDoorButton.setBounds(524, 149, 107, 26);
		mainPanel.add(leftDoorButton);
		
		JButton rightDoorButton = new JButton("Right Doors");
		rightDoorButton.setBounds(524, 188, 107, 26);
		mainPanel.add(rightDoorButton);
		
		JButton lightsButton = new JButton("Lights");
		lightsButton.setBounds(524, 109, 106, 26);
		mainPanel.add(lightsButton);
		
		trainSelectBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == ItemEvent.SELECTED) {
			          Integer trainID = (Integer) arg0.getItem();
			          switchTrain(trainID);
			    }
			}
		});
		trainSelectBox.setBounds(221, 12, 93, 25);
		mainPanel.add(trainSelectBox);
		
		JLabel setpointVelocityLbl = new JLabel("Setpoint Velocity");
		setpointVelocityLbl.setHorizontalAlignment(SwingConstants.CENTER);
		setpointVelocityLbl.setBounds(102, 124, 98, 28);
		mainPanel.add(setpointVelocityLbl);
		
		JLabel velocityFeedbackLbl = new JLabel("Velocity Feedback");
		velocityFeedbackLbl.setHorizontalAlignment(SwingConstants.CENTER);
		velocityFeedbackLbl.setBounds(102, 187, 104, 28);
		mainPanel.add(velocityFeedbackLbl);
		
		JLabel targetVelocityLbl = new JLabel("Target Velocity");
		targetVelocityLbl.setHorizontalAlignment(SwingConstants.CENTER);
		targetVelocityLbl.setBounds(245, 124, 104, 28);
		mainPanel.add(targetVelocityLbl);
		
		JSeparator midSep = new JSeparator();
		midSep.setOrientation(SwingConstants.VERTICAL);
		midSep.setForeground(Color.BLACK);
		midSep.setBackground(SystemColor.textInactiveText);
		midSep.setBounds(372, 63, 14, 409);
		mainPanel.add(midSep);
		
		JSeparator topSep = new JSeparator();
		topSep.setForeground(Color.BLACK);
		topSep.setBackground(SystemColor.textInactiveText);
		topSep.setBounds(118, 49, 510, 2);
		mainPanel.add(topSep);
		
		JSeparator bottomSep = new JSeparator();
		bottomSep.setForeground(Color.BLACK);
		bottomSep.setBackground(SystemColor.textInactiveText);
		bottomSep.setBounds(118, 498, 510, 2);
		mainPanel.add(bottomSep);
		
		JLabel velocityControlSectionLbl = new JLabel("Velocity Control");
		velocityControlSectionLbl.setHorizontalAlignment(SwingConstants.CENTER);
		velocityControlSectionLbl.setFont(new Font("Dialog", Font.BOLD, 15));
		velocityControlSectionLbl.setBounds(159, 66, 157, 35);
		mainPanel.add(velocityControlSectionLbl);
		
		JLabel subsystemControlSectionLbl = new JLabel("Subsystem Control");
		subsystemControlSectionLbl.setHorizontalAlignment(SwingConstants.CENTER);
		subsystemControlSectionLbl.setFont(new Font("Dialog", Font.BOLD, 15));
		subsystemControlSectionLbl.setBounds(445, 70, 157, 26);
		mainPanel.add(subsystemControlSectionLbl);
		
		JLabel trainNumberLbl = new JLabel("Train #");
		trainNumberLbl.setFont(new Font("Dialog", Font.BOLD, 13));
		trainNumberLbl.setBounds(163, 17, 55, 16);
		mainPanel.add(trainNumberLbl);
		
		brakeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				switchServiceBrakeStatus();
			}
		});
		brakeButton.setBounds(158, 407, 83, 26);
		mainPanel.add(brakeButton);
		
		eBrakeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchEmergencyBrakeStatus();
			}
		});
		eBrakeButton.setBounds(158, 446, 83, 26);
		mainPanel.add(eBrakeButton);
		
		JSeparator rightSep = new JSeparator();
		rightSep.setForeground(Color.BLACK);
		rightSep.setBackground(SystemColor.textInactiveText);
		rightSep.setBounds(391, 301, 242, 2);
		mainPanel.add(rightSep);
		
		JLabel faultsSectionLbl = new JLabel("Failures");
		faultsSectionLbl.setHorizontalAlignment(SwingConstants.CENTER);
		faultsSectionLbl.setFont(new Font("Dialog", Font.BOLD, 15));
		faultsSectionLbl.setBounds(445, 316, 157, 26);
		mainPanel.add(faultsSectionLbl);
		
		setpointVelocityField = new JTextField();
		setpointVelocityField.setEditable(false);
		setpointVelocityField.setColumns(10);
		setpointVelocityField.setBounds(102, 150, 86, 20);
		mainPanel.add(setpointVelocityField);
		
		targetVelocityField = new JTextField();
		targetVelocityField.setEditable(false);
		targetVelocityField.setColumns(10);
		targetVelocityField.setBounds(255, 149, 71, 20);
		mainPanel.add(targetVelocityField);
		
		velocityFeedbackField = new JTextField();
		velocityFeedbackField.setEditable(false);
		velocityFeedbackField.setColumns(10);
		velocityFeedbackField.setBounds(102, 212, 86, 20);
		mainPanel.add(velocityFeedbackField);
		
		lightStatusField = new JTextField();
		lightStatusField.setEditable(false);
		lightStatusField.setColumns(10);
		lightStatusField.setBounds(431, 112, 71, 20);
		mainPanel.add(lightStatusField);
		
		leftDoorStatusField = new JTextField();
		leftDoorStatusField.setEditable(false);
		leftDoorStatusField.setColumns(10);
		leftDoorStatusField.setBounds(431, 150, 71, 20);
		mainPanel.add(leftDoorStatusField);
		
		rightDoorStatusField = new JTextField();
		rightDoorStatusField.setEditable(false);
		rightDoorStatusField.setColumns(10);
		rightDoorStatusField.setBounds(431, 191, 71, 20);
		mainPanel.add(rightDoorStatusField);
		
		announcementsField = new JTextField();
		announcementsField.setColumns(10);
		announcementsField.setBounds(272, 536, 296, 20);
		mainPanel.add(announcementsField);
		
		notificationsField = new JTextField();
		notificationsField.setColumns(10);
		notificationsField.setBounds(272, 564, 296, 20);
		mainPanel.add(notificationsField);
		
		brakeStatusField = new JTextField();
		brakeStatusField.setEditable(false);
		brakeStatusField.setBounds(102, 409, 43, 22);
		mainPanel.add(brakeStatusField);
		brakeStatusField.setColumns(10);
		
		eBrakeStatusField = new JTextField();
		eBrakeStatusField.setEditable(false);
		eBrakeStatusField.setColumns(10);
		eBrakeStatusField.setBounds(102, 448, 43, 22);
		mainPanel.add(eBrakeStatusField);
		
		JButton heatButton = new JButton("Heat");
		heatButton.setBounds(524, 224, 106, 26);
		mainPanel.add(heatButton);
		
		JButton acButton = new JButton("AC");
		acButton.setBounds(524, 262, 106, 26);
		mainPanel.add(acButton);
		
		heatStatusField = new JTextField();
		heatStatusField.setEditable(false);
		heatStatusField.setColumns(10);
		heatStatusField.setBounds(431, 226, 71, 20);
		mainPanel.add(heatStatusField);
		
		acStatusField = new JTextField();
		acStatusField.setEditable(false);
		acStatusField.setColumns(10);
		acStatusField.setBounds(431, 264, 71, 20);
		mainPanel.add(acStatusField);
		
		authorityField = new JTextField();
		authorityField.setEditable(false);
		authorityField.setColumns(10);
		authorityField.setBounds(102, 275, 86, 20);
		mainPanel.add(authorityField);
		
		JLabel authorityLbl = new JLabel("Authority");
		authorityLbl.setHorizontalAlignment(SwingConstants.CENTER);
		authorityLbl.setBounds(102, 246, 57, 28);
		mainPanel.add(authorityLbl);
		
		JLabel lblMph = new JLabel("mph");
		lblMph.setBounds(330, 154, 36, 16);
		mainPanel.add(lblMph);
		
		JLabel lblMph_1 = new JLabel("mph");
		lblMph_1.setBounds(192, 154, 36, 16);
		mainPanel.add(lblMph_1);
		
		JLabel label = new JLabel("mph");
		label.setBounds(192, 214, 36, 16);
		mainPanel.add(label);
		
		JLabel lblMiles = new JLabel("miles");
		lblMiles.setBounds(192, 277, 36, 16);
		mainPanel.add(lblMiles);
		
		powerCommandField = new JTextField();
		powerCommandField.setEditable(false);
		powerCommandField.setBounds(102, 331, 86, 22);
		mainPanel.add(powerCommandField);
		powerCommandField.setColumns(10);
		
		JLabel lblPowerCommand = new JLabel("Power Command");
		lblPowerCommand.setBounds(105, 309, 106, 16);
		mainPanel.add(lblPowerCommand);
		
		JLabel lblW = new JLabel("W");
		lblW.setBounds(193, 334, 56, 16);
		mainPanel.add(lblW);
	}
	
	public void addTrain(int trainID) {
		trainSelectBox.addItem(new Integer(trainID));
	}
	
	public void setTargetVelocity(double newVelocity) {
		
		//prevent driver from going above setpoint
		if((controller.getSetpointVelocity() * MPS_TO_MPH) < newVelocity) {
			newVelocity = controller.getSetpointVelocity() * MPS_TO_MPH;
		}
		
		targetVelocityField.setText(formatter.format(newVelocity));
		
		controller.setTargetVelocity(newVelocity / MPS_TO_MPH);
	}
	
	public void switchServiceBrakeStatus() {
		if(brakeStatusField.getText().equals("OFF")) {
			controller.stopTrain(true);
			brakeStatusField.setText("ON");
		}
		else {
			controller.releaseServiceBrakes(true);
			brakeStatusField.setText("OFF");
		}
	}
	
	public void switchEmergencyBrakeStatus() {
		if(eBrakeStatusField.getText().equals("OFF")) {
			controller.emergencyStop(true);
			eBrakeStatusField.setText("ON");
		}
		else {
			controller.releaseEmergencyBrakes(true);
			eBrakeStatusField.setText("OFF");
		}
	}
	
	private void updateSetpointVelocity(double newSetpointVelocity) {
		setpointVelocityField.setText(formatter.format(newSetpointVelocity));
	}
	
	private void updateVelocityFeedback(double newVelocityFeedback) {
		velocityFeedbackField.setText(formatter.format(newVelocityFeedback));
	}
	
	private void updateAuthority(double newAuthority) {
		authorityField.setText(formatter.format(newAuthority));
	}
	
	private void updatePowerCommand(double newPowerCommand) {
		powerCommandField.setText(formatter.format(newPowerCommand));
	}
	
	public void update() {
		if(controller != null) {
			updateSetpointVelocity(controller.getSetpointVelocity() * MPS_TO_MPH);
			updateVelocityFeedback(controller.getVelocityFeedback() * MPS_TO_MPH);
			updateAuthority(controller.getAuthority() * METERS_TO_MILES);
			updatePowerCommand(controller.getPowerCommand());
			checkBrakes();
			checkDoors();
			checkLights();
		}
	}
	
	public void checkBrakes() {
		if(controller.brakeStatus) {
			brakeStatusField.setText("ON");
		}
		else {
			brakeStatusField.setText("OFF");
		}
		
		if(controller.eBrakeStatus) {
			eBrakeStatusField.setText("ON");
		}
		else {
			eBrakeStatusField.setText("OFF");
		}
	}
	
	public void checkDoors() {
		if(controller.leftDoorStatus) {
			leftDoorStatusField.setText("OPEN");
		}
		else {
			leftDoorStatusField.setText("CLOSED");
		}
		
		if(controller.rightDoorStatus) {
			rightDoorStatusField.setText("OPEN");
		}
		else {
			rightDoorStatusField.setText("CLOSED");
		}
	}
	
	public void checkLights() {
		if(controller.lightStatus) {
			lightStatusField.setText("ON");
		}
		else {
			lightStatusField.setText("OFF");
		}
	}
	
	public void switchTrain(int trainID) {
		controller = wrapper.getTrainController(trainID);
		update();
	}
	
	public void switchMode(int modeID) {
		if(controller != null) {
			controller.setMode(modeID);
		}
		
		//auto
		if(modeID == 1) {
			velocitySlider.setEnabled(false);
			brakeButton.setEnabled(false);
		}
		else {
			velocitySlider.setEnabled(true);
			brakeButton.setEnabled(true);
		}
	}
}
