package network_util;

import java.io.Closeable;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class NetworkListener extends Thread implements Closeable {

	public interface onNetworkReceived {
		public void onReceive(DatagramPacket packet);
	}

	private onNetworkReceived packetReceived;
	private byte[] buff = new byte[PacketWriter.BUFFER_SIZE];
	private DatagramSocket socket;
	private boolean running;

	public DatagramSocket getSocket() {
		return this.socket;
	}

	public NetworkListener(onNetworkReceived packetReceived, int port) throws SocketException {
		this.packetReceived = packetReceived;
		this.socket = new DatagramSocket(port);
		this.running = false;
	}

	public NetworkListener(onNetworkReceived packetReceived) throws SocketException {
		this.packetReceived = packetReceived;
		this.socket = new DatagramSocket();
		this.running = false;
	}

	public void run() {
		running = true;
		while (running) {
			try {
				DatagramPacket datagramPacket = new DatagramPacket(buff, buff.length);
				socket.receive(datagramPacket);
				this.packetReceived.onReceive(datagramPacket);
			} catch (IOException e) {
				System.out.println("Erro ao receber o Packet!");
			}
		}

	}

	@Override
	public void close() throws IOException {
		this.running = false;
		this.socket.close();
	}

}