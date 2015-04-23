package MBO;

import java.util.ArrayList;
import CTC.*;
import Simulator.*;
import TrackModel.*;
import TrainModel.*;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;

public class MBO
{
	private static final double TRAIN_ACCEL = 0.5	; 			// Train's medium acceleration in m/s^2
	private static final double TRAIN_SERVICE_BRAKE = -1.2;		// 2/3 load brake decel in m/s^2
	private static final double TRAIN_EBRAKE = -2.73;			// E-brake deceleration in m/s^2
	private static final double METERS_TO_MPH = 2.23694;		// Multiply m/s by this to get mph
	private static final double METERS_TO_FT = 3.28084;			// Multiply meters by this to get feet
	
	protected int throughput;
	protected int actualThroughput;
		
	private Simulator simulator;
	private MBOUI ui;
	private TrainModelWrapper trainModelWrapper;
	private CTC ctc;
	private TrackModel trackModel;
	
	// Map Train ID to train attributes
	protected ArrayList<Antenna> reggies;
	private ArrayList<StationInfo> stations;
	private HashMap<Integer, TrainSchedule> trainSchedules;
	private HashMap<Integer, CrewSchedule> crewSchedules;	
	protected HashMap<Integer, Double> currentVelocities;
	protected HashMap<Integer, Double> currentAuthorities;
	protected HashMap<Integer, Block> currentLocations;
	protected HashMap<Integer, Double> movingBlockAuthorities;
	protected HashMap<Integer, Double> safeVelocities;
	
	protected long systemTime;

	public MBO()
	{
		this.actualThroughput = -1;
		
		reggies = new ArrayList<Antenna>();		
		trainSchedules = new HashMap<>();
		crewSchedules = new HashMap<>();
		currentVelocities = new HashMap<>();
		currentAuthorities = new HashMap<>();
		currentLocations = new HashMap<>();
		movingBlockAuthorities = new HashMap<>();
		safeVelocities = new HashMap<>();
		
		this.ui = new MBOUI(this);
	}
	
	public MBO(Simulator simulator) {
		this();
		this.simulator = simulator;
		this.ctc = simulator.ctc;
	}
	
	public void setModules() {
		this.trainModelWrapper = simulator.trainModelWrapper;
		this.trackModel = simulator.trackModel;
	}
	
	public void showUI() {
		ui.setVisible(true);
	}
	
	public void trainAdded() {
		this.reggies = trainModelWrapper.getAllAntennas();
		ui.setTrainSelectBox();
	}
	
	public TrainSchedule getDefaultSchedule() {
		return trainSchedules.get(1);
	}
	
	/// Set the throughput for the system
	/// Returns: none
	public void setThroughput(int throughput, int hour) {
		this.throughput = throughput;
	}
	
	public void setActualThroughput(int largeBooties){
		this.actualThroughput = largeBooties;
		System.out.println("Thanks Jason. " + largeBooties);
	}
		
	/// In moving block mode, set the authorities for all trains
	/// Called on every tick (second)
	public void calculateSafeAuthority(int trainID) {
		if(reggies.size() == 1) {
			double safeAuthority = currentAuthorities.get(trainID);
			movingBlockAuthorities.put(trainID, safeAuthority);
		}
		else {
			Block block = currentLocations.get(trainID);
			int prevBlock = ctc.reverses[trainID - 1];
			
			for(int otherTrainID : currentLocations.keySet()) {
				if(otherTrainID == trainID) { continue; }
				Block otherTrainBlock = currentLocations.get(otherTrainID);
				int otherTrainPrevBlock = ctc.reverses[otherTrainID - 1];
				
				//Two trains are going up
				if((block.getBlockID() - prevBlock < 0) && (otherTrainBlock.getBlockID() - otherTrainPrevBlock < 0)){
					if(block.getBlockID() > otherTrainBlock.getBlockID()) {
						TrainRoute route = ctc.calcRoute(block.getBlockID(), otherTrainBlock.getBlockID(), true, prevBlock);
						double routeLength =  ctc.routeLength(route)[0];
						if(routeLength < movingBlockAuthorities.get(trainID)) {
							movingBlockAuthorities.put(trainID, routeLength);
						}
					}
				} 
				//Two trains going down
				else if((block.getBlockID() - prevBlock > 0) && (otherTrainBlock.getBlockID() - otherTrainPrevBlock > 0)) {
					if(block.getBlockID() < otherTrainBlock.getBlockID()) {
						TrainRoute route = ctc.calcRoute(block.getBlockID(), otherTrainBlock.getBlockID(), true, prevBlock);
						double routeLength =  ctc.routeLength(route)[0];
						/*if(routeLength != 0.0 && movingBlockAuthorities != null && routeLength < movingBlockAuthorities.get(trainID)) {
							movingBlockAuthorities.put(trainID, routeLength);
						}*/
					}
				}
			}
		}
	}
	
	/// In moving block mode, set the velocity setpoints for all trains
	/// Called on every tick (second)
	public void calculateSafeSetpoint(int trainID) {
		safeVelocities.put(trainID, currentVelocities.get(trainID));
	}
	
	/// In moving block mode, calculate the stop distances for all trains
	/// Called on every tick (second)
	public double calculateStopDistance(double velocity) {
		double timeToStop = timeToAttainSpeed(velocity, 0.0, TRAIN_SERVICE_BRAKE);
		double d = (velocity * timeToStop) + (0.5 * TRAIN_SERVICE_BRAKE * Math.pow(timeToStop, 2));
		double d2 = (velocity * timeToStop) + (0.5 * TRAIN_SERVICE_BRAKE * Math.pow(timeToStop, 2));
		if(d == d2) {
			return d;
		}
		else {
			return -1.0;
		}
	}
	
	// Given medium acceleration, calculates how long it takes to travel from one station to another	
	public double travelTimeBetweenStations(int distanceToTravel, double suggestedVelocity) {
		double secElapsed = 0;
		double distanceToGo = distanceToTravel;
		
		double tryVelocity = suggestedVelocity / 2.0, tryDist = 0.0, tryStopDist = 0.0;
		while(tryVelocity <= suggestedVelocity) {
			tryDist = this.distanceTravelledToAttainSpeed(tryVelocity);
			tryStopDist = this.calculateStopDistance(tryVelocity);
			
			//System.out.println("Trying " + tryVelocity + "m/s. Need to travel " + (distanceToTravel - tryDist) + " more meters. It'll take " + tryStopDist + "m to stop.");
			
			if(tryStopDist >= (distanceToTravel - tryDist)) {
				//System.out.println("Found a good speed");
				secElapsed = timeToAttainSpeed(0, tryVelocity, TRAIN_ACCEL);
				secElapsed += timeToAttainSpeed(tryVelocity, 0, TRAIN_SERVICE_BRAKE);
				//System.out.println("It will take " + secElapsed + " seconds to travel the " + distanceToTravel + " meters.");
				return secElapsed;
			}
			else {
				if(tryDist - tryStopDist < 50) {
					tryVelocity += 0.1;
				}
				else if(tryDist - tryStopDist < 100) {
					tryVelocity += 0.5;
				}
				else {
					tryVelocity += 1.0;
				}
			}
		}
		
		// The distance is great enough that you will reach the speed limit and continue for a while.
		distanceToGo = distanceToTravel - tryDist - tryStopDist;
		secElapsed = timeToAttainSpeed(0, tryVelocity, TRAIN_ACCEL);
		secElapsed += (distanceToGo / tryVelocity);
		secElapsed += timeToAttainSpeed(tryVelocity, 0, TRAIN_SERVICE_BRAKE);
		
		//System.out.println(timeToAttainSpeed(0, tryVelocity, TRAIN_ACCEL) + "s to accelerate to speed");
		//System.out.println((distanceToGo / tryVelocity) + "s of traveling at " + tryVelocity + "m/s");
		//System.out.println(timeToAttainSpeed(tryVelocity, 0, TRAIN_SERVICE_BRAKE) + "s to brake");
		//System.out.println("It will take " + secElapsed + " seconds to travel the " + distanceToTravel + " meters.");
		return secElapsed;
	}
	
	// Returns how long it takes for a train to reach a specified velocity, given the specified acceleration
	// Accepted accelerations: TRAIN_ACCEL (normal accel), TRAIN_SERVICE_BRAKE (normal brake), TRAIN_EBRAKE (e-brake)
	public double timeToAttainSpeed(double startVelocity, double attainedVelocity, double acceleration)	{
		return Math.abs((attainedVelocity - startVelocity) / acceleration);
	}
	
	// From a stop
	public double distanceTravelledToAttainSpeed(double velocity) {
		double timeRequired = timeToAttainSpeed(0.0, velocity, TRAIN_ACCEL);
		return 0.5 * TRAIN_ACCEL * Math.pow(timeRequired, 2);
	}
	
	/// Decreases all station dwell times by the specified number of seconds
	/// Used for fine-tuning the schedule
	public void decreaseDwellTimesBy(int seconds) {
		for(StationInfo station : stations) {
			station.dwellTime -= seconds;
		}
	}
	
	/// Creates a train schedule given the required throughput and the track model
	/// If either of the above are not set, fails to create schedule
	/// Typically called at the beginning of the day (when system is started)
	public void createTrainSchedule(int throughput)	{
		TrainSchedule trainSchedule = new TrainSchedule();
		if(throughput == 0 || trackModel == null) {
			System.out.println("Throughput and track model required.");
			return;
		}
		
		this.throughput = throughput;
		int roundedMinute = 0;
		double suggestedVelocity = 11.1111111; 	// m/s, or 40 km/hr
		int from = 420;
		
		while(roundedMinute != 60) {
			roundedMinute = 0;
			int iThroughput = 0;
			double secsElapsed = 0.0;
			int currentBlock = -1, currentStationID = 0;
			StationInfo currentStation = stations.get(0);
			int increment = 1;
			trainSchedule = new TrainSchedule();
			
			while(iThroughput < throughput) {
				if(currentBlock == -1) {
					currentBlock = 77;
					currentStation = stations.get(0);
				}
				else {
					currentStationID += increment;
					currentStation = stations.get(currentStationID);
					if(currentStationID == 7) {
						increment = -1;
					} else if(currentStationID == 0) {
						increment = 1;
					}
				}
				
				TrainRoute route = ctc.calcRoute(currentBlock, currentStation.block, true, from);
				int[] routeCount = ctc.routeLength(route);
				double routeLength = routeCount[0];
				
				int[] routeArray = ctc.pathFromRoute(route, routeCount[1]);
				from = routeArray[routeArray.length - 2];
				
				secsElapsed += this.travelTimeBetweenStations((int) routeLength, suggestedVelocity);
				secsElapsed += currentStation.dwellTime;
				roundedMinute = (int) Math.round(secsElapsed / 60.0);
				currentBlock = currentStation.block;
				iThroughput++;
				trainSchedule.addStop(roundedMinute, currentBlock, currentStation.stationName, currentStation.dwellTime);
			}
			if(roundedMinute < 58) {
				decreaseDwellTimesBy(-10);
			} else if(roundedMinute < 60) {
				decreaseDwellTimesBy(-2);
			} else if(roundedMinute > 62) {
				decreaseDwellTimesBy(10);
			} else {
				decreaseDwellTimesBy(2);
			}
		}
		System.out.println(trainSchedule);
		trainSchedules.put(1, trainSchedule);		
	}
	
	public void loadTrainSchedule(String filePath) {
		File file = new File(filePath);
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			this.stations = new ArrayList<>();
			String line;
			while((line = reader.readLine()) != null) {
				String[] stuff = line.split(",");
				int block = Integer.parseInt(stuff[0]);
				String stationName = stuff[1];
				int dwellTime = Integer.parseInt(stuff[2]);
				stations.add(new StationInfo(block, stationName, dwellTime));
			}
			reader.close();
			createTrainSchedule(throughput);
		} catch(IOException e) {
			System.out.println("Uh oh, ioexception brah");
		}
	}
	
	/// Creates a crew schedule for a train
	/// Called when the system has started, or when a crew's shift has ended
	public void createCrewSchedule() {
		
	}
	
	public void tick(long systemTime) {
		this.systemTime = systemTime;
		for(Antenna reggie : reggies) {
			int trainID = reggie.getTrainID();
			currentLocations.put(trainID, reggie.getBlock());
			currentVelocities.put(trainID, reggie.getVelocity());	
			currentAuthorities.put(trainID, reggie.getAuthority());
			
			calculateSafeAuthority(trainID);
			calculateSafeSetpoint(trainID);
		}
		
		ui.setItems();
	}
	
	public static void main(String args[]) throws InterruptedException {
		//MBOUI ui = new MBOUI();
	}
	
	public class StationInfo {
		public int block;
		public String stationName;
		public int dwellTime;
		
		public StationInfo(int block, String stationName, int dwellTime) {
			this.block = block;
			this.stationName = stationName;
			this.dwellTime = dwellTime;
		}
	}
}
