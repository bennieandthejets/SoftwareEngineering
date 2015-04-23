package MBO;

import TrainModel.Antenna;

import java.awt.EventQueue;
import java.awt.FileDialog;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;

import java.awt.Color;

import javax.swing.JTextArea;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.SystemColor;

public class MBOUI {

	private JFrame frame;
	private JTextField txtTime;
	private JTextField txtTrainLocation;
	private JTextField txtSafeAuthority;
	private JTextField txtVelocity;
	private JTextField txtRequiredThroughput;
	private JTextField txtStopsThisHour;
	private JTextField txtStopsLastHour;
	private JTextField txtShiftTime;
	private JTextField txtBreakTime;
	private JTextField txtBreakLocation;
	private JTextField txtShiftEnd;
	private JTextField txtFixed;
	private JTextField txtMoving;
	private JTextField txtStatus;
	private JTextField txtStopDistance;
	private JTextArea txtrStops;
	private JComboBox<String> trainSelectBox;
	private MBO mbo;
	
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    private int selectedTrain;
    private JTextField txtSafeVelocity;
    private JTextField txtAuthority;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//MBOUI window = new MBOUI();
					//window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MBOUI(MBO mbo) {
		this.mbo = mbo;
		initialize();
		//frame.setVisible(true);
	}
	
	/// Called when the user selects the train whose info they'd like to see displayed
	public void selectTrain() {
		if(trainSelectBox.getItemCount() > 0) {
			String value = trainSelectBox.getSelectedItem().toString();
			this.selectedTrain = Integer.parseInt(value.substring(value.length() - 1));
			//this.setItems();
		}
	}
	
	/// Called after a train has been added to the system
	public void setTrainSelectBox() {
		trainSelectBox.removeAllItems();
		for(Antenna reggie : mbo.reggies) {
			trainSelectBox.addItem("Train " + (reggie.getTrainID()));
		}
	}
	
	public void setItems() {
		this.txtRequiredThroughput.setText("" + mbo.throughput);
		
		displayTime();
		int actualThroughput = mbo.actualThroughput;
		if(actualThroughput > 0) {
			this.txtStopsLastHour.setText("" + actualThroughput);
		}
		else {
			this.txtStopsLastHour.setText("N/A");
		}
		
		if(mbo.reggies.size() > 0) {
			double currentVelocity = mbo.currentVelocities.get(selectedTrain);
			TrackModel.Block currentBlock = mbo.currentLocations.get(selectedTrain);
			if(currentBlock != null) {
				this.txtTrainLocation.setText("Block " + currentBlock.getBlockID());
			}
			this.txtAuthority.setText("" + mbo.currentAuthorities.get(selectedTrain));
			this.txtVelocity.setText("" + currentVelocity);
			this.txtMoving.setText("" + mbo.movingBlockAuthorities.get(selectedTrain));
			this.txtSafeVelocity.setText("" + mbo.safeVelocities.get(selectedTrain));
			this.txtSafeAuthority.setText("" + mbo.movingBlockAuthorities.get(selectedTrain));
			double stopDistance = mbo.calculateStopDistance(currentVelocity);
			this.txtStopDistance.setText("" + stopDistance);
		}
		//this.txtrStops.setText(mbo.trainSchedules.get(1).toString());
	}
	
	public void displayTime() {
        Date date = new Date(mbo.systemTime);
        txtTime.setText(timeFormat.format(date));
	}
	
	public void setVisible(boolean visible) {
		frame.setVisible(visible);
	}
	
	public void showFileDialog() {
		FileDialog fd = new FileDialog(this.frame, "Load Train Schedule", FileDialog.LOAD);
		fd.setDirectory(System.getProperty("user.dir"));
		fd.setFile("*.txt");
		fd.setVisible(true);
		String filePath = fd.getDirectory() + fd.getFile();
		if (filePath != null) {
			if(!this.txtRequiredThroughput.getText().equals("")) {
				int requiredThroughput = Integer.parseInt(txtRequiredThroughput.getText());
				mbo.setThroughput(requiredThroughput, 8);
				mbo.loadTrainSchedule(filePath);
			}
			else {
				this.txtStatus.setText("Please enter a throughput before loading a schedule.");
			}
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("MBO");
		frame.setResizable(false);
		frame.setBounds(100, 100, 585, 465);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		trainSelectBox = new JComboBox();
		trainSelectBox.setBounds(12, 12, 125, 24);
		trainSelectBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectTrain();
			}
		});
		frame.getContentPane().add(trainSelectBox);
		
		txtTime = new JTextField();
		txtTime.setEditable(false);
		txtTime.setText("09:00");
		txtTime.setBounds(180, 12, 85, 24);
		frame.getContentPane().add(txtTime);
		txtTime.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Block Location");
		lblNewLabel.setBounds(12, 50, 125, 24);
		frame.getContentPane().add(lblNewLabel);
		
		txtTrainLocation = new JTextField();
		txtTrainLocation.setEditable(false);
		txtTrainLocation.setColumns(10);
		txtTrainLocation.setBounds(180, 53, 85, 24);
		frame.getContentPane().add(txtTrainLocation);
		
		JLabel lblMovingBlockAuthority = new JLabel("Moving Block Authority");
		lblMovingBlockAuthority.setBounds(12, 120, 161, 24);
		frame.getContentPane().add(lblMovingBlockAuthority);
		
		txtSafeAuthority = new JTextField();
		txtSafeAuthority.setEditable(false);
		txtSafeAuthority.setColumns(10);
		txtSafeAuthority.setBounds(180, 120, 85, 24);
		frame.getContentPane().add(txtSafeAuthority);
		
		JLabel lblSuggestedSpeed = new JLabel("Current Velocity");
		lblSuggestedSpeed.setBounds(12, 156, 161, 24);
		frame.getContentPane().add(lblSuggestedSpeed);
		
		txtVelocity = new JTextField();
		txtVelocity.setEditable(false);
		txtVelocity.setColumns(10);
		txtVelocity.setBounds(180, 156, 85, 24);
		frame.getContentPane().add(txtVelocity);
		
		JLabel lblRequiredThroughput = new JLabel("Required Throughput");
		lblRequiredThroughput.setBounds(12, 288, 161, 24);
		frame.getContentPane().add(lblRequiredThroughput);
		
		txtRequiredThroughput = new JTextField();
		txtRequiredThroughput.setColumns(10);
		txtRequiredThroughput.setBounds(180, 291, 85, 24);
		frame.getContentPane().add(txtRequiredThroughput);
		
		JLabel lblStopsThisHour = new JLabel("Stops This Hour");
		lblStopsThisHour.setBounds(12, 324, 161, 24);
		frame.getContentPane().add(lblStopsThisHour);
		
		txtStopsThisHour = new JTextField();
		txtStopsThisHour.setEditable(false);
		txtStopsThisHour.setColumns(10);
		txtStopsThisHour.setBounds(180, 327, 85, 24);
		frame.getContentPane().add(txtStopsThisHour);
		
		JLabel lblStopsLastHour = new JLabel("Stops Last Hour");
		lblStopsLastHour.setBounds(12, 360, 161, 24);
		frame.getContentPane().add(lblStopsLastHour);
		
		txtStopsLastHour = new JTextField();
		txtStopsLastHour.setEditable(false);
		txtStopsLastHour.setColumns(10);
		txtStopsLastHour.setBounds(180, 363, 85, 24);
		frame.getContentPane().add(txtStopsLastHour);
		
		JSeparator separator = new JSeparator();
		separator.setBackground(Color.GRAY);
		separator.setForeground(Color.GRAY);
		separator.setBounds(12, 272, 250, 1);
		frame.getContentPane().add(separator);
		
		JLabel lblShiftTime = new JLabel("Shift Time");
		lblShiftTime.setBounds(295, 80, 95, 24);
		frame.getContentPane().add(lblShiftTime);
		
		txtShiftTime = new JTextField();
		txtShiftTime.setEditable(false);
		txtShiftTime.setColumns(10);
		txtShiftTime.setBounds(412, 81, 95, 24);
		frame.getContentPane().add(txtShiftTime);
		
		txtBreakTime = new JTextField();
		txtBreakTime.setEditable(false);
		txtBreakTime.setColumns(10);
		txtBreakTime.setBounds(412, 120, 95, 24);
		frame.getContentPane().add(txtBreakTime);
		
		JLabel lblBreakTime = new JLabel("Break Time");
		lblBreakTime.setBounds(295, 119, 95, 24);
		frame.getContentPane().add(lblBreakTime);
		
		txtBreakLocation = new JTextField();
		txtBreakLocation.setEditable(false);
		txtBreakLocation.setText("YARD");
		txtBreakLocation.setColumns(10);
		txtBreakLocation.setBounds(412, 156, 85, 24);
		frame.getContentPane().add(txtBreakLocation);
		
		JLabel lblBreakLocation = new JLabel("Break Location");
		lblBreakLocation.setBounds(295, 155, 106, 24);
		frame.getContentPane().add(lblBreakLocation);
		
		txtShiftEnd = new JTextField();
		txtShiftEnd.setEditable(false);
		txtShiftEnd.setText("YARD");
		txtShiftEnd.setColumns(10);
		txtShiftEnd.setBounds(412, 193, 85, 24);
		frame.getContentPane().add(txtShiftEnd);
		
		JLabel lblShiftEnd = new JLabel("Shift End");
		lblShiftEnd.setBounds(295, 192, 95, 24);
		frame.getContentPane().add(lblShiftEnd);
		
		JLabel lblOperationMode = new JLabel("Operation Mode");
		lblOperationMode.setBounds(295, 12, 125, 24);
		frame.getContentPane().add(lblOperationMode);
		
		txtrStops = new JTextArea();
		txtrStops.setEditable(false);
		txtrStops.setBounds(295, 226, 269, 63);
		//frame.getContentPane().add(txtrStops);
		
		JScrollPane scroll = new JScrollPane(txtrStops);		
		scroll.setBounds(295, 226, 269, 63);
		frame.getContentPane().add(scroll);
		
		txtFixed = new JTextField();
		txtFixed.setBackground(Color.CYAN);
		txtFixed.setEditable(false);
		txtFixed.setText("FIXED");
		txtFixed.setBounds(430, 8, 67, 34);
		frame.getContentPane().add(txtFixed);
		txtFixed.setColumns(10);
		
		txtMoving = new JTextField();
		txtMoving.setEditable(false);
		txtMoving.setBackground(SystemColor.menu);
		txtMoving.setText("MOVING");
		txtMoving.setColumns(10);
		txtMoving.setBounds(498, 8, 67, 34);
		frame.getContentPane().add(txtMoving);
		
		JLabel lblCrewInformation = new JLabel("Crew Information");
		lblCrewInformation.setBounds(362, 48, 135, 24);
		frame.getContentPane().add(lblCrewInformation);
		
		txtStatus = new JTextField();
		txtStatus.setEditable(false);
		txtStatus.setBounds(295, 301, 269, 24);
		frame.getContentPane().add(txtStatus);
		txtStatus.setColumns(10);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(Color.GRAY);
		separator_1.setOrientation(SwingConstants.VERTICAL);
		separator_1.setBackground(Color.GRAY);
		separator_1.setBounds(277, 12, 1, 318);
		frame.getContentPane().add(separator_1);
		
		JLabel lblStopDistance = new JLabel("Stop Distance");
		lblStopDistance.setBounds(12, 224, 161, 24);
		frame.getContentPane().add(lblStopDistance);
		
		txtStopDistance = new JTextField();
		txtStopDistance.setEditable(false);
		txtStopDistance.setColumns(10);
		txtStopDistance.setBounds(180, 227, 85, 24);
		frame.getContentPane().add(txtStopDistance);
		
		JButton btnLoadSchedule = new JButton("Load Schedule");
		btnLoadSchedule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showFileDialog();
			}
		});
		btnLoadSchedule.setBounds(70, 396, 125, 30);
		frame.getContentPane().add(btnLoadSchedule);
		
		JLabel lblSafeVelocity = new JLabel("Safe Velocity");
		lblSafeVelocity.setBounds(12, 191, 161, 24);
		frame.getContentPane().add(lblSafeVelocity);
		
		txtSafeVelocity = new JTextField();
		txtSafeVelocity.setEditable(false);
		txtSafeVelocity.setColumns(10);
		txtSafeVelocity.setBounds(180, 194, 85, 24);
		frame.getContentPane().add(txtSafeVelocity);
		
		JLabel lblAuthority = new JLabel("Destination Authority");
		lblAuthority.setBounds(12, 85, 161, 24);
		frame.getContentPane().add(lblAuthority);
		
		txtAuthority = new JTextField();
		txtAuthority.setEditable(false);
		txtAuthority.setColumns(10);
		txtAuthority.setBounds(180, 85, 85, 24);
		frame.getContentPane().add(txtAuthority);
		frame.setLocationRelativeTo(null);
	}
}
