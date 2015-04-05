package TrainController;
import TrainModel.*;;

public class TrainController {
		
	
	private final int		MAX_POWER = 120000;			//Maximum power output (W)
	private final double	MAX_VELOCITY = 70.0;		//Maximum train velocity (km/h)
	private final double	BRAKE_DECEL = -1.2;			//Service brake deceleration (m/s^2)
	private final double	E_BRAKE_DECEL = -2.73;		//Emergency brake deceleration (m/s^2)
	private final double	TIME_PERIOD = 1;			//Sample period of time (s)
	private	final double	Ki = 1.0;					//Integral Gain
	private	final double	Kp = 12;					//Proportional Gain
	
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
	
	private TrainModel 	model;
	private TrainControllerUI ui;
	
	
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
	}
	
	public int getID() {
		return this.ID;
	}
	
	//Determine the power command to send to the train model
	public void calculatePower() {
		double confirmedPower;
		setTargetVelocity();
		
		velocityFeedback = model.getVelocity();
		
		velocityError[1] = targetVelocity - velocityFeedback;
		
		if(powerCommand[1] < MAX_POWER) {
			u[1] = u[0] + (TIME_PERIOD/2) * (velocityError[1] + velocityError[0]);
		}
		else {
			u[1] = u[0];
		}
		
		powerCommand[1] = Ki * velocityError[1] + Kp * u[1];
		
		if(powerCommand[1] > 0) {
			powerCommand[1] = 0;
		}
			
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
	
	private double calculateRemainingAuthority() {
		//remainingAuthority = remainingAuthority - model.getDistanceTravelled();
		//stopDistance = model.getStopDistance();
		if(remainingAuthority <= stopDistance) {
			stopTrain();
		}
		return 0.0;
	}
		
	public double getVelocityFeedback() {
		return model.getVelocity();
	}
	
	public double setSetpointVelocity(double newSetpointVelocity) {
		setpointVelocity = newSetpointVelocity;
		if(mode == 1) {
			targetVelocity = setpointVelocity;
		}
		return setpointVelocity;
	}
	
	public void setTargetVelocity() {
		if(mode == 1) {
			//manualVelocity = 
			
			//if(newTargetVelocity > setpointVelocity)
				targetVelocity = setpointVelocity;
			//else
				//targetVelocity = newTargetVelocity;
		}
		else {
			targetVelocity = setpointVelocity;
		}
	}
	
	private void stopTrain() {
		model.setPower(0);
		powerCommand[0] = 0;
		model.activateServiceBrakes();
		return;
		
	}
	
	private void emergencyStop() {
		model.setPower(0);
		powerCommand[0] = 0;
		model.activateEmergencyBrakes();
		return;
	}
	
	public void tick(long currentTime, int currentTemp) {
		//checkFaults();
		calculateRemainingAuthority();
		calculatePower();
		
		//controlSubsystems(currentTime, currentTemp);
		
		
		
	}
	
}
