package util;

import java.io.Closeable;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class PacketReceiver extends Thread implements Closeable{

    public interface onPacketReceivedListener{
    	public void onPacketReceived(DatagramPacket packet);
    }
    
    private onPacketReceivedListener packetReceived;
    private byte[] buff = new byte[256];
    private DatagramSocket socket;
    private boolean running;

    public PacketReceiver(onPacketReceivedListener packetReceived, int port) throws SocketException {
        this.packetReceived = packetReceived;
    	this.socket = new DatagramSocket(port);
    	this.running = false;
    }
    
    public DatagramSocket getSocket() {
    	return this.socket;
    }

    public void run() {
    	running = true;
    	while(running) {
        	try {
        		DatagramPacket datagramPacket = new DatagramPacket(buff, buff.length);
				socket.receive(datagramPacket);
				this.packetReceived.onPacketReceived(datagramPacket);
			} catch (IOException e) {
				//Implementar uma tratativa de erro valida aqui
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
