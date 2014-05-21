/*
 *   An interface for a Priority Queue.
 *   
 *   Each item has an associated priority that
 *   determines its location in the queue at
 *   the time of entry.
 *   
 *   When an item enters a Priority Queue at the
 *   rear it advances ahead of strictly lower
 *   priority items.  It does not advance
 *   ahead of items of equal or greater priority.
 */

public interface PQInterface<T extends Comparable<T>>
{
    public void enqueue(T item);  // Add to the rear of the queue.
    public T dequeue();           // Delete and return the item at the front
                                  // of the queue. If empty queue return null.
                                  //
    public T front();             // Return the item at the front of the queue.
                                  // If empty queue return null.
    
    public boolean isEmpty();     // Check if the queue is empty.
    public boolean isFull();      // Check if the queue is full.
    public String toString();     // Return a printable display 
                                  // of the items in the queue.
}
