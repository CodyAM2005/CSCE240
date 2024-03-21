import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class fileProcessorBE 
{
	/* The possible inquiries (posI) 2D array is made so that the program checks for all possible regex expressions
	 * Each inner array of the 2D array is formatted like this...
	 * {<Keyword 1>,<Keyword 2>,...,<Keyword n>,<Start indicator>,<End indicator>}
	 * The start and end indicator is where it starts printing and ends printing the file
	 * These are keywords that ensure that the program knows which information is relevant
	 */
	private String[][] posI = {{"Item 16","Summary","Item 16. ","SIGNATURES "},
			   {"15","Finance","Schedule","Item 15. ","Item 16. "},
		       {"14","Fees","Services","Item 14. ","Item 15. "},           
			   {"13","Related Transactions","Director Independence","Item 13. ","Item 14. "},
			   {"12","Security","Owner","Item 12. ","Item 13. "},
	           {"11","Executive","Compensation","Pay","Paid","Item 11. ","Item 12. "},
       		   {"10","Directors","Executive","Item 10. ","Item 11. "},
       		   {"9C","Foreign","Item 9C. ","Item 10. "},
       		   {"9B","Other Information","Item 9B. ","Item 9C. "},
       		   {"9A","Controls","Procedures","Item 9A. ","Item 9B. "},
			   {"9","Disagreements","Accountants","Item 9. ","Item 9A. "},
		       {"8","Finance","Statements","Item 8. ","Item 9. "},
		       {"7A","Quantitative","Qualitative","Market Risk","Item 7A. ","Item 8. "},
			   {"7","Analysis","Financial Condition","Item 7. ","Item 7A. "},
			   {"6","Reserved","Item 6. ","Item 7. "},
	           {"5","Equity","Item 5. ","Item 6. "},
			   {"4","Mine","Safety","Item 4. ","PART II "}, 
			   {"3","Legal","Item 3. ","Item 4. "},
			   {"2","Properties","Item 2. ","Item 3. "},
			   {"1B","Staff","Comments","Item 1B. ","Item 2. "},
			   {"1A","Risk", "Factors","Item 1A. ", "Item 1B. "},
			   {"1","Business","Background","Item 1. ", "Item 1A. "},
			   {"IV","PART IV ","SIGNATURES "},
			   {"III","Structure","PART III ","PART IV "},
			   {"II","operate","PART II ","PART III "},
			   {"I","PART I ","PART II "},
			   {"Every","All","PART I ","SIGNATURES "}};
	
	public String[] readFile(String input)
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
		System.out.print("I do not know this information\n\t");
		return null;
	}
	public double SimpsonCoef(String input, String regex) //Simpson Coefficient calculation
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
	public void printRelevantInfo(File txtFile, String[] sectionAreas) /*This method prints the relevant info to the file by
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
			
			System.out.print("Results have been printed to output.txt in the data file :)\nIf you would like to end the chat, reply \'quit\' or just \'q\'\n\t");
			
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
}
