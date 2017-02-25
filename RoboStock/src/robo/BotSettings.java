package robo;

import java.io.File;

/**
 * Gets the settings for each Bot
 * @author John Koehn
 *
 */
public class BotSettings
{
	//ranges for each
	public float minMomentum;
	public float maxMomuntum;
	public float minPurchaseLot;
	public float maxPurchaseLot;
	public float minMovingAverage;
	public float maxMovingAverage;
	public float minSellPrice;
	public float maxSellPrice;
	public float minTrailingPrice;
	public float maxTrailingPrice;
	public float minMaximumLoss;
	public float maxMaximumLoss;
	public float minPercentCashOnHand;
	public float maxPercentCashOnHand;
	public int minTimeLimit;
	public int maxTimeLimit;
	
	//mutations + & -
	public float momentumMutation;
	public float purchaseLotMutation;
	public float movingAverageMutation;
	public float sellPriceMutation;
	public float trailingPriceMutation;
	public float maximumLossMutation;
	public float percentCashOnHandMutation;
	public int timeLimitMutation;
	
	public double startingCash;
	
	public BotSettings(String filename)
	{
		//read in file to memory
		File file = new File(filename);
	}
	
	
	
	
}
