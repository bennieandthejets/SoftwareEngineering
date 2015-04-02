package TrackModel;

public class Block {
	
	int blockID;
	double blockSize;
	boolean trainPresent;
	boolean isBroken;
	boolean isClosed;
	boolean isStation;
	Switch sw;
	Beacon beacon;
	boolean rrCrossing;
	boolean underground;
	String station;
	int trackTemp;
	double grade;
	double elevation;
	int speedLimit;
	String[] directions;
	String lineColor;
	String section;
	double cumElevation;
	//TODO GET RID OF ARROWDIR WHAT EVEN IS THIS
	String arrowDir;
	
	public Block(int blockID)
	{
		this.blockID = blockID;
	}
	
	public int getBlockID()
	{
		return blockID;
	}
	public double getBlockSize()
	{
		return blockSize;
	}
	public boolean isTrainPresent()
	{
		return trainPresent;
	}
	public boolean isRrCrossing()
	{
		return rrCrossing;
	}
	public boolean isUnderground()
	{
		return underground;
	}
	public String getStation()
	{
		return station;
	}
	public double getGrade()
	{
		return grade;
	}
	public double getElevation()
	{
		return elevation;
	}
	public int getSpeedLimit()
	{
		return speedLimit;
	}
	public String getLineColor()
	{
		return lineColor;
	}
	public String getSection()
	{
		return section;
	}
	public double getCumElevation()
	{
		return cumElevation;
	}
	public Switch getSwitch()
	{
		return sw;
	}
	public Beacon getBeacon()
	{
		return beacon;
	}

}
