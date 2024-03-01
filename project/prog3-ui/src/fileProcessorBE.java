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
			   {"Item 15","Finance (Statement)? Schedule","Financial Statement Schedule","Item 15. ","Item 16. "},
		       {"Item 14","Fees","Services","Item 14. ","Item 15. "},           
			   {"Item 13","Related Transactions","Director Independence","Item 13. ","Item 14. "},
			   {"Item 12","Security","Owner","Item 12. ","Item 13. "},
	           {"Item 11","Executive Compensation","Executive Pay","Executive Paid","Item 11. ","Item 12. "},
       		   {"Item 10","Directors","Executive Officers","Corporate Governances","Item 10. ","Item 11. "},
       		   {"Item 9C","Foreign","Item 9C. ","Item 10. "},
       		   {"Item 9B","Other Information","Item 9B. ","Item 9C. "},
       		   {"Item 9A","Controls","Procedures","Item 9A. ","Item 9B. "},
			   {"Item 9","Disagreements","Accountants","Item 9. ","Item 9A. "},
		       {"Item 8","Financial","Finance","Supplementary Data","Item 8. ","Item 9. "},
		       {"Item 7A","Quantitative","Qualitative","Market Risk","Item 7A. ","Item 8. "},
			   {"Item 7","Analysis","Financial Condition","Item 7. ","Item 7A. "},
			   {"Item 6","Reserved","Item 6. ","Item 7. "},
	           {"Item 5","Equity","Item 5. ","Item 6. "},
			   {"Item 4","Mine","Safety","Item 4. ","PART II "}, 
			   {"Item 3","Legal","Item 3. ","Item 4. "},
			   {"Item 2","Properties","Item 2. ","Item 3. "},
			   {"Item 1B","Staff","Comments","Item 1B. ","Item 2. "},
			   {"Item 1A","Risk", "Item 1A. ", "Item 1B. "},
			   {"Item 1","Business","Background","Item 1. ", "Item 1A. "},
			   {"Part 4","Part IV","PART IV ","SIGNATURES "},
			   {"Part 3","Part III","Structure","PART III ","PART IV "},
			   {"Part 2","Part II","operate","operations","PART II ","PART III "},
			   {"Part 1","Part I","\\sdo$","PART I ","PART II "},
			   {"Every","All","PART I ","SIGNATURES "}};
	
	public String[] readFile(String input)
	{
		
		
		for(int i = 0; i<posI.length; i++) //For loop goes through each potential regex expression
		{
			String[] pattArr = posI[i]; //Creates an array of patterns, this makes the next code shorter and easier to read
			for(int j = 0; j<pattArr.length-2; j++) //For loop goes through each potential understood word for regex,
			{													 //It is the length minus 2, because the last 2 words are not used for this part
				
				Pattern pattern = Pattern.compile(pattArr[j], Pattern.CASE_INSENSITIVE); //Creates a case insensitive pattern for the keyword given
				Matcher matcher = pattern.matcher(input);
				
				if(matcher.find()) //If the matcher found the pattern from the 2D array of patterns in the input
				{				   //Then print the relevant information
					
					String[] arr = {pattArr[pattArr.length-2],pattArr[pattArr.length-1]};
					return arr;//Breaks the loop because it already printed relevant information
				}
			}
		}
		
		//No input recognized
		System.out.print("I do not know this information\n\t");
		return null;
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
