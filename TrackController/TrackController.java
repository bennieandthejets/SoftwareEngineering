package TrackController;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.HashMap;
import java.net.URLClassLoader;
import java.io.*;

import Simulator.*;
import TrackModel.*;



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

	TrackController(Simulator simulator) {
		myModel = simulator.trackModel;
		present = new ArrayList<Integer>(); 				
	}
	
	TrackController() {
		System.out.println(loadPLC("TrackController.testPLC"));
		System.out.println(myPLC.returnFive());
	}
	
	public void setRoute(int trainBlock, int destination, double suggestedSpeed, int suggestedAuthority, int[] route) {
		Train train = trains.get(trainBlock);
		
		train.destination = destination;
		train.sugSpeed = suggestedSpeed;
		train.sugAuthority = suggestedAuthority;
		train.suggestedRoute = route;
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
	
	
	public class Train {
		int position;
		int destination;
		double speed;
		double sugSpeed;
		int sugAuthority;
		int[] suggestedRoute;
		
		Train(int pos, int dest) {
			position = pos;
			destination = dest;
			speed = 0;
		}
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
