<<<<<<< HEAD
package MBO;
=======
package com.BennieAndTheJets.MBO;
>>>>>>> 6d2b9428abe1c66a9fea66ffc5d3acf5e93ea78c


public class TrainModel 
{
	private int trainID;
	private int setpoint;
	private int authority;
	
	public TrainModel(int trainID)
	{
		this.trainID = trainID;		
	}
	
	public int getTrainID()
	{
		return trainID;
	}
	
	public int getSetpoint()
	{
		return setpoint;
	}
	
	public int getAuthority()
	{
		return authority;
	}
}
