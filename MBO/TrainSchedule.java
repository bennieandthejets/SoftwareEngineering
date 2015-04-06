package MBO;

import java.util.ArrayList;

public class TrainSchedule
{
	public ArrayList<Stop> stops;
	
	public TrainSchedule() {
		this.stops = new ArrayList<Stop>();
	}
	
	public void addStop(int minute, String station) {
		//stops.add(new Stop(minute, station));
	}
	
	public void addStop(int minute, String station, int dwellTime) {
		stops.add(new Stop(minute, station, dwellTime));
	}
	
	public String toString() {
		String str = "";
		for(Stop stop : stops) {
			if(stop.minute < 10) {
				str += "08:0" + stop.minute + " " + stop.station + ", dwell " + stop.dwellTime + "s\n";
			}
			else {
				str += "08:" + stop.minute + " " + stop.station + ", dwell " + stop.dwellTime + "s\n";
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
		public int minute;		// Minute the train will arrive (rounded down)
		public String station;	// The station to stop at
		public int dwellTime; 	// Dwell time in seconds
		
		public Stop(int minute, String station, int dwellTime) {
			this.minute = minute;
			this.station = station;
			this.dwellTime = dwellTime;
		}
	}
}
