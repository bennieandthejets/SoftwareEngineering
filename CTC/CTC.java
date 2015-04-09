package CTC;

import java.awt.EventQueue;
import java.util.Arrays;

import javax.swing.table.DefaultTableModel;

import MBO.*;
import TrackController.*;
import Simulator.*;
import TrackModel.*;
import TrackModel.Block;

public class CTC {
	private int mode;
	private int stops;
	private int stopsLastHour;
	public int blockCount;
	private boolean[] closedBlocks;
	public int activeTrains;
	private int[] locations;
	private TrainRoute[] routes;
	private ctcWindow myWindow;
	private fakeWindow faaake;
	private Block[] blocks;
	private long time;
	//defautl red line yard block until jackie gives me a better option
	private int fromYard = 77; //tell where new trains will come from
	private int toYard = 77; //which block leads to the yard
	
	
	//modules i can talk to
	private Simulator notReggie;
	private TrackCtrlWrapper ben;
	private MBO drewBaby;
	
	
	//function setSchedule
	//function getMode
	//This comment is a test bro 
	
	public static void main(String[] args) {	
		
		CTC ctc = new CTC(); 
		
		ctc.displayWindow();
		
		//DefaultTableModel model = (DefaultTableModel) ctc.myWindow.tblAnnouncements.getModel();
		//model.insertRow(0,new Object[]{"Opened From Model"});
		//ctc.myWindow.setAnnouncement("Opened From CTC class");
		
		//initialize map
		//for(int i = 0; i < ctc.blockCount; i++){
			//ctc.myWindow.setMapBlock(i, false, false);
		//}
		
		//update window forever
		
		
	}
	
	//function to monitor train locations and whatever else I feel like
	//made public for prototype, will be private later
	public void tick(){
		
		
		//update time
		time = notReggie.getTime();
		myWindow.setTime(time);
		
		
		//update map
		//blocks = ben.getBlocks(); 	
		
		
		boolean[] known = new boolean[activeTrains];
		//check current locations first 
		for(int i = 0; i <activeTrains; i++){
			known[i] = false;
			
			if(locations[i] > 0 && blocks[locations[i]].isTrainPresent()){
				//assume this train is still the one on that block. should hold unless active train # approaches block number
				known[i] = true;
			}			
		}
		//check destinations to see if the trains made it 
		for(int i = 0; i <activeTrains; i++){			
			if(!known[i] && locations[i]>0){
				//make sure a train isn't already there
				boolean cockBlocked = false; 
				for(int j = 0; j < activeTrains; j++){
					if(locations[j] == routes[i].block){
						cockBlocked = true;
						break;
					}
				}
				if(cockBlocked || !blocks[routes[i].block].isTrainPresent()){
					//check for another switch path
					Switch sw = blocks[locations[i]].getSwitch();
					if(sw!=null){
						int root = sw.getRoot();
						int one = sw.getSwitchBlocks()[0];
						int two = sw.getSwitchBlocks()[1];
						int loc  = locations[i];
						if((loc==root)){ // this is the only time a train could go to more than one place
							if(loc==one&&blocks[two].isTrainPresent()){
								myWindow.setLocation(i,  two, -1);
								//!!! do something about the broken route
							} else if (loc==two&&blocks[one].isTrainPresent()){
								myWindow.setLocation(i,  one, -1);
								//!!! do something about the broken route
							}
						}
						
					}
				}
			} else if(blocks[routes[i].block].isTrainPresent()){
				//set current location to this one and cycle route
				myWindow.setLocation(i,routes[i].block, -1);
				locations[i] = routes[i].block;
				known[i] = true;
				routes[i] = routes[i].next;
			
			}
		}
			
		
		//verify that all train locations actually have a train on them
		for(int i = 0;i<activeTrains;i++){
			if(!known[i] && locations[i]>0){
				myWindow.setAnnouncement("!!Lost Train " + (i + 1) + "! What have you done?!?!");
				locations[i] = 0;
			}
			
		}
		
	}
	
	//alternate constructor with no sim. just for viewing design changes
	public CTC(){
		int blocks = 10;
		
		
		this.mode = 0; //manual
		this.stops = 0;
		this.stopsLastHour = 0;
		this.activeTrains = 0;
		//use block count as size of block and train lists; more than one train per block would be crazy 
		this.blockCount = blocks;
		this.closedBlocks = new boolean[blocks];
		this.locations = new int[blocks];
		this.routes = new TrainRoute[blocks]; 
		Arrays.fill(closedBlocks, false);
		Arrays.fill(locations,  -1); //use -1 for trains that don't exist yet
		Arrays.fill(routes,  null);//new TrainRoute(-1, null));
		
				
		//setup window(s)		
		//faaake = new fakeWindow();
		myWindow = new ctcWindow(this);
	}
	
	public CTC(Simulator reggie){
		//setup references to other modules
		this.notReggie = reggie;
		this.ben = reggie.trackControllerWrapper;
		this.drewBaby = reggie.mbo;
		
		
		//load track somehow
		//this.blocks = ben.getBlocks();	//call this once it exists
		
		this.blockCount = 0; // = blocks.length;
		
		this.mode = 0; //manual
		this.stops = 0;
		this.stopsLastHour = 0;
		this.activeTrains = 0;
		//use block count as size of block and train lists; more than one train per block would be crazy 
		
		this.closedBlocks = new boolean[blockCount];
		this.locations = new int[blockCount];
		this.routes = new TrainRoute[blockCount]; 
		Arrays.fill(closedBlocks, false);
		Arrays.fill(locations,  -1); //use -1 for trains that don't exist yet
		Arrays.fill(routes,  null);//new TrainRoute(-1, null));
		
				
		//setup window(s)		
		//faaake = new fakeWindow();
		myWindow = new ctcWindow(this);
			
	}
	
	//sub to initialize after a map is loaded
	public void start(){
		
		//load track somehow
		//this.blocks = ben.getBlocks();	//call this once it exists
		
		//!!!call jackie directly until ben gets his shit together
		this.blocks = notReggie.trackModel.getBlocks();
		
		this.blockCount = blocks.length;
		
		this.mode = 0; //manual
		this.stops = 0;
		this.stopsLastHour = 0;
		this.activeTrains = 0;
		//use block count as size of block and train lists; more than one train per block would be crazy 
		
		this.closedBlocks = new boolean[blockCount];
		this.locations = new int[blockCount];
		this.routes = new TrainRoute[blockCount]; 
		Arrays.fill(closedBlocks, false);
		Arrays.fill(locations,  -1); //use -1 for trains that don't exist yet
		Arrays.fill(routes,  null);//new TrainRoute(-1, null));
		
		myWindow.setAnnouncement("loaded a map with " + blockCount + " blocks");
	}
	
	//method to display the ctcWindow associated with this CTC
	public void displayWindow(){
		
		if (!myWindow.frmCtc.isVisible()){

			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
											
						//ctcWindow window = new ctcWindow();
						myWindow.frmCtc.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});	
		} else {
			myWindow.frmCtc.setVisible(false);
		}
		
		
		
		
	}
	
	
	public boolean routeTrainCTC(int train, double speed, int dest){
		//convert train number to block number
		///!!! test without working train presence by using the train # as block #
		//int block = locations[train];
		int block = train + 1;
		
		
		//myWindow.setAnnouncement("got location " + Integer.toString(block));
		
		if(block > 0 ){
			//route it
			//calculate full path
			TrainRoute path = calcRoute(block, dest);
			int length = routeLength(path);
			routes[train] = path;
			
			
			
				//this.faaake.routeTrain( block, speed, dest, path);			
				//myWindow.setAnnouncement("route set");			
				myWindow.setLocation(train, -1,  dest);
				return true;
			
			
			
		} else {
			myWindow.setAnnouncement("Cannot Route Train: Still in Yard");
			return false;
		}
		
		
		
	}
	
	//choose a train to spawn and set it's destination to the spawn block
	public void spawnTrainCTC(){
		//!!! for demo don't optimize train selection. new trains will always be at the end of the array
		
		//use old max for index of new train
		routes[activeTrains] = new TrainRoute(fromYard, null); 
		locations[activeTrains] = 0; //special flag for yard
		
		myWindow.setLocation(activeTrains, -1,  1);
		
		activeTrains++; 
		
		//make train puppy in sim
		
	}
	
	//sub to find the best route
	private TrainRoute calcRoute(int block, int dest){
		
		myWindow.setAnnouncement("entered calcRoute");
		
		//calculate both directions, choose shortest	
		myWindow.setAnnouncement("start calc up");
		TrainRoute rtUp = calcRoute(block, dest, true);		
		myWindow.setAnnouncement("start calc down");
		TrainRoute rtDwn = calcRoute(block, dest, false);
		myWindow.setAnnouncement("calced both directions");
		int upLength = routeLength(rtUp);
		int downLength = routeLength(rtDwn);
		if(upLength > downLength){
			return rtDwn;
		} else {
			return rtUp;
		}
				
	}
	//sub to determine a path in a specific direction
	private TrainRoute calcRoute(int block, int dest, boolean up){
		TrainRoute rt = new TrainRoute(block, null);
		TrainRoute save = rt; //save start point because we're adding to the end
		int inc; //increment
		int previous = block;
		if(up){
			inc = 1;
		} else {
			inc = -1;
		}
		
		
		
		int next = -1;
		boolean justSwitched = false;
		
		while(block!=dest){
			
			Switch sw = blocks[block].getSwitch();			
			boolean hasSwitch = (sw != null);
			myWindow.setAnnouncement("route: " + block + ". has switch? " + hasSwitch);
			if(hasSwitch && !justSwitched){
				justSwitched = true;
				if (sw.getRoot() == block){
					//choose "One" or "Two"
					int[] paths = sw.getSwitchBlocks();
					//!!! dangerous assumption: paths[0] will be root + 1
					if(onBranch(paths[0], dest)){
						next = paths[0];
					} else if (onBranch(paths[1], dest)){
						next = paths[1];
					} else {
						next =  block + inc;
					}
					
				} else {
					//cann only go to root block from a branch
					next = sw.getRoot();
				}
			
			} else {
				justSwitched = false;
				next = block + inc;
				//turn around if these blocks are pointing the other way
				if (next == previous){
					next = block - inc;
				}
				//don't get turned around if you come off of a switch and you like another block from that switch
				if ((hasSwitch)&&(next==sw.getRoot() || next==sw.getSwitchBlocks()[0] || next==sw.getSwitchBlocks()[1])){
					next = block - inc;
				}
				
				
				//add new location to list (route)
				previous = block;
				block = next;
				TrainRoute rrrt = new TrainRoute(next, null);
				rt.next = rrrt;
				rt = rrrt;				
			}			
			
		} //end while	
		
		return save;
	}

	//sub for determining route length. used to pick between up and down
	private int routeLength(TrainRoute rt){
		int length = 0;
		
		while(rt!=null){
			if(rt.next == null){
				length+=10;
			} else {
				length += blocks[rt.block].getBlockSize();
				rt = rt.next;
			}
			
		}
		return length;		
	}
		
	/* sub to check if the destination is on the branch path. 
	*  Allows the main routing calculation to ignore branches
	*/
	private boolean onBranch(int block, int dest){
		//!!! account for hitting the yard to avoid infinite loop
		
		//find direction of initial switch 
		int increment;
		
		if(blocks[block].getSwitch() != null){ 
			block++;
			increment = 1;
		} 
		else {
			block--;
			increment = -1;
		}
		
		while (blocks[block].getSwitch() == null){ //!!! && blocks.getSwitchRoot() == -1 ){		
			if (block == dest){
				return true;				
			}
			if(block == toYard || block == fromYard ){
				break;
			}
			block += increment;
		}	
		
		//destination might also have switch
		if (block == dest){
			return true;
		}
		
	return false;
	}
	
		
	//set the route for a train after path is calculated 
	//this method will erase any existing route
	private boolean setRoute(int train, int[] path){
		boolean end = true;
		for (int i = path.length - 1; i >= 0; i--){
			if(closedBlocks[path[i]]){
				//block route
				myWindow.setAnnouncement("!!Cannot Route Over Broken Block " + path[i] + "!!");
				return false;
			}else {
				if (end){
				//	ignore existing route steps, force next to null
					routes[train] = new TrainRoute(path[i], null);
					end = false;
				//myWindow.setAnnouncement("set End " + Integer.toString(path[i]) + " train: " + train);
				} else {
					//preserve current block as next
					routes[train] = new TrainRoute(path[i], routes[train]);
					//myWindow.setAnnouncement("set   " + Integer.toString(path[i]));
				}
			}
			
		}
		return true;
	}
	
	public int getMode(){
		return this.mode;
	}
	
	public void setSchedule(TrainSchedule sched){
		//don't do shit yet
	}
	
}
