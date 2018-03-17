package AE_B;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

import javax.swing.text.Position;

import javafx.scene.Node;

public class BSTBag<E extends Comparable<E>> implements Bag<CountedElement<E>>
{
	
	private Node<CountedElement<E>> root;
	
	public BSTBag() 
	{
		root = null;
	}
	
	///Inner Class/////
	
	
	
	private static class Node <E extends Comparable<E>>
	{
		protected E element;
		protected Node<E> left, right;
		
		protected Node(E elem) 
		{
			element = elem;
			left = null;
			right = null;
		}

		public Node<E> deleteTopmost() 
		{
			if(this.left == null) 
			{
				return this.right;
			}
			else if(this.right == null) 
			{
				return this.left;
			}
			else 
			{
				this.element = this.right.getLeftmost();
				this.right = this.right.deleteLeftmost();
				return this;
			}
		}

		private Node<E> deleteLeftmost() 
		{
			if(this.left == null) 
			{
				return this.right;
			}
			else 
			{
				Node<E> parent = this, curr = this.left;
				while(curr.left != null) 
				{
					parent = curr;
					curr = curr.left;
				}
				parent.left = curr.right;
				return this;
			}
		}

		private E getLeftmost() 
		{
			Node<E> curr = this;
			while(curr.left != null) 
			{
				curr = curr.left;
			}
			return curr.element;
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
		
		if(root == null) 
		{
			return 0;
		}
		else if(root.element.getCount() == 0) 
		{
			return (size(curr.left) + size(curr.right)); //this means will not count the parent node
		}
		else 
		{
			return (size(curr.left) + 1 + size(curr.right)); //will count the parent node
		}
	}
	
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
			return (size(node.left) + 1 + size(node.right));
		}		
	}

	@Override
	public boolean contains(CountedElement<E> element) {
		Node<CountedElement<E>> curr = root;
		int direction = 0;
				
		for(;;) 
		{
			if(curr == null) 
			{
				return false;
			}
			
			direction = element.compareTo(curr.element); //don't think this is quite correct
			
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
		if(this == that) 
		{
			return true;
		}
		else 
		{
			return false;
		}
	}

	
	
	////////////Transformers//////////
	
	
	
	@Override
	public void clear() 
	{
		root = null;		
	}

	@Override
	public void add(CountedElement<E> element) 
	{
		int direction = 0;
		Node<CountedElement<E>> parent = null, curr = root;
		
		for(;;) 
		{
			if(curr == null) //if what current has been assigned to is null
			{
				Node<CountedElement<E>> ins = new Node<CountedElement<E>>(element);
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
			direction = element.compareTo(curr.element);
			
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
	public void remove(CountedElement<E> element) 
	{
		int direction = 0;
		Node<CountedElement<E>> parent = null, curr = root;
		for(;;) 
		{
			if(curr == null) //there has not been a match in the tree and therefore cannot delete it
			{
				return; //can't delete what's not there
			}
			
			direction = element.compareTo(curr.element);
			
			if(direction == 0) 
			{
				curr.element.setCount(curr.element.getCount()-1);
				
				//the following would only be applicable if a hard delete was intended to be undertaken. This has been left in as unsure
				//whether this was also wanted.
//				if(curr.element.getCount() > 1) 
//				{
//					curr.element.setCount(curr.element.getCount()-1);
//				}
//				else 
//				{
//					Node<CountedElement<E>> del = curr.deleteTopmost(); //need to do this still
//					if(curr == root) //deciding which will take removed nodes place
//					{
//						root = del;
//					}
//					else if(curr == parent.left)
//					{
//						parent.left = del;
//					}
//					else 
//					{
//						parent.right = del;
//					}
//				}
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

	@Override
	public Iterator<CountedElement<E>> iterator() 
	{
		final Stack<Node<CountedElement<E>>> bag = new LinkedStack<Node<CountedElement<E>>>();
		Node<CountedElement<E>> curr = root;
		while(curr != null) 
		{
			bag.push(curr);
			curr = curr.left;
		}
		return new Iterator<CountedElement<E>>() {

			@Override
			public boolean hasNext() 
			{
				return (!bag.empty());
			}

			@Override
			public CountedElement<E> next() 
			{
				if(!hasNext()) 
				{
					throw new NoSuchElementException();
				}
				Node<CountedElement<E>> place = bag.pop();
				Node<CountedElement<E>> curr = place.right;
				while(curr != null) 
				{
					bag.push(curr);
					curr = curr.left;
				}
				return place.element;
			}
			
		};
		
	}
	
//	private class Iterator
//	{
//		private Stack<Node<CountedElement<E>>> track;
//		
//		private Iterator() 
//		{
//			track = new LinkedStack<Node<CountedElement<E>>>();
////			for(Node<CountedElement<E>> curr = root) 
////			{
////				
////			}
//		}
//		
//		public boolean hasNext() {
//			track.push(root);
//		}
//	}

}
