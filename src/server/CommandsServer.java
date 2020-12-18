package server;

import util.TrafficLightStates;

public enum CommandsServer {
	startResponse,
	setLightGreen,
	setLightYellow,
	setLightRed,
	exit;
	
    public TrafficLightStates getLightFromCommand() throws Exception {    	
		switch (this) {
			case setLightGreen: return TrafficLightStates.GREEN;
			case setLightYellow: return TrafficLightStates.YELLOW;
			case setLightRed: return TrafficLightStates.RED;
			default:
				break;
		}
		throw new Exception("CommandsServer don't have counterpart in TrafficLightStates");
    }
}