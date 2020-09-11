package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import view.TextPane;

public class ReadTextFile {
	public static String text = "";

	public static String readText(String textFile) {

		try {
			BufferedReader read = new BufferedReader(new FileReader(textFile));
			String readLine = "";
			while ((readLine = read.readLine()) != null) {
				text = text + readLine + "\n";
			}
			read.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return text;
	}
}
