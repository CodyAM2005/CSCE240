// Imported packages
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Scanner;
import java.io.File;

public class fileProcessorFE 
{
	public static void main(String[] args) 
	{
		fileProcessorBE BE = new fileProcessorBE(); //Instance variable of fileProcessorBE
		
		Scanner scan = new Scanner(System.in); //Opening the scanner for user input to the console
		
		System.out.print("Welcome to the ultimate business chatbot! Knowledgeable about Unity and Tupperware!\nPlease message what you would like to discuss :)\n\t");
		
		while(true) //This code will run until the break statement in matcherStop.find()
		{
			String input = scan.nextLine(); //Receive user input
			
			
			/*
			 * These lines with patterns and matchers work in the same way...
			 * First, I create a pattern, which will be something that the matcher will be looking for
			 * Then the matcher checks for the pattern in the input line, and decides what to output accordingly
			 */
			Pattern patternUnity = Pattern.compile("Un..y", Pattern.CASE_INSENSITIVE); //The periods in Un..y means that they can replace the two middle
			Matcher matcherUnity = patternUnity.matcher(input);						   //letters with whatever, and it will still recognize the word as Unity
			
			Pattern patternTupp = Pattern.compile("Tupp", Pattern.CASE_INSENSITIVE);
			Matcher matcherTupp = patternTupp.matcher(input);
			
			Pattern patternHello = Pattern.compile("Hello|Hey", Pattern.CASE_INSENSITIVE);
			Matcher matcherHello = patternHello.matcher(input);
			
			Pattern patternStop = Pattern.compile("^q$|quit", Pattern.CASE_INSENSITIVE);
			Matcher matcherStop = patternStop.matcher(input);
			
			File txtFile = null; //Create an empty text file that will be read, the text will be set to either the unity 10K or tupperware 10K
			
			String[] wordArr = null;
			if(matcherHello.find())
			{
				System.out.print("Hey, how are you doing today?\n\t");
			}
			else if(matcherUnity.find()) //If the pattern "Un..y" was found, then run this code
			{
				txtFile = new File("data/UNITY - December 31, 2022.txt"); //Open Unity 10K file
				wordArr = BE.readFile(input); //Run the readFile method, passing in the txtFile and the input line
			}
			else if(matcherTupp.find()) //If the pattern "Tupp" was found, run this code
			{
				txtFile = new File("data/TUPPERWARE - December 31, 2022.txt"); //Open Tupp 10K file
				wordArr = BE.readFile(input); //Run the readFile method, passing in the the input line
			}
			else if(matcherStop.find()) //If the pattern "Stop" was found, run this code
			{
				System.out.println("Goodbye!!!");
				break; //This breaks out of the loop
			}
			else
			{
				System.out.print("Please include either the name \'Unity\' or \'Tupperware\' in your input\n\t");
			}
			
			//Now that we have the relevant txt file, and the word array, we can print the relevant info with this information
			if(wordArr != null && txtFile != null) //This means that a valid input was given
				BE.printRelevantInfo(txtFile, wordArr);
			
		}
		scan.close(); //Close the scanner once the loop is finished
	}
}