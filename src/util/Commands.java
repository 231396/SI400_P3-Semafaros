package util;

public enum Commands {
	StartTrafficLight("start;"), UpdateTrafficLight("update;"), StopTrafficLight("stop;");

	private final String command;

	private Commands(String command) {
		this.command = command;
	}

	public String getValue() {
		return this.command;
	}
}
