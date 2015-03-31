package TrackModel;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.JButton;

//import com.jgoodies.forms.layout.FormLayout;
//import com.jgoodies.forms.layout.ColumnSpec;
//import com.jgoodies.forms.factories.FormFactory;
//import com.jgoodies.forms.layout.RowSpec;

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
	JLabel lblSwitch;
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
		/*frame = new JFrame();
		frame.setBounds(100, 100, 710, 508);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		attributesPanel = new JPanel();
		attributesPanel.setBackground(new Color(255, 153, 0));
		frame.getContentPane().add(attributesPanel, BorderLayout.EAST);
		attributesPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("200px:grow"), }, new RowSpec[] {
				FormFactory.LINE_GAP_ROWSPEC, RowSpec.decode("25px"),
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, }));

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
*/
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
					blocks[count].trackSwitch = true;
				else
					blocks[count].trackSwitch = false;
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
				blocks[count].switchPos = row.get(12);
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
		attributesPanel.add(btnLoadTrack, "2, 2, center, top");

		JButton btnBreakTrack = new JButton("Break Track");
		btnBreakTrack.setBackground(new Color(0, 0, 0));
		btnBreakTrack.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnBreakTrack.setForeground(new Color(51, 255, 51));
		attributesPanel.add(btnBreakTrack, "2, 4, center, default");

		JButton btnPowerFailure = new JButton("Power Failure");
		btnPowerFailure.setBackground(new Color(0, 0, 0));
		btnPowerFailure.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnPowerFailure.setForeground(new Color(255, 255, 51));
		attributesPanel.add(btnPowerFailure, "2, 6, center, default");

		JButton btnCircuitFailure = new JButton("Circuit Failure");
		btnCircuitFailure.setBackground(new Color(0, 0, 0));
		btnCircuitFailure.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnCircuitFailure.setForeground(new Color(220, 20, 60));
		attributesPanel.add(btnCircuitFailure, "2, 8, center, default");
	}

	public void addLabels() {

		lblLine = new JLabel("Line: ");
		lblLine.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblLine.setForeground(new Color(138, 43, 226));
		attributesPanel.add(lblLine, "2, 10");

		lblSection = new JLabel("Section: ");
		lblSection.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblSection.setForeground(new Color(138, 43, 226));
		attributesPanel.add(lblSection, "2, 12");

		lblBlock = new JLabel("Block Number: ");
		lblBlock.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblBlock.setForeground(new Color(138, 43, 226));
		attributesPanel.add(lblBlock, "2, 14");

		lblBlockSize = new JLabel("Block Length: " + " m");
		lblBlockSize.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblBlockSize.setForeground(new Color(138, 43, 226));
		attributesPanel.add(lblBlockSize, "2, 16");

		lblGrade = new JLabel("Grade: " + "%");
		lblGrade.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblGrade.setForeground(new Color(138, 43, 226));
		attributesPanel.add(lblGrade, "2, 18");

		lblSpeedLimit = new JLabel("Speed Limit: " + " km/hr");
		lblSpeedLimit.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblSpeedLimit.setForeground(new Color(138, 43, 226));
		attributesPanel.add(lblSpeedLimit, "2, 20");

		lblStation = new JLabel("Station: ");
		lblStation.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblStation.setForeground(new Color(138, 43, 226));
		attributesPanel.add(lblStation, "2, 22");

		lblSwitch = new JLabel("Switch: ");
		lblSwitch.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblSwitch.setForeground(new Color(138, 43, 226));
		attributesPanel.add(lblSwitch, "2, 24");

		lblUnderground = new JLabel("Underground: ");
		lblUnderground.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblUnderground.setForeground(new Color(138, 43, 226));
		attributesPanel.add(lblUnderground, "2, 26");

		lblRrCrossing = new JLabel("RR Crossing: ");
		lblRrCrossing.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblRrCrossing.setForeground(new Color(138, 43, 226));
		attributesPanel.add(lblRrCrossing, "2, 28");

		lblElevation = new JLabel("Elevation: ");
		lblElevation.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblElevation.setForeground(new Color(138, 43, 226));
		attributesPanel.add(lblElevation, "2, 30");

		lblCumElevation = new JLabel("Cumulative Elevation: ");
		lblCumElevation.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblCumElevation.setForeground(new Color(138, 43, 226));
		attributesPanel.add(lblCumElevation, "2, 32");

		lblSwitchPos = new JLabel("Switch Positions: ");
		lblSwitchPos.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblSwitchPos.setForeground(new Color(138, 43, 226));
		attributesPanel.add(lblSwitchPos, "2, 34");

		lblArrowDir = new JLabel("Arrow Direction: ");
		lblArrowDir.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblArrowDir.setForeground(new Color(138, 43, 226));
		attributesPanel.add(lblArrowDir, "2, 36");
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
		lblSwitch.setText("Switch: " + b.trackSwitch);
		lblUnderground.setText("Underground: " + b.underground);
		lblRrCrossing.setText("RR Crossing: " + b.rrCrossing);
		lblElevation.setText("Elevation: " + b.elevation);
		lblCumElevation.setText("Cumulative Elevation: " + b.cumElevation);
		lblSwitchPos.setText("Switch Positions: " + b.switchPos);
		lblArrowDir.setText("Arrow Direction: " + b.arrowDir);
	}

}
