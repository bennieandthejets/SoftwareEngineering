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

public class TrainControllerUI {

	public JFrame frame;
	private final ButtonGroup controlModes = new ButtonGroup();
	private TrainController trainController;
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

	/**
	 * Create the application.
	 */
	public TrainControllerUI() {
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
		frame.setBounds(100, 100, 754, 654);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setForeground(SystemColor.windowBorder);
		panel.setBackground(SystemColor.controlHighlight);
		panel.setBounds(0, 0, 748, 619);
		frame.getContentPane().add(panel);
		
		JRadioButton autoRadio = new JRadioButton("Automatic");
		autoRadio.setSelected(true);
		autoRadio.setBackground(SystemColor.controlHighlight);
		autoRadio.setBounds(391, 13, 98, 24);
		panel.add(autoRadio);
		
		JRadioButton manRadio = new JRadioButton("Manual");
		manRadio.setBackground(SystemColor.controlHighlight);
		manRadio.setBounds(493, 13, 93, 24);
		panel.add(manRadio);
		
		JSlider velocitySlider = new JSlider();
		velocitySlider.setValue(35);
		velocitySlider.setPaintTicks(true);
		velocitySlider.setPaintLabels(true);
		velocitySlider.setOrientation(SwingConstants.VERTICAL);
		velocitySlider.setMinorTickSpacing(1);
		velocitySlider.setMaximum(70);
		velocitySlider.setMajorTickSpacing(10);
		velocitySlider.setEnabled(false);
		velocitySlider.setBackground(SystemColor.controlHighlight);
		velocitySlider.setBounds(266, 174, 86, 298);
		panel.add(velocitySlider);
		
		JLabel announcementsLbl = new JLabel("Announcements");
		announcementsLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		announcementsLbl.setBounds(158, 538, 93, 16);
		panel.add(announcementsLbl);
		
		JLabel notificationsLbl = new JLabel("Notifications");
		notificationsLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		notificationsLbl.setBounds(180, 566, 71, 16);
		panel.add(notificationsLbl);
		
		JButton leftDoorButton = new JButton("Left Doors");
		leftDoorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		leftDoorButton.setBounds(524, 149, 104, 26);
		panel.add(leftDoorButton);
		
		JButton rightDoorButton = new JButton("Right Doors");
		rightDoorButton.setBounds(524, 188, 104, 26);
		panel.add(rightDoorButton);
		
		JButton lightsButton = new JButton("Lights");
		lightsButton.setBounds(524, 109, 104, 26);
		panel.add(lightsButton);
		
		JComboBox trainSelectBox = new JComboBox();
		trainSelectBox.setBounds(221, 12, 114, 25);
		panel.add(trainSelectBox);
		
		JLabel setpointVelocityLbl = new JLabel("Setpoint Velocity");
		setpointVelocityLbl.setHorizontalAlignment(SwingConstants.CENTER);
		setpointVelocityLbl.setBounds(102, 124, 104, 28);
		panel.add(setpointVelocityLbl);
		
		JLabel velocityFeedbackLbl = new JLabel("Velocity Feedback");
		velocityFeedbackLbl.setHorizontalAlignment(SwingConstants.CENTER);
		velocityFeedbackLbl.setBounds(102, 187, 104, 28);
		panel.add(velocityFeedbackLbl);
		
		JLabel targetVelocityLbl = new JLabel("Target Velocity");
		targetVelocityLbl.setHorizontalAlignment(SwingConstants.CENTER);
		targetVelocityLbl.setBounds(248, 124, 104, 28);
		panel.add(targetVelocityLbl);
		
		JSeparator midSep = new JSeparator();
		midSep.setOrientation(SwingConstants.VERTICAL);
		midSep.setForeground(Color.BLACK);
		midSep.setBackground(SystemColor.textInactiveText);
		midSep.setBounds(372, 63, 14, 409);
		panel.add(midSep);
		
		JSeparator topSep = new JSeparator();
		topSep.setForeground(Color.BLACK);
		topSep.setBackground(SystemColor.textInactiveText);
		topSep.setBounds(118, 49, 510, 2);
		panel.add(topSep);
		
		JSeparator bottomSep = new JSeparator();
		bottomSep.setForeground(Color.BLACK);
		bottomSep.setBackground(SystemColor.textInactiveText);
		bottomSep.setBounds(118, 498, 510, 2);
		panel.add(bottomSep);
		
		JLabel velocityControlSectionLbl = new JLabel("Velocity Control");
		velocityControlSectionLbl.setHorizontalAlignment(SwingConstants.CENTER);
		velocityControlSectionLbl.setFont(new Font("Dialog", Font.BOLD, 15));
		velocityControlSectionLbl.setBounds(159, 66, 157, 35);
		panel.add(velocityControlSectionLbl);
		
		JLabel subsystemControlSectionLbl = new JLabel("Subsystem Control");
		subsystemControlSectionLbl.setHorizontalAlignment(SwingConstants.CENTER);
		subsystemControlSectionLbl.setFont(new Font("Dialog", Font.BOLD, 15));
		subsystemControlSectionLbl.setBounds(445, 70, 157, 26);
		panel.add(subsystemControlSectionLbl);
		
		JLabel trainNumberLbl = new JLabel("Train #");
		trainNumberLbl.setFont(new Font("Dialog", Font.BOLD, 13));
		trainNumberLbl.setBounds(163, 17, 55, 16);
		panel.add(trainNumberLbl);
		
		JButton brakeButton = new JButton("Brake");
		brakeButton.setBounds(171, 407, 83, 26);
		panel.add(brakeButton);
		
		JButton eBrakeButton = new JButton("E-Brake");
		eBrakeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		eBrakeButton.setBounds(171, 446, 83, 26);
		panel.add(eBrakeButton);
		
		JSeparator rightSep = new JSeparator();
		rightSep.setForeground(Color.BLACK);
		rightSep.setBackground(SystemColor.textInactiveText);
		rightSep.setBounds(391, 301, 242, 2);
		panel.add(rightSep);
		
		JLabel faultsSectionLbl = new JLabel("Faults");
		faultsSectionLbl.setHorizontalAlignment(SwingConstants.CENTER);
		faultsSectionLbl.setFont(new Font("Dialog", Font.BOLD, 15));
		faultsSectionLbl.setBounds(445, 316, 157, 26);
		panel.add(faultsSectionLbl);
		
		setpointVelocityField = new JTextField();
		setpointVelocityField.setEditable(false);
		setpointVelocityField.setColumns(10);
		setpointVelocityField.setBounds(102, 150, 114, 20);
		panel.add(setpointVelocityField);
		
		targetVelocityField = new JTextField();
		targetVelocityField.setEditable(false);
		targetVelocityField.setColumns(10);
		targetVelocityField.setBounds(245, 149, 114, 20);
		panel.add(targetVelocityField);
		
		velocityFeedbackField = new JTextField();
		velocityFeedbackField.setEditable(false);
		velocityFeedbackField.setColumns(10);
		velocityFeedbackField.setBounds(102, 212, 114, 20);
		panel.add(velocityFeedbackField);
		
		lightStatusField = new JTextField();
		lightStatusField.setEditable(false);
		lightStatusField.setColumns(10);
		lightStatusField.setBounds(431, 112, 71, 20);
		panel.add(lightStatusField);
		
		leftDoorStatusField = new JTextField();
		leftDoorStatusField.setEditable(false);
		leftDoorStatusField.setColumns(10);
		leftDoorStatusField.setBounds(431, 150, 71, 20);
		panel.add(leftDoorStatusField);
		
		rightDoorStatusField = new JTextField();
		rightDoorStatusField.setEditable(false);
		rightDoorStatusField.setColumns(10);
		rightDoorStatusField.setBounds(431, 191, 71, 20);
		panel.add(rightDoorStatusField);
		
		announcementsField = new JTextField();
		announcementsField.setColumns(10);
		announcementsField.setBounds(272, 536, 296, 20);
		panel.add(announcementsField);
		
		notificationsField = new JTextField();
		notificationsField.setColumns(10);
		notificationsField.setBounds(272, 564, 296, 20);
		panel.add(notificationsField);
		
		brakeStatusField = new JTextField();
		brakeStatusField.setEditable(false);
		brakeStatusField.setBounds(118, 409, 43, 22);
		panel.add(brakeStatusField);
		brakeStatusField.setColumns(10);
		
		eBrakeStatusField = new JTextField();
		eBrakeStatusField.setEditable(false);
		eBrakeStatusField.setColumns(10);
		eBrakeStatusField.setBounds(118, 448, 43, 22);
		panel.add(eBrakeStatusField);
		
		JButton heatButton = new JButton("Heat");
		heatButton.setBounds(524, 224, 104, 26);
		panel.add(heatButton);
		
		JButton acButton = new JButton("AC");
		acButton.setBounds(524, 262, 104, 26);
		panel.add(acButton);
		
		heatStatusField = new JTextField();
		heatStatusField.setEditable(false);
		heatStatusField.setColumns(10);
		heatStatusField.setBounds(431, 226, 71, 20);
		panel.add(heatStatusField);
		
		acStatusField = new JTextField();
		acStatusField.setEditable(false);
		acStatusField.setColumns(10);
		acStatusField.setBounds(431, 264, 71, 20);
		panel.add(acStatusField);
		
		authorityField = new JTextField();
		authorityField.setEditable(false);
		authorityField.setColumns(10);
		authorityField.setBounds(102, 275, 114, 20);
		panel.add(authorityField);
		
		JLabel authorityLbl = new JLabel("Authority");
		authorityLbl.setHorizontalAlignment(SwingConstants.CENTER);
		authorityLbl.setBounds(102, 245, 104, 28);
		panel.add(authorityLbl);
	}
}
