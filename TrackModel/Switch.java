package TrackModel;

public class Switch {

	int blockOne;
	int blockTwo;
	int blockRoot;
	public boolean direction;
	boolean toYardSwitch;
	boolean fromYardSwitch;
	
	public Switch(int blockRoot)
	{
		this.blockRoot = blockRoot;
	}
	
	public int getSwitchTaken()
	{
		if(direction)
			return blockTwo;
		else
			return blockOne;
	}
	
	public boolean getDirection()
	{
		return direction;
	}
	
	public int getBlockOne()
	{
		return blockOne;
	}
	
	public int getBlockTwo()
	{
		return blockTwo;
	}
	
	public int[] getSwitchBlocks()
	{
		return new int[]{blockOne, blockTwo};
	}
	
	public int getRoot()
	{
		return blockRoot;
	}
	
}
