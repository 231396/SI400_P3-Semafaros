package util;

enum TrafficLightStates {
	GREEN(0),YELLOW(1),RED(2);
	
	private final int state;

	TrafficLightStates(int state) {
		this.state = state;
	}
	
	public int getState() {
		return state;
	}
}
