package robo;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Bot
{
	private float momentum;
	private float sellPrice;
	private float trailingPrice;
	private float maximumLoss;
	private int percentCashOnHand;
	private int purchaseLot;
	private int timeLimit;
	private int movingAverage;
	
	private double cash;
	
	//performance
	public int fitnessLevel;
	private float averageReturn;
	private ArrayList<Double> netWorth;
	
	//other
	private volatile boolean done = false;
	private volatile boolean running = false;
	
	public Bot()
	{
		
	}
	
	public void init()
	{
		//used for a fist generation bot :D
		//Here we go!
		momentum = (float) (ThreadLocalRandom.current().nextInt((int)(BotSettings.minMomentum * 100), (int)(BotSettings.maxMomuntum*100 + 1))/100.0);
		sellPrice = (float) (ThreadLocalRandom.current().nextInt((int)(BotSettings.minSellPrice * 100), (int)(BotSettings.maxSellPrice*100 + 1))/100.0);
		trailingPrice = (float) (ThreadLocalRandom.current().nextInt((int)(BotSettings.minTrailingPrice * 100),(int)((BotSettings.maxTrailingPrice*100 + 1)))/100.0);
		maximumLoss = (float)(ThreadLocalRandom.current().nextInt((int)(BotSettings.minMaximumLoss * 100), (int)(BotSettings.maxMaximumLoss*100 + 1))/100.0);
				
		percentCashOnHand = ThreadLocalRandom.current().nextInt(BotSettings.minPercentCashOnHand, BotSettings.maxPercentCashOnHand + 1);
		timeLimit = ThreadLocalRandom.current().nextInt(BotSettings.minTimeLimit, BotSettings.maxTimeLimit + 1);
		movingAverage = ThreadLocalRandom.current().nextInt(BotSettings.minMovingAverage, BotSettings.maxMovingAverage + 1);
		
		purchaseLot = ThreadLocalRandom.current().nextInt((int)BotSettings.minPurchaseLot, (int)BotSettings.maxPurchaseLot + 1);
		
		cash = BotSettings.startingCash;
	}

	public float getMomentum()
	{
		return momentum;
	}

	public void setMomentum(float momentum)
	{
		this.momentum = momentum;
	}

	public int getPurchaseLot()
	{
		return purchaseLot;
	}

	public void setPurchaseLot(int purchaseLot)
	{
		this.purchaseLot = purchaseLot;
	}

	public int getMovingAverage()
	{
		return movingAverage;
	}

	public void setMovingAverage(int movingAverage)
	{
		this.movingAverage = movingAverage;
	}

	public float getSellPrice()
	{
		return sellPrice;
	}

	public void setSellPrice(float sellPrice)
	{
		this.sellPrice = sellPrice;
	}

	public float getTrailingPrice()
	{
		return trailingPrice;
	}

	public void setTrailingPrice(float trailingPrice)
	{
		this.trailingPrice = trailingPrice;
	}

	public int getTimeLimit()
	{
		return timeLimit;
	}

	public void setTimeLimit(int timeLimit)
	{
		this.timeLimit = timeLimit;
	}

	public float getMaximumLoss()
	{
		return maximumLoss;
	}

	public void setMaximumLoss(float maximumLoss)
	{
		this.maximumLoss = maximumLoss;
	}

	public int getPercentCashOnHand()
	{
		return percentCashOnHand;
	}

	public void setPercentCashOnHand(int percentCashOnHand)
	{
		this.percentCashOnHand = percentCashOnHand;
	}
	
	public int getFitness()
	{
		return fitnessLevel;
	}
	
	public double getCash()
	{
		return cash;
	}
	
	public float getAverageReturn()
	{
		return averageReturn;
	}
	
	public Bot mutuate()
	{
		return new Bot();
	}
	
	public ArrayList<Double> getNetWorth()
	{
		return netWorth;
	}
	
	public void setDone()
	{
		done = true;
	}
	
	public boolean isDone()
	{
		return done;
	}
	
	public void setRunning(boolean b)
	{
		running = b;
	}
	
	public boolean isRunning()
	{
		return running;
	}
	public void newDay(ArrayList<Day> info)
	{
		
	}
}
