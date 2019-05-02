import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 *Class to create a GUI for calculating the Hamming Distance of various stations read in from the file Mesonet.text and displays the
 *information through the use of JPanels.
 *
 * @author Michael Smith
 * @version 2019-05-02
 */
public class HammingDistanceGUI extends JFrame
{
	/**
	 * The file to be read in. Contains the stations.
	 */
	private String file = "Mesonet.txt";
	
	/**
	 * stationsArray contains the Array of stations read in through the stations ArrayList and converted to a String Array
	 * for use in the JComboBox drop down menu.
	 */
	private String[] stationsArray;
	
	/**
	 * Stations read from the file and put into an ArrayList.
	 */
	private ArrayList<String> stations = new ArrayList<String>();
	
	/**
	 * ArrayLists containing all the stations the corresponding distance from the one being compared.
	 */
	private ArrayList<String> stationsDistance0 = new ArrayList<String>();
	private ArrayList<String> stationsDistance1 = new ArrayList<String>();
	private ArrayList<String> stationsDistance2 = new ArrayList<String>();
	private ArrayList<String> stationsDistance3 = new ArrayList<String>();
	private ArrayList<String> stationsDistance4 = new ArrayList<String>();
	
	/**
	 * Final Integers for the creation of the slider
	 */
	static final int SLIDER_MIN = 1;
	static final int SLIDER_MAX = 4;
	static final int SLIDER_INIT = 2;
	
	/**
	 * The initial station being compared and set on the JComboBox drop down menu.
	 */
	static final String INITIAL_COMPARE = "NRMN";
	
	/**
	 * The Hamming Distance to be compared with and shown in the comparedStations panel.
	 */
	private int hammingDistance = SLIDER_INIT;
	
	/**
	 * The Stations being compared.
	 */
	private String compareWith = INITIAL_COMPARE;
		
	/**
	 * Constructor for all of the GUI 
	 */
	public HammingDistanceGUI()
	{
		// Reading in the file
		try
    	{
    		read();
    	}
    	catch(IOException e)
    	{
    		System.out.println("Error reading from file!\n");
    		e.printStackTrace();
    	}
		
		// Creating the initial frame to base all other panels out of.
		JFrame gui = new JFrame("Hamming Distance");
		gui.setLayout(new GridLayout(1, 2));
		gui.setPreferredSize(new Dimension(550, 750));
	
		// Splitting the main part of the project into two panels one side being the assigned part of the project
		// and the second being the free space for the creative idea.
		JPanel project = new JPanel();
		project.setLayout(new GridBagLayout());
		JPanel freeSpace = new JPanel();
		
		// Constraints for the GridBagLayout used on the project panel
		GridBagConstraints layoutConst = new GridBagConstraints();
		layoutConst.gridx = 0;
		layoutConst.gridy = 0;

		// Adding both panels to the main gui panel.
		gui.add(project);
		gui.add(freeSpace);

		
		// Further splitting all the parts of the project panel into more panels.
		JPanel hammingDistancePanel = new JPanel();
		JPanel hammingDistanceSliderPanel = new JPanel();
		JPanel showStationPanel = new JPanel();
		JPanel comparedStationsPanel = new JPanel();
		JPanel compareWithPanel = new JPanel();
		JPanel calculatePanel = new JPanel();
		JPanel distance0Panel = new JPanel();
		JPanel distance1Panel = new JPanel();
		JPanel distance2Panel = new JPanel();
		JPanel distance3Panel = new JPanel();
		JPanel distance4Panel = new JPanel();
		JPanel addStationPanel = new JPanel();
		
		// Formatting these panels using the layout constraints created earlier.
		project.add(hammingDistancePanel, layoutConst);
		layoutConst.gridy = 1;
		project.add(hammingDistanceSliderPanel, layoutConst);
		layoutConst.gridy = 2;
		project.add(showStationPanel, layoutConst);
		layoutConst.gridy = 3;
		project.add(comparedStationsPanel, layoutConst);
		layoutConst.gridy = 4;
		project.add(compareWithPanel, layoutConst);
		layoutConst.gridy = 5;
		project.add(calculatePanel, layoutConst);
		layoutConst.gridy = 6;
		project.add(distance0Panel, layoutConst);
		layoutConst.gridy = 7;
		project.add(distance1Panel, layoutConst);
		layoutConst.gridy = 8;
		project.add(distance2Panel, layoutConst);
		layoutConst.gridy = 9;
		project.add(distance3Panel, layoutConst);
		layoutConst.gridy = 10;
		project.add(distance4Panel, layoutConst);
		layoutConst.gridy = 11;
		project.add(addStationPanel, layoutConst);
		
		 // Creating the area to show what value of Hamming Distance in being compared and a slider to manipulate 
		 // this value of 1 through 4.
		JLabel hammingDistanceInfo = new JLabel("Enter Hamming Dist:");
		hammingDistancePanel.add(hammingDistanceInfo);
		
		JTextArea hammingDistanceView = new JTextArea(String.valueOf(hammingDistance), 1, 12);
		hammingDistancePanel.add(hammingDistanceView);
		hammingDistanceView.setEditable(false);
		hammingDistanceView.setOpaque(false);
		hammingDistanceView.setBackground(new Color(0, 0, 0, 0));
		hammingDistanceView.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		
		JSlider hammingDistanceSlider = new JSlider(JSlider.HORIZONTAL, SLIDER_MIN, SLIDER_MAX, SLIDER_INIT);
		hammingDistanceSliderPanel.add(hammingDistanceSlider);
		hammingDistanceSlider.setMajorTickSpacing(1);
		hammingDistanceSlider.setMinorTickSpacing(1);
		hammingDistanceSlider.setPaintTicks(true);
		hammingDistanceSlider.setPaintLabels(true);
		
		hammingDistanceSlider.addChangeListener((e) -> {
			JSlider source = (JSlider)e.getSource();
			hammingDistance = (int)source.getValue();
			hammingDistanceView.setText(String.valueOf(hammingDistance));
		});
	
		// A button to calculate and show the stations with the selected distance from the station being compared.
		JButton showStation = new JButton("Show Station"); 
		showStationPanel.add(showStation);
	
		// ScrollPane to allow scrolling through the stations produced using the showStation button.
		JTextArea comparedStations = new JTextArea("", 15, 20);
		JScrollPane scroll = new JScrollPane(comparedStations, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED , JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		comparedStationsPanel.add(scroll);
		comparedStations.setEditable(false);
		
		showStation.addActionListener((e) -> {
			calculateHammingDistanceOtherStations(compareWith);
			comparedStations.setText(setComparedStations());
		});
	
		// JComboBox drop down menu to select the station being compared with all other stations.
		JLabel compareWithText = new JLabel("Compare with:");
		compareWithPanel.add(compareWithText);
		
		JComboBox<String> stationsList = new JComboBox<String>(stationsArray);
		stationsList.setSelectedItem(INITIAL_COMPARE);
		compareWithPanel.add(stationsList);
		
		stationsList.addActionListener((e) -> {
			@SuppressWarnings("unchecked")
			JComboBox<String> source = (JComboBox<String>)e.getSource();
			compareWith = (String)source.getSelectedItem();
		});
		
		// Button to calculate and display the number of stations the corresponding distance away from the
		// station being compared.
		JButton calculateButton = new JButton("Calculate HD");
		calculatePanel.add(calculateButton);
		
		JLabel distance0Info = new JLabel("Distance 0");
		JTextArea distance0Text = new JTextArea("", 1, 12);
		distance0Text.setEditable(false);
		distance0Text.setOpaque(false);
		distance0Text.setBackground(new Color(0, 0, 0, 0));
		distance0Text.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		
		distance0Panel.add(distance0Info);
		distance0Panel.add(distance0Text);
		
		JLabel distance1Info = new JLabel("Distance 1");
		JTextArea distance1Text = new JTextArea("", 1, 12);
		distance1Text.setEditable(false);
		distance1Text.setOpaque(false);
		distance1Text.setBackground(new Color(0, 0, 0, 0));
		distance1Text.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		
		distance1Panel.add(distance1Info);
		distance1Panel.add(distance1Text);
		
		JLabel distance2Info = new JLabel("Distance 2");
		JTextArea distance2Text = new JTextArea("", 1, 12);
		distance2Text.setEditable(false);
		distance2Text.setOpaque(false);
		distance2Text.setBackground(new Color(0, 0, 0, 0));
		distance2Text.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		
		distance2Panel.add(distance2Info);
		distance2Panel.add(distance2Text);
		
		JLabel distance3Info = new JLabel("Distance 3");
		JTextArea distance3Text = new JTextArea("", 1, 12);
		distance3Text.setEditable(false);
		distance3Text.setOpaque(false);
		distance3Text.setBackground(new Color(0, 0, 0, 0));
		distance3Text.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		
		distance3Panel.add(distance3Info);
		distance3Panel.add(distance3Text);
		
		JLabel distance4Info = new JLabel("Distance 4");
		JTextArea distance4Text = new JTextArea("", 1, 12);
		distance4Text.setEditable(false);
		distance4Text.setOpaque(false);
		distance4Text.setBackground(new Color(0, 0, 0, 0));
		distance4Text.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		
		distance4Panel.add(distance4Info);
		distance4Panel.add(distance4Text);
		
		calculateButton.addActionListener((e) -> {
			calculateHammingDistanceOtherStations(compareWith);
			distance0Text.setText(String.valueOf(stationsDistance0.size()));
			distance1Text.setText(String.valueOf(stationsDistance1.size()));
			distance2Text.setText(String.valueOf(stationsDistance2.size()));
			distance3Text.setText(String.valueOf(stationsDistance3.size()));
			distance4Text.setText(String.valueOf(stationsDistance4.size()));
		});
		
		// Button to add the station in teh JTextArea newStation to the list of stations.
		JButton addStation = new JButton("Add Station");
		addStationPanel.add(addStation);
		
		JTextArea newStation = new JTextArea("ZERO", 1, 12);
		newStation.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		addStationPanel.add(newStation);
		
		addStation.addActionListener((e) -> {
		stations.add(newStation.getText());
		
		stationsList.addItem(newStation.getText());
		
		});
		
		// The free space part of the project. Using a JFXPanel to make a WebView version of the Wikipedia 
		// article on Hamming Distance. This is put into the other panel in the gui.
		JFXPanel fxpanel=new JFXPanel();
		freeSpace.add(fxpanel);

		Platform.runLater(new Runnable() {
		@Override
		public void run()
		{
		    WebEngine engine;
		    WebView webView=new WebView();
		    engine = webView.getEngine();
		    fxpanel.setScene(new Scene(webView));
		    engine.load("https://en.wikipedia.org/wiki/Hamming_distance");
		 }
		 });
		// JFrame basics.
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.pack();
		gui.setVisible(true);
	}

	/**
	 * Returns the stations with the corresponding distance value set in hammingDistance as a String and each station
	 * on a new line.
	 * @return String contentTemp each station with HammingDistance on a new line.
	 */
	private String setComparedStations()
	{
		String contentTemp = "";
		if(hammingDistance == 1)
		{
			for(int i = 0; i < stationsDistance1.size(); ++i)
			{
				contentTemp += stationsDistance1.get(i) + "\n";
			}
		}
		else if(hammingDistance == 2)
		{
			for(int i = 0; i < stationsDistance2.size(); ++i)
			{
				contentTemp += stationsDistance2.get(i) + "\n";
			}
		}
		else if(hammingDistance == 3)
		{
			for(int i = 0; i < stationsDistance3.size(); ++i)
			{
				contentTemp += stationsDistance3.get(i) + "\n";
			}
		}
		else if(hammingDistance == 4)
		{
			for(int i = 0; i < stationsDistance4.size(); ++i)
			{
				contentTemp += stationsDistance4.get(i) + "\n";
			}
		}
	return contentTemp;
	}
	
	/**
	 * Scanner reads each line of the file into the ArrayList stations.
	 * @throws IOException
	 */
	private void read() throws IOException
    {
		Scanner sc = new Scanner(new File(file));
		while(sc.hasNextLine())
		{
			stations.add(sc.next());
			sc.nextLine();
		}
		stationsArray = makeStationsArray();

		sc.close();
		
    }
	
	/**
	 * Creates a String[] Array version of the ArrayList stations for use in the JComboBox
	 * @return String[] stationsTemp
	 */
	public String[] makeStationsArray()
	{
		String[] stationsTemp = new String[stations.size()];
		for(int i = 0; i < stations.size(); ++i)
		{
			stationsTemp[i] = stations.get(i);
		}
		return stationsTemp;
	}
	
	/**
	 * Return the ArrayList<String> stations.
	 * @return ArrayList<String> stations
	 */
	public ArrayList<String> getStations()
	{
		return stations;
	}
	
	/**
	 * Calculates the Hamming Distance between two stations used in calculateHammingDistanceOtherStations
	 * to compare each distance of the two stations being compared.
	 * @param station1
	 * @param station2
	 * @return int distance
	 */
	public int calculateHammingDistance(String station1, String station2)
	{
		int distance = 0;
		for(int index = 0; index < 4; ++index)
		{
			if(station1.charAt(index) != station2.charAt(index))
			{
				++distance;
			}
		}
		return distance;
	}
	
	/**
	 * Calculates the distances of each station to the station being compared from compareWith String then
	 * places each of those in corresponding stationsDistance ArrayLists depending on that distance.
	 * @param station the station to be compared to with all the stations in the stations ArrayList
	 */
	public void calculateHammingDistanceOtherStations(String station)
	{
		int hamDistance = 0;
		ArrayList<String> stationsDistance0Temp = new ArrayList<String>();
		ArrayList<String> stationsDistance1Temp = new ArrayList<String>();
		ArrayList<String> stationsDistance2Temp = new ArrayList<String>();
		ArrayList<String> stationsDistance3Temp = new ArrayList<String>();
		ArrayList<String> stationsDistance4Temp = new ArrayList<String>();
		for(int index = 0; index < stations.size(); ++index)
		{
			hamDistance = calculateHammingDistance(station, stations.get(index));
			if(hamDistance == 0)
			{
				stationsDistance0Temp.add(stations.get(index));
			}
			else if(hamDistance == 1)
			{
				stationsDistance1Temp.add(stations.get(index));
			}
			else if(hamDistance == 2)
			{
				stationsDistance2Temp.add(stations.get(index));
			}
			else if(hamDistance == 3)
			{
				stationsDistance3Temp.add(stations.get(index));
			}
			else if(hamDistance == 4)
			{
				stationsDistance4Temp.add(stations.get(index));
			}
			
		}
		stationsDistance0 = stationsDistance0Temp;
		stationsDistance1 = stationsDistance1Temp;
		stationsDistance2 = stationsDistance2Temp;
		stationsDistance3 = stationsDistance3Temp;
		stationsDistance4 = stationsDistance4Temp;
	}

	/**
	 * Main method to create the GUI.
	 * @param args
	 */
	public static void main(String[] args) 
	{
		new HammingDistanceGUI();
	}

}
