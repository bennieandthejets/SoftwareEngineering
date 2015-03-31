package CTC;

import java.awt.EventQueue;
import java.util.Arrays;

import javax.swing.table.DefaultTableModel;


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
	
	
	//function setSchedule
	//function getMode
	//This comment is a test bro 
	
	public static void main(String[] args) {
		
		CTC ctc = new CTC("sexxxx"); 
		
		
		//DefaultTableModel model = (DefaultTableModel) ctc.myWindow.tblAnnouncements.getModel();
		//model.insertRow(0,new Object[]{"Opened From Model"});
		ctc.myWindow.setAnnouncement("Opened From CTC class");
		
		//initialize map
		for(int i = 0; i < ctc.blockCount; i++){
			ctc.myWindow.setMapBlock(i, false, false);
		}
		
		//update window forever
		
		
	}
	
	//function to monitor train locations and whatever else I feel like
	//made public for prototype, will be private later
	public void updateEverything(){
		boolean[][] stat = faaake.getStatus();
		
		for(int i = 0; i < stat.length; i++){
			boolean train = stat[i][0];
			boolean broke = stat[i][1];
			closedBlocks[i] = stat[i][1]; //use broken and closed as equivalent for prototyope
			myWindow.setMapBlock(i,  train,  broke);
			
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
			if(!stat[locations[i]][0]){
				myWindow.setAnnouncement("!!Lost Train " + (i + 1) + "! What have you done?!?!");			
			}
		}
		
	}
	
	public CTC(String trackFile){
		//load track somehow
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
		faaake = new fakeWindow();
		myWindow = new ctcWindow(this,faaake);
		
		
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
		
		
	}
	
	public boolean routeTrainCTC(int train, double speed, int dest){
		//convert train number to block number 	
		int block = locations[train];
		//myWindow.setAnnouncement("got location " + Integer.toString(block));
		
		if(block >= 0 ){
			//route it
			//calculate full path
			int[] path = calcRoute(block, dest);
			
			//myWindow.setAnnouncement("path calculated");
			
			if(setRoute(train, path)){
				this.faaake.routeTrain( block, speed, dest, path);			
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
		routes[activeTrains] = new TrainRoute(0, null);
		locations[activeTrains] = -1; //initialized to -1 but force anyway
		
		myWindow.setLocation(activeTrains, -1,  0);
		
		activeTrains++; 
		
	}
	
	private int[] calcRoute(int block, int dest){
		block++;
		//straight path for demo, return sequence
		int path[] = new int[(dest-block) + 1];		
		for(int i =0; i < (dest-block) + 1; i++){
			path[i] = block + i;
			//myWindow.setAnnouncement("   " + Integer.toString(path[i]));
		}		
		
		return path;
		
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
	
	public void setSchedule(String schedFile){
		//don't do shit yet
	}
	
}
