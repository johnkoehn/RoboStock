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
	ArrayList<ArrayList<Day>> companies;
	ArrayList<Day> dayNum;

	Data()
	{
		ArrayList<Day> dayNum = new ArrayList<Day>();

	}

	public void accessData(String filename) throws IOException
	{
		Reader in;
		try
		{
			in = new FileReader(filename);
			
			Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
			
			for (CSVRecord record : records) {
				
			    String columnOne = record.get(0);
			    System.out.println(columnOne);
			}
		}
		catch(FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
