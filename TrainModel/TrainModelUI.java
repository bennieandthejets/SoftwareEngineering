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


public class TrainModelUI{ //implements ActionListener{

	public JFrame frame;
	private JTextField txtHeight;
	private JTextField txtCrewMems;
	private JTextField txtWidth;
	private JTextField txtLength;
	private JTextField txtMass;
	private JTextField txtPassengers;
	private JTextField txtAccel;
	private JTextField txtVelocity;
	private JTextField txtPower;
	private JTextField txtBrakes;
	private JTextField txtLights;
	private JTextField txtDoors;
	private JTextField txtNextStation;
	private JTextField txtCurrBlock;
	private JTextField txtCurrTrain;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private TrainModel train;
	private String brakeType;
	private JTextField txtDistance;
	private final JComboBox<Integer> trainSelectBox = new JComboBox<Integer>();
	
	private TrainModelWrapper wrapper;
	private int displayTrain;

	public void setTrain(TrainModel train){
		this.train = train;
		this.displayTrain = train.getID();
	}
	/**
	 * Launch the application.
	 */
	/*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TrainModelUI window = new TrainModelUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the application.
	 */
	public TrainModelUI(TrainModelWrapper wrapper) {
		this.wrapper = wrapper;
		initialize();
		//frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Train Model");
		frame.setBounds(100, 100, 420, 360);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
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
		
		JLabel lblHeight = new JLabel("Height (ft)");
		lblHeight.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblHeight.setBounds(10, 36, 60, 14);
		frame.getContentPane().add(lblHeight);
		
		JLabel lblWidth = new JLabel("Width (ft)");
		lblWidth.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblWidth.setBounds(10, 61, 60, 14);
		frame.getContentPane().add(lblWidth);
		
		JLabel lblLength = new JLabel("Length (ft)");
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
		
		JSeparator separator = new JSeparator();
		separator.setBounds(192, 247, 43, -30);
		frame.getContentPane().add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		separator_1.setBounds(180, 11, 10, 255);
		frame.getContentPane().add(separator_1);
		
		JLabel lblAcceleration = new JLabel("Acceleration (ft/s^2)");
		lblAcceleration.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblAcceleration.setBounds(189, 37, 120, 14);
		frame.getContentPane().add(lblAcceleration);
		
		JLabel lblNewLabel_2 = new JLabel("Velocity (mph)");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_2.setBounds(189, 62, 90, 14);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblBrakes = new JLabel("Brakes");
		lblBrakes.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblBrakes.setBounds(189, 114, 46, 14);
		frame.getContentPane().add(lblBrakes);
		
		JLabel lblSlopeOfTrack = new JLabel("Power (W)");
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
		
		txtAccel = new JTextField();
		txtAccel.setHorizontalAlignment(SwingConstants.CENTER);
		txtAccel.setEditable(false);
		txtAccel.setColumns(10);
		txtAccel.setBounds(310, 34, 83, 20);
		frame.getContentPane().add(txtAccel);
		
		txtVelocity = new JTextField();
		txtVelocity.setHorizontalAlignment(SwingConstants.CENTER);
		txtVelocity.setEditable(false);
		txtVelocity.setColumns(10);
		txtVelocity.setBounds(293, 59, 100, 20);
		frame.getContentPane().add(txtVelocity);
		
		txtPower = new JTextField();
		txtPower.setHorizontalAlignment(SwingConstants.CENTER);
		txtPower.setEditable(false);
		txtPower.setColumns(10);
		txtPower.setBounds(293, 84, 100, 20);
		frame.getContentPane().add(txtPower);
		
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
		
		txtCurrBlock = new JTextField();
		txtCurrBlock.setHorizontalAlignment(SwingConstants.CENTER);
		txtCurrBlock.setEditable(false);
		txtCurrBlock.setColumns(10);
		txtCurrBlock.setBounds(293, 212, 100, 20);
		frame.getContentPane().add(txtCurrBlock);
		
		JLabel lblNewLabel_3 = new JLabel("Current Block");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_3.setBounds(190, 214, 87, 14);
		frame.getContentPane().add(lblNewLabel_3);
		
		JSeparator separator_4 = new JSeparator();
		separator_4.setBounds(10, 188, 160, 2);
		frame.getContentPane().add(separator_4);
		
		JLabel lblTrainSelection = new JLabel("Train Selection");
		lblTrainSelection.setHorizontalAlignment(SwingConstants.CENTER);
		lblTrainSelection.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblTrainSelection.setBounds(10, 203, 160, 14);
		frame.getContentPane().add(lblTrainSelection);
		
		trainSelectBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == ItemEvent.SELECTED) {
			          Integer trainID = (Integer) arg0.getItem();
			          setTrain(wrapper.getTrain(trainID));
			          wrapper.getTrain(trainID).setDisplay();
			    }
			}
		});
		trainSelectBox.setBounds(10, 227, 160, 20);
		frame.getContentPane().add(trainSelectBox);
		
		JSeparator separator_5 = new JSeparator();
		separator_5.setBounds(10, 269, 383, 2);
		frame.getContentPane().add(separator_5);
		
		JLabel lblNewLabel_4 = new JLabel("  Current Train");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_4.setBounds(20, 277, 130, 14);
		frame.getContentPane().add(lblNewLabel_4);
		
		txtCurrTrain = new JTextField();
		txtCurrTrain.setHorizontalAlignment(SwingConstants.CENTER);
		txtCurrTrain.setEditable(false);
		txtCurrTrain.setBounds(41, 293, 100, 20);
		frame.getContentPane().add(txtCurrTrain);
		txtCurrTrain.setColumns(10);
		
		JLabel lblDistanceTraveled = new JLabel("Distance Traveled");
		lblDistanceTraveled.setHorizontalAlignment(SwingConstants.CENTER);
		lblDistanceTraveled.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblDistanceTraveled.setBounds(221, 277, 130, 14);
		frame.getContentPane().add(lblDistanceTraveled);
		
		txtDistance = new JTextField();
		txtDistance.setHorizontalAlignment(SwingConstants.CENTER);
		txtDistance.setEditable(false);
		txtDistance.setColumns(10);
		txtDistance.setBounds(242, 293, 90, 20);
		frame.getContentPane().add(txtDistance);
	}
	public void addTrain(int trainID) {
		trainSelectBox.addItem(new Integer(trainID));
	}
	
	public int getCurrTrain(){
		return displayTrain;
	}
	//Methods to set all of the text fields
	//Train Characteristics
	public void setHeight(String height){
		txtHeight.setText(height);
	}
	
	public void setWidth(String width){
		txtWidth.setText(width);
	}
	
	public void setLength(String length){
		txtLength.setText(length);
	}
	
	public void setMass(String mass){
		txtMass.setText(mass);
	}
	
	public void setCrew(int crew){
		txtCrewMems.setText(new Integer(crew).toString());
	}
	
	public void setPass(int pass){
		txtPassengers.setText(new Integer(pass).toString());
	}
	
	//Display
	public void setAcceleration(String accel){
		txtAccel.setText(accel);
	}
	public void setVelocity(String velocity){
		txtVelocity.setText(velocity);
	}
	
	public void setPower(String power){
		txtPower.setText(power);
	}
	
	public void setBrakes(boolean brakes, boolean eBrakes){
		if(brakes)
			txtBrakes.setText("Active");
		else if(eBrakes)
			txtBrakes.setText("E-Brakes");
		else
			txtBrakes.setText("Inactive");
	}
	
	public void setLights(boolean lights){
		if(lights)
			txtLights.setText("On");
		else
			txtLights.setText("Off");
	}
	
	public void setDoors(boolean left, boolean right){
		if(left)
			txtDoors.setText("Left Open");
		else if(right)
			txtDoors.setText("Right Open");
		else
			txtDoors.setText("Closed");
	}
	
	public void setNextStation(String nextStation){
		txtNextStation.setText(nextStation);
	}
	
	public void setCurrBlock(int currblock){
		txtCurrBlock.setText(new Integer(currblock).toString());
	}
	
	public void setCurrTrain(int train){
		txtCurrTrain.setText(new Integer(train).toString());
	}
	
	public void setDistTrav(String dist){
		txtDistance.setText(dist);
	}

	
	//testing stuff
	public void switchbrakes(String brakes){
		if(brakes.equals("In Use"))
			txtBrakes.setText("Not In Use");
		else
			txtBrakes.setText("In Use");
	}
	
	//Return value of brakes to main prototype
	public String getBrakes(){
		return txtBrakes.getText();
	}
	
	public String getBrakeType(){
		return brakeType;
	}
}
