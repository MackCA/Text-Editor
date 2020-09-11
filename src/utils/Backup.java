package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.LinkedList;
import model.CreateList;

public class Backup {//save training data

	public static void backupTrainedData() {
		try {
			FileOutputStream fos = new FileOutputStream("data\\output\\trained.dat");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(CreateList.getDataPool());
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
}

	@SuppressWarnings("unchecked")
	public static void restoreTrainedData() {

		File file = new File("data\\output\\trained.dat");
		if (file.exists()) {
			try {
				FileInputStream fis = new FileInputStream("data\\output\\trained.dat");
				ObjectInputStream ois = new ObjectInputStream(fis);
				CreateList.setDataPool((HashMap<String, LinkedList<String>>) (ois.readObject()));
				ois.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

}
