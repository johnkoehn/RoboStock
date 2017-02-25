package robo;

import java.util.Random;

public class Bot
{
	private float momentum;
	private int purchaseLot;
	private float movingAverage;
	private float sellPrice;
	private float trailingPrice;
	private int timeLimit;
	private float maximumLoss;
	private int percentCashOnHand;
	
	private double cash;
	
	//performance
	private int fitnessLevel;
	private float averageReturn;
	
	public Bot()
	{
		
	}
	
	public void init(Random random)
	{
		
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

	public float getMovingAverage()
	{
		return movingAverage;
	}

	public void setMovingAverage(float movingAverage)
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
}
