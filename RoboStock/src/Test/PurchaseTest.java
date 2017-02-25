package Test;

import java.io.IOException;

import robo.Bot;
import robo.BotSettings;
import robo.Data;
import robo.Purchase;

public class PurchaseTest
{
	public static void main(String[] args) throws IOException
	{
		BotSettings.init("data/botSettings.txt");
		Data.parseData("data/quotes1.csv");
		Bot b=new Bot();
		b.init();
		Purchase p=new Purchase(10, 0, (float)6.00, b);
		p.update(0, true);
		p.update(0, false);
		p.update(1, true);
		p.update(1, false);
	}
}
