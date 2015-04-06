package Simulator;

import CTC.*;
import MBO.*;
import MBO.*;
import TrackController.*;
import TrackModel.*;
import TrainController.*;
import TrainModel.*;

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

    private boolean isRunning;
    private int speedMultiplier;
    private long systemTime;

    public Simulator() {
        this.ui = new SimulatorUI(this);
        this.isRunning = false;
        this.speedMultiplier = 1;
        this.systemTime = System.currentTimeMillis();
        
        ctc = new CTC(this);
        mbo = new MBO(this);
        trackControllerWrapper = new TrackCtrlWrapper();
        trackModel = new TrackModel();
        trainControllerWrapper = new TrainControllerWrapper(this);
        trainModelWrapper = new TrainModelWrapper();
    }

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

    private void updateModules() {
        ctc.tick();
        mbo.tick();
        //trackControllerWrapper.tick();
        //trackModel.tick();
        trainModelWrapper.tick();
        trainControllerWrapper.tick();
    }

    public static void main(String[] args ) throws InterruptedException {
        Simulator simulator = new Simulator();
        simulator.tick();
    }
}
