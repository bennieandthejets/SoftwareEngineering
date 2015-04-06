package MBO;

public class CrewSchedule 
{
	private long shiftBeginTime;
	private long shiftEndTime;
	private long breakBeginTime;
	private long breakEndTime;
	
	private String breakLocation;
	private String shiftEndLocation;
	
	public CrewSchedule(long startTime)
	{
		this.shiftBeginTime = startTime;
		this.shiftEndTime = startTime + 1000 * (60 * 60 * 8);
		this.breakBeginTime = startTime + 1000 * (60 * 60 * 4);
		this.breakEndTime = this.breakEndTime + 1000 * (60 * 30);
	}

	private long getShiftBeginTime() 
	{
		return shiftBeginTime;
	}
	
	private long getShiftEndTime()
	{
		return shiftEndTime;
	}
	
	private long getBreakBeginTime()
	{
		return breakBeginTime;
	}
	
	private long getBreakEndTime()
	{
		return breakEndTime;
	}	
	
	private String getBreaktLocation()
	{
		return breakLocation;
	}
	
	private String getShiftEndLocation()
	{
		return shiftEndLocation;
	}
}
