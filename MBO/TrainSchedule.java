package MBO;

import java.util.ArrayList;

public class TrainSchedule
{
	public ArrayList<Stop> stops;
	
	public TrainSchedule() {
		this.stops = new ArrayList<Stop>();
	}
	
	public void addStop(int minute, String station) {
		stops.add(new Stop(minute, station));
	}
	
	/*public void removeRange(int start) {
		stops.
		for(int i = start; i < end; i++) {
			stops.remove(i);
		}
	}*/
	
	public String toString() {
		String str = "";
		for(Stop stop : stops) {
			if(stop.minute < 10) {
				str += "08:0" + stop.minute + " " + stop.station + "\n";
			}
			else {
				str += "08:" + stop.minute + " " + stop.station + "\n";
			}
		}
		return str;
	}
	
	public void printSchedule()	{
		for(Stop stop : stops) {
			if(stop.minute < 10) {
				System.out.println("08:0" + stop.minute + " " + stop.station);
			}
			else {
				System.out.println("08:" + stop.minute + " " + stop.station);
			}
		}
	}
	
	public class Stop {
		public int minute;
		public String station;
		
		public Stop(int minute, String station) {
			this.minute = minute;
			this.station = station;
		}
	}
}
