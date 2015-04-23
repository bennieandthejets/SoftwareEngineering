package TrackModel;

import Simulator.Simulator;
import TrainModel.Antenna;
import TrainModel.TrainModel;

public class Beacon {

	int blockID;
	int trainID;
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
	
	public void tellAntenna()
	{
		if(trainNear)
		{
			Antenna antenna = s.trainModelWrapper.getTrain(trainID).antenna;
			antenna.trainNear(trainNear, trainID, station, side);
		}
	}
	
	public void searchForTrain()
	{
		//searches for train from three blocks away
		if(blocks[blockID+3].trainPresent)
		{
			trainID = blocks[blockID+3].trainID;
			trainNear = true;
			fromNorth = false;
			fromSouth = true;
		}
		else if(blocks[blockID+2].trainPresent)
		{
			trainID = blocks[blockID+2].trainID;
			trainNear = true;
			fromNorth = false;
			fromSouth = true;
		}
		else if(blocks[blockID+1].trainPresent)
		{
			trainID = blocks[blockID+1].trainID;
			trainNear = true;
			fromNorth = false;
			fromSouth = true;
		}
		else if(blocks[blockID].trainPresent)
		{
			trainID = blocks[blockID].trainID;
			trainNear = true;
			fromNorth = false;
			fromSouth = false;
		}
		else if(blocks[blockID-1].trainPresent)
		{
			trainID = blocks[blockID-1].trainID;
			trainNear = true;
			fromNorth = true;
			fromSouth = false;
		}
		else if(blocks[blockID-2].trainPresent)
		{
			trainID = blocks[blockID-2].trainID;
			trainNear = true;
			fromNorth = true;
			fromSouth = false;
		}
		else if(blocks[blockID-3].trainPresent)
		{
			trainID = blocks[blockID-3].trainID;
			trainNear = true;
			fromNorth = true;
			fromSouth = false;
		}
		else
		{
			trainID = -1;
			trainNear = false;
			fromNorth = false;
			fromSouth = false;
		}
	}
}
