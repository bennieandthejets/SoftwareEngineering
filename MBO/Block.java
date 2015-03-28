package com.BennieAndTheJets.MBO;


public class Block {
	public int id;				// block id
	public int length;			// length in feet
	public boolean hasStation;	// whether or not a station is on the block
	public String stationName;	// The name of the station
	public int dwellTime;		// Length of time waiting at stations in seconds
	
	public Block(int id, int length, boolean hasStation, String stationName, int dwellTime) {
		this.id = id;
		this.length = length;
		this.hasStation = hasStation;
		this.stationName = stationName;
		this.dwellTime = dwellTime;
	}
}
