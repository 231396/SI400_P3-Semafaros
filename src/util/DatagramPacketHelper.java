package util;

import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.InetAddress;

public class DatagramPacketHelper {
	
	public static DatagramPacket createDatagramPacket(Commands command, InetAddress address, int port) {
		
		byte[] buff = BigInteger.valueOf(command.ordinal()).toByteArray();
		
		return new DatagramPacket(buff, buff.length, address, port);
		
	}

}
