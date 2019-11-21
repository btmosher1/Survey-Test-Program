import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ShortAnswer extends Essay
{
	public ShortAnswer()
	{
		super();
	}
	
	@Override
	public void create()
	{	//collects prompt for short answer question
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter the prompt for your short answer question:");
		prompt = scan.nextLine();
	}
	
	@Override
	public String makeAnswer()
	{	//collects answer for short answer question
		Scanner scan = new Scanner(System.in);
		String answer;
		System.out.println(display());
		System.out.println("Enter the correct answer: ");
		answer = scan.nextLine();
		return answer;
	}
	
	@Override
	public void tabulate(ArrayList<UserResponses> allResults, int index)
	{
		//variables
		HashMap<String,Integer> answers = new HashMap<String,Integer>();
		String curAnswer;
		int count = 0;
		
		//loop through response objects
		for(int i = 0; i < allResults.size(); i++)
		{
			//store answer to question from current response object
			curAnswer = allResults.get(i).get(index);
			
			//check if value already exists
			if(answers.containsKey(curAnswer))
			{
				//increment total responses
				count = answers.get(curAnswer);
				count++;
				answers.put(curAnswer, count);
			}
			else //add new entry
				answers.put(curAnswer, 1);	
		}
		
		//display question
		System.out.println(display());
		System.out.println("Tabulation: ");
		
		//display answers followed by the # of times it was entered
		for (String s: answers.keySet())
		{
            String key =s.toString();
            String value = answers.get(s).toString();  
            System.out.println(key + " " + value); 
		}
	}
}
