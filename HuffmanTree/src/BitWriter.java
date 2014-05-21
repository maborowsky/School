/*
 *    class BitWriter
 *    
 *    Author:  George Benjamin
 *    Date:    mm/dd/yyyy
 *    
 *    This class writes a string of bits to a file.
 *    The bits are packaged into bytes.
 */

import java.io.*;

public class BitWriter 
{
	private int count = 0;        // The number of bits collected into a byte.
	private int curByte = 0;      // The current byte being assembled.
	private String filename;      // Name of the output file.
	FileOutputStream outF = null; // Output file object.
	
	
	/*
	 *   The constructor takes the name of the
	 *   output file as an argument.
	 */
	
	public BitWriter(String filename)
	{
		try {
			this.filename = filename;
		    outF = new FileOutputStream(filename);
		}
		catch (FileNotFoundException e) {
			System.out.printf("Error opening file %s\n", filename);
			System.exit(0);
		}
	}
	
	
	/*
	 *    writeBit() - Write a bit to the outputfile.  The
	 *                 bits are buffered in a byte until
	 *                 8 bits have been written.
	 */
	
	public void writeBit(int bit)
	{
		try {
		    curByte = (curByte << 1) | bit;
		    count++;
		    if (count == 8) {
			    outF.write( (byte) curByte);
			    count = 0;
		    }
		}
		catch (IOException e) {
			System.out.printf("Error writing byte to file: %s\n",filename);
			System.exit(0);
		}
	}

	/*
	 *   writeInt() - Write a 32-but integer to the output file bit by bit.
	 *                This will allow (but not require) that the integer
	 *                be stored across byte boundaries.
	 */
	
	
	public void writeInt(int data)
	{
		for (int i = 0; i < 32; i++)
			writeBit( (data >> (31 - i)) & 0x00000001 );
	}

	/*
	 *  writeByte() -  Write the byte to the output file bit by bit.
	 *                 This will allow (but not require) that the byte
	 *                 be stored across byte boundaries.
	 */
	
	public void writeByte(int data)
	{
		for (int i = 0; i < 8; i++)
			writeBit( (data >> (7 - i)) & 0x0001 );
	}
	
	/*
	 *    close() - Close the outputfile.  Before closing
	 *              the file, pad the final byte with
	 *              zero bits and write this byte to the file.
	 */
	
	public void close()
	{
		try {
			if (count != 0) {
		        while (count != 8) {
			        curByte = (curByte << 1) | 0;
			        count++;
		        }
				outF.write( (byte) curByte);
			}
			outF.close();
		} 
		catch (IOException e) {
			System.out.printf("Error closing file: %s\n",filename);
			System.exit(0);
		}
	}
}
