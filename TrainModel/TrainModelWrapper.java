package TrainModel;

import TrainController.*;
import java.util.ArrayList;
import Simulator.Simulator;


public class TrainModelWrapper{
	//ATTRIBUTES
	ArrayList<TrainModel> trains;
	Simulator sim;
	TrainControllerWrapper trainCtrlWrapper;
	
	public TrainModelWrapper(Simulator sim){
		this.sim = sim;
		this.trainCtrlWrapper = sim.trainControllerWrapper;
		trains = new ArrayList<TrainModel>();
	}
	
	//FUNCTIONS
	public void birthTrain(){
		int trainID = trains.size()+1;
		TrainModel newTrain = new TrainModel(trainID);
		trains.add(trainID,newTrain);
		trainCtrlWrapper.createTrainController(newTrain);
	}
	
	public void removeTrain(int trainID){
		trains.remove(trainID);
	}
	
	public TrainModel getTrain(int trainID){
		return trains.get(trainID);
	}
	
	public void showUI(){
		
	}
	
	public void tick(){
		for(int i = 1; i < trains.size()+1;i++){
			trains.get(i).updateTrain();
		}	
	}
}