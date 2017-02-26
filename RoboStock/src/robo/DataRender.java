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
import org.knowm.xchart.CSVExporter;


public class DataRender
{
	  private static final int NUMLOW = 5;
	  private static final int NUMHIGH = 5;
	  ArrayList<XYChart> charts = new ArrayList<XYChart>();
	  XYChart fitGraph;
	  XYChart genGraph;
	  int generationNum = 1;
	  ArrayList<Integer> numGenerations = new ArrayList<Integer>();
	  ArrayList<Double> fitness = new ArrayList<Double>();
	  ArrayList<Integer> cash = new ArrayList<Integer>();
	  ArrayList<Float> averageReturn = new ArrayList<Float>();
	  JFrame sw;
 	  
	public DataRender(ArrayList<Bot> generation)
	{
		charts.add(constructFitnessGraph(generation));
		charts.add(constructGenerationGraph(generation));
		sw = new SwingWrapper<XYChart>(charts).displayChartMatrix();
	}
	
	/**
	 * Constructor for fitness graph, which will show the trend of fitness of our generations
	 * 
	 * @return
	 */
	 public XYChart constructFitnessGraph(ArrayList<Bot> generation)
	 {
//		 XYChart fitChart = QuickChart.getChart("Fitness Growth Throughout Generations", "Generation", "", seriesName, xData, yData)
		 fitGraph = new XYChartBuilder().title("Fitness Growth Throughout Generations").xAxisTitle("Generation").yAxisTitle("Fitness")
				 								.width(1000).height(600).build();
		 fitGraph.getStyler().setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Scatter);
		 computeAvgFitness(generation);
		 fitGraph.addSeries("fitness", numGenerations, fitness);
		 
		 return fitGraph;
	 }
	 
	 /**
	 * Constructor for fitness graph, which will show the trend of fitness of our generations
	 * 
	 * @return
	 */
	 public XYChart constructGenerationGraph(ArrayList<Bot> generation)
	 {
		 genGraph = new XYChartBuilder().title("Performance of Generation " + generationNum).xAxisTitle("Money in Hand").yAxisTitle("Average Return")
							.width(1000).height(600).build();
		 genGraph.getStyler().setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Scatter);
		 computePerformers(generation);
		 genGraph.addSeries("generation", cash, averageReturn);
		 return genGraph;
	 }
	 
	 public void computeAvgFitness(ArrayList<Bot> generation) 
	 {
		 int sumFitness = 0;
		 for(int i = 0; i < generation.size(); i++){
			sumFitness += generation.get(i).getFitness();
		 }
		 numGenerations.add(generationNum);
		 fitness.add((double)sumFitness/generation.size());
	 }
	 
	 /**
	  * Computes and stores the generations tOP
	  * @param generation
	  */
	 public void computePerformers(ArrayList<Bot> generation)
	 { 
//		 ArrayList<Bot> botList = new ArrayList<Bot>();
		 Collections.sort(generation, (b1,b2) -> b1.getFitness() - b2.getFitness());
		 int index = 0;
		 for(int i = generation.size(); i > generation.size() - NUMHIGH ; i--){
			 averageReturn.add(generation.get(index).getAverageReturn());
			 cash.add(generation.get(index).getPercentCashOnHand());
			 index++;
		 }
		 for(int i = 0; i < NUMLOW; i++){
			 averageReturn.add(generation.get(i).getAverageReturn());
			 cash.add(generation.get(i).getPercentCashOnHand());
		 }
	 }
	 
	 /** 
	  * Redraws fitness and generation graph based on new generation of bots
	  * @param generation
	  */
	 public void updateGraphs(ArrayList<Bot> generation){
		 computeAvgFitness(generation);
		 computePerformers(generation);
       	 charts.get(0).updateXYSeries("fitness", numGenerations, fitness, null);
       	 charts.get(1).updateXYSeries("generation", cash, averageReturn, null);
         
         sw.repaint();
         generationNum++;
         
         //saveGeneration(generation);
	 }
	 
	 public void saveGeneration(ArrayList<Bot> generation){
		 CSVExporter.writeCSVColumns(genGraph, "./GenerationData/Generation" + generationNum);
	 }
	 
	 public void importGeneration(String dataFile){
		 XYChart importChart = CSVImporter.getChartFromCSVDir(dataFile, CSVImporter.DataOrientation.Rows, 1000, 600);
		 JFrame sw = new SwingWrapper<XYChart>(importChart).displayChart();
		 
	 }
	 
	 public static void main(String[] args) throws InterruptedException 
	 {
		 //DataRender d = new DataRender(currentGeneration);
//		 d.importGeneration("./GenerationData/series2.csv");
//	    int numCharts = 2;
//	    
//	    ArrayList<XYChart> charts = new ArrayList<XYChart>();
//	    double phase = 0;
//	    double[][] initdata = getSineData(phase);
//	    
//	    for (int i = 0; i < numCharts; i++) {
//	
//	      XYChart chart = QuickChart.getChart("Simple XChart Real-time Demo", "Radians", "Sine", "sine", initdata[0], initdata[1]);
//	      chart.getStyler().setYAxisMin((double) -10);
//	      chart.getStyler().setYAxisMax((double) 10);
//	      charts.add(chart);
//	    }
//	    
//	    JFrame sw = new SwingWrapper<XYChart>(charts).displayChartMatrix();
//	    while (true) {
//
//	          phase += 2 * Math.PI * 2 / 20.0;
//
//	          Thread.sleep(100);
//
//	          final double[][] data = getSineData(phase);
//	          for(int i = 0; i< numCharts; i++){
//	        	  charts.get(i).updateXYSeries("sine", data[0], data[1], null);
//	          }
//	          sw.repaint();
//	        }
	  }

	  /**
	   * Generates a set of random walk data
	   *
	   * @param numPoints
	   * @return
	   */
	  private static double[][] getSineData(double phase) {
		  
		    double[] xData = new double[100];
		    double[] yData = new double[100];
		    for (int i = 0; i < xData.length; i++) {
		      double radians = phase + (2 * Math.PI / xData.length * i);
		      xData[i] = radians;
		      yData[i] = Math.sin(radians);
		    }
		    return new double[][] { xData, yData };
		  }

	}
