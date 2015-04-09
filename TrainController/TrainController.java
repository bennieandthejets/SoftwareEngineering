package TrainController;

import TrainModel.*;

@SuppressWarnings("unused")
public class TrainController {
		
	private final double	MPS_TO_MPH = 2.23694;		//Conversion ratio for meters per second to miles per hour

	private final int		MAX_POWER = 120000;			//Maximum power output (W)
	private final double	MAX_VELOCITY = 19.4444;		//Maximum train velocity (m/s)
//	private final double	BRAKE_DECEL = -1.2;			//Service brake deceleration (m/s^2)
//	private final double	E_BRAKE_DECEL = -2.73;		//Emergency brake deceleration (m/s^2)
	private final double	TIME_PERIOD = 1;			//Sample period of time (s)
	private	final double	Ki = 0.00000001;						//Integral Gain
	private	final double	Kp = 50;					//Proportional Gain
	
	private int			ID;
	private	int			mode;							//0 for automatic, 1 for manual		
	private	double		setpointVelocity;
	private	double		velocityFeedback;
	private double		targetVelocity;
	private	double[]	powerCommand = new double[2];	//Current[1] and most recent[0] power commands
	private double[]	u = new double[2];				//Intermediate variables
	private double[]	velocityError = new double[2];	//Current [1] and most recent [0] velocity velocityError
	private double		remainingAuthority;
	private double		stopDistance;
	public boolean		brakeStatus;
	public boolean		eBrakeStatus;
	
	private TrainModel			model;
	private TrainControllerUI	ui;
	
	
	public TrainController(int newID, TrainModel newTrainModel, TrainControllerUI newUI) {
		ID = newID;
		model = newTrainModel;
		ui = newUI;
		mode = 1;
		setpointVelocity = 0.0;
		velocityFeedback = 0.0;
		remainingAuthority = 0.0;
		powerCommand[0] = 0.0;
		powerCommand[1] = 0.0;
		velocityError[0] = 0.0;
		velocityError[1] = 0.0;
		u[0] = 0;
		u[1] = 0;
		brakeStatus = false;
		eBrakeStatus = false;
		
		//TEST
		remainingAuthority = 1000.0;
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
	
	//Determine the power command to send to the train model
	public void calculatePower() {
//		double confirmedPower;
		
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
		//confirmedPower = model.getPower();
		
		//if(powerCommand[1] != confirmedPower) {
			
		//}
		
		return;
	}
	
	public void setAuthority(double newAuthority) {
		remainingAuthority = newAuthority;
		return;
	}
	
	private void checkRemainingAuthority() {
		//remainingAuthority = remainingAuthority - model.getDistanceTraveled();
		stopDistance = model.antenna.getStopDistance();
		if(remainingAuthority <= stopDistance) {
			stopTrain();
		}
		return;
	}
		

	
	public void setTargetVelocity(double newVelocity) {
		targetVelocity = newVelocity;
	}
	
	public void stopTrain() {
		model.setPower(0);
		powerCommand[0] = 0;
		model.activateServiceBrakes();
		brakeStatus = true;
		return;
	}
	
	public void emergencyStop() {
		model.setPower(0);
		powerCommand[0] = 0;
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
			calculatePower();
		}
		
		//controlSubsystems(currentTime, currentTemp);
		
	}
	
	
	
}
