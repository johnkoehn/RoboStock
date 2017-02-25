package Test;

import robo.Bot;
import robo.BotSettings;

public class BotTest
{
	public static void main(String args[])
	{
		
		BotSettings.init("data/botSettings.txt");
		for(int i = 0; i < 1; i++)
		{
			Bot bot = new Bot();
			bot.init();
			System.out.println(bot.getMomentum() + " " + bot.getPurchaseLot() + " " + bot.getSellPrice() );
			System.out.println(bot.getTrailingPrice() + " " + bot.getMaximumLoss() + " " + bot.getPercentCashOnHand());
			System.out.println(bot.getTimeLimit() + " " + bot.getMovingAverage() + " " + bot.getCash() );
		}
	}
}
