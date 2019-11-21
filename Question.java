import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class Question implements Serializable
{
	String prompt;
	
	//base constructor
	public Question()
	{
		prompt = "";
	}
	
	//creates question
	public void create()
	{
	}
	
	//displays question
	public String display()
	{
		return prompt;
	}
	
	//modifies question
	public void modify()
	{
		modifyPrompt();
	}
	
	//modify prompt of question
	public void modifyPrompt()
	{
		Scanner scan = new Scanner(System.in);
		display();
		System.out.println("Enter the new prompt: ");
		prompt = scan.nextLine();
	}
	
	//modify list of options for question
	public void modifyChoice()
	{
	}
	
	//asks user for answer when working with test
	//default version is for question types that can't have an answer
	public String makeAnswer()
	{
		return "";
	}
	
	public void tabulate(ArrayList<UserResponses> allResults, int index)
	{
	}
	
	//function that checks if a given string can be an integer
	public boolean isInteger(String s)
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
