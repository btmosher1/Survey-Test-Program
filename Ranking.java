import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Ranking extends Matching
{
	ArrayList<String> ranks;
	
	public Ranking()
	{
		super();
		ranks = new ArrayList<String>();
	}
	
	@Override
	public void create()
	{
		Scanner scan = new Scanner(System.in);
		String num;
		String item;
		
		//get prompt from user
		System.out.println("Enter the prompt for your ranking question:");
		prompt = scan.nextLine();
		
		//get number of items to be ranked
		System.out.println("Enter the # of items you want to have ranked:");
		num = scan.nextLine();
		
		//checks if input is valid integer
		while(!isInteger(num))
		{
			System.out.println("Invalid Input.\n");
			System.out.println("Enter the # of items for your ranking question:");
			num = scan.nextLine();
		}
		
		numOptions = Integer.parseInt(num);
		
		//asks user for details of each item to be ranked
		System.out.println("Enter items to be ranked (one per line): ");
		for(int i = 0; i < numOptions; i++)
		{
			item = scan.nextLine();
			ranks.add(item);
		}
	}
	
	@Override
	public String display()
	{
		//displays prompt and all items in a numbered list
		String question;
		question = prompt + "\n";
		
		int option = 1;
		
		for(int i = 0; i < ranks.size(); i++)
		{
			question += "   " + option + ") " + ranks.get(i) + "\n";
			option += 1;
		}
		
		return question;
	}
	
	@Override
	public void modifyChoice()
	{
		//variables
		Scanner scan = new Scanner(System.in);
		String response = "-1";
		int index;
		
		//display question
		System.out.println(display());
		
		while(response == "-1")
		{
			//prompt user to pick choice to modify
			System.out.println("Enter the # of the choice you wish to modify");
			response = scan.nextLine();
			
			//check input is valid integer
			if(isInteger(response))
			{	
				index = Integer.parseInt(response);
				index--;
				
				//check input is within range of choices
				if(index < ranks.size() &&index >= 0)
				{
					//prompts user to enter new value for choice selected
					System.out.println("Enter new value: ");
					response = scan.nextLine();
					
					//replace value of choice
					ranks.remove(index);
					ranks.add(index, response);
				}
				else
				{
					System.out.println("Invalid Input\n");
					response = "-1";
				}
			}
			else
			{
				System.out.println("Invalid Input\n");
				response = "-1";
			}
		}
		
		response = "-1";
		
		while(response == "-1")
		{
			//prompt user to modify another choice or stop
			System.out.println("Do you wish to modify another choice? (Y/N)");
			response = scan.nextLine();
			
			switch(response)
			{
			case "Y":
				modifyChoice();
				break;
			case "N":
				break;
			default:
				System.out.println("Invalid Input\n");
				response = "-1";
				break;
			}
		}
	}

	@Override
	public String makeAnswer()
	{	
		//asks user for correct order, separating each # by a space
		Scanner scan = new Scanner(System.in);
		String answer = "";
		int check = 0;
		
		System.out.println(display());
		
		while (check == 0)	//error checking for user input
		{
			check = 1;
			System.out.println("Enter the correct order (seperate each # by a space): ");
			answer = scan.nextLine();
			
			String[] split = answer.split(" ");
			
			//checks that correct number of options was given in answer
			if(split.length != ranks.size())
			{
				System.out.println("Invalid Input\n");
				check = 0;
				continue;
			}

			//checks that the answer given is composed of all integers
			//checks that these integers are within the range of options
			for(int i = 0; i < split.length; i++)
			{
				if(!isInteger(split[i]))
				{
					System.out.println("Invalid Input\n");
					check = 0;
					break;
				}
				if(Integer.parseInt(split[i]) > numOptions || Integer.parseInt(split[i]) < 1)
				{
					System.out.println("Invalid Input\n");
					check = 0;
					break;
				}
			}
		}
	
		//returns users answer
		return answer;
	}

	@Override
	public void tabulate(ArrayList<UserResponses> allResults, int index)
	{
		//variables
		HashMap<String,Integer> answers = new HashMap<String,Integer>();
		String curAnswer;
		int count = 0;
		
		//loop through all response objects
		for(int i = 0; i < allResults.size(); i++)
		{
			//save answer to question from current response object
			curAnswer = allResults.get(i).get(index);
			
			//check if answer has already been account for
			if(answers.containsKey(curAnswer))
			{
				//increment total
				count = answers.get(curAnswer);
				count++;
				answers.put(curAnswer, count);
			}
			else //create new entry
				answers.put(curAnswer, 1);	
		}
		
		//display question
		System.out.println(display());
		System.out.println("Tabulation: ");
		
		//display each response below the # of times it was entered
		for (String s: answers.keySet())
		{
            String key = s.toString();
            String value = answers.get(s).toString();  
            System.out.println(value + ")\n" + key + "\n");
		}
	}
}
