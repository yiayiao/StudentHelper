package GetScore;

import java.io.IOException;

public class Test
{
	public static void main(String[] args) throws IOException, Exception
	{
		Login a = new Login("1006840526", "ly05030616");
		if (a.doLogin("1006840526", "ly05030616") == 1)
			a.show();
		else
			System.out.println("’À∫≈√‹¬Î”–ŒÛ");
	}
}