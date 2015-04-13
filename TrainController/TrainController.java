package TrainController;

import TrainModel.*;

@SuppressWarnings("unused")
public class TrainController {
		
	private final double	MPS_TO_MPH = 2.23694;		//Conversion ratio for meters per second to miles per hour
	
	private int			ID;
	private	int			mode;							//0 for automatic, 1 for manual		
	private	double		setpointVelocity;
	private	double		velocityFeedback;
	private double		targetVelocity;
	private double		remainingAuthority;
	private double		stopDistance;
	public boolean		brakeStatus;
	public boolean		eBrakeStatus;
	
	private TrainModel			model;
	private TrainControllerUI	ui;
	private VitalControl		vc;
	
	
	public TrainController(int newID, TrainModel newTrainModel, TrainControllerUI newUI) {
		ID = newID;
		model = newTrainModel;
		ui = newUI;
		vc = new VitalControl();
		mode = 1;
		setpointVelocity = 0.0;
		velocityFeedback = 0.0;
		remainingAuthority = 0.0;
		brakeStatus = false;
		eBrakeStatus = false;
		
		//TEST
		//remainingAuthority = 1000.0;
	}
	
	public int getID() {
		return this.ID;
	}
	
	public void setMode(int newMode) {
		mode = newMode;
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
	
	public void setAuthority(double newAuthority) {
		remainingAuthority = newAuthority;
		return;
	}
	
	private void checkRemainingAuthority() {
		
		double auth = model.getAuthority();
		if(auth > -1) {
			remainingAuthority = auth;
		}
		
		remainingAuthority = remainingAuthority - model.getDistanceTraveled();
		if(remainingAuthority < 0) {
			remainingAuthority = 0;
		}
		stopDistance = model.antenna.getStopDistance();
		if(remainingAuthority <= stopDistance && model.getVelocity() != 0 && brakeStatus != true && eBrakeStatus != true ) {
			stopTrain();
		}
		else if (remainingAuthority > 0) {
			releaseServiceBrakes();
			releaseEmergencyBrakes();
		}
		return;
	}
		

	
	public void setTargetVelocity(double newVelocity) {
		targetVelocity = newVelocity;
	}
	
	public void stopTrain() {
		model.setPower(0);
		vc.resetPower();
		model.activateServiceBrakes();
		brakeStatus = true;
		return;
	}
	
	public void emergencyStop() {
		model.setPower(0);
		vc.resetPower();
		model.activateEmergencyBrakes();
		eBrakeStatus = true;
		return;
	}
	
	public void releaseServiceBrakes() {
		if(brakeStatus == true) {
			model.deactivateServiceBrakes();
			brakeStatus = false;
		}
	}
		
	public void releaseEmergencyBrakes() {
		if(eBrakeStatus == true) {
			model.deactivateEmergencyBrakes();
			eBrakeStatus = false;
		}
	}
	
	public void tick(long currentTime, int currentTemp) {
		//checkFailures();
		checkRemainingAuthority();
		setpointVelocity = model.getSetpointVelocity();
		if(mode == 1) {
			setTargetVelocity(setpointVelocity);
		}
		if(!brakeStatus && !eBrakeStatus) {
			model.setPower(vc.vitalPower(model.getVelocity(), model.getVelocity(), model.getVelocity()));
		}
		
		//controlSubsystems(currentTime, currentTemp);
		
	}
	
	
	
}
