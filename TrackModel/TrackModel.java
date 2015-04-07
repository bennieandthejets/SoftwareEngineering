package TrackModel;

import java.io.IOException;
import java.util.ArrayList;

public class TrackModel {

	Block[] blocks;	
		

	public double trainMovedDistance()
	{
		//find out how far train has moved
		//get deploy time and place from reggie
		//get speed constantly from reggie
		//determine how far train has moved based on speed and position
		//call findTrain
		
		return 0.0;
	}
	
	public void findTrain()
	{
		
	}
	
	public Block[] importTrack(String inputFile) throws IOException
	{
		ExcelParser ex = new ExcelParser(inputFile);
		ArrayList<ArrayList<String>> line = ex.getLine();
		
		blocks = new Block[line.size()];
		
		for (int count = 1; count < line.size(); count++) {
			ArrayList<String> row = line.get(count);
			if (row.size() != 0) {
				blocks[count] = new Block(count);
				blocks[count].lineColor = row.get(0);
				blocks[count].section = row.get(1);
				blocks[count].blockID = Integer.parseInt(row.get(2));
				blocks[count].blockSize = Double.parseDouble(row.get(3));
				blocks[count].grade = Double.parseDouble(row.get(4));
				blocks[count].speedLimit = Integer.parseInt(row.get(5));
				blocks[count].station = row.get(6);
				blocks[count].toYard = false;
				blocks[count].fromYard = false;
				if(row.get(7).equals("TRUE"))
					blocks[count].sw = new Switch(count);
				else if(row.get(7).equals("TO YARD"))
				{
					blocks[count].sw = new Switch(count);
					blocks[count].toYard = true;
				}
				else if(row.get(7).equals("FROM YARD"))
				{
					blocks[count].sw = new Switch(count);
					blocks[count].fromYard = true;
				}
				else if(row.get(7).equals("TO YARD/FROM YARD"))
				{
					blocks[count].sw = new Switch(count);
					blocks[count].toYard = true;
					blocks[count].fromYard = true;
				}
				if(row.get(8).equals("TRUE"))
					blocks[count].underground = true;
				else
					blocks[count].underground = false;
				if(row.get(9).equals("TRUE"))
					blocks[count].rrCrossing = true;
				else
					blocks[count].rrCrossing = false;
				blocks[count].elevation = Double.parseDouble(row.get(10));
				blocks[count].cumElevation = Double.parseDouble(row.get(11));
				if(blocks[count].getSwitch() != null)
				{
					blocks[count].getSwitch().blockRoot = blocks[count].blockID;
					blocks[count].getSwitch().blockOne = Integer.parseInt(row.get(12));
					blocks[count].getSwitch().blockTwo = Integer.parseInt(row.get(13));
				}
			}
		}
			
		return blocks;
	}
	
	public Block[] getBlocks()
	{
		return blocks;
	}
	
	public void breakTrack(int blockID)
	{
		blocks[blockID].isBroken = !blocks[blockID].isBroken();
	}
	
	public void circuitFail(int blockID)
	{
		blocks[blockID].isFailed = !blocks[blockID].isFailed();
	}
	
}
