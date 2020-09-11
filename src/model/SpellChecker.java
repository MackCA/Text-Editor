package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Optional;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import view.TextPane;

public class SpellChecker {
	private Hashtable<String, String> dictionary;
	private LinkedList<Integer> misspelled;
	private boolean checkLoop = true;

	public SpellChecker() {
	//	buildDictionary();
	}

	@SuppressWarnings("unchecked")
	public void spellCheckInterface() {
		while (checkLoop == true) {
			for (int i = 0; i < misspelled.size(); i++) {
				LinkedList<String> suggestions = new LinkedList<String>();
				suggestions.addLast("Exit Spell Check");
				String oldWord = TextPane.getTextContentArray()[misspelled.get(i)].trim().toLowerCase();
				oldWord.replaceAll("[^A-Za-z0-9_]", "");
				// missing letter test
				for (int j = 65; j < 91; j++) {
					char ch = (char) j;
					for (int k = 0; k < oldWord.length(); k++) {
						String test = addChar(oldWord, ch, k);
						if (dictionary.containsKey(test)) {
							if (!suggestions.contains(test)) {
								suggestions.add(test);
							}
						}
					}
				}
				// extra letter test
				for (int j = 0; j < oldWord.length(); j++) {
					String test = delChar(oldWord, j);
					if (dictionary.containsKey(test)) {
						if (!suggestions.contains(test)) {
							suggestions.add(test);
						}
					}
				}
				// swapped letter test
				for (int j = 0; j < oldWord.length() - 1; j++) {
					for (int k = 1; k < oldWord.length(); k++) {
						String test = swapChar(oldWord, j, k);
						if (dictionary.containsKey(test)) {
							if (!suggestions.contains(test)) {
								suggestions.add(test);
							}
						}
					}
				}
				if (suggestions.equals(null)) {
					suggestions.add("No suggestions Found");
				}
				ChoiceDialog cd = new ChoiceDialog(oldWord, suggestions);
				if (oldWord != " ") {
					cd.setTitle("Spell Check");
					cd.setHeaderText(oldWord + " not found.");
					cd.setContentText("Please choose a word from the suggestions.");
					Optional<String> result = cd.showAndWait();
					result.ifPresent(string -> {
					
						if (string.contentEquals("Exit Spell Check")) {
							setCheckLoop(false);
						}else{
							TextPane.getMainTextBox().setText(TextPane.getMainTextBox().getText().replaceAll(oldWord, string));
						}
						
					});
					if (checkLoop == false) {
						break;
					}
				}
			}
		}
	}

	public String addChar(String word, char l, int index) {
		StringBuilder str = new StringBuilder(word);
		str.insert(index, l);
		return str.toString();
	}

	public String delChar(String word, int index) {
		StringBuilder str = new StringBuilder(word);
		str.deleteCharAt(index);
		return str.toString();
	}

	public String swapChar(String word, int j, int k) {
		StringBuilder str = new StringBuilder(word);
		str.setCharAt(j, str.charAt(k));
		str.setCharAt(k, str.charAt(j));
		return str.toString();
	}

	public LinkedList<Integer> checkSpelling() {
		int size = TextPane.getTextContentArray().length;
		misspelled = new LinkedList<Integer>();
		for (int i = 0; i < size; i++) {
			String str = TextPane.getTextContentArray()[i].replaceAll("[^A-Za-z0-9_]", "");
			if (!(dictionary.containsKey(str.toLowerCase()))) {
				if(str.equals("")){
					continue;
				}else{
					misspelled.add(i);
				}
			}
		}
		return misspelled;
	}

	public Hashtable<String, String> buildDictionary() {
		this.dictionary = new Hashtable<String, String>();
		try {
			BufferedReader readDictionary = new BufferedReader(new FileReader("data//import//dictionary.txt"));
			while (readDictionary.ready()) {
				String input = readDictionary.readLine().toLowerCase().trim();
				String[] dictArray = input.split("/n");
				for (int i = 0; i < dictArray.length; i++) {
					dictionary.put(dictArray[i], dictArray[i]);
				}
			}
			readDictionary.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dictionary;
	}

	public Hashtable<String, String> getDictionary() {
		return dictionary;
	}

	public LinkedList<Integer> getMisspelled() {
		return misspelled;
	}

	public boolean isCheckLoop() {
		return checkLoop;
	}

	public void setCheckLoop(boolean checkLoop) {
		this.checkLoop = checkLoop;
	}

}
