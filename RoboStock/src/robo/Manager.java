package robo;

import java.util.ArrayList;

public class Manager
{
	private ArrayList<Bot> currentGeneration = new ArrayList<Bot>();
	private int generationNumber = 0;
	
	
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
	
	public void startNewGeneration()
	{
		//create threads
		Thread[] threadArray = new Thread[100];
		for(int i = 0; i < 100; i++)
		{
			threadArray[i] = new  Thread(new ThreadSimulator(currentGeneration.get(i)));
		}
		//start first 10 threads
		for(int i = 0; i < 10; i++)
		{
			threadArray[i].start();
		}
		//put other 90 threads to sleep
		for(int i = 10; i < 100; i++)
		{
			try
			{
				threadArray[i].wait();
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}	
	}
}
