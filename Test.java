import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Test extends Survey
{
	Answers correctAnswers;
	
	public Test()
	{
		super();
		correctAnswers = new Answers();
	}
	
	public void create()
	{
		Scanner scan = new Scanner(System.in);
		scan = new Scanner(System.in);
		String response = "";
		
		//get name of test from user, also used as file name
		System.out.println("Enter name of new test: ");
		name = scan.nextLine();
		
		
		//display options for adding questions
		while(response != "7")
		{
			System.out.print("\n");
			System.out.println("1) Add a new T/F question");
			System.out.println("2) Add a new multiple choice question");
			System.out.println("3) Add a new short answer question");
			System.out.println("4) Add a new essay question");
			System.out.println("5) Add a new ranking question");
			System.out.println("6) Add a new matching question");
			System.out.println("7) Stop adding questions");
			response = scan.nextLine();
			
			//adds different question type to questions depending on user input
			//also collects answer for each question as they're added and stores them in answers
			switch(response)
			{
			case "1":
				questions.add(new TrueFalse());
				questions.get(questions.size()-1).create();
				correctAnswers.add(questions.get(questions.size()-1).makeAnswer());
				break;
			case "2":
				questions.add(new MultipleChoice());
				questions.get(questions.size()-1).create();
				correctAnswers.add(questions.get(questions.size()-1).makeAnswer());
				break;
			case "3":
				questions.add(new ShortAnswer());
				questions.get(questions.size()-1).create();
				correctAnswers.add(questions.get(questions.size()-1).makeAnswer());
				break;
			case "4":
				questions.add(new Essay());
				questions.get(questions.size()-1).create();
				correctAnswers.add("essay");
				break;
			case "5":
				questions.add(new Ranking());
				questions.get(questions.size()-1).create();
				correctAnswers.add(questions.get(questions.size()-1).makeAnswer());
				break;
			case "6":
				questions.add(new Matching());
				questions.get(questions.size()-1).create();
				correctAnswers.add(questions.get(questions.size()-1).makeAnswer());
				break;
			case "7":
				response = "7";
				break;
			default:
				System.out.println("Invalid Input\n");
				break;
			}
		}
	}
	
	public void display()
	{
		//display name of test
		System.out.println("\n" + name + "\n");
		
		//calls each questions display function
		for(int i = 0; i < questions.size(); i++)
		{
			System.out.print((i+1) + ") ");
			System.out.println(questions.get(i).display());
			System.out.print("\n");
			
			//displays answer to question below question
			System.out.print("Correct Answer:\n" + correctAnswers.get(i) + "\n");
		}
	}
	
	public void modify()
	{
		//variables
		Scanner scan = new Scanner(System.in);
		String response = "-1";
		int index;
		
		//display test
		display();
		
		//ask user for # corresponding to question they wish to modify
		while(response == "-1")
		{
			System.out.println("Enter the question # you wish to modify: ");
			response = scan.nextLine();
			
			//checks user input is valid integer
			if(isInteger(response))
			{
				index = Integer.parseInt(response);
				index--;
				
				//checks user input is within range of questions
				if(index < questions.size() && index >= 0)
				{
					questions.get(index).modify();
					response = "-1";
					
					//prompt user to modify question
					while(response == "-1")
					{
						System.out.println("Do you wish to modify the correct answer? (Y/N)");
						response = scan.nextLine();
						response = response.toLowerCase();
						
						switch(response)
						{
						case "y":
							correctAnswers.remove(index);
							correctAnswers.add(index, questions.get(index).makeAnswer());
							break;
						case "n":
							break;
						default:
							response = "-1";
							break;
						}
					}				
					
					break;
				}
				else
				{
					System.out.println("Invalid Input\n");
					response = "-1";
				}
			}
			else
				System.out.println("Invalid Input\n");
				response = "-1";
		}
	}

	public void take()
	{	
		//output test name
		System.out.println(name + "\n");
		
		//display and collect response for each question on test
		for(int i = 0; i < questions.size(); i++)
		{
			responses.add(questions.get(i).makeAnswer());
			System.out.println();
		}
		
		//finish test, save responses, grade responses
		System.out.println("\nFinished " + name + ".");
		saveResponsesToFile();
		grade();
	}
	
	public void grade()
	{
		//score counts
		int totalScore = 0;
		int userScore = 0;
		
		//for each question compare answer to response
		for(int i = 0; i < questions.size(); i++)
		{
			//do nothing if essay
			if(correctAnswers.get(i).equals("essay"))
			{
				continue;
			} //if correct add 10 to user and total score
			else if(correctAnswers.get(i).equals(responses.get(i)))
			{
				totalScore += 10;
				userScore += 10;
			}
			else //if wrong only add to total score
				totalScore += 10;
		}
		
		System.out.println("Grade: " + userScore + "/" + totalScore + "\n");
	}
	
	public void loadResultFromFile()
	{
		//variables
		Scanner scan = new Scanner(System.in);
		ArrayList<String> files = new ArrayList<String>();
		File dir = new File(".");

		//finds result files for this survey
		for (File file : dir.listFiles())
		{
		    if (file.getName().endsWith((name + ".result")))
		    {
		    	files.add(file.getName());
		    }
		}
		
		//variables needed for user input
		String num;
		int check = -1;
		int count = 0;
		
		//Asks user to select file from list
		while (check == -1)
		{
			//display list of results for current test
			System.out.println("\nChoose a Result: ");
			for(int i = 0; i < files.size(); i++)
			{
				System.out.println( (i+1) + ") " + files.get(i));
			}
			num = scan.nextLine();
			
			//checks if input is valid integer
			while(!isInteger(num))
			{
				System.out.println("Invalid Input.");
				num = scan.nextLine();
			}
		
			count = Integer.parseInt(num);
			
			//if valid integer, checks if within range of list
			if(count > files.size() || count <= 0)
			{
				System.out.println("Invalid Input.");
			}
			else //if valid option, leaves loop
				check = 0;
		}
		
		//load selected responses from file and display grade
		responses = loadResponsesFromFile(files.get(count-1));
		grade();
		responses.clear();
	}
}
