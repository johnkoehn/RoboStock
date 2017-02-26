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
	float momentumClosing;
	float previousOpeningPrice;
	float currentOpeningPrice;
	float currentClosingPrice;
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
			currentClosingPrice = price;
			dataPoints += 1;
			return 0;
		}
			
		previousCloseingPrice = currentClosingPrice;
		currentClosingPrice = price;
		float change = (currentClosingPrice - previousCloseingPrice) / previousCloseingPrice * 100;
		if(dataPoints >= numOfAvgPoints)
		{
			momentumClosing = (change + (momentumClosing * numOfAvgPoints - 1)) / numOfAvgPoints;
		}
		else
		{
			momentumClosing = (change + (momentumClosing * dataPoints)) / (dataPoints + 1);
		}
		
		dataPoints += 1;
		return momentumClosing;
	}
	
	public float getOpeningMomentum()
	{
		return momentumOpening;
	}
	
	public float getClosingMomentum()
	{
		return momentumClosing;
	}
	
}
