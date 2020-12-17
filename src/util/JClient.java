package util;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class JClient extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private TrafficLight client;
	
	private JCircle[] circles;
	
	TrafficLightStates currentState;
	
	int preferredHeight;
	
	public JClient(TrafficLight tl, int circleSize, int offset, TrafficLightStates firstState) {
		setLayout(null);
		preferredHeight = circleSize;
		
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
	
	@Override
	public Dimension preferredSize() {
		return new Dimension(280, preferredHeight);
	}
	
	
	public TrafficLight getTrafficLight() {
		return client;
	}
}
