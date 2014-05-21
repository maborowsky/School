/**
 * A priority queue implementation
 * 
 * @author Michael Borowsky
 * @date 10/29/13
 *
 */
public class PriorityQueue<T extends Comparable<T>> implements PQInterface<T>
{
	private Node header;   // Pointer to the first item in the Queue
	
	
	public PriorityQueue()
	{
		Node front = new Node();   // Construct the sentinel
		Node rear = new Node();    // nodes.
		
		front.prev = null;   // Link the sentinels.  All queue
		front.next = rear;   // items will be between the sentinels
		rear.prev  = front;  // so each items is assured to have 
		rear.next  = null;   // a node in front and in back.
		
		header = front;  // The header points to the front node.
	}
	
	@Override
	public void enqueue(T item) {
		Node tmp = header.next; // A temp node, set to the item after the front
		
		// Traverse the list until we reach the end or a node with the same or
		// greater priority
		while (tmp.next != null && item.compareTo(tmp.item) <= 0)
			tmp = tmp.next;

		
		// Insert the new node
		Node newNode = new Node();	// Make a new node
		newNode.item = item;		// Set the value of the new node
		newNode.prev = tmp.prev;	// newNode points to the node behind tmp
		newNode.next = tmp; 		// newNode points to the node we stopped at
		
		tmp.prev.next = newNode;// Set the previous node to point to the new one
		tmp.prev = newNode;// Set tmp to point to the new one
	}

	@Override
	public T dequeue() {
		Node toDequeue = header.next; // The node to dequeue
		header.next = toDequeue.next; // Remove the node from the queue
		return toDequeue.item; // Return its item
	}

	@Override
	public T front() {
		return header.next != null ? header.next.item : null;
	}

	@Override
	public boolean isEmpty() {
		if (header.next.next == null)
			return true;
		else
			return false;
	}

	@Override
	public boolean isFull() {
		return false;
	}
	
	@Override
	public String toString()
	{
		String s = "";
		
		Node ptr = header.next;
		
		while (ptr.next != null) {   // For each item ...
			s += ptr.item.toString() + "\n";
			ptr = ptr.next;
		}
		
		return s;
	}

	
	/*
	 *    Inner Class - Node objects for Map items
	 *                  in a doubly linked list.
	 *    
	 */

	private class Node
	{
		public T item;		// The node's item.
		public Node prev;   // Pointer to the previous Node.
		public Node next;   // Pointer to the next node.
	}
}
