package server;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import client.CommandsClient;
import network_util.*;
import util.*;

public class MainServer extends MainNetwork {

	private ArrayList<TrafficLight> clients = new ArrayList<>();
	private Timer timer;
	private TimerTask timerStack;

	public static void main(String[] args) {
		try {
			int server_port = Integer.parseInt(args[0]);
			MainServer mainServer = new MainServer(server_port);
			mainServer.startListening();
			mainServer.startTrafficLightControl();

		} catch (SocketException e) {
			System.out.println("Erro na inicializacao do server!");
			e.printStackTrace();
		}
	}

	public MainServer(int port) throws SocketException {
		super(port);
	}

	@Override
	public void listen(DatagramPacket packet, String received) {
		try {
			CommandsClient cc = CommandsClient.valueOf(received);
			Receive(cc, packet.getAddress(), packet.getPort());
		} catch (Exception e) {
			System.out.println("Unknown commands: " + received);
		}
	}

	public void stop() {
		try {
			sendAll(CommandsServer.exit);
		} catch (IOException e) {
			System.out.println("Failed to notify all clients that the server closed!");
		}
	}

	// ----- SEND ----- //

	public void startTrafficLightControl() {
		if (timerStack != null)
			timerStack.cancel();

		timer = new Timer();
		timerStack = new TimerTask() {
			@Override
			public void run() {
				for (TrafficLight tl : clients) {
					try {
						switch (tl.nextState()) {
						case GREEN:
							sendSingle(CommandsServer.setLightGreen, tl);
							break;
						case YELLOW:
							sendSingle(CommandsServer.setLightYellow, tl);
							break;
						case RED:
							sendSingle(CommandsServer.setLightRed, tl);
							break;
						}
					} catch (IOException e) {
						System.out.println(
								"Error while sending command to update client TrafficLight on port :" + tl.getPort());
					}
				}
			};
		};

		timer.scheduleAtFixedRate(timerStack, 2500, 2500);
	}

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
		nt.getSocket().send(sendPacket);
	}

	// ----- RECEIVE ----- //

	private void Receive(CommandsClient cc, InetAddress address, int port) throws IOException {
		TrafficLight client;
		switch (cc) {
		case startRequest:
			client = new TrafficLight(address, port);
			clients.add(client);
			System.out.println("Client connected : " + address + ":" + port);
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
