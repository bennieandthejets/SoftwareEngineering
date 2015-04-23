package TrainController;

import TrainModel.*;

@SuppressWarnings("unused")
public class TrainController {
		
	private final double	MPS_TO_MPH = 2.23694;		//Conversion ratio for meters per second to miles per hour
	
	//TRAIN INFO
	private int			ID;
	private	int			mode;							//0 for automatic, 1 for manual
	
	//POWER CONTROL
	public boolean		brakeStatus;
	public boolean		eBrakeStatus;
	private	double		setpointVelocity;
	private	double		velocityFeedback;
	private double		targetVelocity;
	private double		remainingAuthority;
	private double		stopDistance;
	private boolean		manualBrake;
	private boolean		manualEBrake;
	
	//SUBSYSTEMS
	private boolean		lightStatus;
	public boolean		leftDoorStatus;
	public boolean		rightDoorStatus;
	private boolean		heatStatus;
	private	boolean		acStatus;
	
	//STATION INFO
	private boolean		approachingStation;
	private String		stationName;
	private String		stationSide;
	
	//OTHER CLASSES
	private TrainModel			model;
	private TrainControllerUI	ui;
	private VitalControl		vc;	
	
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
		brakeStatus = false;
		eBrakeStatus = false;
		leftDoorStatus = false;
		rightDoorStatus = false;
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
	
	public void setMode(int newMode) {
		mode = newMode;
	}
	
	public void setSetpointVelocity(double newSetpointVelocity) {
		setpointVelocity = newSetpointVelocity;
		if(mode == 1) {
			setTargetVelocity(setpointVelocity);
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
	
	public void setStationInfo(String newStationName, String newStationSide) {
		stationName = newStationName;
		stationSide = newStationSide;
		approachingStation = true;
		return;
	}
	
	//Determine how much authority the train has left, and whether or not it should stop
	private void checkRemainingAuthority() {
		remainingAuthority = remainingAuthority - model.getTickDistance();
		
		if(remainingAuthority < 0) {
			remainingAuthority = 0;
		}
		stopDistance = model.antenna.getStopDistance();
		if((remainingAuthority - model.getTickDistance()) <= stopDistance && model.getVelocity() != 0 && brakeStatus != true && eBrakeStatus != true ) {
			stopTrain(false);
		}
		else if ((brakeStatus || eBrakeStatus) && model.getVelocity() == 0 && remainingAuthority != 0) {
			releaseServiceBrakes(false);
			releaseEmergencyBrakes(false);
		}
		return;
	}
	
	public void sendPower(double power) {
		model.setPower(power);
		return;
	}
	
	//BRAKING METHODS
	public void stopTrain(boolean manual) {
		if(manual) {
			manualBrake = true;
		}
		model.setPower(0);
		vc.resetPower();
		model.activateServiceBrakes();
		brakeStatus = true;
		return;
	}
	
	public void emergencyStop(boolean manual) {
		if(manual) {
			manualEBrake = true;
		}
		model.setPower(0);
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
	
	//SUBSYSTEM CONTROL
	public void controlDoors(boolean manual, boolean open, String side) {
		//Opening doors
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
		return;
	}
	
	//Check if doors are open
	public boolean checkDoors() {
		return leftDoorStatus || rightDoorStatus;
	}
	
	public void tick(long currentTime, int currentTemp) {		
		//checkFailures();
		checkRemainingAuthority();
		
		if(model.atStation) {
			if(remainingAuthority == 0.0 && !checkDoors() && model.getVelocity() == 0.0) {
				controlDoors(false, true, stationSide);
			}
			else if(remainingAuthority != 0.0 && checkDoors()) {
				controlDoors(false, false, stationSide);
			}
		}
		
		if(!brakeStatus && !eBrakeStatus && remainingAuthority != 0) {
			vc.vitalPower(model.getVelocity(), model.getVelocity(), model.getVelocity());
		}
		
		
		//controlSubsystems(currentTime, currentTemp);
		return;
	}
	
}
