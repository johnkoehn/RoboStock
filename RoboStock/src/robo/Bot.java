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
	private float netWorth;
	private float winsToLoses; //low
	private float avgWorth; //low
	
	//trading
	private ArrayList<Buy> buys;
	
	//other
	private boolean done = false;
	private ArrayList<Float> dailyWorth;
	
	public Bot()
	{
		buys = new ArrayList<Buy>();
	}
	
	
	public void init()
	{
		//used for a fist generation bot :D
		//Here we go!
		momentum = (float) (ThreadLocalRandom.current().nextInt((int)(BotSettings.minMomentum * 100), (int)(BotSettings.maxMomentum*100 + 1))/100.0);
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
	
	public Bot mutuate(Random r)
	{
		//create a bot with new genes
		Bot bot = new Bot();
		
		//momentum
		if(r.nextBoolean())
		{
			float modifier = (float) (ThreadLocalRandom.current().nextInt(-(int)(BotSettings.momentumMutation * 100), (int)(BotSettings.momentumMutation*100 + 1))/100.0);
			bot.setMomentum(calcMutation(modifier, momentum, BotSettings.minMomentum, BotSettings.maxMomentum));
		}
		else
			bot.setMomentum(momentum);
		
		if(r.nextBoolean())
		{
			float modifier = (float) (ThreadLocalRandom.current().nextInt(-(int)BotSettings.sellPriceMutation, (int)(BotSettings.sellPriceMutation + 1))/100.0);
			bot.setSellPrice((calcMutation(modifier, sellPrice, BotSettings.minSellPrice, BotSettings.maxSellPrice)));
		}
		else
			bot.setSellPrice(sellPrice);
		
		if(r.nextBoolean())
		{
			float modifier = (float) (ThreadLocalRandom.current().nextInt(-(int)(BotSettings.trailingPriceMutation * 100),(int)((BotSettings.trailingPriceMutation*100 + 1)))/100.0);
			bot.setTrailingPrice(calcMutation(modifier, trailingPrice, BotSettings.minTrailingPrice, BotSettings.maxTrailingPrice));
		}
		else
			bot.setTrailingPrice(trailingPrice);
		
		if(r.nextBoolean())
		{
			float modifier = (float)(ThreadLocalRandom.current().nextInt(-(int)(BotSettings.maximumLossMutation * 100), (int)(BotSettings.maximumLossMutation*100 + 1))/100.0);
			bot.setMaximumLoss(calcMutation(modifier, maximumLoss, BotSettings.minMaximumLoss, BotSettings.maxMaximumLoss));
		}
		else
			bot.setMaximumLoss(maximumLoss);
		
		if(r.nextBoolean())
		{
			int modifier = ThreadLocalRandom.current().nextInt(-BotSettings.percentCashOnHandMutation, BotSettings.percentCashOnHandMutation + 1);
			bot.setPercentCashOnHand(calcMutation(modifier, percentCashOnHand, BotSettings.minPercentCashOnHand, BotSettings.maxPercentCashOnHand));
		}
		else
			bot.setPercentCashOnHand(percentCashOnHand);
		
		if(r.nextBoolean())
		{
			int modifier = ThreadLocalRandom.current().nextInt(-BotSettings.timeLimitMutation, BotSettings.timeLimitMutation + 1);
			bot.setTimeLimit(calcMutation(modifier, timeLimit, BotSettings.minTimeLimit, BotSettings.maxTimeLimit));
		}
		else
			bot.setTimeLimit(timeLimit);
		
		if(r.nextBoolean())
		{
			int modifier = ThreadLocalRandom.current().nextInt(-BotSettings.movingAverageMutation, BotSettings.movingAverageMutation);
			bot.setMovingAverage(calcMutation(modifier, movingAverage, BotSettings.minMovingAverage, BotSettings.maxMovingAverage));
		}
		else
			bot.setMovingAverage(movingAverage);
			
		//fucked up case
		float modifier = 0;
		if(r.nextBoolean())
			modifier = ThreadLocalRandom.current().nextInt(-(int)BotSettings.purchaseLotMutation, (int)BotSettings.purchaseLotMutation + 1);
		int newPurchaseLot = purchaseLot + (int)modifier;	
		if(newPurchaseLot > BotSettings.maxPurchaseLot)
			bot.setPurchaseLot((int)BotSettings.maxPurchaseLot);
		else if(newPurchaseLot < BotSettings.minPurchaseLot)
			bot.setPurchaseLot((int)BotSettings.minPurchaseLot);
		else
			bot.setPurchaseLot(newPurchaseLot);
		
		return bot;
	}
	
	private float calcMutation(float modifier, float currentValue, float min, float max)
	{
		float newValue = modifier + currentValue;
		if(newValue > max)
			return max;
		else if(newValue < min)
			return min;
		else
			return newValue;
	}
	
	private int calcMutation(int modifier, int currentValue, int min, int max)
	{
		int newValue = modifier + currentValue;
		if(newValue > max)
			return max;
		else if(newValue < min)
			return min;
		else
			return newValue;
	}
	
	public void setDone()
	{
		done = true;
	}
	
	public boolean isDone()
	{
		return done;
	}
	
	public void newDay(ArrayList<Day> info)
	{
		
	}
}
