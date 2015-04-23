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
import javax.swing.BorderFactory;
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

import TrackModel.Block;
import TrackModel.Switch;


public class ctcWindow {

	public CTC owner;
	
	public int mapSize = 37; //37 by 37 map fits both lines
	private JPanel[][] blockPanels; //fake gridview will hold the map
	//save location of map since all blocks will be created at runTime
	private int mapLeft = 10;
	private int mapTop = 52;
	//filepaths for lines; change to "src\\n.png" relative path eventually
	private String nPath =  "src\\n.png";//"src\\n.png"; //"c:\\source\\track\\15px\\n.png";
	private String sPath = "src\\s.png";
	private String ePath = "src\\e.png";
	private String wPath = "src\\w.png";
	private String swPath = "src\\sw.png";
	private String sePath = "src\\se.png";
	private String nwPath = "src\\nw.png";
	private String nePath = "src\\ne.png";
	private String trainPath = "src\\tinyTrain.png";
	//colors for various track states
	private Color yard = Color.BLACK;
	private Color station = Color.pink;
	private Color empty = Color.lightGray;
	private Color train = Color.green;
	private Color broken = Color.red;
	private Color switchh = Color.cyan;
	private Color both = Color.yellow;
	
	public JFrame frmCtc;
	public JTable tblAnnouncements;
	public JTable tblLocations;
	public JTable tblRoutes;
	public JTextField valThisHour;
	public JTextField valExpected;
	public JTextField valLastHour;
	public JTextField txtTrainNum;
	public JTextField txtSpeedSet;
	public JTextField txtDestBlockNum;
	public JTextField txtCloseBlockNum;
	public JTextField txtTime;
	private JComboBox cboClose;
	public JComboBox cboMode;
	
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
		String oldString = "";
		if(model.getValueAt(0,0) != null ){oldString = model.getValueAt(0, 0).toString();}
		if(!oldString.equals(ann)){ //avoid 1000 messages on ticks
			model.insertRow(0,new Object[]{ann});
		}
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
			
			
			if(broke){ //can't really know about train presence, just print broke
				
			} else {
				
			}
			if(train){ 
				
			} else {
				
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
	
	/*add a track direction mapPanels[x][y], pointing in direction type
	 * 	0 = north, 1 = northeast, 2 = east, 3 = southeast,
	 * 	4 = south, 5 = southwest, 6 = west, 7 = northwest
	 * 
	 * this function will be called at least twice for each track block, to connect it to both neighbors
	 */	
	public void addTrack(int x, int y, int type){
		String path = "";
		switch(type){
			case 0: path = nPath;
					break;
			case 1: path = nePath;
					break;
			case 2: path = ePath;
					break;
			case 3: path = sePath;
					break;
			case 4: path = sPath;			
					break;
			case 5: path = swPath;
					break;
			case 6: path = wPath;
					break;
			case 7: path = nwPath;
					break;		
		}
		
		if(path.equals("")){			
			//bad input: fuck off
		} else {
			// add a label with just the image to the chosen panel
			JLabel lbl = new JLabel("");
			lbl.setBounds(0,0,15,15);
			ImageIcon im = new ImageIcon(path);
			lbl.setIcon(im);
			blockPanels[x][y].add(lbl);
			
		}
		
		blockPanels[x][y].repaint();
		
	}
	
	//add block # and train presence labels to a square
	public void setTrackLabel(Block bl, int b){
		JPanel pnl = blockPanels[bl.mapRow][bl.mapCol];
		
		/*//add track number and leave visible for now
		JLabel numLabel = new JLabel("" + b);
		numLabel.setBounds(0,0,15,15);
		numLabel.setForeground(new Color(255, 0, 0)); 
		numLabel.setFont(new Font("Times New Roman", Font.BOLD, 9));
		pnl.add(numLabel);
		*/
		
		//add a label with just the border
		JLabel brdr = new JLabel("");
		brdr.setBounds(0,0,15,15);
		brdr.setBorder(BorderFactory.createLineBorder(Color.darkGray));
		pnl.add(brdr);
		
		//add train image 
		JLabel trainlabel = new JLabel("");
		trainlabel.setBounds(0,0,15,15);				
		trainlabel.setIcon(new ImageIcon(trainPath));				
		trainlabel.setIconTextGap(-15); //if we add numbers they will appear above the image
		//trainlabel.setToolTipText("ohhhhh" + b);
		//trainlabel.setBorder(BorderFactory.createLineBorder(Color.red,2));
		pnl.add(trainlabel);
		if(bl.getStation() != null){
			pnl.setToolTipText("Block " + b + ", " + bl.getStation());
		} else if(bl.isToYard() && bl.isFromYard()){
			pnl.setToolTipText("Block " + b + ", To/From Yard");
		} else if(bl.isToYard()){
			pnl.setToolTipText("Block " + b + ", To Yard");
		} else if(bl.isFromYard()){
			pnl.setToolTipText("Block " + b + ", From Yard");
		} else {
			pnl.setToolTipText("Block " + b);
		}
		
		
			
		//hide image until train is present
		pnl.getComponent(pnl.getComponentCount()-1).setVisible(false);
		
	}
	
	/*set a map block to the appropriate color 
	 * options for: empty, train, switch, broken
	 */
	public void setTrackStatus(Block bl){
		JPanel p = blockPanels[bl.mapRow][bl.mapCol];
		Switch sw = bl.getSwitch();
		Color desired = new Color(0,0,0);
		
		if(bl.isBroken()){
			desired = broken;
			/*} else if(bl.isTrainPresent()){
			desired = train;*/
		} else if(sw != null){
			if(bl.getStation() != null){//switch is also a station in one case
				desired = both;
			} else {
				desired = switchh;	
			}			
		} else if(bl.getSwitchRoot() > 0 && owner.blocks[bl.getSwitchRoot()].getSwitch().getSwitchTaken() == bl.getBlockID()){
				if(bl.getStation() != null){//switch is also a station in one case
					desired = both;
				} else {
					desired = switchh;	
				}		
		} else if(bl.getStation() != null){
			desired = station;
		} else {
			desired = empty;
		}
		
		//set and repaint if the color needs changed
		if(!p.getBackground().equals(desired)){
			p.setBackground(desired);
			p.repaint();
		}
		
		//train presence is seperate from color now
		boolean wasPresent = p.getComponent(p.getComponentCount()-1).isVisible();
		if(wasPresent != bl.isTrainPresent()){
			
			p.getComponent(p.getComponentCount()-1).setVisible(bl.isTrainPresent()); //set train image
			p.getComponent(p.getComponentCount()-2).setVisible(!bl.isTrainPresent()); //remove border while train is there
			p.repaint();
		}
		
		
	}
	
	//seperate functions to paint panels with no block
	public void setStation(int x, int y){
		
	}
	public void setYard(int x, int y){
		
	}
	
	
	public void setStops(int thisHour, double expect, int lastHour){
		
		
		if(thisHour > -1 ) {valThisHour.setText("" + thisHour);}
		if(expect > -1 ) {valExpected.setText("" + expect);}
		if(lastHour > -1 ) {valLastHour.setText("" + lastHour);}
		
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
		frmCtc.setBounds(100, 100, 965, 747);
		frmCtc.getContentPane().setLayout(null);
		
		cboMode = new JComboBox();
		cboMode.setForeground(new Color(0, 100, 0));
		cboMode.setBackground(Color.LIGHT_GRAY);
		cboMode.setModel(new DefaultComboBoxModel(new String[] {"Manual", "MBO", "Fixed Block"}));
		cboMode.setMaximumRowCount(3);
		cboMode.setBounds(226, 11, 110, 20);
		frmCtc.getContentPane().add(cboMode);
		
		JLabel lblControlMode = new JLabel("Control Mode:");
		lblControlMode.setForeground(new Color(0, 255, 0));
		lblControlMode.setBounds(139, 14, 78, 14);
		frmCtc.getContentPane().add(lblControlMode);
		
		JScrollPane scrollPane_Announce = new JScrollPane();
		scrollPane_Announce.setBounds(10, 616, 929, 86);
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
		scrollPane_Locations.setBounds(582, 230, 357, 139);
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
		scrollPane_Routes.setBounds(582, 398, 357, 126);
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
		panel_time.setBounds(9, 5, 86, 44);
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
		pnlMaintenance.setBounds(582, 33, 357, 68);
		frmCtc.getContentPane().add(pnlMaintenance);
		pnlMaintenance.setLayout(null);
		
		JLabel lblSex = new JLabel("Action:");
		lblSex.setForeground(new Color(245, 255, 250));
		lblSex.setHorizontalAlignment(SwingConstants.CENTER);
		lblSex.setBounds(7, 30, 40, 14);
		pnlMaintenance.add(lblSex);
		
		cboClose = new JComboBox();
		cboClose.setModel(new DefaultComboBoxModel(new String[] {"Close", "Open"}));
		cboClose.setMaximumRowCount(2);
		cboClose.setForeground(new Color(255, 255, 255));
		cboClose.setBackground(new Color(153, 50, 204));
		cboClose.setBounds(47, 27, 74, 20);
		pnlMaintenance.add(cboClose);
		
		JLabel lblBlock = new JLabel("Block:");
		lblBlock.setHorizontalAlignment(SwingConstants.CENTER);
		lblBlock.setForeground(new Color(245, 255, 250));
		lblBlock.setBounds(123, 30, 40, 14);
		pnlMaintenance.add(lblBlock);
		
		txtCloseBlockNum = new JTextField();
		txtCloseBlockNum.setForeground(new Color(255, 255, 255));
		txtCloseBlockNum.setHorizontalAlignment(SwingConstants.CENTER);
		txtCloseBlockNum.setColumns(10);
		txtCloseBlockNum.setBackground(new Color(128, 0, 0));
		txtCloseBlockNum.setBounds(163, 27, 74, 20);
		pnlMaintenance.add(txtCloseBlockNum);
		
		JButton btnDoIt = new JButton("Do It");
		btnDoIt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					if((Integer.parseInt(txtCloseBlockNum.getText()) < 1 || Integer.parseInt(txtCloseBlockNum.getText()) > owner.blockCount)){
						setAnnouncement("Not a Block Number");
					} else if (cboClose.getSelectedIndex() == 0 ){ //close
						if(owner.blocks[Integer.parseInt(txtCloseBlockNum.getText())].isBroken()){
							setAnnouncement("Block already closed");
						} else {
							owner.ben.closeBlock(Integer.parseInt(txtCloseBlockNum.getText()));
						}
						
					} else { //open
						if(!owner.blocks[Integer.parseInt(txtCloseBlockNum.getText())].isBroken()){
							setAnnouncement("Block already Open");
						} else {
							owner.ben.closeBlock(Integer.parseInt(txtCloseBlockNum.getText())); //same function actually toggles
						}
						
					}
				} catch(Exception ex){
					setAnnouncement("Error doing things to block");
				}
				
			}
		});
		btnDoIt.setForeground(new Color(128, 0, 128));
		btnDoIt.setBounds(260, 26, 74, 23);
		pnlMaintenance.add(btnDoIt);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 128, 0));
		panel.setLayout(null);
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Route Trains", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(128, 0, 0)));
		panel.setBounds(582, 115, 357, 102);
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
			//	try{
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
				/*}catch(Exception ex){
					setAnnouncement("Error, Routing Failed: " + txtTrainNum.getText() + ", " + txtSpeedSet.getText() + ", " + txtDestBlockNum.getText());
				}*/
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
		pnlThru.setBounds(582, 532, 357, 61);
		frmCtc.getContentPane().add(pnlThru);
		
		JLabel lblThisHr = new JLabel("This Hr:");
		lblThisHr.setHorizontalAlignment(SwingConstants.CENTER);
		lblThisHr.setBounds(10, 28, 57, 14);
		pnlThru.add(lblThisHr);
		
		JLabel lblExpected = new JLabel("Expected:");
		lblExpected.setHorizontalAlignment(SwingConstants.CENTER);
		lblExpected.setBounds(127, 28, 61, 14);
		pnlThru.add(lblExpected);
		
		JLabel lblLastHr = new JLabel("Last Hr:");
		lblLastHr.setHorizontalAlignment(SwingConstants.CENTER);
		lblLastHr.setBounds(247, 28, 48, 14);
		pnlThru.add(lblLastHr);
		
		valThisHour = new JTextField();
		valThisHour.setForeground(new Color(255, 255, 0));
		valThisHour.setBackground(new Color(186, 85, 211));
		valThisHour.setText("0");
		valThisHour.setHorizontalAlignment(SwingConstants.CENTER);
		valThisHour.setEditable(false);
		valThisHour.setColumns(10);
		valThisHour.setBounds(71, 17, 51, 36);
		pnlThru.add(valThisHour);
		
		valExpected = new JTextField();
		valExpected.setText("0.0");
		valExpected.setHorizontalAlignment(SwingConstants.CENTER);
		valExpected.setForeground(Color.YELLOW);
		valExpected.setEditable(false);
		valExpected.setColumns(10);
		valExpected.setBackground(new Color(186, 85, 211));
		valExpected.setBounds(186, 17, 51, 36);
		pnlThru.add(valExpected);
		
		valLastHour = new JTextField();
		valLastHour.setText("0");
		valLastHour.setHorizontalAlignment(SwingConstants.CENTER);
		valLastHour.setForeground(Color.YELLOW);
		valLastHour.setEditable(false);
		valLastHour.setColumns(10);
		valLastHour.setBackground(new Color(186, 85, 211));
		valLastHour.setBounds(298, 17, 51, 36);
		pnlThru.add(valLastHour);
		
		JLabel lblKnownLocations = new JLabel("Known Locations");
		lblKnownLocations.setForeground(new Color(0, 0, 255));
		lblKnownLocations.setBounds(582, 216, 98, 14);
		frmCtc.getContentPane().add(lblKnownLocations);
		
		JLabel lblRecentCommands = new JLabel("Recent");
		lblRecentCommands.setForeground(new Color(255, 0, 0));
		lblRecentCommands.setBounds(582, 386, 110, 14);
		frmCtc.getContentPane().add(lblRecentCommands);
		
		JButton btnSpawn = new JButton("Make Train Puppy");
		btnSpawn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//fakeShit.spawnTrain();
				owner.spawnTrainCTC();
			}
		});
		btnSpawn.setForeground(new Color(0, 0, 205));
		btnSpawn.setBounds(664, 370, 273, 23);
		frmCtc.getContentPane().add(btnSpawn);
		
		JButton btnStartWithMap = new JButton("Debug: load map");
		btnStartWithMap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				owner.start();
			}
		});
		btnStartWithMap.setBounds(424, 10, 148, 23);
		frmCtc.getContentPane().add(btnStartWithMap);
		
		
		//all kinds of crazy jpanels for blocks
		blockPanels = new JPanel [mapSize][mapSize];
		int left = mapLeft;
		int top = mapTop;
		for(int i = 0; i < mapSize; i++){
			for (int j = 0; j < mapSize; j++){
				JPanel pnl = new JPanel();
				pnl.setBounds(left, top, 15, 15);
				pnl.setLayout(null);
				pnl.setBackground(Color.LIGHT_GRAY);
				frmCtc.getContentPane().add(pnl);
				
				/*
				//add train image (and hide it)
				JLabel trainlabel = new JLabel("");
				trainlabel.setBounds(0,0,15,15);				
				trainlabel.setIcon(new ImageIcon(trainPath));				
				trainlabel.setIconTextGap(-15); //if we add numbers they will appear above the image
				pnl.add(trainlabel);
				pnl.getComponent(0).setVisible(false); //hopefully this first label will always be zero
				
				//add track number and leave visible for now
				JLabel numLabel = new JLabel("" + i);
				numLabel.setBounds(0,0,15,15);
				numLabel.setForeground(new Color(100, 20, 200)); //purple
				numLabel.setFont(new Font("Times New Roman", Font.BOLD, 9));
				pnl.add(numLabel);
				*/
				
				//save reference in array
				blockPanels[i][j] = pnl;
				
				left+=15;
			}
			top+=15; //15x15 blocks
			left=mapLeft;
		}
	}
}
