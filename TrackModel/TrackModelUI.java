package TrackModel;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.JButton;

import javax.swing.JLabel;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Font;
import java.awt.Choice;
import java.awt.GridLayout;

public class TrackModelUI extends TrackModel {

	private JFrame frame;
	String inputFile;
	ExcelParser ex;
	JPanel attributesPanel;
	ArrayList<ArrayList<String>> line;
	int count = 1;
	JLabel lblLine;
	JLabel lblArrowDir;
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

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TrackModelUI window = new TrackModelUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

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
		
		addButtons();

		addLabels();

	}

	public void makeMap() {

		// trackPanel = new JPanel();
		// frame.getContentPane().add(trackPanel, BorderLayout.CENTER);
		// trackPanel.setLayout(new GridLayout(1, 0, 0, 0));

		// make block and button arrays
		blocks = new Block[line.size()];
		trackButtons = new JButton[line.size()];
		for (count = 1; count < line.size(); count++) {
			ArrayList<String> row = line.get(count);
			if (row.size() != 0) {
				blocks[count] = new Block(count);
				blocks[count].lineColor = row.get(0);
				blocks[count].section = row.get(1);
				blocks[count].blockID = Integer.parseInt(row.get(2));
				blocks[count].blockSize = Double.parseDouble(row.get(3));
				blocks[count].grade = Double.parseDouble(row.get(4));
				blocks[count].speedLimit = Integer.parseInt(row.get(5));
				blocks[count].station = row.get(6);
				if(row.get(7).equals("TRUE"))
					blocks[count].sw = new Switch(count,row.get(12));
				if(row.get(8).equals("TRUE"))
					blocks[count].underground = true;
				else
					blocks[count].underground = false;
				if(row.get(9).equals("TRUE"))
					blocks[count].rrCrossing = true;
				else
					blocks[count].rrCrossing = false;
				blocks[count].elevation = Double.parseDouble(row.get(10));
				blocks[count].cumElevation = Double.parseDouble(row.get(11));
				blocks[count].arrowDir = row.get(13);

				choice.add(String.valueOf(count));
			}
		}
	}

	public void addBlockChoice() {
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
						ex = new ExcelParser(inputFile);
						validInput = true;
					} catch (IOException e) {
						e.printStackTrace();
					}
					line = ex.getLine();
					count = line.size();
				}
				makeMap();
				addBlockChoice();
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

		lblArrowDir = new JLabel("Arrow Direction: ");
		lblArrowDir.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblArrowDir.setForeground(new Color(138, 43, 226));
		attributesPanel.add(lblArrowDir);
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
		lblSwitchPos.setText("Switch Positions: " + b.getSwitch().switches);
		lblArrowDir.setText("Arrow Direction: " + b.arrowDir);
	}

}
