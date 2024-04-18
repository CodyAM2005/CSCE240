import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.BufferedReader;
import java.io.FileOutputStream;

public class fileProcessorBE 
{
	private static FileWriter chatLogger;
	
	private static String chatFileName;
	
	private static int userUtt = 0;
	private static int computerUtt = 0;
	
	/* The possible inquiries (posI) 2D array is made so that the program checks for all possible regex expressions
	 * Each inner array of the 2D array is formatted like this...
	 * {<Keyword 1>,<Keyword 2>,...,<Keyword n>,<Start indicator>,<End indicator>}
	 * The start and end indicator is where it starts printing and ends printing the file
	 * These are keywords that ensure that the program knows which information is relevant
	 */
	
	private static final String[][] posI = {
    		{"part 1", "part i", "Part I","PART I ","PART II "},
            {"risk", "dangers", "look out for", "factoring risk", "item 1a", "Item 1A. Risk Factors","Item 1A. ", "Item 1B. "},
            {"staff", "workers", "employees", "item 1b", "Item 1B. Unresolved Staff Comments","Item 1B. ","Item 2. "},
            {"business", "item 1", "Item 1. Business","Item 1. ", "Item 1A. "},
            {"property", "land", "locations", "how many restaurants", "Item 2. Properties","Item 2. ","Item 3. "},
            {"legal", "law", "item 3", "Item 3. Legal Proceedings","Item 3. ","Item 4. "},
            {"safety", "mine", "item 4", "disclosure", "Item 4. Mine Safety Disclosures","Item 4. ","PART II "},
            {"part 2", "part ii", "Part II","PART II ","PART III "},
            {"registrant", "item 5", "Item 5. Market for Registrant’s Common Equity, Related Stockholder Matters and Issuer Purchases of Equity Securities","Item 5. ","Item 6. "},
            {"reserved", "item 6", "Item 6. Reserved","Item 6. ","Item 7. "},
            {"discussion","analysis", "financial condition", "item 7", "operations", "Item 7. Management’s Discussion and Analysis of Financial Condition and Results of Operations","Item 7. ","Item 8. "},
            {"market risk", "quantitative", "qualitative", "item 7a", "Item 7A. Quantitative and Qualitative Disclosures About Market Risk","Item 7A. ","Item 8. "},
            {"financial", "statements", "supplementary data", "item 8", "Item 8. Financial Statements and Supplementary Data","Item 8. ","Item 9. "},
            {"changes", "disagreements with accountants", "item 9", "Item 9. Changes in and Disagreements with Accountants on Accounting and Financial Disclosure","Item 9. ","Item 10. "},
            {"controls", "procedures", "item 9a", "Item 9A. Controls and Procedures","Item 9A. ","Item 9B. "},
            {"other", "item 9b", "Item 9B. Other Information","Item 9B. ","Item 9C. "},
            {"foreign", "inspections", "item 9c", "Item 9C. Disclosure Regarding Foreign Jurisdictions that Prevent Inspections","Item 9C. ","Item 10. "},
            {"part 3", "part iii", "Part III","PART III ","PART IV "},
            {"directors", "executives", "corporate", "item 10", "Item 10. Directors, Executive Officers and Corporate Governance","Item 10. ","Item 11. "},
            {"compensation", "item 11", "Item 11. Executive Compensation","Item 11. ","Item 12. "},
            {"ownership", "stockholder", "item 12", "Item 12. Security Ownership of Certain Beneficial Owners and Management and Related Stockholder Matters","Item 12. ","Item 13. "},
            {"relationships", "transactions", "independence", "item 13", "Item 13. Certain Relationships and Related Transactions, and Director Independence","Item 13. ","Item 14. "},
            {"accounting fees", "services", "item 14", "Item 14. Principal Accounting Fees and Services","Item 14. ","Item 15. "},
            {"part 4", "part iv", "Part IV","PART IV ","SIGNATURES "},
            {"exhibits", "schedules", "item 15", "Item 15. Exhibits and Financial Statement Schedules","Item 15. ","Item 16. "},
            {"summary", "generalize this", "item 16", "Item 16. Form 10-K Summary","Item 16. ","SIGNATURES "},
            {"all info", "all information","PART I ","SIGNATURES "}};
	
	public static String[] readFile(String input)
	{
		double highestCoef = -1;
		int returnArr = 0;
		double threshold = 0.7;
		for(int i = 0; i<posI.length; i++) //For loop goes through each potential regex expression
		{
			String[] pattArr = posI[i]; //Creates an array of patterns, this makes the next code shorter and easier to read
			for(int j = 0; j<pattArr.length-2; j++) //For loop goes through each potential understood word for regex,
			{													 //It is the length minus 2, because the last 2 words are not used for this part
				
				if(SimpsonCoef(input,pattArr[j])>highestCoef) //Goes through each regex expression and sees which has the closest match
				{
					highestCoef = SimpsonCoef(input,pattArr[j]);
					returnArr = i;
				}
			}
		}
		if(highestCoef >= threshold) //If the information is recognized enough
		{
			String[] pattArr = posI[returnArr];
			return new String[] {pattArr[pattArr.length-2],pattArr[pattArr.length-1]};
		}
		//No input recognized
		LogChatAndPrint("I do not know this information\n\t");
		return null;
	}
	public static double SimpsonCoef(String input, String regex) //Simpson Coefficient calculation
	{
		//The next two lines are for formatting the strings to be more easily parsed.
		input = input.toLowerCase().replace("tupperware","").replace("unity", "");
		regex = regex.toLowerCase().replace(" ","");
		
		String[] inputArr = input.split(" "); //Split input into an array, checking word by word
	
		double bestMatching = -1; //Initialize the best matching word at -1
		
		for(int h = 0; h<inputArr.length; h++) //Loop by each word in the input
		{
			String tempInput = inputArr[h]; //Creates temporary versions of the words/input
			String tempRegex = regex;
			double uncommonLetters = tempRegex.length() + tempInput.length(); //Later, the common letters will be subtracted, leaving uncommon ones
			int matches = 0;
			for(int i = tempRegex.length()-1; i>=0; i--) //Goes through in backwards order, removing letters that are in both the input and output
			{
				if(tempInput.contains(regex.substring(i,i+1)))
				{
					tempInput = tempInput.replaceFirst(tempRegex.substring(i,i+1), "");
					matches++;
				}
				tempRegex.substring(0,i);
			}
			uncommonLetters -= (int)matches; //Subtract the number of matched letters from the total number of letters
			if(matches/uncommonLetters > bestMatching)
			{
				bestMatching = matches/uncommonLetters; //The coefficient is the number of matches divided by number of unique letters between the two.
			}											//This gives a value from 0.0-1.0
		}
		return bestMatching;
	}
	public static void printRelevantInfo(File txtFile, String[] sectionAreas) /*This method prints the relevant info to the file by
																			   *starting at the start word and ending at the end word */
	{	
		try                                                                    
		{
			String sectionArea = sectionAreas[0];
			String stopSectionArea = sectionAreas[1];
			
			Scanner myReader = new Scanner(txtFile); //Create a file reader
			FileWriter myWriter = new FileWriter("data/output.txt"); //Create a file writer in the data file named 'output.txt'
			
			myWriter.write(txtFile.getName()+"\n\n"); //Writes a header to the file with the txt file name
						
			boolean inCorrectSection = false; //This value determines whether or not the line prints, because if you're in the correct section it will, if not it wont
			
			while (myReader.hasNextLine()) //This while loop goes through each line of the code
			{
				
			    String data = myReader.nextLine() + " "; //This string takes the line and puts it into a string
			    
			    if(data.indexOf(sectionArea) == 0) //If this line starts with the word that is "sectionArea", you're in the right section...
			    {
			    	inCorrectSection = true; //So set inCorrectSection to true
			    }
			    else if(data.indexOf(stopSectionArea) == 0) //If this line starts with the word that is "stopSectionArea", you're in the wrong section...
			    {
			    	inCorrectSection = false; //So set inCorrectSection to false
			    }
			    if(inCorrectSection) //If you're in the correct section...
			    {
			    	myWriter.write(data+"\n"); //Print the line to the file
			    }
			}
			
			LogChatAndPrint("Results have been printed to output.txt in the data file :)\nIf you would like to end the chat, reply \'quit\' or just \'q\'\n\t");
			
			myReader.close(); //Close the file reader and writer
			myWriter.close();
		}
		catch(FileNotFoundException e) //In case an error happens with the error not being found
		{
			e.printStackTrace();
		}
		catch(Exception e) //In case an error happens that was unexpected
		{
			e.printStackTrace();
		}
	}
	public static void LogChatAndPrint(String text)
	{
		System.out.print(text);
		try
		{
			chatLogger.write(text);
			computerUtt++;
		}
		catch(Exception e)
		{
			
		}
	}
	public static void LogChat(String text)
	{
		try
		{
			chatLogger.write(text+"\n");
			userUtt++;
		}
		catch(Exception e)
		{
			
		}
	}
	public static void initChatLogger()
	{
		try
		{
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM_dd_yyyy__hh_mm_ss_a");

			String formattedTime = LocalDateTime.now().format(formatter);
			chatLogger = new FileWriter("data/chat_sessions/data_"+formattedTime+".txt");
			chatFileName = "data/chat_sessions/data_"+formattedTime+".txt";
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public static void closeChatLogger()
	{
		try
		{
			chatLogger.close();
		}
		catch(Exception e)
		{
			
		}
	}
	public static void writeCSV(long timeTaken)
	{
		try //S.NO,file_name,#user_utterance,#system_utterance,time_elapsed
		{
			File csvFile = new File("data/chat_sessions/chat_statistics.csv");
			PrintWriter pw = new PrintWriter(new FileOutputStream(
					csvFile,
					true /* append = true */));
			
			pw.write(getChatNOInc()+","); //Write the chat no
			pw.write(chatFileName+","); //Write the chat file name
			pw.write(userUtt+","); //Write the chat # user utterance
			pw.write(userUtt+","); //Write the chat # computer utterance
			pw.write(timeTaken+"\n"); //Write the chat time taken
			
			pw.close();
		}
		catch(Exception e)
		{
			
		}
	}
	public static int getChatNOInc()
	{
		try
		{
			FileReader reader = new FileReader("data/chat_sessions/chat_statistics.csv");
			BufferedReader bf = new BufferedReader(reader);
			int number = 0;
			String line;
			while((line = bf.readLine())!=null)
			{
				String[] columns = line.split(",");
				if(columns[0].equals("S.NO"))
					continue;
				number = Integer.parseInt(columns[0]);
			}
			
			bf.close();
			
			return number +1;
		}
		catch(Exception e)
		{
			
		}
		return -1;
	}
}
