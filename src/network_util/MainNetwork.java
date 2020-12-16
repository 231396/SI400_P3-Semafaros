package network_util;

import java.net.DatagramPacket;
import java.net.SocketException;

import network_util.NetworkListener.onNetworkReceived;

public abstract class MainNetwork {
	
	
	protected PacketReader pr = new PacketReader();
	protected PacketWriter pw = new PacketWriter();	
	
	protected NetworkListener nt;	
	
	protected MainNetwork(int port) throws SocketException {
		nt = new NetworkListener(onReceivePacket, port);	
		pw.setBuffer();
	}
	
	protected MainNetwork() throws SocketException {
		nt = new NetworkListener(onReceivePacket);
        pw.setBuffer();
    }	
	
	public void startListening() {
		nt.start();
	}
	
	onNetworkReceived onReceivePacket = new onNetworkReceived() {		
		@Override
		public void onReceive(DatagramPacket packet) {
			pr.setBuffer(packet.getData());				
			String received = pr.readString();

			System.out.println("Recieved Data: " + received);	
			
			listen(packet, received);
		}
	};	
	
	public abstract void listen(DatagramPacket packet, String received);
	
}
