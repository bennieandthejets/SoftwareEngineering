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
		//blocks = ben.getBlocks(); //still doesn't exist
		
		
		
		for(int i = 0; i <blockCount; i++){
			//boolean train = blocks[i].get;
			//boolean broke = stat[i][1];
			//closedBlocks[i] = blocks[i].;
			//myWindow.setMapBlock(i,  train,  broke);
			boolean train = false;
			
			if(train){
				boolean known = false; //keep track if this is a ghost train
				//myWindow.setAnnouncement("train found at " + i + " " + activeTrains);
				//see if a train position needs to be updated
				for(int j = 0; j < activeTrains; j++){
					if(locations[j] == i){
						//ignore if a train was already here
						//myWindow.setAnnouncement("already train at " + i);
						known = true;
						break;						
					}
					if(routes[j] != null){
						//myWindow.setAnnouncement("not null, next dest " + routes[j].block);
						if(routes[j].block == i){
					
						//train moved to this block, update position
						routes[j] = routes[j].next;
						locations[j] = i;	
						myWindow.setLocation(j,  i,  -1);
						//myWindow.setAnnouncement(j + " " + i );
						known = true;
						}
					} else {
						//myWindow.setAnnouncement("that shit null: block " + i + " train: " + j);
					}
				}
				if(!known){
					//kinda error: train where we didn't want one
					myWindow.setAnnouncement("!!Unexpected Train at Block " + i + "!!");
				}				
			}			
		}
		
		//verify that all train locations actually have a train on them
		for(int i = 0;i<activeTrains;i++){
			/*if(!stat[locations[i]][0]){
				myWindow.setAnnouncement("!!Lost Train " + (i + 1) + "! What have you done?!?!");							
			}
			*/
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
		
		this.blockCount = 1 // = blocks.length;
		
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
		
		
		/*
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
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//fakeWindow window = new fakeWindow();
					faaake.frmCtcDemo.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		*/
		
		
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
		int block = locations[train];
		//myWindow.setAnnouncement("got location " + Integer.toString(block));
		
		if(block > 0 ){
			//route it
			//calculate full path
			int[] path = calcRoute(block, dest);
			
			//myWindow.setAnnouncement("path calculated");
			
			if(setRoute(train, path)){
				//this.faaake.routeTrain( block, speed, dest, path);			
				//myWindow.setAnnouncement("route set");			
				myWindow.setLocation(train, -1,  dest);
				return true;
			} else {
				return false;
			}
			
			
		} else {
			myWindow.setAnnouncement("Cannot Route Train: Still in Yard");
			return false;
		}
		
		
		
	}
	
	//choose a train to spawn and set it's destination to the spawn block
	public void spawnTrainCTC(){
		//!!! for demo don't optimize train selection. new trains will always be at the end of the array
		
		//use old max for index of new train
		routes[activeTrains] = new TrainRoute(1, null); //!!! 1 is not the first block connected to the yard
		locations[activeTrains] = 0; //special flag for yard
		
		myWindow.setLocation(activeTrains, -1,  1);
		
		activeTrains++; 
		
	}
	
	//sub to find the best route
	private int[] calcRoute(int block, int dest){
		
		//calculate both directions, choose shortest		
		TrainRoute rtUp = new TrainRoute(block, null);
		
		TrainRoute rtDwn = new TrainRoute(block, null);
		
						
		/*
		//straight path for demo, return sequence
		
		for(int i =0; i < (dest-block) + 1; i++){
			path[i] = block + i;
			//myWindow.setAnnouncement("   " + Integer.toString(path[i]));
		}		
		*/
		int path[] = new int[(dest-block) + 1];
		return path;
		
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
		
		boolean hasSwitch = false; //!!!  fuck you jackie; commit yo' shit
		Switch sw = new Switch(0, "fuckkkkkkkkk");
		int next = -1;
		boolean justSwitched = false;
		
		while(block!=dest){
			if(hasSwitch && !justSwitched){
				justSwitched = true;
				if (sw.getRoot() == block){
					//choose "One" or "Two"
					int[] paths = sw.getBlocks();
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
				if ((hasSwitch)&&(next==sw.getRoot() || next==sw.getBlocks()[0] || next==sw.getBlocks()[1])){
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
		
		
		
		
		return rt;
	}
	
	
	
	/* sub to check if the destination is on the branch path. 
	*  Allows the main routing calculation to ignore branches
	*/
	private boolean onBranch(int block, int dest){
		//!!! account for hitting the yard to avoid infinite loop
		
		//find direction of initial switch 
		int increment;
		
		int fuckOffEclipse = 0;
		//!!! jackie needs to expose switch presence   if(blocks[block-1].hasSwitch()){
		if(fuckOffEclipse == 0){ 
			block++;
			increment = 1;
		} 
		else {
			block--;
			increment = -1;
		}
		
		//!!! while (!blocks[block].hasSwitch){		
		while(fuckOffEclipse == 1){
			if (block == dest){
				return true;
			}
			block += increment;
		}	
		
		//destination might also have switch
		if (block == dest){
			return true;
		}
		block += increment;
		
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
