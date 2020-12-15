package util;

import java.nio.*;
import java.nio.charset.StandardCharsets;

public class PacketWriter {
	
	public final static int BUFFER_SIZE = 256;
	
	ByteBuffer buffer;
		
	public void setBuffer() {
		setBuffer(BUFFER_SIZE);
	}
	
	public void setBuffer(int size) {
		buffer = ByteBuffer.allocate(size);
	}
	
	public int length() {
		return buffer.capacity();
	}
	
	// ------ WRITE FUNCS ------ //
	
	public void write(byte b) {
		buffer.put(b);
	}
	
	public void write(byte[] b) {
		buffer.put(b);
	}	
	
	public void write(boolean bool) {
		buffer.put(bool ? (byte)1 : (byte)0);
	}
	
	public void write(Integer i) {
		buffer.putInt(i);
	}
	
	public void write(char c) {
		buffer.putChar(c);
	}
	
	public void write(String str) {
		buffer.putInt(str.length());
		buffer.put(str.getBytes(StandardCharsets.UTF_8));
	}
	
	
	// ------ UTIL FUNCS ------ //
	
	public void clear() {
		buffer.clear();
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
