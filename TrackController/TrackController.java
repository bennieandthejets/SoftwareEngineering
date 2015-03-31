<<<<<<< HEAD
package TrackController;
=======
package com.BennieAndTheJets.TrackController;
>>>>>>> 6d2b9428abe1c66a9fea66ffc5d3acf5e93ea78c

import java.util.ArrayList;
import java.util.ListIterator;
import java.io.*;



public class TrackController {
	boolean presence_redline[];
	//boolean presence_greenline[];
	ArrayList<Integer> present_red;
	//ArrayList<Integer> present_green;
	
	ArrayList<Switch> switches;
	int switchBlocks;
	int stopBlocks;
	
	Train train;
	Crossing crossing;
	int crossingBlocks;
	
	//Initialize a single train in each line
	private void prototype_initialize() {
		for (int i = 0; i < 78; i++) {
			presence_redline[i] = false;
		}
		presence_redline[48] = true;
		train = new Train(48, 72);
		
		switches = new ArrayList<Switch>();
		switches.add(new Switch(66, 67, 72));
		switches.add(new Switch(77, 71, 76));
		
		crossing = new Crossing(30);
		crossingBlocks = 4;
		/*for (int j = 0; j < 150; j++) {
			presence_greenline[j] = false;
		}
		presence_greenline[89] = true;
		*/
	}

	TrackController(String filename) {
		presence_redline =new boolean[78]; 
		present_red = new ArrayList<Integer>(); 
		//ArrayList<Integer> present_green = new ArrayList<Integer>(); 
		
		loadPLC(filename);
		
		prototype_initialize();
		
		populateTrainPresence();
		
		//switchState = 0;

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
	
	void populateTrainPresence() {
		for (int i = 0; i < presence_redline.length; i++) {
			if (presence_redline[i]) {
				present_red.add(i);
			}
		}
		/*for (int i= 0; i < presence_greenline.length; i++) {
			if (presence_greenline[i]) {
				present_green.add(i);
			}
		}
		*/
	}
	
	void checkSwitches() {
		SWITCHES: for (Switch sw : switches) {
			boolean route = true;
			//when a train enters block (root-switchblocks), check the blocks between train and switch for other trains
			if (sw.direction == 1 && presence_redline[sw.root-switchBlocks]) {
				BLOCKS_BETWEEN: for (int i = sw.root; i > sw.root-switchBlocks; i--) {
					//If there is a train between the switch and the other train, can't change switch
					if (presence_redline[i]) {
						route = false;
						continue SWITCHES;
					}
				}
				//if all blocks were empty, route the switch according to the train's destination
				if (train.destination == 72)
					sw.state = 0;
				else 
					sw.state = 1;
			}
			
			//otherwise when a train enters block (head1 + switchBlocks) or (head2 + switchBlocks), do the same as above
			else if (sw.direction == -1 && (presence_redline[sw.head1 - switchBlocks] || presence_redline[sw.head2 - switchBlocks])) {
				int head;
				
				if (presence_redline[sw.head1 - switchBlocks])
					head = sw.head1;
				else 
					head = sw.head2;
					
				BLOCKS_BETWEEN: for (int i = head; i > head - switchBlocks; i--) {
					//If there is a train between the switch and the other train, can't change switch
					if (presence_redline[i]) {
						route = false;
						continue SWITCHES;
					}
				}
				//if all blocks were empty, route the switch according to the train's destination
				if (train.destination == 72)
					sw.state = 0;
				else 
					sw.state = 1;
			}
		}
	}
	
	void advanceTrain() {
		//checkSwitch
		
		if (train.speed == 0) {
			return;
		}
		
		ListIterator<Integer> it = present_red.listIterator();
		PRESENTBLOCKS: while (it.hasNext()) {
			int block = it.next();
			
			//if the train is in the last block, loop it around to the first
			if (block==77) {
				presence_redline[block] = false;
				block = 0;
				it.set(block);
				presence_redline[block] = true;
				train.position = block;
				
				if (train.destination == 72) {
					train.destination = 76;
				}
				else {
					train.destination = 72;
				}
				continue;
			}
			
			//check to see if the train is interacting with a switch
			SWITCHES: for (Switch sw : switches) {
				//if the train is at the root of a switch and the train is moving root -> head, move it to the head that the switch points to
				//If the train is at the root and moving head -> root, just continue as normal
				if (block == sw.root && sw.direction == 1) {
					presence_redline[block] = false;
					if (sw.state == 0)
						block = sw.head1;
					else
						block = sw.head2;
					it.set(block);
					presence_redline[block] = true;
					train.position = block;
					continue PRESENTBLOCKS;
				}
				//Otherwise if the train is at a switch head and moving head -> root, move it to the root IF the switch state allows it to
				else if (block == sw.head1 && sw.direction == -1) {
					if (sw.state == 0) {
						presence_redline[block] = false;
						block = sw.root;
						it.set(block);
						presence_redline[block] = true;
						train.position = block;

						continue PRESENTBLOCKS;
					}
					else {
						System.out.println("ERROR: TRAIN HAS GONE OFF THE RAILS, EVERYBODY DIED");
						System.exit(0);
					}	
				}
				else if (block == sw.head2 && sw.direction == -1) {
					if (sw.state == 1) {
						presence_redline[block] = false;
						block = sw.root;
						it.set(block);
						presence_redline[block] = true;
						train.position = block;

						continue PRESENTBLOCKS;
					}
					else {
						System.out.println("ERROR: TRAIN HAS GONE OFF THE RAILS, EVERYBODY DIED");
						System.exit(0);
					}
				}
			}
			
			//If not looping around or at a switch, Simply advance the block by one
			presence_redline[block] = false;
			block += 1;
			it.set(block);
			presence_redline[block] = true;
			train.position = block;

		}
	}
	
	public void checkCrossing() {
		if (train.position >= crossing.position-crossingBlocks && train.position <= crossing.position) 
			crossing.lightsOn = true;
		else
			crossing.lightsOn = false;
	}
	
	public void startStop() {
		//if the train is not moving, set its speed to 1 to allow it to move
		if (train.speed == 0) {
			train.speed = 1;
		}
		//otherwise set its speed to 0 to stop it from moving
		else {
			train.speed = 0;
		}
	}
	
		
	public int getPosition() {
		return train.position;
	}
	
	public int getSwitchRoot(int switchNumber) {
		return switches.get(switchNumber).root;
	}
	
	public int getSwitchHead(int switchNumber, int head) {
		if (head == 1)
			return switches.get(switchNumber).head1;
		else
			return switches.get(switchNumber).head2;
	}
	
	public int getSwitchState(int switchNumber) {
		return switches.get(switchNumber).state;
	}
	
	public int getCrossing() {
		return crossing.position;
	}
	public boolean getCrossingState() {
		return crossing.lightsOn;
	}
	
	public static void main(String args[]) {
		TrackController ctrl = new TrackController(args[0]);
		
		ctrl.prototype_initialize();
		
		ctrl.populateTrainPresence();
				
		for (int i = 0; i < 200; i++) {
			if (ctrl.train.speed == 0)
				break;
			ctrl.advanceTrain();
			ctrl.checkSwitches();
		}
	}
	
	
	private class Train {
		int position;
		int destination;
		int speed;
		
		Train(int pos, int dest) {
			position = pos;
			destination = dest;
			speed = 0;
		}
	}
	
	private class Switch {
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
	
	private class Crossing {
		boolean lightsOn;
		int position;
		
		Crossing(int pos) {
			lightsOn = false;
			position = pos;
		}
	}
	
}
