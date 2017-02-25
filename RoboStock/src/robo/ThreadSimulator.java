package robo;

public class ThreadSimulator implements Runnable
{
	private Bot bot = new Bot();
	private int currentDay = 0;
	public volatile boolean done = false;
	public ThreadSimulator(Bot bot) 
	{
		this.bot = bot;
	}
	
	public void setBot(Bot bot)
	{
		this.bot = bot;
	}
	
	synchronized public void run()
	{
		for(currentDay = 0; currentDay < Data.TOTALDAYS; currentDay++)
		{
			bot.newDay(Data.getAllDataForDay(currentDay));
		}
		done = true;
		notify();
	}
	

}
