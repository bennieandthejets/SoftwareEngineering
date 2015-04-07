package TrackModel;

public class Beacon {

	int radius;
	int blockID;
	double blockSize;
	boolean trainPresent;
	
	//TODO ALL OF BEACON SOS
	
	public Beacon(int blockID)
	{
		this.blockID = blockID;
		TrackModel t = new TrackModel();
		Block[] blocks = t.getBlocks();
		this.blockSize = blocks[blockID].blockSize;
	}
	
	public int getRadius()
	{
		return radius;
	}
	
	public boolean isTrainPresent()
	{
		return trainPresent;
	}
	
	public int distance()
	{
		//search for train three blocks away
		return (int) blockSize*3;
	}
	
	public void searchForTrain()
	{
		//SEARCH DISTANCE FOR TRAIN
		//USES WHERE TRAIN WAS/SPEED TO FIND WHERE TRAIN IS NOW
		//CHANGE TRAINPRESENT VARIABLE
	}
}
