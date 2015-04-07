package TrackController;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.HashMap;
import java.io.*;
import Simulator.Simulator;
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
	
	public void setRoute(int trainBlock, int destination, double suggestedSpeed, int suggestedAuthority, int[] route) {
		Train train = trains.get(trainBlock);
		
		train.destination = destination;
		train.sugSpeed = suggestedSpeed;
		train.sugAuthority = suggestedAuthority;
		train.suggestedRoute = route;
	}
	void loadPLC(String filename) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(filename)));
			
			//The first line is an integer that corresponds to the number of blocks away a train should be before the switch tries to switch
			try {
				String line = reader.readLine().trim();
				switchBlocks = Integer.parseInt(line);
			}
			catch (IOException e) {
				System.out.println("Invalid PLC File, please try again");
			}
			//The second corresponds to the number of blocks away whose speeds will be set to zero should there be a switch conflict
			try {
				String line = reader.readLine().trim();
				stopBlocks = Integer.parseInt(line);
			}
			catch (IOException e) {
				System.out.println("Invalid PLC File, please try again");
			}
			
			
			try {
				reader.close();
			}
			catch (IOException e) {
				
			}
		}
		catch (FileNotFoundException e) {
			System.out.println("File not found, please try again");
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
