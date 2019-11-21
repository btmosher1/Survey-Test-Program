import java.util.ArrayList;
import java.util.Scanner;

public class Essay extends Question
{
	public Essay()
	{
		super();
	}
	
	@Override
	public void create()
	{	//Collects prompt for essay question
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter the prompt for your essay question:");
		prompt = scan.nextLine();
	}
	
	@Override
	public String makeAnswer()
	{	//collects answer for essay question
		Scanner scan = new Scanner(System.in);
		String line;
		String answer = "";
		int check = 0;
		
		System.out.println(display());
		System.out.println("Enter the correct answer (type \"end\" on a new line when finished): ");
		
		//user can type until entering "end" on a new line
		while(check == 0)
		{
			line = scan.nextLine();
			
			if(line.equals("end"))
				break;
			else
			{
				answer +="\n";
				answer += line;
			}
		}
		
		return answer;
	}
	
	@Override
	public void tabulate(ArrayList<UserResponses> allResults, int index)
	{
		//display prompt
		System.out.println(display());
		
		System.out.println("Tabulation: ");
		
		//display list of responses
		for(int i = 0; i < allResults.size(); i++)
		{
			System.out.println(allResults.get(i).get(index));
		}
	}
}
