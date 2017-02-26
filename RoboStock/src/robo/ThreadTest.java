package robo;

import java.util.ArrayList;

public class ThreadTest
{
	public Object test = new Object();
	public volatile static boolean lock = false;
	public ArrayList<Bot> bots;
	
	public ThreadTest(ArrayList<Bot> bots)
	{
		this.bots = bots;
	}
}
