package robo;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Manager
{
	private ArrayList<Bot> currentGeneration = new ArrayList<Bot>();
	
	public Manager()
	{
		
	}
	public void createFirstGeneration()
	{
		BotSettings.init("data/botSettings.txt");
		Bot bot;
		for(int i = 0; i < 100; i++)
		{
			bot = new Bot();
			bot.init();
			currentGeneration.add(bot);
		}
	}
	
	public ArrayList<Bot> getGeneration(){
		return currentGeneration;
	}
	
	public void startNewGeneration()
	{
		Manager m = new Manager();
		//create threads
		Thread[] threadArray = new Thread[15];
		for(int i = 0; i < threadArray.length; i++)
		{
			threadArray[i] = new Thread(new ThreadSimulator(this, currentGeneration.get(i), "Thread " + i));
		}

		for(int i = 0; i < threadArray.length; i++)
		{
			threadArray[i].start();
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
		Scanner scanner = new Scanner("data/reproductionSettings.txt");
		double crossover = scanner.nextDouble();
		double remainderRatio = scanner.nextDouble();
		int elites = scanner.nextInt();
		int scale = scanner.nextInt();
		Random rand = new Random();
		
		scanner.close();	
		
		Reproduction reproduction = new Reproduction(currentGeneration, crossover, remainderRatio, elites, scale, rand);
		currentGeneration = reproduction.run();
		
	}
	
	public void createDataRender()
	{
		DataRender dr = new DataRender();
		dr.updateGraphs(currentGeneration);
	}
	
	public static void main(String args[]){
		Manager m = new Manager();
		m.createFirstGeneration();
		m.startNewGeneration();
		m.createDataRender();
		m.startReproduction();
	}
	
	
}
