package view;

import javafx.animation.Animation.Status;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class StatusBarPane {
	private Status statusBar;
	private static TextPane textPane;
	private static Label wordCount;
	private Label wordCountLbl;
	private static Label sentenceCount;
	private Label syllableCountLbl;
	private static Label syllableCount;
	private Label sentenceCountLbl;
	private static Label FleschScoreL;
	private Label FleschScoreLvlLbl;
	private static Label FleschScoreE;
	private Label FleschScoreELbl;
	private HBox statusBarBox;
	private boolean wcStatus;
	private boolean scStatus;
	private boolean sylStatus;
	private boolean fleschScoreStatus;

	public StatusBarPane() {
		statusBarBox = new HBox(30);
		statusBarBox.setAlignment(Pos.BOTTOM_CENTER);
		this.wcStatus = false;
		this.scStatus = false;
		this.sylStatus = false;
		this.fleschScoreStatus = false;
		buildLabels();
		statusBarBox.getChildren().addAll(wordCountLbl,wordCount,sentenceCountLbl,sentenceCount,syllableCountLbl,syllableCount,FleschScoreLvlLbl,FleschScoreL,FleschScoreELbl,FleschScoreE);

	}

	private void buildLabels(){
		wordCount = new Label("");
		wordCountLbl = new Label("Word Count");
		sentenceCount = new Label("");
		sentenceCountLbl = new Label("Sentence Count");
		syllableCount = new Label("");
		syllableCountLbl = new Label("Syllable Count");
		FleschScoreL = new Label("");
		FleschScoreLvlLbl = new Label("Flesch Score: Level");
		FleschScoreE = new Label("");
		FleschScoreELbl = new Label("Flesch Score: Ease");
	}


	public TextPane getTextPane() {
		return textPane;
	}

	public HBox getStatusBarBox() {
		return statusBarBox;
	}

	public Status getStatusBar() {
		return statusBar;
	}

	public void setStatusBar(Status statusBar) {
		this.statusBar = statusBar;
	}

	public Label getWordCount() {
		return wordCount;
	}

	public void setWordCount(int wc) {
		 StatusBarPane.wordCount.setText(""+wc);
	}

	public Label getSentenceCount() {
		return sentenceCount;
	}

	public void setSentenceCount(int sc) {
		StatusBarPane.sentenceCount.setText(""+sc);
	}

	public Label getFleschScoreL() {
		return FleschScoreL;
	}

	public void setFleschScoreL(double fcL) {
		StatusBarPane.FleschScoreL.setText(""+fcL);
	}

	public Label getFleschScoreE() {
		return FleschScoreE;
	}

	public void setFleschScoreE(double fcE) {
		StatusBarPane.FleschScoreE.setText(""+fcE);
	}

	public boolean isWcStatus() {
		return wcStatus;
	}

	public void setWcStatus(boolean wcStatus) {
		this.wcStatus = wcStatus;
	}

	public boolean isScStatus() {
		return scStatus;
	}

	public void setScStatus(boolean scStatus) {
		this.scStatus = scStatus;
	}

	public boolean isFleschScoreStatus() {
		return fleschScoreStatus;
	}

	public void setFleschScoreStatus(boolean fleschScoreStatus) {
		this.fleschScoreStatus = fleschScoreStatus;
	}

	public Label getSyllableCount() {
		return syllableCount;
	}

	public void setSyllableCount(int syC) {
		StatusBarPane.syllableCount.setText(""+syC);
	}

	public boolean isSylStatus() {
		return sylStatus;
	}

	public void setSylStatus(boolean sylStatus) {
		this.sylStatus = sylStatus;
	}


}

