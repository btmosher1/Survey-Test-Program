import java.util.ArrayList;
import java.util.Scanner;

public class TrueFalse extends Question
{
	public TrueFalse()
	{
		super();
	}
	
	@Override
	public void create()
	{	//collects prompt for true false question
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter the prompt for your True/False question:");
		prompt = scan.nextLine();
	}
	
	@Override
	public void modify()
	{
		modifyPrompt();
	}
	
	@Override
	public String makeAnswer()
	{	//checks that answer given is either T or F
		Scanner scan = new Scanner(System.in);
		String answer = "-1";
		
		//display question
		System.out.println(display());
		
		//prompt user to enter answer
		while(answer.equals("-1"))
		{
			System.out.println("Enter the correct answer (T/F): ");
			answer = scan.nextLine();
			
			if((answer.equals("T")) == (answer.equals("F")))
			{
				System.out.println("Invalid Input.\n");
				answer = "-1";
			}
			else
				break;
		}
		
		return answer;
	}
	
	@Override
	public void tabulate(ArrayList<UserResponses> allResults, int index)
	{
		// t/f count
		int tCount = 0;
		int fCount = 0;
		
		//tally # of T's and F's
		for(int i = 0; i < allResults.size(); i++)
		{
			if(allResults.get(i).get(index).equals("T"))
				tCount++;
			else
				fCount++;
		}
		
		//display question along with tally
		System.out.println(display());
		System.out.println("Tabulation: ");
		System.out.println("True: " + tCount);
		System.out.println("False: " + fCount);

	}
}
