package model;

import java.io.FileWriter;
import java.io.IOException;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.GridPane;
import view.StatusBarPane;
import view.TextPane;

public class Graph {
	final NumberAxis xAxis = new NumberAxis();
	final NumberAxis yAxis = new NumberAxis();
	final LineChart<Number, Number> lineChart= new LineChart<Number, Number>(xAxis, yAxis);
	private TextAnalysis analysis;
	@SuppressWarnings("rawtypes")
	private XYChart.Series series = new XYChart.Series();
	@SuppressWarnings("rawtypes")
	private XYChart.Series series2= new XYChart.Series();
	private GridPane graphGrid;
	Scene graphPage;

	public Graph(StatusBarPane statusBar) {
		super();
		graphGrid = new GridPane();
		graphGrid.setMinSize(1200, 800);
		graphGrid.setAlignment(Pos.CENTER);
		this.analysis = new TextAnalysis();
		buildGraph();

	}

	@SuppressWarnings({ "unchecked" })
	public void buildGraph() {
		yAxis.setLabel("Nanoseconds");
		xAxis.setLabel("Percentage");
		lineChart.setMinSize(1100, 800);
		lineChart.setTitle("Text Analysis Speeds");
		series.setName("Grouped");
		series2.setName("Split(Total)");
		xAxis.setAutoRanging(true);
		yAxis.setAutoRanging(true);
		lineChart.getData().addAll(series,series2);
		graphGrid.add(lineChart, 5, 5);
	}

	@SuppressWarnings("unchecked")
	public void generateDataPoints() throws IOException {
		FileWriter fw;
		fw = new FileWriter("data//output//datapoints.txt");
		double multiplier = .10;
		int size = (int) (TextPane.getTextContentArray().length * multiplier);
		int percentage = 10;
		for (int i = 0; i < 10; i++) {
			analysis.analyseGrouped(TextPane.getTextContentArray(), size);
			this.series.getData().add(new XYChart.Data<Integer,Long>(percentage,analysis.getTotalTimeGrouped()));
			fw.write("Total time grouped "+percentage+" % "+ analysis.getTotalTimeGrouped()+" nano seconds.\n");
			multiplier = multiplier + .10;
			percentage = percentage + 10;
			size = (int) (TextPane.getTextContentArray().length * multiplier);
		}
		
		multiplier = .10;
		size = (int) (TextPane.getTextContentArray().length * multiplier);
		percentage = 10;
		for (int i = 0; i < 10; i++) {
			analysis.calculateWordCount(TextPane.getTextContentArray(), size);
			analysis.calculateSentenceCount(TextPane.getTextContentArray(), size);
			analysis.calculateSyllableCount(TextPane.getTextContentArray(), size);
			long total = analysis.getTotalTimeWCSingle() +analysis.getTotalTimeSCSingle()+analysis.getTotalTimeSyCSingle();
			this.series2.getData().add(new XYChart.Data<Integer,Long>(percentage,total));
			fw.write("Total time split "+percentage+" % "+ total+" nano seconds.\n");
			multiplier = multiplier + .10;
			percentage = percentage + 10;
			size = (int) (TextPane.getTextContentArray().length * multiplier);
		}
		fw.close();
	}

	public GridPane getGraphGrid() {
		return graphGrid;
	}

}
