package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import util.Commands;

public class MainServer extends Thread {

	private DatagramSocket socket;
	private boolean running;
	private byte[] buf = new byte[256];

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MainServer mainServer;
		try {
			mainServer = new MainServer();
			mainServer.start();
		} catch (SocketException e) {
			System.out.println("Erro na inicialização do server!");
			e.printStackTrace();
		}
	}

	public MainServer() throws SocketException {
		socket = new DatagramSocket(4445);
	}

	public void run() {
		running = true;

		while (running) {
			try {
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				System.out.println("Esperando dados!");

				socket.receive(packet);

				InetAddress address = packet.getAddress();
				int port = packet.getPort();
				packet = new DatagramPacket(buf, buf.length, address, port);
				String received = new String(packet.getData(), 0, packet.getLength()).trim();
				System.out.println("Dados recibidos : " + received);
				
				//System.out.println("|"+received+ "|"+ Commands.StartTrafficLight.getValue()+"|");

				if (received.equals(Commands.StartTrafficLight.getValue())) {
					System.out.println("Iniciando Semaforo ! ");
					buf = "Semaforo iniciado!".getBytes();
					packet = new DatagramPacket(buf, buf.length, address, port);

				} else if (received.equals(Commands.UpdateTrafficLight.getValue())) {

				} else if (received.equals(Commands.StopTrafficLight.getValue())) {
					System.out.println("Finalizando Semaforo !;");
					buf = "Semaforo iniciado!;".getBytes();
					packet = new DatagramPacket(buf, buf.length, address, port);

				}else {
					
				}

				if (received.equals("end")) {
					running = false;
					continue;
				}
				socket.send(packet);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Erro na leitura ou envio dos dados!");
				e.printStackTrace();
			}
		}
		socket.close();
	}

}
