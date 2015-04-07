package TrackController;

import java.util.HashMap;
import TrackController.TrackController.Train;
import TrackModel.Block;

public interface PLC {
	int returnFive();
	void setSwitch(Block switchBlock, Block destBlock);
	boolean checkRoute(HashMap<Integer, Train> trains);
	void setCrossing(int crossingBlock);
}
