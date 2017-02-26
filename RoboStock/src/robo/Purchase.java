package robo;

public class Purchase
{
	// constant values
	private int shares;
	private int companyIndex;
	private float initPrice;
	private Bot bot;

	// update values
	private double currentTrailingPrice;
	private double maxGain;
	private int dayCount;
	private float currentPrice;
	private float currentGain;

	public Purchase(int shares, int companyIndex, float initPrice, Bot bot)
	{
		this.shares = shares;
		this.companyIndex = companyIndex;
		this.initPrice = initPrice;
		this.bot = bot;
		currentTrailingPrice = 0;
		maxGain = 0;
		dayCount = 0;
		currentGain = 0;
	}

	public void update(int dayNum, boolean isOpen)
	{
		dayCount++;
		if (isOpen)
			currentPrice = Data.getDay(companyIndex, dayNum).getOpen();
		else
			currentPrice = Data.getDay(companyIndex, dayNum).getClose();

		if (currentPrice > initPrice)
		{
			currentGain = 100 * ((currentPrice - initPrice) / initPrice);
		}
		if (currentGain > maxGain)
		{
			maxGain = currentGain;
			currentTrailingPrice = bot.getTrailingPrice() + maxGain;
		}

	}

	public boolean doISell()
	{
		if (dayCount >= bot.getTimeLimit())
		{
			return true;
		}
		if (currentGain < currentTrailingPrice)
		{
			return true;
		}
		if (100 * ((initPrice - currentPrice) / initPrice) < bot.getMaximumLoss())
		{
			return true;
		}

		return false;
	}

	public double getPurchasingPrice()
	{
		return initPrice;
	}
	
	public int getShares()
	{
		return shares;
	}
	
	public double getCurrentGain(){
		return currentGain;
	}
	
	public float getCurrentPrice()
	{
		return currentPrice;
	}
}
