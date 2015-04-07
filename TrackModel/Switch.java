package TrackModel;

public class Switch {

	int blockOne;
	int blockTwo;
	int blockRoot;
	String position;
	
	public Switch(int blockRoot)
	{
		this.blockRoot = blockRoot;
		//PARSE STRING SWITCHES INTO BLOCKONE AND BLOCKTWO
		//SET POSITION
	}
	
	public int[] getSwitchBlocks()
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
