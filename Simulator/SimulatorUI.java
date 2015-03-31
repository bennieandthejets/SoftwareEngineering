<<<<<<< HEAD
package Simulator;
=======
package com.BennieAndTheJets.Simulator;
>>>>>>> 6d2b9428abe1c66a9fea66ffc5d3acf5e93ea78c

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Drew on 3/27/2015.
 */
public class SimulatorUI extends JFrame {
    private Simulator simulator;

    private JPanel rootPanel;
    private JButton CTCButton;
    private JButton MBOButton;
    private JButton trackControllerButton;
    private JButton trackModelButton;
    private JButton trainControllerButton;
    private JButton trainModelButton;
    private JSlider sliderSimulationTime;
    private JTextField txtSimulationSpeed;
    private JLabel lblSimulationSpeed;
    private JButton startSimulationButton;
    private JTextField txtSystemTime;

    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

    public SimulatorUI(Simulator simulator) {
        super("Bennie and the Jets");
        this.simulator = simulator;

        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        initComponents();

        // CHANGE SLIDER TEXT BOX
        txtSimulationSpeed.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                String value = txtSimulationSpeed.getText();
                setSliderValue(Integer.parseInt(value));
            }

            public void removeUpdate(DocumentEvent e) { }

            public void changedUpdate(DocumentEvent e) { }
        });


        // CHANGE SLIDER
        sliderSimulationTime.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                setTxtSimulationSpeed(sliderSimulationTime.getValue());
            }
        });

        // START SIMULATION
        startSimulationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(startSimulationButton.getText() == "Start") {
                    startButtonClicked();
                }
                else {
                    stopButtonClicked();
                }
            }
        });
    }

    public void startButtonClicked() {
        startSimulationButton.setText("Stop");
        simulator.start();
    }

    public void stopButtonClicked() {
        startSimulationButton.setText("Start");
        simulator.stop();
    }

    public void setTxtSystemTime(long systemTime) {
        Date date = new Date(systemTime);
        txtSystemTime.setText(timeFormat.format(date));
    }

    public void setTxtSimulationSpeed(int speed) {
        txtSimulationSpeed.setText(Integer.toString(speed));
        simulator.setSpeedMultiplier(speed);
    }

    public void setSliderValue(int speed) {
        sliderSimulationTime.setValue(speed);
        simulator.setSpeedMultiplier(speed);
    }

    public void initComponents() {

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
