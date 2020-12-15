package util;

import java.nio.ByteBuffer;

public class ByteConverter {

	
	public static int byteArrayToInt(byte[] bytes) {
	     return ByteBuffer.wrap(bytes).getInt();
	}
	
	public static byte[] intToByteArray(int value) {
	     return  ByteBuffer.allocate(4).putInt(value).array();
	}
	
}
