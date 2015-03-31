package com.BennieAndTheJets.TrackController;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

public class TrackCtrlWrapper {
	private static TrackController ctrl;
	private static UI ui;
	private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	private int penis = 0;
	
	public static void main (String[] args) {
		ctrl = new TrackController("sample.plc.txt");//args[0]);
		ui = new UI();
	
		ui.setvisible(true);
		
		final Runnable trainLoop = new Runnable() {
            public void run() {
            	ctrl.advanceTrain();
            	ctrl.checkSwitches();
            	ctrl.checkCrossing();
            	int pos = ctrl.getPosition();
            	ui.updatePosition(pos);
            	ui.updateSwitches();
            	ui.updateCrossing();
            }
        };
        final ScheduledFuture<?> beeperHandle = scheduler.scheduleAtFixedRate(trainLoop, 333, 333, MILLISECONDS);
		
	}
	
	public void startStop() {
		ctrl.startStop();
	}
	
	public int getSwitch(int num) {
		return ctrl.getSwitchRoot(num);
	}
	public int getSwitchHead(int num, int head) {
		return ctrl.getSwitchHead(num, head);
	}
	public int getSwitchState(int num) {
		return ctrl.getSwitchState(num);
	}
	
	public int getCrossing() {
		return ctrl.getCrossing();
	}
	public boolean getCrossingState() {
		return ctrl.getCrossingState();
	}
}