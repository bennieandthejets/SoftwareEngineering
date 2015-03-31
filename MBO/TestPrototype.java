package com.BennieAndTheJets.MBO;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;


public class TestPrototype {

	private JFrame frame;
	private JTextField txtVelocity;
	private JTextField txtStopDistance;
	private JTextField txtThroughput;
	private MBO prototype;
	private MBOUI ui;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		/*EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestPrototype window = new TestPrototype();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});*/
		
		TestPrototype tester = new TestPrototype();
	}

	/**
	 * Create the application.
	 */
	public TestPrototype() {
		initialize();
		frame.setVisible(true);
		MBO prototype = new MBO();
		this.prototype = prototype;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 199);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(null);
		
		txtVelocity = new JTextField();
		txtVelocity.setBounds(127, 12, 114, 19);
		frame.getContentPane().add(txtVelocity);
		txtVelocity.setColumns(10);
		
		txtStopDistance = new JTextField();
		txtStopDistance.setBounds(127, 43, 114, 19);
		frame.getContentPane().add(txtStopDistance);
		txtStopDistance.setColumns(10);
		
		JLabel lblTrainVelocity = new JLabel("Train Velocity");
		lblTrainVelocity.setBounds(12, 14, 96, 15);
		frame.getContentPane().add(lblTrainVelocity);
		
		JLabel lblStopDistance = new JLabel("Stop Distance");
		lblStopDistance.setBounds(12, 45, 114, 15);
		frame.getContentPane().add(lblStopDistance);
		
		txtThroughput = new JTextField();
		txtThroughput.setColumns(10);
		txtThroughput.setBounds(127, 74, 114, 19);
		frame.getContentPane().add(txtThroughput);
		
		JLabel lblThroughput = new JLabel("Throughput");
		lblThroughput.setBounds(12, 76, 114, 15);
		frame.getContentPane().add(lblThroughput);
		
		JButton btnCalculateStopDistance = new JButton("Calculate");
		btnCalculateStopDistance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int velocity = Integer.parseInt(txtVelocity.getText());
				double metricVel = velocity * 0.44704;
				double stopDistance = prototype.calculateStopDistance(metricVel) * 3.28084;
				txtStopDistance.setText(String.format("%1$, .2f", stopDistance));
			}
		});
		btnCalculateStopDistance.setBounds(289, 35, 129, 25);
		frame.getContentPane().add(btnCalculateStopDistance);
		
		JButton btnCreateSchedule = new JButton("Create Schedule");
		btnCreateSchedule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int throughput = Integer.parseInt(txtThroughput.getText());
				prototype.setThroughput(throughput, 0);
				prototype.createTrainSchedule(throughput);
				ui.setItems(prototype);
			}
		});
		btnCreateSchedule.setBounds(272, 71, 151, 25);
		frame.getContentPane().add(btnCreateSchedule);
	}
}
