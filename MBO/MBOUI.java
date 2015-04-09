package MBO;

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


public class MBOUI {

	private JFrame frame;
	private JTextField txtTime;
	private JTextField txtTrainLocation;
	private JTextField txtAuthority;
	private JTextField txtSpeed;
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
	private MBO mbo;
	
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MBOUI window = new MBOUI();
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
	public MBOUI() {
		initialize();
		//frame.setVisible(true);
	}
	
	public void setItems(MBO mbo) {
		this.mbo = mbo;
		this.txtRequiredThroughput.setText("" + mbo.getThroughput());
		//this.txtStopsThisHour.setText("" + mbo.getStopsThisHour());
		//this.txtAuthority.setText("" + mbo.get)
		displayTime();
		//this.txtrStops.setText(mbo.trainSchedules.get(1).toString());
	}
	
	public void displayTime() {
        Date date = new Date(mbo.getTime());
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
			mbo.loadTrainSchedule(filePath);
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("MBO");
		frame.setResizable(false);
		frame.setBounds(100, 100, 599, 410);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JComboBox trainSelectBox = new JComboBox();
		trainSelectBox.setBounds(12, 12, 125, 24);
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
		lblMovingBlockAuthority.setBounds(12, 89, 161, 24);
		frame.getContentPane().add(lblMovingBlockAuthority);
		
		txtAuthority = new JTextField();
		txtAuthority.setEditable(false);
		txtAuthority.setColumns(10);
		txtAuthority.setBounds(180, 89, 85, 24);
		frame.getContentPane().add(txtAuthority);
		
		JLabel lblSuggestedSpeed = new JLabel("Suggested Speed");
		lblSuggestedSpeed.setBounds(12, 125, 161, 24);
		frame.getContentPane().add(lblSuggestedSpeed);
		
		txtSpeed = new JTextField();
		txtSpeed.setEditable(false);
		txtSpeed.setColumns(10);
		txtSpeed.setBounds(180, 128, 85, 24);
		frame.getContentPane().add(txtSpeed);
		
		JLabel lblRequiredThroughput = new JLabel("Required Throughput");
		lblRequiredThroughput.setBounds(12, 226, 161, 24);
		frame.getContentPane().add(lblRequiredThroughput);
		
		txtRequiredThroughput = new JTextField();
		txtRequiredThroughput.setEditable(false);
		txtRequiredThroughput.setColumns(10);
		txtRequiredThroughput.setBounds(180, 229, 85, 24);
		frame.getContentPane().add(txtRequiredThroughput);
		
		JLabel lblStopsThisHour = new JLabel("Stops This Hour");
		lblStopsThisHour.setBounds(12, 262, 161, 24);
		frame.getContentPane().add(lblStopsThisHour);
		
		txtStopsThisHour = new JTextField();
		txtStopsThisHour.setEditable(false);
		txtStopsThisHour.setColumns(10);
		txtStopsThisHour.setBounds(180, 265, 85, 24);
		frame.getContentPane().add(txtStopsThisHour);
		
		JLabel lblStopsLastHour = new JLabel("Stops Last Hour");
		lblStopsLastHour.setBounds(12, 298, 161, 24);
		frame.getContentPane().add(lblStopsLastHour);
		
		txtStopsLastHour = new JTextField();
		txtStopsLastHour.setEditable(false);
		txtStopsLastHour.setColumns(10);
		txtStopsLastHour.setBounds(180, 301, 85, 24);
		frame.getContentPane().add(txtStopsLastHour);
		
		JSeparator separator = new JSeparator();
		separator.setBackground(Color.GRAY);
		separator.setForeground(Color.GRAY);
		separator.setBounds(12, 210, 250, 1);
		frame.getContentPane().add(separator);
		
		JLabel lblShiftTime = new JLabel("Shift Time");
		lblShiftTime.setBounds(295, 80, 95, 24);
		frame.getContentPane().add(lblShiftTime);
		
		txtShiftTime = new JTextField();
		txtShiftTime.setEditable(false);
		txtShiftTime.setText("08:00 - 16:30");
		txtShiftTime.setColumns(10);
		txtShiftTime.setBounds(412, 81, 95, 24);
		frame.getContentPane().add(txtShiftTime);
		
		txtBreakTime = new JTextField();
		txtBreakTime.setEditable(false);
		txtBreakTime.setText("5");
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
		txtFixed.setEditable(false);
		txtFixed.setText("FIXED");
		txtFixed.setBounds(430, 8, 67, 34);
		frame.getContentPane().add(txtFixed);
		txtFixed.setColumns(10);
		
		txtMoving = new JTextField();
		txtMoving.setEditable(false);
		txtMoving.setBackground(Color.CYAN);
		txtMoving.setText("MOVING");
		txtMoving.setColumns(10);
		txtMoving.setBounds(498, 8, 67, 34);
		frame.getContentPane().add(txtMoving);
		
		JLabel lblCrewInformation = new JLabel("Crew Information");
		lblCrewInformation.setBounds(362, 48, 135, 24);
		frame.getContentPane().add(lblCrewInformation);
		
		txtStatus = new JTextField();
		txtStatus.setEditable(false);
		txtStatus.setText("Train 1 is on break in the yard");
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
		lblStopDistance.setBounds(12, 161, 161, 24);
		frame.getContentPane().add(lblStopDistance);
		
		txtStopDistance = new JTextField();
		txtStopDistance.setEditable(false);
		txtStopDistance.setColumns(10);
		txtStopDistance.setBounds(180, 164, 85, 24);
		frame.getContentPane().add(txtStopDistance);
		
		JButton btnLoadSchedule = new JButton("Load Schedule");
		btnLoadSchedule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showFileDialog();
			}
		});
		btnLoadSchedule.setBounds(67, 341, 125, 30);
		frame.getContentPane().add(btnLoadSchedule);
		frame.setLocationRelativeTo(null);
	}
}
