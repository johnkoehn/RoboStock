package robo;

import java.util.ArrayList;

public class ThreadSimulator implements Runnable
{
	private Bot bot = new Bot();
	private int currentDay = 0;
	public volatile boolean done = false;
	private ArrayList<Bot> generation;
	String name;
	Manager mang;
	public ThreadSimulator(Manager m, Bot bot, String name) 
	{
		mang = m;
		this.bot = bot;
		this.name = name;
		generation = m.getGeneration();
	}
	
	public void setBot(Bot bot)
	{
		this.bot = bot;
	}
	
	synchronized public void run()
	{
		generation = mang.getGeneration();
		bot.setRunning(true);
//		System.out.println(name + " is running");
		for(currentDay = 0; currentDay < Data.TOTALDAYS; currentDay++)
		{
			bot.setRunning(true);
			bot.newDay(Data.getAllDataForDay(currentDay));
		}
		bot.sellAll();
		done = true;	
		bot.setRunning(false);
		bot.setDone();
//		System.out.println(name +" is done");
		startNewBot();
		
	}
	
	public void startNewBot(){
//		generation = mang.getGeneration();
		boolean run = false;
		synchronized(generation)
		{
			for(int i = 0; i < generation.size(); i++){
				if(generation.get(i).isDone() == false && generation.get(i).isRunning() == false){
					this.setBot(generation.get(i));
					generation.get(i).setRunning(true);
					//System.out.println(name + ": " + i + ", " +  generation.get(i).isRunning() + ", " + generation.get(i).isDone());
					run = true;
					break;
				}
			}
		}
		if(run)
			run();
	}
}
