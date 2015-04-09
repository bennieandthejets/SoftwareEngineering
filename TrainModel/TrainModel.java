package TrainModel;

import java.text.DecimalFormat;
import java.util.*;

import TrackModel.Block;
import MBO.*;
import Simulator.*;

public class TrainModel{
	/* All units for calculation will be kept in metric
	 * Whenever they need to be displayed, they will be
	 * converted to imperial */
	DecimalFormat SigFig = new DecimalFormat("0.00");	// For reducing sigfigs
	
	//################
	//###ATTRIBUTES###
	//################
	private static TrainModelUI ui;
	private TrainModelWrapper tmWrapper;
	public Antenna antenna;
	private Simulator sim;
	private MBO mbo;
	
	//Constants
	private static final double METRIC_VEL_CONV = 3.6;	// Used to convert from km/h --> m/s 
	private static final double METER_TO_FEET = 3.28084; 
	private static final double METRIC_TO_US = 1.10231131;
	private static final double KM_TO_MI = 0.621371;
	
	private static final double TRAIN_VELOCITY_LIMIT = 19.444;	// 19.44m/s --> 70.0 km/h
	private static final double TRAIN_ACCELERATION_LIMIT = 0.5;	// m/s^2
	private static final double BRAKE_DECEL = -1.2;	// m/s^2
	private static final double E_BRAKE_DECEL = -2.73;	// m/s^2
	private static final double TRAIN_MASS = 40900.0;	// 40900 kg --> 40.9 tons;
	private static final int PASS_MASS = 68;
	private static final int MAX_PASS = 222;
	
	//Train Characteristics
	private int trainID;
	private double trainHeight = 3.42;	// meters
	private double trainWidth = 2.65;	// meters
	private double trainLength = 32.20;	// meters
	private double trainPower;	// W
	private double trainForce;	// N
	private double trainAcceleration;	// m/s^2
	private double trainVelocity; 		// m/s
	private int crew;
	private int passengers;
	
	//Train Features
	private boolean brake;
	private boolean eBrake;
	private boolean lightStatus;
	private boolean leftDoorStatus;
	private boolean rightDoorStatus;
	private boolean underground;
	private double temperature;
	private boolean acStatus;
	private boolean heatStatus;
	
	//Track Model stuff
	private Block currentBlock;
	private Block nextBlock;
	private double blockLocation;
	private boolean atStation;
	
	//MBO stuff
	private int safeAuthority;
	private double safeSetpoint;
	private CrewSchedule crewSchedule;
	private double distanceTraveled;
	
	
	//Other stuff
	private double stopDistance;
	private double authority;
	private double setpoint;
	private boolean[] failure = new boolean[3];
	private double departTime;
	private double slope;
	private double tickDistance;
	private double dispVel;
	
	Random randomPass = new Random(System.currentTimeMillis());
	
	//###############
	//###FUNCTIONS###
	//###############
	public TrainModel(int trainID){
		//initializing variables
		this.trainID = trainID;
		ui = new TrainModelUI();
		//setPower(150000.0); for testing
		trainAcceleration = 0.0;
		trainVelocity = 0.0;
		passengers = 0;
		blockLocation = 0.0;
		distanceTraveled = 0.0;
//		while(true){
//			updateTrain();
//			setDisplay();
//			try {
//				Thread.sleep(100);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		
		//TEST ONLY
		setpoint = 18.0;
		stopDistance = 100.0;
		
	}
	
	//main  for testing
	public static void main(String[] args){
		ui = new TrainModelUI();
		TrainModel train = new TrainModel(1);
		ui.setTrain(train);
	}
	
	public void updateTrain(){
		calcForce();
		calcAcceleration();
		calcVelocity();
		if(trainVelocity == 0.0 & atStation){
			removePassengers();
			addPassengers();
		}
		calcDistance();
		setDisplay();
		System.out.println("Train Force: "+trainForce+" N");
		System.out.println("Train Accel: "+trainAcceleration+" m/s^2");
		System.out.println("Train Velocity: "+trainVelocity+" m/s");
		System.out.println("Tick Distance: "+ tickDistance);
		System.out.println("Distance Traveled: "+ distanceTraveled);
		System.out.println();
	}
	
	//Calculation functions
	public void calcForce(){
		double totalMass = getMass();
		//calc force from grav
		
		//calc force from power
		//first check if brakes are applied
		if(eBrake)
			trainForce = E_BRAKE_DECEL*totalMass;
		else if(brake)
			trainForce = BRAKE_DECEL*totalMass;
		else{
			if(trainVelocity == 0.0)
				trainForce = 1000* trainPower/.001; // N = W/(m/s) = kg*m/s^2
			else
				trainForce = 1000* trainPower/trainVelocity;	
		}
	}
	
	public void calcAcceleration(){
		double totalMass = getMass();
		trainAcceleration = trainForce/totalMass;	// a = F/M = N/kg = m/s^2 
		//If calculated acceleration is greater than the limit, set to the limit
		if(trainAcceleration > TRAIN_ACCELERATION_LIMIT)
			trainAcceleration = TRAIN_ACCELERATION_LIMIT;
		if(trainAcceleration < 0.0 & trainVelocity == 0.0)
			trainAcceleration = 0.0;
	}
	
	public void calcVelocity(){
		trainVelocity += trainAcceleration; // v = a/s = m/s
		//If calculated velocity is less than 0, set to 0
		if(trainVelocity < 0.0)
			trainVelocity = 0.0;
	}
	
	public void calcDistance(){
		tickDistance = trainVelocity;
		blockLocation += tickDistance;
		distanceTraveled += tickDistance;
	}
	
	public void addPassengers(){
		int newPassengers = randomPass.nextInt(MAX_PASS-passengers+1);
		passengers += newPassengers;
		System.out.println(newPassengers+" have boarded the train.");
	}
	
	public void removePassengers(){
		int oldPassengers = randomPass.nextInt(passengers+1);
		passengers -= oldPassengers;
		System.out.println(oldPassengers+" have left the train.");
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
	public int getID(){
		return trainID;
	}
	public double getMass(){
		double totalMass = TRAIN_MASS + passengers*PASS_MASS;
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
	
	public double getStopDistance(){
		//return mbo.calculateStopDistance(trainVelocity);
		return 100.0;
	}
	
	public double getVelocity(){
		return trainVelocity;
	}
	
	public double getSetpointVelocity(){
		return setpoint ;
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
	
	public double getDepartTime(){
		return departTime;
	}
	
	public Block getBlock(){
		return currentBlock;
	}
	
	public void setPower(double power){
		trainPower = power;
	}
	
	public void setAntenna(Antenna antenna){
		this.antenna = antenna;
	}
	
	public void setSafeAuthority(double auth){
		authority = auth;
	}
	
	public void setSafeVelocity(double safeVel){
		safeSetpoint = safeVel;
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
	
	public void setAC(boolean ac){
		acStatus = ac;
	}
	
	public void setHeat(boolean heat){
		heatStatus = heat;
	}
	public void setFailure(int fail){
		failure[fail] = true;
	}
	
	public void setDisplay(){
		//Characteristics
		double totalMass = getMass()/1000;
		ui.setHeight(SigFig.format(trainHeight*METER_TO_FEET));
		ui.setWidth(SigFig.format(trainWidth*METER_TO_FEET));
		ui.setLength(SigFig.format(trainLength*METER_TO_FEET));
		ui.setMass(SigFig.format(totalMass*METRIC_TO_US));
		ui.setCrew(crew);
		ui.setPass(passengers);
		//Display
		ui.setAcceleration(SigFig.format(trainAcceleration*METER_TO_FEET));
		ui.setVelocity(SigFig.format(trainVelocity*METRIC_VEL_CONV*KM_TO_MI));
		ui.setDistTrav(SigFig.format(distanceTraveled*KM_TO_MI/1000)+" mi");
		//ui.setSlope(Slope);
		//ui.setBrakes(brake);
		//ui.setLights(lightStatus);
		//ui.setDoors(doorStatus);
		//ui.setNextStation(NextStation);
		//ui.setArrival(Arrival);
		//ui.setCurrBlock(CurrentBlock);
		//ui.setCurrTrain(CurrentTrain);
	}
}