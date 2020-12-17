package util;

import java.net.InetAddress;

public class TrafficLight {
	
	private InetAddress address;
	private int port;
	private TrafficLightStates state;
	
	public TrafficLight(InetAddress address,int port) {
		this.address = address;
		this.port = port;
		state = TrafficLightStates.GREEN;
	}

	public InetAddress getAddress() {
		return address;
	}

	public int getPort() {
		return port;
	}

	public TrafficLightStates getState() {
		return state;
	}
	
	public TrafficLightStates nextState() {
		//System.out.println("Estado anterior : "+ state.ordinal());
		this.state = state.next();
		//System.out.println("Estado proximo : "+ state.ordinal());
		return this.state;
	}

	public void setState(TrafficLightStates state) {
		this.state = state;
	}

	public boolean sameAdress(InetAddress address, int port) {
		return this.address == address && this.port == port;
	}	

}
