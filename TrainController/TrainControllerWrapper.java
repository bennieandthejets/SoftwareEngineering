package com.BennieAndTheJets.TrainController;


import java.awt.EventQueue;

public class TrainControllerWrapper {
	public static void main(String[] args) {
	
		//TrainModel model = new TrainModel();
		final TrainController controller = new TrainController();
		
		
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