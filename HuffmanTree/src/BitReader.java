/*
 *    class BitReader
 *    
 *    Author:  George Benjamin
 *    Date:    mm/dd/yyyy
 *    
 *    This class reads bits from a file.
 *    The bits are unpacked from bytes.
 */

import java.io.*;

public class BitReader 
{
	private int count = 8;      // The number of bits collected into a byte.
	private int curByte = 0;    // The current byte being assembled.
	private String filename;    // The name of the file to read from.
	FileInputStream inF = null; // Input file object.
	
	/*
	 *    Constructor for a bit reader.
	 *    Just open the file.
	 */
	
	public BitReader(String filename)
	{
		try {
			this.filename = filename;
		    inF = new FileInputStream(filename);
		}
		catch (FileNotFoundException e) {
			System.out.printf("Error opening file %s\n", filename);
			System.exit(0);
		}
	}
	
	/*
	 *    readBit() - return the value of the next bit (0 or 1)
	 *                from the input file.  The bits are unpacked
	 *                from the current Byte that was last read.
	 *                A new Byte is 
	 */
	
	public int readBit()
	{
		try {
			if (count == 8) {           // If we've read 8 bits from
				curByte = inF.read();   // the current Byte, it's time
				if (curByte == -1)      // to get a new byte.
					return -1;          //    ... check for EOF.
				count = 0;              //    ... restart bit count.
			}
			           // Shift the bit to the rightmost position.
			           // Mask off everything but this one bit.
			count++;
			return  (curByte >> 8 - count) & 0x0001; 
		}
		catch (IOException e) {
			System.out.printf("Error reading byte file: %s\n",filename);
			System.exit(0);
		}
		
		return 0;
	}
	
	
	/*
	 *   readByte() - collect the next 8-bits into a byte
	 *                and return as the value of the function.
	 */
	
	public byte readByte()
	{
		int b = 0;
		
		for (int i = 0; i < 8; i++) {
			b = (b << 1) | (byte) readBit();
		}
		return (byte) b;
		
	}
		
	/*
	 *   readInt() - collect the next 32-bits into an int
	 *               and return as the value of the function.
	 */
	
	public int readInt()
	{
		int number = 0;
		
		for (int i = 0; i < 32; i++) {
			number = (number << 1) | readBit();
		}
		return number;
		
	}
	
	
	public void close()
	{
		try {
			inF.close();
		} 
		catch (IOException e) {
			System.out.printf("Error closing file: %s\n",filename);
			System.exit(0);
		}
	}
}
