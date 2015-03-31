<<<<<<< HEAD
package TrackModel;
=======
package com.BennieAndTheJets.TrackModel;
>>>>>>> 6d2b9428abe1c66a9fea66ffc5d3acf5e93ea78c


public class TrackModel {

	Block[] blocks;
	
	
	public void breakTrack(int block)
	{
		
	}
	
	public void circuitFail(int block)
	{
		
	}
	
	public void powerFail(int block)
	{
		
	}
	
	public boolean trainPresent(int block)
	{
		
		return false;
	}
	
	public String sideOfTrain(Beacon b)
	{
		//return which side of the station the train appears on
		
		return null;
	}
	
	public boolean trainNearBeacon(Beacon b)
	{
		//return true if beacon sees a train in radius
		//get radius from beacon object
		//use dist formula to see if train is near
		//set beacon object to know train present
		
		return false;
	}
	
}
