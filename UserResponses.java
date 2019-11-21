import java.io.Serializable;
import java.util.ArrayList;

public class UserResponses implements Serializable
{
	ArrayList<String> responses;
	
	public UserResponses()
	{
		responses = new ArrayList<String>();
	}
	
	public void add(String s)
	{
		responses.add(s);
	}
	
	public void add(int index, String s)
	{
		responses.add(index, s);
	}
	
	public String get(int index)
	{
		return responses.get(index);
	}
	
	public void remove(int index)
	{
		responses.remove(index);
	}

	public int size() 
	{
		return responses.size();
	}
	
	public void clear()
	{
		responses.clear();
	}
}
