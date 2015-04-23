package TrainModel;

import java.awt.EventQueue;
import java.text.DecimalFormat;
import java.util.*;

import TrackModel.*;
import MBO.*;
import Simulator.*;
import TrainController.*;

public class TrainModel{
	/* All units for calculation will be kept in metric
	 * Whenever they need to be displayed, they will be
	 * converted to imperial */
	DecimalFormat SigFig = new DecimalFormat("0.00");	// For reducing sigfigs
	
	//################
	//###ATTRIBUTES###
	//################
	private TrainModelUI ui;
	private TrainModelWrapper tmWrapper;
	private TrackModel track;
	public Antenna antenna;
	private Simulator sim;
	private MBO mbo;
	private TrainController trainController;
	
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
	public Block currentBlock;
	private Block nextBlock;
	private double blockLocation;
	public boolean atStation;
	private String stationName = "";
	
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

	Random randomPass = new Random(System.currentTimeMillis());
	
	//###############
	//###FUNCTIONS###
	//###############
	public TrainModel(int trainID, Simulator newSimulator){
		//initializing variables
		this.trainID = trainID;
		//ui = new TrainModelUI();
		sim = newSimulator;
		track = sim.trackModel;
		mbo = sim.mbo;
		antenna = new Antenna(this,track,mbo);
		

		trainAcceleration = 0.0;
		trainVelocity = 0.0;
		passengers = 0;
		blockLocation = 0.0;
		distanceTraveled = 0.0;
		
	}
	
	public void updateTrain(){
		calcForce();
		calcAcceleration();
		calcVelocity();
		calcDistance();
		if(ui.getCurrTrain() == trainID)
			setDisplay();
		
		System.out.println("Train ID: " + trainID);
		System.out.println("Train Force: "+trainForce+" N");
		System.out.println("Train Accel: "+trainAcceleration+" m/s^2");
		System.out.println("Train Velocity: "+trainVelocity+" m/s");
		System.out.println("Tick Distance: "+ tickDistance);
		System.out.println("Distance Traveled: "+ distanceTraveled);
		System.out.println("Block Distance: " + blockLocation);
		System.out.println();
	}
	
	//Calculation functions
	public void calcForce(){
		double totalMass = getMass();
		//calc force from grav
		
		//calc force from power
		//first check if brakes are applied
		if(eBrake)
			if(trainVelocity == 0.0)
				trainForce = 0.0;
			else
				trainForce = E_BRAKE_DECEL*totalMass;
		else if(brake)
			if(trainVelocity == 0.0)
				trainForce = 0.0;
			else
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
		trainAcceleration = trainForce/(totalMass);	// a = F/M = N/kg = m/s^2 
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
	
	public void setStationInfo(String stationName, String stationSide, double distFromStation){
		this.stationName = stationName;
		trainController.setStationInfo(stationName,stationSide,distFromStation);
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
	
	public double getTickDistance(){
		return tickDistance;
	}
	
	public double getDistanceTraveled(){
		return distanceTraveled;
	}
	
	public double getStopDistance(){
		return mbo.calculateStopDistance(trainVelocity);
		//return 100.0;
	}
	
	public double getVelocity(){
		return trainVelocity;
	}
	
	public double getAuthority(){
		double tempAuth = authority;
		//this.authority = -1;
		return tempAuth;
	}
	
	public double getSetpointVelocity(){
		return setpoint;
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
	
	public double getBlockDistance() {
		return this.blockLocation;
	}
	
	public void setBlock(Block currBlock){
		if(this.currentBlock != null) {
			this.blockLocation -= this.currentBlock.getBlockSize();
		}
		this.currentBlock = currBlock;
	}
	
	public void setPower(double power){
		trainPower = power;
	}
	
	public void setAntenna(Antenna antenna){
		this.antenna = antenna;
	}
	
	public void setSafeAuthority(double auth){
		authority = auth;
		trainController.setAuthority(auth);
	}
	
	public void setAuthority(double auth){
		authority = auth;
		trainController.setAuthority(auth);
	}
	
	public void setSafeVelocity(double safeVel){
		safeSetpoint = safeVel;
		trainController.setSetpointVelocity(safeVel);
	}
	
	public void setVelocity(double vel){
		setpoint = vel;
		trainController.setSetpointVelocity(vel);
	}
	
	public void setLeftDoor(boolean doorStatus){
		leftDoorStatus = doorStatus;
		if(leftDoorStatus){
			removePassengers();
			addPassengers();
		}	
	}
	
	public void setRightDoor(boolean doorStatus){
		rightDoorStatus = doorStatus;
		if(rightDoorStatus){
			removePassengers();
			addPassengers();
		}	
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
	
	public void setUI(TrainModelUI ui) {
		this.ui = ui;
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
		ui.setPower(SigFig.format(trainPower));
		ui.setBrakes(brake,eBrake);
		ui.setLights(lightStatus);
		ui.setDoors(leftDoorStatus,rightDoorStatus);
		ui.setNextStation(stationName);
		ui.setCurrBlock(currentBlock.getBlockID());
		ui.setCurrTrain(trainID);
	}
	
	public void setTrainController(TrainController newTrainController) {
		trainController = newTrainController;
	}
}