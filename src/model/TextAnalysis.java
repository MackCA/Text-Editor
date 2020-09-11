package model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import view.TextPane;

public class TextAnalysis {
	private int wordCount;
	private int sentenceCount;
	private int syllableCount;
	private double fleschScoreLevel;
	private double fleschScoreEase;
	private long totalTimeGrouped;
	private long totalTimeWCSingle;
	private long totalTimeSCSingle;
	private long totalTimeSyCSingle;


	public TextAnalysis() {
		super();
		this.wordCount = 0;
		this.sentenceCount = 0;
		this.syllableCount = 0;
		this.fleschScoreLevel = 0;
		this.fleschScoreEase = 0;
	}

	public int calculateWordCount(String[] stringArray, int size) {
		wordCount = 0;
		long singleWCstart = System.nanoTime();
		for (int i = 0; i < size; i++) {
			wordCount = stringArray.length;
			if (stringArray[i].equals("") || stringArray[i].equals("\r\n")) {
				--wordCount;
			}
		}
		long singleWCend = System.nanoTime();
		this.totalTimeWCSingle = singleWCend - singleWCstart;
		return wordCount;
	}

	public int calculateSentenceCount(String[] stringArray, int size) {
		sentenceCount = 0;
		long singleSCstart = System.nanoTime();
		for (int i = 0; i < size; i++) {
			if (stringArray[i].endsWith(".") || stringArray[i].endsWith("?")
					|| stringArray[i].endsWith("!")) {
				sentenceCount++;
			} else if (stringArray[i].endsWith(".\"") || stringArray[i].endsWith("?\"")
					|| stringArray[i].endsWith("!\"")) {
				sentenceCount++;
			}
		}
		long singleSCend = System.nanoTime();
		this.totalTimeSCSingle = singleSCend -singleSCstart;
		return sentenceCount;
	}

	public int calculateSyllableCount(String[] stringArray, int size) {
		syllableCount = 0;
		long singleSyStart = System.nanoTime();
		for (int i = 0; i <size; i++) {
			syllableCount = syllableCount + countSyllables(stringArray[i]);
		}
		long singleSyEnd = System.nanoTime();
		this.totalTimeSyCSingle = singleSyEnd-singleSyStart;
		return syllableCount;
	}

	public int countSyllables(String word) {
		String pattern = "[AEIOUYaeiouy]+";
		Pattern tokenSplitter = Pattern.compile(pattern);
		Matcher m = tokenSplitter.matcher(word);
		int count = 0;
		while (m.find()) {
			count++;
		}
		if (word.endsWith("e")) {
			count--;
		}
		return count;
	}
	
	public double calculateEaseScore(int wordCount, int SentenceCount, int SyllableCount){
		fleschScoreEase = 0;
		if (wordCount > 0 && sentenceCount > 0) {
			fleschScoreEase =  (206.835 - (1.015 * (wordCount / (double)sentenceCount))
					- (84.6 * (syllableCount / (double)wordCount)));
		}
		return fleschScoreEase;
	}
	public double calculateLevelScore(int wordCount, int SentenceCount, int SyllableCount){
		fleschScoreLevel = 0;
		if (wordCount > 0 && sentenceCount > 0) {
			fleschScoreLevel =  (0.39 * (wordCount /(double)sentenceCount) + (11.8 * (syllableCount /(double)wordCount))
					- 15.59);
		}
		return fleschScoreLevel;
	}

	public void analyseGrouped(String[] textArray,int maxSize) {
		long groupedCountStart = System.nanoTime();
		wordCount = maxSize;
		for (int i = 0; i < maxSize-1; i++) {
			
			if (textArray[i].equals("") || textArray[i].equals("\r\n")) {
				--wordCount;
			}

			if (textArray[i].endsWith(".") || textArray[i].endsWith("?") || textArray[i].endsWith("!")) {
				sentenceCount++;
			} else if (textArray[i].endsWith(".\"") || textArray[i].endsWith("?\"") || textArray[i].endsWith("!\"")) {
				sentenceCount++;
			}
			syllableCount = syllableCount + countSyllables(textArray[i]);
		}
		long groupedCountEnd = System.nanoTime();
		this.totalTimeGrouped =groupedCountEnd -groupedCountStart;
	}

	public int getWordCount() {
		return wordCount;
	}

	public void setWordCount(int wordCount) {
		this.wordCount = wordCount;
	}

	public int getSentenceCount() {
		return sentenceCount;
	}

	public void setSentenceCount(int sentenceCount) {
		this.sentenceCount = sentenceCount;
	}

	public int getSyllableCount() {
		return syllableCount;
	}

	public void setSyllableCount(int syllableCount) {
		this.syllableCount = syllableCount;
	}

	public double getFleschScoreLvl() {
		return fleschScoreLevel;
	}

	public void setFleschScoreLvl(int fleschScoreLvl) {
		this.fleschScoreLevel = fleschScoreLvl;
	}

	public double getFleschScoreEase() {
		return fleschScoreEase;
	}

	public void setFleschScoreEase(int fleschScoreEase) {
		this.fleschScoreEase = fleschScoreEase;
	}

	public long getTotalTimeGrouped() {
		return totalTimeGrouped;
	}

	public long getTotalTimeWCSingle() {
		return totalTimeWCSingle;
	}

	public long getTotalTimeSCSingle() {
		return totalTimeSCSingle;
	}

	public long getTotalTimeSyCSingle() {
		return totalTimeSyCSingle;
	}


}
