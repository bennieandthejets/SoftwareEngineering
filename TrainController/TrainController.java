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
	private boolean		manualBrake;
	private boolean		manualEBrake;
	
	private TrainModel			model;
	private TrainControllerUI	ui;
	//private VitalControl		vc;
	
	//REMOVE WHEN VITAL
	private final int		MAX_POWER = 120000;			//Maximum power output (W)
	private final double	MAX_VELOCITY = 19.4444;		//Maximum train velocity (m/s)
	private final double	TIME_PERIOD = 1;			//Sample period of time (s)
	private	final double	Ki = 0.00000001;			//Integral Gain
	private	final double	Kp = 50;					//Proportional Gain
	
	private	double[]	powerCommand = new double[2];	//Current[1] and most recent[0] power commands
	private double[]	u = new double[2];				//Intermediate variables
	private double[]	velocityError = new double[2];	//Current [1] and most recent [0] velocity velocityError
	
	
	public TrainController(int newID, TrainModel newTrainModel, TrainControllerUI newUI) {
		ID = newID;
		model = newTrainModel;
		model.setTrainController(this);
		ui = newUI;
		//vc = new VitalControl();
		mode = 1;
		setpointVelocity = 0.0;
		velocityFeedback = 0.0;
		remainingAuthority = 0.0;
		brakeStatus = false;
		eBrakeStatus = false;
		
		//REMOVE WHEN VITAL
		powerCommand[0] = 0.0;
		powerCommand[1] = 0.0;
		velocityError[0] = 0.0;
		velocityError[1] = 0.0;
		u[0] = 0.0;
		u[1] = 0.0;
		
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
		remainingAuthority = remainingAuthority - model.getTickDistance();
		
		if(remainingAuthority < 0) {
			remainingAuthority = 0;
		}
		stopDistance = model.antenna.getStopDistance();
		if(remainingAuthority <= stopDistance && model.getVelocity() != 0 && brakeStatus != true && eBrakeStatus != true ) {
			stopTrain(false);
		}
		else if (remainingAuthority > 0) {
			releaseServiceBrakes(false);
			releaseEmergencyBrakes(false);
		}
		return;
	}
	
	//REMOVE WHEN VITAL
	public void calculatePower() {
		
		velocityFeedback = model.getVelocity();
		
		velocityError[1] = targetVelocity - velocityFeedback;
		
		if(powerCommand[1] < MAX_POWER) {
			u[1] = u[0] + (TIME_PERIOD/2) * (velocityError[1] + velocityError[0]);
		}
		else {
			u[1] = u[0];
		}
		
		powerCommand[1] = Kp * velocityError[1] + Ki * u[1];
			
		u[0] = u[1];
		powerCommand[0] = powerCommand[1];
		velocityError[0] = velocityError[1];
		
		model.setPower(powerCommand[1]);
		
		return;
	}
	
	public void setTargetVelocity(double newVelocity) {
		targetVelocity = newVelocity;
	}
	
	public void stopTrain(boolean manual) {
		if(manual) {
			manualBrake = true;
		}
		model.setPower(0);
		//CHANGE WHEN VITAL
		powerCommand[0] = 0.0;
		//vc.resetPower();
		model.activateServiceBrakes();
		brakeStatus = true;
		return;
	}
	
	public void emergencyStop(boolean manual) {
		if(manual) {
			manualEBrake = true;
		}
		model.setPower(0);
		//CHANGE WHEN VITAL
		powerCommand[0] = 0.0;
		//vc.resetPower();
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
	}
	
	public void tick(long currentTime, int currentTemp) {
		//checkFailures();
		checkRemainingAuthority();
		setpointVelocity = model.getSetpointVelocity();
		if(mode == 1) {
			setTargetVelocity(setpointVelocity);
		}
		if(!brakeStatus && !eBrakeStatus && remainingAuthority != 0) {
			//CHANGE WHEN VITAL
			calculatePower();
			//model.setPower(vc.vitalPower(model.getVelocity(), model.getVelocity(), model.getVelocity()));
		}
		
		//controlSubsystems(currentTime, currentTemp);
		
	}
	
	
	
}
