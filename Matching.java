import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Matching extends Question
{
	ArrayList<String> leftSide;
	ArrayList<String> rightSide;
	int numOptions;
	
	public Matching()
	{
		super();
		leftSide = new ArrayList<String>();
		rightSide = new ArrayList<String>();
		numOptions = 0;
	}
	
	@Override
	public void create()
	{
		String num, match;
		Scanner scan = new Scanner(System.in);
		
		//get prompt for matching question
		System.out.println("Enter the prompt for your matching question:");
		prompt = scan.nextLine();
		
		//get number of pairs for matching question
		System.out.println("Enter the # of pairs for your matching question:");
		num = scan.nextLine();
		
		//check if user input is valid number
		while(!isInteger(num))
		{
			System.out.println("Invalid Input.\n");
			System.out.println("Enter the # of pairs for your matching question:");
			num = scan.nextLine();
		}
		
		numOptions = Integer.parseInt(num);
		
		//enter data for left col of choices
		System.out.println("Enter left side of pairs (one per line): ");
		
		for(int i = 1; i <= numOptions; i++)
		{
			match = scan.nextLine();
			leftSide.add(match);
		}
		
		//enter data for right col of choices
		System.out.println("Enter right side of pairs (one per line): ");
		
		for(int i = 1; i <= numOptions; i++)
		{
			match = scan.nextLine();
			rightSide.add(match);
		}
	}
	
	@Override
	public String display()
	{	//displays prompt and two columns to be matched
		char p1 = 'A';
		int p2 = 1;
		String question;
		
		question = prompt + "\n";
		
		for(int i = 0; i < leftSide.size(); i++)
		{
			String leftCol = p1 + ") " + leftSide.get(i);
			String rightCol = p2 + ") " + rightSide.get(i);
			question += " " + leftCol + "\t\t" + rightCol + "\n";
	        p1++;
	        p2++;
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
			
			//prompt user 
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
		
		//display question
		System.out.println(display());
		
		while(response == "-1")
		{
			//prompt user to enter side of pair they wish to modify
			System.out.println("Enter the side of the choice you wish to modify. (L/R)");
			response = scan.nextLine();
			
			switch(response)
			{
			case "L":
				leftSideChoice();
				break;
			case "R":
				rightSideChoice();
				break;
			default:
				System.out.println("Invalid Input\n");
				response = "-1";
				break;
			}
		}
		
		response = "-1";
		
		while(response == "-1")
		{
			//prompt user to modify another choice or quit
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
	
	public void leftSideChoice()
	{
		//variables
		Scanner scan = new Scanner(System.in);
		String response = "-1";
		int index;
		
		//display question
		System.out.println(display());
		
		while(response == "-1")
		{
			//prompt user for letter of choice they wish to modify
			System.out.println("Enter the letter of the choice you wish to modify");
			response = scan.nextLine();
			
			//convert letter given to index
			index = convertToIndex(response.toLowerCase());
			
			//check input is within range of options
			if(index >= 0 && index < rightSide.size())
			{
				//get new value for option from user
				System.out.println("Enter new value: ");
				response = scan.nextLine();
				
				//insert new value for option
				leftSide.remove(index);
				leftSide.add(index, response);
			}
			else
			{
				System.out.println("Invalid Input\n");
				response = "-1";
			}
		}
	}
	
	public void rightSideChoice()
	{
		//variables
		Scanner scan = new Scanner(System.in);
		String response = "-1";
		int index;
		
		//display question
		System.out.println(display());
		
		while(response == "-1")
		{
			//prompt user for # of question they wish to modify
			System.out.println("Enter the # of the choice you wish to modify");
			response = scan.nextLine();
			
			//check that input is valid integer
			if(isInteger(response))
			{	
				index = Integer.parseInt(response);
				index--;
				
				//check input is within range of choices
				if(index < rightSide.size() &&index >= 0)
				{
					//prompts user to enter new value for choice selected
					System.out.println("Enter new value: ");
					response = scan.nextLine();
					
					//replace value of choice
					rightSide.remove(index);
					rightSide.add(index, response);
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
	}
	
	public int convertToIndex(String s)
	{
		//return choice as integer
		char ch = s.charAt(0);
		int pos = ch - 'a';
		return pos;
	}
	
	@Override
	public String makeAnswer()
	{	
		//variables
		Scanner scan = new Scanner(System.in);
		String answer = "";
		String pair = "";
		String[] split;
		int check = 0;

		//display question
		System.out.println(display());
		
		while(check == 0)
		{
			System.out.println("Enter the correct pairs (seperate each letter and # by a space, one pair per line): ");
			//check each pair entered for proper syntax
			for(int i = 0; i < numOptions; i++)
			{
				pair = scan.nextLine();
				split = pair.split(" ");
				
				//checks that correct number of inputs was given in answer
				if(split.length != 2)
				{
					System.out.println("Invalid Input\n");
					break;
				}
				
				//check user input for each side of pair separately
				if(checkLeftSideInput(split[0]) && checkRightSideInput(split[1]))
				{
					//add pair to answer
					answer += pair;
				}
				else
					break;
				
				//comma separate pairs
				if(i < numOptions)
				{
					answer += ",";
				}
			}
			
			//checks that correct number of pairs is given
			split = answer.split(",");
			if(split.length == numOptions)
				break;
		}
		
		return answer;
	}
	
	public boolean checkLeftSideInput(String input)
	{
		int check = 'A' + numOptions-1;
		
		//checks that the answer given is composed of all letters
		//checks that these letters are within the range of options
		if(input.charAt(0) < 'A' || input.charAt(0) > check)
		{
			System.out.println("Invalid Input\n");
			return false;
		}
		
		return true;
	}
	
	public boolean checkRightSideInput(String input)
	{
		//checks that the answer given is composed of all integers
		//checks that these integers are within the range of options
		if(!isInteger(input))
		{
			System.out.println("Invalid Input\n");
			return false;
		}
		if(Integer.parseInt(input) > numOptions || Integer.parseInt(input) < 1)
		{
			System.out.println("Invalid Input\n");
			return false;
		}
		
		return true;
	}

	@Override
	public void tabulate(ArrayList<UserResponses> allResults, int index)
	{
		//variables
		HashMap<String,Integer> answers = new HashMap<String,Integer>();
		String curAnswer;
		int count = 0;
		
		//display question
		System.out.println(display());
		
		//loop through all response objects
		for(int i = 0; i < allResults.size(); i++)
		{
			//store answer to question from current response object
			curAnswer = allResults.get(i).get(index);
			
			//check if answer already exists
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
		
		System.out.println("Tabulation: ");
		
		//display tabulation
		for (String s: answers.keySet())
		{
            String key = s.toString();
            String value = answers.get(s).toString();  
            
            //display total entries of current answer
            System.out.println(value + ")");
            
            String[] split = key.split(",");
            
            //display pairs of current answer on separate lines
            for(int i = 0; i < split.length; i++)
            {
            	System.out.println(split[i]);
            }
            System.out.println();
		}
	}
}
