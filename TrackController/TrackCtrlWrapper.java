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
	private Block[] map;
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
		present = new HashSet<>();
		trains = new HashMap<>();		
	}
	
	public Block[] getBlocks() {
		map = myModel.getBlocks();
		populateTrainmap();
		
		return map;
	}
	
	public void tick() {
		getBlocks();
		
		//upish.ui.panel.repaint();
		
		int[] trainlocs = simulator.ctc.locations;
		for (int trainID = 0; trainID < trainlocs.length; trainID++) {
			//If trainlocs[trainid] is -1, there is no train there!
			if (trainlocs[trainID] == -1) {
				continue;
			}
			//If there is already a train with this ID being tracked, update its location
			Train trackedTrain = trains.get(trainID);
			if (trackedTrain != null) {
				trackedTrain.oldposition = trackedTrain.position;
				trackedTrain.position = trainlocs[trainID];
				
				upish.ui.updatePosition(trainID, trainlocs[trainID]);
				downish.ui.updatePosition(trainID, trainlocs[trainID]);

			}
			//If there is not a train already with this ID, add one
			else {
				trains.put(trainID, new Train(trainlocs[trainID]));
				upish.ui.addTrain(trainID, trainlocs[trainID]);
				downish.ui.addTrain(trainID, trainlocs[trainID]);
			}
		}
		if (trains.size() != 0) {
			upish.tick(trains, map);
			downish.tick(trains, map);
		}
	}
	
	public void setRoute(int trainBlock, int destination, double suggestedSpeed, int suggestedAuthority, int[] route) {
		Train train = null;
		Iterator it = trains.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			train = (Train) pair.getValue();
			
			if (train.position == trainBlock) 
				break;
		}
		if (train == null) {
			System.out.println("No train at block " + trainBlock);
		}
		
		
		train.destination = destination;
		train.sugSpeed = suggestedSpeed;
		train.sugAuthority = suggestedAuthority;
		train.suggestedRoute = route;
		
		myModel.setAuthority((double) suggestedAuthority);
		int routeStep;
		for (routeStep = 0; routeStep < train.suggestedRoute.length; routeStep++) {
			if (train.suggestedRoute[routeStep] == train.position) {
				break;
			}
		}
		
		/*double currentLimit = map[train.suggestedRoute[routeStep]].getSpeedLimit();
		double nextLimit = map[train.suggestedRoute[routeStep+1]].getSpeedLimit();
		double thirdLimit = map[train.suggestedRoute[routeStep+2]].getSpeedLimit();
		double safespeed = Math.min(suggestedSpeed, currentLimit);
		safespeed = Math.min(safespeed, nextLimit);
		safespeed = Math.min(safespeed, thirdLimit);
		
		myModel.setSpeed(safespeed);*/
		myModel.setSpeed(suggestedSpeed);
	}
	
	void populateTrainmap() {
		present.clear();
		for (int i = 1; i < map.length; i++) {
			if (map[i].isTrainPresent()) {
				present.add(i);
			}
		}
	}
	
	public void showUI() {
		upish.showUI();
		downish.showUI();
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