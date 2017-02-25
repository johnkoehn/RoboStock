package robo;

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
	private double cash;
	
	public Bot()
	{
		
	}

	public float getMomentum()
	{
		return momentum;
	}

	public float getPurchaseLot()
	{
		return purchaseLot;
	}


	public float getMovingAverage()
	{
		return movingAverage;
	}

	public float getSellPrice()
	{
		return sellPrice;
	}

	public float getTrailingPrice()
	{
		return trailingPrice;
	}

	public int getTimeLimit()
	{
		return timeLimit;
	}

	public float getMaximumLoss()
	{
		return maximumLoss;
	}

	public float getPercentCashOnHand()
	{
		return percentCashOnHand;
	}
	
	public int getFitness()
	{
		return fitnessLevel;
	}
	
	public double getCash()
	{
		return cash;
	}
}
