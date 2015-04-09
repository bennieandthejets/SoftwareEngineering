package TrainModel;

import TrainController.*;

import java.awt.EventQueue;
import java.util.ArrayList;

import Simulator.Simulator;


public class TrainModelWrapper{
	//ATTRIBUTES
	ArrayList<TrainModel> trains;
	Simulator sim;
	TrainControllerWrapper trainCtrlWrapper;
	TrainModelUI ui;
	
	public TrainModelWrapper(TrainControllerWrapper trainCtrlWrapper){
		this.trainCtrlWrapper = trainCtrlWrapper;
		trains = new ArrayList<TrainModel>();
		ui = new TrainModelUI();
	}
	
	public TrainModelWrapper(Simulator sim){
		this.sim = sim;
		this.trainCtrlWrapper = sim.trainControllerWrapper;
		trains = new ArrayList<TrainModel>();
		ui = new TrainModelUI();
	}
	
	//FUNCTIONS
	public void birthTrain(){
		int trainID = trains.size();
		TrainModel newTrain = new TrainModel(trainID);
		trains.add(trainID,newTrain);
		trainCtrlWrapper.createTrainController(newTrain);
	}
	
	public void removeTrain(int trainID){
		trainID -= 1;
		trains.remove(trainID);
	}
	
	public TrainModel getTrain(int trainID){
		trainID -= 1;
		return trains.get(trainID);
	}
	
	public void showUI(){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ui.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void tick(){
		for(int i = 0; i < trains.size();i++){
			trains.get(i).updateTrain();
		}	
	}
	
	public static void main(String[] args) throws InterruptedException{
		//double setpoint = Double.parseDouble(args[0]);
		TrainControllerWrapper trainConWrap = new TrainControllerWrapper();
		TrainModelWrapper trainModWrap = new TrainModelWrapper(trainConWrap);
		trainConWrap.showUI();
		trainModWrap.birthTrain();
		
		while(true) {
			trainModWrap.tick();
			trainConWrap.tick();
			Thread.sleep(200);
		}
	}
}