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

	public final static int tickTime = 2500;

	public static void main(String[] args) {
		try {
			int serverPort = Integer.parseInt(args[0]);
			
			MainServer mainServer = new MainServer(serverPort);
			mainServer.serverScreen.setVisible(true);
			mainServer.startListening();
			mainServer.startTrafficLightControl();
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
	public void listen(DatagramPacket packet) {
		try {
			CommandsClient cc = (CommandsClient) deserialize(packet);
			receive(cc, packet.getAddress(), packet.getPort());
		} catch (Exception e) {
			serverScreen.logln("Unknown command received");
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
		timer.scheduleAtFixedRate(timerStack, tickTime, tickTime);
	}
	
	private void onServerTrafficTick() {
		if (clients.size() > 0)
			serverScreen.logln("\nActive Clients(" + clients.size() + ")");
		for (TrafficLight tl : clients) {
			try {
				TrafficLightStates tls = tl.nextState();
				CommandsServer cs = tls.getCommandFromLight();
				sendSingle(cs, tl);
				serverScreen.getJClientScroll().updateClient(tl,  tls);
				serverScreen.logln(tl + " => " + tls);
			} catch (IOException e) {
				serverScreen.logln("Error trying to update the client: " +  tl);
				System.out.println("Error while sending command to update client TrafficLight on port :" + tl);
			}
		}
	}
	
	private byte[] serverSends(CommandsServer cs) throws IOException {
		return serialize(cs);
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

	private void receive(CommandsClient cc, InetAddress address, int port) throws Exception {
		TrafficLight client;
		switch (cc) {
			case startRequest:
				client = new TrafficLight(address, port);
				clients.add(client);
				serverScreen.logln("New connection: " + client);
				sendSingle(CommandsServer.startResponse, client);
				CommandsServer cs = CommandsServer.setLightGreen;			
				serverScreen.getJClientScroll().addClient(client, cs.getLightFromCommand());
				sendSingle(cs, client);
			break;
			case exit:
				client = getClient(address, port);
				clients.remove(client);
				serverScreen.getJClientScroll().removeClient(client);
				serverScreen.logln("Client disconnected: " + client);
			break;
		}
	}

	public TrafficLight getClient(InetAddress address, int port) {
		for (TrafficLight tl : clients)
			if (tl.sameAddress(address, port))
				return tl;
		return null;
	}
}
