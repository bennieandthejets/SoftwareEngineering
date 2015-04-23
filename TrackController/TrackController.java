package TrackController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.net.URLClassLoader;
import java.io.*;

import Simulator.*;
import TrackModel.*;
import TrackController.TrackCtrlWrapper.Train;



public class TrackController {
	public Block map[];
	private TrackModel myModel;
	
	private ArrayList<Integer> present;
	
	private ArrayList<Switch> switches;
	private int switchBlocks;
	private int stopBlocks;
	
	public HashMap<Integer, Train> trains;
	
	private Crossing crossing;
	private int crossingBlocks;
	
	private PLC myPLC;
	
	private int half;
	
	public UI ui;

	TrackController(Simulator simulator, int fraction, HashMap<Integer, Train> trns) {
		myModel = simulator.trackModel;
		present = new ArrayList<Integer>(); 
		trains = trns;
		half = fraction;
		ui = new UI(this);
		//ui.setvisible(true);
	}
	
	TrackController() {
		System.out.println(loadPLC("TrackController.testPLC"));
		System.out.println(myPLC.returnFive());
	}
	
	public void setRoute(int trainID, int destination, double suggestedSpeed, int suggestedAuthority, int[] route) {
		Train train = trains.get(trainID);
		
		if (myPLC == null) {
			train.speed = 0;
			train.sugSpeed = 0;
			train.sugAuthority = 0;
			myModel.setAuthority(0, train.position);
			myModel.setSpeed(0, train.position);
		}
		else {
			train.speed = suggestedSpeed;
			train.sugSpeed = suggestedSpeed;
			myModel.setAuthority((double) suggestedAuthority, train.position);
			myModel.setSpeed(suggestedSpeed, train.position);
		}
		
	}
	
	public void tick(HashMap<Integer, Train> trains, Block map[]) {
		if(myPLC != null) {
			if (map[1] != null) {
				myPLC.addSwitches(map, ui);
			}
			myPLC.setRegions(trains);
			int routeCheck = myPLC.checkRoutes(trains, ui);
			myPLC.checkTrainEmergency(map, trains, myModel);
			int speedCheck = myPLC.getSafeSpeed(trains, map, myModel);
			
			myPLC.checkSwitches(map, trains, ui);
		}
		return;
	}
	
	public boolean loadPLC(String filename) {
		//File file = new File(filename);
		ClassLoader cl = ClassLoader.getSystemClassLoader();

		try {
			/*URL url = file.toURL();
			URL[] urls = new URL[]{url};
			
			ClassLoader cl = new URLClassLoader(urls);*/
			Class cls = cl.loadClass(filename);
			Object o = cls.newInstance();
			
			myPLC = (PLC) o;
			
			System.out.println("Loaded successfully!");

			
			return true;
		}
		catch (Exception e) {
			System.out.println("Caught Exception: "+e);
			return false;
		} 
	}
	
		
	public int getPosition() {
//		return train.position;
		return -1;
	}
	
	
	
	public static void main(String args[]) {
		/*TrackController ctrl = new TrackController();
		
		ctrl.prototype_initialize();
		
		ctrl.populateTrainmap();
				
		for (int i = 0; i < 200; i++) {
			if (ctrl.train.speed == 0)
				break;
			ctrl.advanceTrain();
			ctrl.checkSwitches();
		}*/
		
		TrackController TC = new TrackController();
	}
	
	public void showUI() {
		ui.setvisible(true);
	}
	
	public void updateTrains(HashSet<Integer> present) {
		/*Iterator it = present.iterator();
		int count = 0;
		while (it.hasNext()) {
			//Map.Entry pair = (Map.Entry) it.next();
			//Train train = (Train) pair.getValue();
			//int key = (Integer) pair.getKey();
			int presentBlock = (Integer)it.next();
			
			//If there are more trains present than there are in the trains structure, add a new one
			if (count == trains.size()) {
				trains.put(presentBlock, );
				count++;
				continue;
			}
			
			//If the block that is reported occupied was already occupied in the previous tick, assume it is the same train
			if (trains.get(presentBlock) != null) {
				trains.get(presentBlock).oldposition = presentBlock;
				continue;
			}
			
			//If the block was NOT occupied, go through each train and see if its next step would be this block
			Iterator trainsIT = trains.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry) it.next();
				Train train = (Train) pair.getValue();
				int trainPos = (Integer) pair.getKey();
				
				int[] route = train.suggestedRoute;
				//iterate over the route until the trains current position is found
				for (int i=0; i < route.length; i++) {
					//when the position is found, check the next block the train should occupy
					if (route[i] == train.position) {
						//if it is the newly present block, update the train with the new value
						if (route[i+1] == presentBlock) {
							trains.put(presentBlock, train);
							trains.remove(trainPos);
							break;
						}
						//if it is not the newly present block,move on to the next train
						else {
							continue;
						}
					}
				}
			}
			
			
			
		}*/
	}
	
	
	
	public class Switch {
		int root;
		int head1;
		int head2;
		int direction;
		int state;
		
		Switch(int t, int h1, int h2) {
			root = t;
			head1 = h1;
			head2 = h2;
			state = 0;
			
			//for prototype only one direction of travel is supported
			//So we use direction as a multiplier when checking switches
			if (root - head1 > 0 && root - head2 > 0) {
				direction = -1;
			}
			else {
				direction = 1;
			}
		}
		
		public void set(int head) {
			if (head == head1) {
				state = 0;
			}
			else if (head == head2) {
				state = 1;
			}
			return;
		}
	}
	
	public class Crossing {
		boolean lightsOn;
		int position;
		
		Crossing(int pos) {
			lightsOn = false;
			position = pos;
		}
	}
}
