<<<<<<< HEAD
package TrackModel;
=======
package com.BennieAndTheJets.TrackModel;
>>>>>>> 6d2b9428abe1c66a9fea66ffc5d3acf5e93ea78c


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
