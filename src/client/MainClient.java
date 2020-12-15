package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import util.Commands;

public class MainClient {
    private byte[] buf = new byte[256];

    private DatagramSocket socket;
    private InetAddress address;
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MainClient mainClient;
		try {
			mainClient = new MainClient();
			System.out.println(mainClient.sendEcho(Commands.StartTrafficLight.getValue()));
			mainClient.close();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public MainClient() throws UnknownHostException, SocketException {
        socket = new DatagramSocket();
        address = InetAddress.getByName("localhost");
    }

    public String sendEcho(String msg) throws IOException {
    	byte[] buf_send = msg.getBytes();
        DatagramPacket packet 
          = new DatagramPacket(buf_send, buf_send.length, address, 4445);
        socket.send(packet);
        packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);
        String received = new String(
        packet.getData(), 0, packet.getLength());
        return received;
    }

    public void close() {
        socket.close();
    }

}