package TrackController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import TrackController.TrackCtrlWrapper.Train;
import TrackModel.Block;

public class redBottomPLC {
	int startblock = 39;
	int endblock = 77;

	
	public HashSet<Integer> switches = new HashSet<>(Arrays.asList(15, 27, 32, 38, 43, 52));
	public HashSet<Integer> trainsWaiting = new HashSet<Integer>();
	
	public int returnFive() {
		return 5;
	}
	public void setSwitch(Block switchBlock, Block destBlock){
	
	}
	public boolean checkRoutes(HashMap<Integer, Train> trains) {
		if (trains.size() == 0) {
			return false;
		}
		Iterator it = trains.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			Train train = (Train) pair.getValue();
			
			//If the train is not within the blocks governed by this PLC, don't check its route
			if (train.position < startblock || train.position > endblock) {
				continue;
			}
			HashMap<String,Integer> nextSwitchInfo = findNextSwitch(train);
			if (nextSwitchInfo == null) {
				return false;
			}
			train.nextSwitch = nextSwitchInfo.get("switchBlock");
			train.blockBefore = nextSwitchInfo.get("switchOn");
			train.blockAfter = nextSwitchInfo.get("afterSwitch");
			
		}
		return true;
	}
	
	private HashMap<String, Integer> findNextSwitch(Train train) {
		int[] route = train.suggestedRoute;
		if (route == null) {
			return null;
		}
		for (int routeStep = 0; routeStep < route.length; routeStep++) {
			//step through the route until the current train position is found
			boolean posFound = false;
			if (route[routeStep] == train.position) {
				posFound = true;
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
		}
		
		return null;
	}
	
	public boolean checkSwitches(Block[] map) {
		Iterator tw = trainsWaiting.iterator();
		
		while(tw.hasNext()) {
				Train train = (Train) tw.next();
				
				//once again, ignore trains that are not within this plc's governance
				if (train.position < startblock || train.position > endblock) {
					continue;
				}
				
				if (train.position == train.blockBefore) {
					int switchblock = train.nextSwitch;
					int switchDestId = map[switchblock].getSwitch().getSwitchTaken();
					//if the train is at one of the heads of a switch and is attempting to cross the switch, ensure the switch points at that head
					if (map[switchblock].getSwitchRoot() != -1) {
						//If the switch is pointing to the block the train is on, don't change the switch
						if (switchDestId == train.position) {
							continue;
						}
						//if the switch is pointing the opposite direction, switch the switch
						else {
							map[switchblock].getSwitch().direction = !(map[switchblock].getSwitch().direction);
						}
					}
					
					//If the train is at the block before the root of the switch, ensure that the switch is pointing to the desired head
					else {
						//if the switch is pointing to the block the train wants to go to, don't change it
						if (switchDestId == train.blockAfter) {
							continue;
						}
						//otherwise, toggle that switch
						else{
							map[switchblock].getSwitch().direction = !(map[switchblock].getSwitch().direction);
						}
					}
					//if (map[train.position] && map[switchblock].sw.getBlockOne() == train.blockAfter)
				}
		}
		
		return true;
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
