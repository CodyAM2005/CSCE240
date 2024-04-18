// Imported packages
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;

public class fileProcessorFE 
{
	public static final String supportedInfo = "PART I\r\n"
			+ "Item 1.		Business\r\n"
			+ "Item 1A. 	Risk Factors\r\n"
			+ "Item 1B.	Unresolved Staff Comments\r\n"
			+ "Item 2.		Properties\r\n"
			+ "Item 3.		Legal Proceedings\r\n"
			+ "Item 4.		Mine Safety Disclosures\r\n"
			+ "\r\n"
			+ "PART II\r\n"
			+ "Item 5.		Market For Registrant’s Common Equity, Related Stockholder Matters, and Issuer Purchases of Equity Securities\r\n"
			+ "Item 6.		[Reserved]\r\n"
			+ "Item 7.		Management’s Discussion and Analysis of Financial Condition and Results of Operations\r\n"
			+ "Item 7A.	Quantitative and Qualitative Disclosures About Market Risk\r\n"
			+ "Item 8.		Financial Statements and Supplementary Data\r\n"
			+ "Item 9.		Changes in and Disagreements with Accountants on Accounting and Financial Disclosure\r\n"
			+ "Item 9A.	Controls and Procedures\r\n"
			+ "Item 9B.	Other Information\r\n"
			+ "Item 9C.	Disclosure Regarding Foreign Jurisdictions That Prevent Inspection\r\n"
			+ "\r\n"
			+ "PART III\r\n"
			+ "Item 10.	Directors, Executive Officers, and Corporate Governance\r\n"
			+ "Item 11.	Executive Compensation\r\n"
			+ "Item 12.	Security Ownership of Certain Beneficial Owners and Management and Related Stockholder Matters\r\n"
			+ "Item 13.	Certain Relationships and Related Transactions, and Director Independence\r\n"
			+ "Item 14.	Principal Accountant Fees and Services\r\n"
			+ "\r\n"
			+ "PART IV\r\n"
			+ "Item 15.	Exhibits and Financial Statement Schedules\r\n"
			+ "Item 16.	Form 10-K Summary\n\t";
	public static void main(String[] args)
	{
		try
		{	
			chatParser CP = new chatParser();
			
			long startTime;
			long endTime;
	
			startTime = System.currentTimeMillis();
			
			Scanner scan = new Scanner(System.in); //Opening the scanner for user input to the console
	
			fileProcessorBE.initChatLogger();
			
			fileProcessorBE.LogChatAndPrint("Welcome to the ultimate business chatbot! Knowledgeable about Unity and Tupperware!\nPlease message what you would like to discuss :)\n\t");
			
			Pattern patternUnity = Pattern.compile("Un..y", Pattern.CASE_INSENSITIVE); //The periods in Un..y means that they can replace the two middle
			Pattern patternTupp = Pattern.compile("Tupp", Pattern.CASE_INSENSITIVE);
			Pattern patternHello = Pattern.compile("Hello|Hey", Pattern.CASE_INSENSITIVE);
			Pattern patternSummary = Pattern.compile("session-summary", Pattern.CASE_INSENSITIVE);
			Pattern patternChatSummary = Pattern.compile("session-(summarychat|chatsummary) [0-9]+", Pattern.CASE_INSENSITIVE);
			Pattern patternShowChat = Pattern.compile("session-showchat [0-9]+", Pattern.CASE_INSENSITIVE);
			Pattern patternChatNum = Pattern.compile("[0-9]+", Pattern.CASE_INSENSITIVE); //This is used to find the chat number later
			Pattern patternCompanySupport = Pattern.compile("(?=.*companies)(?=.*support)", Pattern.CASE_INSENSITIVE);
			Pattern patternUsageStats = Pattern.compile("(summary|summaries)", Pattern.CASE_INSENSITIVE);
			Pattern patternUnityFileStats = Pattern.compile("(?=.*unity)(?=.*file)(?=.*stats)", Pattern.CASE_INSENSITIVE);
			Pattern patternTuppFileStats = Pattern.compile("(?=.*tupperware)(?=.*file)(?=.*stats)", Pattern.CASE_INSENSITIVE);
			Pattern patternPrintInfo = Pattern.compile("(?=.*info)(?=.*support)", Pattern.CASE_INSENSITIVE);
			Pattern patternStop = Pattern.compile("^q$|quit", Pattern.CASE_INSENSITIVE);
			
			while(true) //This code will run until the break statement in matcherStop.find()
			{
				String input = scan.nextLine(); //Receive user input
				fileProcessorBE.LogChat(input);
				
				/*
				 * These lines with patterns and matchers work in the same way...
				 * First, I create a pattern, which will be something that the matcher will be looking for
				 * Then the matcher checks for the pattern in the input line, and decides what to output accordingly
				 */
				Matcher matcherUnity = patternUnity.matcher(input);	
				Matcher matcherTupp = patternTupp.matcher(input);
				Matcher matcherHello = patternHello.matcher(input);
				Matcher matcherSummary = patternSummary.matcher(input);
				Matcher matcherChatSummary = patternChatSummary.matcher(input);
				Matcher matcherShowChat = patternShowChat.matcher(input);
				Matcher matcherChatNum = patternChatNum.matcher(input);
				Matcher matcherCompanySupport = patternCompanySupport.matcher(input);
				Matcher matcherUsageStats = patternUsageStats.matcher(input);
				Matcher matcherUnityFileStats = patternUnityFileStats.matcher(input);
				Matcher matcherTuppFileStats = patternTuppFileStats.matcher(input);
				Matcher matcherPrintInfo = patternPrintInfo.matcher(input);
				Matcher matcherStop = patternStop.matcher(input);
				
				File txtFile = null; //Create an empty text file that will be read, the text will be set to either the unity 10K or tupperware 10K
				
				String[] wordArr = null;
				if(matcherHello.find())
				{
					fileProcessorBE.LogChatAndPrint("Hey, how are you doing today?\n\t");
				}
				else if(matcherUnityFileStats.find())
				{
					fileParser.PrintStats("Unity");
				}
				else if(matcherTuppFileStats.find())
				{
					fileParser.PrintStats("Tupperware");
				}
				else if(matcherUnity.find()) //If the pattern "Un..y" was found, then run this code
				{
					txtFile = new File("data/UNITY - December 31, 2022.txt"); //Open Unity 10K file
					wordArr = fileProcessorBE.readFile(input); //Run the readFile method, passing in the txtFile and the input line
				}
				else if(matcherTupp.find()) //If the pattern "Tupp" was found, run this code
				{
					txtFile = new File("data/TUPPERWARE - December 31, 2022.txt"); //Open Tupp 10K file
					wordArr = fileProcessorBE.readFile(input); //Run the readFile method, passing in the the input line
				}
				else if(matcherChatSummary.find() && matcherChatNum.find())
				{
					CP.chatSummary(Integer.parseInt(matcherChatNum.group())); //Print specific chat summary
				}
				else if(matcherShowChat.find() && matcherChatNum.find())
				{
					CP.showChat(Integer.parseInt(matcherChatNum.group())); //Print specific chat log
				}
				else if(matcherSummary.find() || matcherUsageStats.find())
				{
					CP.summary(); //print summary of chats
				}
				else if(matcherCompanySupport.find())
				{
					fileProcessorBE.LogChatAndPrint("The companies that are supported are \'Unity\' and \'Tupperware\'\n\t");
				}
				else if(matcherPrintInfo.find())
				{
					fileProcessorBE.LogChatAndPrint(supportedInfo);
				}
				else if(matcherStop.find()) //If the pattern "Stop" was found, run this code
				{
					fileProcessorBE.LogChatAndPrint("Goodbye!!!");
	
					endTime = System.currentTimeMillis();
	
					long elapsedTimeSecs = (endTime - startTime)/1000;
					
					
					//BE method to write to csv
					fileProcessorBE.writeCSV(elapsedTimeSecs);
					
					fileProcessorBE.closeChatLogger();
					
					break; //This breaks out of the loop
				}
				else
				{
					fileProcessorBE.LogChatAndPrint("Please include either the name \'Unity\' or \'Tupperware\' in your input\n\t");
				}
				
				//Now that we have the relevant txt file, and the word array, we can print the relevant info with this information
				if(wordArr != null && txtFile != null) //This means that a valid input was given
					fileProcessorBE.printRelevantInfo(txtFile, wordArr);
				
			}
			scan.close(); //Close the scanner once the loop is finished
		}
		catch(Exception e)
		{
			
		}
	}
}