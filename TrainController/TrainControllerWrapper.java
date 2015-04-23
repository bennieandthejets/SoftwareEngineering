package TrainController;

import java.awt.EventQueue;

import java.util.*;

import Simulator.*;
import TrainModel.*;

@SuppressWarnings("unused")
public class TrainControllerWrapper {
	
	private Simulator simulator;
	private long systemTime;
	private int temperature;
	private ArrayList<TrainController> controllers;
	private TrainControllerUI ui;

	public TrainControllerWrapper() {
		controllers = new ArrayList<TrainController>();
		ui = new TrainControllerUI(this);
	}
	
	public TrainControllerWrapper(Simulator newSimulator) {
		controllers = new ArrayList<TrainController>();
		simulator = newSimulator;
		ui = new TrainControllerUI(this);
	}
	
	public TrainController getTrainController(int targetID) {
		return controllers.get(targetID - 1);
	}
	
	public void createTrainController(TrainModel newModel) {
		int nextID = controllers.size() + 1;
		controllers.add(new TrainController(nextID, newModel, ui));
		ui.addTrain(nextID);
	}
	
	
	
	public void tick(long time) {
		for(int i = 0; i < controllers.size(); i++) {
			controllers.get(i).tick(time, 0);
		}
		ui.update();
	}
	
	public void showUI() {
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
	
	public static void main(String[] args) {
		TrainControllerWrapper wrapper = new TrainControllerWrapper();
		wrapper.showUI();
	}
}