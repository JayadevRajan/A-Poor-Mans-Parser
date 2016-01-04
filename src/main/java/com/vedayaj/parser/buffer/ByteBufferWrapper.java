package com.vedayaj.parser.buffer;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

import com.vedayaj.parser.buffer.exception.UnderflowException;

public class ByteBufferWrapper 
{
	private ByteBuffer buffer;
	
	private static int CHUNK_SIZE = 256;
	
	public ByteBufferWrapper(FileInputStream fis, int length) throws IOException
	{
		buffer = ByteBuffer.allocate(length);
		
		int read;
		byte[] b = new byte[CHUNK_SIZE];
		while( (read = fis.read(b)) != -1)
		{
			//reading the last buffer. 
			if(read < CHUNK_SIZE)
			{
				byte[] bnew = Arrays.copyOfRange(b, 0, read);
				buffer.put(bnew);
			}
			else
			{
				buffer.put(b);
			}
		}
		
		//reset to zero to get ready for read. 
		buffer.flip();
	}
	
	public ByteBufferWrapper(byte[] bytes)
	{
		buffer = ByteBuffer.allocate(bytes.length);
		buffer.put(bytes);
		
		buffer.flip();
	}
	
	public byte[] getBytesByLength(int length) throws UnderflowException
	{
		if(length > buffer.remaining())
			throw new UnderflowException();
		
		byte[] b = new byte[length];
		buffer.get(b, 0, length);
		
		return b;
	}
	
	public byte[] getBytesByTerminator(byte[] terminator)
	{
		if(terminator.length == 0)
		{
			return null;
		}
		
		int terminatorIndex = 0;
		
		//note the start position
		int startPosition = buffer.position();
		int endPosition = -1;
		
		while(buffer.remaining() > 0)
		{
			if(buffer.get() == terminator[terminatorIndex])
				terminatorIndex++;
			else
				terminatorIndex = 0;
			
			if( terminatorIndex == terminator.length)
			{
				endPosition = buffer.position();
				break;
			}
			else
			{
				
			}
		}
		
		if(endPosition == -1)
		{
			//didn't find the terminator. Reset and return; 
			buffer.position(startPosition);
			return null;
		}
		
		//reset buffer to orig 
		buffer.position(startPosition);
		
		byte[] buf = new byte[endPosition - startPosition];
		buffer.get(buf);
		
		byte[] bufWithoutTerminator = Arrays.copyOf(buf, buf.length - terminator.length); 
		
		
		return bufWithoutTerminator;
	}
	
	public String toString()
	{
		StringBuilder s = new StringBuilder();
		
		s.append("Position: " + buffer.position() + "; ");
		s.append("Remaining: " + buffer.remaining() + "; ");
		s.append("Capacity: " + buffer.capacity() + "; ");
		
		return s.toString();
	}
	
	public static ByteBufferWrapper makeCopy(byte[] bytes)
	{
		return new ByteBufferWrapper(bytes);
	}

	public byte[] getRemaining()
	{
		if(buffer.remaining() > 0)
		{
			byte[] b = new byte[buffer.remaining()];
			buffer.get(b);
			return b;
		}
		return null;
	}
	
	public boolean hasRemaining()
	{
		return buffer.hasRemaining();
	}
}
