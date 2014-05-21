/*
 * Michael Borowsky and MariAnne Skolnik
 * 11/18/13
 * 
 *    A program to decode a file using Huffman Code Algorithm.
 */

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class HDecode {
	
	private Node root = null;      // Root of the Huffman Code Tree.
	
	private String inputFilename;
	private int fileSize;          // The number of bytes 
	                               // in the original file.

	private BitReader bitr;
	private FileOutputStream outF = null;
	
	public static void main(String[] args)
			throws FileNotFoundException, IOException
	{
		HDecode decoder = new HDecode(args[0]);  // Construct a Huffman Decoder
		decoder.decode();
	}
	
	public HDecode(String inputFilename)
	{
		this.inputFilename = inputFilename;
		
	}
	
	
	public void decode() throws IOException
	{
		Node tmp; // A temporary node
		
		// Remove ".huf"
		String outputName = inputFilename.substring(0,inputFilename.length()-4);
		
		bitr = new BitReader(inputFilename); // Set up the bit reader
		outF = new FileOutputStream(outputName + ".orig"); // output stream
		
		int fsize = this.bitr.readInt();  // Read the file size
		
		// Now read the tree
		root = readTree();
		rPrintTree(root, 5);
		
		for (int i=0; i<fsize; i++)
		{
			tmp = root; // Set tmp to the beginning of the tree
			boolean leafFound = false; // If a leaf has been reached yet
			
			while (!leafFound) // While we haven't reached a leaf
			{
				if (tmp.lchild == null && tmp.rchild == null) //If tmp is a leaf
					leafFound = true;
				else {
					int bit = bitr.readBit(); // Read the next bit
					
					if (bit == 0)         // If the bit is a 0
						tmp = tmp.lchild; // traverse the left subtree
					else                  // If the bit is a 1
						tmp = tmp.rchild; // traverse the right subtree
				}
			}
			
			
			
			byte b = tmp.data;
//			System.out.println((char)b);
			outF.write(b);
			
			
			//tmp is now pointing to a leaf node
//			outF.write(tmp.data); // Write the decoded letter
		}
		
		// Close both files
		bitr.close();
		outF.close();
	}
	
	
	public Node readTree()
	{
		int b = bitr.readBit();
		Node n = new Node();
		
		if (b==0)
		{
			n.data = bitr.readByte();
			n.lchild = null;
			n.rchild = null;
			return n;
		}
		else
		{
			Node left  = readTree();
			Node right = readTree();
			n.lchild = left;
			n.rchild = right;
			left.parent = n;
			right.parent = n;
			
			return n;
		}
	}
		
	
	
	/*
	 * REMOVE BEFORE A
	 */

	public void rPrintTree(Node r, int level)
	{
		
		if (r == null)          // Empty tree.
			return;
		
		rPrintTree(r.rchild, level + 1);   // Print the right subtree.
		
		for (int i = 0; i < level; i++)
			System.out.print("         ");
		
		if (r.data > (byte) 31)
			System.out.printf("%c-%d\n", (char) r.data, r.frequency);
		else
			System.out.printf("%c-%d\n", '*', r.frequency);
		
		rPrintTree(r.lchild, level + 1);
	}

			
	
	
	

	
	
	public class Node implements Comparable<Node>
	{
		byte data;           // A byte of data - stored in an Integer.
		Node lchild;         // Left child pointer.
		Node rchild;         // Right child pointer.
		Node parent;         // Pointer to parent node.
		Integer frequency;   // Frequency the data within
		                     // a file being encoded.
		/*
		 *   Basic node constructor.
		 */
		
		public Node()
		{
			data = 0;          // Each Huffman Code Tree node
			lchild = null;     // contains data, pointers to
			rchild = null;     // children and parent nodes
			parent = null;     // plus a frequency count
			frequency = 0;     // associated with the data.
		}
		
		
		/*
		 *   Constructor specifying all values
		 *   of the node instance variables.
		 */
		
		public Node(byte data, Node lchild, Node rchild,
				               Node parent, int frequency)
		{
			this.data = data;
			this.lchild = lchild;
			this.rchild = rchild;
			this.parent = parent;
			this.frequency = frequency;
		}
		
		
		/*
		 *    compareTo() - Compare two frequency values.  We want Nodes
		 *                  with lower frequencies to have higher priority
		 *                  in the priority queue.
		 *                  
		 */
		
		@Override
		public int compareTo(Node other)
		{
			if (this.frequency.compareTo(other.frequency) < 0)
				return 1;
			else if (this.frequency.compareTo(other.frequency) > 0)
				return -1;
			else
				return 0;
		}
		
		@Override
		public String toString()
		{
			char ch = (char) this.data;
			
			String str = "byte: " + data + "  char: ";
			
			if (data > (byte) 31)
				str = str + (char) data + "  freq: " + frequency;
			else
				str = str + " " + "  freq: " + frequency;
			
			return str;
		}
		
	}
}
