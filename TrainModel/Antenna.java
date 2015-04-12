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
	
	public int getTrainID() {
		return train.getID();
	}
	
	//MBO Communication
	public Block getBlock(){
		return train.getBlock();
	}
	
	public double getVelocity(){
		return train.getVelocity();
	}
	
	public double getAuthority() {
		return train.getAuthority();
	}
	
	public void setSafeVelocity(double safeVel){
		train.setSafeVelocity(safeVel);
	}
	
	public void setSafeAuthority(double auth){
		train.setSafeAuthority(auth);
	}
	
	//Train Controller Communication
	public double getStopDistance(){
		return mbo.calculateStopDistance(getVelocity());
	}

}