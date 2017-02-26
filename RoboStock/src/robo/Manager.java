package robo;

import java.util.ArrayList;

public class Manager
{
	private ArrayList<Bot> currentGeneration = new ArrayList<Bot>();
	private int generationNumber = 0;
	
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
		//start first 10 threads
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
	
	public static void main(String args[]){
		Manager m = new Manager();
		m.createFirstGeneration();
		m.startNewGeneration();
	}
	
	
}
