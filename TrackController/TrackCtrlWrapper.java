package TrackController;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import TrackModel.*;
import Simulator.Simulator;

public class TrackCtrlWrapper {
	private static TrackController upish;
	private static TrackController downish;
	private static UI ui;
	private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	private Block map[];
	private int penis = 0;
	private Simulator simulator;
	private TrackModel myModel;
	ArrayList<Integer> present;

	
	public static void main (String[] args) {
		/*upish = new TrackController();
		downish = new TrackController();
		
		ui = new UI();
	
		ui.setvisible(true);
		
		final Runnable trainLoop = new Runnable() {
            public void run() {
            	upish.advanceTrain();
            	upish.checkSwitches();
            	upish.checkCrossing();
            	int pos = upish.getPosition();
            	ui.updatePosition(pos);
            	ui.updateSwitches();
            	ui.updateCrossing();
            }
        };
        final ScheduledFuture<?> beeperHandle = scheduler.scheduleAtFixedRate(trainLoop, 333, 333, MILLISECONDS);
        */
		
	}
	
	public TrackCtrlWrapper(Simulator sim) {
		simulator = sim;
		upish = new TrackController(simulator);
		downish = new TrackController(simulator);
		myModel = simulator.trackModel;

		
		getBlocks();
	}
	
	public Block[] getBlocks() {
		map = myModel.getBlocks();
		populateTrainmap();
		
		return map;
	}
	
	void populateTrainmap() {
		for (int i = 0; i < map.length; i++) {
			if (map[i].isTrainPresent()) {
				present.add(i);
			}
		}
	}
}