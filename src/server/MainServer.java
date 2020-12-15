package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;

import util.Commands;
import util.PacketReceiver;
import util.PacketReceiver.onPacketReceivedListener;

public class MainServer implements onPacketReceivedListener{

	private DatagramSocket socket;


	public static void main(String[] args) {
		
		
		try {
			
			int server_port = Integer.parseInt(args[0]);
			
			MainServer mainServer = new MainServer();
			PacketReceiver packetReceiver = new PacketReceiver(mainServer,server_port);
			Thread t = new Thread(packetReceiver);
			t.start();
		} catch (SocketException e) {
			System.out.println("Erro na inicialização do server!");
			e.printStackTrace();
		}
	}

	@Override
	public void onPacketReceived(DatagramPacket packet) {
		
		byte[] buff;
		InetAddress address = packet.getAddress();
		int port = packet.getPort();
		
		int commandReceived = ByteBuffer.wrap(packet.getData()).getInt();				

		System.out.println("Comando recebido - port : "+port+" - address : "+address.toString());
		
		if (commandReceived == Commands.START.ordinal() ) {
			System.out.println("Iniciando Semaforo !");
			buff = "Semaforo iniciado!;".getBytes();
			packet = new DatagramPacket(buff, buff.length, address, port);

		} else if (commandReceived == Commands.UPDATE.ordinal()) {
			System.out.println("Atualizando Semaforo !");
			buff = "Atualizando Semaforo!;".getBytes();
			packet = new DatagramPacket(buff, buff.length, address, port);

		} else if (commandReceived == Commands.STOP.ordinal()) {
			System.out.println("Finalizando Semaforo !");
			buff = "Finalizando Semaforo!;".getBytes();
			packet = new DatagramPacket(buff, buff.length, address, port);
		}else {
			System.out.println("Comando Invalido");
			buff = "Comando Invalido".getBytes();
			packet = new DatagramPacket(buff, buff.length, address, port);
		}
		
		try {
			socket.send(packet);
		} catch (IOException e) {
			//Implementar uma tratativa de erro valida aqui
			System.out.println("Erro ao responder o comando do cliente");
		}
	}

	public MainServer() throws SocketException{
		socket = new DatagramSocket();
	}

}
