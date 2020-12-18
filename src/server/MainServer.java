package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import client.CommandsClient;
import network_util.MainNetwork;
import util.OnClose;
import util.TrafficLight;
import util.TrafficLightStates;
import view.ServerScreen;

public class MainServer extends MainNetwork {

	private ArrayList<TrafficLight> clients = new ArrayList<>();
	private Timer timer;
	private TimerTask timerStack;

	private ServerScreen serverScreen;
	
	public static void main(String[] args) {
		try {
			int serverPort = Integer.parseInt(args[0]);
			
			MainServer mainServer = new MainServer(serverPort);
			mainServer.serverScreen.setVisible(true);
			mainServer.startListening();
			mainServer.startTrafficLightControl();
			
//			try {
//				MainClient mc = new MainClient(InetAddress.getByName("localhost"), serverPort);
//				mc.sendPacket(CommandsClient.startRequest);
//				mc.startListening();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}			
		} catch (SocketException e) {
			System.out.println("Erro na inicializacao do server!");
			e.printStackTrace();
		}
	}

	public MainServer(int port) throws SocketException {
		super(port);
		serverScreen = new ServerScreen();
		serverScreen.setTitle("Server: " + port);
		serverScreen.setCloseEvent(new OnClose() {
			public void onCloseTrigger() {
				onCloseEvent();
			}
		});
	}
	
	public void onCloseEvent(){
		stop();
    	System.exit(0);
	}	

	@Override
	public void listen(DatagramPacket packet, String received) {
		try {
			CommandsClient cc = CommandsClient.valueOf(received);
			Receive(cc, packet.getAddress(), packet.getPort());
		} catch (Exception e) {
			System.out.println("Unknown commands: " + received);
			e.printStackTrace();
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
				onServerTrafficTick();
			};
		};
		timer.scheduleAtFixedRate(timerStack, 2500, 2500);
	}
	
	private void onServerTrafficTick() {
		for (TrafficLight tl : clients) {
			try {
				TrafficLightStates tls = tl.nextState();
				CommandsServer cs = tls.getCommandFromLight();
				sendSingle(cs, tl);
				serverScreen.getJClientScroll().updateClient(tl,  tls);
			} catch (IOException e) {
				System.out.println("Error while sending command to update client TrafficLight on port :" + tl.getPort());
			}
		}
	}
	
	private byte[] serverSends(CommandsServer cs) {
		pw.clear();
		pw.write(cs.toString());
		return pw.toArray();
	}

	public void sendSingle(CommandsServer cs, TrafficLight tl) throws IOException {
//		System.out.println(tl);
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

	private void Receive(CommandsClient cc, InetAddress address, int port) throws Exception {
		TrafficLight client;
		switch (cc) {
		case startRequest:
			client = new TrafficLight(address, port);
			clients.add(client);
			System.out.println("Client connected : " + address + ":" + port);
			sendSingle(CommandsServer.startResponse, client);
			CommandsServer cs = CommandsServer.setLightGreen;			
			serverScreen.getJClientScroll().addClient(client, cs.getLightFromCommand());
			sendSingle(cs, client);
			break;
		case exit:
			client = getClient(address, port);
			clients.remove(client);
			serverScreen.getJClientScroll().removeClient(client);
			System.out.println(clients.size());
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
