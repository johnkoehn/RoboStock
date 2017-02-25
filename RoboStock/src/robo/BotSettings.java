package robo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

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
	public float minSellPrice;
	public float maxSellPrice;
	public float minTrailingPrice;
	public float maxTrailingPrice;
	public float minMaximumLoss;
	public float maxMaximumLoss;
	public int minPercentCashOnHand;
	public int maxPercentCashOnHand;
	public int minTimeLimit;
	public int maxTimeLimit;
	public int minMovingAverage; //number of days to count in moving average
	public int maxMovingAverage;
	
	//mutations + & -
	public float momentumMutation;
	public float purchaseLotMutation;
	public float sellPriceMutation;
	public float trailingPriceMutation;
	public float maximumLossMutation;
	public float percentCashOnHandMutation;
	public int timeLimitMutation;
	public int movingAverageMutation;
	
	public double startingCash;
	
	public BotSettings(String filename)
	{
		//read in file to memory
		File file = new File(filename);
		try
		{
			Scanner scanner = new Scanner(file);
			
			
			
			scanner.close();
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
}
