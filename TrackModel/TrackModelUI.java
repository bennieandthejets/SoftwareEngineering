package TrackModel;
import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Simulator.Simulator;

public class TrackModelUI {

	JFrame frame;
	String inputFile;
	JPanel attributesPanel;
	ArrayList<ArrayList<String>> line;
	int count = 1;
	JLabel lblLine;
	JLabel lblSwitchPos;
	JLabel lblCumElevation;
	JLabel lblElevation;
	JLabel lblRrCrossing;
	JLabel lblUnderground;
	JLabel lblStation;
	JLabel lblSpeedLimit;
	JLabel lblGrade;
	JLabel lblBlockSize;
	JLabel lblBlock;
	JLabel lblSection;
	JLabel lblBroken;
	JLabel lblFailed;
	Block[] blocks;
	JButton[] trackButtons;
	private JPanel selBlockPanel;
	private JLabel lblSelectBlock;
	private Choice choice;
	TrackModel t;
	private JPanel mapPanel;
	Object[][] data;

	/**
	 * Create the application.
	 */
	public TrackModelUI(TrackModel t) {
		this.t = t;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame = new JFrame();
		frame.setBounds(100, 100, 710, 508);

		attributesPanel = new JPanel();
		attributesPanel.setBackground(new Color(255, 153, 0));
		frame.getContentPane().add(attributesPanel, BorderLayout.EAST);

		selBlockPanel = new JPanel();
		selBlockPanel.setBackground(new Color(51, 204, 255));
		frame.getContentPane().add(selBlockPanel, BorderLayout.NORTH);

		lblSelectBlock = new JLabel("Select Block:");
		lblSelectBlock.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblSelectBlock.setForeground(new Color(255, 0, 102));
		selBlockPanel.add(lblSelectBlock);

		choice = new Choice();
		choice.setBackground(new Color(0, 0, 0));
		choice.setForeground(new Color(255, 255, 255));
		choice.setFont(new Font("Dialog", Font.BOLD, 12));
		selBlockPanel.add(choice);
		
		mapPanel = new JPanel();
		frame.getContentPane().add(mapPanel, BorderLayout.CENTER);
		
		addButtons();

		addLabels();

	}
	
	public void paintMap()
	{
		//mapPanel.repaint();
		MyMap m = new MyMap(data);
		mapPanel.removeAll();
		mapPanel.add(m.getTable());
	}
	
	public void addMap(Object[][] data) throws FileNotFoundException
	{
		mapPanel.removeAll();
		this.data = data;
		mapPanel.setLayout(new GridLayout(1, 0, 0, 0));
		MyMap m = new MyMap(data);
		mapPanel.add(m.getTable());
	}
	
	public void trainOnBlock(int r, int c)
	{
		data[r][c] = new Color(255, 0 ,0);
		MyMap m = new MyMap(data);
		mapPanel.removeAll();
		mapPanel.add(m.getTable());
	}
	
	public void trainOffBlock(int r, int c)
	{
		data[r][c] = new Color(143,105,255);
		MyMap m = new MyMap(data);
		mapPanel.removeAll();
		mapPanel.add(m.getTable());
	}
	
	public void trainOffSwitch(int r, int c)
	{
		data[r][c] = new Color(0,9,255);
		MyMap m = new MyMap(data);
		mapPanel.removeAll();
		mapPanel.add(m.getTable());
	}
	
	public void addBlockChoice() {
		
		for(int i=1; i<blocks.length; i++)
			choice.add(String.valueOf(blocks[i].blockID));
		
		// Add item listener
		choice.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				resetLabels(blocks[Integer.parseInt(choice.getSelectedItem())]);
				//FOR TESTING ONLY
				//trainOnBlock(blocks[Integer.parseInt(choice.getSelectedItem())].mapRow, blocks[Integer.parseInt(choice.getSelectedItem())].mapCol);
				//if(blocks[Integer.parseInt(choice.getSelectedItem())].blockID > 1)
				//	trainOffBlock(blocks[Integer.parseInt(choice.getSelectedItem())-1].mapRow, blocks[Integer.parseInt(choice.getSelectedItem())-1].mapCol);
			}
		});
	}

	public void addButtons() {
		JButton btnLoadTrack = new JButton("Load Track");
		btnLoadTrack.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnLoadTrack.setForeground(Color.MAGENTA);
		btnLoadTrack.setBackground(new Color(0, 0, 0));
		btnLoadTrack.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				boolean validInput = false;
				while (!validInput) {
					inputFile = JOptionPane
							.showInputDialog("Enter a file name to import: ");
					try {
						blocks = t.importTrack(inputFile);
						addBlockChoice();
						validInput = true;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
		});
		attributesPanel.setLayout(new GridLayout(19, 1, 0, 0));
		attributesPanel.add(btnLoadTrack);

		JButton btnBreakTrack = new JButton("Break Track");
		btnBreakTrack.setBackground(new Color(0, 0, 0));
		btnBreakTrack.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnBreakTrack.setForeground(new Color(51, 255, 51));
		btnBreakTrack.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(blocks != null)
				{
					t.breakTrack(Integer.parseInt(choice.getSelectedItem()));
					resetLabels(blocks[Integer.parseInt(choice.getSelectedItem())]);
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
			
		});
		attributesPanel.add(btnBreakTrack);

		JButton btnCircuitFailure = new JButton("Circuit Failure");
		btnCircuitFailure.setBackground(new Color(0, 0, 0));
		btnCircuitFailure.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnCircuitFailure.setForeground(new Color(220, 20, 60));
		btnCircuitFailure.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(blocks != null)
				{
					t.circuitFail(Integer.parseInt(choice.getSelectedItem()));
					resetLabels(blocks[Integer.parseInt(choice.getSelectedItem())]);
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
			
		});
		attributesPanel.add(btnCircuitFailure);
	}

	public void addLabels() {

		lblLine = new JLabel("Line: ");
		lblLine.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblLine.setForeground(new Color(138, 43, 226));
		attributesPanel.add(lblLine);

		lblSection = new JLabel("Section: ");
		lblSection.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblSection.setForeground(new Color(138, 43, 226));
		attributesPanel.add(lblSection);

		lblBlock = new JLabel("Block Number: ");
		lblBlock.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblBlock.setForeground(new Color(138, 43, 226));
		attributesPanel.add(lblBlock);

		lblBlockSize = new JLabel("Block Length: " + " m");
		lblBlockSize.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblBlockSize.setForeground(new Color(138, 43, 226));
		attributesPanel.add(lblBlockSize);

		lblGrade = new JLabel("Grade: " + "%");
		lblGrade.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblGrade.setForeground(new Color(138, 43, 226));
		attributesPanel.add(lblGrade);

		lblSpeedLimit = new JLabel("Speed Limit: " + " km/hr");
		lblSpeedLimit.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblSpeedLimit.setForeground(new Color(138, 43, 226));
		attributesPanel.add(lblSpeedLimit);

		lblStation = new JLabel("Station: ");
		lblStation.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblStation.setForeground(new Color(138, 43, 226));
		attributesPanel.add(lblStation);

		lblUnderground = new JLabel("Underground: ");
		lblUnderground.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblUnderground.setForeground(new Color(138, 43, 226));
		attributesPanel.add(lblUnderground);

		lblRrCrossing = new JLabel("RR Crossing: ");
		lblRrCrossing.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblRrCrossing.setForeground(new Color(138, 43, 226));
		attributesPanel.add(lblRrCrossing);

		lblElevation = new JLabel("Elevation: ");
		lblElevation.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblElevation.setForeground(new Color(138, 43, 226));
		attributesPanel.add(lblElevation);

		lblCumElevation = new JLabel("Cumulative Elevation: ");
		lblCumElevation.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblCumElevation.setForeground(new Color(138, 43, 226));
		attributesPanel.add(lblCumElevation);

		lblSwitchPos = new JLabel("Switch Positions: ");
		lblSwitchPos.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblSwitchPos.setForeground(new Color(138, 43, 226));
		attributesPanel.add(lblSwitchPos);

		lblBroken = new JLabel("Is Broken: ");
		lblBroken.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblBroken.setForeground(new Color(138, 43, 226));
		attributesPanel.add(lblBroken);
		
		lblFailed = new JLabel("Is Failed: ");
		lblFailed.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblFailed.setForeground(new Color(138, 43, 226));
		attributesPanel.add(lblFailed);
	}

	public void resetLabels(Block b) {
		// get all info from block and set labels
		lblLine.setText("Line: " + b.lineColor);
		lblSection.setText("Section: " + b.section);
		lblBlock.setText("Block Number: " + b.blockID);
		lblBlockSize.setText("Block Length: " + b.blockSize + " m");
		lblGrade.setText("Grade: " + b.grade + "%");
		lblSpeedLimit.setText("Speed Limit: " + b.speedLimit + " km/hr");
		lblStation.setText("Station: " + b.station);
		lblUnderground.setText("Underground: " + b.underground);
		lblRrCrossing.setText("RR Crossing: " + b.rrCrossing);
		lblElevation.setText("Elevation: " + b.elevation);
		lblCumElevation.setText("Cumulative Elevation: " + b.cumElevation);
		if(b.getSwitch() != null)
			lblSwitchPos.setText("Switch Positions: " + b.getSwitch().blockOne + ", " + b.getSwitch().blockTwo);
		else
			lblSwitchPos.setText("Switch Positions: ");
		lblBroken.setText("Is Broken: " + b.isBroken);
		lblFailed.setText("Is Failed: " + b.isFailed);
	}	

}
