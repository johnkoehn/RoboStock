package robo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class ThreadSimulator implements Runnable
{
	private Bot bot = new Bot();
	private int currentDay = 0;
	public volatile boolean done = false;
	private ArrayBlockingQueue<Bot> generation;
	String name;
	Manager mang;
	public ThreadSimulator(ArrayBlockingQueue<Bot> generation) 
	{
		this.generation = generation;
	}

	
	synchronized public void run()
	{
		boolean run = true;
		while(run)
		{
			bot.setRunning(true);
//			System.out.println(name + " is running");
			for(currentDay = 0; currentDay < Data.TOTALDAYS; currentDay++)
			{
				bot.setRunning(true);
				bot.newDay(Data.getAllDataForDay(currentDay));
			}
			bot.sellAll();
			done = true;
			bot.setDone();
			bot.setRunning(false);
			
//			System.out.println(name +" is done");
			run = startNewBot();
		}

		
	}
	
	public boolean startNewBot()
	{
		if(generation.isEmpty() == false)
		{
			try
			{
				bot = generation.take();
				return true;
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		/*for (int i = 0; i < generation.size(); i++)
		{
			if (generation.get(i).isDone() == false && generation.get(i).isRunning() == false)
			{
				if (generation.get(i).setRunning(true))
				{
					bot = generation.get(i);
					// System.out.println(name + ": " + i + ", " +
					// generation.get(i).isRunning() + ", " +
					// generation.get(i).isDone());
					run = true;
					return run;
				}
			}
		}*/
		return false;
	}
}
