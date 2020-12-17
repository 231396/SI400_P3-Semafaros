package view;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

import util.JCircle;
import util.TrafficLight;
import util.TrafficLightStates;

public class JClient extends JPanel {

	private static final long serialVersionUID = 1L;
	
	public TrafficLight client;
	
	private JCircle[] circles;
	
	TrafficLightStates currentState;
	
	public JClient(TrafficLight tl, int circleSize, int offset, TrafficLightStates firstState) {
		setLayout(null);
		setSize(280, circleSize);
		
		currentState = firstState;
		
		client = tl;
		circles = new JCircle[] { 
				new JCircle(Color.green), 
				new JCircle(Color.yellow), 
				new JCircle(Color.red) 
		};
		
		int x = 0;
		circles[0].setBounds(x, 0, circleSize);
		x += circleSize + offset;
		circles[1].setBounds(x, 0, circleSize);
		x += circleSize + offset;
		circles[2].setBounds(x, 0, circleSize);
		
		JLabel labelAddress = new JLabel(tl.getAddress().toString()); 
		JLabel labelPort = new JLabel(String.valueOf(tl.getPort())); 
		
		x += circleSize + offset;
		labelAddress.setBounds(x, -circleSize, 100, 100);
		labelPort.setBounds(x, -circleSize/2, 100, 100);
		
		add(labelAddress);
		add(labelPort);
		
    	for (JCircle c : circles)
    		add(c);
	}	
	
	public void changeState(TrafficLightStates state) {
		getJCircle(currentState).changeState(false);
		getJCircle(state).changeState(true);
		currentState = state;
	}
	
	private JCircle getJCircle(TrafficLightStates state) {
		return circles[state.ordinal()];
	}
	
}
