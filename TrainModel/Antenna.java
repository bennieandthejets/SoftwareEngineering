package TrainModel;

import TrackModel.*;
import MBO.MBO;

public class Antenna{
	//ATTRIBUTES
	private TrainModel train;
	private TrackModel track;
	private MBO mbo;
	
	//FUNCTIONS
	public Antenna(TrainModel train, TrackModel track, MBO mbo){
		this.train = train;
		this.track = track;
		this.mbo = mbo;
	}
	
	//Beacon Communication
	public void sendStationInfo(double distance, String stationName, String stationSide){
		
	}
	
	//MBO Communication
	public Block getBlock(){
		return train.getBlock();
	}
	
	public double getVelocity(){
		return train.getVelocity();
	}
	
	public void setSafeVelocity(double safeVel){
		train.setSafeVelocity(safeVel);
	}
	
	public void setSafeAuthority(double auth){
		train.setSafeAuthority(auth);
	}

}