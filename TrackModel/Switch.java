package TrackModel;

public class Switch {

	int blockOne;
	int blockTwo;
	int blockRoot;
	
	public Switch(int blockRoot)
	{
		this.blockRoot = blockRoot;
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
