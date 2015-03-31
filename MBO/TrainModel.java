package MBO;


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
