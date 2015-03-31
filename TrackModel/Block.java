package com.BennieAndTheJets.TrackModel;


public class Block {
	
	int blockID;
	double blockSize;
	boolean trainPresent;
	boolean trackSwitch;
	boolean rrCrossing;
	boolean underground;
	String station;
	int trackTemp;
	double grade;
	double elevation;
	int power;
	int speedLimit;
	String directions;
	boolean beaconPresent;
	String lineColor;
	String section;
	double cumElevation;
	String switchPos;
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
	public boolean isTrackSwitch()
	{
		return trackSwitch;
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
	public int getPower()
	{
		return power;
	}
	public int getSpeedLimit()
	{
		return speedLimit;
	}
	public String getDirections()
	{
		return directions;
	}
	public boolean isBeacon()
	{
		return beaconPresent;
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
	public String getSwitchPos()
	{
		return switchPos;
	}
	public String getArrowDir()
	{
		return arrowDir;
	}
	public void setBlockID(int blockID)
	{
		this.blockID = blockID;
	}
	public void setBlockSize(double blockSize)
	{
		this.blockSize = blockSize;
	}
	public void setTrackSwitch(boolean trackSwitch)
	{
		this.trackSwitch = trackSwitch;
	}
	public void setRrCrossing(boolean rrCrossing)
	{
		this.rrCrossing = rrCrossing;
	}
	public void setUnderground(boolean underground)
	{
		this.underground = underground;
	}
	public void setStation(String station)
	{
		this.station = station;
	}
	public void setGrade(double grade)
	{
		this.grade = grade;
	}
	public void setElevation(double elevation)
	{
		this.elevation = elevation;
	}
	public void setSpeedLimit(int speedLimit)
	{
		this.speedLimit = speedLimit;
	}
	public void setDirections(String directions)
	{
		this.directions = directions;
	}
	public void setBeacon(boolean beaconPresent)
	{
		this.beaconPresent = beaconPresent;
	}
	public void setLineColor(String lineColor)
	{
		this.lineColor = lineColor;
	}
	public void setSection(String section)
	{
		this.section = section;
	}
	public void setCumElevation(double cumElevation)
	{
		this.cumElevation = cumElevation;
	}
	public void setSwitchPos(String switchPos)
	{
		this.switchPos = switchPos;
	}
	public void setArrowDir(String arrowDir)
	{
		this.arrowDir = arrowDir;
	}
}
