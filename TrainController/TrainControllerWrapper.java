package TrainController;


import java.awt.EventQueue;
import java.util.*;
import Simulator.*;

public class TrainControllerWrapper {
	
	private long systemTime;
	private int temperature;
	private ArrayList<TrainController> controllers = new ArrayList<TrainController>();
	
	public TrainControllerWrapper() {
		
	}
	
	public TrainController getTrainController(int targetID) {
		return controllers.get(targetID);
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
	
	public void tick() {
		this.temperature = Simulator.getTemp();
		this.systemTime = Simulator.getTime();
		for(int i = 1; i < controllers.size() + 1; i++) {
			
		}
	}
}