package TrainModel;

//import TrainModelUIProto;

import java.text.DecimalFormat;

public class TrainModel{
	/* All units for calculation will be kept in metric
	 * Whenever they need to be displayed, they will be
	 * converted to imperial */
	DecimalFormat SigFig = new DecimalFormat("0.00");	// For reducing sigfigs
	
	//################
	//###ATTRIBUTES###
	//################
	private TrainModelUIProto ui;
	
	//Constants
	private static final double METRIC_VEL_CONV = 3.6;	// Used to convert from km/h --> m/s 
	private static final double TRAIN_VELOCITY_LIMIT = 19.444;	// 19.44m/s --> 70.0 km/h
	//private static final double TRAIN_ACCELERATION_LIMIT = 0.5;	// m/s^2
	private static final double BRAKE_DECEL = -1.2;	// m/s^2
	private static final double E_BRAKE_DECEL = -2.73;	// m/s^2
	private static final double TRAIN_MASS = 40900.0;	// 40900 kg --> 40.9 tons;
	private static final int PASS_MASS = 68;
	
	//Train Characteristics
	private double totalMass; // combined train and people
	private double trainHeight = 3.42;	// meters
	private double trainWidth = 2.65;	// meters
	private double trainLength = 32.20;	// meters
	private double trainPower;
	private double trainAcceleration;
	private double trainVelocity;
	private int crew;
	private int passengers;
	
	//Train Features
	private boolean brake;
	private boolean eBrake;
	private boolean brakeStatus;
	private boolean lightStatus;
	private boolean leftDoorStatus;
	private boolean rightDoorStatus;
	private boolean underground;
	private double temperature;
	private boolean acStatus;
	private boolean heatStatus;
	
	//Other stuff
	private boolean[] failure = new boolean[3];
	private double distanceTraveled;
	private double tickDistance;
	private double departTime;
	private int safeAuthority;
	private int safeSetPoint;
	//private CrewSchedule crewSchedule;
	private int arrivalBlock;
	private int currentBlock;
	private double slope;
	
	//###############
	//###FUNCTIONS###
	//###############
	public TrainModel(int trainID){
		while(true){
			//calcAcceleration();
			//calcVelocity();
			//setTxtFields();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	//Calculation functions
	public void calcAcceleration(){
		
	}
	
	public void calcVelocity(){
		
	}
	
	public void addPassengers(int newPassengers){
		
	}
	
	public void removePassengers(){
		passengers = 0;
	}
	//Dealing with brakes
	public void activateServiceBrakes(){
		brake = true;
	}
	public void deactivateServiceBrakes(){
		brake = false;
	}
	
	public void activateEmergencyBrakes(){
		eBrake = true;
	}
	public void deactivateEmergencyBrakes(){
		eBrake = false;
	}
	
	//Getter and Setter functions
	public double getMass(){
		totalMass = TRAIN_MASS + passengers*PASS_MASS;
		return totalMass;
	}
	
	public double getHeight(){
		return trainHeight;
	}
	
	public double getWidth(){
		return trainWidth;
	}
	
	public double getLength(){
		return trainLength;
	}
	
	public int getCrew(){
		return crew;
	}
	
	public int getPassengers(){
		return passengers;
	}
	
	public double getDistanceTraveled(){
		return distanceTraveled;
	}
	
	public double getVelocity(){
		return trainVelocity;
	}
	
	public double getAcceleration(){
		return trainAcceleration;
	}
	
	public boolean leftDoorStatus(){
		return leftDoorStatus;
	}
	
	public boolean rightDoorStatus(){
		return rightDoorStatus;
	}
	
	public boolean lightStatus(){
		return lightStatus;
	}
	
	public double getTemp(){
		return temperature;
	}
	
	public boolean isUnderground(){
		//check which block you are on
		//if you fall under tunnel blocks then true
		//else false
		return underground;
	}
	
	public int getBlock(){
		return currentBlock;
	}
	
	public void setPower(double power){
		trainPower = power;
	}
	
	public void setLeftDoor(boolean doorStatus){
		leftDoorStatus = doorStatus;
	}
	
	public void setRightDoor(boolean doorStatus){
		rightDoorStatus = doorStatus;
	}
	
	public void setLights(boolean lights){
		lightStatus = lights;
	}
	
	public void setTemp(double setTemp){
		temperature = setTemp;
	}
	
	public void setFailure(int fail){
		failure[fail] = true;
	}
	
	public void setDisplay(){
		
	}
}