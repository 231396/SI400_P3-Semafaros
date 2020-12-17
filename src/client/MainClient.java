package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketException;

import network_util.MainNetwork;
import network_util.Timeout;
import network_util.Timeout.OnTaskTimeout;
import server.CommandsServer;
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
    	pw.clear();
    	pw.write(cc.toString());
    	byte[] sendData = pw.toArray();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
        nt.getSocket().send(sendPacket);
        timeout.startTimeout();
    }
    
    @Override
	public void listen(DatagramPacket packet, String received) {
		try {					
			CommandsServer cs = CommandsServer.valueOf(received);
			receive(cs, packet.getAddress(), packet.getPort());
		} catch (Exception e) {					
			System.out.println("Unknown command: " + received);
		}
	}
	
	private void receive(CommandsServer cs, InetAddress address, int port) throws IOException {		
		//System.out.println(address + " : " + serverAddress);
		//System.out.println(port+ " : " + serverPort);
		if (!isServer(address, port)) {
			System.out.println("Command from non-server");
			return;
		}
		
		
		switch (cs) {
			case startResponse:
				timeout.stopTimeout();
				startTrafficLight();
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

	private void startTrafficLight() {
		//Acender a luz inicial
		clientScreen.startTrafficLight();
	}

	private void upadateTrafficLight(TrafficLightStates state) {
		//TODO - Trocar a cor
		//System.out.println("Trocando a cor do semaforo para: "+state.toString());
		clientScreen.changeState(state);
	}
	
	private void onServerExit() {
		//Desligar cliente
		System.out.println("Server has closed, the program will finish!");
		System.exit(0);	
	}
}