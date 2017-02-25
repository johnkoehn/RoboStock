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
	private int fitnessLevel;
	private float averageReturn;
	
	private ArrayList<Double> netWorth;
	
	public Bot()
	{
		
	}
	
	public void init()
	{
		//used for a fist generation bot :D
		//Here we go!
		momentum = (float) (ThreadLocalRandom.current().nextInt((int)BotSettings.minMomentum * 100, (int)BotSettings.maxMomuntum*100 + 1)/100);
		
		
		percentCashOnHand = ThreadLocalRandom.current().nextInt(BotSettings.minPercentCashOnHand, BotSettings.maxPercentCashOnHand + 1);
		purchaseLot = ThreadLocalRandom.current().nextInt((int)BotSettings.minPurchaseLot, (int)BotSettings.maxPurchaseLot + 1);
		movingAverage = ThreadLocalRandom.current().nextInt(BotSettings.minMovingAverage, BotSettings.maxMovingAverage + 1);
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
}
