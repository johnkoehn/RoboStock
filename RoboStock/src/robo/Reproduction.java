package robo;

import java.util.ArrayList;

public class Reproduction
{
	private ArrayList<Bot> bots;
	private double crossoverRatio;
	private double remainderRatio;
	private int eliteNumber;
	private int fitnessScaleTo;

	/**
	 * 
	 * @param bots
	 *            : generation that may get to be parents
	 * @param crossoverRatio:
	 *            value from 0-1 that determines ratio of crossover/mutation
	 *            parents (1 is all crossover)
	 * @param remainderRatio:
	 *            value from 0-1 that determines ratio of remainder/stochastic
	 *            children(1 is all remainder)
	 * @param eliteNumber
	 *            : number of elites to save until the next round
	 * @param fitnessScaleTo:
	 *            the maximum scaled value used for Remainder selection
	 */
	public Reproduction(ArrayList<Bot> bots, double crossoverRatio, double remainderRatio, int eliteNumber,
			int fitnessScaleTo)
	{
		this.bots = bots;
		this.crossoverRatio = crossoverRatio;
		this.remainderRatio = remainderRatio;
		this.eliteNumber = eliteNumber;
		this.fitnessScaleTo = fitnessScaleTo;
	}

	public ArrayList<Bot> run()
	{
		// select and categorize parents
		ArrayList<Bot> parents = new ArrayList<Bot>();
		selectParents(parents);
		ArrayList<Bot> mutators = new ArrayList<Bot>();
		ArrayList<Bot> crossovers = new ArrayList<Bot>();
		ArrayList<Bot> elites = new ArrayList<Bot>();
		categorizeParents(parents, mutators, crossovers, elites);

		// Reproduce
		ArrayList<Bot> children = new ArrayList<Bot>();
		elitify(elites, children);
		crossover(crossovers, children);
		mutate(mutators, children);

		return children;
	}

	private void selectParents(ArrayList<Bot> parents)
	{
		// step 1: remainder selection
		int timesIsParent = 0;
		for (int i = 0; i < bots.size(); i++)
		{
			// scale it down to 0-fitnessScaleTo, then it truncates the
			// remainder
			timesIsParent = (int) (bots.get(i).getFitness() / (100.0 / fitnessScaleTo));
			for (int j = 0; j < timesIsParent; j++)
			{
				parents.add(bots.get(i));
			}
		}

		// step 2: stochastic selection
		int numOfStochasticParents = (int) (parents.size() / remainderRatio);

		double totalStochasticVal = 0.0;
		ArrayList<Double> stochasticVals = new ArrayList<Double>();
		// create the stochastic distribution of bots
		for (int i = 0; i < bots.size(); i++)
		{
			// scale it down to 0-fitnessScaleTo then take the floating point
			// remainder and save it in your range
			stochasticVals.add((bots.get(i).getFitness() / (100.0 / fitnessScaleTo))
					- (int) (bots.get(i).getFitness() / (100.0 / fitnessScaleTo)));
			totalStochasticVal += stochasticVals.get(i);
		}

		// go over
		double jumpValue = totalStochasticVal / numOfStochasticParents;
		double remainsInJump = jumpValue;
		int index = 0;
		for (int i = 0; i < numOfStochasticParents; i++)
		{
			if (stochasticVals.get(index) < remainsInJump)// jumped over the end of this bot's range
			{
				remainsInJump = jumpValue - stochasticVals.get(index);
				index++;
			} 
			else// landed in the range of the bot at index's range
			{
				parents.add(bots.get(index));
				remainsInJump = jumpValue;
			}
		}

	}

	private void categorizeParents(ArrayList<Bot> parents, ArrayList<Bot> mutators, ArrayList<Bot> crossovers,
			ArrayList<Bot> elites)
	{

	}

	private void elitify(ArrayList<Bot> elites, ArrayList<Bot> children)
	{

	}

	private void crossover(ArrayList<Bot> crossovers, ArrayList<Bot> children)
	{

	}

	private void mutate(ArrayList<Bot> mutators, ArrayList<Bot> children)
	{

	}
}
