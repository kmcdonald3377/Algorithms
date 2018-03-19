package AE_A;

/**
 * 
 * @author Student ID: 2081415M
 * @author Student Name: Kelly McDonald
 *
 * @param <E>
 */
public class CountedElement<E extends Comparable<E>> implements Comparable<CountedElement<E>> 
{
	private E element;
	private int count;

	/**
	 * Constructor
	 * @param e is the generic E
	 * @param count is the integer
	 */
	public CountedElement(E e, int count)
	{
		element = e;
		this.count = count;
	}
	
	/**
	 * Second constructor which only takes in the generic
	 * @param e is the generic
	 */
	public CountedElement(E e)
	{
		element = e;
		count = 1;
	}
	
	/**
	 * Will return the generic element
	 * @return
	 */
	public E getElement() 
	{
		return element;
	}
	
	/**
	 * Will set the generic element
	 * @param e
	 */
	public void setElement(E e) 
	{
		element = e;
	}
	
	/**
	 * Will return the count of this element
	 * @return
	 */
	public int getCount() 
	{
		return count;
	}
	
	/**
	 * Will set the count of this element
	 * @param count
	 */
	public void setCount(int count) 
	{
		if(this.count > 0) //if is at 0 then don't want to continue decreasing the value
		{
			this.count = count;
		}
	}
	
	/**
	 * Will return a string composed of the element printed to the screen and the count of the element
	 */
	public String toString()
	{
		String a = "(" + element + "," + count + ")";
		return a;
	}
	
	/**
	 * This method will compare this element against the element passed in.
	 * It will return an integer representing if the elements are the same,
	 * lesser or greater.
	 */
	public int compareTo(CountedElement<E> sC1) 
	{
		int result = this.getElement().compareTo(sC1.getElement());		
		return result;
	}
}
