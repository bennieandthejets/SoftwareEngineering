package Simulator;

import CTC.*;
import MBO.*;
import TrackController.*;
import TrackModel.*;
import TrainController.*;
import TrainModel.*;
import java.util.Date;

/**
 * Created by Drew on 3/27/2015.
 */
public class Simulator {
    public SimulatorUI ui;
    public CTC ctc;
    public MBO mbo;
    public TrackCtrlWrapper trackControllerWrapper;
    public TrackModel trackModel;
    public TrainControllerWrapper trainControllerWrapper;
    public TrainModelWrapper trainModelWrapper;
    
    private String blockMode;
    private String operationMode;

    private boolean isRunning;
    private int speedMultiplier;
    private long systemTime;

    public Simulator() {
        this.ui = new SimulatorUI(this);
        this.isRunning = false;
        this.speedMultiplier = 1;
        this.systemTime = System.currentTimeMillis();
        
        
        this.blockMode = "fixed";
        this.blockMode = "manual";
        
        ctc = new CTC(this);
        mbo = new MBO(this);
        trainControllerWrapper = new TrainControllerWrapper(this);
        trainModelWrapper = new TrainModelWrapper(this);
        trackModel = new TrackModel(this);
        trackControllerWrapper = new TrackCtrlWrapper(this);
        ctc.setBen(trackControllerWrapper);
        mbo.setModules();
    }
    
//=====================
// SYSTEM
//=====================    
    
    /// Called from tick(), signifies one second has passed
    private void updateModules() {
        ctc.tick();
        mbo.tick(systemTime);
        trackControllerWrapper.tick();
        trackModel.tick();
        trainModelWrapper.tick();
        trainControllerWrapper.tick(systemTime);
    }
    
    /// Creates a new train in the train model wrapper, then tells other modules
    public void makeTrainPuppy() {
    	int trainID = trainModelWrapper.birthTrain();
    	mbo.trainAdded();
    	trackModel.trainBirthed(trainID);
    	trackModel.addTrain(trainID);
    }
    
    public String getBlockMode() {
    	return blockMode;
    }
    
    public String getOperationMode() {
    	return operationMode;
    }
    
    public void setBlockMode(String blockMode) {
    	this.blockMode = blockMode;
    }
    
    public void setOperationMode(String operationMode) {
    	this.operationMode = operationMode;
    }
    
//=====================
// CLOCK
//=====================

    /// The simulator ticks every (1000 / speedMultiplier) milliseconds
    public void setSpeedMultiplier(int speedMultiplier) {
        this.speedMultiplier = speedMultiplier;
    }

    public void start() {
        this.isRunning = true;
    }

    public void stop() {
        this.isRunning = false;
    }
    
    public long getTime() {
    	return systemTime;
    }

    /// Main program loop
    /// Simulates the passing of 1 second in the system
    public void tick() throws InterruptedException {
        while(true) {
            if ((isRunning)) {
                systemTime += (1000);
                updateModules();
            }
            ui.setTxtSystemTime(systemTime);
            Thread.sleep(1000 / speedMultiplier);
        }
    }

//=====================
// UIs
//=====================
    
    public void showCTCUI() {
    	ctc.displayWindow();
    }

	public void showMBOUI() {
		mbo.showUI();
	}
	
	public void showTrackControllerUI() {
		trackControllerWrapper.showUI();
	}
	
	public void showTrackModelUI() {
		trackModel.showUI();
	}
	
	public void showTrainControllerUI() {
		trainControllerWrapper.showUI();
	}	
	
	public void showTrainModelUI() {
		trainModelWrapper.showUI();
	}
	
//=====================
// MAIN
//=====================
	
    public static void main(String[] args ) throws InterruptedException {
        Simulator simulator = new Simulator();
        simulator.tick();
    }
}