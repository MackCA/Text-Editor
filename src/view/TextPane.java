package view;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import model.SpellChecker;
import model.TextAnalysis;

public class TextPane {
	private GridPane textGrid;
	private static TextArea mainTextBox;
	private BackgroundFill back;
	private Background backGr;
	private static int textLength;
	private static StatusBarPane statusBar;
	private static ArrayList<String> textContentArrayList;
	private TextAnalysis analysis;
	private static String[] textContentArray;
	private static int wordCount;
	private static int sentenceCount;
	private static int syllableCount;
	private SpellChecker spellCheck;
	
	public TextPane(StatusBarPane statusBar) {
		textGrid = new GridPane();
		textGrid.setMinSize(1200, 800);
		textGrid.setAlignment(Pos.CENTER);
		back = new BackgroundFill(Color.rgb(221, 221, 221, 0.65), CornerRadii.EMPTY, Insets.EMPTY);
		backGr = new Background(back);
		textGrid.setBackground(backGr);
		textGrid.setGridLinesVisible(true);
		spellCheck = new SpellChecker();
		TextPane.statusBar = statusBar;
		this.analysis = new TextAnalysis();
		populateGrid();
	}

	public void populateGrid() {
		mainTextBox = new TextArea();
		mainTextBox.textProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable, String OldStr, String newStr) {
				textContentArray = TextPane.getMainTextBox().getText().trim().split(" ");
				int size = textContentArray.length;
				wordCount = analysis.calculateWordCount(TextPane.getTextContentArray(),size);
				sentenceCount =analysis.calculateSentenceCount(TextPane.getTextContentArray(),size);
				syllableCount = analysis.calculateSyllableCount(TextPane.getTextContentArray(),size);
				
				if (statusBar.isWcStatus() == true) {
					statusBar.setWordCount(wordCount);
				}

				if (statusBar.isScStatus() == true) {
					statusBar.setSentenceCount(sentenceCount);
				}
				
				if (statusBar.isSylStatus() == true) {
					statusBar.setSyllableCount(syllableCount);
				}

				if (statusBar.isFleschScoreStatus() == true) {
					statusBar.setFleschScoreE(analysis.calculateEaseScore(wordCount,sentenceCount,syllableCount));
					statusBar.setFleschScoreL(analysis.calculateLevelScore(wordCount,sentenceCount,syllableCount));
				}
			}
		});
		mainTextBox.setMinSize(1200, 800);
		mainTextBox.setWrapText(true);
		mainTextBox.setEditable(true);
		textGrid.add(mainTextBox, 5, 5);
	}

	public GridPane getTextGrid() {
		return textGrid;
	}

	public static TextArea getMainTextBox() {
		return mainTextBox;
	}

	public void setMainTextBox(TextArea mainTextBox) {
		TextPane.mainTextBox = mainTextBox;
	}

	public static void setTextContent(ArrayList<String> textContentArrayList) {
		TextPane.textContentArrayList = textContentArrayList;
	}

	public static ArrayList<String> getTextContentArrayList() {
		return textContentArrayList;
	}

	public static String[] getTextContentArray() {
		return textContentArray;
	}

	public static void setTextContentArray(String[] textContentArray) {
		TextPane.textContentArray = textContentArray;
	}

	public static int getTextLength() {
		return textLength;
	}

}