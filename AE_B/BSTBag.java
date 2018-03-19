package AE_B;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 
 * @author Student ID: 2081415M
 * @author Student Name: Kelly McDonald
 *
 * @param <E>
 */
public class BSTBag<E extends Comparable<E>> implements Bag<E>
{
	private Node<E> root;

	/**
	 * Constructor.
	 */
	public BSTBag() 
	{
		root = null;
	}

	
	/**
	 * Class creating node
	 * @param <E>
	 */
	private static class Node <E extends Comparable<E>>
	{
		protected CountedElement<E> element;
		protected Node<E> left, right;

		/**
		 * Constructor
		 * @param elem
		 */
		protected Node(CountedElement<E> elem) 
		{
			element = elem;
			left = null;
			right = null;
		}
	}


	/**
	 * This method will return a boolean which will declare whether this BST is empty or not
	 */
	@Override
	public boolean isEmpty()
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

	/**
	 * This method will return the size of the BST, which is the number of nodes within the tree.
	 * I have assumed that in addition to the above, due to the manner of this tree that it will also include multiple counts of one node if 
	 * the element has a count higher than 1.
	 */
	@Override
	public int size() 
	{
		Node<E> curr = root;

		if(curr == null) 
		{
			return 0;
		}
		else if(curr.element.getCount() == 0) 
		{
			return (size(curr.left) + size(curr.right)); //will not count the parent node as it is product of 'soft-delete'
		}
		else 
		{
			return (size(curr.left) + curr.element.getCount() + size(curr.right)); //will count the parent node * by the count of this element
		}
	}

	/**
	 * This method is a recursive helper method for size(). This is responsible for assisting calculating the total size of the tree.
	 * @param node
	 * @return
	 */
	public int size(Node<E> node) 
	{
		if (node == null) 
		{
			return 0;
		}
		else if(node.element.getCount() == 0) 
		{
			return (size(node.left) + size(node.right));
		}
		else
		{
			return (size(node.left) + node.element.getCount() + size(node.right));
		}		
	}

	/**
	 * This method is responsible for identifying if the element passed in is already contained in the BST.
	 * If the element is already contained then the boolean value of true will be returned. If not, then false will be returned.
	 */
	@Override
	public boolean contains(E element) 
	{
		Node<E> curr = root;
		CountedElement<E> elem = new CountedElement<E>(element);
		int direction = 0;

		for(;;) 
		{
			if(curr == null) 
			{
				return false;
			}

			direction = elem.compareTo(curr.element);

			if(direction == 0) 
			{
				if(curr.element.getCount() == 0) //put in so if count is at 0 then will not count the element as present
				{
					return false;
				}
				else 
				{
					return true;
				}
			}
			else if(direction < 0) 
			{
				curr = curr.left;
			}
			else if(direction > 0) 
			{
				curr = curr.right;
			}
		}
	}

	/**
	 * This method is responsible for identifying if the Bag object which is passed in has the same contents as this current Bag.
	 * If the contents are the same then true will be returned; if not then false will be returned.
	 */
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

	/**
	 * This method will clear the current tree
	 */
	@Override
	public void clear() 
	{
		root = null;		
	}

	/**
	 * This method is responsible for adding a new generic element to the tree
	 */
	@Override
	public void add(E element) 
	{
		int direction = 0;
		Node<E> parent = null, curr = root;
		CountedElement<E> elem = new CountedElement<E>(element);

		for(;;) 
		{
			if(curr == null)
			{
				Node<E> ins = new Node<E>(elem);
				if(root == null)
				{
					root = ins; //ins element is the first element in the tree
				}
				else if(direction < 0)
				{
					parent.left = ins;
				}
				else 
				{
					parent.right = ins;
				}
				return;
			}
			
			direction = elem.compareTo(curr.element);

			if(direction == 0) 
			{
				//This bag already has this element so don't need to add full node. But will increase the count of this element to display there is
				//multiple copies of this element.
				curr.element.setCount(curr.element.getCount()+1);
				return;
			}
			
			parent = curr;
			
			if(direction < 0)
			{
				curr = curr.left;
			}
			else if(direction > 0)
			{
				curr = curr.right;
			}
		}
	}

	/**
	 * This method is responsible for removing a generic element from the tree.
	 * This will be a 'soft-delete' and as such the node will not be removed from the tree but rather the count of that element 
	 * will decrease by one.
	 */
	@Override
	public void remove(E element) 
	{
		int direction = 0;
		Node<E> parent = null, curr = root;
		CountedElement<E> elem = new CountedElement<E>(element);
		for(;;) 
		{
			if(curr == null) //there has not been a match in the tree and therefore cannot delete what is not there
			{
				return;
			}

			direction = elem.compareTo(curr.element);

			if(direction == 0) 
			{
				//The element has been found in the tree and rather than delete the whole node, instead will decrease the count of the element.
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

	/**
	 * This method will return an instance of an Iterator.
	 */
	@Override
	public Iterator<E> iterator() 
	{
		return new BagIterator();
	}

	
	/**
	 * Class creating iterator methods
	 */
	private class BagIterator implements Iterator<E>
	{
		private Stack<Node<E>> bag;

		/**
		 * Constructor
		 */
		private BagIterator() 
		{
			bag = new LinkedStack<Node<E>>();
			for(Node<E> curr = root; curr != null; curr = curr.left) 
			{
				Node<E> current = new Node<E>(new CountedElement<E>(curr.element.getElement(), curr.element.getCount()));
				bag.push(curr);
			}
//			Node<E> curr = root;
//			if(curr != null) 
//			{
//				for(int i = 0; i < curr.element.getCount(); i++) 
//				{
//					bag.push(curr); //add this node as many times as it has a count
//				}
//
//				addingToStack(curr); //calling recursive method
//			}
		}

		/**
		 * This is a recursive, helper method assisting in adding all tree elements to the Stack-bag
		 * @param curr
		 */
		public void addingToStack(Node<E> curr) 
		{
			if(curr.left != null) 
			{
				for(int i = 0; i < curr.left.element.getCount(); i++) 
				{
					bag.push(curr.left);
				}
				addingToStack(curr.left);
			}

			if(curr.right != null) 
			{
				for(int i = 0; i < curr.right.element.getCount(); i++) 
				{
					bag.push(curr.right);
				}
				addingToStack(curr.right);
			}
		}

		/**
		 * This method is responsible for identifying if there is another element left within the Stack.
		 */
		@Override
		public boolean hasNext() 
		{
			return (!bag.empty());
		}

		/**
		 * This method will return the generic element of the next Node in the Stack provided that it has a count higher than 0.
		 */
		@Override
		public E next() 
		{
			if(!hasNext()) 
			{
				throw new NoSuchElementException();
			}
			else 
			{
				Node<E> place = bag.peek();
				
				for(Node<E> curr = place.right; curr != null; curr = curr.left) 
				{
					Node<E> current = new Node<E>(new CountedElement<E>(curr.element.getElement(), curr.element.getCount()));
					bag.push(current);
//					while(place.element.getCount() == 0) 
//					{
//						Node<E> current = new Node<E>(new CountedElement<E>(curr.element.getElement(), curr.element.getCount()));
//						bag.push(current);
//						place = bag.pop();
//					}
				}
				if(place.element.getCount() == 0) {
					place = bag.pop();
				}
				
				if(place.element.getCount() == 1) 
				{
					place = bag.pop();
				}
				if(place.element.getCount() > 1) 
				{
					place.element.setCount(place.element.getCount()-1);
				}
				
//				if(place.element.getCount() == 0) 
//				{
//					place = bag.pop();
//					return null;
//				}
//				else if(place.element.getCount() == 1) 
//				{
//					place = bag.pop();
//				}
//				else if(place.element.getCount() > 1) 
//				{
//					place.element.setCount(place.element.getCount()-1);
//				}

//				while(place.element.getCount() == 0) 
//				{
//					place = bag.pop();
//				}

				return place.element.getElement();
			}
		}

	}
}
