package TrackController;

import java.util.*;

import CTC.CTC;
import TrackController.TrackCtrlWrapper.Train;
import TrackModel.*;

public class testPLC implements PLC{
	int startblock;
	int endblock;

	
	public HashSet<Integer> switches = new HashSet<>(Arrays.asList(9, 16, 27, 33, 38, 44, 52));
	//public HashSet<Integer> crossings = new HashSet<>(Arrays.asList(9));
	
	
	public HashSet<Integer> toploop = new HashSet<>(Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15));
	public HashSet<Integer> topstraight = new HashSet<>(Arrays.asList(17,18,19,20,21,22,23,24,25,26));
	public HashSet<Integer> A1 = new HashSet<>(Arrays.asList(28,29,30,31,32));
	public HashSet<Integer> A2 = new HashSet<>(Arrays.asList(76,75,74,73,72));
	public HashSet<Integer> midstraight = new HashSet<>(Arrays.asList(34,35,36,37));
	public HashSet<Integer> B1 = new HashSet<>(Arrays.asList(39,40,41,42,43));
	public HashSet<Integer> B2 = new HashSet<>(Arrays.asList(67,68,69,70,71));
	public HashSet<Integer> bottomstraight = new HashSet<>(Arrays.asList(45,46,47,48,49,50,51));
	public HashSet<Integer> bottomloop = new HashSet<>(Arrays.asList(53,54,55,56,57,58,59,60,61,62,63,64,65,66));


	
	public HashSet<Integer> trainsWaiting = new HashSet<Integer>();
	
	boolean firstTick = true;
	
	public int returnFive() {
		return 5;
	}
	public void setSwitch(Block switchBlock, Block destBlock){
	
	}
	
	public void addSwitches(Block[] map, UI ui) {
		if (firstTick) {
			for (Integer switchNum : switches) {
				ui.addSwitch(switchNum, map[switchNum].getSwitch().getSwitchTaken());
			}
			firstTick = false;
		}
	}
	
	public int checkRoutes(HashMap<Integer, Train> trains, UI ui) {
		if (trains.size() == 0) {
			return -1;
		}
		Iterator it = trains.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			int key = (Integer) pair.getKey();
			Train train = (Train) pair.getValue();
			
			ui.updatePosition(train.id, train.position);
						
			HashMap<String,Integer> nextSwitchInfo = findNextSwitch(train);
			if (nextSwitchInfo == null) {
				continue;
			}
			train.nextSwitch = nextSwitchInfo.get("switchBlock");
			train.blockBefore = nextSwitchInfo.get("switchOn");
			train.blockAfter = nextSwitchInfo.get("afterSwitch");
			
			trainsWaiting.add(key);
			
		}
		return 0;
	}
	
	private HashMap<String, Integer> findNextSwitch(Train train) {
		int[] route = train.suggestedRoute;
		if (route == null) {
			return null;
		}
		
		boolean posFound = false;
		int firstSwitch = -1;
		int switchIndex = -1;
		
		for (int routeStep = 0; routeStep < route.length; routeStep++) {
			//if the train is on block 77 (from yard) and is newly created, make sure the switch is correct 
			if (routeStep == 0 && train.position == 77) {
				HashMap<String,Integer> switchInfo = new HashMap<String,Integer>();
				switchInfo.put("switchOn",77);
				//comment out if 77 is not part of the route
				switchInfo.put("switchBlock",route[routeStep]);
				//comment out if 77 is part of the route
				//switchInfo.put("switchBlock",77);

				switchInfo.put("afterSwitch",route[routeStep+1]);
				
				return switchInfo;
			}
			
			//step through the route until the current train position is found
			//If the position is not found, either there are no switches on the route, or the trains position is not in the route, which means the route is newly received
			//In that case, keep track of the first switch found
			if (route[routeStep] == train.position) {
				posFound = true;
				train.routeStep = routeStep;
			}
			if (firstSwitch == -1 && switches.contains(route[routeStep])) {
				firstSwitch = route[routeStep];
				switchIndex = routeStep;
			}
			
			
			//find the first switch the train will encounter after its current position
			if (switches.contains(route[routeStep]) && posFound && (route[routeStep] != train.position)) {
				//return the block the train will occupy before it reaches the switch, and the block it will occupy after the switch
				HashMap<String,Integer> switchInfo = new HashMap<String,Integer>();
				switchInfo.put("switchOn", route[routeStep-1]);
				switchInfo.put("switchBlock", route[routeStep]);
				if (routeStep + 1 != route.length) 
					switchInfo.put("afterSwitch", route[routeStep+1]);
				else
					switchInfo.put("afterSwitch", -1);
				return switchInfo;
			}
			/*if (switches.contains(route[routeStep]) && posFound && (route[routeStep] == train.position)) {
				switchInfo.put("switchOn",);
			}*/
		}
		//if the position was found but the look didnt return, no more switches on route!
		if (posFound) {
			return null;
		}
		
		//If above loop did not return, either there are no switches, or the train's position is not in the route
		//If the latter, there will be a firstSwitch and its index stored
		if (firstSwitch != -1 && train.position == 77) {
			HashMap<String,Integer> switchInfo = new HashMap<String,Integer>();
			if (switchIndex == 0) {
				switchInfo.put("switchOn", train.position);
				switchInfo.put("switchBlock", firstSwitch);
				switchInfo.put("afterSwitch", route[1]);
			}
			else {
				switchInfo.put("switchOn", route[switchIndex-1]);
				switchInfo.put("switchBlock", route[switchIndex]);
				switchInfo.put("afterSwitch", route[switchIndex+1]);
			}
			return switchInfo;
		}
		
		return null;
	}
	
	public boolean checkSwitches(Block[] map, HashMap<Integer, Train> trains, UI ui) {
		Iterator tw = trainsWaiting.iterator();
		
		while(tw.hasNext()) {
				Integer trainNum = (Integer) tw.next();
				Train train = trains.get(trainNum);
				
				if (train.position == train.blockBefore && train.crossingWait) {
					map[train.nextSwitch].setRRStatus(true);
				}
				else if (train.position == train.blockAfter && train.crossingWait) {
					map[train.nextSwitch].setRRStatus(false);
					train.crossingWait = false;
					findNextSwitch(train);
				}
				else if (train.position == train.blockBefore) {
					int switchblock = train.nextSwitch;
					System.out.println(switchblock);
					int switchDestId = map[switchblock].getSwitch().getSwitchTaken();
					//if the train is at one of the heads of a switch and is attempting to cross the switch, ensure the switch points at that head
					if (map[train.position].getSwitchRoot() != -1) {
						//If the switch is pointing to the block the train is on, don't change the switch
						if (switchDestId == train.position) {
							//train.blockBefore = -1;
							//train.nextSwitch = -1;
							//train.blockAfter = -1;
							tw.remove();
							continue;
						}
						//if the switch is pointing the opposite direction, switch the switch
						else {
							map[switchblock].getSwitch().direction = !(map[switchblock].getSwitch().direction);
							//train.blockBefore = -1;
							//train.nextSwitch = -1;
							//train.blockAfter = -1;
							tw.remove();
							continue;

						}
					}
					
					//If the train is at the block before the root of the switch, ensure that the switch is pointing to the desired head
					else {
						//if the switch is pointing to the block the train wants to go to, don't change it
						if (switchDestId == train.blockAfter) {
							//train.blockBefore = -1;
							//train.nextSwitch = -1;
							//train.blockAfter = -1;
							tw.remove();
							continue;
						}
						//otherwise, toggle that switch
						else{
							map[switchblock].getSwitch().direction = !(map[switchblock].getSwitch().direction);
							//train.blockBefore = -1;
							//train.nextSwitch = -1;
							//train.blockAfter = -1;
							tw.remove();
							continue;

						}
					}
					//if (map[train.position] && map[switchblock].sw.getBlockOne() == train.blockAfter)
				}
		}
		
		//update all switches in the ui
		for (Integer sw : switches) {
			ui.updateSwitchPoint(sw, map[sw].getSwitch().getSwitchTaken());
		}
		return true;
	}
	
	public int getSafeSpeed(HashMap<Integer, Train> trains, Block[] map, TrackModel trackmodel) {
		if (trains.size() == 0) {
			return -1;
		}
		Iterator it = trains.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			int key = (Integer) pair.getKey();
			Train train = (Train) pair.getValue();
			
			if (train == null || train.position == 0 || map[train.position] == null) {
				continue;
			}
			//get block speed limit (in kph) and convert to meters per second
			double speedLimit = map[train.position].getSpeedLimit();
			speedLimit *= 0.2777777778;
			
			//If the train is going faster than the speed limit, decelerate
			if (train.speed > speedLimit) {
				train.speed = speedLimit;
				trackmodel.setSpeed(train.speed, train.position);
				
				return -1;
			}
			//otherwise if the train's suggested speed is higher than its current speed, but lower than the speed limit, accellerate
			else if (train.speed < train.sugSpeed && train.sugSpeed <= speedLimit) {
				train.speed = train.sugSpeed;
				trackmodel.setSpeed(train.speed, train.position);
			}
		}
		

		
		return 0;
	}
	
	public void checkTrainEmergency(Block[] map, HashMap<Integer, Train> trains, TrackModel trackmodel) {
		Iterator it = trains.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			int key = (Integer) pair.getKey();
			Train train = (Train) pair.getValue();
			
			if (train.suggestedRoute == null) {
				continue;
			}
			if (train.routeStep == train.suggestedRoute.length - 1) {
				continue;
			}
			if ((train.routeStep < train.suggestedRoute.length - 1 && (map[train.suggestedRoute[train.routeStep+1]].isTrainPresent() || map[train.suggestedRoute[train.routeStep + 1]].isBroken())) ) {
				int rs = train.routeStep;
				int pos  = train.position;
				//boolean new = train.suggestedRoute[routeStep+1];
				//boolean check = map[train.routeStep+2].isTrainPresent();
				trackmodel.setSpeed(0, train.position);
				train.speed = 0;
				train.authstop = true; 
			}
			else if ((train.routeStep < train.suggestedRoute.length - 2 && (map[train.suggestedRoute[train.routeStep+2]].isTrainPresent() || map[train.suggestedRoute[train.routeStep + 2]].isBroken()))) {
				//If there is a switch between this train and the obstructed block, determine if this train can be sent down an alternate path
				if (map[train.suggestedRoute[train.routeStep + 1]].getSwitch() != null ) {
					//if the train is on the root side of the switch, reroute to the other head. otherwise, just stop
					if (map[train.suggestedRoute[train.routeStep + 1]].getSwitch().getBlockOne() != train.position && map[train.suggestedRoute[train.routeStep + 1]].getSwitch().getBlockTwo() != train.position) {
						map[train.suggestedRoute[train.routeStep + 1]].getSwitch().direction = !map[train.suggestedRoute[train.routeStep + 1]].getSwitch().direction;
						
						train.suggestedRoute[train.routeStep + 2] = map[train.suggestedRoute[train.routeStep+1]].getSwitch().getSwitchTaken();
						continue;
					}
				}
				int rs = train.routeStep;
				int pos  = train.position;
				//boolean new = train.suggestedRoute[routeStep+1];
				//boolean check = map[train.routeStep+2].isTrainPresent();
				trackmodel.setSpeed(0, train.position);
				train.speed = 0;
				train.authstop = true; 
			}
			else if ((train.routeStep < train.suggestedRoute.length - 3 && (map[train.suggestedRoute[train.routeStep+3]].isTrainPresent() || map[train.suggestedRoute[train.routeStep + 3]].isBroken()))) {
				//If there is a switch between this train and the obstructed block, determine if this train can be sent down an alternate path
				//If there is a switch between this train and the obstructed block, determine if this train can be sent down an alternate path
				if (map[train.suggestedRoute[train.routeStep + 2]].getSwitch() != null ) {
					//if the train is on the root side of the switch, reroute to the other head. otherwise, just stop
					if (map[train.suggestedRoute[train.routeStep + 2]].getSwitch().getBlockOne() != train.suggestedRoute[train.routeStep+1] && map[train.suggestedRoute[train.routeStep + 1]].getSwitch().getBlockTwo() != train.suggestedRoute[train.routeStep+1]) {
						map[train.suggestedRoute[train.routeStep + 2]].getSwitch().direction = !map[train.suggestedRoute[train.routeStep + 2]].getSwitch().direction;
						
						train.suggestedRoute[train.routeStep + 3] = map[train.suggestedRoute[train.routeStep+2]].getSwitch().getSwitchTaken();
						continue;
					}
				}
				else if (map[train.suggestedRoute[train.routeStep + 1]].getSwitch() != null ) {
					//if the train is on the root side of the switch, reroute to the other head. otherwise, just stop
					if (map[train.suggestedRoute[train.routeStep + 1]].getSwitch().getBlockOne() != train.position && map[train.suggestedRoute[train.routeStep + 1]].getSwitch().getBlockTwo() != train.position) {
						map[train.suggestedRoute[train.routeStep + 1]].getSwitch().direction = !map[train.suggestedRoute[train.routeStep + 1]].getSwitch().direction;
						
						train.suggestedRoute[train.routeStep + 2] = map[train.suggestedRoute[train.routeStep+1]].getSwitch().getSwitchTaken();
						train.suggestedRoute[train.routeStep + 3] = train.suggestedRoute[train.routeStep+2] + 1;
						continue;
					}
				}
				
				int rs = train.routeStep;
				int pos  = train.position;
				//boolean new = train.suggestedRoute[routeStep+1];
				//boolean check = map[train.routeStep+2].isTrainPresent();
				trackmodel.setSpeed(0, train.position);
				train.speed = 0;
				train.authstop = true; 
			}
			else if (train.authstop) {
				train.authstop = false;
				train.speed = train.sugSpeed;
				trackmodel.setSpeed(train.speed, train.position);
			}
			
		}
	}
	
	public void setRegions(HashMap<Integer, Train> trains) {
		Iterator it = trains.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			int key = (Integer) pair.getKey();
			Train train = (Train) pair.getValue();
			int region = -1;
			
			if (train.position==0) {
				train.region = -1;
				continue;
			}
			else {
				if (toploop.contains(train.position))
					train.region = 0;
				else if (topstraight.contains(train.position))
					train.region = 1;
				else if (A1.contains(train.position))
					train.region = 2;
				else if (A2.contains(train.position))
					train.region = 3;
				else if (midstraight.contains(train.position))
					train.region = 4;
				else if (B1.contains(train.position))
					train.region = 5;
				else if (B2.contains(train.position))
					train.region = 6;
				else if (bottomstraight.contains(train.position))
					train.region = 7;
				else if (bottomloop.contains(train.position))
					train.region = 8;
			}
		}
	}
	
	public boolean checkRegion (int region, int switchnum, HashMap<Integer, Train> trains) {
		Iterator it = trains.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			int key = (Integer) pair.getKey();
			Train train = (Train) pair.getValue();
			
			if (train.region == region && train.nextSwitch == switchnum) {
				return true;
			}
		}
		
		return false;
	}
	
	private boolean onBranch(int firstblock, int secondblock, Block[] map, CTC myCTC){
		
		
		//find direction of initial switch 
		int increment;
		
		boolean trainpresent = false;
		
		if(map[firstblock].getSwitchRoot() == firstblock - 1 || myCTC.getTouch(firstblock, firstblock - 1) == -1){ 
			firstblock++;
			increment = 1;
		} 
		else {
			firstblock--;
			increment = -1;
		}
		
		do {
			//myWindow.setAnnouncement("looking for dest on Block " + block);
			if (firstblock == secondblock){
				return true;				
			}
			if( firstblock == 0 ||map[firstblock].isToYard() || map[firstblock].isFromYard() || firstblock >= map.length-1){
				break;
			}
			firstblock += increment;
		} while (!(map[firstblock].isToYard() || map[firstblock].isFromYard() ) && firstblock > 0 && map[firstblock].getSwitch() == null && map[firstblock].getSwitchRoot() == -1 );
		
		
		//destination might also have switch
		if (firstblock == secondblock){
			return true;
		}
		
	return false;
	}
	
	public void setCrossing(int crossingBlock) {
		
	}
	
	final class SwitchPair<K, V> implements Map.Entry<K, V> {
	    private final K key;
	    private V value;

	    public SwitchPair(K key, V value) {
	        this.key = key;
	        this.value = value;
	    }

	    @Override
	    public K getKey() {
	        return key;
	    }

	    @Override
	    public V getValue() {
	        return value;
	    }

	    @Override
	    public V setValue(V value) {
	        V old = this.value;
	        this.value = value;
	        return old;
	    }
	}

}
