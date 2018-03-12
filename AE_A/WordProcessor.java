package AE_A;



import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class WordProcessor
{
	private static <E> String displaySet(Set<E> inputSet)
	{
		String sb = "";
		int count = 0;

		for(E e : inputSet)
		{
			if (count==5) 
			{
				count = 0;
				sb += "\n";
			}
			sb += e.toString() + ", ";
			count++;
		}

		return sb;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		FileReader[] arguments = new FileReader[3];

		try
		{
			for(int i = 0; i < args.length; i++)
			{
				arguments[i] = new FileReader(args[i]);
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
				String w = scanner.next();

				if(!wordSet.contains(w) || wordSet.isEmpty())
				{
					wordSet.add(w);
					countedWordSet.add(new CountedElement<String>(w, 1)); //should always be 1 here as this is the first instance of this word
				}
				else
				{
					for(CountedElement<String> t : countedWordSet)
					{
						if(t.getElement().equals(w)) {
							t.setCount(t.getCount()+1);
						}
					}
				}
			}

			scanner.close();
		}

		System.out.println(displaySet(countedWordSet));

	}
}
