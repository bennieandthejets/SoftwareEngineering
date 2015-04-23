package TrackModel;

import java.awt.Color;
import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import Simulator.Simulator;
import TrainModel.TrainModel;
import TrainModel.TrainModelWrapper;

public class TrackModel {

	Block[] blocks;
	HashMap<Integer, Integer> trainOnBlock = new HashMap<Integer, Integer>();
	Simulator s;
	TrackModelUI t;
	HashMap<Integer, Double> totalBlockDist = new HashMap<Integer, Double>();
	HashMap<Integer, Double> totalTrainDist = new HashMap<Integer, Double>();
	HashMap<Integer, ArrayList<Integer>> allRoutes = new HashMap<Integer, ArrayList<Integer>>();

	public TrackModel(Simulator s) {
		this.s = s;
		t = new TrackModelUI(this);
	}

	public void showUI() {
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

	public void tick() {
		ArrayList<Integer> keys = new ArrayList<Integer>(allRoutes.keySet());
		for (int k : keys) {
			trainMoved(k);
			findTrain(k);
			t.paintMap();
		}
		for(int i=1; i<blocks.length; i++)
		{
			if(blocks[i].beacon != null)
				blocks[i].getBeacon().tick();
		}
	}

	public void setSpeed(double speed, int blockID) {
		ArrayList<Integer> trains = new ArrayList<Integer>(allRoutes.keySet());
		for (int train : trains) {
			if (trainOnBlock.get(train) == blockID) {
				TrainModel trainM = s.trainModelWrapper.getTrain(train);
				trainM.setVelocity(speed);
			}
		}
	}

	public void setAuthority(double authority, int blockID) {
		ArrayList<Integer> trains = new ArrayList<Integer>(allRoutes.keySet());
		for (int train : trains) {
			if (trainOnBlock.get(train) == blockID) {
				TrainModel trainM = s.trainModelWrapper.getTrain(train);
				trainM.setAuthority(authority);
			}
		}
	}

	public void addTrain(int trainID) {
		allRoutes.put(trainID, new ArrayList<Integer>());
	}

	public void trainBirthed(int trainID) {
		for (int i = 1; i < blocks.length; i++) {
			if (blocks[i] != null && blocks[i].fromYard) {
				trainOnBlock.put(trainID, i);
				blocks[i].trainPresent = true;
				allRoutes.put(trainID, new ArrayList<Integer>());
				t.trainOnBlock(blocks[i].mapRow, blocks[i].mapCol);
			}
		}
	}

	public void trainMoved(int trainID) {
		TrainModel trainM = s.trainModelWrapper.getTrain(trainID);
		totalTrainDist.put(trainID, trainM.getDistanceTraveled());
	}
	
	public void findTrain(int trainID) {
		/*
		 * System.out.println("TOTAL DIST = " + totalTrainDist.get(trainID));
		 * System.out.println("TRAIN MOVED DIST = " +
		 * totalBlockDist.get(trainID)); System.out.println("BLOCK SIZE = " +
		 * blocks[trainOnBlock.get(trainID)].blockSize);
		 * System.out.println("ON BLOCK " + trainOnBlock.get(trainID));
		 * System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		 */
		double blockDist = 0.0;

		ArrayList<Integer> travelRoute = allRoutes.get(trainID);

		for (int i = 0; i < travelRoute.size(); i++) {
			blockDist += blocks[travelRoute.get(i)].blockSize;
		}
		totalBlockDist.put(trainID, blockDist);

		if (s.trainModelWrapper.getTrain(trainID).getBlockDistance() > blocks[trainOnBlock.get(trainID)].blockSize){
			blocks[trainOnBlock.get(trainID)].trainPresent = false;
			blocks[trainOnBlock.get(trainID)].trainID = -1;
			travelRoute.add(trainOnBlock.get(trainID));
			
			t.trainOffBlock(blocks[trainOnBlock.get(trainID)].mapRow,
					blocks[trainOnBlock.get(trainID)].mapCol);

			int prevBlock;
			if (travelRoute.size() == 1)
				prevBlock = -9; // arbitrarily chose this number, i just really
								// like the #9
			else
				prevBlock = travelRoute.get(travelRoute.size() - 2);

			Switch sw = blocks[trainOnBlock.get(trainID)].getSwitch();

			if (sw != null) {
				if (sw.getSwitchBlocks()[0] != prevBlock
						&& sw.getSwitchBlocks()[1] != prevBlock) {
					trainOnBlock.put(trainID, sw.getSwitchTaken());
				} else {
					if (sw.getSwitchBlocks()[0] == (trainOnBlock.get(trainID) + 1)
							|| sw.getSwitchBlocks()[1] == (trainOnBlock
									.get(trainID) + 1)) {
						int currBlock = trainOnBlock.get(trainID);
						trainOnBlock.put(trainID, currBlock - 1);
					} else {
						int currBlock = trainOnBlock.get(trainID);
						trainOnBlock.put(trainID, currBlock + 1);
					}
				}
			} else if (blocks[trainOnBlock.get(trainID)].getSwitchRoot() > -1) {
				if (prevBlock != blocks[trainOnBlock.get(trainID)]
						.getSwitchRoot()) {
					int nextBlock = blocks[trainOnBlock.get(trainID)]
							.getSwitchRoot();
					trainOnBlock.put(trainID, nextBlock);
				} else {
					if(s.ctc.getTouch(trainOnBlock.get(trainID), trainOnBlock.get(trainID) + 1) == -1 || prevBlock == trainOnBlock.get(trainID) + 1)
					{
						int nextBlock = trainOnBlock.get(trainID) - 1;
						if(nextBlock < 1)
							nextBlock = nextBlock + 2;
						trainOnBlock.put(trainID, nextBlock);
					}
					else
					{
						int nextBlock = trainOnBlock.get(trainID) + 1;
						trainOnBlock.put(trainID, nextBlock);
					}
				}
			} else {
				if (prevBlock > trainOnBlock.get(trainID)) {
					int currBlock = trainOnBlock.get(trainID);
					trainOnBlock.put(trainID, currBlock - 1);
				} else {
					int currBlock = trainOnBlock.get(trainID);
					trainOnBlock.put(trainID, currBlock + 1);
				}
			}

			blocks[trainOnBlock.get(trainID)].trainPresent = true;
			blocks[trainOnBlock.get(trainID)].trainID = trainID;
			t.trainOnBlock(blocks[trainOnBlock.get(trainID)].mapRow,
					blocks[trainOnBlock.get(trainID)].mapCol);
			//HERE YOU GO, REGGIE. blocks[trainOnBlock.get(trainID)];
			s.trainModelWrapper.getTrain(trainID).setBlock(blocks[trainOnBlock.get(trainID)]);
		}
		
		System.out.println(trainOnBlock.get(trainID));
		System.out.println(travelRoute);
		allRoutes.put(trainID, travelRoute);

	}

	public Block[] importTrack(String inputFile) throws IOException {
		// read in info for blocks
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
				if (row.get(6).length() > 1) {
					blocks[count].station = row.get(6);
					blocks[count].beacon = new Beacon(count, this, s);
					blocks[count].stationSide = row.get(15);
				}
				blocks[count].toYard = false;
				blocks[count].fromYard = false;
				if (row.get(7).equals("TRUE")) {
					blocks[count].sw = new Switch(count);
					blocks[count].sw.toYardSwitch = false;
					blocks[count].sw.fromYardSwitch = false;
				} else if (row.get(7).equals("TO YARD")) {
					blocks[count].sw = new Switch(count);
					blocks[count].sw.toYardSwitch = true;
					blocks[count].sw.fromYardSwitch = false;
				} else if (row.get(7).equals("FROM YARD")) {
					blocks[count].sw = new Switch(count);
					blocks[count].sw.fromYardSwitch = true;
					blocks[count].sw.toYardSwitch = false;
				} else if (row.get(7).equals("TO YARD/FROM YARD")) {
					blocks[count].sw = new Switch(count);
					blocks[count].sw.toYardSwitch = true;
					blocks[count].sw.fromYardSwitch = true;
				} else
					blocks[count].sw = null;
				if (row.get(8).equals("TRUE"))
					blocks[count].underground = true;
				else
					blocks[count].underground = false;
				if (row.get(9).equals("TRUE"))
					blocks[count].rrCrossing = true;
				else
					blocks[count].rrCrossing = false;
				blocks[count].elevation = Double.parseDouble(row.get(10));
				blocks[count].cumElevation = Double.parseDouble(row.get(11));
				if (blocks[count].getSwitch() != null) {
					blocks[count].getSwitch().blockRoot = blocks[count].blockID;
					blocks[count].getSwitch().blockOne = Integer.parseInt(row
							.get(12));
					blocks[count].getSwitch().blockTwo = Integer.parseInt(row
							.get(13));
					blocks[count].getSwitch().direction = false;
				}
				blocks[count].switchRoot = -1;
			}
		}

		for (int i = 1; i < blocks.length; i++) {
			if (blocks[i] != null && blocks[i].getSwitch() != null) {
				if (blocks[i].getSwitch().fromYardSwitch) {
					blocks[blocks[i].getSwitch().blockTwo].fromYard = true;
				}
				if (blocks[i].getSwitch().toYardSwitch) {
					blocks[blocks[i].getSwitch().blockTwo].toYard = true;
				}
				blocks[blocks[i].getSwitch().blockOne].switchRoot = i;
				blocks[blocks[i].getSwitch().blockTwo].switchRoot = i;
			}
		}

		// read in map file for UI
		String mapFile = "";

		if (inputFile.equals("Green.csv"))
			mapFile = "greenmap.txt";
		else if (inputFile.equals("Red.csv"))
			mapFile = "redmap.txt";

		Scanner map = new Scanner(new File("src//" + mapFile));

		ArrayList<ArrayList<String>> mapRows = new ArrayList<ArrayList<String>>();

		while (map.hasNextLine()) {
			String col = map.nextLine();
			ArrayList<String> mapCol = new ArrayList<String>();

			String[] colArray = col.split("\\s+");

			for (int i = 0; i < colArray.length; i++) {
				mapCol.add(colArray[i]);
			}
			mapRows.add(mapCol);
		}

		Object[][] data = new Object[mapRows.size()][mapRows.get(0).size()];

		for (int i = 0; i < mapRows.size(); i++) {
			ArrayList<String> mapCol = mapRows.get(i);

			for (int j = 0; j < mapCol.size(); j++) {
				if (mapCol.get(j).equals("x"))
					data[i][j] = new Color(255, 255, 255);
				else if (mapCol.get(j).equals("Y"))
					data[i][j] = new Color(255, 128, 0);
				else if (mapCol.get(j).equals("S"))
					data[i][j] = new Color(255, 0, 191);
				else if (blocks[Integer.parseInt(mapCol.get(j))].switchRoot != -1) {
					if (blocks[blocks[Integer.parseInt(mapCol.get(j))].switchRoot].sw.blockTwo == Integer
							.parseInt(mapCol.get(j))) {
						data[i][j] = new Color(0, 9, 255);
					} else {
						data[i][j] = new Color(143, 105, 255);
					}
					blocks[Integer.parseInt(mapCol.get(j))].mapRow = i;
					blocks[Integer.parseInt(mapCol.get(j))].mapCol = j;
				} else {
					data[i][j] = new Color(143, 105, 255);
					int blockNum = Integer.parseInt(mapCol.get(j));
					if (blocks[blockNum].getSwitch() != null) {
						data[i][j] = new Color(0, 9, 255);
					}
					blocks[Integer.parseInt(mapCol.get(j))].mapRow = i;
					blocks[Integer.parseInt(mapCol.get(j))].mapCol = j;
				}
			}
		}

		t.addMap(data);
		map.close();

		// FOR TESTING
		// trainWrap = s.trainModelWrapper;
		// trainID = trainWrap.birthTrain();
		// train = trainWrap.getTrain(trainID);
		// s.start();

		return blocks;
	}

	public Block[] getBlocks() {
		return blocks;
	}

	public void breakTrack(int blockID) {
		blocks[blockID].isBroken = !blocks[blockID].isBroken();
	}

	public void circuitFail(int blockID) {
		blocks[blockID].isFailed = !blocks[blockID].isFailed();
	}

}