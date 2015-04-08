package TrackModel;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.Color;
import java.awt.Font;
import java.awt.Choice;
import java.awt.GridLayout;

public class TrackModelUI {

	private JFrame frame;
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
	Block[] blocks;
	JButton[] trackButtons;
	private JPanel selBlockPanel;
	private JLabel lblSelectBlock;
	private Choice choice;
	TrackModel t;
	private JPanel mapPanel;


	/**
	 * Create the application.
	 */
	public TrackModelUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame = new JFrame();
		frame.setBounds(100, 100, 710, 508);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
	
	public void addMap(String input) throws FileNotFoundException
	{
		String mapFile = "";
		
		if(input.equals("TrackLayoutGreenLine.csv"))
			mapFile = "greenmap.txt";
		else if(input.equals("TrackLayoutRedLine.csv"))
			mapFile = "redmap.txt";
		
		Scanner map = new Scanner(new File(mapFile));
		
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
		
		mapPanel.setLayout(new GridLayout(1, 0, 0, 0));

	    Object[][] data = new Object[mapRows.size()][mapRows.get(0).size()];
	    
		for(int i=0; i<mapRows.size(); i++)
		{
			ArrayList<String> mapCol = mapRows.get(i);
			
			for(int j=0; j<mapCol.size(); j++)
			{
				//data[i][j] = mapCol.get(j);
				if(mapCol.get(j).equals("x"))
					data[i][j] = new Color(143,105,255);
				else
					data[i][j] = new Color(105,255,161);
			}
		}
		MyMap m = new MyMap(data);
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
						addMap(inputFile);
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
		attributesPanel.setLayout(new GridLayout(17, 1, 0, 0));
		attributesPanel.add(btnLoadTrack);

		JButton btnBreakTrack = new JButton("Break Track");
		btnBreakTrack.setBackground(new Color(0, 0, 0));
		btnBreakTrack.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnBreakTrack.setForeground(new Color(51, 255, 51));
		attributesPanel.add(btnBreakTrack);

		JButton btnPowerFailure = new JButton("Power Failure");
		btnPowerFailure.setBackground(new Color(0, 0, 0));
		btnPowerFailure.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnPowerFailure.setForeground(new Color(255, 255, 51));
		attributesPanel.add(btnPowerFailure);

		JButton btnCircuitFailure = new JButton("Circuit Failure");
		btnCircuitFailure.setBackground(new Color(0, 0, 0));
		btnCircuitFailure.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnCircuitFailure.setForeground(new Color(220, 20, 60));
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
	}	

}
