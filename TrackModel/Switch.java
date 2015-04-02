package TrackModel;

public class Switch {

	int blockOne;
	int blockTwo;
	int blockRoot;
	String position;
	String switches;
	
	public Switch(int blockRoot, String switches)
	{
		this.blockRoot = blockRoot;
		this.switches = switches;
		//PARSE STRING SWITCHES INTO BLOCKONE AND BLOCKTWO
		//SET POSITION
	}
	
	public int[] getBlocks()
	{
		return new int[]{blockOne, blockTwo};
	}
	
	public int getRoot()
	{
		return blockRoot;
	}
	
	public String getPosition()
	{
		return position;
	}
}
