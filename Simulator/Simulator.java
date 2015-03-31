package com.BennieAndTheJets.Simulator;

import com.BennieAndTheJets.CTC.CTC;
import com.BennieAndTheJets.MBO.MBO;
import com.BennieAndTheJets.MBO.TrainModel;
import com.BennieAndTheJets.TrackController.TrackController;
import com.BennieAndTheJets.TrackModel.TrackModel;
import com.BennieAndTheJets.TrainController.TrainControllerWrapper;

/**
 * Created by Drew on 3/27/2015.
 */
public class Simulator {
    private SimulatorUI ui;
    private CTC ctc;
    private MBO mbo;
    private TrackController trackController;
    private TrackModel trackModel;
    private TrainControllerWrapper trainControllerWrapper;
    private TrainModel trainModel;

    private boolean isRunning;
    private int speedMultiplier;
    private long systemTime;

    public Simulator() {
        this.ui = new SimulatorUI(this);
        this.isRunning = false;
        this.speedMultiplier = 1;
        this.systemTime = System.currentTimeMillis();
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

    public void tick() throws InterruptedException {

        while(true) {
            if ((isRunning)) {
                systemTime += (1000 / speedMultiplier);
                updateModules();
            }
            ui.setTxtSystemTime(systemTime);
            Thread.sleep(1000 / speedMultiplier);
        }
    }

    private void updateModules() {
        /*
        ctc.tick();
        mbo.tick();
        trackControllerWrapper.tick();
        trackModel.tick();
        trainModelWrapper.tick();
        trainController.tick();
        */
    }

    public static void main(String[] args ) throws InterruptedException {
        Simulator simulator = new Simulator();
        simulator.tick();
    }
}
