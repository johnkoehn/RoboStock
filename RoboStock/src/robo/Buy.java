package robo;

/**
 * Each company has a buy with a moving price
 * Class will decide weather bot should make a buy
 * @author jkoehn
 *
 */
public class Buy
{
	float momentumOpening;
	float momentumCloseing;
	float previousOpeningPrice;
	float currentOpeningPrice;
	float currentCloseingPrice;
	float previousCloseingPrice;
	int numOfAvgPoints;
	int dataPoints;
	
	public Buy(int avg)
	{
		numOfAvgPoints = avg;
	}
	
	public float openingPrice(float price)
	{
		if(dataPoints == 0)
		{
			currentOpeningPrice = price;
			return 0;
		}
			
		previousOpeningPrice = currentOpeningPrice;
		currentOpeningPrice = price;
		float change = (currentOpeningPrice - previousOpeningPrice) / previousOpeningPrice * 100;
		if(dataPoints >= numOfAvgPoints)
		{
			momentumOpening = (change + (momentumOpening * numOfAvgPoints - 1)) / numOfAvgPoints;
		}
		else
		{
			momentumOpening = (change + (momentumOpening * dataPoints)) / (dataPoints + 1);
		}
		
		return momentumOpening;
	}
	
	public float closingPrice(float price)
	{
		if(dataPoints == 0)
		{
			currentCloseingPrice = price;
			dataPoints += 1;
			return 0;
		}
			
		previousCloseingPrice = currentCloseingPrice;
		currentCloseingPrice = price;
		float change = (currentCloseingPrice - previousCloseingPrice) / previousCloseingPrice * 100;
		if(dataPoints >= numOfAvgPoints)
		{
			momentumCloseing = (change + (momentumCloseing * numOfAvgPoints - 1)) / numOfAvgPoints;
		}
		else
		{
			momentumCloseing = (change + (momentumCloseing * dataPoints)) / (dataPoints + 1);
		}
		
		dataPoints += 1;
		return momentumCloseing;
	}
	
	public float getOpeningMomentum()
	{
		return momentumOpening;
	}
	
	public float getClosingMomentum()
	{
		return momentumCloseing;
	}
	
}
