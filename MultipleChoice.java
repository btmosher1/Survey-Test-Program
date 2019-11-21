import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class MultipleChoice extends TrueFalse
{
	ArrayList<String> options;
	int numOptions;
	int numAnswers;
	
	public MultipleChoice()
	{
		super();
		options = new ArrayList<String>();
		numOptions = 0;
		numAnswers = 0;
	}
	
	@Override
	public void create()
	{
		Scanner scan = new Scanner(System.in);
		String option;
		String num;
		
		//get prompt from user
		System.out.println("Enter the prompt for your multiple choice question:");
		prompt = scan.nextLine();
		
		//get number of options from user
		System.out.println("Enter the # of choices for your multiple choice question:");
		num = scan.nextLine();
		
		//check that user input is valid integer
		while(!isInteger(num))
		{
			System.out.println("Invalid Input.\n");
			System.out.println("Enter the # of choices for your multiple choice question:");
			num = scan.nextLine();
		}
				
		numOptions = Integer.parseInt(num);
		
		//enter details for each option
		for(int i = 1; i <= numOptions; i++)
		{
			System.out.println("Enter choice #" + i + ": ");
			option = scan.nextLine();
			options.add(option);
		}
		
		//ask for number of answer to question
		System.out.println("Enter the # of answers for your multiple choice question:");
		num = scan.nextLine();
		
		//check that user input is valid integer
		while(!isInteger(num))
		{
			System.out.println("Invalid Input.\n");
			System.out.println("Enter the # of answers for your multiple choice question:");
			num = scan.nextLine();
		}
		
		numAnswers = Integer.parseInt(num);
	}
	
	@Override
	public String display()
	{	//displays prompt and all of the options
		String question = "";
		question = prompt + "\n";
		
		for(int i = 0; i < options.size(); i++)
		{
			question+= " " + (i+1) + ") " + options.get(i) + "\n";
		}
		
		return question;
	}
	
	@Override
	public void modify()
	{
		//variables
		Scanner scan = new Scanner(System.in);
		String response = "-1";
		
		//display question
		System.out.println(display());
		
		while(response == "-1")
		{
			//prompt user to modify prompt
			System.out.println("Do you wish to modify the prompt? (Y/N)");
			response = scan.nextLine();
			
			switch(response)
			{
			case "Y":
				modifyPrompt();
				break;
			case "N":
				modifyChoice();
				break;
			default:
				System.out.println("Invalid Input\n");
				response = "-1";
				break;
			}
			
			//if prompt was modified ask user if they'd also like to modify choices
			if(response == "Y") 
			{
				System.out.println("Do you wish to modify the choices? (Y/N)");
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
			
			//check that input is valid integer
			if(isInteger(response))
			{	
				index = Integer.parseInt(response);
				index--;
				
				//check input is within range of choices
				if(index < options.size() &&index >= 0)
				{
					//prompts user to enter new value for choice selected
					System.out.println("Enter new value: ");
					response = scan.nextLine();
					
					//replace value of choice
					options.remove(index);
					options.add(index, response);
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
		//variables
		Scanner scan = new Scanner(System.in);
		String answer = "";
		int check = 0;
		
		//display question
		System.out.println(display());
		
		while (check == 0)	//error checking for user input
		{
			check = 1;
			
			if(numAnswers > 1)
			{
				System.out.println("Enter #'s of the " + numAnswers + " correct choices (seperate each # by a space): ");
			}
			else
				System.out.println("Enter the # of the correct choice: ");
			
			answer = scan.nextLine();
			
			String[] split = answer.split(" ");
			
			//checks that correct number of options was given in answer
			if(split.length != numAnswers)
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
		HashMap<String,Integer> answers = new HashMap<String,Integer>();
		String curAnswer;
		int count = 0;
		
		for(int i = 0; i < allResults.size(); i++)
		{
			curAnswer = allResults.get(i).get(index);
			
			if(answers.containsKey(curAnswer))
			{
				count = answers.get(curAnswer);
				count++;
				answers.put(curAnswer, count);
			}
			else
				answers.put(curAnswer, 1);	
		}
		
		System.out.println(display());
		System.out.println("Tabulation: ");
		
		for (String s: answers.keySet())
		{
            String key =s.toString();
            String value = answers.get(s).toString();  
            System.out.println(key + " : " + value); 
		}
	}
}
