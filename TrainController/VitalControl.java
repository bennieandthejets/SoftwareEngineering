package TrainController;

import TrainModel.*;

@SuppressWarnings("unused")
public class VitalControl {
	
	private final int		MAX_POWER = 120000;				//Maximum power output (W)
	
	private final double	MAX_VELOCITY = 19.4444;			//Maximum train velocity (m/s)
	private final double	TIME_PERIOD = 1;				//Sample period of time (s)
	private	final double	Ki = 0.00000001;				//Integral Gain
	private	final double	Kp = 50;						//Proportional Gain
	private final double	MIN_VARIANCE = .01;				//Minimum acceptable variance
	
	private TrainController tc;
	
	private double		targetVelocity;
	private	double[]	newPowerCommand = {0.0, 0.0, 0.0};	//Next power command
	private double		prevPowerCommand;
	private double[]	newU = {0.0, 0.0, 0.0};				//Intermediate variables
	private double		prevU;
	private double[]	newVelocityError = {0.0, 0.0, 0.0};	//Current [1] and most recent [0] velocity velocityError
	private double		prevVelocityError;
	
	
	public VitalControl(TrainController newTrainController) {
		//initialize variables
		tc = newTrainController;
		prevPowerCommand = 0.0; 
		prevU = 0.0;
		prevVelocityError = 0.0;

	}
	
	public void setTargetVelocity(double newTargetVelocity) {
		targetVelocity = newTargetVelocity;
		return;
	}
	
	public void resetPower() {
		prevPowerCommand = 0.0;
		return;
	}
	
	public void vitalPower(double velocity1, double velocity2, double velocity3) {
		newPowerCommand[0] = calculatePower(velocity1, 0);
		newPowerCommand[1] = calculatePower(velocity2, 1);
		newPowerCommand[2] = calculatePower(velocity3, 2);
		
		if(calculateVariance(newPowerCommand[0], newPowerCommand[1]) > MIN_VARIANCE) {
			tc.emergencyStop(false);
			return;
		}
		else if(calculateVariance(newPowerCommand[0], newPowerCommand[2]) > MIN_VARIANCE) {
			tc.emergencyStop(false);
			return;
		}
		else if(calculateVariance(newPowerCommand[1], newPowerCommand[2]) > MIN_VARIANCE) {
			tc.emergencyStop(false);
			return;
		}
		else { 
			prevU = (newU[0] + newU[1] + newU[0]) / 3;
			prevPowerCommand = (newPowerCommand[0] + newPowerCommand[1] + newPowerCommand[2]) / 3;
			prevVelocityError = (newVelocityError[0] + newVelocityError[1] + newVelocityError[0]) / 3;
			tc.sendPower(prevPowerCommand);
			return;
		}

	}
	
	//Determine the power command to send to the train model
	private double calculatePower(double velocityFeedback, int i) {
		
		double powerCommand;
		
		newVelocityError[i] = targetVelocity - velocityFeedback;
		
		if(newPowerCommand[i] < MAX_POWER) {
			newU[i] = prevU + (TIME_PERIOD/2) * (newVelocityError[i] + prevVelocityError);
		}
		else {
			newU[i] = prevU;
		}
		
		powerCommand = Kp * newVelocityError[i] + Ki * newU[i];
		
		return powerCommand;
	}
	
	private double calculateVariance(double x, double y) {
		return Math.abs((x-y)/x);
	}
}
