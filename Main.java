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


public class Main implements Serializable
{
	//survey object used throughout menu system
	static Survey survey;
	
	public static void main (String[] args)
	{
		//initialize variables and scanner object
		survey = null;
		Scanner scan = new Scanner(System.in);
		String menu1Response = "";
		String menu2Response = "";
		
		//ask user if working with test or survey, remains until proper input is given
		while(menu1Response == "")
		{
			menu1Response = menu1(scan);
			System.out.print("\n");
			
			if((menu1Response == "Survey") || (menu1Response == "Test"))
			{
				break;
			}
			else
				menu1Response = "";
			
		}
		
		//displays menu options for survey/test, remains until proper input is given or quit is selected
		while(menu2Response != "quit")
		{	
			if(menu1Response == "Survey")
			{
				menu2Response = menuSurvey(scan);
			}
			else
			{
				menu2Response = menuTest(scan);
			}
				
		}
		
		//end of main program
		System.out.println("\nGoodbye!\n");
	}
	
	//first menu options
	public static String menu1(Scanner scan)
	{
		String response = "-1";
		
		while (response == "-1")
		{
			System.out.println("Menu 1:");
			System.out.println("1) Survey\n2) Test");
			response = scan.nextLine();
			
			switch(response)
			{
			case "1":
				response = "Survey";
				break;
			case "2":
				response = "Test";
				break;
			default:
				System.out.println("Invalid Input.");
				break;
			}
		}
		
		return response;
	}
	
	//survey menu options
	public static String menuSurvey(Scanner scan)
	{		
		System.out.println("Menu 2:");
		System.out.println("1) Create a new Survey");
		System.out.println("2) Display a Survey");
		System.out.println("3) Load a Survey");
		System.out.println("4) Save a Survey");
		System.out.println("5) Modify an Existing Survey");
		System.out.println("6) Take a Survey");
		System.out.println("7) Tabulate a Survey");
		System.out.println("8) Quit");
		String response = scan.nextLine();
		
		//calls appropriate function depending on user input
		switch(response)
		{
		case "1":
			survey = new Survey();
			survey.create();
			break;
		case "2":
			if(survey == null)
				System.out.println("No survey selected/created.\n");
			else
				survey.display();
			break;
		case "3":
			survey = load(scan, "Survey");
			break;
		case "4":
			if(survey == null)
				System.out.println("No survey selected.\n");
			else
				save("Survey");
			break;
		case "5":
			survey = load(scan,"Survey");
			survey.modify();
			save("Survey");
			break;
		case "6":
			survey = load(scan,"Survey");
			survey.take();
			break;
		case "7":
			survey = load(scan,"Survey");
			survey.tabulate();
			break;
		case "8":
			response = "quit";
			break;
		default:
			System.out.println("Invalid Input.\n");
			break;
		}
		
		return response;
	}
	
	//test menu options
	public static String menuTest(Scanner scan)
	{
		System.out.println("Menu 2:");
		System.out.println("1) Create a new Test");
		System.out.println("2) Display a Test");
		System.out.println("3) Load a Test");
		System.out.println("4) Save a Test");
		System.out.println("5) Modify an Existing Test");
		System.out.println("6) Take a Test");
		System.out.println("7) Tabulate a Test");
		System.out.println("8) Grade a Test");
		System.out.println("9) Quit");
		String response = scan.nextLine();
		
		//calls appropriate function depending on user input
		switch(response)
		{
		case "1":
			survey = new Test();
			survey.create();
			break;
		case "2":
			if(survey == null)
				System.out.println("No test selected/created.\n");
			else
				survey.display();
			break;
		case "3":
			survey = load(scan, "Test");
			break;
		case "4":
			if(survey == null)
				System.out.println("No test selected/created.\n");
			else
				save("Test");
			break;
		case "5":
			survey = load(scan, "Test");
			survey.modify();
			save("Test");
			break;
		case "6":
			survey = load(scan,"Test");
			survey.take();
			break;
		case "7":
			survey = load(scan,"Test");
			survey.tabulate();
			break;
		case "8":
			survey = load(scan,"Test");
			survey.loadResultFromFile();
			break;
		case "9":
			response = "quit";
			break;
		default:
			System.out.println("Invalid Input.\n");
			break;
		}
		
		return response;
	}
	
	//loads survey/test from file, returns survey object
	public static Survey load(Scanner scan, String type)
	{
		ArrayList<String> files = new ArrayList<String>();
		File dir = new File(".");
		Survey result = null;
		
		//checks if working with surveys or tests
		if(type == "Survey") //displays all .survey files in a list
		{
			for (File file : dir.listFiles())
			{
			    if (file.getName().endsWith((".survey")))
			    {
			    	files.add(file.getName());
			    }
			}
		}
		else //displays all .test files in a list
		{
			for (File file : dir.listFiles())
			{
			    if (file.getName().endsWith((".test")))
			    {
			    	files.add(file.getName());
			    }
			}
		}
		
		if(files.isEmpty()) //if file list is empty
		{
			System.out.println("\nNo avialable " + type + "s to load.\n");
		}
		else
		{
			//user input variable and checks
			String num;
			int check = -1;
			int count = 0;
			
			//Asks user to select file from list
			while (check == -1)
			{
				//display list
				System.out.println("\nChoose a " + type + ": ");
				for(int i = 0; i < files.size(); i++)
				{
					System.out.println( (i+1) + ") " + files.get(i));
				}
				num = scan.nextLine();
				
				//checks if input is valid integer
				while(!isInteger(num))
				{
					//invalid input, redisplays prompt and list
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
				result = (Survey) ois.readObject();
				ois.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
			System.out.println("Loading " + result.name +"...\n");
		}
		return result;
	}
	
	//saves survey/test to file
	public static void save(String type)
	{
		//check if working with test or survey
		if(type == "Survey")
		{
			try
			{
				// write survey to .survey file
				FileOutputStream fos = new FileOutputStream(survey.name + ".survey");
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(survey);
				oos.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else
		{
			try
			{
				// write test to .test file
				FileOutputStream fos = new FileOutputStream(survey.name + ".test");
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(survey);
				oos.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("\nSaved "+ survey.name +".\n");
	}
	
	//checks if input given is an integer
	//checks if given string is a valid integer
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
