package util;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class JClientScroll extends JScrollPane {

	private static final long serialVersionUID = 1L;
	
	ArrayList<JClient> clients = new ArrayList<>();
	JPanel jp;
	
	public JClientScroll() {		
		super();
		
		jp = new JPanel();
		jp.setLayout(new GridLayout(0, 1, 0, 5));
		
		setViewportView(jp);
		
		setBounds(10, 10, 315, 400);
		setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);		
		
//		try {
//			JClient[] tls = new JClient[10];
//			for (int i = 0; i < tls.length; i++) {
//				TrafficLight tl = new TrafficLight(InetAddress.getByName("localhost"), 1000 + i);
//				tls[i] = addClient(tl, TrafficLightStates.GREEN);
//			}
//			removeClient(tls[0].getTrafficLight());
//		} catch (UnknownHostException e) {
//			e.printStackTrace();
//		}
	}
	
	public void updateClient(TrafficLight tl, TrafficLightStates tls) {
		JClient jc = getJClient(tl);
		jc.changeState(tls);
	}	
	
	public JClient addClient(TrafficLight tl, TrafficLightStates initialColor) {
		JClient jc = new JClient(tl, 40, 15, initialColor);
		clients.add(jc);
		jp.add(jc);
		return jc;
	}
	
	public void removeClient(TrafficLight tl) {
		JClient jc = getJClient(tl);		
		clients.remove(jc);
		jp.remove(jc);
	}
	
	public JClient getJClient(TrafficLight tl) {
	    for(JClient jc : clients)
	        if(jc.getTrafficLight().equals(tl))
	            return jc;
	    return null;
	}
}
