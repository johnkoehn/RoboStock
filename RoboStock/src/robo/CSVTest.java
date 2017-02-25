package robo;

import java.io.IOException;
import java.util.ArrayList;

public class CSVTest
{

	public static void main(String[] args) throws IOException
	{
		Data test = new Data();
		for(int i = 1; i < 16; i++)
		{
			test.parseData("data/quotes" + i + ".csv");
		}
		ArrayList<Day> values = test.getAllDataForDay(0);
		for(int i = 0; i < values.size(); i++)
		{
			System.out.println(values.get(i));
		}

	}
}