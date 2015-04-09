package CTC;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

import java.awt.Color;

import javax.swing.AbstractListModel;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;

import java.awt.Toolkit;
import java.awt.Window.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ImageIcon;


public class ctcWindow {

	public CTC owner;
	
	
	public JFrame frmCtc;
	public JTable tblAnnouncements;
	public JTable tblLocations;
	public JTable tblRoutes;
	public JTable tblClosures;
	public JScrollPane scrollPane_1;
	public JTextField valThisHour;
	public JTextField valExpected;
	public JTextField valLastHour;
	public JTextField txtTrainNum;
	public JTextField txtSpeedSet;
	public JTextField txtDestBlockNum;
	public JTextField txtCloseBlockNum;
	public JTable tblFakeMap;
	private JTextField txtTime;
	
	private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					
					ctcWindow window = new ctcWindow();
					window.frmCtc.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	

	/**
	 * Create the application.
	 */
	public ctcWindow(CTC initCTC) {
		this.owner = initCTC;
		
		initialize();
	}
	public ctcWindow() {
		initialize();
	}
	
	//print announcement by inserting it into the listview
	public void setAnnouncement(String ann){
		
		DefaultTableModel model = (DefaultTableModel) tblAnnouncements.getModel();
		model.insertRow(0,new Object[]{ann});
	}
	
	//prepend routing commands to list; currently no limit
	public void setRecentCommand(String time, String train, String dest){
		DefaultTableModel model = (DefaultTableModel) tblRoutes.getModel();
		model.insertRow(0, new Object[]{time, train, dest});
		
	}
	
	public void setTime(long t){
		
		Date date = new Date(t);
        txtTime.setText(timeFormat.format(date));
	}
	
	//edit a map row with new data
	public void setMapBlock(int block, boolean train, boolean broke){
		if(block >= 0){
			DefaultTableModel model = (DefaultTableModel) tblFakeMap.getModel();
			
			model.setValueAt(new String("         |--"), block, 1);
			model.setValueAt(new String("--|"), block, 3);
			
			if(broke){ //can't really know about train presence, just print broke
				model.setValueAt(new String("BROKE"), block, 4);
			} else {
				model.setValueAt(new String(""), block, 4);
			}
			if(train){ //can't really know about train presence, just print broke
				model.setValueAt(new String("TRAIN!"), block, 2);
			} else {
				model.setValueAt(new String("----------"), block, 2);
			}
		}
	}
	
	//set train location and/or destination
	public void setLocation(int train, int location, int dest){
		DefaultTableModel model = (DefaultTableModel) tblLocations.getModel();
		model.setValueAt(train + 1, train, 0);
		
		//allow for destination or location to stay the same
		if(location >=0 ){
		model.setValueAt(location, train, 1);
		}
		if (dest >= 0){
		model.setValueAt(dest, train, 2);
		}
		
	}
	
	public void setStops(int thisHour, double expect, int lastHour){
		
		valThisHour.setText("" + thisHour);
		valExpected.setText("" + expect);
		valLastHour.setText("" + lastHour);
		
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("unchecked")
	private void initialize() {
		frmCtc = new JFrame();
		frmCtc.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\source\\sex.png"));
		frmCtc.getContentPane().setBackground(new Color(128, 128, 128));
		frmCtc.setTitle("CTC Office");
		frmCtc.setBounds(100, 100, 965, 590);
		frmCtc.getContentPane().setLayout(null);
		
		JComboBox cboMode = new JComboBox();
		cboMode.setForeground(new Color(0, 100, 0));
		cboMode.setBackground(Color.LIGHT_GRAY);
		cboMode.setModel(new DefaultComboBoxModel(new String[] {"Manual", "MBO", "Fixed Block"}));
		cboMode.setMaximumRowCount(3);
		cboMode.setBounds(250, 11, 110, 20);
		frmCtc.getContentPane().add(cboMode);
		
		JLabel lblControlMode = new JLabel("Control Mode:");
		lblControlMode.setForeground(new Color(0, 255, 0));
		lblControlMode.setBounds(163, 14, 78, 14);
		frmCtc.getContentPane().add(lblControlMode);
		
		JScrollPane scrollPane_Announce = new JScrollPane();
		scrollPane_Announce.setBounds(10, 402, 329, 138);
		frmCtc.getContentPane().add(scrollPane_Announce);
		
		tblAnnouncements = new JTable();
		tblAnnouncements.setEnabled(false);
		tblAnnouncements.setBackground(new Color(135, 206, 250));
		scrollPane_Announce.setViewportView(tblAnnouncements);
		tblAnnouncements.setModel(new DefaultTableModel(
			new Object[][] {
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
			},
			new String[] {
				"Announcements"
			}
		));
		
		JScrollPane scrollPane_Locations = new JScrollPane();
		scrollPane_Locations.setBounds(582, 260, 357, 139);
		frmCtc.getContentPane().add(scrollPane_Locations);
		
		tblLocations = new JTable();
		tblLocations.setEnabled(false);
		tblLocations.setBackground(new Color(255, 140, 0));
		scrollPane_Locations.setViewportView(tblLocations);
		tblLocations.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
			},
			new String[] {
				"Train#", "Location", "Destination"
			}
		));
		
		JScrollPane scrollPane_Routes = new JScrollPane();
		scrollPane_Routes.setBounds(582, 414, 357, 126);
		frmCtc.getContentPane().add(scrollPane_Routes);
		
		tblRoutes = new JTable();
		tblRoutes.setEnabled(false);
		tblRoutes.setBackground(new Color(255, 255, 0));
		scrollPane_Routes.setViewportView(tblRoutes);
		tblRoutes.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
			},
			new String[] {
				"Time", "Train#", "Destination"
			}
		));
		
		JPanel panel_time = new JPanel();
		panel_time.setBackground(new Color(0, 0, 0));
		panel_time.setForeground(new Color(127, 255, 0));
		panel_time.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Time", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(255, 255, 255)));
		panel_time.setBounds(22, 11, 86, 44);
		frmCtc.getContentPane().add(panel_time);
		panel_time.setLayout(null);
		
		txtTime = new JTextField();
		txtTime.setBounds(10, 14, 66, 22);
		txtTime.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtTime.setText("HH:mm:ss");
		txtTime.setHorizontalAlignment(SwingConstants.CENTER);
		txtTime.setForeground(new Color(0, 255, 255));
		txtTime.setEditable(false);
		txtTime.setColumns(10);
		txtTime.setBackground(new Color(0, 0, 0));
		panel_time.add(txtTime);
		
		JPanel pnlMaintenance = new JPanel();
		pnlMaintenance.setBackground(new Color(75, 0, 130));
		pnlMaintenance.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Maintenance", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 0, 0)));
		pnlMaintenance.setBounds(582, 11, 357, 102);
		frmCtc.getContentPane().add(pnlMaintenance);
		pnlMaintenance.setLayout(null);
		
		JLabel lblSex = new JLabel("Action:");
		lblSex.setForeground(new Color(245, 255, 250));
		lblSex.setHorizontalAlignment(SwingConstants.CENTER);
		lblSex.setBounds(7, 24, 40, 14);
		pnlMaintenance.add(lblSex);
		
		JComboBox cboClose = new JComboBox();
		cboClose.setModel(new DefaultComboBoxModel(new String[] {"Close", "Open"}));
		cboClose.setMaximumRowCount(2);
		cboClose.setForeground(new Color(255, 255, 255));
		cboClose.setBackground(new Color(153, 50, 204));
		cboClose.setBounds(47, 21, 74, 20);
		pnlMaintenance.add(cboClose);
		
		JLabel lblBlock = new JLabel("Block:");
		lblBlock.setHorizontalAlignment(SwingConstants.CENTER);
		lblBlock.setForeground(new Color(245, 255, 250));
		lblBlock.setBounds(123, 24, 40, 14);
		pnlMaintenance.add(lblBlock);
		
		txtCloseBlockNum = new JTextField();
		txtCloseBlockNum.setForeground(new Color(255, 255, 255));
		txtCloseBlockNum.setHorizontalAlignment(SwingConstants.CENTER);
		txtCloseBlockNum.setColumns(10);
		txtCloseBlockNum.setBackground(new Color(128, 0, 0));
		txtCloseBlockNum.setBounds(163, 21, 74, 20);
		pnlMaintenance.add(txtCloseBlockNum);
		
		JButton btnDoIt = new JButton("Do It");
		btnDoIt.setForeground(new Color(128, 0, 128));
		btnDoIt.setBounds(162, 68, 74, 23);
		pnlMaintenance.add(btnDoIt);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(245, 11, 102, 83);
		pnlMaintenance.add(scrollPane_1);
		
		tblClosures = new JTable();
		tblClosures.setEnabled(false);
		tblClosures.setBackground(new Color(255, 105, 180));
		scrollPane_1.setViewportView(tblClosures);
		tblClosures.setModel(new DefaultTableModel(
			new Object[][] {
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
			},
			new String[] {
				"Closed"
			}
		));
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 128, 0));
		panel.setLayout(null);
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Route Trains", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(128, 0, 0)));
		panel.setBounds(582, 121, 357, 102);
		frmCtc.getContentPane().add(panel);
		
		JLabel lblTrain = new JLabel("Train#:");
		lblTrain.setHorizontalAlignment(SwingConstants.CENTER);
		lblTrain.setBounds(39, 29, 40, 14);
		panel.add(lblTrain);
		
		JLabel lblSpeedmph = new JLabel("Speed (mph):");
		lblSpeedmph.setHorizontalAlignment(SwingConstants.CENTER);
		lblSpeedmph.setBounds(4, 65, 79, 14);
		panel.add(lblSpeedmph);
		
		JLabel lblDestination = new JLabel("Destination:");
		lblDestination.setHorizontalAlignment(SwingConstants.CENTER);
		lblDestination.setBounds(181, 29, 74, 14);
		panel.add(lblDestination);
		
		JButton btnRoute = new JButton("Route");
		btnRoute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					//block invalid train and block numbers
					if((Integer.parseInt(txtTrainNum.getText()) < 1 || Integer.parseInt(txtTrainNum.getText()) > owner.activeTrains)){
						setAnnouncement("Routing Failed: invalid train number " + txtTrainNum.getText());
					} else if(Integer.parseInt(txtDestBlockNum.getText()) < 0 || Integer.parseInt(txtDestBlockNum.getText()) > owner.blockCount){
						setAnnouncement("Routing Failed: invalid block number " + txtDestBlockNum.getText());						
					}else {
					//convert to meters per second for internal signals
					double mph = Double.parseDouble(txtSpeedSet.getText());
					double mps = mph*0.44704;										
					boolean succ = owner.routeTrainCTC(Integer.parseInt(txtTrainNum.getText()) - 1, mps, Integer.parseInt(txtDestBlockNum.getText()));
										
					//record command
					if(succ){
						setRecentCommand(txtTime.getText(),txtTrainNum.getText(),txtDestBlockNum.getText());
					}
					
					
					}
				}catch(Exception ex){
					setAnnouncement("Error, Routing Failed: " + txtTrainNum.getText() + ", " + txtSpeedSet.getText() + ", " + txtDestBlockNum.getText());
				}
			}
		});
		btnRoute.setForeground(new Color(0, 128, 0));
		btnRoute.setBounds(257, 61, 74, 23);
		panel.add(btnRoute);
		
		txtTrainNum = new JTextField();
		txtTrainNum.setHorizontalAlignment(SwingConstants.CENTER);
		txtTrainNum.setBackground(new Color(127, 255, 0));
		txtTrainNum.setBounds(87, 27, 74, 20);
		panel.add(txtTrainNum);
		txtTrainNum.setColumns(10);
		
		txtSpeedSet = new JTextField();
		txtSpeedSet.setHorizontalAlignment(SwingConstants.CENTER);
		txtSpeedSet.setBackground(new Color(127, 255, 0));
		txtSpeedSet.setColumns(10);
		txtSpeedSet.setBounds(87, 63, 74, 20);
		panel.add(txtSpeedSet);
		
		txtDestBlockNum = new JTextField();
		txtDestBlockNum.setHorizontalAlignment(SwingConstants.CENTER);
		txtDestBlockNum.setBackground(new Color(127, 255, 0));
		txtDestBlockNum.setColumns(10);
		txtDestBlockNum.setBounds(257, 26, 74, 20);
		panel.add(txtDestBlockNum);
		
		JPanel pnlThru = new JPanel();
		pnlThru.setBackground(new Color(0, 128, 128));
		pnlThru.setLayout(null);
		pnlThru.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Throughput", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 255, 0)));
		pnlThru.setBounds(349, 450, 212, 90);
		frmCtc.getContentPane().add(pnlThru);
		
		JLabel lblThisHr = new JLabel("This Hr");
		lblThisHr.setHorizontalAlignment(SwingConstants.CENTER);
		lblThisHr.setBounds(16, 23, 40, 14);
		pnlThru.add(lblThisHr);
		
		JLabel lblExpected = new JLabel("Expected");
		lblExpected.setHorizontalAlignment(SwingConstants.CENTER);
		lblExpected.setBounds(70, 23, 61, 14);
		pnlThru.add(lblExpected);
		
		JLabel lblLastHr = new JLabel("Last Hr");
		lblLastHr.setHorizontalAlignment(SwingConstants.CENTER);
		lblLastHr.setBounds(144, 23, 48, 14);
		pnlThru.add(lblLastHr);
		
		valThisHour = new JTextField();
		valThisHour.setForeground(new Color(255, 255, 0));
		valThisHour.setBackground(new Color(186, 85, 211));
		valThisHour.setText("Value");
		valThisHour.setHorizontalAlignment(SwingConstants.CENTER);
		valThisHour.setEditable(false);
		valThisHour.setColumns(10);
		valThisHour.setBounds(12, 41, 51, 36);
		pnlThru.add(valThisHour);
		
		valExpected = new JTextField();
		valExpected.setText("Value");
		valExpected.setHorizontalAlignment(SwingConstants.CENTER);
		valExpected.setForeground(Color.YELLOW);
		valExpected.setEditable(false);
		valExpected.setColumns(10);
		valExpected.setBackground(new Color(186, 85, 211));
		valExpected.setBounds(76, 40, 51, 36);
		pnlThru.add(valExpected);
		
		valLastHour = new JTextField();
		valLastHour.setText("Value");
		valLastHour.setHorizontalAlignment(SwingConstants.CENTER);
		valLastHour.setForeground(Color.YELLOW);
		valLastHour.setEditable(false);
		valLastHour.setColumns(10);
		valLastHour.setBackground(new Color(186, 85, 211));
		valLastHour.setBounds(141, 41, 51, 36);
		pnlThru.add(valLastHour);
		
		JLabel lblKnownLocations = new JLabel("Known Locations");
		lblKnownLocations.setForeground(new Color(0, 0, 255));
		lblKnownLocations.setBounds(582, 246, 98, 14);
		frmCtc.getContentPane().add(lblKnownLocations);
		
		JLabel lblRecentCommands = new JLabel("Recent Commands");
		lblRecentCommands.setForeground(new Color(255, 0, 0));
		lblRecentCommands.setBounds(582, 402, 110, 14);
		frmCtc.getContentPane().add(lblRecentCommands);
		
		JButton btnSpawn = new JButton("Make Train Puppy From Train Embryo");
		btnSpawn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//fakeShit.spawnTrain();
				owner.spawnTrainCTC();
			}
		});
		btnSpawn.setForeground(new Color(0, 0, 205));
		btnSpawn.setBounds(690, 229, 241, 23);
		frmCtc.getContentPane().add(btnSpawn);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(23, 95, 234, 287);
		frmCtc.getContentPane().add(scrollPane);
		
		tblFakeMap = new JTable();
		tblFakeMap.setShowGrid(false);
		tblFakeMap.setCellSelectionEnabled(true);
		scrollPane.setViewportView(tblFakeMap);
		tblFakeMap.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
			},
			new String[] {
				"map", "", "", "", ""
			}
		));
		
		JButton btnStartWithMap = new JButton("Click this when map loads");
		btnStartWithMap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				owner.start();
			}
		});
		btnStartWithMap.setBounds(333, 117, 183, 23);
		frmCtc.getContentPane().add(btnStartWithMap);
	}
}
