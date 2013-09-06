package GetScore;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex 
{
	private String pattern;
	public Regex(String pattern)
	{
		this.pattern = pattern;
		//System.out.println(pattern);
	}
	public Matcher getMatch(String Text)
	{
		Pattern p = Pattern.compile(pattern);        
        Matcher matcher = p.matcher(Text);	
        matcher.find();
        return matcher;
	}
	public boolean isMatch(String Text)
	{
		Pattern p = Pattern.compile(pattern);        
        Matcher matcher = p.matcher(Text);	
        if(matcher.find())
        	return true;
        return false;
	}
}
