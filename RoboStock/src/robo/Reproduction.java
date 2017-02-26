package robo;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Random;

public class Reproduction
{
	private ArrayList<Bot> bots;
	private double crossoverRatio;
	private double remainderRatio;
	private int eliteNumber;
	private int fitnessScaleTo;
	private Random r;
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
	public Reproduction(ArrayList<Bot> bot, double crossoverRatio, double remainderRatio, int eliteNumber,
			int fitnessScaleTo, Random r)
	{
		bots=new ArrayList<Bot>();
		for (int i = 0; i < bot.size(); i++)
		{
			this.bots.add(bot.get(i));
		}
		this.crossoverRatio = crossoverRatio;
		this.remainderRatio = remainderRatio;
		this.eliteNumber = eliteNumber;
		this.fitnessScaleTo = fitnessScaleTo;
		this.r=r;
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
		int numOfStochasticParents = (int) (parents.size() / remainderRatio) - parents.size();

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
			if (stochasticVals.get(index) < remainsInJump)// jumped over the end
															// of this bot's
															// range
			{
				remainsInJump = jumpValue - stochasticVals.get(index);
				index++;
			} else// landed in the range of the bot at index's range
			{
				parents.add(bots.get(index));
				remainsInJump = jumpValue;
			}
		}

	}

	private void categorizeParents(ArrayList<Bot> parents, ArrayList<Bot> mutators, ArrayList<Bot> crossovers,
			ArrayList<Bot> elites)
	{
		// categorize elites
		Bot tempMax;
		int tempIndex;
		for (int i = 0; i < eliteNumber; i++)
		{
			tempMax = parents.get(0);
			tempIndex = 0;
			for (int j = 1; j < parents.size(); j++)
			{
				if (parents.get(j).getFitness() > tempMax.getFitness())
				{
					tempMax = parents.get(j);
					tempIndex = j;
				}
			}
			elites.add(tempMax);
			parents.remove(tempIndex);
		}

		// categorize crossovers
		int numCrossOvers = (int) (crossoverRatio * parents.size());
		if(numCrossOvers%2==1)
			numCrossOvers-=1;
		for (int i = 0; i < numCrossOvers; i++)
		{
			crossovers.add(parents.remove(r.nextInt(parents.size())));
		}
		
		//remaining are categorized as mutators
		while(parents.size()>0)
		{
			mutators.add(parents.remove(0));
		}
	}

	private void elitify(ArrayList<Bot> elites, ArrayList<Bot> children)
	{
		//all elites survive on another generation
		while(elites.size()>0)
		{
			Bot bot = new Bot();
			bot.setMaximumLoss(elites.get(0).getMaximumLoss());
			bot.setMomentum(elites.get(0).getMomentum());
			bot.setMovingAverage(elites.get(0).getMovingAverage());
			bot.setPercentCashOnHand(elites.get(0).getPercentCashOnHand());
			bot.setPurchaseLot(elites.get(0).getPurchaseLot());
			bot.setSellPrice(elites.get(0).getSellPrice());
			bot.setTimeLimit(elites.get(0).getTimeLimit());
			bot.setTrailingPrice(elites.get(0).getTrailingPrice());
			children.add(bot);
			elites.remove(0);
		}
	}

	private void crossover(ArrayList<Bot> crossovers, ArrayList<Bot> children)
	{
		if(crossovers.size()%2==1)
			throw new InvalidParameterException("Odd number of crossovers");
		Bot parent1,parent2,child;
		while(crossovers.size()>0)
		{
			parent1=crossovers.remove(r.nextInt(crossovers.size()));
			parent2=crossovers.remove(r.nextInt(crossovers.size()));
			child=new Bot();
			if(r.nextBoolean())
				child.setMaximumLoss(parent1.getMaximumLoss());
			else
				child.setMaximumLoss(parent2.getMaximumLoss());
			if(r.nextBoolean())
				child.setMomentum(parent1.getMomentum());
			else
				child.setMomentum(parent2.getMomentum());
			if(r.nextBoolean())
				child.setMovingAverage(parent1.getMovingAverage());
			else
				child.setMovingAverage(parent2.getMovingAverage());
			if(r.nextBoolean())
				child.setPercentCashOnHand(parent1.getPercentCashOnHand());
			else
				child.setPercentCashOnHand(parent2.getPercentCashOnHand());
			if(r.nextBoolean())
				child.setPurchaseLot(parent1.getPurchaseLot());
			else
				child.setPurchaseLot(parent2.getPurchaseLot());
			if(r.nextBoolean())
				child.setSellPrice(parent1.getSellPrice());
			else
				child.setSellPrice(parent2.getSellPrice());
			if(r.nextBoolean())
				child.setTimeLimit(parent1.getTimeLimit());
			else
				child.setTimeLimit(parent2.getTimeLimit());
			if(r.nextBoolean())
				child.setTrailingPrice(parent1.getTrailingPrice());
			else
				child.setTrailingPrice(parent2.getTrailingPrice());
			children.add(child);
		}
	}

	private void mutate(ArrayList<Bot> mutators, ArrayList<Bot> children)
	{
		while(mutators.size()>0)
		{
			children.add(mutators.remove(0).mutate(r));
		}
	}
}
