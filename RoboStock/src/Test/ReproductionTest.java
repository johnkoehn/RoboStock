package Test;

import java.util.ArrayList;
import java.util.Random;

import robo.Bot;
import robo.BotSettings;
import robo.Reproduction;

public class ReproductionTest
{
	public static void main(String[] args)
	{
		Random r=new Random();
		BotSettings.init("data/botSettings.txt");
		ArrayList<Bot> bots=new ArrayList<Bot>();
		for(int i=0;i<100;i++)
		{
			bots.add(new Bot());
			(bots.get(i)).init();
			bots.get(i).fitnessLevel=r.nextInt(100)+1;
		}
		double crossoverRatio=.80;
		double remainderRatio=.75;
		int eliteNumber=5;
		int fitnessScaleTo=4;
		Reproduction rep=new Reproduction(bots, crossoverRatio, remainderRatio, eliteNumber, fitnessScaleTo,r);
		ArrayList<Bot> children=rep.run();
	}
}
