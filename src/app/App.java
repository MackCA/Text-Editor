package app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Graph;
import model.TextAnalysis;
import view.MenuBarPane;
import view.StatusBarPane;
import view.TextPane;


public class App extends Application{

	public static void main(String[] args) {

		launch(args);
	}

	public void start(Stage primaryStage){

		BorderPane root = new BorderPane();
		StatusBarPane statusBar = new StatusBarPane();
		TextPane textPane = new TextPane(statusBar);
		TextAnalysis analysis = new TextAnalysis();
		//Backup.restoreTrainedData();
		Graph graph = new Graph(statusBar);
		root.setTop(new MenuBarPane(root, statusBar.getTextPane(),primaryStage,statusBar,analysis,graph).getMenuBar());
		root.setCenter(textPane.getTextGrid());
		root.setBottom(statusBar.getStatusBarBox());
		Scene scene = new Scene(root, 1600, 900);
		scene.getStylesheets().add("stylesheet.css");
		primaryStage.setScene(scene);
		primaryStage.setTitle("Text Editor");
		primaryStage.show();
	}



}
