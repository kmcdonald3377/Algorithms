package AE_B;

import java.util.Iterator;

import javafx.scene.Node;

public class BSTBag<E extends Comparable<E>> implements Bag
{ //CountedElement<E>
	
	///Inner Class/////
	private static class Node <E extends Comparable<E>>
	{
		protected E element;
		protected Node<E> left, right;
		
		protected Node(E elem) 
		{
			element = elem;
			left = null; right = null;
		}
	}
	
	private Node<E> root;
	
	public BSTBag() 
	{
		root = null;
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
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean contains(Object element) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean equals(Bag that) {
		// TODO Auto-generated method stub
		return false;
	}

	
	
	
	
	////////////Transformers//////////
	
	
	
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add(Object element) 
	{
		int direction = 0;
		Node parent = null, curr = root;
		for(;;) {
			if(curr == null) 
			{
				Node<E> ins = new Node<E>(element);
				if(root == null) 
				{
					root = ins;
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
			direction = element.compareTo(curr.element);
			if(direction == 0) {
				return;
			}
			parent = curr;
			if(direction < 0) {
				curr = curr.left;
			}
			else {
				curr = curr.right;
			}
		}		
	}

	@Override
	public void remove(Object element) 
	{
		int direction = 0;
		Node<E> parent = null, curr = root;
		for(;;) 
		{
			if(curr == null) 
			{
				return;
			}
			direction = element.compareTo(curr.element);
			if(direction == 0) 
			{
				Node<E> del = curr.deleteTopmost();
				if(curr == root) 
				{
					root = del;
				}
				else if(curr == parent.left) 
				{
					parent.left = del;
				}
				else 
				{
					parent.right = del;
				}
				return;
			}
			parent = curr;
			if(direction<0) 
			{
				curr = parent.left;
			}
			else 
			{
				curr = parent.right;
			}
		}
	}

	@Override
	public Iterator iterator() {
		// TODO Auto-generated method stub
		return null;
	}

}
