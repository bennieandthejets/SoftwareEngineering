package TrainController;

import java.awt.EventQueue;
import java.util.*;
import Simulator.*;

public class TrainControllerWrapper {
	
	private Simulator simulator;
	private long systemTime;
	private int temperature;
	private ArrayList<TrainController> controllers;
	
	public TrainControllerWrapper(Simulator newSimulator) {
		controllers = new ArrayList<TrainController>();
	}
	
	public TrainController getTrainController(int targetID) {
		return controllers.get(targetID);
	}
	
	public void createTrainController(TrainModel newModel) {
		int nextID = controllers.size() + 1;
		controllers.add(new TrainController(nextID, newModel));
	}
	
	
	
	public void tick() {
		this.temperature = simulator.getTemp();
		this.systemTime = simulator.getTime();
		for(int i = 1; i < controllers.size() + 1; i++) {
			controllers.get(i).tick(temperature, systemTime);
		}
	}
	
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TrainControllerUI UI = new TrainControllerUI(controller);
					UI.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}