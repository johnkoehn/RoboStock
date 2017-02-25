package robo;

public class Day
{
	protected float open;
	protected float close;
	Day(float open, float close)
	{
		this.open = open;
		this.close = close;
	}
	
	public float getOpen()
	{
		return open;
	}
	
	public float getClose()
	{
		return close;
	}
}
