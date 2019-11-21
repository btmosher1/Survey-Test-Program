import java.util.ArrayList;

public class Answers extends UserResponses
{
	ArrayList<String> answers;
	
	public Answers()
	{
		super();
		answers = new ArrayList<String>();
	}
	
	public void add(String s)
	{
		answers.add(s);
	}
	
	public void add(int index, String s)
	{
		answers.add(index, s);
	}
	
	public String get(int index)
	{
		return answers.get(index);
	}
	
	public void remove(int index)
	{
		answers.remove(index);
	}
	
	public int size() 
	{
		return answers.size();
	}
}
