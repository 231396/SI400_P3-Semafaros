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
	
	public JClient(TrafficLight tl, int circleRadius, int circleSpace, TrafficLightStates firstState) {
		setLayout(null);
		currentState = firstState;
		
		client = tl;
		circles = new JCircle[] { 
				new JCircle(Color.green), 
				new JCircle(Color.yellow), 
				new JCircle(Color.red) 
		};
		
		int x = 0;
		circles[0].setBounds(x, 0, circleRadius);
		x += circleRadius + circleSpace;
		circles[1].setBounds(x, 0, circleRadius);
		x += circleRadius + circleSpace;
		circles[2].setBounds(x, 0, circleRadius);
		
		JLabel labelAddress = new JLabel(tl.getAddress().toString()); 
		JLabel labelPort = new JLabel(String.valueOf(tl.getPort())); 
		
    	for (JCircle c : circles)
    		add(c);
    	add(labelAddress);
    	add(labelPort);
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
