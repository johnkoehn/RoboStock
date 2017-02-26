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
	
	private float cash;
	
	//performance
	private float averageReturn;
	private float netWorth;
	private int wins;
	private int losses;
	private float avgWorth; //low
	
	//trading
	private ArrayList<Buy> buys;
	private ArrayList<Purchase> purchases;
	private int dayNum = 0;
	private int numOfClosedTrades;
	private int currentCompany = 0;
	
	//other
	private volatile boolean done = false;
	private volatile boolean running = false;
	private ArrayList<Float> dailyWorth;
	
	public Bot()
	{
		buys = new ArrayList<Buy>();
		purchases = new ArrayList<Purchase>();
		dailyWorth = new ArrayList<Float>();
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
		
		cash = (float)BotSettings.startingCash;
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
		if(numOfClosedTrades<8)
			return 0;
		double netWorthWeight=.50;
		double avgReturnWeight=.20;
		double winLossWeight=.20;
		double avgWorthWeight=.20;
		
		double winLossRatio=wins/losses;
		double netWorthRatio=netWorth/600000;
		double avgReturnRatio=averageReturn/2.5;
		double avgWorthRatio=avgWorth/375000;
		
		return (int)(100*(netWorthWeight*netWorthRatio + winLossWeight*winLossRatio + avgReturnWeight*avgReturnRatio + avgWorthWeight*avgWorthRatio));
	}
	
	public double getCash()
	{
		return cash;
	}
	
	public float getAverageReturn()
	{
		return averageReturn;
	}
	
	public Bot mutate(Random r)
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
		if(buys.isEmpty())
		{
			for(Day day : info)
			{
				Buy buy = new Buy(movingAverage);
				buy.openingPrice(day.open);
				buy.closingPrice(day.close);
				buys.add(buy);
			}
			netWorth = cash;
		}
		else
		{
			//update and sell for morning
			float stockValue = 0;
			for(int i = 0; i < purchases.size(); i++)
			{
				purchases.get(i).update(dayNum, true);
				if(purchases.get(i).doISell())
				{
					makeSell(purchases.get(i));
					purchases.remove(i);
					i -= 1;
				}
				else
				{
					stockValue += purchases.get(i).getValue();
				}
			}
			netWorth = cash + stockValue;
			
			//update buys for morning
			//System.out.println(info.size());
			for(int i = 0; i < info.size(); i++)
			{
				buys.get(i).openingPrice(info.get(i).getOpen());				
			}
			
			//if possible, make purchases
			if(cash >= (netWorth * (percentCashOnHand / 100.0)))
			{
				//we can buy
				for(int i = currentCompany; i < buys.size(); i++)
				{
					//if possible, make purchases
					if(cash >= (netWorth * (percentCashOnHand/100.0)))
					{
						attemptBuy(buys.get(i).currentOpeningPrice, i, buys.get(i).momentumOpening);
					}
					else
					{
						currentCompany = i;
						break;
					}
				}
				
				//do the companies missed
				for(int i = 0; i < currentCompany; i++)
				{
					//if possible, make purchases
					if(cash >= (netWorth * (percentCashOnHand/100.0)))
					{
						attemptBuy(buys.get(i).currentOpeningPrice, i, buys.get(i).momentumOpening);
					}
					else
					{
						currentCompany = i;
						break;
					}
				}
			}
			
			//update and sell for evening
			stockValue = 0;
			for(int i = 0; i < purchases.size(); i++)
			{
				purchases.get(i).update(dayNum, false);
				if(purchases.get(i).doISell())
				{
					makeSell(purchases.get(i));
					purchases.remove(i);
					i -= 1;
				}
				else
				{
					stockValue += purchases.get(i).getValue();
				}
			}
			netWorth = stockValue + cash;
			
			//update and buy for evening
			for(int i = 0; i < info.size(); i++)
			{
				buys.get(i).closingPrice(info.get(i).getClose());				
			}
			
			//if possible, make purchases
			if(cash >= (netWorth * (percentCashOnHand / 100.0)))
			{
				//we can buy
				for(int i = currentCompany; i < buys.size(); i++)
				{
					//if possible, make purchases
					if(cash >= (netWorth * (percentCashOnHand/100.0)))
					{
						attemptBuy(buys.get(i).currentClosingPrice, i, buys.get(i).momentumClosing);
					}
					else
					{
						currentCompany = i;
						break;
					}
				}
				
				//do the companies missed
				for(int i = 0; i < currentCompany; i++)
				{
					//if possible, make purchases
					if(cash >= (netWorth * (percentCashOnHand/100.0)))
					{
						attemptBuy(buys.get(i).currentOpeningPrice, i, buys.get(i).momentumOpening);
					}
					else
					{
						currentCompany = i;
						break;
					}
				}
			}
		}
		dayNum += 1;
		dailyWorth.add(new Float(netWorth));
	}
	
	private void attemptBuy(float price, int companyIndex, float stockMomentum)
	{
		//System.out.println(stockMomentum);
		if(stockMomentum >= momentum)
		{
			//buy some shares
			float cashToSpend = (float) (cash * (purchaseLot / 100.0));
			int numOfShares = (int) (cashToSpend / price);
			cash -= (numOfShares * price);
			purchases.add(new Purchase(numOfShares, companyIndex, price, this));
		}
	}
	
	private void makeSell(Purchase purchase)
	{
		numOfClosedTrades += 1;
		float gain = (float)((purchase.getCurrentPrice() - purchase.getPurchasingPrice()) / purchase.getPurchasingPrice() * 100);
		
		cash += purchase.getShares() * purchase.getCurrentPrice();
		if(gain > 0)
			wins += 1;
		else 
			losses += 1;
		
		averageReturn = ((averageReturn * (numOfClosedTrades - 1)) + gain) / numOfClosedTrades;
		
	}
	
	public void sellAll()
	{
		for(int i = 0; i < purchases.size(); i++)
		{
			
			makeSell(purchases.get(i));
			purchases.remove(i);
			i -= 1;
		}
		System.out.println(cash);
	}
}
