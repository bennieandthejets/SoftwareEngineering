package TrainModel;

import TrainController.*;
import java.util.ArrayList;
import Simulator.Simulator;


public class TrainModelWrapper{
	//ATTRIBUTES
	ArrayList<TrainModel> trains = new ArrayList<TrainModel>();
	Simulator simulator;
	
	//FUNCTIONS
	public void birthTrain(){
		int trainID = trains.size()+1;
		TrainModel newTrain = new TrainModel(trainID);
		trains.add(trainID,newTrain);
	}
	
	public void removeTrain(int trainID){
		trains.remove(trainID);
	}
	
	public TrainModel getTrain(int trainID){
		return trains.get(trainID);
	}
	
	public void tick(){
		
	}
}