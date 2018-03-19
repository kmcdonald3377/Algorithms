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
		
		
		BSTBag<String> bag = new BSTBag<String>();
		bag.add("Cat");
		bag.add("Dog");
		bag.add("Rabbit");
		bag.add("Mouse");
		bag.add("Hamster");
		bag.add("Hamster");
		bag.add("Hamster");
		bag.add("Horse");
		bag.add("Rat");
		bag.add("Donkey");
		bag.remove("Mouse");
		bag.add("Cat");
		bag.add("Cat");
		bag.remove("Pokemon");

		System.out.println(bag.size()); //this has to be called first or else the iterator is going to remove the count back down to 1
		//need to make copy of everything rather than putting exact same object into iterator list
		//could possibly mean before push then will push x number of objects
		
		Iterator test = bag.iterator();
		String output = "";
		
		while(test.hasNext()) 
		{
			output += test.next() + ", ";
		}
		
		System.out.println(output);
		System.out.println(bag.size());
		
	}
}
