package TrackModel;

import Simulator.Simulator;

public class Beacon {

	int blockID;
	boolean trainNear;
	Block[] blocks;
	String station;
	String side;
	Simulator s;
		
	public Beacon(int blockID, TrackModel t, Simulator s)
	{
		this.blockID = blockID;
		blocks = t.getBlocks();
		this.station = blocks[blockID].station;
		this.s = s;
	}
	
	public void tick()
	{
		searchForTrain();
		findSide();
	}
	
	public void findSide()
	{
		//when trainnear is true, find which block train is seen on first
		//UPDATE CSV FILES to see which side of track station is on
		//if train is coming from north and station is on right of track, side is left
		//if train is coming from north and station is on left of track, side is right
		//if train is coming from south and station is on right of track, side is right
		//if train is coming from south and station is on left of track, side is on left
	}
	
	public String getSide()
	{
		return side;
	}
	
	public String getStation()
	{
		return station;
	}
	
	public boolean isTrainNear()
	{
		return trainNear;
	}
	
	public void searchForTrain()
	{
		//SEARCH DISTANCE FOR TRAIN
		//USES WHERE TRAIN WAS/SPEED TO FIND WHERE TRAIN IS NOW
		//CHANGE TRAINPRESENT VARIABLE
		if(blocks[blockID+3].trainPresent)
			trainNear = true;
		else if(blocks[blockID+2].trainPresent)
			trainNear = true;
		else if(blocks[blockID+1].trainPresent)
			trainNear = true;
		else if(blocks[blockID].trainPresent)
			trainNear = true;
		else if(blocks[blockID+1].trainPresent)
			trainNear = true;
		else if(blocks[blockID+2].trainPresent)
			trainNear = true;
		else if(blocks[blockID+3].trainPresent)
			trainNear = true;
		else
			trainNear = false;
	}
}
