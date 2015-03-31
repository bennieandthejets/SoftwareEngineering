package TrackModel;


public class Beacon {

	int radius;
	int blockID;
	boolean trainPresent;
	
	public Beacon(int blockID)
	{
		this.blockID = blockID;
	}
	
	public int getRadius()
	{
		return radius;
	}
	public boolean isTrainPresent()
	{
		return trainPresent;
	}
}
