package TrainModel;

import TrackModel.*;
import MBO.MBO;

public class Antenna{
	//ATTRIBUTES
	private TrainModel train;
	private TrackModel track;
	private MBO mbo;
	public boolean trainNear;
	public int trainID;
	public String stationName;
	public String stationSide;
	
	//FUNCTIONS
	public Antenna(TrainModel train, TrackModel track, MBO mbo){
		this.train = train;
		this.track = track;
		this.mbo = mbo;
	}
	
	//Beacon Communication
	public void trainNear(boolean trainNear, int trainID, String stationName, String stationSide){
		this.trainNear = trainNear;
		this.trainID = trainID;
		this.stationName = stationName;
		this.stationSide = stationSide;
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