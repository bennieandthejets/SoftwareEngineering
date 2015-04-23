package MBO;

import java.util.ArrayList;

public class TrainSchedule
{
	public ArrayList<Stop> stops;
	
	public TrainSchedule() {
		this.stops = new ArrayList<Stop>();
	}
	
	public void addStop(int minute, int block, String station, int dwellTime) {
		 stops.add(new Stop(minute, block, station, dwellTime));
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
		public int block;		// The block the station is located on
		public String station;	// The station to stop at
		public int dwellTime; 	// Dwell time in seconds
		
		public Stop(int minute, int block, String station, int dwellTime) {
			this.minute = minute;
			this.block = block;
			this.station = station;
			this.dwellTime = dwellTime;
		}
	}
}
