package TrackController;

import java.util.HashMap;

import TrackController.TrackController.Train;
import TrackModel.Block;

public class testPLC implements PLC{
	public int returnFive() {
		return 5;
	}
	public void setSwitch(Block switchBlock, Block destBlock){
	
	}
	public boolean checkRoute(HashMap<Integer, Train> trains) {
		return false;
	}
	public void setCrossing(int crossingBlock) {
		
	}
}
