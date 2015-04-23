package Simulator;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JLabel;

public class SimulatorUI {

	private final Simulator simulator;
	private JFrame frmBennieAndThe;
	
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    private JTextField txtSimulatorSpeed;
    private JTextField txtSystemTime;
    private JSlider sliderSimulationTime;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//SimulatorUI window = new SimulatorUI();
					//window.frmBennieAndThe.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @wbp.parser.entryPoint
	 */
	public SimulatorUI(Simulator simulator) {
		initialize();
		this.simulator = simulator;
		
        // CHANGE SLIDER TEXT BOX
        /*txtSimulatorSpeed.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
            	txtSimulatorSpeedChanged();
            }

            public void removeUpdate(DocumentEvent e) { }

            public void changedUpdate(DocumentEvent e) { }
        });*/


        // CHANGE SLIDER
        sliderSimulationTime.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                setTxtSimulationSpeed(sliderSimulationTime.getValue());
            }
        });
        
        this.frmBennieAndThe.setVisible(true);
	}
	
//=====================
// Change Methods
//=====================	
	
	/// Start ticking
    public void startButtonClicked() {
        simulator.start();
    }

    /// Stop ticking
    public void stopButtonClicked() {
        simulator.stop();
    }
    
    /// Called from the system to update the time text box
    public void setTxtSystemTime(long systemTime) {
        Date date = new Date(systemTime);
        txtSystemTime.setText(timeFormat.format(date));
    }
    
    /// Change the slider position based on text box
    public void txtSimulatorSpeedChanged() {
        String value = txtSimulatorSpeed.getText();
        setSliderValue(Integer.parseInt(value));
    }

    /// Change the text box based on slider position
    public void setTxtSimulationSpeed(int speed) {
        txtSimulatorSpeed.setText(Integer.toString(speed));
        simulator.setSpeedMultiplier(speed);
    }

    public void setSliderValue(int speed) {
        sliderSimulationTime.setValue(speed);
        simulator.setSpeedMultiplier(speed);
    }
    
//=====================
// INIT
//=====================
   
	private void initialize() {
		frmBennieAndThe = new JFrame();
		frmBennieAndThe.setTitle("Bennie and the Jets");
		frmBennieAndThe.setBounds(100, 100, 398, 355);
		frmBennieAndThe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmBennieAndThe.getContentPane().setLayout(null);
		
		JButton ctcButton = new JButton("CTC");
		ctcButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				simulator.showCTCUI();
			}
		});
		ctcButton.setBounds(10, 11, 109, 28);
		frmBennieAndThe.getContentPane().add(ctcButton);
		
		JButton mboButton = new JButton("MBO");
		mboButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				simulator.showMBOUI();
			}
		});
		mboButton.setBounds(10, 50, 109, 28);
		frmBennieAndThe.getContentPane().add(mboButton);
		
		JButton trackControllerButton = new JButton("Track Controller");
		trackControllerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				simulator.showTrackControllerUI();
			}
		});
		trackControllerButton.setBounds(10, 89, 109, 28);
		frmBennieAndThe.getContentPane().add(trackControllerButton);
		
		JButton trackModelButton = new JButton("Track Model");
		trackModelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				simulator.showTrackModelUI();
			}
		});
		trackModelButton.setBounds(10, 128, 109, 28);
		frmBennieAndThe.getContentPane().add(trackModelButton);
		
		JButton trainControllerButton = new JButton("Train Controller");
		trainControllerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				simulator.showTrainControllerUI();
			}
		});
		trainControllerButton.setBounds(10, 167, 109, 28);
		frmBennieAndThe.getContentPane().add(trainControllerButton);
		
		JButton trainModelButton = new JButton("Train Model");
		trainModelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				simulator.showTrainModelUI();
			}
		});
		trainModelButton.setBounds(10, 206, 109, 28);
		frmBennieAndThe.getContentPane().add(trainModelButton);
		
		sliderSimulationTime = new JSlider();
		sliderSimulationTime.setValue(1);
		sliderSimulationTime.setMinimum(1);
		sliderSimulationTime.setMaximum(50);
		sliderSimulationTime.setBounds(129, 62, 180, 23);
		frmBennieAndThe.getContentPane().add(sliderSimulationTime);
		
		final JButton startButton = new JButton("Start");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(startButton.getText() == "Start") {
					startButtonClicked();
					startButton.setText("Stop");
				}
				else {
					stopButtonClicked();
					startButton.setText("Start");
				}
			}
		});
		startButton.setBounds(263, 89, 109, 28);
		frmBennieAndThe.getContentPane().add(startButton);
		
		txtSimulatorSpeed = new JTextField();
		txtSimulatorSpeed.setText("1");
		txtSimulatorSpeed.setBounds(267, 42, 105, 20);
		frmBennieAndThe.getContentPane().add(txtSimulatorSpeed);
		txtSimulatorSpeed.setColumns(10);
		
		txtSystemTime = new JTextField();
		txtSystemTime.setEditable(false);
		txtSystemTime.setColumns(10);
		txtSystemTime.setBounds(267, 12, 105, 20);
		frmBennieAndThe.getContentPane().add(txtSystemTime);
		
		JLabel label = new JLabel("");
		label.setBounds(129, 135, 46, 14);
		frmBennieAndThe.getContentPane().add(label);
		
		JLabel lblWave = new JLabel("");
		String pth ="/little wave.png";
		lblWave.setIcon(new ImageIcon(pth));
		
		lblWave.setBounds(129, 122, 259, 193);
		frmBennieAndThe.getContentPane().add(lblWave);
		
	}
}
