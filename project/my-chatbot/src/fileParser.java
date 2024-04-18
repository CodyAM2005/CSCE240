import java.util.Scanner;
import java.io.File;

public class fileParser
{
	public static int lineCount = 0;
	public static int wordCount = 0;
	public static int charCount = 0;
	public static int partCount = 0;
	
	public static void PrintStats(String companyName) 
    {
		if(companyName.equalsIgnoreCase("unity"))
		{
			readFile("data/UNITY - December 31, 2022.txt");
			printPartStuff();
		}
		else if(companyName.equalsIgnoreCase("tupperware"))
		{
			readFile("data/TUPPERWARE - December 31, 2022.txt");
			printPartStuff();
		}
	}
	
	private static void printPartStuff()
	{
		fileProcessorBE.LogChatAndPrint(String.format("There are %s parts, %s lines, %s words, and %s characters in the file :)\n\t",partCount, lineCount, wordCount, charCount));
	}
	
	public static int countParts(String fileLine)
	{
		if (fileLine.toUpperCase().contains("PART I") && partCount != 2 && partCount != 3 && partCount != 4)
			partCount = 1;
		if (fileLine.toUpperCase().contains("PART II") && partCount != 3 && partCount != 4)
			partCount = 2;
		if (fileLine.toUpperCase().contains("PART III") && partCount != 4)
			partCount = 3;
		if (fileLine.toUpperCase().contains("PART IV"))
			partCount = 4;
		return partCount;
	}
	
	public static int countWords(String s){

	    int wordCount = 0;

	    boolean word = false;
	    int endOfLine = s.length() - 1;

	    for (int i = 0; i < s.length(); i++) {
	        // if the char is a letter, word = true.
	        if (Character.isLetter(s.charAt(i)) && i != endOfLine) {
	            word = true;
	            // if char isn't a letter and there have been letters before,
	            // counter goes up.
	        } else if (!Character.isLetter(s.charAt(i)) && word) {
	            wordCount++;
	            word = false;
	            // last word of String; if it doesn't end with a non letter, it
	            // wouldn't count without this.
	        } else if (Character.isLetter(s.charAt(i)) && i == endOfLine) {
	            wordCount++;
	        }
	    }
	    return wordCount;
	}
	
	public static void readFile(String aFile)
	{	
		try 
		{
			Scanner fileScanner = new Scanner(new File("./"+aFile));
			while(fileScanner.hasNextLine())
			{
				String next = fileScanner.nextLine();
				lineCount++;
				charCount += next.length();
				wordCount += countWords(next);
				countParts(next);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
	}
}