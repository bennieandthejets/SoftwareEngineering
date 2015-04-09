package TrackModel;

import Simulator.Simulator;

public class Beacon {

	int blockID;
	boolean trainNear;
	Block[] blocks;
	String station;
	String side;
	Simulator s;
	boolean fromNorth;
	boolean fromSouth;
		
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
		if(trainNear)
		{
			if(fromNorth && blocks[blockID].stationSide.equals("right"))
			{
				side = "left";
			}
			else if(fromNorth && blocks[blockID].stationSide.equals("left"))
			{
				side = "right";
			}
			else if(fromSouth && blocks[blockID].stationSide.equals("right"))
			{
				side = "right";
			}
			else if(fromSouth && blocks[blockID].stationSide.equals("left"))
			{
				side = "left";
			}
			else
				side = null;
		}
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
		//searches for train from three blocks away
		//TODO search based on distance rather than blocks
		if(blocks[blockID+3].trainPresent)
		{
			trainNear = true;
			fromNorth = false;
			fromSouth = true;
		}
		else if(blocks[blockID+2].trainPresent)
		{
			trainNear = true;
			fromNorth = false;
			fromSouth = true;
		}
		else if(blocks[blockID+1].trainPresent)
		{
			trainNear = true;
			fromNorth = false;
			fromSouth = true;
		}
		else if(blocks[blockID].trainPresent)
		{
			trainNear = true;
			fromNorth = false;
			fromSouth = false;
		}
		else if(blocks[blockID+1].trainPresent)
		{
			trainNear = true;
			fromNorth = true;
			fromSouth = false;
		}
		else if(blocks[blockID+2].trainPresent)
		{
			trainNear = true;
			fromNorth = true;
			fromSouth = false;
		}
		else if(blocks[blockID+3].trainPresent)
		{
			trainNear = true;
			fromNorth = true;
			fromSouth = false;
		}
		else
		{
			trainNear = false;
			fromNorth = false;
			fromSouth = false;
		}
	}
}
