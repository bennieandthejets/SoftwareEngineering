package TrainModel;

//import TrainModelUIProto;

import java.text.DecimalFormat;

public class TrainModel{
	/* All units for calculation will be kept in metric
	 * Whenever they need to be displayed, they will be
	 * converted to imperial */
	DecimalFormat SigFig = new DecimalFormat("0.00");	// For reducing sigfigs
	
	//ATTRIBUTES
	private TrainModelUIProto ui;
	
	//FUNCTIONS
	public TrainModel(int trainID){
		
		while(true){
			//this.ui = ui;
			//calcTotalMass();
			//calcAcceleration();
			//calcVelocity();
			//setTxtFields();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
		
}