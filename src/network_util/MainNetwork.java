package network_util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.SocketException;

import network_util.NetworkListener.onNetworkReceived;

public abstract class MainNetwork {
	
	protected NetworkListener nt;	
	
	protected MainNetwork(int port) throws SocketException {
		nt = new NetworkListener(onReceivePacket, port);	
	}
	
	protected MainNetwork() throws SocketException {
		nt = new NetworkListener(onReceivePacket);
    }	
	
	public void startListening() {
		nt.start();
	}
	
	onNetworkReceived onReceivePacket = new onNetworkReceived() {		
		@Override
		public void onReceive(DatagramPacket packet) {
			listen(packet);
		}
	};	
	
	public abstract void listen(DatagramPacket packet);
		
	protected Object deserialize(DatagramPacket incomingPacket) throws IOException, ClassNotFoundException
	{
		return (new ObjectInputStream(new ByteArrayInputStream(incomingPacket.getData()))).readObject();
	}
	
	protected byte[] serialize(Object obj) throws IOException
	{
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		(new ObjectOutputStream(byteArrayOutputStream)).writeObject(obj);
		return (byteArrayOutputStream.toByteArray());
	}
}
