package AE_B;

import java.util.Iterator;
import java.util.NoSuchElementException;

import javax.swing.text.Position;

import javafx.scene.Node;

public class BSTBag<E extends Comparable<E>> implements Bag<E>
{
	private Stack<Node<CountedElement<E>>> bag = new LinkedStack<Node<CountedElement<E>>>();
	private Node<CountedElement<E>> root;
	
	public BSTBag() 
	{
		root = null;
	}
	
	///Inner Class/////
	
	private static class Node <E extends Comparable<E>>
	{
		//your node can have elements of type countedelement
		protected E element; //think this needs to be countedelement?
		protected Node<E> left, right;
		
		protected Node(E elem) 
		{
			element = elem;
			left = null;
			right = null;
		}
	}
	
	
	
	/////////////Accessors//////////////

	
	@Override
	public boolean isEmpty() //need to check if the depth is -1
	{
		if(root == null) 
		{
			return true;
		}
		else 
		{
			return false;
		}
	}

	@Override
	public int size() 
	{
		Node<CountedElement<E>> parent = null, curr = root;
		int size = 0;
		
		if(curr == null) 
		{
			return 0;
		}
		else if(curr.element.getCount() == 0) 
		{
			return (size(curr.left) + size(curr.right)); //this means will not count the parent node
		}
		else 
		{
			return (size(curr.left) + curr.element.getCount() + size(curr.right)); //will count the parent node
		}
	}
	
	//Helper method
	public int size(Node<CountedElement<E>> node) 
	{
		if (node == null) 
		{
			return 0;
		}
		else if(node.element.getCount() == 0) 
		{
			return (size(node.left) + size(node.right)); //will not return the count of this node as 'soft-delete'
		}
		else
		{
			return (size(node.left) + node.element.getCount() + size(node.right));
		}		
	}

	@Override
	public boolean contains(E element) 
	{
		Node<CountedElement<E>> curr = root;
		CountedElement<E> element2 = new CountedElement<E>(element);
		int direction = 0;
				
		for(;;) 
		{
			if(curr == null) 
			{
				return false;
			}
			
			direction = element2.compareTo(curr.element); //don't think this is quite correct
			
			if(direction == 0) 
			{
				if(curr.element.getCount() == 0) //put in so if count is at 0 then wont
				{
					return false;
				}
				else 
				{
					return true;
				}
			}
			else if(direction < 0) //compare to 0? or curr.element? must be former or else why bother with the direction=element.compareTo...etc
			{
				curr = curr.left;
			}
			else if(direction > 0) 
			{
				curr = curr.right;
			}
		}
	}

	@Override
	public boolean equals(Bag that)
	{
		BSTBag<E> next = (BSTBag<E>) that;
		
		Iterator<E> current = this.iterator();
		Iterator<E> nextOne = next.iterator();
				
		while(current.hasNext() && nextOne.hasNext()) 
		{
			if(!current.next().equals(nextOne.next())) 
			{
				return false;
			}
		}
		return true;
	}

	
	
	////////////Transformers//////////
	
	@Override
	public void clear() 
	{
		root = null;		
	}

	@Override
	public void add(E element) 
	{
		int direction = 0;
		Node<CountedElement<E>> parent = null, curr = root;
		CountedElement<E> element2 = new CountedElement<E>(element);
		
		for(;;) 
		{
			if(curr == null) //if what current has been assigned to is null
			{
				Node<CountedElement<E>> ins = new Node<CountedElement<E>>(element2);
				if(root == null) //will not get past this until root has been set
				{
					root = ins; //if this is going to be the first node in the tree basically
				}
				else if(direction < 0) //direction will have been set because these will only loop through these 
				{						//statements once direction has been set further down
					parent.left = ins; //parent will not be null as already set in prior loop
				}
				else 
				{
					parent.right = ins;
				}
				return;
			}
			direction = element2.compareTo(curr.element);
			
			if(direction == 0) 
			{
				curr.element.setCount(curr.element.getCount()+1);
				return; //do nothing here as the element has already been added to the tree - but want to 
				//increase the number for this element?
			}
			parent = curr;
			if(direction < 0) //curr is equal to something and element is less than this
			{
				curr = curr.left;
			}
			else if(direction > 0) //curr is equal to something and element is greater than this
			{
				curr = curr.right;
			}
		}
	}

	@Override
	public void remove(E element) 
	{
		int direction = 0;
		Node<CountedElement<E>> parent = null, curr = root;
		CountedElement<E> element2 = new CountedElement<E>(element);
		for(;;) 
		{
			if(curr == null) //there has not been a match in the tree and therefore cannot delete it
			{
				return; //can't delete what's not there
			}
			
			direction = element2.compareTo(curr.element);
			
			if(direction == 0) 
			{
				curr.element.setCount(curr.element.getCount()-1);
				return;
			}
			parent = curr;
			if(direction < 0) 
			{
				curr = parent.left;
			}
			else if(direction > 0)
			{
				curr = parent.right;
			}
		}
	}

	public void addingToStack(Node<CountedElement<E>> test) 
	{
		if(test.left != null) 
		{
			for(int i = 0; i < test.left.element.getCount(); i++) 
			{
				bag.push(test.left);
			}
			addingToStack(test.left);
		}
		
		if(test.right != null) 
		{
			for(int i = 0; i < test.right.element.getCount(); i++) 
			{
				bag.push(test.right);
			}
			addingToStack(test.right);
		}
	}
	
	@Override
	public Iterator<E> iterator() 
	{
		Node<CountedElement<E>> curr = root;
		if(curr != null) 
		{
			for(int i = 0; i < curr.element.getCount(); i++) 
			{
				bag.push(curr);
			}
		}
		
		addingToStack(curr);
		
		return new Iterator<E>() {

			@Override
			public boolean hasNext() 
			{
				return (!bag.empty());
			}

			@Override
			public E next() 
			{
				if(!hasNext()) 
				{
					throw new NoSuchElementException();
				}
				else 
				{
					Node<CountedElement<E>> place = bag.pop();
					
					while(place.element.getCount() == 0) 
					{
						place = bag.pop();
					}
					
					return (E)place.element.getElement();
				}
			}
		};	
	}
}
