package TrainController;

import java.text.SimpleDateFormat;
import java.util.Date;

import TrainModel.*;

@SuppressWarnings("unused")
public class TrainController {
		
	private final double	MPS_TO_MPH = 2.23694;		//Conversion ratio for meters per second to miles per hour
	
	//TRAIN INFO
	private	int			ID;
	private	int			mode;							//0 for automatic, 1 for manual
	
	//POWER CONTROL
	private	double		setpointVelocity;
	private	double		velocityFeedback;
	private	double		targetVelocity;
	private	double		remainingAuthority;
	private double		powerCommand;
	
	//BRAKING
	public	boolean		brakeStatus;					//true = on
	public	boolean		eBrakeStatus;					//true = on
	private	boolean		manualBrake;					//true = manual braking
	private	boolean		manualEBrake;					//true = manual e-braking
	
	//SUBSYSTEMS
	public	boolean		lightStatus;					//true = on
	public	boolean		leftDoorStatus;					//true = open
	public	boolean		rightDoorStatus;				//true = open
	public	boolean		heatStatus;						//true = on
	public	boolean		acStatus;						//true = open
	
	//STATION INFO
	private	String		stationName;
	private	String		stationSide;					//"left" or "right"
	
	//ASSOCIATED CLASSES
	private	TrainModel			model;
	private	TrainControllerUI	ui;
	private	VitalControl		vc;	
	

	
	public TrainController(int newID, TrainModel newTrainModel, TrainControllerUI newUI) {
		ID = newID;
		
		model = newTrainModel;
		model.setTrainController(this);
		ui = newUI;
		vc = new VitalControl(this);
		mode = 1;
		
		setpointVelocity = 0.0;
		velocityFeedback = 0.0;
		remainingAuthority = 0.0;
		targetVelocity = 0.0;
		powerCommand = 0.0;
		
		brakeStatus = false;
		eBrakeStatus = false;
		
		leftDoorStatus = false;
		rightDoorStatus = false;
		
		stationName = "";
	}
	
	public int getID() {
		return this.ID;
	}
	
	public double getSetpointVelocity() {
		return setpointVelocity;
	}
	
	public double getVelocityFeedback() {
		return model.getVelocity();
	}
	
	public double getAuthority() {
		return remainingAuthority;
	}
	
	public double getPowerCommand() {
		return powerCommand;
	}
	
	//Switch between auto and manual modes
	public void setMode(int newMode) {
		mode = newMode;
		return;
	}
	
	//Get a new setpoint velocity, use it as the target velocity in auto mode
	public void setSetpointVelocity(double newSetpointVelocity) {
		setpointVelocity = newSetpointVelocity;
		if(mode == 1) {
			setTargetVelocity(setpointVelocity);
		}
		
		if(setpointVelocity == 0.0) {
			stopTrain(false);
		}
		return;
	}
	
	public void setTargetVelocity(double newVelocity) {
		targetVelocity = newVelocity;
		vc.setTargetVelocity(newVelocity);
		return;
	}
	
	public void setAuthority(double newAuthority) {
		remainingAuthority = newAuthority;
		return;
	}
	
	//Get station info from the beacon and activate station approach mode
	public void setStationInfo(String newStationName, String newStationSide, double newDistanceFromStation) {
		if(!stationName.equals(newStationName)) {
			stationName = newStationName;
			stationSide = newStationSide;
		}
		return;
	}
	
	
	
	//===================
	//POWER & AUTHORITY
	//===================
	
	//Determine how much authority the train has left, and whether or not it should stop
	private void checkRemainingAuthority() {
		double tickDistance = model.getTickDistance();
		double currentVelocity = model.getVelocity();
		double stopDistance = model.antenna.getStopDistance();
		
		remainingAuthority = remainingAuthority - tickDistance;
		
		if(remainingAuthority < 0) {
			remainingAuthority = 0;
		}
		
		if((remainingAuthority - tickDistance) <= stopDistance && currentVelocity != 0 && brakeStatus != true && eBrakeStatus != true ) {
			stopTrain(false);
		}
		else if ((brakeStatus || eBrakeStatus) && currentVelocity == 0 && remainingAuthority != 0 && targetVelocity > 0) {
			releaseServiceBrakes(false);
			releaseEmergencyBrakes(false);
		}
		
		return;
	}
	
	//Call for vital power control only if the brakes aren't set and the train has authority
	private void controlPower() {
		if(!brakeStatus && !eBrakeStatus && remainingAuthority != 0) {
			vc.vitalPower(model.getVelocity(), model.getVelocity(), model.getVelocity());
		}
	}
	
	//Send the model its power command
	public void sendPower(double power) {
		powerCommand = power;
		model.setPower(power);
		return;
	}
	
	
	
	//===================
	//		BRAKING
	//===================
	
	public void stopTrain(boolean manual) {
		if(manual) {
			manualBrake = true;
		}
		sendPower(0);
		vc.resetPower();
		model.activateServiceBrakes();
		brakeStatus = true;
		return;
	}
	
	public void emergencyStop(boolean manual) {
		if(manual) {
			manualEBrake = true;
		}
		sendPower(0);
		vc.resetPower();
		model.activateEmergencyBrakes();
		eBrakeStatus = true;
		return;
	}
	
	public void releaseServiceBrakes(boolean manual) {
		if(manualBrake) {
			if(manual && brakeStatus) {
				model.deactivateServiceBrakes();
				brakeStatus = false;
				manualBrake = false;
			}
		}
		else if(brakeStatus) {
			model.deactivateServiceBrakes();
			brakeStatus = false;
		}
		return;
	}
		
	public void releaseEmergencyBrakes(boolean manual) {
		if(manualEBrake) {
			if(manual && eBrakeStatus) {
				model.deactivateEmergencyBrakes();
				eBrakeStatus = false;
				manualEBrake = false;
			}
		}
		else if(eBrakeStatus) {
			model.deactivateEmergencyBrakes();
			eBrakeStatus = false;
		}
		return;
	}
	
	
	
	//===================
	//SUBSYSTEM CONTROL
	//===================
	
	public void controlSubsystems(long currentTime, long currentTemp) {
		if(model.currentBlock.getStation() != null) {
			if(model.getVelocity() == 0.0 && remainingAuthority == 0.0) {
				if(!checkDoors()) {
					controlDoors(false, true, stationSide);
				}
			}
			else if(remainingAuthority != 0.0 && model.getVelocity() > 0.0) {
				controlDoors(false, false, "right");
				controlDoors(false, false, "left");
			}
		}
		
		Date date = new Date(currentTime);
		int hour = Integer.parseInt(new SimpleDateFormat("HH:mm:ss").format(date).split(":")[0]);
		//Lights are on between 6PM and 6AM
		if( hour >= 18 || hour <= 6) {
			controlLights(true);
		}
		else {
			controlLights(false);
		}
		
		if(model.currentBlock.isUnderground()) {
			controlLights(true);
		}
	}
	
	public void controlDoors(boolean manual, boolean open, String side) {
		//Opening doors
		if(side != null) {
			if(open) {
				//Don't open the doors of a moving train
				if(model.getVelocity() == 0) {
					if(side.equals("right")) {
						model.setRightDoor(true);
						rightDoorStatus = true;
					}
					else if(side.equals("left")) {
						model.setLeftDoor(true);
						leftDoorStatus = true;
					}
				}
			}
			//Closing doors
			else {
				if(side.equals("right")) {
					model.setRightDoor(false);
					rightDoorStatus = false;
				}
				else if(side.equals("left")) {
					model.setLeftDoor(false);
					leftDoorStatus = false;
				}
			}
		}
		return;
	}
	
	public void controlLights(boolean on) {
		model.setLights(on);
		lightStatus = on;
	}
	
	//Check if doors are open
	public boolean checkDoors() {
		return leftDoorStatus || rightDoorStatus;
	}
	
	
	
	//===================
	//PATRICK WARBURTON
	//===================
	
	public void tick(long currentTime, int currentTemp) {		
		checkRemainingAuthority();
		controlSubsystems(currentTime, currentTemp);
		controlPower();
		return;
	}
	
}
