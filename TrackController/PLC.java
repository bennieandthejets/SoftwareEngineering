package TrackController;

import java.util.HashMap;

import TrackController.TrackCtrlWrapper.Train;
import TrackModel.Block;

public interface PLC {
	int returnFive();
	void setSwitch(Block switchBlock, Block destBlock);
	boolean checkRoutes(HashMap<Integer, Train> trains);
	void setCrossing(int crossingBlock);
	boolean checkSwitches(Block[] map, HashMap<Integer, Train> trains);
}
