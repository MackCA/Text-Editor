package model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Random;

import view.TextPane;

public class AutoText {
	private Random random = new Random();
	private String word;
	private String autoText;
	private static HashMap<String,LinkedList<String>> dataPool;


	public AutoText() {
		super();//change to CreateList.getDataPool();
		AutoText.dataPool = CreateList.createList(TextPane.getTextContentArray());
	}

	public void train(String word, int length){
		this.word = word;
		autoText = textGenerator(length, word);
	}

	public String textGenerator(int length, String word) {
		String text = word;
		for (int i = 0; i < length-1; i++) {
			text = text + " " + wordSearch(this.word);
		}
		return text;
	}

	public String wordSearch(String searchKey) {

		if (dataPool.containsKey(searchKey)) {
			this.word = dataPool.get(searchKey).get(random.nextInt(dataPool.get(searchKey).size()));
			return word;
		}
		return null;
	}

	public String getAutoText() {
		return autoText;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public void setLength(int length) {
	}

}
