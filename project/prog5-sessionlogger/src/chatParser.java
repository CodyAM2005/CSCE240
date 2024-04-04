import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class chatParser 
{
	public static void main(String[] args) 
	{	
		Scanner scan = new Scanner(System.in); //Opening the scanner for user input to the console
		
		Pattern patternChatSumm = Pattern.compile("showchat-summary [1-9]+", Pattern.CASE_INSENSITIVE); //These patterns will be used to search for the used commands later
		
		Pattern patternShowChat = Pattern.compile("showchat [1-9]+", Pattern.CASE_INSENSITIVE);
		
		Pattern patternChatNum = Pattern.compile("[0-9]+", Pattern.CASE_INSENSITIVE); //This is used to find the chat number later
		
		
		while(true) //inf loop
		{
			System.out.println("Here are all of the commands that you are allowed to use!");
			System.out.println("\'Summary\': Gives a summary of all chats in the system");
			System.out.println("\'Showchat-summary <Chat NO>\': Gives a summary of chat specified");
			System.out.println("\'Showchat <Chat NO>\': Outputs the chat logs of chat specified");
			System.out.println("\'PA4\': Continues on to use PA4");
			System.out.print("\'Quit\': Quits the program\n\t"); //Prints the commands\
			
			String input = scan.nextLine(); //Gather input from user
			
			Matcher matcherChatSumm = patternChatSumm.matcher(input); //Create the matchers for the patterns
			
			Matcher matcherShowChat = patternShowChat.matcher(input); //Create the matchers for the patterns
			
			Matcher matcherChatNum = patternChatNum.matcher(input); //Create the matchers for the patterns
		
			if(matcherChatSumm.find() && matcherChatNum.find())
			{
				chatSummary(Integer.parseInt(matcherChatNum.group())); //Print specific chat summary
			}
			else if(matcherShowChat.find() && matcherChatNum.find())
			{
				showChat(Integer.parseInt(matcherChatNum.group())); //Print specific chat log
			}
			else if(input.toLowerCase().contains("summary"))
			{
				summary(); //print summary of chats
			}
			else if(input.toLowerCase().contains("pa4"))
			{
				fileProcessorFE.runPA4(); //run the base pa4 program
				break; //quit inf loop after running pa4
			}
			else if(input.toLowerCase().contains("quit"))
			{
				break; //quit the inf loop
			}
			else
			{
				System.out.println("Your input is bad, do it again >:(");
			}
		}
	}
	public static void summary() //This method prints the summary of all chats
	{
		try
		{
			FileReader reader = new FileReader("data/chat_sessions/chat_statistics.csv");
			BufferedReader bf = new BufferedReader(reader); //Creates reader and buffered reader to parse the csv file
			
			int totalChats = 0; //Initialization of variables needed
			int numUserMessage = 0;
			int numSystemMessage = 0;
			int totalTimeElapsed = 0;
			
			String line;	
			while((line = bf.readLine())!=null) //Go through each line of the csv
			{
				String[] columns = line.split(","); //Split the csv by commas
				if(columns[0].equals("S.NO")) //If it's the first line, skip it
					continue;
				
				totalChats++; //Increment all values so that it adds together everything
				numUserMessage += Integer.parseInt(columns[2]);
				numSystemMessage += Integer.parseInt(columns[3]);
				totalTimeElapsed += Integer.parseInt(columns[4]);
			}
			
			System.out.print(String.format("There are %d total chats to date with user asking %d times and system respond %d times. Total duration is %d seconds.\n\t", 
					totalChats, numUserMessage, numSystemMessage, totalTimeElapsed));
			
			bf.close(); //Close the reader
		}
		catch(Exception e) //Just in case of file error
		{
			e.printStackTrace();
		}
	}
	public static void chatSummary(int chatNum)
	{
		try
		{
			FileReader reader = new FileReader("data/chat_sessions/chat_statistics.csv");
			BufferedReader bf = new BufferedReader(reader); //Create file reader
			
			int numUserMessage = 0; //Initialize variables needed
			int numSystemMessage = 0;
			int timeElapsed = 0;
			
			String line;
			while((line = bf.readLine())!=null) //parse each line of code
			{
				String[] columns = line.split(","); //Split line of code by commas
				if(!columns[0].equals(chatNum+"")) //This will skip all lines but the wanted line
					continue;
				
				numUserMessage = Integer.parseInt(columns[2]); //Set all variables to correct values
				numSystemMessage = Integer.parseInt(columns[3]);
				timeElapsed = Integer.parseInt(columns[4]);
			}
			if(numSystemMessage == 0) //There is always at least 1 system message, so if it's 0 then its an invalid chat num
				System.out.print(String.format("Error, there is no chat with the ID num of %d\n\t",chatNum));
			else
				System.out.print(String.format("Chat %d has user asking %d times and system respond %d times. Total duration is %d seconds.\n\t", 
						chatNum, numUserMessage, numSystemMessage, timeElapsed));
			
			bf.close(); //Close file reader
		}
		catch(Exception e) //Just in case of file error
		{
			e.printStackTrace();
		}
	}
	public static void showChat(int chatNum) //This method gets the file of the chat needed to print, and sends that file to the print contents method
	{
		try
		{
			FileReader reader = new FileReader("data/chat_sessions/chat_statistics.csv");
			BufferedReader bf = new BufferedReader(reader); //Create a file parser to parse the csv file
			
			String fileName = "-1"; //Will stay -1 if there is an invalid chat number
			
			String line;
			while((line = bf.readLine())!=null) //go through each line in the csv
			{
				String[] columns = line.split(","); //Split the csv by commas
				if(!columns[0].equals(chatNum+"")) //Only use the line that equals the chat number
					continue;
				
				fileName = columns[1]; //Set filename to the filename that the chat was sent to
			}
			if(fileName.equals("-1")) //Error checking
				System.out.print(String.format("Error, there is no chat with the ID num of %d\n\t",chatNum));
			else
				printFileContents(fileName);
			
			bf.close(); //Close file reader
		}
		catch(Exception e) //Just in case of file error
		{
			e.printStackTrace();
		}
	}
	public static void printFileContents(String fileName) //This method prints all the contents of a given file
	{
		try
		{
			FileReader reader = new FileReader(fileName);
			BufferedReader bf = new BufferedReader(reader); //create a buffered reader to parse the file
			
			String line;
			while((line = bf.readLine())!=null)
			{
				System.out.println(line); //print each line of the file
			}
			
			bf.close(); //close the file reader
		}
		catch(Exception e) //Just in case there is a file error
		{
			e.printStackTrace();
		}
	}
}