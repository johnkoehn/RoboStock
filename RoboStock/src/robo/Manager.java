package robo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;

public class Manager
{
	private ArrayList<Bot> currentGeneration = new ArrayList<Bot>();
	private ArrayBlockingQueue<Bot> blockingQueue;
	DataRender dr;
	public Manager()
	{
		
	}
	public void createFirstGeneration() throws IOException
	{
		
		BotSettings.init("data/botSettings.txt");
		for(int i = 1; i < 16; i++)
		{
			Data d = new Data();
			d.parseData("data/quotes" + i + ".csv");
		}
		Bot bot;
		for(int i = 0; i < 100; i++)
		{
			bot = new Bot();
			bot.init();
			currentGeneration.add(bot);
		}
		 dr = new DataRender(currentGeneration);
	}
	
	public ArrayList<Bot> getGeneration(){
		return currentGeneration;
	}
	
	public void startNewGeneration()
	{
		blockingQueue = new ArrayBlockingQueue<Bot>(currentGeneration.size(), false, currentGeneration);
		//create threads
		Thread[] threadArray = new Thread[6];
		for(int i = 0; i < threadArray.length; i++)
		{
			threadArray[i] = new Thread(new ThreadSimulator(blockingQueue));
		}

		for(int i = 0; i < threadArray.length; i++)
		{
			threadArray[i].start();
		}
		
		for(int i = 0; i < threadArray.length; i++)
		{
			try
			{
				threadArray[i].join();
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//put other 90 threads to sleep
//		for(int i = 10; i < 100; i++)
//		{
//			try
//			{
//				threadArray[i].wait();
//			} catch (InterruptedException e)
//			{
//				e.printStackTrace();
//			}
//		}	
	}
	
	public void startReproduction()
	{
		Scanner scanner;
		try
		{
			scanner = new Scanner(new File("data/reproductionSettings.txt"));
			double crossover = scanner.nextDouble();
			double remainderRatio = scanner.nextDouble();
			int elites = scanner.nextInt();
			int scale = scanner.nextInt();
			float hamming = scanner.nextFloat();
			Random rand = new Random();
			
			Reproduction reproduction = new Reproduction(currentGeneration, crossover, remainderRatio, elites, scale, rand, hamming);
			currentGeneration = reproduction.run();
			
			scanner.close();
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		

		
	}
	
	public void createDataRender()
	{
		dr.updateGraphs(currentGeneration);
	}
	
	public static void main(String args[]) throws IOException{
		Manager m = new Manager();
		m.createFirstGeneration();

		for(int i = 0; i < 50; i++)
		{
			m.startNewGeneration();
			m.createDataRender();
			m.startReproduction();
		}

	}
	
	
}
