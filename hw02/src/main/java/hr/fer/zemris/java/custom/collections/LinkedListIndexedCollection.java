package hr.fer.zemris.java.custom.collections;

import java.util.Objects;

/**
 * 
 * The class is a Collection that represents a
 * double linked list.
 * It contains all the methods from the Collection class
 * and has some own methods.
 * The variables that the class holds are the size of the
 * collection , the first added Node of the list and the
 * last added node of the list
 * It has a private nested class which represents the nodes
 * 
 * @author Tomislav Kurtović
 *
 */

public class LinkedListIndexedCollection extends Collection{

	
	/**
	 * Class that represents the node of the list
	 *
	 */
	private static class ListNode {
		
		/**
		 * Represents previous node of this list node
		 */
		ListNode previous;
		
		/**
		 * Represents previous node of this list node
		 */
		ListNode next;
		
		/**
		 * Represents value of this node 
		 */
		Object value;
		
		/**
		 * 
		 * Constructor of the list node
		 * @param previous
		 * @param next
		 * @param value
		 */
		public ListNode(ListNode previous,ListNode next,Object value) {
			this.previous = previous;
			this.next = next;
			this.value = Objects.requireNonNull(value);
		}
	}
	
	/**
	 * Size of this List
	 */
	private int size;
	
	/**
	 * Represents first list node of this list 
	 */
	private ListNode first;
	
	/**
	 * Represents last node of this list 
	 */
	private ListNode last;
	
	/**
	 * Default constructor for this Collection
	 * It sets the first and last node to null and size to 0
	 */
	public LinkedListIndexedCollection() {
		first = last = null;
		size = 0;
	}
	
	/**
	 * Constructor that copies the elements from the other Collection
	 * and constructs a new list
	 * @param other
	 */
	public LinkedListIndexedCollection(Collection other) {
		this();
		this.addAll(other);
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public int size() {
		return this.size;
	}

	/**
	 * Adds new nodes to the end of the list.
	 * If the size of the list is 0 then 
	 * the first and last node are the same.
	 * If value is null then an exception is thrown when 
	 * constructing new list node.
	 * @param value value to add
	 * @throws NullPointerException
	 */
	@Override
	public void add(Object value) {
		ListNode addedNode = new ListNode(last,null,value);
		if(size == 0) {
			first = addedNode;
		}
		else {
			last.next = addedNode;
		}
		last = addedNode;
		size++;
	}
	
	/**
	 * Cheks if the list contains the specified value.
	 * It goes through all nodes and returns either true or false
	 * @param value value to search for
	 * @return true if value is in the list,else false
	 */

	@Override
	public boolean contains(Object value) {
		if(value!=null && size > 0) {
			ListNode helpingNode = first;
			while(helpingNode != null) {
				if(helpingNode.value.equals(value)) {
					return true;
				}
				helpingNode = helpingNode.next;
			}
		}
		return false;
	}

	@Override
	public boolean remove(Object value) {
		if(value!=null && size > 0) {
			ListNode helpingNode = first;
			int i=0;
			while(helpingNode != null) {
				if(helpingNode.value.equals(value)) {
					remove(i);
					return true;
				}
				helpingNode = helpingNode.next;
				i++;
			}
		}
		return false;
	}
	

	@Override
	public Object[] toArray() {
		if(this.size == 0) {
			return null;
		}
		Object[] newArray = new Object[this.size];
		ListNode helpingNode = first;
		for(int i=0;i<this.size;i++) {
			newArray[i] = helpingNode.value;
			helpingNode = helpingNode.next;
		}
		return newArray;
	}
	
	@Override
	public void forEach(Processor processor) {
		ListNode helpingNode = first;
		while(helpingNode != null) {
			processor.process(helpingNode.value);
			helpingNode = helpingNode.next;
		}
	}

	@Override
	public void addAll(Collection other) {
		super.addAll(other);
	}

	@Override
	public void clear() {
		first.next = null;
		last.previous = null;
		first = last = null;
		size = 0;
	}
	
	/**
	 * Gets the object from the specified index.
	 * It searches for the object by first checking 
	 * if the object if the index is less than half the 
	 * size and then searches from there either towards the middle node
	 * or towards the last node
	 * @param index index of object to retrieve
	 * @throws IndexOutOfBoundsException
	 * @return Object at specified index
	 */
	public Object get(int index) {
		if(index < 0 || index > this.size-1) {
			throw new IndexOutOfBoundsException("Index is not valid"); 
		}
		ListNode searchNode;
		int i;
		if(index <= size/2) {
			searchNode = first;
			i=0;
			while(i != index ) {
				searchNode = searchNode.next;
				i++;
			}
		} else {
			searchNode = last;
			i=size-1;
			while(i!=index) {
				searchNode = searchNode.previous;
				i--;
			}
		}
		return searchNode.value;
	}
		
	/**
	 * Inserts the value at the specified position.
	 * If the size is 0 the value is just added to the list and 
	 * if not then it iterates through the list to the position
	 * and connects the nodes appropriately by constructing a new node
	 * @param value
	 * @param position
	 */
	public void insert(Object value, int position) {
		if(position < 0 || position > this.size) {
			throw new IndexOutOfBoundsException("Index is not valid"); 
		}
		if(size==0) {
			add(value);
			return;
		}
		int i=0;
		ListNode helpingNode = first;
		while(i < position-1) {
			i++;
			helpingNode = helpingNode.next;
		}
		if(helpingNode == first) {
			helpingNode = new ListNode(null,first,value);
			first.previous = helpingNode;
			first = helpingNode;
		} else if (helpingNode == last) {
			helpingNode = new ListNode(last,null,value);
			last.next = helpingNode;
			last = helpingNode;
		} else {
			ListNode insertedNode = new ListNode(helpingNode,helpingNode.next,value);
			helpingNode.next.previous = insertedNode;
			helpingNode.next = insertedNode;
		}
		size++;
	}
	
	/**
	 * Retrieves the index of the specified object if
	 * it exists and if it is not null
	 * @param value object to retrieve
	 * @return index of retrieved object if found, else -1
	 */
	public int indexOf(Object value) {
		if(value!= null && first !=null ) {
			ListNode helpingNode = first;
			int i=0;
			while(helpingNode != null) {
				if(helpingNode.value.equals(value)) return i;
				i++;
				helpingNode = helpingNode.next;
			}
		}
		return -1;
	}
	

	/**
	 * Removes the object at the specified index 
	 * @param index
	 */
	public void remove(int index) {
		if(index<0 || index>size-1) {
			throw new IndexOutOfBoundsException("Index is not valid");
		}
		if(first == null) {
			return;
		}
		if(index == 0) {
			first = first.next;
		}
		else {
			int i=0;
			ListNode helpingNode = first;
			while(i < index-1) {
				helpingNode = helpingNode.next;
				i++;
			}
			if(helpingNode.next == last) {
				helpingNode.next = null;
				last = helpingNode;
			} else {
			helpingNode.next.next.previous = helpingNode;
			helpingNode.next = helpingNode.next.next;
			}
		}
		size--;
	}
}
