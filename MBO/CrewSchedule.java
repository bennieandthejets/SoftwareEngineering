<<<<<<< HEAD
package MBO;
=======
package com.BennieAndTheJets.MBO;
>>>>>>> 6d2b9428abe1c66a9fea66ffc5d3acf5e93ea78c


public class CrewSchedule 
{
	private long sysTime;
	private long shiftBeginTime;
	private long shiftEndTime;
	private long breakBeginTime;
	private long breakEndTime;
	
	private String breakLocation;
	private String shiftEndLocation;
	
	public CrewSchedule(long currentTime)
	{
		this.sysTime = currentTime;
	}
	
	public void create()
	{
		
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
