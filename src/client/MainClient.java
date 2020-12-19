package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketException;

import network_util.MainNetwork;
import network_util.Timeout;
import network_util.Timeout.OnTaskTimeout;
import server.CommandsServer;
import util.OnClose;
import util.TrafficLightStates;
import view.ClientScreen;

public class MainClient extends MainNetwork {

    private InetAddress serverAddress;
    private int serverPort;
    private Timeout timeout;
    private ClientScreen clientScreen;
    
	public static void main(String[] args) {
		try {
			InetAddress server_ip = InetAddress.getByName(args[0]);
			int server_port = Integer.parseInt(args[1]);
			
			MainClient mainClient = new MainClient(server_ip, server_port);
			mainClient.sendPacket(CommandsClient.startRequest);
			mainClient.startListening();
			
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public MainClient(InetAddress serverAddress, int serverPort) throws SocketException {
		super();
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
		this.timeout = new Timeout(onTaskTimeout,5000);
		
		clientScreen = new ClientScreen();
		clientScreen.setVisible(true);
		clientScreen.setCloseEvent(new OnClose() {
			public void onCloseTrigger()
			{
				onCloseEvent();
			}
		});
    }	
	
	public void onCloseEvent(){
		try {
			sendPacket(CommandsClient.exit);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	System.exit(0);
	}
	
	private OnTaskTimeout onTaskTimeout = new OnTaskTimeout() {
		@Override
		public void onTimeout() {
			System.out.println("Timeout on sending command");
			System.exit(1);
		}
	};
	
	public boolean isServer(InetAddress address, int port) {
		return this.serverAddress.equals(address) && this.serverPort == port;
	}

    public void sendPacket(CommandsClient cc) throws IOException {
    	byte[] sendData = serialize(cc);
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
        nt.getSocket().send(sendPacket);
        timeout.startTimeout();
    }
    
    @Override
	public void listen(DatagramPacket packet) {
		try {					
			CommandsServer cs = (CommandsServer) deserialize(packet);
			receive(cs, packet.getAddress(), packet.getPort());
		} catch (Exception e) {					
			System.out.println("Unknown command");
		}
	}
	
	private void receive(CommandsServer cs, InetAddress address, int port) throws IOException {		
		if (!isServer(address, port)) {
			System.out.println("Command from non-server");
			return;
		}
		
		
		switch (cs) {
			case startResponse:
				timeout.stopTimeout();
				clientScreen.setTitle("Connected: " + serverAddress + ":" + serverPort + " on Port: " + nt.getSocket().getLocalPort());
				System.out.println("Connected to the server");
			break;
			case setLightGreen:
				upadateTrafficLight(TrafficLightStates.GREEN);
			break;
			case setLightYellow:
				upadateTrafficLight(TrafficLightStates.YELLOW);
			break;
			case setLightRed:
				upadateTrafficLight(TrafficLightStates.RED);
			break;
			case exit:
				onServerExit();
			break;
		}
	}

	private void upadateTrafficLight(TrafficLightStates state) {
		clientScreen.changeState(state);
	}
	
	private void onServerExit() {
		System.out.println("Server has closed, the program will finish!");
		System.exit(0);	
	}
}