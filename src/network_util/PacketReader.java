package network_util;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class PacketReader {
	ByteBuffer buffer;
	
	public void setBuffer(byte[] packet) {
		buffer = ByteBuffer.wrap(packet);
	}
	
	public boolean hasPacketEnd() {
		return buffer.remaining() <= 0;
	}
	
	// ------ READ FUNCS ------ //
	
	public byte readByte() {
		return buffer.get();
	}
	
	public byte[] readBytes() {
		int len = buffer.getInt();
		return readBytes(len);
	}
	
	public byte[] readBytes(int lenght) {
		byte[] array = new byte[lenght];
		for (int i = 0; i < lenght; i++)
			array[i] = buffer.get();		
		return array;
	}
	
	public boolean readBool() {
		return buffer.get() > 0 ? true : false;
	}
	
	public char readChar() {
		return buffer.getChar();
	}
	
	public int readInt() {
		return buffer.getInt();
	}
	
	public String readString() {
		int len = buffer.getInt();
		String value = new String(buffer.array(), buffer.position(), len, StandardCharsets.UTF_8);
		buffer.position(buffer.position() + len);
		return value;
	}
	
	// ------ UTIL FUNCS ------ //
	
	public void rewind() {
		buffer.rewind();
	}	
	
	public void print() {
		System.out.println(buffer.capacity());
		System.out.println(buffer.position());
		System.out.println(buffer.limit());
		System.out.println(buffer.remaining());
	}

	public byte[] toArray() {
		return buffer.array();
	}
}
