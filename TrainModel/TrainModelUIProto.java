package TrainModel;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import java.awt.Color;
import java.awt.event.*;


public class TrainModelUIProto implements ActionListener{

	private JFrame frame;
	private JTextField txtHeight;
	private JTextField txtCrewMems;
	private JTextField txtWidth;
	private JTextField txtLength;
	private JTextField txtMass;
	private JTextField txtPassengers;
	private JTextField txtNumofCars;
	private JTextField txtAccel;
	private JTextField txtVelocity;
	private JTextField txtSlope;
	private JTextField txtBrakes;
	private JTextField txtLights;
	private JTextField txtDoors;
	private JTextField txtNextStation;
	private JTextField txtArrival;
	private JTextField txtCurrBlock;
	private JTextField txtCurrTrain;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JButton btnBrakes;
	private JButton btnEmergencyBrakes;
	private TrainModelProto proto;
	private String brakeType;

	public void setProto(TrainModelProto proto){
		this.proto = proto;
	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TrainModelUIProto window = new TrainModelUIProto();
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
	public TrainModelUIProto() {
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Train Model");
		frame.setBounds(100, 100, 585, 360);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblTrainCharacteristics = new JLabel("Train Characteristics");
		lblTrainCharacteristics.setHorizontalAlignment(SwingConstants.CENTER);
		lblTrainCharacteristics.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblTrainCharacteristics.setBounds(10, 11, 160, 14);
		frame.getContentPane().add(lblTrainCharacteristics);
		
		JLabel lblNewLabel = new JLabel("Display");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setBounds(190, 11, 200, 14);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblFailureModes = new JLabel("Failure Modes");
		lblFailureModes.setHorizontalAlignment(SwingConstants.CENTER);
		lblFailureModes.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblFailureModes.setBounds(423, 11, 125, 14);
		frame.getContentPane().add(lblFailureModes);
		
		JLabel lblHeight = new JLabel("Height (m)");
		lblHeight.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblHeight.setBounds(10, 36, 60, 14);
		frame.getContentPane().add(lblHeight);
		
		JLabel lblWidth = new JLabel("Width (m)");
		lblWidth.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblWidth.setBounds(10, 61, 60, 14);
		frame.getContentPane().add(lblWidth);
		
		JLabel lblLength = new JLabel("Length (m)");
		lblLength.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblLength.setBounds(10, 86, 65, 14);
		frame.getContentPane().add(lblLength);
		
		JLabel lblMass = new JLabel("Mass (tons)");
		lblMass.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblMass.setBounds(10, 113, 65, 14);
		frame.getContentPane().add(lblMass);
		
		JLabel lblCrewMembers = new JLabel("Crew Members");
		lblCrewMembers.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblCrewMembers.setBounds(10, 138, 87, 14);
		frame.getContentPane().add(lblCrewMembers);
		
		JLabel lblPassengers = new JLabel("Passengers");
		lblPassengers.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPassengers.setBounds(10, 163, 75, 14);
		frame.getContentPane().add(lblPassengers);
		
		JLabel lblNewLabel_1 = new JLabel("Number of Cars");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(10, 188, 87, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		txtHeight = new JTextField();
		txtHeight.setHorizontalAlignment(SwingConstants.CENTER);
		txtHeight.setEditable(false);
		txtHeight.setBounds(112, 34, 55, 20);
		frame.getContentPane().add(txtHeight);
		txtHeight.setColumns(10);
		
		txtCrewMems = new JTextField();
		txtCrewMems.setHorizontalAlignment(SwingConstants.CENTER);
		txtCrewMems.setEditable(false);
		txtCrewMems.setColumns(10);
		txtCrewMems.setBounds(112, 136, 55, 20);
		frame.getContentPane().add(txtCrewMems);
		
		txtWidth = new JTextField();
		txtWidth.setHorizontalAlignment(SwingConstants.CENTER);
		txtWidth.setEditable(false);
		txtWidth.setColumns(10);
		txtWidth.setBounds(112, 59, 55, 20);
		frame.getContentPane().add(txtWidth);
		
		txtLength = new JTextField();
		txtLength.setHorizontalAlignment(SwingConstants.CENTER);
		txtLength.setEditable(false);
		txtLength.setColumns(10);
		txtLength.setBounds(112, 84, 55, 20);
		frame.getContentPane().add(txtLength);
		
		txtMass = new JTextField();
		txtMass.setHorizontalAlignment(SwingConstants.CENTER);
		txtMass.setEditable(false);
		txtMass.setColumns(10);
		txtMass.setBounds(112, 111, 55, 20);
		frame.getContentPane().add(txtMass);
		
		txtPassengers = new JTextField();
		txtPassengers.setHorizontalAlignment(SwingConstants.CENTER);
		txtPassengers.setEditable(false);
		txtPassengers.setColumns(10);
		txtPassengers.setBounds(112, 161, 55, 20);
		frame.getContentPane().add(txtPassengers);
		
		txtNumofCars = new JTextField();
		txtNumofCars.setHorizontalAlignment(SwingConstants.CENTER);
		txtNumofCars.setEditable(false);
		txtNumofCars.setColumns(10);
		txtNumofCars.setBounds(112, 186, 55, 20);
		frame.getContentPane().add(txtNumofCars);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(192, 247, 43, -30);
		frame.getContentPane().add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		separator_1.setBounds(180, 11, 10, 255);
		frame.getContentPane().add(separator_1);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setOrientation(SwingConstants.VERTICAL);
		separator_2.setBounds(403, 11, 10, 255);
		frame.getContentPane().add(separator_2);
		
		JLabel lblAcceleration = new JLabel("Acceleration");
		lblAcceleration.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblAcceleration.setBounds(189, 37, 67, 14);
		frame.getContentPane().add(lblAcceleration);
		
		JLabel lblNewLabel_2 = new JLabel("Velocity");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_2.setBounds(189, 62, 46, 14);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblBrakes = new JLabel("Brakes");
		lblBrakes.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblBrakes.setBounds(189, 114, 46, 14);
		frame.getContentPane().add(lblBrakes);
		
		JLabel lblSlopeOfTrack = new JLabel("Slope of Track");
		lblSlopeOfTrack.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblSlopeOfTrack.setBounds(189, 87, 87, 14);
		frame.getContentPane().add(lblSlopeOfTrack);
		
		JLabel lblLights = new JLabel("Lights");
		lblLights.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblLights.setBounds(189, 139, 46, 14);
		frame.getContentPane().add(lblLights);
		
		JLabel lblDoors = new JLabel("Doors");
		lblDoors.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDoors.setBounds(189, 164, 46, 14);
		frame.getContentPane().add(lblDoors);
		
		JLabel lblNextStation = new JLabel("Next Station");
		lblNextStation.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNextStation.setBounds(189, 189, 87, 14);
		frame.getContentPane().add(lblNextStation);
		
		JLabel lblArrivalTime = new JLabel("Arrival Time");
		lblArrivalTime.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblArrivalTime.setBounds(189, 214, 67, 14);
		frame.getContentPane().add(lblArrivalTime);
		
		txtAccel = new JTextField();
		txtAccel.setHorizontalAlignment(SwingConstants.CENTER);
		txtAccel.setEditable(false);
		txtAccel.setColumns(10);
		txtAccel.setBounds(293, 34, 100, 20);
		frame.getContentPane().add(txtAccel);
		
		txtVelocity = new JTextField();
		txtVelocity.setHorizontalAlignment(SwingConstants.CENTER);
		txtVelocity.setEditable(false);
		txtVelocity.setColumns(10);
		txtVelocity.setBounds(293, 59, 100, 20);
		frame.getContentPane().add(txtVelocity);
		
		txtSlope = new JTextField();
		txtSlope.setHorizontalAlignment(SwingConstants.CENTER);
		txtSlope.setEditable(false);
		txtSlope.setColumns(10);
		txtSlope.setBounds(293, 84, 100, 20);
		frame.getContentPane().add(txtSlope);
		
		txtBrakes = new JTextField();
		txtBrakes.setHorizontalAlignment(SwingConstants.CENTER);
		txtBrakes.setEditable(false);
		txtBrakes.setColumns(10);
		txtBrakes.setBounds(293, 111, 100, 20);
		frame.getContentPane().add(txtBrakes);
		
		txtLights = new JTextField();
		txtLights.setHorizontalAlignment(SwingConstants.CENTER);
		txtLights.setEditable(false);
		txtLights.setColumns(10);
		txtLights.setBounds(293, 136, 100, 20);
		frame.getContentPane().add(txtLights);
		
		txtDoors = new JTextField();
		txtDoors.setHorizontalAlignment(SwingConstants.CENTER);
		txtDoors.setEditable(false);
		txtDoors.setColumns(10);
		txtDoors.setBounds(293, 161, 100, 20);
		frame.getContentPane().add(txtDoors);
		
		txtNextStation = new JTextField();
		txtNextStation.setHorizontalAlignment(SwingConstants.CENTER);
		txtNextStation.setEditable(false);
		txtNextStation.setColumns(10);
		txtNextStation.setBounds(293, 186, 100, 20);
		frame.getContentPane().add(txtNextStation);
		
		txtArrival = new JTextField();
		txtArrival.setHorizontalAlignment(SwingConstants.CENTER);
		txtArrival.setEditable(false);
		txtArrival.setColumns(10);
		txtArrival.setBounds(293, 212, 100, 20);
		frame.getContentPane().add(txtArrival);
		
		txtCurrBlock = new JTextField();
		txtCurrBlock.setHorizontalAlignment(SwingConstants.CENTER);
		txtCurrBlock.setEditable(false);
		txtCurrBlock.setColumns(10);
		txtCurrBlock.setBounds(293, 238, 100, 20);
		frame.getContentPane().add(txtCurrBlock);
		
		JLabel lblNewLabel_3 = new JLabel("Current Block");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_3.setBounds(189, 241, 87, 14);
		frame.getContentPane().add(lblNewLabel_3);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(417, 135, 145, 2);
		frame.getContentPane().add(separator_3);
		
		JLabel lblBrakes_1 = new JLabel("Brakes");
		lblBrakes_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblBrakes_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblBrakes_1.setBounds(423, 148, 125, 14);
		frame.getContentPane().add(lblBrakes_1);
		
		btnBrakes= new JButton("Brake");
		btnBrakes.setBackground(new Color(255, 255, 102));
		btnBrakes.setBounds(425, 180, 125, 25);
		btnBrakes.addActionListener(this);
		frame.getContentPane().add(btnBrakes);
		
		btnEmergencyBrakes = new JButton("E-Brake");
		btnEmergencyBrakes.setBackground(new Color(255, 51, 0));
		btnEmergencyBrakes.setBounds(425, 225, 125, 25);
		btnEmergencyBrakes.addActionListener(this);
		frame.getContentPane().add(btnEmergencyBrakes);
		
		JSeparator separator_4 = new JSeparator();
		separator_4.setBounds(10, 213, 160, 2);
		frame.getContentPane().add(separator_4);
		
		JLabel lblTrainSelection = new JLabel("Train Selection");
		lblTrainSelection.setHorizontalAlignment(SwingConstants.CENTER);
		lblTrainSelection.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblTrainSelection.setBounds(10, 220, 160, 14);
		frame.getContentPane().add(lblTrainSelection);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(10, 238, 160, 20);
		frame.getContentPane().add(comboBox);
		
		JSeparator separator_5 = new JSeparator();
		separator_5.setBounds(10, 269, 549, 2);
		frame.getContentPane().add(separator_5);
		
		JLabel lblNewLabel_4 = new JLabel("  Current Train");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_4.setBounds(10, 274, 550, 14);
		frame.getContentPane().add(lblNewLabel_4);
		
		txtCurrTrain = new JTextField();
		txtCurrTrain.setHorizontalAlignment(SwingConstants.CENTER);
		txtCurrTrain.setEditable(false);
		txtCurrTrain.setBounds(240, 293, 100, 20);
		frame.getContentPane().add(txtCurrTrain);
		txtCurrTrain.setColumns(10);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("Engine Failure");
		chckbxNewCheckBox.setFont(new Font("Tahoma", Font.PLAIN, 12));
		chckbxNewCheckBox.setBounds(419, 33, 109, 23);
		frame.getContentPane().add(chckbxNewCheckBox);
		
		JCheckBox chckbxSignalPickupFailure = new JCheckBox("Signal Pickup Failure");
		chckbxSignalPickupFailure.setFont(new Font("Tahoma", Font.PLAIN, 12));
		chckbxSignalPickupFailure.setBounds(419, 68, 140, 23);
		frame.getContentPane().add(chckbxSignalPickupFailure);
		
		JCheckBox chckbxBrakeFailure = new JCheckBox("Brake Failure");
		chckbxBrakeFailure.setFont(new Font("Tahoma", Font.PLAIN, 12));
		chckbxBrakeFailure.setBounds(419, 103, 109, 23);
		frame.getContentPane().add(chckbxBrakeFailure);
		
		JLabel lbleverythingCurrentlyIn = new JLabel("* Everything currently in metric");
		lbleverythingCurrentlyIn.setFont(new Font("Tahoma", Font.ITALIC, 11));
		lbleverythingCurrentlyIn.setBounds(10, 282, 150, 14);
		frame.getContentPane().add(lbleverythingCurrentlyIn);
	}
	//Methods to set all of the text fields
	//Train Characteristics
	public void setHeight(double height){
		txtHeight.setText(new Double(height).toString());
	}
	
	public void setWidth(double width){
		txtWidth.setText(new Double(width).toString());
	}
	
	public void setLength(double length){
		txtLength.setText(new Double(length).toString());
	}
	
	public void setMass(double mass){
		txtMass.setText(new Double(mass).toString());
	}
	
	public void setCrew(int crew){
		txtCrewMems.setText(new Integer(crew).toString());
	}
	
	public void setPass(int pass){
		txtPassengers.setText(new Integer(pass).toString());
	}
	
	public void setCars(int cars){
		txtNumofCars.setText(new Integer(cars).toString());
	}
	//Display
	public void setAcceleration(double accel){
		txtAccel.setText(new Double(accel).toString());
	}
	public void setVelocity(String velocity){
		txtVelocity.setText(velocity);
	}
	
	public void setSlope(double slope){
		txtSlope.setText(new Double(slope).toString()+"%");
	}
	
	public void setBrakes(String brakes){
		txtBrakes.setText(brakes);
	}
	
	public void setLights(String lights){
		txtLights.setText(lights);
	}
	
	public void setDoors(String doors){
		txtDoors.setText(doors);
	}
	
	public void setNextStation(String nextStation){
		txtNextStation.setText(nextStation);
	}
	
	public void setArrival(int arrivalTime){
		txtArrival.setText(new Integer(arrivalTime).toString()+" mins");
	}
	
	public void setCurrBlock(int currblock){
		txtCurrBlock.setText(new Integer(currblock).toString());
	}
	
	public void setCurrTrain(String train){
		txtCurrTrain.setText(train);
	}
	//Getting events from buttons
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == getBtnBrakes()){
			brakeType = "brakes";
			switchbrakes(txtBrakes.getText());
		}
		else if(e.getSource() == btnEmergencyBrakes){
			brakeType = "Ebrakes";
			switchbrakes(txtBrakes.getText());
		}
	}
	
	//testing stuff
	public void switchbrakes(String brakes){
		if(brakes.equals("In Use"))
			txtBrakes.setText("Not In Use");
		else
			txtBrakes.setText("In Use");
	}
	
	public JButton getBtnBrakes() {
		return btnBrakes;
	}
	public void setBtnBrakes(JButton btnBrakes) {
		this.btnBrakes = btnBrakes;
	}
	//Return value of brakes to main prototype
	public String getBrakes(){
		return txtBrakes.getText();
	}
	
	public String getBrakeType(){
		return brakeType;
	}
}
