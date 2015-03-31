package CTC;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class fakeWindow {

	public JFrame frmCtcDemo;
	public JTextField txtBlock;
	public JTextField txtDest;
	public JTextField txtSpeedSuggest;
	private JCheckBox checkBox;
	private JCheckBox checkBox_0;
	private JCheckBox checkBox_1;
	private JCheckBox checkBox_2;
	private JCheckBox checkBox_3;
	private JCheckBox checkBox_4;
	private JCheckBox checkBox_5;
	private JCheckBox checkBox_6;
	private JCheckBox checkBox_7;
	private JCheckBox checkBox_8;
	private JCheckBox checkBox_9;
	private JCheckBox checkBox_10;
	private JCheckBox checkBox_11;
	private JCheckBox checkBox_12;
	private JCheckBox checkBox_13;
	private JCheckBox checkBox_14;
	private JCheckBox checkBox_15;
	private JCheckBox checkBox_16;
	private JCheckBox checkBox_17;
	private JCheckBox checkBox_18;
	public JTextField txtPath;
	private JTextField txtSpawn;
	private JLabel lblSpawn;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					fakeWindow window = new fakeWindow();
					window.frmCtcDemo.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public fakeWindow() {
		initialize();
	}
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCtcDemo = new JFrame();
		frmCtcDemo.setTitle("CTC demo - Fake Track Controler and/or MBO");
		frmCtcDemo.setBounds(100, 100, 411, 368);
		frmCtcDemo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCtcDemo.getContentPane().setLayout(null);
		
		JLabel lblTrackController = new JLabel("Track Controller");
		lblTrackController.setForeground(Color.BLUE);
		lblTrackController.setBounds(16, 11, 99, 14);
		frmCtcDemo.getContentPane().add(lblTrackController);
		
		txtBlock = new JTextField();
		txtBlock.setHorizontalAlignment(SwingConstants.CENTER);
		txtBlock.setForeground(new Color(0, 255, 0));
		txtBlock.setBackground(new Color(0, 0, 0));
		txtBlock.setEditable(false);
		txtBlock.setBounds(74, 48, 55, 20);
		frmCtcDemo.getContentPane().add(txtBlock);
		txtBlock.setColumns(10);
		
		txtDest = new JTextField();
		txtDest.setHorizontalAlignment(SwingConstants.CENTER);
		txtDest.setForeground(new Color(0, 255, 0));
		txtDest.setBackground(new Color(0, 0, 0));
		txtDest.setEditable(false);
		txtDest.setColumns(10);
		txtDest.setBounds(147, 48, 55, 20);
		frmCtcDemo.getContentPane().add(txtDest);
		
		txtSpeedSuggest = new JTextField();
		txtSpeedSuggest.setHorizontalAlignment(SwingConstants.CENTER);
		txtSpeedSuggest.setForeground(new Color(0, 255, 0));
		txtSpeedSuggest.setBackground(new Color(0, 0, 0));
		txtSpeedSuggest.setEditable(false);
		txtSpeedSuggest.setColumns(10);
		txtSpeedSuggest.setBounds(219, 48, 55, 20);
		frmCtcDemo.getContentPane().add(txtSpeedSuggest);
		
		JLabel lblBlock = new JLabel("block#");
		lblBlock.setBounds(73, 32, 55, 14);
		frmCtcDemo.getContentPane().add(lblBlock);
		
		JLabel lblDest = new JLabel("Dest");
		lblDest.setBounds(147, 32, 55, 14);
		frmCtcDemo.getContentPane().add(lblDest);
		
		JLabel lblSpeedkmhr = new JLabel("Speed (m/s)");
		lblSpeedkmhr.setBounds(219, 32, 78, 14);
		frmCtcDemo.getContentPane().add(lblSpeedkmhr);
		
		JLabel lblRequest = new JLabel("request");
		lblRequest.setBounds(9, 51, 55, 14);
		frmCtcDemo.getContentPane().add(lblRequest);
		
		checkBox_9 = new JCheckBox("9");
		checkBox_9.setBackground(new Color(255, 255, 0));
		checkBox_9.setBounds(348, 265, 36, 23);
		frmCtcDemo.getContentPane().add(checkBox_9);
		
		checkBox_0 = new JCheckBox("0");
		checkBox_0.setBackground(new Color(255, 255, 0));
		checkBox_0.setBounds(348, 31, 36, 23);
		frmCtcDemo.getContentPane().add(checkBox_0);
		
		checkBox_1 = new JCheckBox("1");
		checkBox_1.setBackground(new Color(255, 255, 0));
		checkBox_1.setBounds(348, 57, 36, 23);
		frmCtcDemo.getContentPane().add(checkBox_1);
		
		checkBox_2 = new JCheckBox("2");
		checkBox_2.setBackground(new Color(255, 255, 0));
		checkBox_2.setBounds(348, 82, 36, 23);
		frmCtcDemo.getContentPane().add(checkBox_2);
		
		checkBox_3 = new JCheckBox("3");
		checkBox_3.setBackground(new Color(255, 255, 0));
		checkBox_3.setBounds(348, 108, 36, 23);
		frmCtcDemo.getContentPane().add(checkBox_3);
		
		checkBox_4 = new JCheckBox("4");
		checkBox_4.setBackground(new Color(255, 255, 0));
		checkBox_4.setBounds(348, 134, 36, 23);
		frmCtcDemo.getContentPane().add(checkBox_4);
		
		checkBox_5 = new JCheckBox("5");
		checkBox_5.setBackground(new Color(255, 255, 0));
		checkBox_5.setBounds(348, 161, 36, 23);
		frmCtcDemo.getContentPane().add(checkBox_5);
		
		checkBox_6 = new JCheckBox("6");
		checkBox_6.setBackground(new Color(255, 255, 0));
		checkBox_6.setBounds(348, 187, 36, 23);
		frmCtcDemo.getContentPane().add(checkBox_6);
		
		checkBox_7 = new JCheckBox("7");
		checkBox_7.setBackground(new Color(255, 255, 0));
		checkBox_7.setBounds(348, 213, 36, 23);
		frmCtcDemo.getContentPane().add(checkBox_7);
		
		checkBox_8 = new JCheckBox("8");
		checkBox_8.setBackground(new Color(255, 255, 0));
		checkBox_8.setBounds(348, 239, 36, 23);
		frmCtcDemo.getContentPane().add(checkBox_8);
		
		JLabel lblTrainPresences = new JLabel("train presences");
		lblTrainPresences.setBounds(310, 295, 78, 14);
		frmCtcDemo.getContentPane().add(lblTrainPresences);
		
		checkBox = new JCheckBox("0");
		checkBox.setBackground(new Color(255, 0, 0));
		checkBox.setBounds(300, 32, 34, 23);
		frmCtcDemo.getContentPane().add(checkBox);
		
		checkBox_10 = new JCheckBox("1");
		checkBox_10.setBackground(new Color(255, 0, 0));
		checkBox_10.setBounds(300, 58, 34, 23);
		frmCtcDemo.getContentPane().add(checkBox_10);
		
		checkBox_11 = new JCheckBox("2");
		checkBox_11.setBackground(new Color(255, 0, 0));
		checkBox_11.setBounds(300, 83, 34, 23);
		frmCtcDemo.getContentPane().add(checkBox_11);
		
		checkBox_12 = new JCheckBox("3");
		checkBox_12.setBackground(new Color(255, 0, 0));
		checkBox_12.setBounds(300, 109, 34, 23);
		frmCtcDemo.getContentPane().add(checkBox_12);
		
		checkBox_13 = new JCheckBox("4");
		checkBox_13.setBackground(new Color(255, 0, 0));
		checkBox_13.setBounds(300, 135, 34, 23);
		frmCtcDemo.getContentPane().add(checkBox_13);
		
		checkBox_14 = new JCheckBox("5");
		checkBox_14.setBackground(new Color(255, 0, 0));
		checkBox_14.setBounds(300, 162, 34, 23);
		frmCtcDemo.getContentPane().add(checkBox_14);
		
		checkBox_15 = new JCheckBox("6");
		checkBox_15.setBackground(new Color(255, 0, 0));
		checkBox_15.setBounds(300, 188, 34, 23);
		frmCtcDemo.getContentPane().add(checkBox_15);
		
		checkBox_16 = new JCheckBox("7");
		checkBox_16.setBackground(new Color(255, 0, 0));
		checkBox_16.setBounds(300, 214, 34, 23);
		frmCtcDemo.getContentPane().add(checkBox_16);
		
		checkBox_17 = new JCheckBox("8");
		checkBox_17.setBackground(new Color(255, 0, 0));
		checkBox_17.setBounds(300, 240, 34, 23);
		frmCtcDemo.getContentPane().add(checkBox_17);
		
		checkBox_18 = new JCheckBox("9");
		checkBox_18.setBackground(new Color(255, 0, 0));
		checkBox_18.setBounds(300, 266, 34, 23);
		frmCtcDemo.getContentPane().add(checkBox_18);
		
		JLabel lblBrokedTracks = new JLabel("broked tracks");
		lblBrokedTracks.setBounds(269, 11, 78, 14);
		frmCtcDemo.getContentPane().add(lblBrokedTracks);
		
		txtPath = new JTextField();
		txtPath.setHorizontalAlignment(SwingConstants.CENTER);
		txtPath.setForeground(Color.GREEN);
		txtPath.setEditable(false);
		txtPath.setColumns(10);
		txtPath.setBackground(Color.BLACK);
		txtPath.setBounds(74, 89, 200, 20);
		frmCtcDemo.getContentPane().add(txtPath);
		
		JLabel lblPath = new JLabel("Path");
		lblPath.setBounds(74, 72, 55, 14);
		frmCtcDemo.getContentPane().add(lblPath);
		
		txtSpawn = new JTextField();
		txtSpawn.setHorizontalAlignment(SwingConstants.CENTER);
		txtSpawn.setForeground(new Color(0, 255, 255));
		txtSpawn.setEditable(false);
		txtSpawn.setColumns(10);
		txtSpawn.setBackground(Color.BLACK);
		txtSpawn.setBounds(74, 162, 55, 20);
		frmCtcDemo.getContentPane().add(txtSpawn);
		
		lblSpawn = new JLabel("spawn");
		lblSpawn.setBounds(9, 165, 55, 14);
		frmCtcDemo.getContentPane().add(lblSpawn);
		
		JButton btnClearRequest = new JButton("Clear Request");
		btnClearRequest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtSpawn.setText("");
			}
		});
		btnClearRequest.setBounds(147, 161, 115, 23);
		frmCtcDemo.getContentPane().add(btnClearRequest);
	}
	
	//sub to accept routing requests and display
	//public int[] routeTrain(int block, double speed, int dest){
	public void routeTrain(int block, double speed, int dest, int[] path){
		this.txtBlock.setText(Integer.toString(block));
		this.txtDest.setText(Integer.toString(dest));
		this.txtSpeedSuggest.setText(Double.toString(speed));		
		
		//record path too
		String pathhh = "";
		for (int i = 0; i < path.length; i++){
			if(i > 0){
				pathhh = pathhh + "->";
			}
			pathhh = pathhh + path[i];
		}
		this.txtPath.setText(pathhh);
		
	}

	//function for the CTC to request basic track info: train locations and broke ass tracks
	public boolean[][] getStatus(){
		//use 10 block track for demo
		boolean[][] prez = {{false,false}, {false,false}, {false,false}, {false,false}, {false,false}, {false,false}, {false,false}, {false,false}, {false,false}, {false,false}};
		
		//use check boxes
		prez[0][0] = checkBox_0.isSelected();
		prez[0][1] = checkBox.isSelected();
		
		prez[1][0] = checkBox_1.isSelected();
		prez[1][1] = checkBox_10.isSelected();
		
		prez[2][0] = checkBox_2.isSelected();
		prez[2][1] = checkBox_11.isSelected();
		
		prez[3][0] = checkBox_3.isSelected();
		prez[3][1] = checkBox_12.isSelected();
		
		prez[4][0] = checkBox_4.isSelected();
		prez[4][1] = checkBox_13.isSelected();
		
		prez[5][0] = checkBox_5.isSelected();
		prez[5][1] = checkBox_14.isSelected();
		
		prez[6][0] = checkBox_6.isSelected();
		prez[6][1] = checkBox_15.isSelected();
		
		prez[7][0] = checkBox_7.isSelected();
		prez[7][1] = checkBox_16.isSelected();
		
		prez[8][0] = checkBox_8.isSelected();
		prez[8][1] = checkBox_17.isSelected();
		
		prez[9][0] = checkBox_9.isSelected();
		prez[9][1] = checkBox_18.isSelected();
		
		return prez;		
	}
	
	//fake function to show a spawn request; just sets text
	public void spawnTrain(){
		txtSpawn.setText("Do It");
	
	}
}
