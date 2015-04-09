package TrackModel;

public class Switch {

	int blockOne;
	int blockTwo;
	int blockRoot;
	int direction;
	
	public Switch(int blockRoot)
	{
		this.blockRoot = blockRoot;
	}
	
	public int getSwitchTaken()
	{
		if(direction == 1)
			return blockOne;
		else
			return blockTwo;
	}
	
	public int getDirection()
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
