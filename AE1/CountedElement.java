package AE1;



public class CountedElement<E extends Comparable<E>> implements Comparable<CountedElement<E>> 
{
	private E element;
	private int count;

	public CountedElement(E e, int count)
	{
		element = e;
		this.count = count;
	}
	
	public CountedElement(E e)
	{
		element = e;
	}
	
	public E getElement() 
	{
		return element;
	}
	
	public void setElement(E e) 
	{
		element = e;
	}
	
	public int getCount() 
	{
		return count;
	}
	
	public void setCount(int count) 
	{
		this.count = count;
	}
	
	public String toString() //this to string method will print both the element and the count of that element
	{
		String a = "(" + element + "," + count + ")"; //not sure if correct what they want
		return a;
	}
		
	public int compareTo(CountedElement<E> sC1) 
	{
		if(this == sC1) 
		{
			return 1;
		}
		else 
		{
			return 0;
		}
	}

}
