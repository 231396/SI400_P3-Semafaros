package util;

public enum TrafficLightStates {
	GREEN,
	YELLOW,
	RED;
	
	private static TrafficLightStates[] values = values();
    public TrafficLightStates next()
    {
        return values[(this.ordinal()+1) % values.length];
    }
}


