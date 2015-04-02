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
	
	public int distance(int radius)
	{
		//CALCULATE DISTANCE TO SEARCH
		return 0;
	}
	
	public void searchForTrain()
	{
		//SEARCH DISTANCE FOR TRAIN
		//USES WHERE TRAIN WAS/SPEED TO FIND WHERE TRAIN IS NOW
		//CHANGE TRAINPRESENT VARIABLE
	}
}
