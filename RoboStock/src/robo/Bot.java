package robo;

import java.util.Random;

public class Bot
{
	private float momentum;
	private float purchaseLot;
	private float movingAverage;
	private float sellPrice;
	private float trailingPrice;
	private int timeLimit;
	private float maximumLoss;
	private float percentCashOnHand;
	private int fitnessLevel;
	
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

	public float getPurchaseLot()
	{
		return purchaseLot;
	}

	public void setPurchaseLot(float purchaseLot)
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

	public float getPercentCashOnHand()
	{
		return percentCashOnHand;
	}

	public void setPercentCashOnHand(float percentCashOnHand)
	{
		this.percentCashOnHand = percentCashOnHand;
	}
	
	public int getFitness()
	{
		return fitnessLevel;
	}
}
