package TrainController;


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
	private double[]	error = new double[2];			//Current [1] and most recent [0] velocity error
	
	//private TrainModel 	model;
	
	
	public TrainController() {
		//model = trainModel;
		ID = 0;
		mode = 1;
		setpointVelocity = 0.0;
		velocityFeedback = 0.0;
		powerCommand[0] = 0.0;
		powerCommand[1] = 0.0;
		error[0] = 0.0;
		error[1] = 0.0;
		u[0] = 0;
		u[1] = 0;
	}
	
	public int getID() {
		return ID;
	}
	
	public double calculatePower() {
		//velocityFeedback = 
		error[1] = setpointVelocity - velocityFeedback;
		
		if(powerCommand[1] < MAX_POWER) {
			u[1] = u[0] + (TIME_PERIOD/2) * (error[1] + error[0]);
		}
		else 
			u[1] = u[0];
		
		powerCommand[1] = Ki * error[1] + Kp * u[1];
		
		u[0] = u[1];
		powerCommand[0] = powerCommand[1];
		error[0] = error[1];
		return powerCommand[1];
	}
		
	//public double getVelocityFeedback() {
		//return model.getVelocity();
	//}
	
	public double setSetpointVelocity(double stptVel) {
		setpointVelocity = stptVel;
		if(mode == 1) {
			targetVelocity = setpointVelocity;
		}
		return setpointVelocity;
	}
	
	public double setVelocityFeedback(double velFdbk) {
		velocityFeedback = velFdbk;
		return velocityFeedback;
	}
	
	public void setTargetVelocity() {
		targetVelocity = setpointVelocity;
	}
	
	public void setTargetVelocity(double tgtVel)
	{
		if(mode == 1) {
			if(tgtVel > setpointVelocity)
				targetVelocity = setpointVelocity;
			else
				targetVelocity = tgtVel;
		}
		else
			targetVelocity = setpointVelocity;
	}
	
}
