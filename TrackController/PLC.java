package TrackController;

import java.util.HashMap;

import TrackController.TrackCtrlWrapper.Train;
import TrackModel.Block;
import TrackModel.TrackModel;

public interface PLC {
	int returnFive();
	void setSwitch(Block switchBlock, Block destBlock);
	int checkRoutes(HashMap<Integer, Train> trains, UI ui);
	public void addSwitches(Block[] map, UI ui);
	void setCrossing(int crossingBlock);
	boolean checkSwitches(Block[] map, HashMap<Integer, Train> trains, UI ui);
	int getSafeSpeed(HashMap<Integer, Train> trains, Block[] map, TrackModel trackmodel);
	void checkTrainEmergency(Block[] map, HashMap<Integer, Train> trains, TrackModel trackmodel);
	public void setRegions(HashMap<Integer, Train> trains);
}
