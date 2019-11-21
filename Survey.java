import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class Survey implements Serializable
{
	ArrayList<Question> questions;
	UserResponses responses;
	String name;
	
	public Survey()
	{
		questions = new ArrayList<Question>();
		responses = new UserResponses();
	}
	
	public void create()
	{
		Scanner scan = new Scanner(System.in);
		String response = "";
		
		//get name of survey, used as file name also
		System.out.println("Enter name of new survey: ");
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
			switch(response)
			{
			case "1":
				questions.add(new TrueFalse());
				questions.get(questions.size()-1).create();
				break;
			case "2":
				questions.add(new MultipleChoice());
				questions.get(questions.size()-1).create();
				break;
			case "3":
				questions.add(new ShortAnswer());
				questions.get(questions.size()-1).create();
				break;
			case "4":
				questions.add(new Essay());
				questions.get(questions.size()-1).create();
				break;
			case "5":
				questions.add(new Ranking());
				questions.get(questions.size()-1).create();
				break;
			case "6":
				questions.add(new Matching());
				questions.get(questions.size()-1).create();
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
		//display name of survey
		System.out.println("\n" + name + "\n");
		
		//calls each questions display function
		for(int i = 0; i < questions.size(); i++)
		{
			System.out.print((i+1) + ") ");
			System.out.println(questions.get(i).display());
			System.out.print("\n");
		}
	}
	
	public void modify()
	{
		//variables
		Scanner scan = new Scanner(System.in);
		String response = "-1";
		int index;
		
		//displays survey to be modified
		display();
		
		//asks user for # corresponding to question they wish to modify
		while(response == "-1")
		{
			System.out.println("Enter the question # you wish to modify: ");
			response = scan.nextLine();
			
			//checks if response given is a valid integer
			if(isInteger(response))
			{
				index = Integer.parseInt(response);
				index--;
				
				//calls modify function for question specified
				if(index < questions.size() && index >= 0)
				{
					questions.get(index).modify();
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
		//outputs name of survey
		System.out.println(name + "\n");
		
		//displays each question and collects responses
		for(int i = 0; i < questions.size(); i++)
		{
			responses.add(questions.get(i).makeAnswer());
			System.out.println();
		}
		
		System.out.println("\nFinished " + name + ".\n");
		saveResponsesToFile();
	}
	
	public void tabulate()
	{
		//variables
		ArrayList<UserResponses> allResults = new ArrayList<UserResponses>();
		ArrayList<String> files = new ArrayList<String>();
		File dir = new File(".");
		
		System.out.println(name + " Tabulation: \n");
		
		//stores all file names for current survey
		for (File file : dir.listFiles())
		{
		    if (file.getName().endsWith((name+".result")))
		    {
		    	files.add(file.getName());
		    }
		}
		
		//load survey result files
		for(int i = 0; i < files.size(); i++)
		{
			allResults.add(loadResponsesFromFile(files.get(i)));
		}
		
		//call tabulate function for each question
		for(int i = 0; i < questions.size(); i++)
		{
			questions.get(i).tabulate(allResults, i);
			System.out.println();
		}
	}
	
	public void saveResponsesToFile()
	{
		//variables
		ArrayList<String> files = new ArrayList<String>();
		File dir = new File(".");
		
		//find all result files for current test
		for (File file : dir.listFiles())
		{
		    if (file.getName().endsWith((name+".result")))
		    {
		    	files.add(file.getName());
		    }
		}
		
		//save current survey responses to file
		try
		{
			FileOutputStream fos = new FileOutputStream(files.size() + name + ".result");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(responses);
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public UserResponses loadResponsesFromFile(String file)
	{
		//return given file name as UserResponse Object
		UserResponses result = new UserResponses();
		
		try {
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			result = (UserResponses) ois.readObject();
			ois.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void loadResultFromFile()
	{
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
		
		String num;
		int check = -1;
		int count = 0;
		
		//Asks user to select file from list
		while (check == -1)
		{
			//display list
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

		
		try {
			// read test/survey that was selected from its file
			FileInputStream fis = new FileInputStream(files.get(count-1));
			ObjectInputStream ois = new ObjectInputStream(fis);
			responses = (UserResponses) ois.readObject();
			ois.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	//checks if input given is an integer

	public static boolean isInteger(String s)
	{
	    if(s.isEmpty())
	    	return false;
	    
	    for(int i = 0; i < s.length(); i++) 
	    {
	    	if(Character.isDigit(s.charAt(i)))
	    		continue;
	    	else
	    		return false;
	    }
	    
	    return true;
	}
}
