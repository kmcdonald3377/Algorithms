package AE_A;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;


/**
 * 
 * @author Student ID: 2081415M
 * @author Student Name: Kelly McDonald
 *
 */
public class WordProcessor
{
	/**
	 * This method will return the string which is to be displayed to the console
	 * @param inputSet
	 * @return
	 */
	private static <E> String displaySet(Set<E> inputSet)
	{
		String output = "";
		int count = 0;

		for(E e : inputSet)
		{
			if (count==5) 
			{
				count = 0;
				output += "\n";
			}
			output += e.toString() + ", ";
			count++;
		}
		
		return output;
	}

	/**
	 * Main method
	 * @param args
	 */
	public static void main(String[] args)
	{
		FileReader[] arguments = new FileReader[args.length];

		try
		{
			for(int i = 0; i < args.length; i++)
			{
				arguments[i] = new FileReader(args[i]); //will create a FileReader for every file passed into the main method
			}
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}

		Set<String> wordSet = new HashSet<String>();
		Set <CountedElement<String>> countedWordSet = new HashSet<CountedElement<String>>();

		for(int i = 0; i < arguments.length; i++)
		{
			Scanner scanner = new Scanner(arguments[i]);

			while(scanner.hasNext())
			{
				String word = scanner.next();

				if(!wordSet.contains(word) || wordSet.isEmpty())
				{
					wordSet.add(word);
					countedWordSet.add(new CountedElement<String>(word, 1)); //will always be 1 here as this is the first instance of this word
				}
				else
				{
					for(CountedElement<String> element : countedWordSet)
					{
						if(element.getElement().equals(word)) 
						{
							element.setCount(element.getCount()+1);
						}
					}
				}
			}

			scanner.close();
		}

		System.out.println(displaySet(countedWordSet));
	}
}
