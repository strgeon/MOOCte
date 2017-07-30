package textgen;

import java.util.AbstractList;


/** A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		// TODO: Implement this method
		head = new LLNode<E>();
		tail = new LLNode<E>();
		head.prev = null;
		head.next = tail;
		tail.prev = head;
		tail.next = null;
		size = 0;
		//System.out.println("creating new list: head = "+head+", head.next = "+head.next+", tail = "+tail+", tail.prev = "+tail.prev);
		
	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	public boolean add(E element ) 
	{
		if(element == null) {
			//System.out.println("got a null");
			throw new NullPointerException();
		}
		// TODO: Implement this method
		LLNode<E> node = new LLNode<E>(element);
		node.prev = tail.prev;
		tail.prev = node;
		node.next = tail;
		(node.prev).next = node;
		++size;
		//System.out.println("adding node at: "+(size-1)+", data = "+node.data+", node.prev = "+node.prev+", node = "+node+", node.next = "+node.next+", tail.prev = "+tail.prev+", (node.prev).next = "+(node.prev).next );
		
		return true;
		//return false;
	}

	/** Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	public E get(int index) 
	{
		if (index < 0 || index >= size ) {
			throw new IndexOutOfBoundsException();
		}
			
		int i = 0;
		LLNode<E> node = head.next;
	
		while (node != tail && i != index ) {
			node = node.next;
			i++;
		}
		
		if (i==index && node.data != null) {
			//System.out.println("getting node "+i+", data = "+node.data);
			return node.data;
		}
		else { 
			//throw new IndexOutOfBoundsException();
			return null; 
		}
	}

	/**
	 * Add an element to the list at the specified index
	 * @param The index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element ) 
	{
		if (index < 0 || index > size ) {
			throw new IndexOutOfBoundsException();
		}
				
		//System.out.println("["+element+"]");
		if(element == null) {
			//System.out.println("got a null");
			throw new NullPointerException();
		}
		// TODO: Implement this method
		int i = 0;
		LLNode<E> newnode = new LLNode<E>(element);
		LLNode<E> node = head.next;
		
		while (node != tail && i != index ) {
			node = node.next;
			i++;
		}
		
		//if (i==index && node.data != null) {
		if (i==index) {
			//System.out.println("inserting node at "+i+", data = "+node.data);
			newnode.prev = node.prev;
			node.prev = newnode;
			newnode.next = node;
			(newnode.prev).next = newnode;
			newnode.data = element;
			++size;
		}
				
	}


	/** Return the size of the list */
	public int size() 
	{
		// TODO: Implement this method
		return size;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) 
	{
		// TODO: Implement this method
		int i = 0;
		LLNode<E> node = head.next;
		
		while (node != tail && i != index ) {
			node = node.next;
			i++;
		}
		//System.out.println("i = "+i+", index = "+index+" data = "+node.data);
		if (i==index && node.data != null) {
			//System.out.println("removing node "+i+", data = "+node.data);
			(node.prev).next = node.next;
			(node.next).prev = node.prev;
			--size;
			return node.data;
		}
		else {
			throw new IndexOutOfBoundsException();
			
		}
	}

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element) 
	{
		// TODO: Implement this method
		if(element == null) {
			//System.out.println("got a null");
			throw new NullPointerException();
		}
		int i = 0;
		LLNode<E> node = head.next;
		
		while (node != tail && i != index ) {
			node = node.next;
			i++;
		}
		
		if (i==index && node.data != null) {
			//System.out.println("setting node "+i+", data = "+node.data+" "+element);
			node.data=element;
			return node.data;
		}
		else {
			throw new IndexOutOfBoundsException();
			
		}

	}   
}


class LLNode<E> 
{
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	// TODO: Add any other methods you think are useful here
	// E.g. you might want to add another constructor

	//for creating sentinel nodes
	public LLNode()
	{
		this.data = null;
		this.prev = null;
		this.next = null;
	}
	public LLNode(E e) 
	{
		this.data = e;
		this.prev = null;
		this.next = null;
	}

}
