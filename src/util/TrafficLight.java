package util;

import java.net.InetAddress;

public class TrafficLight {
	
	private InetAddress address;
	private int port;
	private int state;
	
	public TrafficLight(InetAddress address,int port) {
		this.address = address;
		this.port = port;
		state = TrafficLightStates.GREEN.getState();
	}

	public InetAddress getAddress() {
		return address;
	}

	public int getPort() {
		return port;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
	
	

}
