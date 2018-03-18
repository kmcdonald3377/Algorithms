package AE_B;



import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class WordProcessor2
{
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		
		
		BSTBag bag = new BSTBag();
		bag.add(new CountedElement<>("Cat"));
		bag.add(new CountedElement<>("Dog"));
		bag.add(new CountedElement<>("Rabbit"));
		bag.add(new CountedElement<>("Mouse"));
		bag.add(new CountedElement<>("Hamster"));
		bag.add(new CountedElement<>("Horse"));
		bag.add(new CountedElement<>("Rat"));
		bag.add(new CountedElement<>("Donkey"));
		bag.remove(new CountedElement<>("Mouse"));
		
		Iterator test = bag.iterator();
		String output = "";
		
		while(test.hasNext()) 
		{
			output += test.next();
		}
		
		System.out.println(output);
		
		
	}
}
