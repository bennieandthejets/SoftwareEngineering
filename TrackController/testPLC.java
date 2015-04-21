package TrackController;

import java.util.*;

import TrackController.TrackCtrlWrapper.Train;
import TrackModel.*;

public class testPLC implements PLC{
	int startblock;
	int endblock;

	
	public HashSet<Integer> switches = new HashSet<>(Arrays.asList(9, 16, 27, 33, 38, 44, 52));
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
				return -1;
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
				switchInfo.put("afterSwitch", route[routeStep+1]);
				return switchInfo;
			}
			/*if (switches.contains(route[routeStep]) && posFound && (route[routeStep] == train.position)) {
				switchInfo.put("switchOn",);
			}*/
		}
		
		//If above loop did not return, either there are no switches, or the train's position is not in the route
		//If the latter, there will be a firstSwitch and its index stored
		if (firstSwitch != -1) {
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
				
				if (train.position == train.blockBefore) {
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
			
			double speedLimit = map[train.position].getSpeedLimit();
			
			if (train.speed > speedLimit) {
				train.speed = speedLimit;
				trackmodel.setSpeed(train.speed, train.position);
				
				return -1;
			}
		}
		

		
		return 0;
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
