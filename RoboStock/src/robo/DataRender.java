package robo;

import java.awt.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.swing.JFrame;
import java.util.*;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries.XYSeriesRenderStyle;
import org.knowm.xchart.CSVImporter;
import org.knowm.xchart.CSVImporter.DataOrientation;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.CategorySeries.CategorySeriesRenderStyle;
import org.knowm.xchart.CSVExporter;


public class DataRender
{
	  private static final int HEIGHT = 500;
	  private static final int WIDTH = 950;
	  ArrayList<CategoryChart> charts = new ArrayList<CategoryChart>();
	  CategoryChart fitGraph;
	  CategoryChart netGraph;
	  CategoryChart winGraph;
	  CategoryChart childGraph;
	  int generationNum = 1;
	  ArrayList<Integer> numGenerations = new ArrayList<Integer>();
	  ArrayList<Double> fitness = new ArrayList<Double>();
	  ArrayList<Double> winLoss = new ArrayList<Double>();
	  ArrayList<Float> netWorth = new ArrayList<Float>();
	  ArrayList<Integer> numChildren = new ArrayList<Integer>();
	  JFrame sw;
	  
 	  
	public DataRender(ArrayList<Bot> generation)
	{
		numGenerations.add(generationNum);
		charts.add(constructFitnessGraph(generation));
		charts.add(constructNetWorthGraph(generation));
//		charts.add(constructWinLossGraph(generation));
//		charts.add(constructChildrenGraph(generation));
		sw = new SwingWrapper<CategoryChart>(charts).displayChartMatrix();
	}
	
	/**
	 * Constructor for fitness graph, which will show the trend of fitness of our generations
	 * 
	 * @return
	 */
	 public CategoryChart constructFitnessGraph(ArrayList<Bot> generation)
	 {
//		 XYChart fitChart = QuickChart.getChart("Fitness Growth Throughout Generations", "Generation", "", seriesName, xData, yData)
		 fitGraph = new CategoryChartBuilder().title("Fitness Throughout Generations").xAxisTitle("Generation").yAxisTitle("Fitness")
				 								.width(WIDTH).height(HEIGHT).build();
		 fitGraph.getStyler().setDefaultSeriesRenderStyle(CategorySeriesRenderStyle.Line);
		 computeAvgFitness(generation);
		 fitGraph.addSeries("fitness", numGenerations, fitness);
		 return fitGraph;
	 }
	 
	 /**
	 * Constructor for netWorth graph, which will show the trend of the net worth over generations
	 * 
	 * @return
	 */
	 public CategoryChart constructNetWorthGraph(ArrayList<Bot> generation)
	 {
		 netGraph = new CategoryChartBuilder().title("Net Worth ThroughOut Generations").xAxisTitle("Generation").yAxisTitle("Net Worth")
							.width(WIDTH).height(HEIGHT).build();
		 netGraph.getStyler().setDefaultSeriesRenderStyle(CategorySeriesRenderStyle.Line);
		 computeAvgNetWorth(generation);
		 netGraph.addSeries("netWorth", numGenerations, netWorth);
		 return netGraph;
	 }
	 
	 /**
		 * Constructor for netWorth graph, which will show the trend of the net worth over generations
		 * 
		 * @return
		 */
		 public CategoryChart constructWinLossGraph(ArrayList<Bot> generation)
		 {
			 winGraph = new CategoryChartBuilder().title("Win/Loss Ratio ThroughOut Generations").xAxisTitle("Generation").yAxisTitle("Win/Loss Ratio")
								.width(WIDTH).height(HEIGHT).build();
			 winGraph.getStyler().setDefaultSeriesRenderStyle(CategorySeriesRenderStyle.Line);
			 computeAvgWinLoss(generation);
			 winGraph.addSeries("winLoss", numGenerations, winLoss);
			 return winGraph;
		 }
		 
	    /**
		 * Constructor for netWorth graph, which will show the number of children for each generation
		 * @return
		 */
		 public CategoryChart constructChildrenGraph(ArrayList<Bot> generation)
		 {
			 childGraph = new CategoryChartBuilder().title("Number of Chilren ThroughOut Generations").xAxisTitle("Generation").yAxisTitle("NumChildren")
								.width(WIDTH).height(HEIGHT).build();
			 childGraph.getStyler().setDefaultSeriesRenderStyle(CategorySeriesRenderStyle.Stick);
			 computenumChildren(generation);
			 childGraph.addSeries("children", numGenerations, numChildren);
			 return childGraph;
		 }
		 
	public void computenumChildren(ArrayList<Bot> generation){
		numChildren.add(generation.size());
	}
	 public void computeAvgFitness(ArrayList<Bot> generation) 
	 {
		 int sumFitness = 0;
		 for(int i = 0; i < generation.size(); i++){
			sumFitness += generation.get(i).getFitness();
		 }
		 fitness.add((double)sumFitness/generation.size());
		 
	 }
	 
	 public void computeAvgWinLoss(ArrayList<Bot> generation) 
	 {
		 int sumWinLoss = 0;
		 for(int i = 0; i < generation.size(); i++){
			 sumWinLoss += generation.get(i).getWinLossRatio();
		 }
		 winLoss.add((double)sumWinLoss/generation.size());
	 }
	 
	 public void computeAvgNetWorth(ArrayList<Bot> generation) 
	 {
		 int sumNetWorth = 0;
		 for(int i = 0; i < generation.size(); i++){
			 sumNetWorth += generation.get(i).getNetWorth();
		 }
		 netWorth.add((float)sumNetWorth/generation.size());
	 }
	 
//	 /**
//	  * Computes and stores the generations tOP
//	  * @param generation
//	  */
//	 public void computePerformers(ArrayList<Bot> generation)
//	 { 
////		 ArrayList<Bot> botList = new ArrayList<Bot>();
//		 Collections.sort(generation, (b1,b2) -> b1.getFitness() - b2.getFitness());
//		 int index = 0;
//		 for(int i = generation.size(); i > generation.size() - NUMHIGH ; i--){
//			 averageReturn.add(generation.get(index).getAverageReturn());
//			 cash.add(generation.get(index).getPercentCashOnHand());
//			 index++;
//		 }
//		 for(int i = 0; i < NUMLOW; i++){
//			 averageReturn.add(generation.get(i).getAverageReturn());
//			 cash.add(generation.get(i).getPercentCashOnHand());
//		 }
//	 }
	 
	 /** 
	  * Redraws fitness and generation graph based on new generation of bots
	  * @param generation
	  */
	 public void updateGraphs(ArrayList<Bot> generation){
		 
		 generationNum++;
		 numGenerations.add(generationNum);
		 computeAvgFitness(generation);
//		 computeAvgWinLoss(generation);
		 computeAvgNetWorth(generation);
//		 computenumChildren(generation);
       	 charts.get(0).updateCategorySeries("fitness", numGenerations, fitness, null);
       	 charts.get(1).updateCategorySeries("netWorth", numGenerations, netWorth, null);
//       	 charts.get(2).updateCategorySeries("winLoss", numGenerations, winLoss, null);
//         charts.get(3).updateCategorySeries("children", numGenerations, numChildren, null);
       	 sw.repaint();
       	 

         //saveGeneration(generation);
	 }
	 
	 public void saveGeneration(ArrayList<Bot> generation){
//		 CSVExporter.writeCSVColumns(netGraph, "./GenerationData/Generation" + generationNum);
	 }
	 
	 public void importGeneration(String dataFile){
		 XYChart importChart = CSVImporter.getChartFromCSVDir(dataFile, CSVImporter.DataOrientation.Rows, 1000, 600);
		 JFrame sw = new SwingWrapper<XYChart>(importChart).displayChart();
		 
	 }
	 
	}
