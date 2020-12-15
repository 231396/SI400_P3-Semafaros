package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import util.ByteConverter;
import util.Commands;
import util.PacketReceiver;
import util.PacketReceiver.onPacketReceivedListener;

public class MainClient implements onPacketReceivedListener{
    private byte[] buf = new byte[256];
    
    private InetAddress address;
    
	public static void main(String[] args) {
		try {
			int server_port = Integer.parseInt(args[0]);
			int client_port = Integer.parseInt(args[1]);
			
			MainClient mainClient = new MainClient();
			PacketReceiver packetReceiver = new PacketReceiver(mainClient,client_port);
			Thread t = new Thread(packetReceiver);
			t.start();
			mainClient.sendStartCommand(packetReceiver.getSocket(),server_port);
			
		} catch (Exception e) {
			//Implementar uma tratativa de erro valida aqui
			System.out.println("Erro na inicialização do MainClient");
		} 
	}
	

	@Override
	public void onPacketReceived(DatagramPacket packet) {
		String received = new String(
		        packet.getData(), 0, packet.getLength());
		System.out.println("Resposta do Server :  "+received);
		
	}
	
	public MainClient() throws UnknownHostException, SocketException {
        address = InetAddress.getByName("localhost");
    }

    public void sendStartCommand(DatagramSocket socket, int server_port) throws IOException {
    	byte[] buff_send = ByteConverter.intToByteArray(Commands.START.ordinal());
        DatagramPacket packet = new DatagramPacket(buff_send, buff_send.length, address, server_port);
        System.out.println("Comando enviado, porta do packet : "+packet.getPort());
        socket.send(packet);
    }

}