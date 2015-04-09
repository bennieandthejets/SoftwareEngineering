package TrackController;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import TrackModel.*;
import Simulator.Simulator;

public class TrackCtrlWrapper {
	private static TrackController upish;
	private static TrackController downish;
	//private static UI ui;
	private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	private Block map[];
	private int penis = 0;
	private Simulator simulator;
	private TrackModel myModel;
	HashSet<Integer> present;
	HashMap<Integer, Train> trains;

	
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
		upish = new TrackController(simulator,1);
		downish = new TrackController(simulator,2);
		myModel = simulator.trackModel;

		
		getBlocks();
	}
	
	public void getBlocks() {
		map = myModel.getBlocks();
		populateTrainmap();
	}
	
	public void tick() {
		getBlocks();
		
		int[] trainlocs = simulator.ctc.locations;
		for (int trainID = 0; trainID < trainlocs.length; trainID++) {
			//If there is already a train with this ID being tracked, update its location
			Train trackedTrain = trains.get(trainID);
			if (trackedTrain != null) {
				trackedTrain.oldposition = trackedTrain.position;
				trackedTrain.position = trainlocs[trainID];
			}
			//If there is not a train already with this ID, add one
			else {
				trains.put(trainID, new Train(trainlocs[trainID]));
			}
		}
		
		upish.tick(trains, map);
		downish.tick(trains, map);
	}
	
	void populateTrainmap() {
		present.clear();
		for (int i = 0; i < map.length; i++) {
			if (map[i].isTrainPresent()) {
				present.add(i);
			}
		}
	}
	
	public class Train {
		int oldposition;
		int position;
		int destination;
		double speed;
		double sugSpeed;
		int sugAuthority;
		int[] suggestedRoute;
		
		int nextSwitch;
		int blockBefore;
		int blockAfter;
		
		Train(int pos, int dest) {
			position = pos;
			destination = dest;
			speed = 0;
		}
		Train(int pos) {
			position = pos;
			speed = 0;
		}
	}
}