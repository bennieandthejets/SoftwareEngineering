package TrainController;

import TrainModel.*;

public class VitalControl {
	
	private final int		MAX_POWER = 120000;			//Maximum power output (W)
	private final double	MAX_VELOCITY = 19.4444;		//Maximum train velocity (m/s)
	private final double	TIME_PERIOD = 1;			//Sample period of time (s)
	private	final double	Ki = 0.00000001;			//Integral Gain
	private	final double	Kp = 50;					//Proportional Gain
	private final double	MIN_VARIANCE = .01;			//Minimum acceptable variance
	
	private	double[]	newPowerCommand = new double[3];//Next power command
	private double		prevPowerCommand;
	private double[]	u = new double[2];				//Intermediate variables
	private double[]	velocityError = new double[2];	//Current [1] and most recent [0] velocity velocityError
	private double		targetVelocity;
	
	public VitalControl() {
		//initialize variables
		newPowerCommand[0] = 0.0;
		newPowerCommand[1] = 0.0;
		newPowerCommand[2] = 0.0;
		prevPowerCommand = 0.0;
		u[0] = 0.0;
		u[1] = 0.0;
		velocityError[0] = 0.0;
		velocityError[1] = 0.0;
	}
	
	public void setTargetVelocity(double newTargetVelocity) {
		targetVelocity = newTargetVelocity;
		return;
	}
	
	public void resetPower() {
		prevPowerCommand = 0.0;
		return;
	}
	
	public double vitalPower(double velocity1, double velocity2, double velocity3) {
		newPowerCommand[0] = calculatePower(velocity1, 0);
		newPowerCommand[1] = calculatePower(velocity2, 0);
		newPowerCommand[2] = calculatePower(velocity3, 0);
		
		if(calculateVariance(newPowerCommand[0], newPowerCommand[1]) > MIN_VARIANCE) {
			return -1;
		}
		if(calculateVariance(newPowerCommand[0], newPowerCommand[2]) > MIN_VARIANCE) {
			return -1;
		}
		if(calculateVariance(newPowerCommand[1], newPowerCommand[2]) > MIN_VARIANCE) {
			return -1;
		}
		
		else { 
			u[0] = u[1];
			prevPowerCommand = (newPowerCommand[0] + newPowerCommand[1] + newPowerCommand[2]) / 3;
			velocityError[0] = velocityError[1];
			return prevPowerCommand;
		}

	}
	
	//Determine the power command to send to the train model
	private double calculatePower(double velocityFeedback, int i) {
		
		velocityError[1] = targetVelocity - velocityFeedback;
		
		if(powerCommand[i][1] < MAX_POWER) {
			u[1] = u[0] + (TIME_PERIOD/2) * (velocityError[1] + velocityError[0]);
		}
		else {
			u[1] = u[0];
		}
		
		powerCommand[1] = Kp * velocityError[i][1] + Ki * u[1];
			
		
		
		return powerCommand[1];
	}
	
	private double calculateVariance(double x, double y) {
		return Math.abs((x-y)/x);
	}
}
