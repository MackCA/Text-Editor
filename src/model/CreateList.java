package model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

import utils.Backup;

public class CreateList {
	private static HashMap<String, LinkedList<String>> dataPool;

	public static HashMap<String, LinkedList<String>> createList(String[] stringArray) {
		FileWriter fw;
		try {
			fw = new FileWriter("data//output//markovData.txt");
			dataPool = new HashMap<>();
			for (int i = 0; i < stringArray.length; i++) {
				LinkedList<String> keyList = new LinkedList<String>();
				String temp = stringArray[i];
				if (temp.equals("")) {
					continue;
				} else {
					for (int j = 0; j < stringArray.length-1; j++) {
						if (stringArray[j].compareToIgnoreCase(temp)==0) {
							keyList.add(stringArray[j + 1]);
						}
					}
					dataPool.put(temp, keyList);
					//fw.write(temp + " : " + dataPool.get(temp).toString() + "\n\n");
				}
			}
			Backup.backupTrainedData();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dataPool;
	}

	public static HashMap<String, LinkedList<String>> getDataPool() {
		return dataPool;
	}

	public static void setDataPool(HashMap<String, LinkedList<String>> dataPool) {
		CreateList.dataPool = dataPool;
	}
}
