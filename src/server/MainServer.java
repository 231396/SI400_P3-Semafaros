package server;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

import client.CommandsClient;
import util.*;

public class MainServer {

	private DatagramSocket socket;
	private boolean running;
	
	private PacketReader pr = new PacketReader();
	private PacketWriter pw = new PacketWriter();	
	
	ArrayList<TrafficLight> clients = new ArrayList<>();
	
	public static void main(String[] args) {
		try {
			MainServer mainServer = new MainServer(7777);
			mainServer.listen();
		} catch (SocketException e) {
			System.out.println("Erro na inicializacao do server!");
			e.printStackTrace();
		}
	}

	public MainServer(int port) throws SocketException {
		socket = new DatagramSocket(port);
		pw.setBuffer();
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
					CommandsClient cc = CommandsClient.valueOf(received);
					Receive(cc, packet.getAddress(), packet.getPort());
				} catch (Exception e) {					
					System.out.println("Unknown commands: " + received);
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
	
	
	// ----- SEND ----- //


	private byte[] serverSends(CommandsServer cs) {
		pw.clear();
		pw.write(cs.toString());
		return pw.toArray();
	}
	
	public void sendSingle(CommandsServer cs, TrafficLight tl) throws IOException {
		byte[] sendData = serverSends(cs);
		send(sendData, tl);
	}
	
	public void sendAll(CommandsServer cs) throws IOException {
		byte[] sendData = serverSends(cs);
		for (TrafficLight tl : clients)
			send(sendData, tl);
	}
	
	private void send(byte[] sendData, TrafficLight tl) throws IOException {
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, tl.getAddress(), tl.getPort());
		socket.send(sendPacket);
	}
	
	// ----- RECEIVE ----- //
	
	private void Receive(CommandsClient cc, InetAddress address, int port) throws IOException {
		TrafficLight client;
		switch (cc) {
			case startRequest:
					client = new TrafficLight(address, port);
					clients.add(client);
					sendSingle(CommandsServer.startResponse, client);
				break;
			case exit:
					client = getClient(address, port);
					clients.remove(client);
				break;
		}
	}
	
	
	public TrafficLight getClient(InetAddress address, int port) {
		for (TrafficLight tl : clients)
			if (tl.sameAdress(address, port))
				return tl;
		return null;
	}
}
