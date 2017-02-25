package robo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;


public class Data
{
	final static int TOTALDAYS = 2518;
	static ArrayList<ArrayList<Day>> companies = new ArrayList<ArrayList<Day>>();
	private static ArrayList<Day> dayNum;
	



	public static void parseData(String filename) throws IOException
	{
		Reader in;
		dayNum = new ArrayList<Day>();
		try
		{
			in = new FileReader(filename);
			
			Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
			
			for (CSVRecord record : records) {
				
			    String close = record.get(1);
			    String open = record.get(3);
			    Day today = new Day(Float.parseFloat(open),Float.parseFloat(close));
			    dayNum.add(0, today);
			}
			companies.add(dayNum);
		}
		catch(FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static Day getDay(int company, int dayNumber)
	{
		
		return companies.get(company).get(dayNumber);
	}
	
	public static ArrayList<Day> getAllDataForDay(int dayNumber)
	{
		ArrayList<Day> values = new ArrayList<Day>();
		for(int i = 0; i < companies.size(); i++)
		{
			values.add(companies.get(i).get(dayNumber));
		}
		
		
		return values;
	}

}
