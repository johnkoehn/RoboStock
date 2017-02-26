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
	public static float minMomentum;
	public static float maxMomentum;
	public static float minPurchaseLot;
	public static float maxPurchaseLot;
	public static float minSellPrice;
	public static float maxSellPrice;
	public static float minTrailingPrice;
	public static float maxTrailingPrice;
	public static float minMaximumLoss; //this is the most negative (English ^.^)
	public static float maxMaximumLoss;
	public static int minPercentCashOnHand;
	public static int maxPercentCashOnHand;
	public static int minTimeLimit;
	public static int maxTimeLimit;
	public static int minMovingAverage; //number of days to count in moving average
	public static int maxMovingAverage;
	
	//mutations + & -
	public static float momentumMutation;
	public static float purchaseLotMutation;
	public static float sellPriceMutation;
	public static float trailingPriceMutation;
	public static float maximumLossMutation;
	public static int percentCashOnHandMutation;
	public static int timeLimitMutation;
	public static int movingAverageMutation;
	
	public static double startingCash;
	
	public static void init(String filename)
	{
		//read in file to memory
		File file = new File(filename);
		try
		{
			Scanner scanner = new Scanner(file);
			//scanner.useDelimiter(",");
			
			//bad programming alert... assume file good :)
			minMomentum = scanner.nextFloat();
			maxMomentum = scanner.nextFloat();
			minPurchaseLot = scanner.nextFloat();
			maxPurchaseLot = scanner.nextFloat();
			minSellPrice = scanner.nextFloat();
			maxSellPrice = scanner.nextFloat();
			minTrailingPrice = scanner.nextFloat();
			maxTrailingPrice = scanner.nextFloat();
			minMaximumLoss = scanner.nextFloat();
			maxMaximumLoss = scanner.nextFloat();
			
			minPercentCashOnHand = scanner.nextInt();
			maxPercentCashOnHand = scanner.nextInt();
			minTimeLimit = scanner.nextInt();
			maxTimeLimit = scanner.nextInt();
			minMovingAverage = scanner.nextInt();
			maxMovingAverage = scanner.nextInt();
			
			//mutations
			momentumMutation = scanner.nextFloat();
			purchaseLotMutation = scanner.nextFloat();
			sellPriceMutation = scanner.nextFloat();
			trailingPriceMutation = scanner.nextFloat();
			maximumLossMutation = scanner.nextFloat();
			
			percentCashOnHandMutation = scanner.nextInt();
			timeLimitMutation = scanner.nextInt();
			movingAverageMutation = scanner.nextInt();
			
			startingCash = scanner.nextDouble();
			
			scanner.close();
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
}
