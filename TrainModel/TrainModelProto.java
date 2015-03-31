package TrainModel;

import java.text.DecimalFormat;

public class TrainModelProto{
	
	/* All units for calculation will be kept in metric
	 * Whenever they need to be displayed, they will be
	 * converted to imperial */
	
	DecimalFormat SigFig = new DecimalFormat("0.00");	// For reducing sigfigs
	
	//constants
	private static final double TRAIN_VELOCITY_LIMIT = 19.444;	// 19.44m/s --> 70.0 km/h
	private static final double METRIC_VEL_CONV = 3.6;	//Used to convert from km/h --> m/s 
	//private static final double TRAIN_ACCELERATION_LIMIT = 0.5;	// m/s^2
	private static final double BRAKE_DECEL = -1.2;	// m/s^2
	private static final double E_BRAKE_DECEL = -2.73;	// m/s^2
	private static final int CREW = 3;
	private static final int PASS_MASS = 68;	// 68 kg =~ 150 lbs
	private static final int PASSENGERS = 83;
	private static final int CARS = 1;
	
	//variables
	private double TrainVelocity;	// m/s
	private double TrainAcceleration;	// m/s^2
	private double TrainMass = 40900.0;	// 40900 kg --> 40.9 tons
	private double TotalMass;
	private double TrainLength = 32.20;	// meters
	private double TrainHeight = 3.42;	// meters
	private double TrainWidth = 2.65;	// meters
	private double Slope = 10;	// gradient
	
	//defaults
	private String BrakeStatus;
	private String LightStatus = "On";
	private String DoorStatus = "Closed";
	private String NextStation = "Shadyside";
	private int Arrival = 13;
	private int CurrentBlock = 7;
	private String CurrentTrain = "Train 1";
	
	private TrainModelUIProto ui;
	//display variables
	private String DisplayVel;
	private double DisplayMass;
	
	
	public TrainModelProto(TrainModelUIProto ui){
		
		while(true){
			this.ui = ui;
			calcTotalMass();
			calcAcceleration();
			calcVelocity();
			setTxtFields();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String args[]){
		TrainModelUIProto UI = new TrainModelUIProto();
		TrainModelProto prototype = new TrainModelProto(UI);
		UI.setProto(prototype);
	}
	
	//This method receives the Set Point Velocity from the track
	public double getSetPointVelocity(){
		double setPointVel = 50.0;
		return setPointVel;
	}
	
	//This method receives power from the Train Controller
	public double getPower(){
		double Power = 140000.0;
		return Power;
	}
	//This method calculates the velocity
	public void calcVelocity(){
		double trainPower = getPower();
		double updatedVelocity = 0.0;
		updatedVelocity = trainPower/(TotalMass*TrainAcceleration); //in m/s
		TrainVelocity += TrainAcceleration;	// Vf = Vi + a*t and t is 1s
		if(TrainVelocity > TRAIN_VELOCITY_LIMIT)
			TrainVelocity = TRAIN_VELOCITY_LIMIT;
		if(TrainVelocity < 0)
			TrainVelocity = 0;
		//reducing velocity to 2 sigfigs for display
		DisplayVel = SigFig.format(TrainVelocity*METRIC_VEL_CONV);
	}
	
	//This method calculates the acceleration
	public void calcAcceleration(){
		String brakecheck = ui.getBrakes();
		if(brakecheck.equals("In Use")){
			BrakeStatus = "In Use";
			applyBrakes();
		}
		else{
			BrakeStatus = "Not In Use";
			TrainAcceleration = 0.5;
		}
	}
	
	//This method calculates the total mass of the train
	public void calcTotalMass(){
		TotalMass = (TrainMass + PASSENGERS*PASS_MASS);
		DisplayMass = TotalMass/1000;	// in metric tons
	}

	//This method applies the brake command, if brake failure is enabled, regular brakes will not work, e-brake will
	public void applyBrakes(){
		String breaktype = ui.getBrakeType();
		if(breaktype.equals("brakes"))
			TrainAcceleration = BRAKE_DECEL;
		else
			TrainAcceleration = E_BRAKE_DECEL;
	}
	
	
	//This method gets the slope of the terrain
	public void getSlope(){
		
	}
	
	//The following methods control the doors
	//Main door method
	public void Doors(){
		boolean leftState;
		boolean rightState;
	}
	//Opens left doors
	public boolean openLeft(boolean leftState){
		return leftState = true;
	}
	//Closes left doors
	public boolean closeLeft(boolean leftState){
		return leftState = false;
	}
	//Opens right doors
	public boolean openRight(boolean rightState){
		return rightState = true;
	}
	//Closes right doors
	public boolean closeRight(boolean rightState){
		return rightState = false;
	}
	
	//The following methods control the lights
	//Main light method
	public void Lights(){
		boolean lightsOn;
	}
	//Turns on lights
	public boolean turnOn(boolean lightsOn){
		return lightsOn = true;
	}
	//Turns off lights
	public boolean turnOff(boolean lightsOn){
		return lightsOn = false;
	}
	
	
	public void setTxtFields(){
		//Characteristics
		ui.setHeight(TrainHeight);
		ui.setWidth(TrainWidth);
		ui.setLength(TrainLength);
		ui.setMass(DisplayMass);
		ui.setCrew(CREW);
		ui.setPass(PASSENGERS);
		ui.setCars(CARS);
		//Display
		ui.setAcceleration(TrainAcceleration);
		ui.setVelocity(DisplayVel);
		ui.setSlope(Slope);
		ui.setBrakes(BrakeStatus);
		ui.setLights(LightStatus);
		ui.setDoors(DoorStatus);
		ui.setNextStation(NextStation);
		ui.setArrival(Arrival);
		ui.setCurrBlock(CurrentBlock);
		ui.setCurrTrain(CurrentTrain);
	}
}
