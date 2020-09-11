package view;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import model.AutoText;
import model.Graph;
import model.ReadTextFile;
import model.SpellChecker;
import model.TextAnalysis;
import javafx.stage.Stage;

public class MenuBarPane {
	private MenuBar menuBar;
	private Menu fileMenu;
	private Menu viewMenu;
	private Menu editMenu;
	private Graph graphPane;
	private BorderPane root;
	private Stage primaryStage;
	private FileChooser fileChooser = new FileChooser();
	private StatusBarPane statusBar;
	private final Clipboard clipboard = Clipboard.getSystemClipboard();
	private final ClipboardContent content = new ClipboardContent();
	private TextAnalysis analysis;
	private static String[] textContentArray;
	private String word;
	int length;

	public MenuBarPane(BorderPane root, TextPane textPane, Stage primaryStage, StatusBarPane statusBar,
			TextAnalysis analysis, Graph graphPane) {
		super();
		this.statusBar = statusBar;
		this.menuBar = new MenuBar();
		this.root = root;
		this.primaryStage = primaryStage;
		this.analysis = analysis;
		this.graphPane = graphPane;
		buildFileMenu();
		buildViewMenu();
		buildEditMenu();
		menuBar.getMenus().addAll(fileMenu, editMenu, viewMenu);

	}

	private void buildEditMenu() {
		editMenu = new Menu("Edit");
		MenuItem copy = new MenuItem("Copy");
		copy.setOnAction(ex -> {
			String text = TextPane.getMainTextBox().getSelectedText();
			if (text != null) {
				content.putString(text);
				clipboard.setContent(content);
			}
		});

		MenuItem cut = new MenuItem("Cut");
		cut.setOnAction(ex -> {
			String text = TextPane.getMainTextBox().getSelectedText();
			if (text != null) {
				content.putString(text);
				clipboard.setContent(content);
				TextPane.getMainTextBox().replaceSelection("");
			}
		});
		MenuItem paste = new MenuItem("Paste");
		paste.setOnAction(ex -> {
			if (content.getString() != null) {
				TextPane.getMainTextBox().appendText(content.getString());
			}
		});
		MenuItem delete = new MenuItem("Delete");
		delete.setOnAction(ex -> {
			if (TextPane.getMainTextBox().getSelectedText() != null) {
				TextPane.getMainTextBox().replaceSelection("");
			}
		});

		MenuItem markov = new MenuItem("Markov");
		markov.setOnAction(ex -> {
			TextInputDialog markovDialog = new TextInputDialog();
			markovDialog.setTitle("Auto Text Generator");
			markovDialog.setHeaderText("Please enter the seed word:");
			markovDialog.setContentText("Enter a word:");

			Optional<String> result = markovDialog.showAndWait();
			result.ifPresent(e -> {
				word = result.get();
			});
			TextInputDialog markovDialogLen = new TextInputDialog();
			markovDialogLen.setTitle("Auto Text Generator");
			markovDialogLen.setHeaderText("Please enter number of words desired:");
			markovDialogLen.setContentText("Enter a Number:");
			Optional<String> value = markovDialogLen.showAndWait();
			value.ifPresent(e -> {
				String check = value.get();
				length = Integer.parseInt(check);
			});
			AutoText markovText = new AutoText();
			markovText.train(word, length);
			TextPane.getMainTextBox().clear();
			TextPane.getMainTextBox().setText(markovText.getAutoText());
		});

		MenuItem spCk = new MenuItem("Spell Check");
		spCk.setOnAction(ex -> {
			SpellChecker spellCheck = new SpellChecker();
			spellCheck.buildDictionary();
			spellCheck.checkSpelling();
			if(spellCheck.getMisspelled().size()>0){
			spellCheck.spellCheckInterface();
			}else{
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Spell Check Complete");
				alert.setHeaderText("Spell check complete..");
				alert.setContentText("No errors detected.");
				alert.showAndWait();
			}
		});
		editMenu.getItems().addAll(copy, cut, paste, delete, markov, spCk);
	}

	private void buildViewMenu() {
		viewMenu = new Menu("View");
		MenuItem wc = new MenuItem("Word Count");
		wc.setOnAction(ex -> {
			if (statusBar.isWcStatus() == false) {
				analysis.calculateWordCount(TextPane.getTextContentArray(), TextPane.getTextContentArray().length);
				statusBar.setWordCount(analysis.getWordCount());
				statusBar.setWcStatus(true);
			} else {
				statusBar.setWcStatus(false);
				statusBar.setWordCount(0);
			}
		});
		MenuItem sc = new MenuItem("Sentence Count");
		sc.setOnAction(ex -> {// set these to on off switch using while loop
			if (statusBar.isScStatus() == false) {
				statusBar.setScStatus(true);
				analysis.calculateSentenceCount(TextPane.getTextContentArray(), TextPane.getTextContentArray().length);
				statusBar.setSentenceCount(analysis.getSentenceCount());
			} else {
				statusBar.setScStatus(false);
				statusBar.setSentenceCount(0);
			}
		});
		MenuItem sylc = new MenuItem("Syllable Count");
		sylc.setOnAction(ex -> {// set these to on off switch using while loop
			if (statusBar.isSylStatus() == false) {
				statusBar.setSylStatus(true);
				analysis.calculateSyllableCount(TextPane.getTextContentArray(), TextPane.getTextContentArray().length);
				statusBar.setSyllableCount(analysis.getSyllableCount());
			} else {
				statusBar.setSylStatus(false);
				statusBar.setSyllableCount(0);
			}
		});
		MenuItem fs = new MenuItem("Flesch Scores: ");
		fs.setOnAction(ex -> {
			if (statusBar.isFleschScoreStatus() == true) {
				statusBar.setFleschScoreStatus(false);
				statusBar.setFleschScoreE(0);
				statusBar.setFleschScoreL(0);
			} else {
				statusBar.setFleschScoreStatus(true);
				int wordC = analysis.calculateWordCount(TextPane.getTextContentArray(),
						TextPane.getTextContentArray().length);
				int sentC = analysis.calculateSentenceCount(TextPane.getTextContentArray(),
						TextPane.getTextContentArray().length);
				int sylC = analysis.calculateSyllableCount(TextPane.getTextContentArray(),
						TextPane.getTextContentArray().length);
				analysis.calculateEaseScore(wordC, sentC, sylC);
				analysis.calculateLevelScore(wordC, sentC, sylC);
				statusBar.setFleschScoreE(analysis.getFleschScoreEase());
				statusBar.setFleschScoreL(analysis.getFleschScoreLvl());
			}
		});

		MenuItem gph = new MenuItem("Speed Graph");
		gph.setOnAction(ex -> {
			try {
				graphPane.generateDataPoints();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			root.setCenter(graphPane.getGraphGrid());
		});
		viewMenu.getItems().addAll(wc, sc, sylc, fs, gph);
	}

	private void buildFileMenu() {
		fileMenu = new Menu("File");
		MenuItem newDoc = new MenuItem("New");
		newDoc.setOnAction(ex -> {
			TextPane textPane = new TextPane(statusBar);
			root.setCenter(textPane.getTextGrid());
			TextPane.setTextContent(null);
			TextPane.setTextContentArray(null);
		});
		MenuItem open = new MenuItem("Open");
		open.setOnAction(ex -> {
			TextPane textPane = new TextPane(statusBar);
			root.setCenter(textPane.getTextGrid());
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"));
			String defaultFolder = "data\\import\\";
			String currentPath = Paths.get(defaultFolder).toAbsolutePath().normalize().toString();
			fileChooser.setInitialDirectory(new File(currentPath));
			File selectedFile = fileChooser.showOpenDialog(primaryStage);
			String text = ReadTextFile.readText(selectedFile.getPath());
			if (selectedFile != null) {
				TextPane.getMainTextBox().setText("");
				TextPane.getMainTextBox().setText(text);
			}
		});
		MenuItem close = new MenuItem("Close");
		close.setOnAction(ex -> {
			TextPane.getMainTextBox().setText("");
		});
		MenuItem save = new MenuItem("Save");
		save.setOnAction(ex -> {
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"));
			String defaultFolder = "data\\import\\";
			String currentPath = Paths.get(defaultFolder).toAbsolutePath().normalize().toString();
			fileChooser.setInitialDirectory(new File(currentPath));
			File selectedFile = fileChooser.showSaveDialog(primaryStage);
			if (selectedFile != null) {
				try {
					selectedFile.createNewFile();
					Save(TextPane.getMainTextBox().getText(), selectedFile);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		MenuItem exit = new MenuItem("Exit Program");
		exit.setOnAction(e -> {
			Platform.exit();
		});
		fileMenu.getItems().addAll(newDoc, open, close, save, exit);
	}

	public MenuBar getMenuBar() {
		return menuBar;
	}

	public String[] getTextContentArray() {
		return textContentArray;
	}

	public static void setTextContentArray(String[] textContentArray) {
		MenuBarPane.textContentArray = textContentArray;
	}

	private void Save(String text, File file) {
		try {
			FileWriter fw;
			fw = new FileWriter(file);
			fw.write(text);
			fw.close();
		} catch (IOException ex) {
		}
	}
}