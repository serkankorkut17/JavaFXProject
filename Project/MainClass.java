import java.io.File;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainClass extends Application{

	public static void main(String[] args) {
		launch();
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		BorderPane mainPane = new BorderPane();
		
		Pane topPane = new Pane();
		topPane.setMinHeight(10);
		topPane.getChildren().add(new Label("Level #1"));
		
		Pane bottomPane = new Pane();
		bottomPane.getChildren().add(new Label("---Text---"));
		
		File level = new File("Level5.txt");
		GamePane gamePane = new GamePane(level);
		
		mainPane.setCenter(gamePane);
		mainPane.setTop(topPane);
		mainPane.setBottom(bottomPane);
		
		Scene scene = new Scene(mainPane);
		stage.setScene(scene);
		stage.show();
	}

}
