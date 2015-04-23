package CTC;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.table.DefaultTableModel;

import MBO.*;
import MBO.TrainSchedule.Stop;
import TrackController.*;
import Simulator.*;
import TrackModel.*;

public class CTC {
	private int mode;
	private int stops;
	private int stopsLastHour;
	public int blockCount;
	private boolean[] closedBlocks;
	public int activeTrains;
	public int[] locations;
	public int[] reverses; // array of blocks the trains came from: can't turn around
	private double[] speeds;
	private boolean[] panics; //help to only print error messages once
	private TrainRoute[] routes;
	private ctcWindow myWindow;
	private fakeWindow faaake;
	public Block[] blocks;
	private long time;
	//defautl red line yard block until jackie gives me a better option
	private int fromYard = 77; //tell where new trains will come from
	private int toYard = 77; //which block leads to the yard
	private  ArrayList<Stop> schedules;
	private int scheduleStop = 0;
	private int dwelled = 0;
	public int waitForspawn = 0;
	
	
	//modules i can talk to
	private Simulator notReggie;
	public TrackCtrlWrapper ben;
	private MBO drewBaby;
	
	
	//function setSchedule
	//function getMode
	//This comment is a test bro 
	/*
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
		
		
	}*/
	
	//function to monitor train locations and whatever else I feel like
	//made public for prototype, will be private later
	public void tick(){
		
		if(blockCount == 0){
			start();
		}
		
		this.mode = myWindow.cboMode.getSelectedIndex();
		
		//update time
		time = notReggie.getTime();
		myWindow.setTime(time);
		
		//do throughput stuff every hour
		String mins = myWindow.txtTime.getText().split(":")[1];
		String secs = myWindow.txtTime.getText().split(":")[2];
		if(Integer.parseInt(mins)==0 && Integer.parseInt(secs)==0){
			myWindow.setStops(0,  0,  stops);
			//Tell Andrew
			//drewBaby.set
			drewBaby.setActualThroughput(stops);
			stops = 0;
		}
		
		//get schedule so we have it 
		if(drewBaby.getDefaultSchedule() != null)
			schedules = drewBaby.getDefaultSchedule().stops;
		
		if(this.mode > 0 && activeTrains < 1){ //1 train, hard coded for now :(
			spawnTrainCTC();
			waitForspawn = 3;
			return;
		}
		if(waitForspawn > 0){
			waitForspawn--;
			return;
		}
		
		//update map
		//blocks = ben.getBlocks(); 
		notReggie.trackModel.getBlocks();
		
		
		boolean[] known = new boolean[activeTrains];
		//check current locations first 
		for(int i = 0; i <activeTrains; i++){
			known[i] = false;
			
			if(locations[i] > 0 && blocks[locations[i]].isTrainPresent()){
				//assume this train is still the one on that block. should hold unless active train # approaches block number
				known[i] = true;
				panics[i] = false;
			}			
		}
		//check destinations to see if the trains made it 
		for(int i = 0; i <activeTrains; i++){			
			if(!known[i]){
				if(routes[i] == null){
					//train moved unexpectedly; hit a switch or Kyle fucked up the stop distance
					//overshot backwards
					if(locations[i]+1<blockCount && reverses[i] != locations[i]+1 && blocks[locations[i]+1].isTrainPresent()){
						//check that another train isn't already there
						boolean blocked = false;
						for(int j = 0; j < activeTrains; j++){
							if(i != j && locations[j] == locations[i]+1){
								blocked = true;
								break;
							}							
						}
						if(!blocked){//the train went here
							known[i] = true;
							reverses[i] = locations[i];
							locations[i] = locations[i]+1;							
							
							myWindow.setLocation(i,  locations[i], -1);
														
							continue;
						}
					}
					//overshot forwards
					if(locations[i]>1 && reverses[i] != locations[i]-1 && blocks[locations[i]-1].isTrainPresent()){
						//check that another train isn't already there
						boolean blocked = false;
						for(int j = 0; j < activeTrains; j++){
							if(i != j && locations[j] == locations[i]-1){
								blocked = true;
								break;
							}							
						}
						if(!blocked){//the train went here
							known[i] = true;
							reverses[i] = locations[i];
							locations[i] = locations[i]-1;		
							
							myWindow.setLocation(i,  locations[i], -1);
							
							continue;
						}
					}
									
					
					continue;
				}
				//make sure a train isn't already there
				boolean cockBlocked = false; 
				for(int j = 0; j < activeTrains; j++){
					if(locations[j] == routes[i].block && i != j){
						cockBlocked = true;
						break;
					}
				}
				if(cockBlocked || (locations[i] > 0 && !blocks[routes[i].block].isTrainPresent())){
					//check for another switch path
					Switch sw = blocks[locations[i]].getSwitch();
					if(sw!=null){
						int root = sw.getRoot();
						int one = sw.getSwitchBlocks()[0];
						int two = sw.getSwitchBlocks()[1];
						int loc  = locations[i];
						int blockedBlock = routes[i].block;
						if((loc==root)){ // this is the only time a train could go to more than one place
							if(blocks[two].isTrainPresent() && blockedBlock != two){
								known[i] = true;
								myWindow.setLocation(i,  two, -1);
								reverses[i]=locations[i];
								locations[i]=two;
								
								//route it again
								int destination = 0;
								TrainRoute notTaken = routes[i];
								while(notTaken != null){
									destination = notTaken.block;
									notTaken = notTaken.next;
								}
								routeTrainCTC(i, speeds[i], destination);
								
							} else if (blocks[one].isTrainPresent() && blockedBlock != one){
								known[i] = true;
								myWindow.setLocation(i,  one, -1);
								reverses[i]=locations[i];
								locations[i]=one;
								
								//route it again
								int destination = 0;
								TrainRoute notTaken = routes[i];
								while(notTaken != null){
									destination = notTaken.block;
									notTaken = notTaken.next;
								}
								routeTrainCTC(i, speeds[i], destination);
								
							}
						}
						
					} 
				} else if(blocks[routes[i].block].isTrainPresent()){
					//set current location to this one and cycle route
					myWindow.setLocation(i,routes[i].block, -1);
					reverses[i] = locations[i];
					locations[i] = routes[i].block;
					known[i] = true;
					routes[i] = routes[i].next;
					
					//record stop when it reaches station block that it is scheduled to stop at
					if (blocks[locations[i]].getStation() != null && routes[i] == null){
						//record a stop
						myWindow.setAnnouncement("Train " + (i + 1) + " reached " + blocks[locations[i]].getStation() + " station");
						this.stops++;
						mins = myWindow.txtTime.getText().split(":")[1]; 
						double expected = (double)stops/Double.parseDouble(mins) * 60;
						myWindow.setStops(stops, expected, -1); //!!!not calculating expected yet
					}
				}
			}
		}
		
		//verify that all train locations actually have a train on them
		for(int i = 0;i<activeTrains;i++){
			if(!known[i] && locations[i]>0 && !panics[i]){
				myWindow.setAnnouncement("!!Lost Train " + (i + 1) + "! What have you done?!?!");
				panics[i] = true;
			} 
			
		}
		
		//update pretty colors
		for(int i = 1; i < blockCount; i++){
			myWindow.setTrackStatus(blocks[i]);			
		}
		
		
		
		if(this.mode > 0 && schedules.size() > 0){
			Stop st = schedules.get(scheduleStop);
			
			
			if (locations[0] == st.block){
				if(dwelled < st.dwellTime){
					dwelled++;
				} else { //next step
					scheduleStop++;
					if(scheduleStop >= schedules.size()){
						scheduleStop = 0;
					}
					
					dwelled = 0;
					routeTrainCTC(0, 11.11, schedules.get(scheduleStop).block);
				}
			} else if(routes[0]==null){
				routeTrainCTC(0, 11.11, schedules.get(scheduleStop).block);
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
		this.reverses = new int[blocks];
		this.speeds = new double[blocks];
		this.routes = new TrainRoute[blocks]; 
		this.panics = new boolean[blocks];
		Arrays.fill(panics, false);
		Arrays.fill(closedBlocks, false);
		Arrays.fill(locations,  -1); //use -1 for trains that don't exist yet
		Arrays.fill(reverses, -1);
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
		this.reverses = new int[blockCount];
		this.speeds = new double[blockCount];
		this.panics = new boolean[blockCount];
		Arrays.fill(panics, false);
		Arrays.fill(closedBlocks, false);
		Arrays.fill(locations,  -1); //use -1 for trains that don't exist yet
		Arrays.fill(routes,  null);//new TrainRoute(-1, null));
		Arrays.fill(reverses, -1);
		
				
		//setup window(s)		
		//faaake = new fakeWindow();
		myWindow = new ctcWindow(this);
			
	}
	
	public void setBen(TrackCtrlWrapper ben) {
		this.ben = ben;
	}
	
	//sub to initialize after a map is loaded
	public void start(){
		
		this.drewBaby = notReggie.mbo;
		
		//load track somehow
		//this.blocks = ben.getBlocks();	//call this once it exists
		
		//!!!call jackie directly to set up map
		this.blocks = notReggie.trackModel.getBlocks();
		
		this.blockCount = blocks.length;
		
		this.mode = 0; //manual
		this.stops = 0;
		this.stopsLastHour = 0;
		this.activeTrains = 0;
		//use block count as size of block and train lists; more than one train per block would be crazy 
		
		this.closedBlocks = new boolean[blockCount];
		this.locations = new int[blockCount];
		this.reverses = new int[blockCount];
		this.routes = new TrainRoute[blockCount]; 
		this.speeds = new double[blockCount];
		this.panics = new boolean[blockCount];
		Arrays.fill(panics, false);
		Arrays.fill(closedBlocks, false);
		Arrays.fill(locations,  -1); //use -1 for trains that don't exist yet
		Arrays.fill(routes,  null);//new TrainRoute(-1, null));
		Arrays.fill(reverses,  -1);
		
		myWindow.setAnnouncement("loaded a map with " + blockCount + " blocks");
		
		//draw awesome sexy map
		for(int i = 1; i < blockCount; i++){
			
			//set yard blocks
			if(blocks[i].isToYard()){
				toYard = i;
			}
			if(blocks[i].isFromYard()){
				fromYard = i;
			}
			
			
			//check i+-1; will only paint if getTouch returns a good value
			myWindow.addTrack(blocks[i].mapRow, blocks[i].mapCol, getTouch(i,i+1));
			myWindow.addTrack(blocks[i].mapRow, blocks[i].mapCol, getTouch(i,i-1));
			//myWindow.addTrack(1,3,0);
			//check switch stuff
			Switch sw = blocks[i].getSwitch();
			if(sw!=null){
				//you are root, connect to both branches
				myWindow.addTrack(blocks[i].mapRow, blocks[i].mapCol, getTouch(i,sw.getSwitchBlocks()[0]));
				myWindow.addTrack(blocks[i].mapRow, blocks[i].mapCol, getTouch(i,sw.getSwitchBlocks()[1]));
			} else if (blocks[i].getSwitchRoot() > 0){
				myWindow.addTrack(blocks[i].mapRow, blocks[i].mapCol, getTouch(i,blocks[i].getSwitchRoot()));
			}
			
			//add special labels for block # and train
			myWindow.setTrackLabel(blocks[i],  i);
			
			
			//set dat color
			myWindow.setTrackStatus(blocks[i]);
		}
		
	}
	
	//helper function to see if blocks touch
	public int getTouch(int n, int m){
		//assume n is a valid index, but check m
		if (m<1||m>=blockCount){
			return -1;
		}
		
		//check coordinates
		int ny = blocks[n].mapRow;
		int nx = blocks[n].mapCol;
		int my = blocks[m].mapRow;
		int mx = blocks[m].mapCol;
		
		if(my==ny-1){
			if(mx==nx-1){
				return 7;
			} else if (mx==nx){
				return 0;
			} else if (mx==nx+1){
				return 1;
			}
		} else if (my==ny+1){
			if(mx==nx-1){
				return 5;
			} else if (mx==nx){
				return 4;
			} else if (mx==nx+1){
				return 3;
			}
		} else if (my==ny){
			if(mx==nx-1){
				return 6;
			} else if (mx==nx+1){
				return 2;
			}
		}
		
		return -1;
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
			TrainRoute path = calcRoute(train,block, dest);
						
			int[] lengths = routeLength(path);			
			//chop start point off of route after it is used to calculate authority
			path = path.next;
			
			routes[train] = path;
			
			
			
			int[] p = pathFromRoute(path, lengths[1] - 1);
			
			String ann = "Route: ";
			for(int i = 0; i < lengths[1] - 1;i++){
				ann = ann + p[i] + "-";
			}
			myWindow.setAnnouncement(ann);
			myWindow.setAnnouncement("Train: " + (train + 1) + " Distance: " + lengths[0] + "m Dest: " + dest);
			
			
			
			System.out.println(ann);
				//this.faaake.routeTrain( block, speed, dest, path);			
				//myWindow.setAnnouncement("route set");			
				myWindow.setLocation(train, -1,  dest);
				
				ben.setRoute( block,  dest, speed, lengths[0], p);
				speeds[train] = speed; //remember this for re-routes
				
				return true;
			
				
				
			
			
		} else {
			myWindow.setAnnouncement("Cannot Route Train:  Still in Yard or Location unknown");
			return false;
		}
		
		
		
	}
	
	public int[] pathFromRoute(TrainRoute rt, int length){
		int[] p = new int[length];
		
		for (int i = 0; i < length; i++){
			p[i] = rt.block;
			rt = rt.next;
		}		
		return p;
	}
	
	//choose a train to spawn and set it's destination to the spawn block
	public void spawnTrainCTC(){
		//!!! for demo don't optimize train selection. new trains will always be at the end of the array
		
		if(blockCount == 0 ){
			myWindow.setAnnouncement("Wait For Track To Load to Make a Train Puppy!");
		} else if (blocks[fromYard].isTrainPresent()){
			myWindow.setAnnouncement("Yard Blocked, Wait Your turn!");
		} else {
							
			//use old max for index of new train
			routes[activeTrains] = new TrainRoute(fromYard, null); 
			locations[activeTrains] = 0; //special flag for yard
			
			myWindow.setLocation(activeTrains, -1,  fromYard);
			
			activeTrains++; 
			
			//make train puppy in sim
			notReggie.makeTrainPuppy();
		}
	}
	
	//sub to find the best route
	private TrainRoute calcRoute(int train, int block, int dest){
		
		//myWindow.setAnnouncement("entered calcRoute");
		
		//calculate both directions, choose shortest	
		//myWindow.setAnnouncement("start calc up");
		TrainRoute rtUp = calcRoute(block, dest, true, reverses[train]);		
		//myWindow.setAnnouncement("start calc down");
		TrainRoute rtDwn = calcRoute(block, dest, false, reverses[train]);
		//myWindow.setAnnouncement("calced both directions");
		
		//see which path is illegal because it went backwards
		if(rtUp.next.block == reverses[train]){
			return rtDwn;
		} else {
			return rtUp;
		}
		
		/* don't care about length because only one route is valid
		int upLength = routeLength(rtUp)[0];
		int downLength = routeLength(rtDwn)[0];
		if(upLength > downLength){
			return rtDwn;
		} else {
			return rtUp;
		}*/
				
	}
	//sub to determine a path in a specific direction
	public TrainRoute calcRoute(int block, int dest, boolean up, int from){
		TrainRoute rt = new TrainRoute(block, null);
		TrainRoute save = rt; //save start point because we're adding to the end
		int inc; //increment
		int previous = from;
		if(up){
			inc = 1;
		} else {
			inc = -1;
		}
		
		
		
		int next = -1;
		boolean justSwitched = false;
		Switch sw = blocks[block].getSwitch();
		//special case if routing from a switch branch
		if(blocks[block].getSwitchRoot() == from ||
			sw != null && (sw.getBlockOne() == from || sw.getBlockTwo() == from)){
			justSwitched = true;
		}
		
		while(block!=dest){			
			sw = blocks[block].getSwitch();			
			boolean hasSwitch = (sw != null);
			//myWindow.setAnnouncement("route: " + block + ". has switch? " + hasSwitch);
			if(hasSwitch && !justSwitched){
				justSwitched = true;
				if (sw.getRoot() == block){
					//myWindow.setAnnouncement("dat da root");
					//choose "One" or "Two"
					int[] paths = sw.getSwitchBlocks();
					//!!! dangerous assumption: paths[0] will be root +/- 1
					if(paths[0]==dest || onBranch(paths[0], dest)){
						//myWindow.setAnnouncement("Dest on Branch " + paths[0]);
						next = paths[0];
					} else if (paths[1]==dest || onBranch(paths[1], dest)){
						//myWindow.setAnnouncement("Dest on Branch " + paths[1]);
						next = paths[1];
					} else {
						//myWindow.setAnnouncement("neither path has dest, go to " + paths[0]);
						next =  paths[0];
					}					
				} else {
					//cann only go to root block from a branch
					//myWindow.setAnnouncement("root: " + sw.getRoot() + ", block: " + block);
					next = sw.getRoot();
				}			
			} else if(blocks[block].getSwitchRoot() != -1 && !justSwitched){
				//myWindow.setAnnouncement("branch");
				justSwitched = true;
				next = blocks[block].getSwitchRoot();
			} else {				
				
				//can ignore some possibilities because we know we aren't going over a switch when this point is reached
				if (previous == block - 1){
					next = block + 1;
				} else if (previous == block + 1) {
					next = block - 1;
					
					//else we know the train just switched
				} else if	(getTouch(block, block + 1) >= 0 &&
							(sw == null || 
							(sw.getBlockOne() != (block + 1) && sw.getBlockTwo() != (block + 1)))) {
					next = block + 1;
				} else { //it better fucking touch <-1> so don't even check
					next = block - 1;
				}
					
				
				justSwitched = false;
			}	
			//add new location to list (route)
			previous = block;
			block = next;
			TrainRoute rrrt = new TrainRoute(next, null);
			rt.next = rrrt;
			rt = rrrt;	
			
			if(block==previous || block == toYard || block == fromYard){
				//myWindow.setAnnouncement("fuckkkk");
				break;
			}
			
		} //end while	
		
		return save;
	}

	//sub for determining route length. used to pick between up and down
	public int[] routeLength(TrainRoute rt){
		int[] length = {0,0};
		
		boolean first = true; 
		
		while(rt!=null){
			
			if(rt.block == fromYard || rt.block == toYard){
				// always count the whole block leaving the yard
				length[0] += blocks[rt.block].getBlockSize(); 
				first = false;
			} else if(rt.next == null || first){
				//route it to halfway 
				first = false;
				length[0] += blocks[rt.block].getBlockSize()/2;
			} else {
				length[0] += blocks[rt.block].getBlockSize();				
			}
			length[1]++;
			rt = rt.next;
			
		}
		return length;		
	}
		
	/* sub to check if the destination is on the branch path. 
	*  Allows the main routing calculation to ignore branches
	*/
	private boolean onBranch(int block, int dest){
		
		
		//find direction of initial switch 
		int increment;
		
		if(blocks[block].getSwitchRoot() == block - 1 || getTouch(block, block - 1) == -1){ 
			block++;
			increment = 1;
		} 
		else {
			block--;
			increment = -1;
		}
		
		do {
			//myWindow.setAnnouncement("looking for dest on Block " + block);
			if (block == dest){
				return true;				
			}
			if(block == toYard || block == fromYard || block == 0 || block >= blockCount){
				break;
			}
			block += increment;
		} while (!(block == toYard || block == fromYard ) && block > 0 && blocks[block].getSwitch() == null && blocks[block].getSwitchRoot() == -1 );
		
		
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
				//myWindow.setAnnouncement("!!Cannot Route Over Broken Block " + path[i] + "!!");
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
