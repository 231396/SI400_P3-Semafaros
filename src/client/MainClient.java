package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import server.CommandsServer;
import util.*;

public class MainClient {

    private DatagramSocket socket;    
    boolean running;    
    
    private InetAddress serverAddress;
    private int serverPort;
    
	private PacketReader pr = new PacketReader();
	private PacketWriter pw = new PacketWriter();	
	    
	public static void main(String[] args) {
		try {
			MainClient mainClient = new MainClient(InetAddress.getByName("localhost"), 7777);
			mainClient.sendPacket(CommandsClient.startRequest);
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public MainClient(InetAddress serverAddress, int serverPort) throws SocketException {
        socket = new DatagramSocket();
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        pw.setBuffer();
    }	
	
	public boolean isServer(InetAddress address, int port) {
		return this.serverAddress == address && this.serverPort == port;
	}

    public void sendPacket(CommandsClient cc) throws IOException {
    	pw.clear();
    	pw.write(cc.toString());
    	byte[] sendData = pw.toArray();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
		socket.send(sendPacket);
    }
    
	public void listen() {
		running = true;		
		byte[] buffer = new byte[pw.length()];
		
		while (running) {
			try {
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				
				System.out.println(String.format("Waiting Data on port %d...", socket.getLocalPort()));
				socket.receive(packet);
				
				pr.setBuffer(packet.getData());				
				String received = pr.readString();

				System.out.println("Recieved Data: " + received);
				
				try {					
					CommandsServer cs = CommandsServer.valueOf(received);
					Receive(cs, packet.getAddress(), packet.getPort());
				} catch (Exception e) {					
					System.out.println("Unknown command: " + received);
				}
				
			} 
			catch (IOException e) {
				System.out.println("Error on data reading!");
				e.printStackTrace();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		socket.close();
	}
	
	private void Receive(CommandsServer cs, InetAddress address, int port) throws IOException {
		if (!isServer(address, port)) {
			System.out.println("Command from non-server");
			return;
		}
		
		switch (cs) {
			case startResponse:
				startTrafficLight();
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
		//TODO - Acender a luz inicial
	}


	private void upadateTrafficLight(TrafficLightStates state) {
		//TODO - Trocar a cor
	}
	
	private void onServerExit() {
		//TODO - Desligar cliente
	}
}