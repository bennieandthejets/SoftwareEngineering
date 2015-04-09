package TrackModel;

import java.awt.Color;
import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import Simulator.Simulator;
import TrainModel.TrainModel;
import TrainModel.TrainModelWrapper;

public class TrackModel {

	Block[] blocks;	
	int trainOnBlock;
	double trainMovedDist = 0.0;
	double lastDist = 0.0;
	double totalDist;
	int prevBlock;
	Simulator s;
	TrackModelUI t;
	TrainModelWrapper trainWrap;
	TrainModel train;
	int trainID;
	ArrayList<TrainModel> trains;
	
	public TrackModel(Simulator s)
	{
		this.s = s;
		t = new TrackModelUI(this);
	}
	
	public void showUI()
	{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					t.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void tick()
	{
		getAllTrains();
		
		if (train != null) {
			findTrain();
			trainMoved();
			t.paintMap();
		}
	}
	
	public void getAllTrains()
	{
		trainWrap = s.trainModelWrapper;
		trains = trainWrap.trains;
		if(trains.size() > 0)
		{
			train = trains.get(0);
		}
	}
	
	public void trainMoved()
	{
		totalDist = train.getDistanceTraveled();
		trainMovedDist += totalDist - lastDist;
	}
	
	public void findTrain()
	{
		System.out.println("TOTAL DIST = " + totalDist);
		System.out.println("TRAIN MOVED DIST = " + trainMovedDist);
		System.out.println("BLOCK SIZE = " + blocks[trainOnBlock].blockSize);
		System.out.println("ON BLOCK " + trainOnBlock);
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		if(trainMovedDist/blocks[trainOnBlock].blockSize >= 1)
		{
			prevBlock = trainOnBlock;
			lastDist = totalDist;
			blocks[trainOnBlock].trainPresent = false;
			t.trainOffBlock(blocks[trainOnBlock].mapRow, blocks[trainOnBlock].mapCol);
			trainMovedDist = 0.0;
			if(blocks[trainOnBlock].getSwitch() != null)
			{
				if(blocks[trainOnBlock].getSwitch().blockOne == blocks[trainOnBlock].cameFrom || blocks[trainOnBlock].getSwitch().blockTwo == blocks[trainOnBlock].cameFrom)
				{
					t.trainOffSwitch(blocks[trainOnBlock].mapRow, blocks[trainOnBlock].mapCol);
					trainOnBlock++;
				}
				else
				{
					t.trainOffSwitch(blocks[trainOnBlock].mapRow, blocks[trainOnBlock].mapCol);
					trainOnBlock = blocks[trainOnBlock].getSwitch().getSwitchTaken();
				}
			}
			else if(blocks[trainOnBlock].switchRoot != -1 && blocks[blocks[trainOnBlock].switchRoot].sw.blockOne == trainOnBlock)
			{
				t.trainOffSwitch(blocks[trainOnBlock].mapRow, blocks[trainOnBlock].mapCol);
				trainOnBlock++;
			}
			else
			{
				trainOnBlock++;
			}
			blocks[trainOnBlock].cameFrom = prevBlock;
			blocks[trainOnBlock].trainPresent = true;
			t.trainOnBlock(blocks[trainOnBlock].mapRow, blocks[trainOnBlock].mapCol);
		}
	}
	
	public Block[] importTrack(String inputFile) throws IOException
	{
		//read in info for blocks
		ExcelParser ex = new ExcelParser("src//" + inputFile);
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
				if(row.get(6).length() > 1)
				{
					blocks[count].station = row.get(6);
					blocks[count].beacon = new Beacon(count, this, s);
					blocks[count].stationSide = row.get(15);
				}
				blocks[count].toYard = false;
				blocks[count].fromYard = false;
				if(row.get(7).equals("TRUE"))
				{
					blocks[count].sw = new Switch(count);
				}
				else if(row.get(7).equals("TO YARD"))
				{
					blocks[count].toYard = true;
					blocks[count].sw = new Switch(count);
				}
				else if(row.get(7).equals("FROM YARD"))
				{
					blocks[count].fromYard = true;
					trainOnBlock = count;
					blocks[count].sw = new Switch(count);
				}
				else if(row.get(7).equals("TO YARD/FROM YARD"))
				{
					blocks[count].toYard = true;
					blocks[count].fromYard = true;
					trainOnBlock = count;
					blocks[count].sw = new Switch(count);
				}
				else
					blocks[count].sw = null;
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
					blocks[count].getSwitch().direction = false;
				}
				blocks[count].switchRoot = -1;
			}
		}
			
		for(int i = 1; i < blocks.length; i++)
		{
			if(blocks[i] != null && blocks[i].getSwitch() != null)
			{
				blocks[blocks[i].getSwitch().blockOne].switchRoot = i;
				blocks[blocks[i].getSwitch().blockTwo].switchRoot = i;
			}
		}
		
		//read in map file for UI
		String mapFile = "";
				
		if(inputFile.equals("TrackLayoutGreenLine.csv"))
			mapFile = "greenmap.txt";
		else if(inputFile.equals("TrackLayoutRedLine.csv"))
			mapFile = "redmap.txt";
			
		Scanner map = new Scanner(new File("src//" + mapFile));
				
		ArrayList<ArrayList<String>> mapRows = new ArrayList<ArrayList<String>>();
			
		while(map.hasNextLine())
		{
			String col = map.nextLine();
			ArrayList<String> mapCol = new ArrayList<String>();
			
			String[] colArray = col.split("\\s+");
			
			for(int i=0; i<colArray.length; i++)
			{
				mapCol.add(colArray[i]);
			}
			mapRows.add(mapCol);
		}

		Object[][] data = new Object[mapRows.size()][mapRows.get(0).size()];
		    
		for(int i=0; i<mapRows.size(); i++)
		{
			ArrayList<String> mapCol = mapRows.get(i);
				
			for(int j=0; j<mapCol.size(); j++)
			{
				if(mapCol.get(j).equals("x"))
					data[i][j] = new Color(255,255,255);
				else if(mapCol.get(j).equals("Y"))
					data[i][j] = new Color(255, 128, 0);
				else if(mapCol.get(j).equals("S"))
					data[i][j] = new Color(255, 0, 191);
				else if(blocks[Integer.parseInt(mapCol.get(j))].switchRoot != -1)
				{
					if(blocks[blocks[Integer.parseInt(mapCol.get(j))].switchRoot].sw.blockTwo == Integer.parseInt(mapCol.get(j)))
					{
						data[i][j] = new Color(0,9,255);
					}
					else
					{
						data[i][j] = new Color(143,105,255);
					}
					blocks[Integer.parseInt(mapCol.get(j))].mapRow = i;
					blocks[Integer.parseInt(mapCol.get(j))].mapCol = j;
				}
				else
				{
					data[i][j] = new Color(143,105,255);
					int blockNum = Integer.parseInt(mapCol.get(j));
					if(blocks[blockNum].getSwitch() != null)
					{
						data[i][j] = new Color(0,9,255);
					}		
					blocks[Integer.parseInt(mapCol.get(j))].mapRow = i;
					blocks[Integer.parseInt(mapCol.get(j))].mapCol = j;
				}
			}
		}
		
		t.addMap(data);
		map.close();
				
				
		//trainWrap = s.trainModelWrapper;
		//trainID = trainWrap.birthTrain();
		//train = trainWrap.getTrain(trainID);
		//s.start();
		
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
