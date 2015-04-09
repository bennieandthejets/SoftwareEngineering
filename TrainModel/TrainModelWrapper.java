package TrainModel;

import TrainController.*;
import TrackModel.*;

import java.awt.EventQueue;
import java.util.ArrayList;

import Simulator.Simulator;


public class TrainModelWrapper{
	//ATTRIBUTES
	public ArrayList<TrainModel> trains;
	ArrayList<Antenna> antennas;
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
		antennas = new ArrayList<Antenna>();
		ui = new TrainModelUI();
	}
	
	//FUNCTIONS
	public int birthTrain(){
		int trainID = trains.size()+1;
		TrainModel newTrain = new TrainModel(trainID);
		Antenna newAntenna = new Antenna(newTrain,sim.trackModel,sim.mbo);
		newTrain.setAntenna(newAntenna);
		newTrain.setUI(ui);
		sim.trackModel.addTrain(newTrain);
		trains.add(trainID-1,newTrain);
		antennas.add(trainID-1,newAntenna);
		trainCtrlWrapper.createTrainController(newTrain);
		return trainID;
	}
	
	public void removeTrain(int trainID){
		trainID -= 1;
		trains.remove(trainID);
	}
	
	public TrainModel getTrain(int trainID){
		trainID -= 1;
		return trains.get(trainID);
	}
	
	public ArrayList<Antenna> getAllAntennas(){
		return antennas;
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
		int ID;
		TrainControllerWrapper trainConWrap = new TrainControllerWrapper();
		TrainModelWrapper trainModWrap = new TrainModelWrapper(trainConWrap);
		trainConWrap.showUI();
		ID = trainModWrap.birthTrain();
		
		while(true) {
			trainModWrap.tick();
			trainConWrap.tick();
			Thread.sleep(200);
		}
	}
}