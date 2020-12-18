package util;

import server.CommandsServer;

public enum TrafficLightStates {
	GREEN,
	YELLOW,
	RED;
	
	private static TrafficLightStates[] values = values();
    public TrafficLightStates next()
    {
        return values[(this.ordinal()+1) % values.length];
    }
    
    public CommandsServer getCommandFromLight() {    	
		switch (this) {
			case GREEN: return CommandsServer.setLightGreen;
			case YELLOW: return CommandsServer.setLightYellow;
			case RED: return CommandsServer.setLightRed;
		}
		return null;
    }
}


